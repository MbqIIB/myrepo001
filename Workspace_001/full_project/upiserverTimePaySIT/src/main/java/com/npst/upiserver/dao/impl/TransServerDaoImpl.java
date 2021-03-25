package com.npst.upiserver.dao.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.TransServerDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.TransServerEntity;
import com.npst.upiserver.repo.TransServerRepo;
import com.npst.upiserver.repo.TransnoRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.Util;

@Component
public class TransServerDaoImpl implements TransServerDao {
	private static final Logger log = LoggerFactory.getLogger(TransServerDaoImpl.class);
	@Autowired
	private TransServerRepo transServerRepo;
	
	@Autowired
	TransnoRepository transNoRepo;

	private static boolean isEnable =ConstantI.YES.equalsIgnoreCase(Util.getProperty("IS_ENABLE_TRANS_SERVER"));

	private static final String defPurpose="00",defInMode="00";
	@Override
	public void insertTransServer(final ReqResp reqResp, String reqMsgId, String revType) {
		if (reqResp == null || !isEnable) {
			return;
		}
		try {
			TransServerEntity en = new TransServerEntity();
			// en.setAggregatorCode(aggregatorCode);
			en.setCbsRespCode(reqResp.getCbsErrorCode());
			en.setDcId(ConstantI.MOB);
			en.setInitiationMode(StringUtils.isBlank(reqResp.getInitiationMode())?defInMode:reqResp.getInitiationMode());
			en.setTxnPurpose(StringUtils.isBlank(reqResp.getTxnPurpose())?defPurpose:reqResp.getTxnPurpose());
			en.setInsertDate(new Date());
			if ((reqResp.getPayerIfsc() != null
					&& reqResp.getPayerIfsc().toUpperCase().startsWith(Constant.BANKIFSC_PREFIX)
					&& (reqResp.getTxnType().equalsIgnoreCase(ConstantI.CREDIT)
							|| (reqResp.getPayeeIfsc() != null
									? reqResp.getPayeeIfsc().toUpperCase().startsWith(Constant.BANKIFSC_PREFIX)
									: false)
							|| (reqResp.getPayeeMmid() != null
									? reqResp.getPayeeMmid().startsWith(Constant.BANKMMID_PREFIX)
									: false)
							|| (reqResp.getPayeeIin() != null
									? reqResp.getPayeeIin().startsWith(Constant.BANKIIN_PREFIX)
									: false)))
					|| reqResp.getTxnType().equalsIgnoreCase(ConstantI.ONUS)) {
				en.setIntraFlag(ConstantI.Y);
			} else {
				en.setIntraFlag(ConstantI.N);
			}
			if (reqResp.getTxnId().startsWith(Constant.bKPrf) || reqResp.getTxnType().equalsIgnoreCase("ONUS")) {
				en.setIssuerOrAcqFlag(ConstantI.A);
			} else {
				en.setIssuerOrAcqFlag(ConstantI.I);
			}

			// TODO discussion
			// en.setMerchantId(merchantId);
			en.setMsgId(reqMsgId);
			en.setNpciRespCode(reqResp.getRespCode());
			en.setBeneDetails(getBeneDetail(reqResp.getPayeeAddrType(), reqResp.getTxnType()));
			en.setPayeeAccount(reqResp.getPayeeAcNum());
			en.setPayeeCode(reqResp.getPayeeCode());

			en.setPayeeIfsc(reqResp.getPayeeIfsc());
			en.setPayeeName(reqResp.getPayeeName());

			if (reqResp.getPayeeAddr() != null) {
				en.setPayeePspCode(reqResp.getPayeeAddr()
						.substring(reqResp.getPayeeAddr().lastIndexOf(ConstantI.CONST_SPCL_AT_RATE)));
			}
			en.setPayeeVpa(reqResp.getPayeeAddr());
			en.setPayerAccount(reqResp.getPayerAcNum());
			en.setPayerCode(reqResp.getPayerCode());
			en.setPayerIfsc(reqResp.getPayerIfsc());
			en.setPayerMobileNo(reqResp.getPayerDeviceMobile());
			en.setPayerName(reqResp.getPayerName());
			if (reqResp.getPayerAddr() != null) {
				en.setPayerPspCode(reqResp.getPayerAddr()
						.substring(reqResp.getPayerAddr().lastIndexOf(ConstantI.CONST_SPCL_AT_RATE)));
			}
			en.setPayerVpa(reqResp.getPayerAddr());
			en.setRecordType(getRecordType(reqResp.getPayeeType(), reqResp.getPayeeAddrType(), reqResp.getTxnType()));

			if (reqResp.getTxnType() != null && reqResp.getTxnType().equalsIgnoreCase(ConstantI.DEBIT)) {
				en.setRemitterBankCode(Constant.BANKIFSC_PREFIX);
				if (ConstantI.Y.equals(en.getIntraFlag())) {
					en.setBeneBankCode(Constant.BANKIFSC_PREFIX);
				} else {
					// en.setBeneBankCode(Constant.BANKIFSC_PREFIX);
				}

			} else if (reqResp.getTxnType() != null && reqResp.getTxnType().equalsIgnoreCase(ConstantI.CREDIT)) {
				en.setBeneBankCode(Constant.BANKIFSC_PREFIX);
				if (ConstantI.Y.equals(en.getIntraFlag())) {
					en.setRemitterBankCode(Constant.BANKIFSC_PREFIX);
				} else {
					// en.setRemitterBankCode();
				}
			} else {

			}
			en.setRrn(reqResp.getRrn());
			en.setSplitTxnFlag(ConstantI.NO);
			en.setTxnId(reqResp.getTxnId());
			if (revType == null || revType.isEmpty()) {
				en.setTxnType(reqResp.getTxnType());
			} else {
				en.setTxnType(revType.toUpperCase() + ConstantI.REVERSAL);
			}
			en.setAmount(Util.convertAmountInPaisa(
					reqResp.getPayerAmount() == null ? reqResp.getPayeeAmount() : reqResp.getPayerAmount()));
			en.setOperation(reqResp.getOperation());
			en.setSubOperation(reqResp.getSubOperation());
			en.setIsoId(reqResp.getIsoId());
			en.setNarration(reqResp.getTxnNote());
			transServerRepo.save(en);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, TransServerDaoImpl.class);
		}

	}

	private String getRecordType(String payeeType, String payeeAddrType, String txnType) {
		String recordType = "";
		if (!ConstantI.REVERSAL.equalsIgnoreCase(txnType)) {
			if (payeeType != null && ConstantI.ENTITY.equalsIgnoreCase(payeeType)) {
				// P2M-Person to Merchant
				recordType = ConstantI.P2M;
			} else if (ConstantI.ACCOUNT.equalsIgnoreCase(payeeAddrType)) {
				// P2A-Person to Account
				recordType = ConstantI.P2A;
			} else if (ConstantI.MOBILE.equalsIgnoreCase(payeeAddrType)) {
				// P2P-Person to Person
				recordType = ConstantI.P2P;
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(payeeAddrType)) {
				// P2U-Person to Aadhaar
				recordType = ConstantI.P2U;
			} else {
				// P2V-Person to Virtual
				recordType = ConstantI.P2V;
			}
		}
		return recordType;

	}

	private String getBeneDetail(String payeeAddrType, String txnType) {
		String recordType = "";
		if (!ConstantI.REVERSAL.equalsIgnoreCase(txnType)) {
			if (ConstantI.ACCOUNT.equalsIgnoreCase(payeeAddrType)) {
				// For P2A: Account Number – IFSC Code PAYEE_ACCOUNT_DETAILS
				recordType = ConstantI.P2A;
			} else if (ConstantI.MOBILE.equalsIgnoreCase(payeeAddrType)) {
				// For P2P: Mobile Number– MMID
				recordType = ConstantI.P2P;
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(payeeAddrType)) {
				// For P2U: Aadhaar Number – IIN
				recordType = ConstantI.P2U;
			} else {
				// For Virtual Address: Virtual Address
				recordType = ConstantI.P2V;
			}
		}
		return recordType;

	}
}