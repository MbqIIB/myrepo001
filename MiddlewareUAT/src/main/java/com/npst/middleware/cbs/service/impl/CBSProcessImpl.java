/*package com.npst.middleware.cbs.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.NACChannel;
import org.jpos.util.LogSource;
import org.jpos.util.SimpleLogListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.cbs.service.CBSConSocket;
import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.dao.TransRepository;
import com.npst.middleware.entity.Trans;
import com.npst.middleware.iso.CBSPackager;
import com.npst.middleware.iso.ISO93APackager;
import com.npst.middleware.iso.ISOUtility;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.obj.ReqResp.Account;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.ISOMsgConstant;
import com.npst.middleware.util.Util;

@Service
public class CBSProcessImpl implements CBSProcess {
	private static final Logger LOG = LoggerFactory.getLogger(CBSProcessImpl.class);
	private static final Integer FETCH_IFSC_COUNT=Integer.parseInt(Util.getProperty("FETCH_IFSC_COUNT"));
	
	private static final String REM_OTHER_ACC_POOL=Util.getProperty("POOL_ACCOUNT_REM");
	private static final String BEN_OTHER_ACC_POOL=Util.getProperty("POOL_ACCOUNT_BEN");
	
	
	private static final String REM_SOPAY_ACC_POOL=Util.getProperty("REM_SOPAY_ACC_POOL");
	private static final String REM_TIMEPAY_ACC_POOL=Util.getProperty("REM_TIMEPAY_ACC_POOL");
	
	
	private static final String BEN_SOPAY_ACC_POOL=Util.getProperty("BEN_SOPAY_ACC_POOL");
	private static final String BEN_TIMEPAY_ACC_POOL=Util.getProperty("BEN_TIMEPAY_ACC_POOL");
	
	public static int ifcscounnt = 0;

	private static String maskedAccountNumber(String accountNumber) { // Needs
		// to
		// fix
		// masking
		LOG.info("Masking method for account number {}:", accountNumber);
		int total = accountNumber.length();
		int startlen = 0, endlen = 5;
		int masklen = total - (startlen + endlen);
		StringBuffer maskedbuf = new StringBuffer(accountNumber.substring(0, startlen));
		for (int i = 0; i < masklen; i++) {
			maskedbuf.append('X');
		}
		maskedbuf.append(accountNumber.substring(startlen + masklen, total));
		String masked = maskedbuf.toString();
		long endtime = System.currentTimeMillis();
		LOG.info("Masked Account number:{}  of : {} size ", masked, masked.length());
		return masked;
	}

	@Autowired
	CBSConSocket cbsConSocket;

	@Autowired
	TransRepository transRepository;

	@Override
	public ReqResp balEnq(final ReqResp reqResp) {
		try {
			String branchCode = reqResp.getPayerAcNum().substring(0, 3);
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			m.setHeader(ISOMsgConstant.MSG_HEADER.getBytes());
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.set(2, ISOMsgConstant.DE_2);
			m.set(3, ISOMsgConstant.BAL_ENQ_PROCESSING_CODE);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(new Date()));
			m.set(17, ISOUtility.getTransactionDate8(new Date()));
			m.set(24, ISOMsgConstant.DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(reqResp.getPayerAcNum(), 17, ' ')); // 16-11-17
			m.set(123, ISOMsgConstant.DE_123);
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			m.set(127,reqResp.getPayerDeviceMobile()); //Added 6th-July-2018 

			LOG.info("Going for Connecting with the CBS Socket Connections at {} with request as {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.info("After back from cbs con socket at {} with response as {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				BigInteger ledgBal = new BigInteger(ISOMsgConstant.BAL_ZERO);
				BigInteger ffdBal = new BigInteger(ISOMsgConstant.BAL_ZERO);
				reqResp.setRespCode(cbsResCode);
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					String balance = "1001356C0000000000001001356C000000000000";
					
					try {
						String balanceString = isoMsgRes.getString(48);
						if (balanceString != null) {
				
							ledgBal = new BigInteger(balanceString.substring(17,34));
							String f=balanceString.substring(17,34).startsWith("+")?"C000"+balanceString.substring(18,34):"D000"+balanceString.substring(18,34);
							LOG.info("first Bal="+f);
							ffdBal = new BigInteger(balanceString.substring(51,68));
							BigInteger sumOfledgAndffdBal = ledgBal.add(ffdBal);
							String s = sumOfledgAndffdBal.toString();
							LOG.info("sumOfledgAndffdBal="+s);
							if ("1".equalsIgnoreCase(reqResp.getIsUPI2())) {
								s=addPrefixW20(s);
								LOG.info("addPrefixW20 return="+s);
								balance=s+f;//f+s
								balance = balance.replaceAll("C0000000", "1001356C");
								if(balance.contains("D")){
									balance = balance.replaceAll("D0000000", "1001356D");
								}
								LOG.info("two balance is ="+balance);
							}else {
								balance=s;
								balance = Util.convertPaisaInAmount(balance)+ISOMsgConstant.FUND_TRANSFER_DE_49;
							}
							
						}
					} catch (Exception e) {
						LOG.error("errorB: {}", e);
					}
					LOG.info("final balance="+balance);
					reqResp.setRespBal(balance);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
		} catch (Exception e) {
			LOG.error("errorFinal :{}", e);
		}
		LOG.info("Response from balEnq method is as {} ", reqResp.getRespCode());
		return reqResp;
	}

	private static String addPrefixW20(String s) {
		int l=s.length();
		int act=19-l;
		StringBuilder sb=new StringBuilder("C");
		for(int i=0;i<act;i++){
			sb.append("0");
		}
		sb.append(s);
		return sb.toString();
	}

	@Override
	public ReqResp creditAadhar(ReqResp reqResp) {
		String amount = reqResp.getPayeeAmount();
		String customerAadharNumber = reqResp.getPayeeUidNum();
		String poolAccount = Util.getProperty("POOL_ACCOUNT_BEN");
		try {
			amount = Util.convertAmountInPaisa(amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI("1200");
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, "200");
			m.set(32, ISOMsgConstant.DE_32);
			m.set(33, ISOMsgConstant.DE_33);
			// m.set(37, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(43, Util.getProperty("BANK_NAME"));
			m.set(49, ISOMsgConstant.DE_49);
			m.set(46, "0000000000000000");
			m.set(102, poolAccount);
			m.set(103, customerAadharNumber);
			m.set(123, "UPI");
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			Trans trans = new Trans();
			try {
				// trans.setAccNo(customerAadharNumber);
				// trans.setIfsc(reqResp.getPayeeIfsc());
				// trans.setOpration("CREDIT");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setAmount(Long.parseLong(amount)); // Paise saved into
				// the
				// // DB
				// trans.setTxnTime(txnTime);
				// trans.setRefId(reqResp.getTxnRefId()); // 16-11-17
				// // trans.setRefId(reqResp.getRefId());
				// trans.setRefURL(reqResp.getTxnRefUrl()); // 16-11-17
				// // trans.setRefURL(reqResp.getRefURL());
				// trans.setTxnNote(reqResp.getTxnNote());
				// trans.setMobileNumber(reqResp.getPayeeDeviceMobile());// TODO
				// PK
				// trans.setPoolAccount(poolAccount); // POOL ACCOUNT
				// transRepository.save(trans);

				trans.setAccNo(customerAadharNumber);
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDIT");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccount); // POOL ACCOUNT
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.info("Going for Connecting with the CBS Socket Connections at {} with {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Return success from cbsConSocket with {} at {} ", isoMsgRes, new Date());
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					if (ISOMsgConstant.ERROR_CODE_CBS_119.equalsIgnoreCase(cbsResCode)) {
						String reasonText = isoMsgRes.getString(127);
						if (null != reasonText
								&& reasonText.toUpperCase().contains(ISOMsgConstant.ACCOUNT_TYPE_DORMANT)) {
							reqResp.setRespCode("D" + cbsResCode);
						} else {
							reqResp.setRespCode("F" + cbsResCode);
						}
					}
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.info("Ending credit AADHAR method as {} ", reqResp);
		return reqResp;

	}

	@Override
	public ReqResp creditAccount(final ReqResp reqResp) {
		String amount = reqResp.getPayeeAmount();
		try {
			
			String poolAccountNo = null;
			String poolBranchCode = null;
			
			amount = Util.convertAmountInPaisa(amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			String customerAccountNumber = reqResp.getPayeeAcNum();
			String branchCode = reqResp.getPayeeAcNum().substring(0, 3);
			String poolAccountNo = Util.getProperty("POOL_ACCOUNT_BEN");
			String poolBranchCode = poolAccountNo.substring(0, 3);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_TRANSFER_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			
			//NEW POOL ACCOUNT account and 127 field changes.
			if(reqResp.getPayeeAddr().endsWith("@cosb")) {
				poolAccountNo=BEN_TIMEPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			}
			
			
			else if(reqResp.getPayeeAddr().endsWith("@cosmos")) {
				poolAccountNo=BEN_SOPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			}
			else {
				poolAccountNo=BEN_OTHER_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			}
			
			
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
			+ Util.padRight(poolAccountNo, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-CR/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayerAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			LOG.debug("isFir------- {}",reqResp.getIsFIR());
			
			if(reqResp.getPayeeAddr().endsWith("@cosb")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"TIMEPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else if(reqResp.getPayeeAddr().endsWith("@cosmos")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"SOPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"OTHERS"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			if("1".equalsIgnoreCase(reqResp.getIsFIR())){
			
				if(reqResp.getPayerAddr().endsWith("@cosb")) {
					m.set(127,firDetails(reqResp.getFirInfo())+"|"+"TIMEPAY"); 
				}
				else {
					m.set(127,firDetails(reqResp.getFirInfo())+"|"+"SOPAY"); 

				}
			
			}
			// with AZAD
			Trans trans = new Trans();
			try {
				trans.setAccNo(customerAccountNumber);//Payee Account No
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDIT");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				
				trans.setMccCode(reqResp.getMccCode());
				trans.setAccNo1(reqResp.getPayerAcNum());//Payer Account No
				trans.setPayeeAcType(reqResp.getPayeeAcType());
				trans.setPayerAcType(reqResp.getPayerAcType());
				trans.setPayeeType(reqResp.getPayeeType());
				trans.setPayerType(reqResp.getPayerType());
				if ("1".equalsIgnoreCase(reqResp.getIsUPI2())) {//if upi 2.0 request
					trans.setInitiationMode(reqResp.getInitiationMode());
					trans.setTxnPurpose(reqResp.getTxnPurpose());
				}
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections  at {} with request {}", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("After Back from CBS Con Socket at {} with response as {}", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));

				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					if (ISOMsgConstant.ERROR_CODE_CBS_119.equalsIgnoreCase(cbsResCode)) {
						String reasonText = isoMsgRes.getString(127);
						if (null != reasonText
								&& reasonText.toUpperCase().contains(ISOMsgConstant.ACCOUNT_TYPE_DORMANT)) {
							reqResp.setRespCode("D" + cbsResCode);
						} else {
							reqResp.setRespCode("F" + cbsResCode);
						}
					}
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.info("Response from creditAccount method is as {} ", reqResp.getRespCode());
		return reqResp;
	}

	@Override
	public ReqResp creditReversalAadhar(ReqResp reqResp) {
		LOG.info("Starting creditReversalAadhar method  with {} ", reqResp);
		try {
			String amount = reqResp.getPayeeAmount();
			amount = Util.convertAmountInPaisa(amount);
			String poolAccount = Util.getProperty("POOL_ACCOUNT_BEN");
			// 1.6.2.2 Funds Transfer authorization request
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI("1420");
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, "200");
			m.set(32, ISOMsgConstant.DE_32);
			m.set(33, ISOMsgConstant.DE_33);
			// m.set(37, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(43, Util.getProperty("BANK_NAME"));
			m.set(49, ISOMsgConstant.DE_49);
			m.set(46, "0000000000000000");
			m.set(90, ISOUtil.zeropadRight(reqResp.getReversalInfo(), 42));
			m.set(102, poolAccount);
			m.set(103, reqResp.getPayeeUidNum());
			m.set(123, "AEP");
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			Trans trans = new Trans();
			try {
				// trans.setAccNo(reqResp.getPayeeUidNum());
				// trans.setIfsc(reqResp.getPayeeIfsc());
				// trans.setOpration("CREDITREVERSAL");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setTxnTime(txnTime);
				// trans.setPoolAccount(poolAccount); // POOL ACCOUNT
				// transRepository.save(trans);

				trans.setAccNo(reqResp.getPayeeUidNum());
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDITREVERSAL");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccount); // POOL ACCOUNT
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.info("Going for Connecting with the CBS Socket Connections at {} with {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Return Success from CBS Socket Connections at {} with {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS); // TODO
					// maintain
					// the CBS
					// RESPONSE
					// CODE into
					// the DB
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				// no response from server response message null
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode()); // CBS RESPONSE
				// CODE SAVE
				// INTO THE
				// TRANS
				// TABLE
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.info("ending creditReversalAccount method with {} ", reqResp);
		return reqResp;

	}

	@Override
	public ReqResp creditReversalAccount(final ReqResp reqResp) {
		LOG.trace("Starting creditReversalAccount method");
		try {
			
			String poolAccountNo = null;
			String poolBranchCode = null;
			
			
			String branchCode = reqResp.getPayeeAcNum().substring(0, 3);
			String poolAccountNo = Util.getProperty("POOL_ACCOUNT_BEN");
			String poolBranchCode = poolAccountNo.substring(0, 3);
			String customerAccountNumber = reqResp.getPayeeAcNum();
			LOG.info("CustomerAccountNumber {} ", customerAccountNumber);
			String amount = reqResp.getPayeeAmount();
			amount = Util.convertAmountInPaisa(amount);
			LOG.info("Reversal amount in paisa is {} ", amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.FUND_TRANSFET_REVERSAL_MTI);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_REVERSAL_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			m.set(56, reqResp.getReversalInfo() + ISOMsgConstant.SUBCODE + ISOMsgConstant.BANK_CODE);
			
				if(reqResp.getPayeeAddr().endsWith("@cosb")) {
				poolAccountNo=BEN_TIMEPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			}
					
				
				else if(reqResp.getPayeeAddr().endsWith("@cosmos")) {
					poolAccountNo=BEN_SOPAY_ACC_POOL;
					poolBranchCode=poolAccountNo.substring(0, 3);
					m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
					+ Util.padRight(poolAccountNo, 19, ' '));
					m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
							+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
				}
				
			else {
				poolAccountNo=BEN_OTHER_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));;
			}
			
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
			+ Util.padRight(poolAccountNo, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-RC/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayeeAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getOrgTxnId()); // Add 13-july-2018 after discussing

			
			if(reqResp.getPayeeAddr().endsWith("@cosb")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"TIMEPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else if(reqResp.getPayeeAddr().endsWith("@cosmos")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"SOPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else {
				
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"OTHERS"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			Trans trans = new Trans();
			try {
				// trans.setAccNo(reqResp.getPayeeAcNum());
				// trans.setIfsc(reqResp.getPayeeIfsc());
				// trans.setOpration("CREDITREVERSAL");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setTxnTime(txnTime);
				// trans.setCrrn(reqResp.getField11());
				// trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				// transRepository.save(trans);
				trans.setAccNo(reqResp.getPayeeAcNum());
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDITREVERSAL");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getOrgTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections  at {} with {}  ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("After getting response from cbsConSocket at {} with response as {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.trace("ending creditReversalAccount method at {} with obj {} ", new Date(), reqResp);
		return reqResp;
	}


	//NRE CREDIT REVERSAL

	@Override
	public ReqResp creditReversalFIRAccount(final ReqResp reqResp) {
		LOG.trace("Starting creditReversalAccount method");
		try {
			String branchCode = reqResp.getPayeeAcNum().substring(0, 3);
			String poolAccountNo = Util.getProperty("POOL_ACCOUNT_BEN");
			String poolBranchCode = poolAccountNo.substring(0, 3);
			String customerAccountNumber = reqResp.getPayeeAcNum();
			LOG.debug("CustomerAccountNumber {} ", customerAccountNumber);
			String amount = reqResp.getPayeeAmount();
			amount = Util.convertAmountInPaisa(amount);
			LOG.debug("Reversal amount in paisa is {} ", amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.FUND_TRANSFET_REVERSAL_MTI);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_REVERSAL_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			m.set(56, reqResp.getReversalInfo() + ISOMsgConstant.SUBCODE + ISOMsgConstant.BANK_CODE);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
			+ Util.padRight(poolAccountNo, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-RC/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayeeAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getOrgTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			Trans trans = new Trans();
			try {
				// trans.setAccNo(reqResp.getPayeeAcNum());
				// trans.setIfsc(reqResp.getPayeeIfsc());
				// trans.setOpration("CREDITREVERSAL");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setTxnTime(txnTime);
				// trans.setCrrn(reqResp.getField11());
				// trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				// transRepository.save(trans);
				trans.setAccNo(reqResp.getPayeeAcNum());
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDITREVERSAL");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getOrgTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections  at {} with {}  ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("After getting response from cbsConSocket at {} with response as {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.trace("ending creditReversalAccount method at {} with obj {} ", new Date(), reqResp);
		return reqResp;
	}



	@Override
	public ReqResp debitAccount(final ReqResp reqResp) {
		try {
			
			String poolAccountNo = null;
			String poolBranchCode = null;
			Trans trans = new Trans();
			String amount = reqResp.getPayerAmount();
			String customerAccountNumber = reqResp.getPayerAcNum(); // 16-11-17
			// reqResp.getPayerAccNum()
			String branchCode = reqResp.getPayerAcNum().substring(0, 3); // 16-11-17
			// reqResp.getPayerAccNum()
			
			
			String poolAccountNo = Util.getProperty("POOL_ACCOUNT_REM");
			String poolBranchCode = poolAccountNo.substring(0, 3);
			amount = Util.convertAmountInPaisa(amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_TRANSFER_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			
			
			//NEW POOL ACCOUNT account and 127 field changes.
			if(reqResp.getPayerAddr().endsWith("@cosb")) {
				LOG.info("Its Time-pay TXN {}",reqResp.getTxnId());
				poolAccountNo=REM_TIMEPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			
			
			else if(reqResp.getPayerAddr().endsWith("@cosmos")) {
				LOG.info("Its Time-pay TXN {}",reqResp.getTxnId());
				poolAccountNo=REM_SOPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			else {
				LOG.info("Its SO-pay TXN {}",reqResp.getTxnId());
				poolAccountNo=REM_OTHER_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(customerAccountNumber, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-DR/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayeeAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			if(reqResp.getPayerAddr().endsWith("@cosb")) {
				String PayeeType=reqResp.getPayerCode().equals("0000")?"P2P":"P2M";
				m.set(127,reqResp.getPayerDeviceMobile()+"|"+"TIMEPAY"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			
			else if(reqResp.getPayerAddr().endsWith("@cosmos")) {
				String PayeeType=reqResp.getPayerCode().equals("0000")?"P2P":"P2M";
				m.set(127,reqResp.getPayerDeviceMobile()+"|"+"SOPAY"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			else {
				String PayeeType=reqResp.getPayerCode().equals("0000")?"P2P":"P2M";
				m.set(127,reqResp.getPayerDeviceMobile()+"|"+"OTHERS"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			try {
				trans.setAccNo(customerAccountNumber);
				trans.setIfsc(reqResp.getPayerIfsc());
				trans.setOpration("DEBIT");
				trans.setReq(new Date());
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setCrrn(reqResp.getField11());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount)); // Paise saved into the
				// DB
				trans.setTxnTime(txnTime);
				trans.setRefId(reqResp.getTxnRefId()); // 16-11-17
				trans.setRefURL(reqResp.getTxnRefUrl()); // 16-11-17
				trans.setTxnNote(reqResp.getTxnNote());
				trans.setMobileNumber(reqResp.getPayerDeviceMobile());// TODO PK
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				
				trans.setAccNo1(reqResp.getPayeeAcNum());//Payee Account No
				trans.setPayeeAcType(reqResp.getPayeeAcType());
				trans.setPayerAcType(reqResp.getPayerAcType());
				trans.setPayeeType(reqResp.getPayeeType());
				trans.setPayerType(reqResp.getPayerType());
				if (ConstantNew.MANDTAE_DEBIT.equalsIgnoreCase(reqResp.getSubOperation())) {
					trans.setSubOperation(reqResp.getSubOperation());//for mandate Debit 
				}
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections at {}  with req as {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Returned from cn=bs channel with response as {} at {}", isoMsgRes, new Date());
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setReqStr(new String(isoMsgRes.pack()));
				if (cbsResCode.equals(ISOMsgConstant.CBS_SUCCESS_RESPONSE)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					if (ISOMsgConstant.ERROR_CODE_CBS_119.equalsIgnoreCase(cbsResCode)) {
						String reasonText = isoMsgRes.getString(127);
						if (null != reasonText
								&& reasonText.toUpperCase().contains(ISOMsgConstant.ACCOUNT_TYPE_DORMANT)) {
							reqResp.setRespCode("D" + cbsResCode);
						} else {
							reqResp.setRespCode("F" + cbsResCode);
						}
					}
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.debug("Ending debitAccount method and returned with {}", reqResp);
		return reqResp;
	}

	@Override
	public ReqResp debitReversalAccount(final ReqResp reqResp) {
		try {
			
			String poolAccountNo = null;
			String poolBranchCode = null;
			String amount = reqResp.getPayerAmount();
			String customerAccountNumber = reqResp.getPayerAcNum(); // 16-11-17
			// reqResp.getPayerAccNum()
			String branchCode = reqResp.getPayerAcNum().substring(0, 3); // 16-11-17
			// reqResp.getPayerAccNum()
			String poolAccountNo = Util.getProperty("POOL_ACCOUNT_REM");
			String poolBranchCode = poolAccountNo.substring(0, 3);
			amount = Util.convertAmountInPaisa(amount);
			LOG.info("Reversal amount is as : {} ", amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.FUND_TRANSFET_REVERSAL_MTI);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16)); // wants to clearity
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_REVERSAL_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			m.set(56, reqResp.getReversalInfo() + ISOMsgConstant.SUBCODE + ISOMsgConstant.BANK_CODE);
			
			if(reqResp.getPayerAddr().endsWith("@cosb")) {
				poolAccountNo=REM_TIMEPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			
			else if(reqResp.getPayerAddr().endsWith("@cosmos")) {
				poolAccountNo=REM_SOPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			else {
				poolAccountNo=REM_OTHER_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(customerAccountNumber, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-RD/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayerAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getOrgTxnId()); // Add 13-july-2018 after discussing
			
			if(reqResp.getPayerAddr().endsWith("@cosb")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"TIMEPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else if(reqResp.getPayerAddr().endsWith("@cosmos")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"SOPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			else {
				
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"OTHERS"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			Trans trans = new Trans();
			try {
				// trans.setAccNo(reqResp.getPayerAcNum());
				// trans.setIfsc(reqResp.getPayerIfsc());
				// trans.setOpration("DEBITREVERSAL");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setTxnTime(txnTime);
				// trans.setCrrn(reqResp.getField11());
				// trans.setAmount(Long.parseLong(amount));
				// trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				// transRepository.save(trans);

				trans.setAccNo(customerAccountNumber);
				trans.setIfsc(reqResp.getPayerIfsc());
				trans.setOpration("DEBITREVERSAL");
				trans.setReq(new Date());
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setCrrn(reqResp.getField11());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getOrgTxnId());
				trans.setAmount(Long.parseLong(amount)); // Paise saved into the
				// DB
				trans.setTxnTime(txnTime);
				trans.setRefId(reqResp.getTxnRefId()); // 16-11-17
				trans.setRefURL(reqResp.getTxnRefUrl()); // 16-11-17
				trans.setTxnNote(reqResp.getTxnNote());
				trans.setMobileNumber(reqResp.getPayerDeviceMobile());// TODO PK
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				
				if (ConstantNew.MANDTAE_REV.equalsIgnoreCase(reqResp.getSubOperation())) {
					trans.setSubOperation(reqResp.getSubOperation());
				}
				transRepository.save(trans);

			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.info("Initiating CBS req at {} with {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.info("Response from CBS received at {} with {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.debug("Ending debitReversalAccount method  with {} ", reqResp);
		return reqResp;
	}

	@Override
	public ReqResp fatchAccounts(final ReqResp reqResp, final String custId, final String custName,
			final String mobileNo) {
		List<Account> list = new ArrayList<Account>();
		try {
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			Date date = new Date();
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.setHeader(ISOMsgConstant.ISO_HEADER.getBytes());
			m.set(2, custId);
			m.set(3, ISOMsgConstant.GET_ACC_LIST_PROCESSING_CODE);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(date));
			m.set(17, ISOUtility.getTransactionDate8(date));
			m.set(24, ISOMsgConstant.STATUS_CODE);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(49, ISOMsgConstant.DE_49);
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, ISOMsgConstant.DE_125 + Util.padLeft(custId, 9, ' ') + ISOMsgConstant.DE_ALL);
			m.set(126, reqResp.getTxnId()); 
			Trans trans = new Trans();
			try {
				trans.setAccNo(reqResp.getLinkValue());
				trans.setOpration("LISTACCOUNT");
				//trans.setReq(date);
				//trans.setReqStr(new String(m.pack()));
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.info("Going for Connecting with the CBS Socket Connections at {} ", new Date());
			//ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			
			ISOMsg isoMsgRes = new ISOMsg();
			isoMsgRes.set(125,"LP03 01030554ALL010204201930805 SBA+0000000003736017INR90620010205603  SBA+0000000003455900INR010200101416563 TDA+0000000001090100INR");
			isoMsgRes.set(127,"COSB0000111,COSB0000222");
			isoMsgRes.set(39,"000");
			LOG.info("Returned from cbsConSocket at {} ", new Date());
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				LOG.info("Got CBS response {}", cbsResCode);
				reqResp.setRespCode(cbsResCode);
				//trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
					list = splitListAccountFromCBS(isoMsgRes.getString(125), custName, mobileNo,reqResp,isoMsgRes.getString(127));

					if (list.isEmpty()) {
						trans.setStatus(ISOMsgConstant.STATUS_FAIL);
						reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
					} else {
						int count = 1;
						for (Account account : list) {
							if (count <= FETCH_IFSC_COUNT) { //FETCH IFSC COUNT
								account.setIfsc(getIfscCode(reqResp, account.getAccRefNumber()));
							} else {
								account.setIfsc("COSB0000000");
							}
						}
						reqResp.setAccounts(list);
					}
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			} 
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return reqResp;
	}

	@Override
	public String getIfscCode(ReqResp reqResp, final String custAccNo) {
		String ifscCode = null;
		try {
			String branchCode = custAccNo.substring(0, 3);
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			Date date = new Date();
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.setHeader(ISOMsgConstant.ISO_HEADER.getBytes());
			m.set(2, ISOMsgConstant.DE_2);
			m.set(3, ISOMsgConstant.DE_3);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(date));
			m.set(17, ISOUtility.getTransactionDate8(date));
			m.set(24, ISOMsgConstant.STATUS_CODE);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(custAccNo, 17, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			LOG.debug("Going for Connecting with the CBS Socket Connections at {} with {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Return success from cbsConSocket  at {} with {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					ifscCode = splitIfsc(isoMsgRes.getString(126));
				} else {
					ifscCode = "COSB0000000";
				}
			}else {
				ifscCode = "COSB0000000";
			}
		} catch (Exception e) {
			ifscCode = "COSB0000000";
			LOG.error(e.getMessage(), e);
		}
		return ifscCode;

	}
	
	 * public static void main(String[] args) { String str=
	 * "090~UNIVERSITY ROAD BRANCH,PUNE~~~          2082.66~COSB0000090~~29-12-2017"
	 * ; String []ifsc=str.split("~"); System.out.println(ifsc[5]); }
	 

	private List<String> parseCBSResponseListAccount(final String tempAccountString, final String custName,
			final String mobileNo) {
		List<String> data = new ArrayList<String>();
		List<String> tempAccountList = new ArrayList<String>();
		String noOfAccounts = null;
		String remaningStr = null;
		String tempAccStr = null;
		if (null != tempAccountString && null != custName && null != mobileNo) {
			noOfAccounts = tempAccountString.substring(2, 4);
			remaningStr = tempAccountString.substring(16, tempAccountString.length());
			for (int i = 0; i < remaningStr.length(); i = i + 39) {
				tempAccountList.add(remaningStr.substring(i, i + 39));
			}
			tempAccStr = custName + "~" + mobileNo + "~N~" + noOfAccounts;
			data.add(tempAccStr);
			for (String str : tempAccountList) {
				String accountString = null;
				if (str.contains("SBA")) {
					accountString = str.substring(0, 16) + "~SBA";
					data.add(accountString);
				}
				if (str.contains("CAA")) {
					accountString = str.substring(0, 16) + "~CAA";
					data.add(accountString);
				}
				if (str.contains("ODA")) {
					accountString = str.substring(0, 16) + "~ODA";
					data.add(accountString);
				}
			}
		}
		LOG.info("Return success from parseCBSResponseListAccount with response as as {}", data);
		return data;
	}

	private String splitIfsc(final String tempString) {
		String[] str = tempString.split("~");
		return str[5];
	}

	
	private ArrayList<String> parseCBSResponseIFSC(final String tempAccountString) {
		ArrayList<String> data = new ArrayList<String>();
		List<String> tempAccountList = new ArrayList<String>();
		String remaningStr = null;
		if (null != tempAccountString) {
			remaningStr = tempAccountString.substring(0, tempAccountString.length());
			for (int i = 0; i < remaningStr.length(); i = i + 12) {
				tempAccountList.add(remaningStr.substring(i, i + 11));
			}
			for (String str : tempAccountList) {
				    String ifscString = null;
				    ifscString = str.substring(0, 11);
					data.add(ifscString);
			}
		}
		LOG.info("Return success from parseCBSResponseIFSC with response as as {}", data);
		return data;
	}

	@SuppressWarnings("unused")
	private List<Account> splitListAccountFromCBS(final String tempAccountString, final String custName,
			final String mobileNo,ReqResp reqResp,final String ifsc) {
		LOG.info("Starting from split List Account From CBS with tempAccountString as {}, custName as {} and mob no as {} ",tempAccountString, custName, mobileNo);
		LOG.info("value of isUpi {} ",reqResp.getIsUPI2());
		List<String> data = parseCBSResponseListAccount(tempAccountString, custName, mobileNo);
		String[] commonFields = data.get(0).split("~");
		
		ArrayList<String> ifscdata = parseCBSResponseIFSC(ifsc);
		List<Account> list = new ArrayList<>();
		String accountName;
		String aadhaar;
		
		String mobileNumber;
		int noOfAccount;
		if (commonFields.length >= 4) {
			accountName = commonFields[0];
			mobileNumber = commonFields[1];
			aadhaar = commonFields[2];
			noOfAccount = Integer.parseInt(commonFields[3].trim());
			if (noOfAccount > 0) {
				for (int count = 1; count < data.size(); count++) {
					Account account = new Account();
					try {
						for (int ifcscounnt = 0; ifcscounnt < ifscdata.size(); ifcscounnt++) {
					    	String[] ifscfields = ifscdata.get(ifcscounnt).split(",");
					    	String ii=ifscfields[0];
					    	if(ii!=null&&ii!="") {
					    		account.setIfsc(ii);
					    	}else {
					    		account.setIfsc("COSB0000000");
					    	}
					    	break;
						}
				    	
						String[] fields = data.get(count).split("~");
						account.setAccRefNumber(fields[0].trim());
						account.setMaskedAccnumber(maskedAccountNumber(fields[0].trim()));
						if ("SBA".equalsIgnoreCase(fields[1])) {
							account.setAccType("SAVINGS");
						}else if ("CAA".equalsIgnoreCase(fields[1])) {
							account.setAccType("CURRENT");
						}
					    if ("1".equalsIgnoreCase(reqResp.getIsUPI2())) {
					    	LOG.info("Going to set AccType for 2.0 is {}",reqResp.getIsUPI2());
							account.setAccType(accountVerificationForListAccount(reqResp,account.getAccRefNumber()));
						}
						account.setName(accountName);
						account.setAadhaar(aadhaar);
						account.setMobNumber(mobileNumber);
						list.add(account);
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
		}
		System.out.println("HIII"+list.toString());
		return list;
	}

	
	@Override
	public ReqResp accountVerification(ReqResp reqResp) {
		LOG.trace("Starting accountVerification from tempIsoMessageFromCBS:{} ",reqResp.toString());
		String custAccNo=reqResp.getPayeeAcNum();
		LOG.debug("accountVerification   {}", custAccNo);
		String branchCode = custAccNo.substring(0, 3);
		try {
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			Date date = new Date();
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.setHeader(ISOMsgConstant.ISO_HEADER.getBytes());
			m.set(2, ISOMsgConstant.DE_2);
			m.set(3, ISOMsgConstant.DE_4);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(date));
			m.set(17, ISOUtility.getTransactionDate8(date));
			m.set(24, ISOMsgConstant.STATUS_CODE);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(custAccNo, 17, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, ISOMsgConstant.BAL_DE_125);
			Trans trans = new Trans();
			try {
				trans.setAccNo(reqResp.getPayeeAcNum());
				trans.setOpration("ACCOUNTVERIFICATION");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				//transRepository.save(trans);
			} catch (Exception e) {
				LOG.error("Exception caught at verifyAccounts : " + e);
				e.printStackTrace();
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections at {}  with req as {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Returned from cn=bs channel with response as {} at {}", isoMsgRes, new Date());
			if (isoMsgRes != null) {
				LOG.debug("Request for verify accno Inside isoMsgRes != null ");
				String cbsResCode = isoMsgRes.getString(39);
				LOG.debug("CBS RESPONSE CODE {}",cbsResCode);
				String accountAndStatus = isoMsgRes.getString(125);
				LOG.debug("Getting data in 125 field {}",accountAndStatus);

				trans.setRespStr(new String(isoMsgRes.pack()));
				if (null != accountAndStatus) {
					reqResp.setRespCode(cbsResCode);
					LOG.debug("Inside IF Block");
					if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
						LOG.debug("Inside If Block ISOMsgConstant.CBS_SUCCESS_RESPONSE");
						trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
						List<Account> list = splitAccountStatus(accountAndStatus);
						reqResp.setAccounts(list);
					}else {
						trans.setStatus(ISOMsgConstant.STATUS_FAIL);
					}
				}else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
					reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
				}

			}else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				//transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.debug("Ending Account verification method and returned with {}", reqResp);
		return reqResp;
	}



	@Override
	public String  accountVerificationForListAccount(ReqResp reqResp, final String custAccNum) {
		LOG.trace("Starting accountVerification for list account from tempIsoMessageFromCBS: {} " , reqResp.toString());
		String custAccNo=custAccNum;
		LOG.info("Acc No "+custAccNo);
		String branchCode = custAccNo.substring(0, 3);
		String accType = null;
		try {
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			Date date = new Date();
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.setHeader(ISOMsgConstant.ISO_HEADER.getBytes());
			m.set(2, ISOMsgConstant.DE_2);
			m.set(3, ISOMsgConstant.DE_4);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(date));
			m.set(17, ISOUtility.getTransactionDate8(date));
			m.set(24, ISOMsgConstant.STATUS_CODE);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(custAccNo, 17, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, ISOMsgConstant.BAL_DE_125);

			LOG.debug("Going for Connecting with the CBS Socket Connections at {}  with req as {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Returned from cn=bs channel with response as {} at {}", isoMsgRes, new Date());
			if (isoMsgRes != null) {
				LOG.debug("Request for verify accno Inside isoMsgRes != null ");
				String cbsResCode = isoMsgRes.getString(39);
				LOG.debug("CBS RESPONSE CODE {}",cbsResCode);
				String accountAndStatus = isoMsgRes.getString(125);
				LOG.debug("Getting data in 125 field {}",accountAndStatus);

				if (null != accountAndStatus) {
					reqResp.setRespCode(cbsResCode);
					LOG.debug("Inside IF Block");
					if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
						LOG.debug("Inside If Block ISOMsgConstant.CBS_SUCCESS_RESPONSE");
						accType = splitAccountStatusListAccount(accountAndStatus);

					}
				}
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.debug("Ending Account verification method and returned with {}", reqResp);
		return accType;
	}


	private List<Account> splitAccountStatus(final String tempAccountString) {
		String accStatus=tempAccountString.substring(19, 21);
		String accName=tempAccountString.substring(45, 90);
		String accType = tempAccountString.substring(0, 3);
		String accTypesNreNroOD = tempAccountString.substring(6, 11);
		String accTypesSB = tempAccountString.substring(8, 11);
		List<Account> list = new ArrayList<>();
		try {
			Account account = new Account();
			if (ConstantNew.ACC_TYPE_CAA.equalsIgnoreCase(accType)) {
				account.setAccType(ConstantNew.ACC_TYPE_CURRENT);
			}
			if (ConstantNew.ACC_TYPE_SBA.equalsIgnoreCase(accType)) {
				 if (ConstantNew.ACC_TYPE_SBNRE.equalsIgnoreCase(accTypesNreNroOD)) {
					 account.setAccType(ConstantNew.ACC_TYPE_NRE);
				}else if (ConstantNew.ACC_TYPE_SBNRO.equalsIgnoreCase(accTypesNreNroOD)) {
					account.setAccType(ConstantNew.ACC_TYPE_NRO);
				}else {
					account.setAccType(ConstantNew.ACC_TYPE_SAVINGS);
				}
			}
			if (ConstantNew.ACC_TYPE_ODA.equalsIgnoreCase(accType)) {
				account.setAccType(ConstantNew.ACC_TYPE_UOD);
			}
			account.setName(accName.trim());
			account.setAccStatus(accStatus);
			list.add(account);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return list;
	}

	private String splitAccountStatusListAccount(final String tempAccountString) {
		String accType = tempAccountString.substring(0, 3);
		String accTypesNreNroOD = tempAccountString.substring(6, 11);
		//String accTypesSB = tempAccountString.substring(8, 11);
		String accountType = null;
		try {
			if (ConstantNew.ACC_TYPE_CAA.equalsIgnoreCase(accType)) {
				accountType = ConstantNew.ACC_TYPE_CURRENT;
			}
			if (ConstantNew.ACC_TYPE_SBA.equalsIgnoreCase(accType)) {
				 if (ConstantNew.ACC_TYPE_SBNRE.equalsIgnoreCase(accTypesNreNroOD)) {
					accountType = ConstantNew.ACC_TYPE_NRE;
				}else if (ConstantNew.ACC_TYPE_SBNRO.equalsIgnoreCase(accTypesNreNroOD)) {
					accountType = ConstantNew.ACC_TYPE_NRO;
				}else {
					accountType = ConstantNew.ACC_TYPE_SAVINGS;
				}
			}
			if (ConstantNew.ACC_TYPE_ODA.equalsIgnoreCase(accType)) {
				accountType = ConstantNew.ACC_TYPE_UOD;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.info("Return Account Type Is   "+ accountType);
		return accountType;
	}

	  private  String firDetails(String  data) {
		    String mystringarr[] =data.split("\\/");
			String data1=mystringarr[6];
			String mystringarr1[] =data1.split("\\-");
			String addressCity = mystringarr1[1];
			String addressCountry = mystringarr1[2];
			String result=mystringarr[0]+"/"+mystringarr[5]+"/"+addressCity+"/"+addressCountry;
		  return result;
	  }
	
	  
	  private static String firDetails1(String  data) {
		  StringTokenizer token = new StringTokenizer(data, "\\/");
		  StringBuilder sbFIR = new StringBuilder("");
		  int count=0;
		  while(token.hasMoreTokens()){
			  sbFIR.append(token.nextToken());
			  count++;
			  if(count==0 || count==5 || count ==6){
				  sbFIR.append("/");
			  }
		  }
		  System.out.println(sbFIR);
		  return sbFIR.toString();
	  }
	@Override
	public ReqResp creditAccountFir(final ReqResp reqResp) {
		String amount = reqResp.getPayeeAmount();
		try {
			amount = Util.convertAmountInPaisa(amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			String customerAccountNumber = reqResp.getPayeeAcNum();
			String branchCode = reqResp.getPayeeAcNum().substring(0, 3);
			String poolAccountNo = Util.getProperty("POOL_ACCOUNT_BEN");
			String poolBranchCode = poolAccountNo.substring(0, 3);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_TRANSFER_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
			+ Util.padRight(poolAccountNo, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-CR/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayerAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			m.set(127, reqResp.getFirInfo());								// with AZAD
			Trans trans = new Trans();
			try {
				trans.setAccNo(customerAccountNumber);
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDIT");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				trans.setSubType(ISOMsgConstant.FIR_TYPE);
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections  at {} with request {}", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("After Back from CBS Con Socket at {} with response as {}", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));

				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					if (ISOMsgConstant.ERROR_CODE_CBS_119.equalsIgnoreCase(cbsResCode)) {
						String reasonText = isoMsgRes.getString(127);
						if (null != reasonText
								&& reasonText.toUpperCase().contains(ISOMsgConstant.ACCOUNT_TYPE_DORMANT)) {
							reqResp.setRespCode("D" + cbsResCode);
						} else {
							reqResp.setRespCode("F" + cbsResCode);
						}
					}
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.info("Response from creditAccount method is as {} ", reqResp.getRespCode());
		return reqResp;
	}


	 public static void main(String[] args) {
	 ReqResp reqResp = new ReqResp();
	 String str="ODAINRODNRE20151027A        +0000000000209903KEDARI VINAYAK FAKIRAPPA                                                                                                +0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000209903+0000000000000000+0000000000209903"; 
	  String accStatus=str.substring(19, 21);
	  String accName=str.substring(45, 90);
	  String accType = str.substring(0, 3);
	  String accTypes = str.substring(8, 11);
	  String accTypesNreNroOD = str.substring(6, 11);
		String accTypesSB = str.substring(8, 11);
	  System.out.println("AccountTye NRE:::::"+accTypes);
	  System.out.println("Accout status is"+accStatus);
	  System.out.println("Name"+accName.trim());
	  System.out.println("Type"+accType);
	  List<Account> list = new ArrayList<>();
	  Account account = new Account();
					account.setName(accName.trim());

					if ("CAA".equalsIgnoreCase(accType)) {
						LOG.info("Inside SBA BLOCK");
						account.setAccType("CURRENT");
					}
					if ("SBA".equalsIgnoreCase(accType)) {
						 if ("SBNRE".equalsIgnoreCase(accTypesNreNroOD)) {
							 account.setAccType("NRE");
						}else if ("SBNRO".equalsIgnoreCase(accTypesNreNroOD)) {
							account.setAccType("NRO");
						}else {
							account.setAccType("SAVINGS");
						}
					}
					if ("ODA".equalsIgnoreCase(accType)) {
						if ("NRE".equalsIgnoreCase(accTypesSB)) {
							account.setAccType("NRE");
						}else if ("NRO".equalsIgnoreCase(accTypesSB)) {
							account.setAccType("NRO");
						}else {
							account.setAccType("OD");
						}
					}
					account.setAccStatus(accStatus);
					LOG.debug("Account details {}",account.toString());
					list.add(account);
					reqResp.setAccounts(list);
					
}
	 private ISOMsg tempIsoMessageFromCBS() {
			LOG.trace("Starting from tempIsoMessageFromCBS:");
			ISOMsg m = null;
			try {
				org.jpos.util.Logger logger = new org.jpos.util.Logger();
				logger.addListener(new SimpleLogListener(System.out));
				CBSPackager packager = new CBSPackager();
				NACChannel channel = new NACChannel();
				channel.setPackager(packager);
				((LogSource) channel).setLogger(logger, "test-channel");
				m = new ISOMsg();
				m.setPackager(packager);
				m.setDirection(ISOMsg.INCOMING);
				m.setHeader("49534F303135303030303135".getBytes());
				Date date = new Date();
				m.setMTI("1210");
				m.set(2, "0902616");
				m.set(3, "370000");
				m.set(11, "000000454253");
				m.set(12, "20170817113236");
				m.set(17, "20170928");
				m.set(32, "627611");
				m.set(38, "UNI000");
				m.set(39, "000");
				m.set(48,
						"+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000                 ");
				m.set(49, "356");
				m.set(123, "ITB");
				// m.set(124, "TAB");
				// m.set(125, "LP11 0428766ALL0421501017240
				// SBA+0000000001000000INR0421501017259
				// TDA+0000000001000000INR0421501017268
				// SBA+0000000001000000INR0421501017231
				// SBA+0000000001000000INR0421501017277
				// TDA+0000000001000000INR0421501017222
				// TDA+0000000001000000INR0421501017286
				// TDA+0000000001000000INR0421501017541
				// SBA+0000000010000000INR04265010806
				// ODA+0000000000000000INR0421501017295
				// TDA+0000000001000000INR0420501034609 SBA+0000000000416500INR");
				m.set(125,
						"LP02  0428766ALL0900501016560   SBA+0000000001000000INR0901501017259   SBA+0000000001000000INR");
				// m.set(125, "LP00 0428766ALL");
				m.set(127,
						"ATMIN~8149742212~8149742212~8149742212~vinayakkedari14@gmail.com~14-09-1991 00:00:00~CMRPK8918G~*");
				byte[] data = m.pack();
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			return m;
		}
	
    


	public static void main(String[] args) {
		String balanceString="+0000000000211764+0000000000211764+0000000000000000+0000000000500000+0000000000211764INR              ";
		//String s ="FIR/PAN-SDF45645BJB20/Shyam/MTO/MTSS/822217098761/London-London-UK/134.07273.987.8253072";
		String f=balanceString.substring(0,16).startsWith("+")?"C000"+balanceString.substring(1,17):"D000"+balanceString.substring(1,17);
		// s1=firDetails1(s);
		System.out.println(f);
		System.out.println(balanceString.substring(0,17));
		String mystringarr[] =s.split("\\/");
		String s1=mystringarr[6];
		String mystringarr1[] =s1.split("\\-");
		String addressCity = mystringarr1[1];
		String addressCountry = mystringarr1[2];
		String result=mystringarr[0]+"/"+mystringarr[5]+"/"+addressCity+"/"+addressCountry;
		//System.out.println(result);
	}

}

*/

















































