package com.npst.mobileservice.service;

import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.AcqTxnLimitDao;
import com.npst.mobileservice.object.Message;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.RabbitMQSend;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.AcqTxnLimitEntity;

public class AcqTxnLimitService {
	private static final Logger log = Logger.getLogger(AcqTxnLimitService.class);

	private static int newUserAmtLimit = StringUtils.isNotBlank(Util.getProperty("NEW_USER_LIMIT"))
			? Integer.parseInt(Util.getProperty("NEW_USER_LIMIT"))
			: 5000;

	private static int newUserGreenHH = StringUtils.isNotBlank(Util.getProperty("NEW_USER_GREEN_HH"))
			? Integer.parseInt(Util.getProperty("NEW_USER_GREEN_HH"))
			: 24;

	private static int payMaxCount = StringUtils.isNotBlank(Util.getProperty("PAY_MAX_COUNT"))
			? Integer.parseInt(Util.getProperty("PAY_MAX_COUNT"))
			: 10;
	private static int collectMaxCount = StringUtils.isNotBlank(Util.getProperty("COLLECT_MAX_COUNT"))
			? Integer.parseInt(Util.getProperty("COLLECT_MAX_COUNT"))
			: 5;

	private static int totalMaxCount = StringUtils.isNotBlank(Util.getProperty("TOTAL_MAX_COUNT"))
			? Integer.parseInt(Util.getProperty("TOTAL_MAX_COUNT"))
			: 10;

	private static String newUserUnderGHHError = StringUtils.isNotBlank(Util.getProperty("NEW_USER_UNDER_GHH_ERROR"))
			? Util.getProperty("NEW_USER_UNDER_GHH_ERROR")
			: "XB";

	private static String payCountError = StringUtils.isNotBlank(Util.getProperty("PAY_COUNT_ERROR"))
			? Util.getProperty("PAY_COUNT_ERROR")
			: "XB";

	private static String collectCountError = StringUtils.isNotBlank(Util.getProperty("COLLECT_COUNT_ERROR"))
			? Util.getProperty("COLLECT_COUNT_ERROR")
			: "XB";

	private static String newUserPayAmountError = StringUtils.isNotBlank(Util.getProperty("NEW_USER_AMOUNT_ERROR"))
			? Util.getProperty("NEW_USER_AMOUNT_ERROR")
			: "XB";

	private static boolean isEnable = "YES".equalsIgnoreCase(Util.getProperty("IS_ENABLE_ACQ_LIMIT_CHK"));
	private final static long msToHour = 60 * 60 * 1000;
	private static final int NEW_USER_TYPE = 0;
	private static final int NORMAL_USER_TYPE = 1;
	private  static final String MESSAGE_TYPE_SMS = "MESSAGE_TYPE_SMS";
	private  static String SMS_QUEUE=Util.getProperty("SMS_QUEUE");
	
	public static String COLLECT_LIMIT_MSG = StringUtils.isBlank(Util.getProperty("COLLECT_LIMIT_MSG"))?"XB":Util.getProperty("COLLECT_LIMIT_MSG");
	public static int COLLECT_LIMIT_PER_TXN =StringUtils.isBlank(Util.getProperty("COLLECT_LIMIT_PER_TXN"))?500000:Integer.parseInt(Util.getProperty("COLLECT_LIMIT_PER_TXN"));
	
	private static AcqTxnLimitService acqTxnLimitService = new AcqTxnLimitService();

	private AcqTxnLimitDao acqTxnLimitDao = AcqTxnLimitDao.getInstance();

	private AcqTxnLimitService() {

	}

	public static AcqTxnLimitService getInstance() {
		return acqTxnLimitService;
	}

