package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespListAccDao;
import com.npst.upiserver.dao.RiskXHDao;
import com.npst.upiserver.entity.Registration;
import com.npst.upiserver.entity.ReqRespListAccount;
import com.npst.upiserver.entity.RiskXH;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsAllowedType;
import com.npst.upiserver.npcischema.ReqListAccount;
import com.npst.upiserver.npcischema.RespListAccount;
import com.npst.upiserver.repo.ReqRespListAccountRepository;
import com.npst.upiserver.repo.RiskXHRepository;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.Util;

@Component
public class ReqRespListAccDaoImpl implements ReqRespListAccDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespListAccDaoImpl.class);
	private static Integer listAccCounter = Constant.FRAUD_LIST_ACC_ERR_CODE_XH_COUNT;
	
	@Autowired
	ReqRespListAccountRepository reqRespListAccRepo;
	
	
	@Autowired
	RiskXHDao riskXHDao;
	
	@Override
	public void insertReqResp(ReqListAccount reqListAccount, RespListAccount respListAccount, Ack ack, Date reqDate) {
		try {

			ReqRespListAccount reqresplistaccount = new ReqRespListAccount();
			reqresplistaccount.setReqHeadMsgId(reqListAccount.getHead().getMsgId());
			reqresplistaccount.setReqHeadOrgId(reqListAccount.getHead().getOrgId());
			reqresplistaccount.setReqHeadTs(reqListAccount.getHead().getTs());
			reqresplistaccount.setRespHeadMsgId(respListAccount.getHead().getMsgId());
			reqresplistaccount.setRespHeadOrgId(respListAccount.getHead().getOrgId());
			reqresplistaccount.setRespHeadTs(respListAccount.getHead().getTs());
			reqresplistaccount.setTxnId(reqListAccount.getTxn().getId());
			reqresplistaccount.setTxnIdPrf(respListAccount.getTxn().getId().substring(0, 3));
			reqresplistaccount.setTxnNote(reqListAccount.getTxn().getNote());
			reqresplistaccount.setTxnRefid(reqListAccount.getTxn().getRefId());
			reqresplistaccount.setTxnRefurl(reqListAccount.getTxn().getRefUrl());
			reqresplistaccount.setTxnTs(reqListAccount.getTxn().getTs());
			reqresplistaccount.setTxnType(reqListAccount.getTxn().getType().value());
			reqresplistaccount.setLink(reqListAccount.getLink().getType().value() + ConstantI.CONST_SPCL_EQUAL + reqListAccount.getLink().getValue());
			reqresplistaccount.setAcAddrType(reqListAccount.getPayer().getAc().getAddrType().value());
			List<Detail> acDetails = reqListAccount.getPayer().getAc().getDetail();
			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 = ConstantI.CONST_BLANK, AcAddrTypeDetail3 = ConstantI.CONST_BLANK;
			for (int i = 0; i < acDetails.size(); i++) {
				String temp = acDetails.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL + acDetails.get(i).getValue();
				if (0 == i) {
					acAddrTypeDetail1 = temp;
				} else if (1 == i) {
					AcAddrTypeDetail2 = temp;
				} else {
					AcAddrTypeDetail3 = temp;
				}
			}
			reqresplistaccount.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqresplistaccount.setAcAddrTypeDetail2(AcAddrTypeDetail2);
			reqresplistaccount.setAcAddrTypeDetail3(AcAddrTypeDetail3);
			reqresplistaccount.setReqInsertDate(reqDate);
			reqresplistaccount.setResInsertDate(new Date());
			reqresplistaccount.setRespResult(respListAccount.getResp().getResult());
			reqresplistaccount.setRespErrCode(respListAccount.getResp().getErrCode());
			List<RespListAccount.AccountList.Account> accounts = respListAccount.getAccountList().getAccount();
			String accountAccRefNumber = ConstantI.CONST_BLANK, accountAccType = ConstantI.CONST_BLANK, accountAeba = ConstantI.CONST_BLANK, accountIfsc = ConstantI.CONST_BLANK,
					accountMaskedAccnumber = ConstantI.CONST_BLANK, accountMbeba = ConstantI.CONST_BLANK, accountName = ConstantI.CONST_BLANK, credsAllowedDlength = ConstantI.CONST_BLANK,
					credsAllowedDtype = ConstantI.CONST_BLANK, credsAllowedSubType = ConstantI.CONST_BLANK, credsAllowedType = ConstantI.CONST_BLANK;
			for (int i = 0; i < accounts.size(); i++) {
				accountAccRefNumber += accounts.get(i).getAccRefNumber() + ConstantI.CONST_SPCL_PIPE;
				accountAccType += accounts.get(i).getAccType() + ConstantI.CONST_SPCL_PIPE;
				accountAeba += accounts.get(i).getAeba() + ConstantI.CONST_SPCL_PIPE;
				accountIfsc += accounts.get(i).getIfsc() + ConstantI.CONST_SPCL_PIPE;
				accountMaskedAccnumber += accounts.get(i).getMaskedAccnumber() + ConstantI.CONST_SPCL_PIPE;
				accountMbeba += accounts.get(i).getMbeba() + ConstantI.CONST_SPCL_PIPE;
				accountName += accounts.get(i).getName() + ConstantI.CONST_SPCL_PIPE;
				List<CredsAllowedType> credsAllowedList = accounts.get(i).getCredsAllowed();

				for (int j = 0; j < credsAllowedList.size(); j++) {
					credsAllowedDlength += credsAllowedList.get(j).getSubType().value() + ConstantI.CONST_SPCL_TILD;
					credsAllowedDtype += credsAllowedList.get(j).getSubType().value() + ConstantI.CONST_SPCL_TILD;
					credsAllowedSubType += credsAllowedList.get(j).getSubType().value() + ConstantI.CONST_SPCL_TILD;
					credsAllowedType += credsAllowedList.get(j).getSubType().value() + ConstantI.CONST_SPCL_TILD;
				}
				credsAllowedDlength += ConstantI.CONST_SPCL_PIPE;
				credsAllowedDtype += ConstantI.CONST_SPCL_PIPE;
				credsAllowedSubType += ConstantI.CONST_SPCL_PIPE;
				credsAllowedType += ConstantI.CONST_SPCL_PIPE;
			}
			reqresplistaccount.setAccountAccRefNumber(accountAccRefNumber);
			reqresplistaccount.setAccountAccType(accountAccType);
			reqresplistaccount.setAccountAeba(accountAeba);
			reqresplistaccount.setAccountIfsc(accountIfsc);
			reqresplistaccount.setAccountMaskedAccnumber(accountMaskedAccnumber);
			reqresplistaccount.setAccountMbeba(accountMbeba);
			reqresplistaccount.setAccountName(accountName);
			reqresplistaccount.setCredsAllowedDlength(credsAllowedDlength);
			reqresplistaccount.setCredsAllowedDtype(credsAllowedDtype);
			reqresplistaccount.setCredsAllowedSubType(credsAllowedSubType);
			reqresplistaccount.setCredsAllowedType(credsAllowedType);
			reqresplistaccount.setAckResp(JsonMan.getJSONStr(ack));
			reqresplistaccount.setPayerAddr(reqListAccount.getPayer().getAddr()); //Adding payer Address information 
			reqRespListAccRepo.save(reqresplistaccount);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	@Override
	public void updateResp(RespListAccount respListAccount) {
		try {
			ReqRespListAccount reqresplistaccount = getOnTxnId(respListAccount.getTxn().getId());
			if (reqresplistaccount == null) {
				log.warn("reqresplistaccount found for update RespListAccount txnId={}",respListAccount.getTxn().getId());
				return ;
			}
			reqresplistaccount.setRespHeadMsgId(respListAccount.getHead().getMsgId());
			reqresplistaccount.setRespHeadOrgId(respListAccount.getHead().getOrgId());
			reqresplistaccount.setRespHeadTs(respListAccount.getHead().getTs());
			reqresplistaccount.setResInsertDate(new Date());
			reqresplistaccount.setRespResult(respListAccount.getResp().getResult());
			reqresplistaccount.setRespErrCode(respListAccount.getResp().getErrCode());
			List<RespListAccount.AccountList.Account> accounts = respListAccount.getAccountList().getAccount();
			String accountAccRefNumber = ConstantI.CONST_BLANK, accountAccType = ConstantI.CONST_BLANK, accountAeba = ConstantI.CONST_BLANK, accountIfsc = ConstantI.CONST_BLANK,
					accountMaskedAccnumber = ConstantI.CONST_BLANK, accountMbeba = ConstantI.CONST_BLANK, accountName = ConstantI.CONST_BLANK, credsAllowedDlength = ConstantI.CONST_BLANK,
					credsAllowedDtype = ConstantI.CONST_BLANK, credsAllowedSubType = ConstantI.CONST_BLANK, credsAllowedType = ConstantI.CONST_BLANK;
			for (int i = 0; i < accounts.size(); i++) {
				log.info("Account Size= {}", accounts.size());
				accountAccRefNumber += accounts.get(i).getAccRefNumber() + ConstantI.CONST_SPCL_PIPE;
				accountAccType += accounts.get(i).getAccType() + ConstantI.CONST_SPCL_PIPE;
				accountAeba += accounts.get(i).getAeba() + ConstantI.CONST_SPCL_PIPE;
				accountIfsc += accounts.get(i).getIfsc() + ConstantI.CONST_SPCL_PIPE;
				accountMaskedAccnumber += accounts.get(i).getMaskedAccnumber() + ConstantI.CONST_SPCL_PIPE;
				accountMbeba += accounts.get(i).getMbeba() + ConstantI.CONST_SPCL_PIPE;
				accountName += accounts.get(i).getName() + ConstantI.CONST_SPCL_PIPE;
				List<CredsAllowedType> credsAllowedList = accounts.get(i).getCredsAllowed();
				for (int j = 0; j < credsAllowedList.size(); j++) {
					log.info("Creds Allow List Size= {}",credsAllowedList.size());
					log.info("SubType value= {}",credsAllowedList.get(i).getSubType().value());
					credsAllowedDlength += credsAllowedList.get(i).getSubType().value() + ConstantI.CONST_SPCL_TILD;
					credsAllowedDtype += credsAllowedList.get(i).getSubType().value() + ConstantI.CONST_SPCL_TILD;
					credsAllowedSubType += credsAllowedList.get(i).getSubType().value() + ConstantI.CONST_SPCL_TILD;
					credsAllowedType += credsAllowedList.get(i).getSubType().value() + ConstantI.CONST_SPCL_TILD;
				}
				credsAllowedDlength += ConstantI.CONST_SPCL_PIPE;
				credsAllowedDtype += ConstantI.CONST_SPCL_PIPE;
				credsAllowedSubType += ConstantI.CONST_SPCL_PIPE;
				credsAllowedType += ConstantI.CONST_SPCL_PIPE;
			}
			reqresplistaccount.setAccountAccRefNumber(accountAccRefNumber);
			reqresplistaccount.setAccountAccType(accountAccType);
			reqresplistaccount.setAccountAeba(accountAeba);
			reqresplistaccount.setAccountIfsc(accountIfsc);
			reqresplistaccount.setAccountMaskedAccnumber(accountMaskedAccnumber);
			reqresplistaccount.setAccountMbeba(accountMbeba);
			reqresplistaccount.setAccountName(accountName);
			reqresplistaccount.setCredsAllowedDlength(credsAllowedDlength);
			reqresplistaccount.setCredsAllowedDtype(credsAllowedDtype);
			reqresplistaccount.setCredsAllowedSubType(credsAllowedSubType);
			reqresplistaccount.setCredsAllowedType(credsAllowedType);
			reqRespListAccRepo.save(reqresplistaccount);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	
	}
	@Override
	public boolean checkRiskListAccReq(Long regId,Date lastlogindt) {
		try {
			Long count=riskXHDao.getXHCountByRegId(regId,lastlogindt);
			if(count>0) {
				return count < listAccCounter;
			}
			else {
				return true;
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	@Override
	public void insertReq(ReqListAccount reqListAccount, Ack ack) {
		try {
			ReqRespListAccount reqresplistaccount = new ReqRespListAccount();
			reqresplistaccount.setReqHeadMsgId(reqListAccount.getHead().getMsgId());
			reqresplistaccount.setReqHeadOrgId(reqListAccount.getHead().getOrgId());
			reqresplistaccount.setReqHeadTs(reqListAccount.getHead().getTs());

			reqresplistaccount.setTxnId(reqListAccount.getTxn().getId());
			reqresplistaccount.setTxnIdPrf(reqListAccount.getTxn().getId().substring(0, 3));
			reqresplistaccount.setTxnNote(reqListAccount.getTxn().getNote());
			reqresplistaccount.setTxnRefid(reqListAccount.getTxn().getRefId());
			reqresplistaccount.setTxnRefurl(reqListAccount.getTxn().getRefUrl());
			reqresplistaccount.setTxnTs(reqListAccount.getTxn().getTs());
			reqresplistaccount.setTxnType(reqListAccount.getTxn().getType().value());
			reqresplistaccount.setPayerAddr(reqListAccount.getPayer().getAddr());
			reqresplistaccount.setPayerCode(reqListAccount.getPayer().getCode());
			reqresplistaccount.setPayerSeqNum(reqListAccount.getPayer().getSeqNum());
			reqresplistaccount.setPayerType(reqListAccount.getPayer().getType().value());
			reqresplistaccount.setPayerHandal(
					reqListAccount.getPayer().getAddr().substring(reqListAccount.getPayer().getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));
			reqresplistaccount.setPayerName(reqListAccount.getPayer().getName());

			reqresplistaccount
					.setLink(reqListAccount.getLink().getType().value() + ConstantI.CONST_SPCL_EQUAL + reqListAccount.getLink().getValue());

			reqresplistaccount.setAcAddrType(reqListAccount.getPayer().getAc().getAddrType().value());
			List<Detail> acDetails = reqListAccount.getPayer().getAc().getDetail();

			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 = ConstantI.CONST_BLANK, AcAddrTypeDetail3 = ConstantI.CONST_BLANK;
			for (int i = 0; i < acDetails.size(); i++) {
				String temp = acDetails.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL + acDetails.get(i).getValue();
				if (0 == i) {
					acAddrTypeDetail1 = temp;
				} else if (1 == i) {
					AcAddrTypeDetail2 = temp;
				} else {
					AcAddrTypeDetail3 = temp;
				}
			}
			reqresplistaccount.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqresplistaccount.setAcAddrTypeDetail2(AcAddrTypeDetail2);
			reqresplistaccount.setAcAddrTypeDetail3(AcAddrTypeDetail3);
			reqresplistaccount.setReqInsertDate(new Date());
			reqresplistaccount.setAckReq(JsonMan.getJSONStr(ack));
			reqRespListAccRepo.save(reqresplistaccount);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	@Override
	public void insertUpdateRiskCount(Registration regDetails) {
		RiskXH riskDetails=null;
		try {
			riskDetails=riskXHDao.getXHDetailsByRegId(regDetails.getRegid().longValue());
			if(riskDetails!=null) {
				Date riskInsertDate=riskDetails.getCreateUpdDate();
				long diffMinutes = Util.getDateDiffInMinutes(regDetails.getLastlogindt(),riskInsertDate);
				if(diffMinutes< 0) {
					riskDetails.setCount(1);
					riskDetails.setCreateUpdDate(new Date());
				}
				else {
					riskDetails.setCount(riskDetails.getCount()+1);
				}
			}
			else {
				log.debug("Risk data not found for regid {} ",regDetails.getRegid());
				riskDetails=new RiskXH();
				riskDetails.setRegid(regDetails.getRegid().longValue());
				riskDetails.setCount(1);
				riskDetails.setMobNo(regDetails.getMobno());
				riskDetails.setCreateUpdDate(new Date());
			}
			riskXHDao.saveUpdateRiskDetails(riskDetails);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	private ReqRespListAccount getOnTxnId(String txnId) {
		// TODO test
		List<ReqRespListAccount> list = reqRespListAccRepo.findByTxnId(txnId);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 0) {
			log.warn(" ReqRespListAccountEntity not found for txnId={}", txnId);
		} else {
			log.warn("Found more than one  ReqRespListAccountEntity for txnId={}", txnId);
		}
		return null;
	}
}