package com.npst.middleware.cbs.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.NACChannel;
import org.jpos.util.LogSource;
import org.jpos.util.SimpleLogListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.cbs.service.CBSConSocket;
import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.dao.TransRepository;
import com.npst.middleware.entity.Trans;
import com.npst.middleware.iso.CBSPackager;
import com.npst.middleware.iso.ISO93APackager;
import com.npst.middleware.iso.ISOUtility;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.obj.ReqResp.Account;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.ISOMsgConstant;
import com.npst.middleware.util.Util;

@Service
public class CBSProcessImpl implements CBSProcess {
	private static final Logger LOG = LoggerFactory.getLogger(CBSProcessImpl.class);
	private static final Integer FETCH_IFSC_COUNT=Integer.parseInt(Util.getProperty("FETCH_IFSC_COUNT"));
	
	private static final String REM_OTHER_ACC_POOL=Util.getProperty("POOL_ACCOUNT_REM");
	private static final String BEN_OTHER_ACC_POOL=Util.getProperty("POOL_ACCOUNT_BEN");
	
	
	private static final String REM_SOPAY_ACC_POOL=Util.getProperty("REM_SOPAY_ACC_POOL");
	private static final String REM_TIMEPAY_ACC_POOL=Util.getProperty("REM_TIMEPAY_ACC_POOL");
	
	
	private static final String BEN_SOPAY_ACC_POOL=Util.getProperty("BEN_SOPAY_ACC_POOL");
	private static final String BEN_TIMEPAY_ACC_POOL=Util.getProperty("BEN_TIMEPAY_ACC_POOL");