	public String checkAcqLimit(String mobileNo, int amountPaisa, String txnFlag, String txnType, String txnId) {
		String respCode = "XB";
		
		log.info("FROM checkAcqLimit method START{}"+respCode);
		try {
			if (!isEnable) {
				return ConstantI.MSGID_SUCCESS;
			}
			if ("PAY".equalsIgnoreCase(txnType)) {
				// TODO discussion
				respCode = checkForPay(mobileNo, amountPaisa, txnId);
			} else if ("COLLECT".equalsIgnoreCase(txnType)) {
				// TODO discussion
				if ("PAYCOLLECT".equalsIgnoreCase(txnFlag)) {
					respCode = checkForCollect(mobileNo, txnId,amountPaisa);
				} else if ("COLLECTACCEPT".equalsIgnoreCase(txnFlag)) {
					respCode = checkForPay(mobileNo, amountPaisa, txnId);
				} else {
					log.error("No match found for txnFlag={} ,ending success"+ txnFlag);
					return ConstantI.MSGID_SUCCESS;
				}
			} else {
				log.error("No match found for txnType={} ,sending success"+ txnType);
				return ConstantI.MSGID_SUCCESS;
			}
		} catch (Exception e) {
			log.info("error in checkAcqLimit {}", e);
		}
		log.info("FROM checkAcqLimit method {}"+respCode);
		return respCode;
	}

	public boolean saveNewUser(String mobileNo) {
		boolean f = false;
		try {
			log.info("inside saveNewUser for mobileNo={}"+mobileNo);
			AcqTxnLimitEntity ob = new AcqTxnLimitEntity();
			ob.setMobileNo(get12DigitMobNum(mobileNo));
			ob.setCreateDate(new Date());
			ob.setUserType(NEW_USER_TYPE);
			acqTxnLimitDao.saveUpdate(ob);
			f = true;
		} catch (Exception e) {
			log.info("error {}", e);
		}
		return f;
	}

	public boolean saveNewUser(long mobileNo) {
		boolean f = false;
		try {
			AcqTxnLimitEntity ob = new AcqTxnLimitEntity();
			ob.setMobileNo(mobileNo);
			ob.setCreateDate(new Date());
			acqTxnLimitDao.saveUpdate(ob);
			f = true;
		} catch (Exception e) {
			log.info("error {}", e);
		}
		return f;
	}

	public boolean saveUpdate(AcqTxnLimitEntity ob) {
		boolean f = false;
		try {
			acqTxnLimitDao.saveUpdate(ob);
			f = true;
		} catch (Exception e) {
			log.info("error {}", e);
		}
		return f;
	}

	private String checkForPay(String mobileNo, int amountInPaisa, String txnId) {
		String respCode = "XB";
		AcqTxnLimitEntity ob = getByMobileNumber(mobileNo);
		if (ob == null) {
			ob = new AcqTxnLimitEntity();
			ob.setCreateDate(new Date());
			ob.setMobileNo(get12DigitMobNum(mobileNo));
			ob.setUserType(NORMAL_USER_TYPE);
			ob.setFirstPaySTxnDate(ob.getCreateDate());
		}
		String strCurrentDate = LocalDate.now().toString();
		int uType = ob.getUserType();
		// TODO and if first txn success=> status==success
		if (ob.getPayCount() >= 1 && NEW_USER_TYPE == uType) {
			if (ob.getFirstPaySTxnDate() == null) {
				return newUserUnderGHHError;
			}
			int HH = (int) ((System.currentTimeMillis() - ob.getFirstPaySTxnDate().getTime()) / msToHour);
			if (HH >= newUserGreenHH) {
				acqTxnLimitDao.updateUserType(mobileNo, NORMAL_USER_TYPE);
				uType = NORMAL_USER_TYPE;
			} else {
				return newUserUnderGHHError;
			}
		}

		if (strCurrentDate.equals(ob.getDateStr())) {
			if (NEW_USER_TYPE == uType) {
				if (amountInPaisa <= newUserAmtLimit) {
					if (ob.getFirstPaySTxnDate() == null) {
						acqTxnLimitDao.updatePayCountAndFirstTxnDate(mobileNo, txnId);
					} else {
						acqTxnLimitDao.updatePayCount(mobileNo, txnId);
					}
					sendMsg(mobileNo);
					respCode = ConstantI.MSGID_SUCCESS;
				} else {
					respCode = newUserPayAmountError;
				}
			} else {
				if (ob.getCollectCount() < payMaxCount && ob.getCountPayAndCollect() < totalMaxCount) {
					acqTxnLimitDao.updatePayCount(mobileNo, txnId);
					respCode = ConstantI.MSGID_SUCCESS;
				} else {
					respCode = payCountError;
				}
			}
		} else {
			ob.setCollectCount(0);
			ob.setIdPk(0);
			ob.setPayCount(1);
			ob.setTxnId(txnId);
			ob.setStatus(null);
			if (NEW_USER_TYPE == uType) {
				if (amountInPaisa <= newUserAmtLimit) {
					respCode = ConstantI.MSGID_SUCCESS;
					if (ob.getFirstPaySTxnDate() == null) {
						ob.setFirstPaySTxnDate(new Date());
					}
				} else {
					ob.setPayCount(0);
					respCode = newUserPayAmountError;
				}
			} else {
				ob.setUserType(uType);
				respCode = ConstantI.MSGID_SUCCESS;
			}
			ob.setDateStr(strCurrentDate);
			acqTxnLimitDao.saveUpdate(ob);
		}
		return respCode;
	}

