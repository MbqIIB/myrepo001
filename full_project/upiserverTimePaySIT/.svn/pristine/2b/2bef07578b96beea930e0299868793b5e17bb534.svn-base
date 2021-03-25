package com.npst.upiserver.issuer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespValAddDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.dto.ReqResp.Account;
import com.npst.upiserver.issuer.service.IssuerUpiReqValAddService;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.IdentifierType;
import com.npst.upiserver.npcischema.ListedAccountType;
import com.npst.upiserver.npcischema.MerchantGenreType;
import com.npst.upiserver.npcischema.MerchantIdentifierType;
import com.npst.upiserver.npcischema.MerchantOnBoardingType;
import com.npst.upiserver.npcischema.MerchantOwnership;
import com.npst.upiserver.npcischema.MerchantType;
import com.npst.upiserver.npcischema.NameType;
import com.npst.upiserver.npcischema.OwnershipType;
import com.npst.upiserver.npcischema.ReqValAdd;
import com.npst.upiserver.npcischema.RespTypeValAddr;
import com.npst.upiserver.npcischema.RespValAdd;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.service.impl.MiddlewareRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class IssuerUpiReqValAddServiceImpl implements IssuerUpiReqValAddService {
	private static final Logger log = LoggerFactory.getLogger(IssuerUpiReqValAddServiceImpl.class);
	@Autowired
	NpciUpiRestConService npciResponseService;
	@Autowired
	ReqRespValAddDao reqRespValAddDao;
	@Autowired
	MiddlewareRestConServiceImpl middlewareService;
	
	@Autowired
	private IdGeneratorService idGeneratorService;

	@Override
	public void issuerProcess(final ReqValAdd reqValAdd) {
		log.info("issuer reqValAdd :{}", reqValAdd);
		try {
			String msgId = Util.uuidGen();
			String ts = Util.getTS();
			RespValAdd respValAdd = new RespValAdd();
			HeadType head = new HeadType();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			respValAdd.setHead(head);
			RespTypeValAddr resp = new RespTypeValAddr();
			String payeeAddress = reqValAdd.getPayee().getAddr().toLowerCase();
			getValFromMiddl(resp, payeeAddress);
			resp.setReqMsgId(reqValAdd.getHead().getMsgId());
			respValAdd.setResp(resp);
			respValAdd.setTxn(reqValAdd.getTxn());
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respValAdd).toString());
			log.info("UpiReqValAdd Sending request to NPCI : {} ", xmlStr);
			Ack ack = npciResponseService.send(xmlStr, ConstantI.API_RESP_VAL_ADD, reqValAdd.getTxn().getId());
			log.info("Acknowledgement from NPCI {} ", ack);
			try {
				reqRespValAddDao.insertReqResp(reqValAdd, respValAdd, ack);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error :{}", e);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void getValFromMiddl(final RespTypeValAddr resp, final String payeeAddress) throws Exception {
		ReqResp req = new ReqResp();
		log.info("ReqValAdd Sending request to middlware :  {} ", req);
		try {
			log.info("payeeAddress {}", payeeAddress);
			String[] temp = payeeAddress.split(ConstantI.AT_SYMBOL);
			String ifsc = null;
			if (payeeAddress.endsWith(ConstantI.ADDR_END_IFSC_NPCI)) {
				req.setPayeeAddrType(AddressType.ACCOUNT.toString());
				req.setPayeeAcNum(temp[0]);
				req.setPayeeAccNum(temp[0]);
				log.info("Payee Account Number:{}", temp[0]);
				req.setPayeeIfsc(temp[1].substring(0, 11));
				ifsc = req.getPayeeIfsc();
			} else if (payeeAddress.endsWith(ConstantI.ADDR_END_MMID_NPCI)) {
				req.setPayeeAddrType(AddressType.MOBILE.toString());
				req.setPayeeMobileNo(temp[0]);
				req.setPayeeMmid(temp[1].substring(0, 7));
			} else if (payeeAddress.endsWith(ConstantI.ADDR_END_IIN_NPCI)) {
				req.setPayeeAddrType(AddressType.AADHAAR.toString());
				req.setPayeeUidNum(temp[0]);
				req.setPayeeIin(temp[1].substring(0, 6));
			} else {
				log.error("error in resolving vpa addr");
				resp.setResult(ConstantI.FAILURE);
				resp.setErrCode(ConstantI.RESP_ZD);
				return;
			}
			req.setTxnType(ConstantI.API_REQ_VALADD);
			req.setPayeeAddr(payeeAddress);
			req.setRrn(idGeneratorService.getRrn());
			req.setIsUPI2("1");
			req = middlewareService.send(req);
			log.debug("IFSC {}, Acnum {} ", req.getPayeeIfsc(), req.getPayeeAccNum());
			log.info("Response Recieved from middlware :  {} ", req);
			if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode().trim())&&req.getPayeeName()!="") {
				setRespFinal(req.getAccounts(), resp, ifsc);
			} else {
				resp.setResult(ConstantI.FAILURE);
				resp.setErrCode(req.getRespCode());
			}
		} catch (Exception e) {
			resp.setResult(ConstantI.FAILURE);
			resp.setErrCode(ConstantI.XB);
			log.error("error in val addr from getValFromMiddl={}", e);
		}
	}

	private void setRespFinal(List<Account> accList, final RespTypeValAddr resp, final String ifsc) {

		log.info("To Set received params from middleware ");
		try {
			for (Account ac : accList) {
				// TODO if person is merchant
				resp.setMaskName(ac.getName());
				resp.setCode("0000");
				resp.setType("PERSON");
				resp.setAccType(ListedAccountType.valueOf(ac.getAccType()));
				resp.setIFSC(ifsc.toUpperCase());
				resp.setResult(ConstantI.SUCCESS);
				break;
			}
		} catch (Exception e) {
			log.error("error in setRespFinal Method={}", e);
		}
		resp.setResult(ConstantI.SUCCESS);
	}
	private static  MerchantType getMerchantType(String vpa, ReqResp req) {
		MerchantType merchant = new MerchantType();
		IdentifierType iden = new IdentifierType();
		iden.setMerchantGenre(MerchantGenreType.OFFLINE);
		iden.setMerchantType(MerchantIdentifierType.SMALL);
		iden.setMid("123454");
		iden.setOnBoardingType(MerchantOnBoardingType.BANK);
		iden.setSid("1234566");
		iden.setSubCode(req.getPayeeCode());
		iden.setTid("12345");
		merchant.setIdentifier(iden);
		NameType nameType = new NameType();
		
		if(req.getPayeeName().length() > ConstantI.NAME_Length) {	
		nameType.setBrand(req.getPayeeName().substring(0, 14));
		}
		else {
			nameType.setBrand(req.getPayeeName());
		}
		nameType.setLegal("ABC Canara");
		nameType.setFranchise("Canara MER");
		merchant.setName(nameType);//Fix
		MerchantOwnership ownership = new MerchantOwnership();
		ownership.setType(OwnershipType.PRIVATE);
		merchant.setOwnership(ownership);
		return merchant;
	}	
}
