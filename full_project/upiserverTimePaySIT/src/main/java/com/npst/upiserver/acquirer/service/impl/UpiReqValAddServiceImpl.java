package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiReqValAddService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.ReqRespValAddDao;
import com.npst.upiserver.dto.ReqResp.Mandate;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.npcischema.Ack;
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
import com.npst.upiserver.npcischema.RespPType;
import com.npst.upiserver.npcischema.RespTypeValAddr;
import com.npst.upiserver.npcischema.RespValAdd;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqValAddServiceImpl implements UpiReqValAddService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqValAddServiceImpl.class);
	
	
	@Autowired
	ReqRespValAddDao reqRespValAddDao;
	
	@Autowired
	CustomerAccountDao		custAccDao;
	@Autowired
	NpciUpiRestConService		npciResponseService;
	@Override
	public void acquirerProcess(final ReqValAdd reqValAdd) {
		log.debug("ACQUIRER reqValAdd {}", reqValAdd);
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
			
			List<CustomerAccount> resultListaccount = custAccDao.findActiveAccounts(reqValAdd.getPayee().getAddr(),ConstantI.RR);
			if (resultListaccount== null || 0 == resultListaccount.size()) {
				resp.setResult(ConstantI.FAILURE);
				resp.setErrCode(ConstantI.RESP_ZH);
				resp.setMaskName(ConstantI.CODE_NA);
				// to do
				/*resp.setMaskName("Testing Har");
				resp.setCode("0000");
				resp.setIIN(Constant.BANK_IIN);
				resp.setIFSC("COSB0000000");
				resp.setType("PERSON");// PERSON/ENTITY
				resp.setPType(RespPType.UPIMANDATE);
				resp.setAccType(ListedAccountType.SAVINGS);
				resp.setResult(ConstantI.SUCCESS);*/
				log.info("if case ");
				
			} else {
				for (CustomerAccount listaccount : resultListaccount) {
					if(ConstantI.ENTITY.equalsIgnoreCase(listaccount.getCusttype())) {	
						resp.setMerchant(getMerchantType(listaccount));
						log.info("Merchant Tag Added");
					}
					resp.setResult(ConstantI.SUCCESS);
					resp.setMaskName(listaccount.getName());
					resp.setAccType(ListedAccountType.fromValue(listaccount.getAcctype()));
					resp.setType(listaccount.getCusttype());
					resp.setIFSC(listaccount.getIfsc());
					resp.setPType(RespPType.UPIMANDATE);//new 
					resp.setCode(listaccount.getCustcode());
					resp.setIIN(Constant.BANK_IIN);//new
				}
				/*resp.setMaskName("Testing Har");
				resp.setCode("0000");
				resp.setIIN(Constant.BANK_IIN);
				resp.setIFSC("COSB0000012");
				resp.setType("PERSON");// PERSON/ENTITY
				resp.setPType(RespPType.UPIMANDATE);
				resp.setAccType(ListedAccountType.SAVINGS);
				resp.setResult(ConstantI.SUCCESS);*/
			}
			resp.setReqMsgId(reqValAdd.getHead().getMsgId());
			respValAdd.setResp(resp);
			respValAdd.setTxn(reqValAdd.getTxn());
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respValAdd).toString());
			log.info("Response {} ",xmlStr);
			Ack ack = npciResponseService.send(xmlStr,ConstantI.API_RESP_VAL_ADD, reqValAdd.getTxn().getId());
			if(ack!=null) {
				reqRespValAddDao.insertReqResp(reqValAdd, respValAdd, ack);
			}
		}
		catch(Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	private void setRespByMandates(Mandate mandatesEntity, final RespTypeValAddr resp) {
		resp.setResult(ConstantI.SUCCESS);
		resp.setIFSC("CNRB0000001");
		resp.setAccType(ListedAccountType.SAVINGS);
		resp.setCode("0000");
		//resp.setErrCode(ConstantI.RESP_ZH);
		// TODO will check payer or payee initiated
		// resp.setResult(ConstantI.SUCCESS);
		//resp.setMaskName(mandatesEntity.getInfoIdVerifiedName());
		resp.setPType(RespPType.UPIMANDATE);
		resp.setIIN("508532");
		resp.setType("PERSON");
		// TODO
		// PERSON/ENTITY
		// resp.setType(type);
		// resp.setAccType(ListedAccountType.fromValue(mandatesEntity.getAccType()));
		// resp.setIFSC(mandatesEntity.getIfsc());
		// resp.setCode(mandatesEntity.getCustCode());
		// TODO need discussion
		// resp.setIIN();
		//if ("ENTITY".equalsIgnoreCase(resp.getType())) {
		//	resp.setMerchant(getMerchantType(mandatesEntity));
		//}

	}
	
	
		private MerchantType getMerchantType(CustomerAccount dto) {
		MerchantType merchant = new MerchantType();
		IdentifierType iden = new IdentifierType();
		iden.setMerchantGenre(MerchantGenreType.OFFLINE);
		iden.setMerchantType(MerchantIdentifierType.SMALL);
		iden.setMid(String.valueOf(dto.getRegid()));
		iden.setOnBoardingType(MerchantOnBoardingType.BANK);
		iden.setSid(ConstantI.MID_CONT);
		iden.setSubCode(dto.getCustcode());
		iden.setTid(ConstantI.MID_CONT);
		merchant.setIdentifier(iden);
		NameType nameType = new NameType();

		if(dto.getName().length() > ConstantI.NAME_Length) {
			nameType.setBrand(dto.getName().substring(0, 14));
			nameType.setLegal(dto.getName().substring(0, 14));
			nameType.setFranchise(dto.getName().substring(0, 14));
		}
		else {
			nameType.setBrand(dto.getName());
			nameType.setLegal(dto.getName());
			nameType.setFranchise(dto.getName());
		}
		merchant.setName(nameType);
		MerchantOwnership ownership = new MerchantOwnership();
		ownership.setType(OwnershipType.PRIVATE);
		merchant.setOwnership(ownership);
		return merchant;
	}
}
