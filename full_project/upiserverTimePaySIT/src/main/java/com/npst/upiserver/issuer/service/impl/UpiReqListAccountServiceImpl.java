package com.npst.upiserver.issuer.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespListAccDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.dto.ReqResp.Account.CredsAllowed;
import com.npst.upiserver.issuer.service.UpiReqListAccountService;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AebaType;
import com.npst.upiserver.npcischema.CredDataTypeConstant;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsAllowedType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.ListedAccountType;
import com.npst.upiserver.npcischema.ReqListAccount;
import com.npst.upiserver.npcischema.RespListAccount;
import com.npst.upiserver.npcischema.RespListAccount.AccountList.Account;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.impl.MiddlewareRestConServiceImpl;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqListAccountServiceImpl implements UpiReqListAccountService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqListAccountServiceImpl.class);

	@Autowired
	ReqRespListAccDao reqRespListAccDao;

	@Autowired
	MiddlewareRestConServiceImpl middlewareService;

	@Autowired
	NpciUpiRestConServiceImpl npciResponseService;
	
	@Autowired
	private IdGeneratorService idGeneratorService;

	@Override
	public void issuerProcess(final ReqListAccount reqListAccount) {
		log.trace("reqListAccount {}" ,  reqListAccount);
		try {
			log.debug("Mobile no is  {}", reqListAccount.getLink().getValue());
			String ts = Util.getTS();
			String msgId = Util.uuidGen();
			Date reqDate = new Date();
			RespListAccount respListAccount = new RespListAccount();
			respListAccount.setHead(setHeadTypeDetails(msgId, ts));
			respListAccount.setTxn(reqListAccount.getTxn());
			RespType resp = new RespType();
			resp.setReqMsgId(reqListAccount.getHead().getMsgId());
			ReqResp req = setReqRespDetails(reqListAccount.getTxn().getId(), reqListAccount.getLink().getValue(),
					reqListAccount.getLink().getType().value(), reqListAccount.getTxn().getType().value(),
					reqListAccount.getPayer().getAddr());
			try {
				boolean b = checkUSDCDevice(reqListAccount);
				if (b) {
					log.debug("Before going to send middleware");
					req.setIsUPI2("1");
					req = middlewareService.send(req);
					log.debug("recieved request on middleware {} ", req);
					if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
						resp.setResult(ConstantI.SUCCESS);
						List<Account> accounts = respListAccount.getAccountList().getAccount();
						for (com.npst.upiserver.dto.ReqResp.Account account : req.getAccounts()) {
							Account acc = new Account();
							List<CredsAllowed> credsAlloweds = account.getCredsAlloweds();
							List<CredsAllowedType> credsAllowedTypes = acc.getCredsAllowed();
							for (CredsAllowed credsAllowed : credsAlloweds) {
								CredsAllowedType credsAllowedType = new CredsAllowedType();
								credsAllowedType.setDLength(Integer.valueOf(credsAllowed.getdLength()));
								credsAllowedType.setDType(CredDataTypeConstant.valueOf(credsAllowed.getdType()));
								credsAllowedType.setSubType(CredSubType.valueOf(credsAllowed.getSubType()));
								credsAllowedType.setType(CredType.valueOf(credsAllowed.getType()));
								credsAllowedTypes.add(credsAllowedType);
							}
							acc.setAccType(ListedAccountType.fromValue(account.getAccType().trim()));
							acc.setMbeba(AebaType.fromValue(account.getMbeba()));
							acc.setAeba(AebaType.fromValue(account.getAeba()));
							acc.setIfsc("COSB0000012");//account.getIfsc().toUpperCase()
							acc.setMaskedAccnumber(account.getMaskedAccnumber());
							acc.setMmid(account.getMmid());
							acc.setName(account.getName());
							acc.setAccRefNumber(account.getAccRefNumber());
							accounts.add(acc);
						}
					} else {
						respListAccount.setAccountList(respListAccount.getAccountList());
						resp.setResult(ConstantI.FAILURE);
						resp.setErrCode(req.getRespCode());
					}
					
					if (ConstantI.UKN.equalsIgnoreCase(resp.getErrCode())) {
						resp.setErrCode(ConstantI.XB);
					}
					respListAccount.setResp(resp);
					respListAccount.setAccountList(respListAccount.getAccountList());
					String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respListAccount).toString());
					log.info("ResListAccount XML FILE" + xmlStr);
					Ack ack = npciResponseService.send(xmlStr, ConstantI.API_RESP_LIST_ACC,
							reqListAccount.getTxn().getId());
					if (!Constant.bKPrf.equalsIgnoreCase(respListAccount.getTxn().getId().substring(0, 3))) {
						reqRespListAccDao.insertReqResp(reqListAccount, respListAccount, ack, reqDate);
					}
				} else {
					resp.setResult(ConstantI.FAILURE);
					resp.setErrCode(ConstantI.XB);
				}
			} catch (Exception e) {
				log.info("ERROR_1 {}", e);
				log.info("ERROR_1 in ListAccount TxnId{}={} ,linkValue={} ,errorMsg={}", req.getTxnId(),
						req.getLinkValue(), e.getMessage());
				resp.setResult(ConstantI.FAILURE);
				resp.setErrCode(ConstantI.XB);
				ErrorLog.sendError(e, UpiReqListAccountServiceImpl.class);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private boolean checkUSDCDevice(final ReqListAccount reqListAccount) throws Exception {
		boolean f = false;
		boolean f2 = false;
		DeviceType deviceType = reqListAccount.getPayer().getDevice();
		if (deviceType == null) {
			return true;
		}
		for (Tag tag : deviceType.getTag()) {
			if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().name().toString())) {
				if (ConstantI.USDC.equalsIgnoreCase(tag.getValue())) {
					f = true;
				}
			}
			if (f && ConstantI.TELECOM.equalsIgnoreCase(tag.getName().name()) && !tag.getValue().equalsIgnoreCase("")) {
				f2 = true;
				break;
			}
		}
		if (f) {
			return f2;
		} else {
			return true;
		}
	}

	private HeadType setHeadTypeDetails(final String msgId, final String ts) throws Exception {
		HeadType head = new HeadType();
		try {
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return head;
	}

	private ReqResp setReqRespDetails(final String txnId, final String linkValue, final String linkType,
			final String txnType, final String payerAddr) throws Exception {
		ReqResp req = new ReqResp();
		try {
			req.setRrn(idGeneratorService.getRrn());
			req.setTxnId(txnId);
			req.setLinkValue(linkValue);
			req.setLinkType(linkType);
			req.setBkPrf(txnId.substring(0, 3));
			req.setTxnType(txnType);
			req.setPayerAddr(payerAddr);
			req.setField11(idGeneratorService.getRrn());
			req.setIsUPI2("1");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return req;
	}
}