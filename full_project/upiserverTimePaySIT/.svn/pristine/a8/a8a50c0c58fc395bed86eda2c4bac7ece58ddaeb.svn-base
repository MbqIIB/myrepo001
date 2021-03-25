package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.OrphanDebitCreditRevDao;
import com.npst.upiserver.dao.ReqRespDebitCreditDao;
import com.npst.upiserver.dao.ReqRespDebitCreditPayeesDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.FirInfo;
import com.npst.upiserver.entity.OrphanDebitCredit;
import com.npst.upiserver.entity.ReqRespDebitCredit;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqChkTxn;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.repo.FirInfoRepository;
import com.npst.upiserver.repo.OrphanDebitCreditRepository;
import com.npst.upiserver.repo.ReqRespDebitCreditRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespDebitCreditDaoImpl implements ReqRespDebitCreditDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespDebitCreditDaoImpl.class);

	@Autowired
	ReqRespDebitCreditRepository reqRespDebitCreditRepo;
	
	/*@Autowired
	PayeesDaoImpl payeesDaoImpl;*/
	
	@Autowired
	OrphanDebitCreditRepository orphanDebitCreditRepo;
	@Autowired
	FirInfoRepository firInfoRepository;
	
	@Autowired
	private OrphanDebitCreditRevDao orphanDebitCreditRevDao;
	
	@Autowired
	private ReqRespDebitCreditPayeesDao reqRespDebitCreditPayeesDao;

	@Override
	public ReqRespDebitCredit getOnTxnIdAndTxnType(ReqChkTxn reqChkTxn) {
		log.info("this is here ");
		ReqRespDebitCredit reqrespdebitcredit = null;
		try {
			log.info("org txn id: {}",reqChkTxn.getTxn().getOrgTxnId());
			log.info("org msg id: {}",reqChkTxn.getTxn().getOrgMsgId());
			reqrespdebitcredit = reqRespDebitCreditRepo.findByTxnIdAndReqHeadMsgId(reqChkTxn.getTxn().getOrgTxnId(),
					reqChkTxn.getTxn().getOrgMsgId());
			if (reqrespdebitcredit != null) {
				reqrespdebitcredit.setChkTxnCount(reqrespdebitcredit.getChkTxnCount() + 1);
				reqRespDebitCreditRepo.save(reqrespdebitcredit);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return reqrespdebitcredit;
	}

	@Override
	public void insertReqResp(ReqPay reqPay, RespPay respPay, Ack ack, Date reqDate, Date respDate, String revType,
			String cbsErrorCode, String cbsrrn) {
		try {
			
			log.info("TXN Type {} ",reqPay.getTxn().getType());
			if (!PayConstant.REVERSAL.equals(reqPay.getTxn().getType())) {
				ReqRespDebitCredit reqrespdebitcredit = new ReqRespDebitCredit();
				reqrespdebitcredit.setReqInsert(reqDate);
				reqrespdebitcredit.setRespInsert(respDate);
				reqrespdebitcredit.setCbsRRN(cbsrrn);
				reqrespdebitcredit.setReqHeadMsgId(reqPay.getHead().getMsgId());
				reqrespdebitcredit.setReqHeadOrgId(reqPay.getHead().getOrgId());
				reqrespdebitcredit.setReqHeadTs(reqPay.getHead().getTs());

				reqrespdebitcredit.setRespHeadMsgId(respPay.getHead().getMsgId());
				reqrespdebitcredit.setRespHeadOrgId(respPay.getHead().getOrgId());
				reqrespdebitcredit.setRespHeadTs(respPay.getHead().getTs());

				reqrespdebitcredit.setTxnCustRef(reqPay.getTxn().getCustRef());
				reqrespdebitcredit.setTxnId(reqPay.getTxn().getId());
				reqrespdebitcredit.setTxnIdPrf(reqPay.getTxn().getId().substring(0, 3));
				reqrespdebitcredit.setTxnNote(reqPay.getTxn().getNote());
				reqrespdebitcredit.setTxnRefid(reqPay.getTxn().getRefId());
				reqrespdebitcredit.setTxnRefurl(reqPay.getTxn().getRefUrl());
				reqrespdebitcredit.setTxnTs(reqPay.getTxn().getTs());
				reqrespdebitcredit.setTxnType(reqPay.getTxn().getType().value());

				reqrespdebitcredit.setPayerAddr(reqPay.getPayer().getAddr());
				reqrespdebitcredit.setPayerCode(reqPay.getPayer().getCode());
				reqrespdebitcredit.setPayerSeqNum(reqPay.getPayer().getSeqNum());
				reqrespdebitcredit.setPayerType(reqPay.getPayer().getType().value());
				reqrespdebitcredit.setPayerName(reqPay.getPayer().getName());
				reqrespdebitcredit.setTxnInitiationMode(reqPay.getTxn().getInitiationMode());
				reqrespdebitcredit.setTxnPurpose(reqPay.getTxn().getPurpose());
				reqrespdebitcredit.setPayerHandal(
						reqPay.getPayer().getAddr().substring(reqPay.getPayer().getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));

				reqrespdebitcredit.setAmountCrr(reqPay.getPayer().getAmount().getCurr());
				reqrespdebitcredit.setAmountVal(reqPay.getPayer().getAmount().getValue());

				if (null != reqPay.getPayer().getDevice()) {
					List<Tag> deviceTag = reqPay.getPayer().getDevice().getTag();

					for (int i = 0; i < deviceTag.size(); i++) {
						if (DeviceTagNameType.APP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceApp(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.CAPABILITY.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceCapability(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.GEOCODE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceGeocode(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.ID.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceId(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.IP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceIp(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.LOCATION.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceLocation(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.MOBILE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceMobile(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.OS.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceOs(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.TYPE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespdebitcredit.setDeviceType(deviceTag.get(i).getValue());
						}
					}
				}

				List<Detail> acDetails = reqPay.getPayer().getAc().getDetail();
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
				reqrespdebitcredit.setAcAddrType(reqPay.getPayer().getAc().getAddrType().value());
				reqrespdebitcredit.setAcAddrTypeDetail1(acAddrTypeDetail1);
				reqrespdebitcredit.setAcAddrTypeDetail2(AcAddrTypeDetail2);
				reqrespdebitcredit.setAcAddrTypeDetail3(AcAddrTypeDetail3);
				if (null != reqPay.getPayer().getCreds()) {
					// check the null condtion for creds , dated : 13-06-2017,
					// Modified by :Abhishek Bhardwaj
					List<CredsType.Cred> creadsList = reqPay.getPayer().getCreds().getCred();
					String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
					for (int i = 0; i < creadsList.size(); i++) {
						creadsSubType.concat(creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE);
						creadsType.concat(creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE);
					}
					reqrespdebitcredit.setCredSubType(creadsSubType);
					reqrespdebitcredit.setCredType(creadsType);
				} // End check the null condtion for creds , dated : 13-06-2017,
					// Modified by : Abhishek Bhardwaj

				// reqrespdebitcredit.setPayeeses(payeeSet);
				reqrespdebitcredit.setRespErrCode(respPay.getResp().getErrCode());
				reqrespdebitcredit.setRespResult(respPay.getResp().getResult().value());
				List<Ref> refList = respPay.getResp().getRef();
				String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK, refAddr = ConstantI.CONST_BLANK, refRegName = ConstantI.CONST_BLANK, refSettAmount = ConstantI.CONST_BLANK,
						refReversalRespCode = ConstantI.CONST_BLANK, refOrgAmount = ConstantI.CONST_BLANK, refSettCurrency = ConstantI.CONST_BLANK, refAcNum = ConstantI.CONST_BLANK,
						refApprovalNum = ConstantI.CONST_BLANK, refRespCode = ConstantI.CONST_BLANK;

				if(refList!=null) {
					for (Ref ref : refList) {
						refType += ref.getType() + ConstantI.CONST_SPCL_PIPE;
						refSeqNum += ref.getSeqNum() + ConstantI.CONST_SPCL_PIPE;
						refAddr += ref.getAddr() + ConstantI.CONST_SPCL_PIPE;
						refRegName += ref.getRegName() + ConstantI.CONST_SPCL_PIPE;
						refSettAmount += ref.getSettAmount() + ConstantI.CONST_SPCL_PIPE;
						refSettCurrency += ref.getSettCurrency() + ConstantI.CONST_SPCL_PIPE;
						// refAcNum+=ref.geta+"|";
						refApprovalNum += ref.getApprovalNum() + ConstantI.CONST_SPCL_PIPE;
						refRespCode += ref.getRespCode() + ConstantI.CONST_SPCL_PIPE;
						refReversalRespCode += ref.getReversalRespCode() + ConstantI.CONST_SPCL_PIPE;
						refOrgAmount += ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE;
					}
					reqrespdebitcredit.setRefType(refType);
					reqrespdebitcredit.setRefSeqNum(refSeqNum);
					reqrespdebitcredit.setRefAddr(refAddr);
					reqrespdebitcredit.setRefRegName(refRegName);
					reqrespdebitcredit.setRefSettAmount(refSettAmount);
					reqrespdebitcredit.setRefSettCurrency(refSettCurrency);
					reqrespdebitcredit.setRefAcNum(refAcNum);
					reqrespdebitcredit.setRefApprovalNum(refApprovalNum);
					reqrespdebitcredit.setRefRespCode(refRespCode);
					reqrespdebitcredit.setRefReversalRespCode(refReversalRespCode);
					reqrespdebitcredit.setRefOrgAmount(refOrgAmount);
				}
				reqrespdebitcredit.setCbserrorCode(cbsErrorCode);
				reqrespdebitcredit.setMberrorCode(ConstantI.CONST_BLANK);
				reqrespdebitcredit.setChkTxnCount(0);
				try {
					if(ack!=null) {
						reqrespdebitcredit.setAckDebitCredit(JsonMan.getJSONStr(ack));
					}
				} catch (Exception ee) {
					log.error(ee.getMessage(),ee); 
				}
				log.info("Saving DebitCreditReq");
				reqRespDebitCreditRepo.save(reqrespdebitcredit);
				log.info("Saved DebitCreditReq");
				reqRespDebitCreditPayeesDao.insertPayees(reqPay.getPayees(), reqPay.getTxn().getId(),
						reqPay.getHead().getMsgId(), reqrespdebitcredit.getIdReqRespDebitCredit().longValue());
				
			} else {
				ReqRespDebitCredit reqrespdebitcredit = reqRespDebitCreditRepo.findByTxnIdAndTxnType(reqPay.getTxn().getOrgTxnId(),revType);
				if (null != reqrespdebitcredit) {
					reqrespdebitcredit.setRevCBSrrn(cbsrrn);
					reqrespdebitcredit.setRev(1);
					reqrespdebitcredit.setRevHeadMsgId(reqPay.getHead().getMsgId());
					reqrespdebitcredit.setRevTxnId(reqPay.getTxn().getId());
					reqrespdebitcredit.setRevRespResult(respPay.getResp().getResult().value());
					reqrespdebitcredit.setRevRespErrCode(respPay.getResp().getErrCode());
					List<Ref> refList = respPay.getResp().getRef();
					String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK, refAddr = ConstantI.CONST_BLANK, refRegName = ConstantI.CONST_BLANK, refSettAmount = ConstantI.CONST_BLANK,
							refReversalRespCode = ConstantI.CONST_BLANK, refOrgAmount = ConstantI.CONST_BLANK, refSettCurrency = ConstantI.CONST_BLANK, refAcNum = ConstantI.CONST_BLANK,
							refApprovalNum = ConstantI.CONST_BLANK, refRespCode = ConstantI.CONST_BLANK;

					for (Ref ref : refList) {
						refType += ref.getType() + ConstantI.CONST_SPCL_PIPE;
						refSeqNum += ref.getSeqNum() + ConstantI.CONST_SPCL_PIPE;
						refAddr += ref.getAddr() + ConstantI.CONST_SPCL_PIPE;
						refRegName += ref.getRegName() + ConstantI.CONST_SPCL_PIPE;
						refSettAmount += ref.getSettAmount() + ConstantI.CONST_SPCL_PIPE;
						refSettCurrency += ref.getSettCurrency() + ConstantI.CONST_SPCL_PIPE;
						// refAcNum+=ref.geta+"|";
						refApprovalNum += ref.getApprovalNum() + ConstantI.CONST_SPCL_PIPE;
						refRespCode += ref.getRespCode() + ConstantI.CONST_SPCL_PIPE;
						refReversalRespCode += ref.getReversalRespCode() + ConstantI.CONST_SPCL_PIPE;
						refOrgAmount += ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE;
					}
					reqrespdebitcredit.setRevRefType(refType);
					reqrespdebitcredit.setRevRefSeqNum(refSeqNum);
					reqrespdebitcredit.setRevRefAddr(refAddr);
					reqrespdebitcredit.setRevRefRegName(refRegName);
					reqrespdebitcredit.setRevRefSettAmount(refSettAmount);
					reqrespdebitcredit.setRevRefSettCurrency(refSettCurrency);
					reqrespdebitcredit.setRevRefAcNum(refAcNum);
					reqrespdebitcredit.setRevRefApprovalNum(refApprovalNum);
					reqrespdebitcredit.setRevRefRespCode(refRespCode);
					reqrespdebitcredit.setRevRefReversalRespCode(refReversalRespCode);
					reqrespdebitcredit.setRevRefOrgAmount(refOrgAmount);
					reqrespdebitcredit.setRevCbserrorCode(cbsErrorCode);
					reqrespdebitcredit.setRevMberrorCode(ConstantI.CONST_BLANK);
					reqrespdebitcredit.setRevReqInsert(reqDate);
					reqrespdebitcredit.setRevRespInsert(respDate);
					// if (null != ack.getErr() || null !=
					// ack.getErrorMessages()) {
					try {
						reqrespdebitcredit.setAckRev(JsonMan.getJSONStr(ack));
					} catch (Exception ee) {
						log.error(ee.getMessage(),ee);
					}
					reqRespDebitCreditRepo.save(reqrespdebitcredit);
				} else {

					log.info("Going to set the oprhan debit credit");
					OrphanDebitCredit orphanDebitCredit = new OrphanDebitCredit();
					orphanDebitCredit.setTxnType(revType);
					orphanDebitCredit.setOrgTxnId(reqPay.getTxn().getOrgTxnId());
					orphanDebitCredit.setRevCBSrrn(cbsrrn);
					orphanDebitCredit.setRevHeadMsgId(reqPay.getHead().getMsgId());
					orphanDebitCredit.setRevTxnId(reqPay.getTxn().getId());
					orphanDebitCredit.setRevRespResult(respPay.getResp().getResult().value());
					orphanDebitCredit.setRevRespErrCode(respPay.getResp().getErrCode());
					List<Ref> refList = respPay.getResp().getRef();
					String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK, refAddr = ConstantI.CONST_BLANK, refRegName = ConstantI.CONST_BLANK, refSettAmount = ConstantI.CONST_BLANK,
							refReversalRespCode = ConstantI.CONST_BLANK, refOrgAmount = ConstantI.CONST_BLANK, refSettCurrency = ConstantI.CONST_BLANK, refAcNum = ConstantI.CONST_BLANK,
							refApprovalNum = ConstantI.CONST_BLANK, refRespCode = ConstantI.CONST_BLANK;
					for (Ref ref : refList) {
						refType += ref.getType() + ConstantI.CONST_SPCL_PIPE;
						refSeqNum += ref.getSeqNum() + ConstantI.CONST_SPCL_PIPE;
						refAddr += ref.getAddr() + ConstantI.CONST_SPCL_PIPE;
						refRegName += ref.getRegName() + ConstantI.CONST_SPCL_PIPE;
						refSettAmount += ref.getSettAmount() + ConstantI.CONST_SPCL_PIPE;
						refSettCurrency += ref.getSettCurrency() + ConstantI.CONST_SPCL_PIPE;
						// refAcNum+=ref.geta+"|";
						refApprovalNum += ref.getApprovalNum() + ConstantI.CONST_SPCL_PIPE;
						refRespCode += ref.getRespCode() + ConstantI.CONST_SPCL_PIPE;
						refReversalRespCode += ref.getReversalRespCode() + ConstantI.CONST_SPCL_PIPE;
						refOrgAmount += ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE;
					}
					orphanDebitCredit.setRevRefType(refType);
					orphanDebitCredit.setRevRefSeqNum(refSeqNum);
					orphanDebitCredit.setRevRefAddr(refAddr);
					orphanDebitCredit.setRevRefRegName(refRegName);
					orphanDebitCredit.setRevRefSettAmount(refSettAmount);
					orphanDebitCredit.setRevRefSettCurrency(refSettCurrency);
					orphanDebitCredit.setRevRefAcNum(refAcNum);
					orphanDebitCredit.setRevRefApprovalNum(refApprovalNum);
					orphanDebitCredit.setRevRefRespCode(refRespCode);
					orphanDebitCredit.setRevRefReversalRespCode(refReversalRespCode);
					orphanDebitCredit.setRevRefOrgAmount(refOrgAmount);
					orphanDebitCredit.setRevCbserrorCode(cbsErrorCode);
					orphanDebitCredit.setRevMberrorCode(ConstantI.CONST_BLANK);
					orphanDebitCredit.setRevReqInsert(reqDate);
					orphanDebitCredit.setRevRespInsert(respDate);
					try {
						orphanDebitCredit.setAckRev(JsonMan.getJSONStr(ack));
					} catch (Exception ee) {
						log.error(ee.getMessage(),ee);
					}
					orphanDebitCreditRepo.save(orphanDebitCredit);
					log.info("Ending oprhan debit credit");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	
	@Override
	public String insertReq(FirInfo firinfo) {
		log.info("Saving INITIATION FIR MODE request");
		FirInfo firInfo=firInfoRepository.save(firinfo);
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("FIR").append("/").append(firInfo.getIdentityType()).append("-").append(firInfo.getIdentityValue())
		.append("/").append(firInfo.getPayerName()).append("/").append(firInfo.getInstitutionType()).append("/").append(firInfo.getInstitutionRoute())
		.append("/").append(firInfo.getTxnCustRef()).append("/").append(firInfo.getAddressLocation()).append("-").append(firInfo.getAddressCity())
		.append("-").append(firInfo.getAddressCountry()).append("/").append(firInfo.getAddressGeocode());
		log.info("Saved INITIATION FIR MODE request");
		return strBuilder.toString();
		
	}

	@Override
	public void updatePreApprovedReversal(ReqResp reqResp, RespPay respPay, Date reqDate, Date respDate) {
		try {
			ReqRespDebitCredit reqrespdebitcredit = getOnTxnIdAndTxnType(reqResp.getOrgTxnId(),reqResp.getOrgTxnType());
			if (null != reqrespdebitcredit) {
				reqrespdebitcredit.setRev(1);
				reqrespdebitcredit.setRevTxnId(reqResp.getTxnId());
				reqrespdebitcredit.setRevRespResult(
						ConstantI.CODE_SUCCESS.equals(reqResp.getRespCode()) ? ConstantI.SUCCESS : ConstantI.FAILURE);
				reqrespdebitcredit.setRevRespErrCode(reqResp.getRespCode());
				reqrespdebitcredit.setOrgRespCode(respPay.getTxn().getOrgRespCode());
				reqrespdebitcredit.setRevCbserrorCode(reqResp.getCbsErrorCode());
				List<Ref> refList = respPay.getResp().getRef();
				String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK,
						refAddr = ConstantI.CONST_BLANK, refRegName = ConstantI.CONST_BLANK,
						refSettAmount = ConstantI.CONST_BLANK, refOrgAmount = ConstantI.CONST_BLANK,
						refSettCurrency = ConstantI.CONST_BLANK, refAcNum = ConstantI.CONST_BLANK,
						refApprovalNum = ConstantI.CONST_BLANK, refRespCode = ConstantI.CONST_BLANK;

				for (Ref ref : refList) {
					refSettAmount += ref.getSettAmount() != null ? ref.getSettAmount() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
					refApprovalNum += ref.getApprovalNum() != null ? ref.getApprovalNum() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
					refRespCode += ref.getRespCode() != null ? ref.getRespCode() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
					refOrgAmount += ref.getOrgAmount() != null ? ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
					refRegName += ref.getRegName() != null ? ref.getRegName() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
					refAddr += ref.getAddr() != null ? ref.getAddr() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
					refSeqNum += ref.getSeqNum() != null ? ref.getSeqNum() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
					refType += ref.getType() != null ? ref.getType() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
					refSettCurrency += ref.getSettCurrency() != null ? ref.getSettCurrency() + ConstantI.CONST_SPCL_PIPE
							: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				}

				reqrespdebitcredit.setRevRefType(refType);
				reqrespdebitcredit.setRevRefSeqNum(refSeqNum);
				reqrespdebitcredit.setRevRefAddr(refAddr);
				reqrespdebitcredit.setRevRefRegName(refRegName);
				reqrespdebitcredit.setRevRefSettAmount(refSettAmount);
				reqrespdebitcredit.setRevRefSettCurrency(refSettCurrency);
				reqrespdebitcredit.setRevRefAcNum(refAcNum);
				reqrespdebitcredit.setRevRefApprovalNum(refApprovalNum);
				reqrespdebitcredit.setRevRefRespCode(refRespCode);
				reqrespdebitcredit.setRevRefOrgAmount(refOrgAmount);
				reqrespdebitcredit.setRevReqInsert(reqDate);
				reqrespdebitcredit.setRevRespInsert(respDate);
				reqRespDebitCreditRepo.save(reqrespdebitcredit);
			} else {
				orphanDebitCreditRevDao.insertRevPreApproved(reqResp, respPay, reqDate, respDate);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespDebitCreditDaoImpl.class);
		}
		
	}
	
	private ReqRespDebitCredit getOnTxnIdAndTxnType(String orgTxnId, String revType) {
		try {
			return reqRespDebitCreditRepo.findByTxnIdAndTxnType(orgTxnId, revType);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespDebitCreditDaoImpl.class);
		}
		return null;
	}

	@Override
	public void insertOnusAndPreApproed(ReqResp reqResp, Date reqDate, Date respDate) {
		try {
			ReqRespDebitCredit reqrespdebitcredit = new ReqRespDebitCredit();
			reqrespdebitcredit.setReqInsert(reqDate);
			reqrespdebitcredit.setRespInsert(respDate);
			reqrespdebitcredit.setReqHeadMsgId(reqResp.getTxnId());
			reqrespdebitcredit.setRespHeadMsgId(reqResp.getTxnId());
			reqrespdebitcredit.setTxnCustRef(reqResp.getRrn());
			reqrespdebitcredit.setTxnId(reqResp.getTxnId());
			reqrespdebitcredit.setTxnIdPrf(reqResp.getTxnId().substring(0, 3));
			reqrespdebitcredit.setTxnType(reqResp.getTxnType());

			reqrespdebitcredit.setDeviceApp(reqResp.getPayerDeviceAppId());
			reqrespdebitcredit.setDeviceCapability(reqResp.getPayerDeviceCapability());
			reqrespdebitcredit.setDeviceId(reqResp.getPayerDeviceId());
			reqrespdebitcredit.setDeviceGeocode(reqResp.getPayerDeviceGeoCode());
			reqrespdebitcredit.setDeviceIp(reqResp.getPayerDeviceIp());
			reqrespdebitcredit.setDeviceLocation(reqResp.getPayerDeviceLocation());
			reqrespdebitcredit.setDeviceMobile(reqResp.getPayerDeviceMobile());
			reqrespdebitcredit.setDeviceOs(reqResp.getPayerDeviceOsType());
			reqrespdebitcredit.setDeviceType(reqResp.getPayerDeviceType());
			reqrespdebitcredit.setPayerName(reqResp.getPayerName());
			reqrespdebitcredit.setPayerAddr(reqResp.getPayerAddr());
			reqrespdebitcredit.setAmountVal(reqResp.getPayeeAmount());
			reqrespdebitcredit.setAcAddrTypeDetail1(AccountDetailType.IFSC.toString() + ConstantI.CONST_SPCL_PIPE
					+ reqResp.getPayerIfsc().toUpperCase());
			reqrespdebitcredit.setAcAddrTypeDetail2(
					AccountDetailType.ACTYPE.toString() + ConstantI.CONST_SPCL_PIPE + reqResp.getPayerAcType());
			reqrespdebitcredit.setAcAddrTypeDetail3(
					AccountDetailType.ACNUM.toString() + ConstantI.CONST_SPCL_PIPE + reqResp.getPayerAcNum());
			reqrespdebitcredit.setRespErrCode(reqResp.getRespCode());
			reqrespdebitcredit.setCbserrorCode(reqResp.getCbsErrorCode());
			if (ConstantI.CODE_SUCCESS.equals(reqResp.getRespCode())) {
				reqrespdebitcredit.setRespResult(ConstantI.SUCCESS);
			} else {
				reqrespdebitcredit.setRespResult(ConstantI.FAILURE);
			}
			reqRespDebitCreditRepo.save(reqrespdebitcredit);
			reqRespDebitCreditPayeesDao.insertPayees(reqResp, reqResp.getTxnId(), reqResp.getTxnId());
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespDebitCreditDaoImpl.class);
		}
		
	}

}