	private String checkForCollect(String mobileNo, String txnId,int amountPaisa) {
		String respCode = "XB";
		if (amountPaisa > COLLECT_LIMIT_PER_TXN) {
			return COLLECT_LIMIT_MSG;
		}
		AcqTxnLimitEntity ob = getByMobileNumber(mobileNo);
		if (ob == null) {
			ob = new AcqTxnLimitEntity();
			ob.setCreateDate(new Date());
			ob.setMobileNo(get12DigitMobNum(mobileNo));
			ob.setFirstPaySTxnDate(ob.getCreateDate());
			ob.setUserType(NORMAL_USER_TYPE);
		}
		String strCurrentDate = LocalDate.now().toString();
		if (strCurrentDate.equals(ob.getDateStr())) {
			if (ob.getCollectCount() < collectMaxCount && ob.getCountPayAndCollect() < totalMaxCount) {
				acqTxnLimitDao.updateCollectCount(mobileNo, txnId);
				respCode = ConstantI.MSGID_SUCCESS;
			} else {
				if (ob.getCollectCount() >= collectMaxCount) {
					// collectMaxCount=Collect request limited to 5 per day
					respCode = collectCountError;
				} else {
					// payCountError=Maximum payments+Collect requests limited to 10 per day
					respCode = payCountError;
				}
			}
		} else {
			ob.setCollectCount(1);
			ob.setDateStr(strCurrentDate);
			ob.setIdPk(0);
			if (NORMAL_USER_TYPE == ob.getUserType()) {
				ob.setPayCount(0);
			}
			ob.setStatus(null);
			ob.setTxnId(txnId);
			acqTxnLimitDao.saveUpdate(ob);
			respCode = ConstantI.MSGID_SUCCESS;
		}
		
		if (ConstantI.MSGID_SUCCESS.equals(respCode) && ob.getUserType() == NEW_USER_TYPE
				&& ob.getCollectCount() <= 1) {
			sendMsg(mobileNo);
		}
		return respCode;
	}

	private AcqTxnLimitEntity getByMobileNumber(String mobileNo) {
		return acqTxnLimitDao.findByMobileNo(get12DigitMobNum(mobileNo));
	}

	public boolean updateIdPk(String mobileNo, long idpk, String status) {
		// TODO if required
		return false;
	}

	public boolean updateIdPk(long mobileNo, long idpk, String status) {
		// TODO if required
		return false;
	}

	public long get12DigitMobNum(String mobileNo) {
		if (mobileNo != null) {
			if (mobileNo.length() == 12) {
				return Long.valueOf(mobileNo);
			} else {
				log.info("mobileNo={}"+ mobileNo);
				if (mobileNo.length() >= 10) {
					mobileNo = "91" + mobileNo.substring(mobileNo.length() - 10);
					return Long.valueOf(mobileNo);
				}
			}
		}
		log.info("invalid mobile number ie {}"+ mobileNo);
		return -1;
	}
	
	public static void sendMsg(String mobileNo) {
		try {
			Message msg = new Message();
			msg.setMobileNo(mobileNo);
			msg.setMessage(Util.getProperty("ACQ_SMS_C170"));
			msg.setType(MESSAGE_TYPE_SMS);
			RabbitMQSend.send(JSONConvert.getJsonStr(msg), SMS_QUEUE);
		} catch (Exception e) {
			log.info("error in send sms={}", e);
		}
	}
}
