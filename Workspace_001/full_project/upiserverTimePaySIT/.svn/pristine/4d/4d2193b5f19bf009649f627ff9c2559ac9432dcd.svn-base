package com.npst.upiserver.acquirer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobSpamCollectReject;
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
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobSpamCollectRejectImpl implements MobSpamCollectReject {
	private static final Logger	log	= LoggerFactory.getLogger(MobSpamCollectRejectImpl.class);
	
	@Autowired
	ReqRespAuthDetailsDao		reqRespAuthDetailsDao;
	@Autowired
	ReqRespAuthDetailsPayeesDao	reqRespAuthDetailsPayeesDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	CustomerTxnsDao				custTxnDao;
	
	@Override
	public void create(String payeeAdd) {
		try {
			List<ReqRespAuthDetails> reqrespauthdetailslist = reqRespAuthDetailsDao.getOnIdReqRespAuthDetails();
			if (0 < reqrespauthdetailslist.size()) {
				for (ReqRespAuthDetails reqrespauthdetails : reqrespauthdetailslist) {
					ReqRespAuthDetailsPayees dbPayee = reqRespAuthDetailsPayeesDao.getPayeesByTxnId(payeeAdd,reqrespauthdetails.getIdReqRespAuthDetails().toString());
					if (null != reqrespauthdetails) {
						String ts = Util.getTS();
						String txnId = reqrespauthdetails.getTxnId();
						String msgId = Util.uuidGen();
						RespAuthDetails respAuthDetails = new RespAuthDetails();
						respAuthDetails.setHead(setHeadTypeDetails(msgId, ts));
						PayTrans txn = setPayTransDetails(txnId, reqrespauthdetails.getTxnNote(), reqrespauthdetails.getTxnRefid(),
								reqrespauthdetails.getTxnRefurl(), ts, reqrespauthdetails.getTxnCustRef(), reqrespauthdetails.getTxnType());
						respAuthDetails.setTxn(txn);
						PayerType payer =setPayerTypeDetails(reqrespauthdetails.getPayerAddr(),reqrespauthdetails.getPayerName(),
								reqrespauthdetails.getPayerSeqNum(),reqrespauthdetails.getPayerType(),reqrespauthdetails.getPayerCode(),reqrespauthdetails.getAmountVal());
						
						respAuthDetails.setPayer(payer);
						PayeesType payees = new PayeesType();
						List<PayeeType> payeeList = payees.getPayee();
						
						PayeeType payee = setPayeeTypeDetails(dbPayee.getPayeeAddr(), dbPayee.getPayeeName(), dbPayee.getPayeeSeqNum(), 
								dbPayee.getPayeeType(), dbPayee.getPayeeCode(), 
								reqrespauthdetails.getAmountVal(), dbPayee.getInfoIdType(), dbPayee.getInfoIdVerifiedName(), dbPayee.getInfoId(),
								dbPayee.getAcAddrType(),dbPayee.getAcAddrTypeDetail1(),dbPayee.getAcAddrTypeDetail2(),dbPayee.getAcAddrTypeDetail3());
						
						payeeList.add(payee);
						respAuthDetails.setPayees(payees);
						RespType resp = new RespType();
						resp.setReqMsgId(reqrespauthdetails.getReqHeadMsgId());
						resp.setResult(ConstantI.FAILURE);
						
						resp.setErrCode(Constant.TRANSACTIONDECLINEDBYPSPS0);
						respAuthDetails.setResp(resp);
						String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthDetails).toString());
						Ack ack = npciService.send(xmlStr,ConstantI.RESP_AUTH_DETAILS, txnId);
						if(ack!=null) {
							reqRespAuthDetailsDao.updateResp(respAuthDetails, ack);
							custTxnDao.update(respAuthDetails, ack);
						}
						else {
							reqRespAuthDetailsDao.updateResp(respAuthDetails, null);
							custTxnDao.update(respAuthDetails, null);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
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
	private PayTrans setPayTransDetails(final String txnId, final String txnNote, final String txnRefId,
			final String txnRefUrl, final String ts, final String rrn, String txnType)throws Exception {
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setCustRef(rrn);
			txn.setType(PayConstant.fromValue(txnType));
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txn;
	}
	
	private PayerType setPayerTypeDetails(final String payerAddr,final String payerName,final String payerSeqNum,final String payerType,final String payerCode,final String amountVal)throws Exception {
		PayerType payer = new PayerType();
		try {
			payer.setAddr(payerAddr);
			payer.setName(payerName);
			payer.setSeqNum(payerSeqNum);
			payer.setType(PayerConstant.fromValue(payerType));
			payer.setCode(payerCode);
			AmountType amount = new AmountType();
			amount.setValue(amountVal);
			amount.setCurr(ConstantI.INR);
			payer.setAmount(amount);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}
	
	private PayeeType setPayeeTypeDetails(final String payeeAddr,final String payeeName,final String payeeSeqNum,final String payeeType,
			final String payeeCode,final String amountVal,final String infoIdType,final String infoIdVerifiedName,final String infoId,
			final String acAddrType,final String acAddrTypeDetail1,final String acAddrTypeDetail2,final String acAddrTypeDetail3
			)throws Exception {
		PayeeType payee = new PayeeType();
		try {
			AmountType amount = new AmountType();
			amount.setValue(amountVal);
			amount.setCurr(ConstantI.INR);
			payee.setAmount(amount);
			payee.setAddr(payeeAddr);
			payee.setName(payeeName);
			payee.setSeqNum(payeeSeqNum);
			payee.setType(PayerConstant.fromValue(payeeType));
			payee.setCode(payeeCode);
			InfoType	info = new InfoType();
			IdentityType identity = new IdentityType();
			RatingType rating = new RatingType();
			identity.setType(IdentityConstant.fromValue(infoIdType));
			identity.setVerifiedName(infoIdVerifiedName);
			identity.setId(infoId);
			info.setIdentity(identity);
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
			payee.setInfo(info);
			
			AccountType ac = new AccountType();
			Detail detail = new Detail();
			List<Detail> details = ac.getDetail();
			ac.setAddrType(AddressType.fromValue(acAddrType));
			details = ac.getDetail();
			ArrayList<String> detailList1 = Util.strSplit(acAddrTypeDetail1, ConstantI.CHAR_CONST_PIPE);
			detail = new Detail();
			detail.setName(AccountDetailType.fromValue(Util.strSplit(detailList1.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
			detail.setValue(Util.strSplit(detailList1.get(0),  ConstantI.CHAR_CONST_EQUAL).get(1));
			details.add(detail);
			ArrayList<String> detailList2 = Util.strSplit(acAddrTypeDetail2,  ConstantI.CHAR_CONST_PIPE);
			detail = new Detail();
			detail.setName(AccountDetailType.fromValue(Util.strSplit(detailList2.get(0),  ConstantI.CHAR_CONST_EQUAL).get(0)));
			detail.setValue(Util.strSplit(detailList2.get(0),  ConstantI.CHAR_CONST_EQUAL).get(1));
			details.add(detail);
			if (null != acAddrTypeDetail3) {
				ArrayList<String> detailList3 = Util.strSplit(acAddrTypeDetail3,  ConstantI.CHAR_CONST_PIPE);
				detail = new Detail();
				detail.setName(AccountDetailType.fromValue(Util.strSplit(detailList3.get(0),  ConstantI.CHAR_CONST_EQUAL).get(0)));
				detail.setValue(Util.strSplit(detailList3.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
				details.add(detail);
			}
			payee.setAc(ac);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payee;
	}
}