	private static String maskedAccountNumber(String accountNumber) { // Needs
		// to
		// fix
		// masking
		LOG.info("Masking method for account number {}:", accountNumber);
		int total = accountNumber.length();
		int startlen = 0, endlen = 5;
		int masklen = total - (startlen + endlen);
		StringBuffer maskedbuf = new StringBuffer(accountNumber.substring(0, startlen));
		for (int i = 0; i < masklen; i++) {
			maskedbuf.append('X');
		}
		maskedbuf.append(accountNumber.substring(startlen + masklen, total));
		String masked = maskedbuf.toString();
		long endtime = System.currentTimeMillis();
		LOG.info("Masked Account number:{}  of : {} size ", masked, masked.length());
		return masked;
	}

	@Autowired
	CBSConSocket cbsConSocket;

	@Autowired
	TransRepository transRepository;

	@Override
	public ReqResp balEnq(final ReqResp reqResp) {
		try {
			String branchCode = reqResp.getPayerAcNum().substring(0, 3);
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			m.setHeader(ISOMsgConstant.MSG_HEADER.getBytes());
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.set(2, ISOMsgConstant.DE_2);
			m.set(3, ISOMsgConstant.BAL_ENQ_PROCESSING_CODE);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(new Date()));
			m.set(17, ISOUtility.getTransactionDate8(new Date()));
			m.set(24, ISOMsgConstant.DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(reqResp.getPayerAcNum(), 17, ' ')); // 16-11-17
			m.set(123, ISOMsgConstant.DE_123);
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			m.set(127,reqResp.getPayerDeviceMobile()); //Added 6th-July-2018 

			LOG.info("Going for Connecting with the CBS Socket Connections at {} with request as {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.info("After back from cbs con socket at {} with response as {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				BigInteger ledgBal = new BigInteger(ISOMsgConstant.BAL_ZERO);
				BigInteger ffdBal = new BigInteger(ISOMsgConstant.BAL_ZERO);
				reqResp.setRespCode(cbsResCode);
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					String balance = "1001356C0000000000001001356C000000000000";
					
					try {
						String balanceString = isoMsgRes.getString(48);
						if (balanceString != null) {
				
							ledgBal = new BigInteger(balanceString.substring(17,34));
							String f=balanceString.substring(17,34).startsWith("+")?"C000"+balanceString.substring(18,34):"D000"+balanceString.substring(18,34);
							LOG.info("first Bal="+f);
							ffdBal = new BigInteger(balanceString.substring(51,68));
							BigInteger sumOfledgAndffdBal = ledgBal.add(ffdBal);
							String s = sumOfledgAndffdBal.toString();
							LOG.info("sumOfledgAndffdBal="+s);
							if ("1".equalsIgnoreCase(reqResp.getIsUPI2())) {
								s=addPrefixW20(s);
								LOG.info("addPrefixW20 return="+s);
								balance=s+f;//f+s
								balance = balance.replaceAll("C0000000", "1001356C");
								if(balance.contains("D")){
									balance = balance.replaceAll("D0000000", "1001356D");
								}
								LOG.info("two balance is ="+balance);
							}else {
								balance=s;
								balance = Util.convertPaisaInAmount(balance)+ISOMsgConstant.FUND_TRANSFER_DE_49;
							}
							
						}
					} catch (Exception e) {
						LOG.error("errorB: {}", e);
					}
					LOG.info("final balance="+balance);
					reqResp.setRespBal(balance);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
		} catch (Exception e) {
			LOG.error("errorFinal :{}", e);
		}
		LOG.info("Response from balEnq method is as {} ", reqResp.getRespCode());
		return reqResp;
	}

	private static String addPrefixW20(String s) {
		int l=s.length();
		int act=19-l;
		StringBuilder sb=new StringBuilder("C");
		for(int i=0;i<act;i++){
			sb.append("0");
		}
		sb.append(s);
		return sb.toString();
	}

	@Override
	public ReqResp creditAadhar(ReqResp reqResp) {
		String amount = reqResp.getPayeeAmount();
		String customerAadharNumber = reqResp.getPayeeUidNum();
		String poolAccount = Util.getProperty("POOL_ACCOUNT_BEN");
		try {
			amount = Util.convertAmountInPaisa(amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI("1200");
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, "200");
			m.set(32, ISOMsgConstant.DE_32);
			m.set(33, ISOMsgConstant.DE_33);
			// m.set(37, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(43, Util.getProperty("BANK_NAME"));
			m.set(49, ISOMsgConstant.DE_49);
			m.set(46, "0000000000000000");
			m.set(102, poolAccount);
			m.set(103, customerAadharNumber);
			m.set(123, "UPI");
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			Trans trans = new Trans();
			try {
				// trans.setAccNo(customerAadharNumber);
				// trans.setIfsc(reqResp.getPayeeIfsc());
				// trans.setOpration("CREDIT");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setAmount(Long.parseLong(amount)); // Paise saved into
				// the
				// // DB
				// trans.setTxnTime(txnTime);
				// trans.setRefId(reqResp.getTxnRefId()); // 16-11-17
				// // trans.setRefId(reqResp.getRefId());
				// trans.setRefURL(reqResp.getTxnRefUrl()); // 16-11-17
				// // trans.setRefURL(reqResp.getRefURL());
				// trans.setTxnNote(reqResp.getTxnNote());
				// trans.setMobileNumber(reqResp.getPayeeDeviceMobile());// TODO
				// PK
				// trans.setPoolAccount(poolAccount); // POOL ACCOUNT
				// transRepository.save(trans);

				trans.setAccNo(customerAadharNumber);
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDIT");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccount); // POOL ACCOUNT
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.info("Going for Connecting with the CBS Socket Connections at {} with {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Return success from cbsConSocket with {} at {} ", isoMsgRes, new Date());
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					if (ISOMsgConstant.ERROR_CODE_CBS_119.equalsIgnoreCase(cbsResCode)) {
						String reasonText = isoMsgRes.getString(127);
						if (null != reasonText
								&& reasonText.toUpperCase().contains(ISOMsgConstant.ACCOUNT_TYPE_DORMANT)) {
							reqResp.setRespCode("D" + cbsResCode);
						} else {
							reqResp.setRespCode("F" + cbsResCode);
						}
					}
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.info("Ending credit AADHAR method as {} ", reqResp);
		return reqResp;

	}

	@Override
	public ReqResp creditAccount(final ReqResp reqResp) {
		String amount = reqResp.getPayeeAmount();
		try {
			
			String poolAccountNo = null;
			String poolBranchCode = null;
			
			amount = Util.convertAmountInPaisa(amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			String customerAccountNumber = reqResp.getPayeeAcNum();
			String branchCode = reqResp.getPayeeAcNum().substring(0, 3);
			//String poolAccountNo = Util.getProperty("POOL_ACCOUNT_BEN");
			//String poolBranchCode = poolAccountNo.substring(0, 3);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_TRANSFER_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			
			//NEW POOL ACCOUNT account and 127 field changes.
			if(reqResp.getPayeeAddr().endsWith("@cosb")) {
				poolAccountNo=BEN_TIMEPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			}
			
			
			else if(reqResp.getPayeeAddr().endsWith("@cosmos")) {
				poolAccountNo=BEN_SOPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			}
			else {
				poolAccountNo=BEN_OTHER_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			}
			
			
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
			+ Util.padRight(poolAccountNo, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-CR/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayerAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			LOG.debug("isFir------- {}",reqResp.getIsFIR());
			
			if(reqResp.getPayeeAddr().endsWith("@cosb")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"TIMEPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else if(reqResp.getPayeeAddr().endsWith("@cosmos")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"SOPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"OTHERS"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			if("1".equalsIgnoreCase(reqResp.getIsFIR())){
			
				if(reqResp.getPayerAddr().endsWith("@cosb")) {
					m.set(127,firDetails(reqResp.getFirInfo())+"|"+"TIMEPAY"); 
				}
				else {
					m.set(127,firDetails(reqResp.getFirInfo())+"|"+"SOPAY"); 

				}
			
			}
			// with AZAD
			Trans trans = new Trans();
			try {
				trans.setAccNo(customerAccountNumber);//Payee Account No
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDIT");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				
				trans.setMccCode(reqResp.getMccCode());
				trans.setAccNo1(reqResp.getPayerAcNum());//Payer Account No
				trans.setPayeeAcType(reqResp.getPayeeAcType());
				trans.setPayerAcType(reqResp.getPayerAcType());
				trans.setPayeeType(reqResp.getPayeeType());
				trans.setPayerType(reqResp.getPayerType());
				if ("1".equalsIgnoreCase(reqResp.getIsUPI2())) {//if upi 2.0 request
					trans.setInitiationMode(reqResp.getInitiationMode());
					trans.setTxnPurpose(reqResp.getTxnPurpose());
				}
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections  at {} with request {}", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("After Back from CBS Con Socket at {} with response as {}", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));

				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					if (ISOMsgConstant.ERROR_CODE_CBS_119.equalsIgnoreCase(cbsResCode)) {
						String reasonText = isoMsgRes.getString(127);
						if (null != reasonText
								&& reasonText.toUpperCase().contains(ISOMsgConstant.ACCOUNT_TYPE_DORMANT)) {
							reqResp.setRespCode("D" + cbsResCode);
						} else {
							reqResp.setRespCode("F" + cbsResCode);
						}
					}
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.info("Response from creditAccount method is as {} ", reqResp.getRespCode());
		return reqResp;
	}

	@Override
	public ReqResp creditReversalAadhar(ReqResp reqResp) {
		LOG.info("Starting creditReversalAadhar method  with {} ", reqResp);
		try {
			String amount = reqResp.getPayeeAmount();
			amount = Util.convertAmountInPaisa(amount);
			String poolAccount = Util.getProperty("POOL_ACCOUNT_BEN");
			// 1.6.2.2 Funds Transfer authorization request
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI("1420");
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, "200");
			m.set(32, ISOMsgConstant.DE_32);
			m.set(33, ISOMsgConstant.DE_33);
			// m.set(37, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(43, Util.getProperty("BANK_NAME"));
			m.set(49, ISOMsgConstant.DE_49);
			m.set(46, "0000000000000000");
			m.set(90, ISOUtil.zeropadRight(reqResp.getReversalInfo(), 42));
			m.set(102, poolAccount);
			m.set(103, reqResp.getPayeeUidNum());
			m.set(123, "AEP");
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			Trans trans = new Trans();
			try {
				// trans.setAccNo(reqResp.getPayeeUidNum());
				// trans.setIfsc(reqResp.getPayeeIfsc());
				// trans.setOpration("CREDITREVERSAL");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setTxnTime(txnTime);
				// trans.setPoolAccount(poolAccount); // POOL ACCOUNT
				// transRepository.save(trans);

				trans.setAccNo(reqResp.getPayeeUidNum());
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDITREVERSAL");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccount); // POOL ACCOUNT
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.info("Going for Connecting with the CBS Socket Connections at {} with {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Return Success from CBS Socket Connections at {} with {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS); // TODO
					// maintain
					// the CBS
					// RESPONSE
					// CODE into
					// the DB
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				// no response from server response message null
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode()); // CBS RESPONSE
				// CODE SAVE
				// INTO THE
				// TRANS
				// TABLE
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.info("ending creditReversalAccount method with {} ", reqResp);
		return reqResp;

	}

	@Override
	public ReqResp creditReversalAccount(final ReqResp reqResp) {
		LOG.trace("Starting creditReversalAccount method");
		try {
			
			String poolAccountNo = null;
			String poolBranchCode = null;
			
			
			String branchCode = reqResp.getPayeeAcNum().substring(0, 3);
			//String poolAccountNo = Util.getProperty("POOL_ACCOUNT_BEN");
			//String poolBranchCode = poolAccountNo.substring(0, 3);
			String customerAccountNumber = reqResp.getPayeeAcNum();
			LOG.info("CustomerAccountNumber {} ", customerAccountNumber);
			String amount = reqResp.getPayeeAmount();
			amount = Util.convertAmountInPaisa(amount);
			LOG.info("Reversal amount in paisa is {} ", amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.FUND_TRANSFET_REVERSAL_MTI);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_REVERSAL_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			m.set(56, reqResp.getReversalInfo() + ISOMsgConstant.SUBCODE + ISOMsgConstant.BANK_CODE);
			
				if(reqResp.getPayeeAddr().endsWith("@cosb")) {
				poolAccountNo=BEN_TIMEPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			}
					
				
				else if(reqResp.getPayeeAddr().endsWith("@cosmos")) {
					poolAccountNo=BEN_SOPAY_ACC_POOL;
					poolBranchCode=poolAccountNo.substring(0, 3);
					m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
					+ Util.padRight(poolAccountNo, 19, ' '));
					m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
							+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
				}
				
			else {
				poolAccountNo=BEN_OTHER_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
				+ Util.padRight(poolAccountNo, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));;
			}
			
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
			+ Util.padRight(poolAccountNo, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-RC/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayeeAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getOrgTxnId()); // Add 13-july-2018 after discussing

			
			if(reqResp.getPayeeAddr().endsWith("@cosb")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"TIMEPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else if(reqResp.getPayeeAddr().endsWith("@cosmos")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"SOPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else {
				
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"OTHERS"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			Trans trans = new Trans();
			try {
				// trans.setAccNo(reqResp.getPayeeAcNum());
				// trans.setIfsc(reqResp.getPayeeIfsc());
				// trans.setOpration("CREDITREVERSAL");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setTxnTime(txnTime);
				// trans.setCrrn(reqResp.getField11());
				// trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				// transRepository.save(trans);
				trans.setAccNo(reqResp.getPayeeAcNum());
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDITREVERSAL");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getOrgTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections  at {} with {}  ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("After getting response from cbsConSocket at {} with response as {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.trace("ending creditReversalAccount method at {} with obj {} ", new Date(), reqResp);
		return reqResp;
	}


	//NRE CREDIT REVERSAL

	@Override
	public ReqResp creditReversalFIRAccount(final ReqResp reqResp) {
		LOG.trace("Starting creditReversalAccount method");
		try {
			String branchCode = reqResp.getPayeeAcNum().substring(0, 3);
			String poolAccountNo = Util.getProperty("POOL_ACCOUNT_BEN");
			String poolBranchCode = poolAccountNo.substring(0, 3);
			String customerAccountNumber = reqResp.getPayeeAcNum();
			LOG.debug("CustomerAccountNumber {} ", customerAccountNumber);
			String amount = reqResp.getPayeeAmount();
			amount = Util.convertAmountInPaisa(amount);
			LOG.debug("Reversal amount in paisa is {} ", amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.FUND_TRANSFET_REVERSAL_MTI);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_REVERSAL_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			m.set(56, reqResp.getReversalInfo() + ISOMsgConstant.SUBCODE + ISOMsgConstant.BANK_CODE);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
			+ Util.padRight(poolAccountNo, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-RC/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayeeAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getOrgTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			Trans trans = new Trans();
			try {
				// trans.setAccNo(reqResp.getPayeeAcNum());
				// trans.setIfsc(reqResp.getPayeeIfsc());
				// trans.setOpration("CREDITREVERSAL");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setTxnTime(txnTime);
				// trans.setCrrn(reqResp.getField11());
				// trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				// transRepository.save(trans);
				trans.setAccNo(reqResp.getPayeeAcNum());
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDITREVERSAL");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getOrgTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections  at {} with {}  ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("After getting response from cbsConSocket at {} with response as {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.trace("ending creditReversalAccount method at {} with obj {} ", new Date(), reqResp);
		return reqResp;
	}



	@Override
	public ReqResp debitAccount(final ReqResp reqResp) {
		try {
			
			String poolAccountNo = null;
			String poolBranchCode = null;
			Trans trans = new Trans();
			String amount = reqResp.getPayerAmount();
			String customerAccountNumber = reqResp.getPayerAcNum(); // 16-11-17
			// reqResp.getPayerAccNum()
			String branchCode = reqResp.getPayerAcNum().substring(0, 3); // 16-11-17
			// reqResp.getPayerAccNum()
			
			
			//String poolAccountNo = Util.getProperty("POOL_ACCOUNT_REM");
			//String poolBranchCode = poolAccountNo.substring(0, 3);
			amount = Util.convertAmountInPaisa(amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_TRANSFER_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			
			
			//NEW POOL ACCOUNT account and 127 field changes.
			if(reqResp.getPayerAddr().endsWith("@cosb")) {
				LOG.info("Its Time-pay TXN {}",reqResp.getTxnId());
				poolAccountNo=REM_TIMEPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			
			
			else if(reqResp.getPayerAddr().endsWith("@cosmos")) {
				LOG.info("Its Time-pay TXN {}",reqResp.getTxnId());
				poolAccountNo=REM_SOPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			else {
				LOG.info("Its SO-pay TXN {}",reqResp.getTxnId());
				poolAccountNo=REM_OTHER_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(customerAccountNumber, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-DR/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayeeAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			if(reqResp.getPayerAddr().endsWith("@cosb")) {
				String PayeeType=reqResp.getPayerCode().equals("0000")?"P2P":"P2M";
				m.set(127,reqResp.getPayerDeviceMobile()+"|"+"TIMEPAY"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			
			else if(reqResp.getPayerAddr().endsWith("@cosmos")) {
				String PayeeType=reqResp.getPayerCode().equals("0000")?"P2P":"P2M";
				m.set(127,reqResp.getPayerDeviceMobile()+"|"+"SOPAY"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			else {
				String PayeeType=reqResp.getPayerCode().equals("0000")?"P2P":"P2M";
				m.set(127,reqResp.getPayerDeviceMobile()+"|"+"OTHERS"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			try {
				trans.setAccNo(customerAccountNumber);
				trans.setIfsc(reqResp.getPayerIfsc());
				trans.setOpration("DEBIT");
				trans.setReq(new Date());
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setCrrn(reqResp.getField11());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount)); // Paise saved into the
				// DB
				trans.setTxnTime(txnTime);
				trans.setRefId(reqResp.getTxnRefId()); // 16-11-17
				trans.setRefURL(reqResp.getTxnRefUrl()); // 16-11-17
				trans.setTxnNote(reqResp.getTxnNote());
				trans.setMobileNumber(reqResp.getPayerDeviceMobile());// TODO PK
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				
				trans.setAccNo1(reqResp.getPayeeAcNum());//Payee Account No
				trans.setPayeeAcType(reqResp.getPayeeAcType());
				trans.setPayerAcType(reqResp.getPayerAcType());
				trans.setPayeeType(reqResp.getPayeeType());
				trans.setPayerType(reqResp.getPayerType());
				if (ConstantNew.MANDTAE_DEBIT.equalsIgnoreCase(reqResp.getSubOperation())) {
					trans.setSubOperation(reqResp.getSubOperation());//for mandate Debit 
				}
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections at {}  with req as {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Returned from cn=bs channel with response as {} at {}", isoMsgRes, new Date());
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setReqStr(new String(isoMsgRes.pack()));
				if (cbsResCode.equals(ISOMsgConstant.CBS_SUCCESS_RESPONSE)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					if (ISOMsgConstant.ERROR_CODE_CBS_119.equalsIgnoreCase(cbsResCode)) {
						String reasonText = isoMsgRes.getString(127);
						if (null != reasonText
								&& reasonText.toUpperCase().contains(ISOMsgConstant.ACCOUNT_TYPE_DORMANT)) {
							reqResp.setRespCode("D" + cbsResCode);
						} else {
							reqResp.setRespCode("F" + cbsResCode);
						}
					}
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.debug("Ending debitAccount method and returned with {}", reqResp);
		return reqResp;
	}

	@Override
	public ReqResp debitReversalAccount(final ReqResp reqResp) {
		try {
			
			String poolAccountNo = null;
			String poolBranchCode = null;
			String amount = reqResp.getPayerAmount();
			String customerAccountNumber = reqResp.getPayerAcNum(); // 16-11-17
			// reqResp.getPayerAccNum()
			String branchCode = reqResp.getPayerAcNum().substring(0, 3); // 16-11-17
			// reqResp.getPayerAccNum()
			//String poolAccountNo = Util.getProperty("POOL_ACCOUNT_REM");
			//String poolBranchCode = poolAccountNo.substring(0, 3);
			amount = Util.convertAmountInPaisa(amount);
			LOG.info("Reversal amount is as : {} ", amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.FUND_TRANSFET_REVERSAL_MTI);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16)); // wants to clearity
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_REVERSAL_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			m.set(56, reqResp.getReversalInfo() + ISOMsgConstant.SUBCODE + ISOMsgConstant.BANK_CODE);
			
			if(reqResp.getPayerAddr().endsWith("@cosb")) {
				poolAccountNo=REM_TIMEPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			
			else if(reqResp.getPayerAddr().endsWith("@cosmos")) {
				poolAccountNo=REM_SOPAY_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			else {
				poolAccountNo=REM_OTHER_ACC_POOL;
				poolBranchCode=poolAccountNo.substring(0, 3);
				m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
				+ Util.padRight(customerAccountNumber, 19, ' '));
				m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
						+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			}
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(customerAccountNumber, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(poolBranchCode, 8, ' ') + Util.padRight(poolAccountNo, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-RD/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayerAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getOrgTxnId()); // Add 13-july-2018 after discussing
			
			if(reqResp.getPayerAddr().endsWith("@cosb")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"TIMEPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			
			else if(reqResp.getPayerAddr().endsWith("@cosmos")) {
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"SOPAY"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			else {
				
				String PayeeType=reqResp.getPayeeCode().equals("0000")?"P2P":"P2M";
				m.set(127,"OTHERS"+"|"+PayeeType+"|"+reqResp.getTxnRefId()); 
			}
			Trans trans = new Trans();
			try {
				// trans.setAccNo(reqResp.getPayerAcNum());
				// trans.setIfsc(reqResp.getPayerIfsc());
				// trans.setOpration("DEBITREVERSAL");
				// trans.setReq(date);
				// trans.setReqStr(new String(m.pack()));
				// trans.setRrn(reqResp.getRrn());
				// trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				// trans.setTxnId(reqResp.getTxnId());
				// trans.setTxnTime(txnTime);
				// trans.setCrrn(reqResp.getField11());
				// trans.setAmount(Long.parseLong(amount));
				// trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				// transRepository.save(trans);

				trans.setAccNo(customerAccountNumber);
				trans.setIfsc(reqResp.getPayerIfsc());
				trans.setOpration("DEBITREVERSAL");
				trans.setReq(new Date());
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setCrrn(reqResp.getField11());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getOrgTxnId());
				trans.setAmount(Long.parseLong(amount)); // Paise saved into the
				// DB
				trans.setTxnTime(txnTime);
				trans.setRefId(reqResp.getTxnRefId()); // 16-11-17
				trans.setRefURL(reqResp.getTxnRefUrl()); // 16-11-17
				trans.setTxnNote(reqResp.getTxnNote());
				trans.setMobileNumber(reqResp.getPayerDeviceMobile());// TODO PK
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				
				if (ConstantNew.MANDTAE_REV.equalsIgnoreCase(reqResp.getSubOperation())) {
					trans.setSubOperation(reqResp.getSubOperation());
				}
				transRepository.save(trans);

			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.info("Initiating CBS req at {} with {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.info("Response from CBS received at {} with {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.debug("Ending debitReversalAccount method  with {} ", reqResp);
		return reqResp;
	}

	@Override
	public ReqResp fatchAccounts(final ReqResp reqResp, final String custId, final String custName,final String mobileNo ) {
		List<Account> list = new ArrayList<Account>();
		
		try {
			/*ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			Date date = new Date();
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.setHeader(ISOMsgConstant.ISO_HEADER.getBytes());
			m.set(2, custId);
			m.set(3, ISOMsgConstant.GET_ACC_LIST_PROCESSING_CODE);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(date));
			m.set(17, ISOUtility.getTransactionDate8(date));
			m.set(24, ISOMsgConstant.STATUS_CODE);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(49, ISOMsgConstant.DE_49);
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, ISOMsgConstant.DE_125 + Util.padLeft(custId, 9, ' ') + ISOMsgConstant.DE_ALL);
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			*///m.set(127, reqResp.getif);
			// with AZAD			
			Trans trans = new Trans();
			try {
				trans.setAccNo(reqResp.getLinkValue());
				trans.setOpration("LISTACCOUNT");
				//trans.setReq(date);
				//trans.setReqStr(new String(m.pack()));
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			// handle time out and other things
			LOG.info("Going for Connecting with the CBS Socket Connections at {} ", new Date());
			//ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);///send iso to cbs 
			
			ISOMsg isoMsgRes = new ISOMsg();
			isoMsgRes.set(125,"LP03 01030554ALL010204201930805 SBA+0000000003736617INR010200101416563 TDA+0000000001090100INR90620010205603  TDA+0000000003455900INR");
			//isoMsgRes.set(127,"COSB0000011");
			isoMsgRes.set(127,"COSB0000011");
			//isoMsgRes.set("");
			isoMsgRes.set(39,ISOMsgConstant.CBS_SUCCESS_RESPONSE);
			LOG.info("Returned from cbsConSocket at {} ", new Date());
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				LOG.info("Got CBS response {}", cbsResCode);
				reqResp.setRespCode(cbsResCode);
				//trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
					list = splitListAccountFromCBS(isoMsgRes.getString(125),isoMsgRes.getString(127),custName, mobileNo,reqResp);
					
					if (list == null || list.size() == 0) {
						trans.setStatus(ISOMsgConstant.STATUS_FAIL);
						reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
					} else {
						// 26/07/2017
						int count = 1;
						for (Account account : list) {
							if (count <= FETCH_IFSC_COUNT) { //FETCH IFSC COUNT
								account.setIfsc(getIfscCode(reqResp, account.getAccRefNumber()));
							} else {														
								account.setIfsc("COSB0000000");
							}
						}
						// IFSC
						reqResp.setAccounts(list);											
					}
				} else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans) ;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) { 
			LOG.error(e.getMessage(), e);
		}
		return reqResp;
	}

	@Override
	public String getIfscCode(ReqResp reqResp, final String custAccNo) {
		String ifscCode = null;
		try {
			String branchCode = custAccNo.substring(0, 3);
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			Date date = new Date();
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.setHeader(ISOMsgConstant.ISO_HEADER.getBytes());
			m.set(2, ISOMsgConstant.DE_2);
			m.set(3, ISOMsgConstant.DE_3);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(date));
			m.set(17, ISOUtility.getTransactionDate8(date));
			m.set(24, ISOMsgConstant.STATUS_CODE);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(custAccNo, 17, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			// with AZAD
			LOG.debug("Going for Connecting with the CBS Socket Connections at {} with {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Return success from cbsConSocket  at {} with {} ", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					ifscCode = splitIfsc(isoMsgRes.getString(126));
				} else {
					ifscCode = "COSB0000000";
				}
			}else {
				ifscCode = "COSB0000000";
			}
		} catch (Exception e) {
			ifscCode = "COSB0000000";
			LOG.error(e.getMessage(), e);
		}
		return ifscCode;

	}
	
	/* * public static void main(String[] args) { String str=
	 * "090~UNIVERSITY ROAD BRANCH,PUNE~~~          2082.66~COSB0000090~~29-12-2017"
	 * ; String []ifsc=str.split("~"); System.out.println(ifsc[5]); }
	 */

	private List<String> parseCBSResponseListAccount(final String tempAccountString, final String custName,
			final String mobileNo) {
		List<String> data = new ArrayList<String>();
		List<String> tempAccountList = new ArrayList<String>();
		String noOfAccounts = null;
		String remaningStr = null;
		String tempAccStr = null;
		if (null != tempAccountString && null != custName && null != mobileNo) {
			noOfAccounts = tempAccountString.substring(2, 4);
			remaningStr = tempAccountString.substring(16, tempAccountString.length());
			for (int i = 0; i < remaningStr.length(); i = i + 39) {
				tempAccountList.add(remaningStr.substring(i, i + 39));
			}
			tempAccStr = custName + "~" + mobileNo + "~N~" + noOfAccounts;
			data.add(tempAccStr);
			for (String str : tempAccountList) {
				String accountString = null;
				if (str.contains("SBA")) {
					accountString = str.substring(0, 16) + "~SBA";
					data.add(accountString);
				}
				if (str.contains("CAA")) {
					accountString = str.substring(0, 16) + "~CAA";
					data.add(accountString);
				}
				if (str.contains("ODA")) {
					accountString = str.substring(0, 16) + "~ODA";
					data.add(accountString);
				}
			}
		}
		LOG.info("Return success from parseCBSResponseListAccount with response as as {}", data);
		return data;
	}

	
	//START///Parse CBS response For List  IFSC Account
		private List<String> parseCBSResponseListIfscCode(final String tempIfscCodeString) {
			List<String> data = new ArrayList<String>();
			String remaningStr = null;
			//New for IFSC Code
			
			String  noOfIfscCode=null;
			
			int j=0;
			if (null != tempIfscCodeString ) {
				noOfIfscCode = tempIfscCodeString;				
				String[] ifscCodeArr = noOfIfscCode.split("\\+");
				ArrayList<String> itemList = new ArrayList<String>(Arrays.asList(ifscCodeArr));
				data.addAll(itemList);
				
				
			}
			LOG.info("Return success from parseCBSResponseListIfscCode with response as as {}", data);
			return data;
		}

		//END/// 

	
	
	private String splitIfsc(final String tempString) {
		String[] str = tempString.split("~");
		return str[5];
	}
	///New from AZAD
	private List<Account> splitListAccountFromCBS(final String tempAccountString, final String ifscCode,
			final String custName, final String mobileNo, ReqResp reqResp) {
		LOG.info("Starting from split List Account From CBS with tempAccountString as {}, custName as {} and mob no as {} ",tempAccountString, custName, mobileNo);
		LOG.info("value of isUpi {} ", reqResp.getIsUPI2());
		List<String> data = parseCBSResponseListAccount(tempAccountString, custName, mobileNo);
		List<String> ifsclist = parseCBSResponseListIfscCode(ifscCode);
		String[] commonFields = data.get(0).split("~");
		List<Account> list = new ArrayList<>();
		String accountName;
		String aadhaar;
		String DEFAULT_IFSC	 = "COSB0000000";
		String mobileNumber;
		int i;
		int noOfAccount;
		if (commonFields.length >= 4) {
			accountName = commonFields[0];
			mobileNumber = commonFields[1];
			aadhaar = commonFields[2];
			noOfAccount = Integer.parseInt(commonFields[3].trim());
			if (noOfAccount > 0) {
				for (int count = 1; count < data.size(); count++) {
					try {
						String[] fields = data.get(count).split("~");
						Account account = new Account();
						account.setAccRefNumber(fields[0].trim());
						account.setMaskedAccnumber(maskedAccountNumber(fields[0].trim()));
//String accountVerification(fields[0].trim());
						if ("SBA".equalsIgnoreCase(fields[1])) {
							account.setAccType("SAVINGS");
						} else if ("CAA".equalsIgnoreCase(fields[1])) {
							account.setAccType("CURRENT");
						}
					    if ("1".equalsIgnoreCase(reqResp.getIsUPI2())) {
							LOG.info("Going to set AccType for 2.0 is {}", reqResp.getIsUPI2());
							account.setAccType(accountVerificationForListAccount(reqResp, account.getAccRefNumber()));
						}
					    String ifsc = ifsclist.get(count);
					    if(ifsc==null || "".equalsIgnoreCase(ifsc)) {
					 //   	ifsc = "COSB0000012";
					    	ifsc=DEFAULT_IFSC;
					    }
					    account.setIfsc(ifsc);
					    account.setName(accountName);
					    account.setAadhaar(aadhaar);
					    account.setMobNumber(mobileNumber);
						if(account.getAccType()==null || "".equalsIgnoreCase(account.getAccType())) {
							continue;
						}
					    list.add(account);
					   // Log.info("list is {} "+list.toString());
					}catch (Exception e)
					{ 
						LOG.error(e.getMessage(), e); 
			    	}
				}
			 }
			}
		  return list; 
		}

	/*private List<Account> splitListAccountFromCBS(final String tempAccountString,final String ifscCode, final String custName,
			final String mobileNo,ReqResp reqResp) {
		LOG.info(
				"Starting from split List Account From CBS with tempAccountString as {}, custName as {} and mob no as {} ",
				tempAccountString, custName, mobileNo);
		LOG.info("value of isUpi {} ",reqResp.getIsUPI2());
		List<String> data = parseCBSResponseListAccount(tempAccountString, custName, mobileNo);
		List<String> ifsclist = parseCBSResponseListIfscCode(ifscCode);
		String[] commonFields = data.get(0).split("~");
		List<Account> list = new ArrayList<>();
		String accountName;
		String aadhaar;
		String mobileNumber;
		int i;
		int noOfAccount;
		if (commonFields.length >= 4) {
			accountName = commonFields[0];
			mobileNumber = commonFields[1];
			aadhaar = commonFields[2];
			noOfAccount = Integer.parseInt(commonFields[3].trim());
			if (noOfAccount > 0) {
				for (int count = 1; count < data.size(); count++) {
					try {
						String[] fields = data.get(count).split("~");
						HashMap<String, String> map = new HashMap();
						Account account = new Account();
						account.setAccRefNumber(fields[0].trim());
						account.setMaskedAccnumber(maskedAccountNumber(fields[0].trim()));
						//String accountVerification(fields[0].trim());
						if ("SBA".equalsIgnoreCase(fields[1])) {
							account.setAccType("SAVINGS");
						}else if ("CAA".equalsIgnoreCase(fields[1])) {
							account.setAccType("CURRENT");
						}
					    if ("1".equalsIgnoreCase(reqResp.getIsUPI2())) {
							LOG.info("Going to set AccType for 2.0 is {}",reqResp.getIsUPI2());
								account.setAccType(accountVerificationForListAccount(reqResp,account.getAccRefNumber()));
						}
					    //new for ifsc 
					   
							List<String> tempAccountList = new ArrayList<String>();
							String noOfAccounts = null;
							String remaningStr = null;
							
							if (null != tempAccountString && null != custName && null != mobileNo) 
							{
								noOfAccounts = tempAccountString.substring(2, 4);
								remaningStr = tempAccountString.substring(16, tempAccountString.length());
								for (i = 0; i < remaningStr.length(); i = i + 39) {
									tempAccountList.add(remaningStr.substring(i, i + 39));
								}
								
								int ifcslistsize=ifsclist.size();
								int tempAccountListsize=tempAccountList.size();
								if(ifcslistsize==tempAccountListsize)
								{
									for(int k=0;k<tempAccountListsize;k++)
									{
										Account accountDeatils=new Account();
										String accountString=tempAccountList.get(k);
										String ifsclistvar_=ifsclist.get(k);
										accountDeatils.setAccRefNumber(accountString);
										accountDeatils.setIfsc(ifsclistvar_);
										accountDeatils.setName(accountName);
										accountDeatils.setAadhaar(aadhaar);
										accountDeatils.setMobNumber(mobileNumber);
										list.add(accountDeatils);
												
									}
									System.out.println("zzzzzzzzzzz"+list);
									return list;
									
								}
								
								else
								{
									String ifsclistvar_="";
									int p=0;
									for(int k=0;k<tempAccountListsize;k++)
									{
										p++;
										if(ifcslistsize>=p) 
										{
											ifsclistvar_=ifsclist.get(k);	
										}
										
										else {
											ifsclistvar_="COSB0000000";
										}
																				

										Account accountDeatils=new Account();
										String accountString=tempAccountList.get(k);
										
										accountDeatils.setAccRefNumber(accountString);
										accountDeatils.setIfsc(ifsclistvar_);
										accountDeatils.setName(accountName);
										accountDeatils.setAadhaar(aadhaar);
										accountDeatils.setMobNumber(mobileNumber);
										list.add(accountDeatils);
										
									}
									System.out.println("zzzzzzzzzzz"+list);
									return list;
									
								}
							 							
				
							
					    }
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
		}*/
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxx"+list.toString());
		//return list;
	//}

	
	@Override
	public ReqResp accountVerification(ReqResp reqResp) {
		LOG.trace("Starting accountVerification from tempIsoMessageFromCBS:{} ",reqResp.toString());
		String custAccNo=reqResp.getPayeeAcNum();
		LOG.debug("accountVerification   {}", custAccNo);
		String branchCode = custAccNo.substring(0, 3);
		try {
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			Date date = new Date();
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.setHeader(ISOMsgConstant.ISO_HEADER.getBytes());
			m.set(2, ISOMsgConstant.DE_2);
			m.set(3, ISOMsgConstant.DE_4);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(date));
			m.set(17, ISOUtility.getTransactionDate8(date));
			m.set(24, ISOMsgConstant.STATUS_CODE);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(custAccNo, 17, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, ISOMsgConstant.BAL_DE_125);
			Trans trans = new Trans();
			try {
				trans.setAccNo(reqResp.getPayeeAcNum());
				trans.setOpration("ACCOUNTVERIFICATION");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				//transRepository.save(trans);
			} catch (Exception e) {
				LOG.error("Exception caught at verifyAccounts : " + e);
				e.printStackTrace();
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections at {}  with req as {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Returned from cn=bs channel with response as {} at {}", isoMsgRes, new Date());
			if (isoMsgRes != null) {
				LOG.debug("Request for verify accno Inside isoMsgRes != null ");
				String cbsResCode = isoMsgRes.getString(39);
				LOG.debug("CBS RESPONSE CODE {}",cbsResCode);
				String accountAndStatus = isoMsgRes.getString(125);
				LOG.debug("Getting data in 125 field {}",accountAndStatus);

				trans.setRespStr(new String(isoMsgRes.pack()));
				if (null != accountAndStatus) {
					reqResp.setRespCode(cbsResCode);
					LOG.debug("Inside IF Block");
					if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
						LOG.debug("Inside If Block ISOMsgConstant.CBS_SUCCESS_RESPONSE");
						trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
						List<Account> list = splitAccountStatus(accountAndStatus);
						reqResp.setAccounts(list);
					}else {
						trans.setStatus(ISOMsgConstant.STATUS_FAIL);
					}
				}else {
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
					reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
				}

			}else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				//transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.debug("Ending Account verification method and returned with {}", reqResp);
		return reqResp;
	}



	@Override
	public String  accountVerificationForListAccount(ReqResp reqResp, final String custAccNum) {
		LOG.trace("Starting accountVerification for list account from tempIsoMessageFromCBS: {} " , reqResp.toString());
		String custAccNo=custAccNum;
		LOG.info("Acc No "+custAccNo);
		String branchCode = custAccNo.substring(0, 3);
		String accType = null;
		try {
			ISOMsg m = new ISOMsg();
			m.setPackager(new CBSPackager());
			Date date = new Date();
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.setHeader(ISOMsgConstant.ISO_HEADER.getBytes());
			m.set(2, ISOMsgConstant.DE_2);
			m.set(3, ISOMsgConstant.DE_4);
			m.set(11, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(12, ISOUtility.getTransactionDateTime14(date));
			m.set(17, ISOUtility.getTransactionDate8(date));
			m.set(24, ISOMsgConstant.STATUS_CODE);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(branchCode, 8, ' ')
			+ Util.padRight(custAccNo, 17, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, ISOMsgConstant.BAL_DE_125);

			LOG.debug("Going for Connecting with the CBS Socket Connections at {}  with req as {} ", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("Returned from cn=bs channel with response as {} at {}", isoMsgRes, new Date());
			if (isoMsgRes != null) {
				LOG.debug("Request for verify accno Inside isoMsgRes != null ");
				String cbsResCode = isoMsgRes.getString(39);
				LOG.debug("CBS RESPONSE CODE {}",cbsResCode);
				String accountAndStatus = isoMsgRes.getString(125);
				LOG.debug("Getting data in 125 field {}",accountAndStatus);

				if (null != accountAndStatus) {
					reqResp.setRespCode(cbsResCode);
					LOG.debug("Inside IF Block");
					if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
						LOG.debug("Inside If Block ISOMsgConstant.CBS_SUCCESS_RESPONSE");
						accType = splitAccountStatusListAccount(accountAndStatus);

					}
				}
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.debug("Ending Account verification method and returned with {}", reqResp);
		return accType;
	}


	private List<Account> splitAccountStatus(final String tempAccountString) {
		String accStatus=tempAccountString.substring(19, 21);
		String accName=tempAccountString.substring(45, 90);
		String accType = tempAccountString.substring(0, 3);
		String accTypesNreNroOD = tempAccountString.substring(6, 11);
		String accTypesSB = tempAccountString.substring(8, 11);
		List<Account> list = new ArrayList<>();
		try {
			Account account = new Account();
			if (ConstantNew.ACC_TYPE_CAA.equalsIgnoreCase(accType)) {
				account.setAccType(ConstantNew.ACC_TYPE_CURRENT);
			}
			if (ConstantNew.ACC_TYPE_SBA.equalsIgnoreCase(accType)) {
				 if (ConstantNew.ACC_TYPE_SBNRE.equalsIgnoreCase(accTypesNreNroOD)) {
					 account.setAccType(ConstantNew.ACC_TYPE_NRE);
				}else if (ConstantNew.ACC_TYPE_SBNRO.equalsIgnoreCase(accTypesNreNroOD)) {
					account.setAccType(ConstantNew.ACC_TYPE_NRO);
				}else {
					account.setAccType(ConstantNew.ACC_TYPE_SAVINGS);
				}
			}
			if (ConstantNew.ACC_TYPE_ODA.equalsIgnoreCase(accType)) {
				account.setAccType(ConstantNew.ACC_TYPE_UOD);
			}
			account.setName(accName.trim());
			account.setAccStatus(accStatus);
			list.add(account);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return list;
	}

	private String splitAccountStatusListAccount(final String tempAccountString) {
		String accType = tempAccountString.substring(0, 3);
		String accTypesNreNroOD = tempAccountString.substring(6, 11);
		//String accTypesSB = tempAccountString.substring(8, 11);
		String accountType = null;
		try {
			if (ConstantNew.ACC_TYPE_CAA.equalsIgnoreCase(accType)) {
				accountType = ConstantNew.ACC_TYPE_CURRENT;
			}
			if (ConstantNew.ACC_TYPE_SBA.equalsIgnoreCase(accType)) {
				 if (ConstantNew.ACC_TYPE_SBNRE.equalsIgnoreCase(accTypesNreNroOD)) {
					accountType = ConstantNew.ACC_TYPE_NRE;
				}else if (ConstantNew.ACC_TYPE_SBNRO.equalsIgnoreCase(accTypesNreNroOD)) {
					accountType = ConstantNew.ACC_TYPE_NRO;
				}else {
					accountType = ConstantNew.ACC_TYPE_SAVINGS;
				}
			}
			if (ConstantNew.ACC_TYPE_ODA.equalsIgnoreCase(accType)) {
				accountType = ConstantNew.ACC_TYPE_UOD;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.info("Return Account Type Is   "+ accountType);
		return accountType;
	}

	  private  String firDetails(String  data) {
		    String mystringarr[] =data.split("\\/");
			String data1=mystringarr[6];
			String mystringarr1[] =data1.split("\\-");
			String addressCity = mystringarr1[1];
			String addressCountry = mystringarr1[2];
			String result=mystringarr[0]+"/"+mystringarr[5]+"/"+addressCity+"/"+addressCountry;
		  return result;
	  }
	
	  
	  private static String firDetails1(String  data) {
		  StringTokenizer token = new StringTokenizer(data, "\\/");
		  StringBuilder sbFIR = new StringBuilder("");
		  int count=0;
		  while(token.hasMoreTokens()){
			  sbFIR.append(token.nextToken());
			  count++;
			  if(count==0 || count==5 || count ==6){
				  sbFIR.append("/");
			  }
		  }
		  System.out.println(sbFIR);
		  return sbFIR.toString();
	  }
/*	@Override
	public ReqResp creditAccountFir(final ReqResp reqResp) {
		String amount = reqResp.getPayeeAmount();
		try {
			amount = Util.convertAmountInPaisa(amount);
			Date date = new Date();
			String txnTime = ISOUtility.getTransactionDateTime14(date);
			String txnDate = ISOUtility.getTransactionDate8(date);
			String customerAccountNumber = reqResp.getPayeeAcNum();
			String branchCode = reqResp.getPayeeAcNum().substring(0, 3);
			String poolAccountNo = Util.getProperty("POOL_ACCOUNT_BEN");
			String poolBranchCode = poolAccountNo.substring(0, 3);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ISO93APackager());
			m.setMTI(ISOMsgConstant.MTI_FIN_VALUE);
			m.set(2, ISOMsgConstant.FUND_TRANSFER_DE_2);
			m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
			m.set(4, ISOUtility.padZeros(amount, 16));
			m.set(11, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(12, txnTime);
			m.set(17, txnDate);
			m.set(24, ISOMsgConstant.FUND_TRANSFER_DE_24);
			m.set(32, ISOMsgConstant.DE_32);
			m.set(37, ISOUtility.padZeros(reqResp.getField11(), 12));
			m.set(46, ISOMsgConstant.FUND_TRANSFER_DE_46);
			m.set(49, ISOMsgConstant.FUND_TRANSFER_DE_49);
			m.set(102, Util.padRight(ISOMsgConstant.BRANCH_CODE, 11, ' ') + Util.padRight(poolBranchCode, 8, ' ')
			+ Util.padRight(poolAccountNo, 19, ' '));
			m.set(103, Util.padRight(Util.padLeft(ISOMsgConstant.BRANCH_CODE, 8, ' '), 13, ' ')
					+ Util.padRight(branchCode, 8, ' ') + Util.padRight(customerAccountNumber, 19, ' '));
			m.set(123, ISOMsgConstant.DE_123);
			m.set(125, "UPI-CR/" + Util.getD125_With6(reqResp.getRrn(), reqResp.getPayerAddr(), reqResp.getTxnNote()));
			m.set(126, reqResp.getTxnId()); // Add 13-july-2018 after discussing
			m.set(127, reqResp.getFirInfo());								// with AZAD
			Trans trans = new Trans();
			try {
				trans.setAccNo(customerAccountNumber);
				trans.setIfsc(reqResp.getPayeeIfsc());
				trans.setOpration("CREDIT");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				trans.setAmount(Long.parseLong(amount));
				trans.setTxnTime(txnTime);
				trans.setCrrn(reqResp.getField11());
				trans.setRefId(reqResp.getTxnRefId());
				trans.setRefURL(reqResp.getTxnRefUrl());
				trans.setMobileNumber(reqResp.getPayeeDeviceMobile());
				trans.setPoolAccount(poolAccountNo); // POOL ACCOUNT
				trans.setSubType(ISOMsgConstant.FIR_TYPE);
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			LOG.debug("Going for Connecting with the CBS Socket Connections  at {} with request {}", new Date(), m);
			ISOMsg isoMsgRes = cbsConSocket.sendChannel(m);
			LOG.debug("After Back from CBS Con Socket at {} with response as {}", new Date(), isoMsgRes);
			if (isoMsgRes != null) {
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));

				if (ISOMsgConstant.CBS_SUCCESS_RESPONSE.equals(cbsResCode)) {
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				} else {
					if (ISOMsgConstant.ERROR_CODE_CBS_119.equalsIgnoreCase(cbsResCode)) {
						String reasonText = isoMsgRes.getString(127);
						if (null != reasonText
								&& reasonText.toUpperCase().contains(ISOMsgConstant.ACCOUNT_TYPE_DORMANT)) {
							reqResp.setRespCode("D" + cbsResCode);
						} else {
							reqResp.setRespCode("F" + cbsResCode);
						}
					}
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			} else {
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try {
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			reqResp.setCbsErrorCode(reqResp.getRespCode());
		}
		LOG.info("Response from creditAccount method is as {} ", reqResp.getRespCode());
		return reqResp;
	}
*/
/*
	 public static void main(String[] args) {
	 ReqResp reqResp = new ReqResp();
	 String str="ODAINRODNRE20151027A        +0000000000209903KEDARI VINAYAK FAKIRAPPA                                                                                                +0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000209903+0000000000000000+0000000000209903"; 
	  String accStatus=str.substring(19, 21);
	  String accName=str.substring(45, 90);
	  String accType = str.substring(0, 3);
	  String accTypes = str.substring(8, 11);
	  String accTypesNreNroOD = str.substring(6, 11);
		String accTypesSB = str.substring(8, 11);
	  System.out.println("AccountTye NRE:::::"+accTypes);
	  System.out.println("Accout status is"+accStatus);
	  System.out.println("Name"+accName.trim());
	  System.out.println("Type"+accType);
	  List<Account> list = new ArrayList<>();
	  Account account = new Account();
					account.setName(accName.trim());

					if ("CAA".equalsIgnoreCase(accType)) {
						LOG.info("Inside SBA BLOCK");
						account.setAccType("CURRENT");
					}
					if ("SBA".equalsIgnoreCase(accType)) {
						 if ("SBNRE".equalsIgnoreCase(accTypesNreNroOD)) {
							 account.setAccType("NRE");
						}else if ("SBNRO".equalsIgnoreCase(accTypesNreNroOD)) {
							account.setAccType("NRO");
						}else {
							account.setAccType("SAVINGS");
						}
					}
					if ("ODA".equalsIgnoreCase(accType)) {
						if ("NRE".equalsIgnoreCase(accTypesSB)) {
							account.setAccType("NRE");
						}else if ("NRO".equalsIgnoreCase(accTypesSB)) {
							account.setAccType("NRO");
						}else {
							account.setAccType("OD");
						}
					}
					account.setAccStatus(accStatus);
					LOG.debug("Account details {}",account.toString());
					list.add(account);
					reqResp.setAccounts(list);
					
}
*/	 private ISOMsg tempIsoMessageFromCBS() {
			LOG.trace("Starting from tempIsoMessageFromCBS:");
			ISOMsg m = null;
			try {
				org.jpos.util.Logger logger = new org.jpos.util.Logger();
				logger.addListener(new SimpleLogListener(System.out));
				CBSPackager packager = new CBSPackager();
				NACChannel channel = new NACChannel();
				channel.setPackager(packager);
				((LogSource) channel).setLogger(logger, "test-channel");
				m = new ISOMsg();
				m.setPackager(packager);
				m.setDirection(ISOMsg.INCOMING);
				m.setHeader("49534F303135303030303135".getBytes());
				Date date = new Date();
				m.setMTI("1210");
				m.set(2, "0902616");
				m.set(3, "370000");
				m.set(11, "000000454253");
				m.set(12, "20170817113236");
				m.set(17, "20170928");
				m.set(32, "627611");
				m.set(38, "UNI000");
				m.set(39, "000");
				m.set(48,"+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000                 ");
				m.set(49, "356");
				m.set(123, "ITB");
				// m.set(124, "TAB");
				// m.set(125, "LP11 0428766ALL0421501017240
				// SBA+0000000001000000INR0421501017259
				// TDA+0000000001000000INR0421501017268
				// SBA+0000000001000000INR0421501017231
				// SBA+0000000001000000INR0421501017277
				// TDA+0000000001000000INR0421501017222
				// TDA+0000000001000000INR0421501017286
				// TDA+0000000001000000INR0421501017541
				// SBA+0000000010000000INR04265010806
				// ODA+0000000000000000INR0421501017295
				// TDA+0000000001000000INR0420501034609 SBA+0000000000416500INR");
				m.set(125,
						"LP02  0428766ALL0900501016560   SBA+0000000001000000INR0901501017259   SBA+0000000001000000INR");
				// m.set(125, "LP00 0428766ALL");
				m.set(127,"ATMIN~8149742212~8149742212~8149742212~vinayakkedari14@gmail.com~14-09-1991 00:00:00~CMRPK8918G~*");
				byte[] data = m.pack();
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			return m;
		}
	
    


	/*public static void main(String[] args) {
		String balanceString="+0000000000211764+0000000000211764+0000000000000000+0000000000500000+0000000000211764INR              ";
		//String s ="FIR/PAN-SDF45645BJB20/Shyam/MTO/MTSS/822217098761/London-London-UK/134.07273.987.8253072";
		String f=balanceString.substring(0,16).startsWith("+")?"C000"+balanceString.substring(1,17):"D000"+balanceString.substring(1,17);
		// s1=firDetails1(s);
		System.out.println(f);
		System.out.println(balanceString.substring(0,17));
		String mystringarr[] =s.split("\\/");
		String s1=mystringarr[6];
		String mystringarr1[] =s1.split("\\-");
		String addressCity = mystringarr1[1];
		String addressCountry = mystringarr1[2];
		String result=mystringarr[0]+"/"+mystringarr[5]+"/"+addressCity+"/"+addressCountry;
		//System.out.println(result);
	}
*/
}