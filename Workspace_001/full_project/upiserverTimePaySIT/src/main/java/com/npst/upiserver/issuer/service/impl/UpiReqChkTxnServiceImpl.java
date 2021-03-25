package com.npst.upiserver.issuer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespDebitCreditDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.ReqRespDebitCredit;
import com.npst.upiserver.issuer.service.UpiReqChkTxnService;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.ListedAccountType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.RefType;
import com.npst.upiserver.npcischema.ReqChkTxn;
import com.npst.upiserver.npcischema.RespChkTxn;
import com.npst.upiserver.npcischema.RespChkTxn.Resp;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.service.impl.MiddlewareRestConServiceImpl;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqChkTxnServiceImpl implements UpiReqChkTxnService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqChkTxnServiceImpl.class);

	@Autowired
	ReqRespDebitCreditDao reqRespDebitCredit;
	
	@Autowired
	MiddlewareRestConServiceImpl  middlewareService;
	
	@Autowired
	NpciUpiRestConServiceImpl  npciResponseService;
	
	@Override
	public void issuerProcess(final ReqChkTxn reqChkTxn) {
		log.info("reqChkTxn {}", reqChkTxn);
		
		Resp resp = new Resp();
		try {
 			String msgId = Util.uuidGen();
			String ts = Util.getTS();
			ReqRespDebitCredit reqrespdebitcredit = reqRespDebitCredit.getOnTxnIdAndTxnType(reqChkTxn);
			if (reqrespdebitcredit != null) {
				log.info("reqrespdebitcredit is not null",reqrespdebitcredit);
				RespChkTxn respChkTxn = new RespChkTxn();
				respChkTxn.setHead(setHeadTypeDetails(msgId, ts));
				List<Ref> refs = resp.getRef();
				// To DO
				Ref ref =setRefDetails(reqrespdebitcredit.getRefAddr(),reqrespdebitcredit.getRefRegName(), reqChkTxn.getTxn().getCustRef().substring(6), reqrespdebitcredit.getTxnType(), reqrespdebitcredit.getAmountCrr(),reqrespdebitcredit.getRefAcNum(),"",""); // To DO
				refs.add(ref);
				respChkTxn.setTxn(reqChkTxn.getTxn());
				resp.setReqMsgId(reqChkTxn.getHead().getMsgId());
				ReqResp req =setReqRespDetails(reqChkTxn.getTxn().getId(), reqChkTxn.getTxn().getType().value(), reqrespdebitcredit.getTxnCustRef(), reqrespdebitcredit.getTxnType());
				req.setIsUPI2("1");
				req = middlewareService.send(req);
				if (reqChkTxn.getTxn().getSubType().toString().equalsIgnoreCase(ConstantI.DEBIT)) {
					
					ref.setAccType(setAcType(req));
					ref.setIFSC(req.getPayerIfsc());
					//ref.setAcNum(req.getPayerAcNum());	
				}
				else{
					ref.setAccType(setAcTypePayee(req));
					ref.setIFSC(req.getPayeeIfsc());
					//ref.setAcNum(req.getPayeeAcNum());
				}
				if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
					resp.setResult(ResultType.SUCCESS.value());
					resp.setErrCode(ConstantI.CONST_BLANK);
					ref.setRespCode(ConstantI.CODE_00);
					ref.setSettAmount(reqrespdebitcredit.getAmountVal());
				} else {
					resp.setResult(ResultType.FAILURE.value());
					resp.setErrCode(req.getRespCode().trim());
					ref.setRespCode(req.getRespCode().trim());
					ref.setSettAmount(ConstantI.MIN_AMT);
					if (resp.getRef() != null) {
						if (resp.getRef().get(0) != null) {
							resp.getRef().get(0).setApprovalNum(null);
							if (ConstantI.NO.equalsIgnoreCase(req.getRespCode()) || ConstantI.CODE_XY.equalsIgnoreCase(req.getRespCode())) {
								resp.getRef().get(0).setAddr(ConstantI.CONST_BLANK);
								resp.getRef().get(0).setApprovalNum(ConstantI.CONST_BLANK);
								resp.getRef().get(0).setSeqNum(ConstantI.CONST_BLANK);
								resp.getRef().get(0).setType(null);
							}
						}
					}
				}
				respChkTxn.setResp(resp);
				respChkTxn.getResp().getRef().get(0).setRegName(reqrespdebitcredit.getRefRegName().replace(ConstantI.CONST_SPCL_PIPE,ConstantI.CONST_BLANK));
				if (ConstantI.NO.equalsIgnoreCase(req.getRespCode()) || ConstantI.CODE_XY.equalsIgnoreCase(req.getRespCode())) {
					respChkTxn.getResp().getRef().get(0).setRegName(null);
				}
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respChkTxn).toString());
				log.info("Sending Response XML of CheckTxn to NPCI------------>" + xmlStr);
				if (!ConstantI.UKN.equals(req.getRespCode())) {
					log.info("Sending Response back to NPCI");
					npciResponseService.send(xmlStr, ConstantI.API_RESP_CHK_TRANS, respChkTxn.getTxn().getOrgTxnId());
				}
			}
			else {
				log.info("If null then "); // To be discussed
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private ListedAccountType setAcTypePayee(ReqResp req) {
		if("SAVINGS".equalsIgnoreCase(req.getPayeeAcType()))
		{
			return ListedAccountType.SAVINGS;
		}
		
		else if("CURRENT".equalsIgnoreCase(req.getPayeeAcType()))
		{
			return ListedAccountType.CURRENT;
		}
		else if("DEFAULT".equalsIgnoreCase(req.getPayeeAcType()))
		{
			return ListedAccountType.DEFAULT;
		}
		else if("NRE".equalsIgnoreCase(req.getPayeeAcType()))
		{
			return ListedAccountType.NRE;
		}
		else if("NRO".equalsIgnoreCase(req.getPayeeAcType()))
		{
			return ListedAccountType.NRO;
		}
		else if("CREDIT".equalsIgnoreCase(req.getPayeeAcType()))
		{
			return ListedAccountType.CREDIT;
		}
		
		else if("SOD".equalsIgnoreCase(req.getPayeeAcType()))
		{
			return ListedAccountType.SOD;
		}
		else if("UOD".equalsIgnoreCase(req.getPayeeAcType()))
		{
			return ListedAccountType.UOD;
		}
		return ListedAccountType.SAVINGS;
	}

	private ListedAccountType setAcType(ReqResp req) {
		if("SAVINGS".equalsIgnoreCase(req.getPayerAcType()))
		{
			return ListedAccountType.SAVINGS;
		}
		
		else if("CURRENT".equalsIgnoreCase(req.getPayerAcType()))
		{
			return ListedAccountType.CURRENT;
		}
		else if("DEFAULT".equalsIgnoreCase(req.getPayerAcType()))
		{
			return ListedAccountType.DEFAULT;
		}
		else if("NRE".equalsIgnoreCase(req.getPayerAcType()))
		{
			return ListedAccountType.NRE;
		}
		else if("NRO".equalsIgnoreCase(req.getPayerAcType()))
		{
			return ListedAccountType.NRO;
		}
		else if("CREDIT".equalsIgnoreCase(req.getPayerAcType()))
		{
			return ListedAccountType.CREDIT;
		}
		
		else if("SOD".equalsIgnoreCase(req.getPayerAcType()))
		{
			return ListedAccountType.SOD;
		}
		else if("UOD".equalsIgnoreCase(req.getPayerAcType()))
		{
			return ListedAccountType.UOD;
		}
		return ListedAccountType.SAVINGS;
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
	
	private Ref setRefDetails(final String payerAddr,final String regName,final String custRef,final String txnType,final String amountCrr,final String acNum,final String ifsc,final String accountType)  throws Exception{
		Ref ref = new Ref();
		try {
			ref.setAddr(payerAddr);
			ref.setRegName(regName);
			ref.setApprovalNum(custRef);
			ref.setRespCode(ConstantI.CODE_00);
			if (PayConstant.CREDIT.value().equalsIgnoreCase(txnType)) {
				ref.setType(RefType.PAYEE);
			} else {
				ref.setType(RefType.PAYER);
			}
			ref.setCode(ConstantI.CODE_0000);
			ref.setSettCurrency(amountCrr);
			ref.setSeqNum(ConstantI.CONSTANT_SEQ_NUM_1);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return ref;
	}
	
	/*private Ref setRefDetails(final String payerAddr,final String custRef,final String txnType,final String amountCrr,final String acNum)  throws Exception{
		Ref ref = new Ref();
		try {
			ref.setAddr("9540202589@klgb");
			ref.setRegName("");
			ref.setApprovalNum(custRef);
			ref.setRespCode(ConstantI.CODE_00);
			if (PayConstant.CREDIT.value().equalsIgnoreCase(txnType)) {
				ref.setType(RefType.PAYEE);
			} else {
				ref.setType(RefType.PAYER);
			}
			ref.setCode("0000");
			ref.setSettCurrency(amountCrr);
			ref.setSeqNum(ConstantI.CONSTANT_SEQ_NUM_1);
			ref.setAcNum("40112100114278");
			ref.setIFSC("KLGB0000001");
			ref.setAccType(ListedAccountType.SAVINGS);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return ref;
	}
	*/
	private ReqResp setReqRespDetails(final String txnId,final String type,final String txnCustRef,final String txnType)  throws Exception{
		ReqResp req = new ReqResp();
		try {
			req.setBkPrf(txnId.substring(0, 3));
			req.setTxnType(type);
			req.setTxnId(txnId);
			req.setRrn(txnCustRef);
			req.setOrgTxnType(txnType);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return req;
	}
}
