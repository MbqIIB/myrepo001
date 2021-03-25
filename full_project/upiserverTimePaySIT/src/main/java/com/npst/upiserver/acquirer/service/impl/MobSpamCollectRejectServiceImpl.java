package com.npst.upiserver.acquirer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.acquirer.service.MobSpamCollectRejectService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsPayeesDao;
import com.npst.upiserver.entity.ReqRespAuthDetails;
import com.npst.upiserver.entity.ReqRespAuthDetailsPayees;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.AmountType;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.IdentityConstant;
import com.npst.upiserver.npcischema.IdentityType;
import com.npst.upiserver.npcischema.InfoType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Component
public class MobSpamCollectRejectServiceImpl implements MobSpamCollectRejectService {
	private static final Logger log = LoggerFactory.getLogger(MobSpamCollectRejectServiceImpl.class);
	@Autowired
	private ReqRespAuthDetailsDao reqRespAuthDetailsDao;
	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	@Autowired
	private CustomerTxnsDao customerTxnsDao;
	@Autowired
	private ReqRespAuthDetailsPayeesDao reqRespAuthDetailsPayeesDao;

	@Override
	public void create(String payeeAddr) {
		try {
			List<ReqRespAuthDetails>reqrespauthdetailslist = reqRespAuthDetailsDao.getAllByCollectAndRespInsertIsNull();
			if (0 < reqrespauthdetailslist.size()) {
				for (ReqRespAuthDetails reqrespauthdetails : reqrespauthdetailslist) {
					ReqRespAuthDetailsPayees dbPayee = reqRespAuthDetailsPayeesDao.getByPayeesVpaAndIdReqRespAuthDetails(payeeAddr,
									reqrespauthdetails.getIdReqRespAuthDetails());
					if (null != reqrespauthdetails) {
						String ts = Util.getTS();
						String txnId = reqrespauthdetails.getTxnId();
						String msgId = Util.uuidGen();
						RespAuthDetails respAuthDetails = new RespAuthDetails();
						HeadType head = new HeadType();
						head.setMsgId(msgId);
						head.setOrgId(Constant.orgId);
						head.setTs(ts);
						head.setVer(Constant.headVer);
						respAuthDetails.setHead(head);
						PayTrans txn = new PayTrans();
						txn.setId(txnId);
						txn.setNote(reqrespauthdetails.getTxnNote());
						txn.setRefId(reqrespauthdetails.getTxnRefid());
						txn.setRefUrl(reqrespauthdetails.getTxnRefurl());
						txn.setTs(ts);
						txn.setCustRef(reqrespauthdetails.getTxnCustRef());
						txn.setType(PayConstant.fromValue(reqrespauthdetails.getTxnType()));
						respAuthDetails.setTxn(txn);
						PayerType payer = new PayerType();
						payer.setAddr(reqrespauthdetails.getPayerAddr());
						payer.setName(reqrespauthdetails.getPayerName());
						payer.setSeqNum(reqrespauthdetails.getPayerSeqNum());
						payer.setType(PayerConstant.fromValue(reqrespauthdetails.getPayerType()));
						payer.setCode(reqrespauthdetails.getPayerCode());
						InfoType info = new InfoType();
						IdentityType identity = new IdentityType();
						RatingType rating = new RatingType();
						AmountType amount = new AmountType();
						amount.setValue(reqrespauthdetails.getAmountVal());
						amount.setCurr(ConstantI.INR);
						payer.setAmount(amount);
						respAuthDetails.setPayer(payer);
						PayeesType payees = new PayeesType();
						List<PayeeType> payeeList = payees.getPayee();
						PayeeType payee = new PayeeType();
						payee.setAddr(dbPayee.getPayeeAddr());
						payee.setName(dbPayee.getPayeeName());
						payee.setSeqNum(dbPayee.getPayeeSeqNum());
						payee.setType(PayerConstant.fromValue(dbPayee.getPayeeType()));
						payee.setCode(dbPayee.getPayeeCode());
						payee.setAmount(amount);
						info = new InfoType();
						identity = new IdentityType();
						identity.setType(IdentityConstant.fromValue(dbPayee.getInfoIdType()));
						identity.setVerifiedName(dbPayee.getInfoIdVerifiedName());
						identity.setId(dbPayee.getInfoId());
						info.setIdentity(identity);
						rating.setVerifiedAddress(WhiteListedConstant.TRUE);
						info.setRating(rating);
						payee.setInfo(info);
						AccountType ac = new AccountType();
						Detail detail = new Detail();
						List<Detail> details = ac.getDetail();
						ac.setAddrType(AddressType.fromValue(dbPayee.getAcAddrType()));
						details = ac.getDetail();
						ArrayList<String> detailList1 = Util.strSplit(dbPayee.getAcAddrTypeDetail1(), '|');
						detail = new Detail();
						detail.setName(AccountDetailType.fromValue(Util.strSplit(detailList1.get(0), '=').get(0)));
						detail.setValue(Util.strSplit(detailList1.get(0), '=').get(1));
						details.add(detail);
						ArrayList<String> detailList2 = Util.strSplit(dbPayee.getAcAddrTypeDetail2(), '|');
						detail = new Detail();
						detail.setName(AccountDetailType.fromValue(Util.strSplit(detailList2.get(0), '=').get(0)));
						detail.setValue(Util.strSplit(detailList2.get(0), '=').get(1));
						details.add(detail);
						if (null != dbPayee.getAcAddrTypeDetail3()) {
							ArrayList<String> detailList3 = Util.strSplit(dbPayee.getAcAddrTypeDetail3(), '|');
							detail = new Detail();
							detail.setName(AccountDetailType.fromValue(Util.strSplit(detailList3.get(0), '=').get(0)));
							detail.setValue(Util.strSplit(detailList3.get(0), '=').get(1));
							details.add(detail);
						}
						payee.setAc(ac);
						payeeList.add(payee);
						respAuthDetails.setPayees(payees);
						RespType resp = new RespType();
						resp.setReqMsgId(reqrespauthdetails.getReqHeadMsgId());
						resp.setResult(ConstantI.FAILURE);
						resp.setErrCode(Constant.TRANSACTIONDECLINEDBYPSPS0);
						respAuthDetails.setResp(resp);
						String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthDetails).toString());
						Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.RESP_AUTH_DETAILS, txnId);
						reqRespAuthDetailsDao.updateResp(respAuthDetails, ack);
						customerTxnsDao.update(respAuthDetails, ack);
					}
				}
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobSpamCollectRejectServiceImpl.class);
		}

	}
}
