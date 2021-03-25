package com.npst.mobileservice.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.npst.mobileservice.dao.CustomeraccountHome;
import com.npst.mobileservice.dao.RegistrationHome;
import com.npst.mobileservice.dao.ReqrespauthdetailsHome;
import com.npst.mobileservice.object.RegistrationVO;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.object.TimePayPropertyVO;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.DecryptionUtility;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.CustomerVPA;
import com.npst.upi.hor.Customeraccount;
import com.npst.upi.hor.Custvpa;
import com.npst.upi.hor.Feedback;
import com.npst.upi.hor.Registration;
import com.npst.upi.hor.Reqrespauthdetails;

public class RegistrationHomeService {
	private static final Logger				log							= Logger.getLogger(RegistrationHomeService.class);
	private static RegistrationHome			registrationHome			= null;
	private static SilentsmsHomeService		silentsmsHomeService		= null;
	private static CustomeraccountHome		customerAccountHome			= null;
	private static TimePayPropertyService	timePayPropertyService		= null;
	private static int						feedbackCount				= 1;
	private static String					feedBackAttach				= null;
	private static VersionManagementService	versionManagementService	= null;
	private static final String				feedbackLimit				= Util.getProperty("feedbackLimit");
	private static final String				imagesLimit					= Util.getProperty("imagesLimit");
	DateFormat								dfLogin						= new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	DateFormat								dfDob						= new SimpleDateFormat("dd/MM/yyyy");
	String									osName						= System.getProperty("os.name");
	ReqrespauthdetailsHome					reqrespauthdetailsHome		= null;
	private static AcqOtpService			acqOtpService				= null;
	public RespJson changeAppPass(ReqJson reqJson) {
		String pspPin2, pspPin3, newPin;
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		Registration reg = null;
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			if (null == customerAccountHome) {
				customerAccountHome = new CustomeraccountHome();
			}
			if (null == acqOtpService) {
				acqOtpService = new AcqOtpService();
			}
			newPin = DecryptionUtility.encrypt(reqJson.getNewLoginPin());
			if ("FORGETPASS".equalsIgnoreCase(reqJson.getFlag())) {
				respJson = acqOtpService.validate(reqJson,true);
				log.info("Response from validate OTP for forget passcode:{}"+respJson);
				if (!ConstantI.MSGID_SUCCESS.equalsIgnoreCase(respJson.getMsgId())) {
					return respJson;
				}
				
				Registration registration = registrationHome.getUserByIdAndMobNo(Integer.parseInt(reqJson.getRegId()),
						reqJson.getMobileNo());
				if (null != registration) {

					if (1 == registration.getStatus()) {
						if (null != reqJson.getAccRefNumber()) {
							if (customerAccountHome.getInActiveOrActiveListAccountByMobNoAndAccNoAndMaskedAccNo(
									reqJson.getAccRefNumber(), reqJson.getMobileNo(), reqJson.getMaskedAccnumber())) {
								reg = registration;
							} else {
								respJson.setMsgId(ConstantI.MSGID_FAIL);
								respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_WRONG_ACC_NO.getCode());
								return respJson;
							}
						} else {
							reg = registration;
						}

					} else {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NOT_ACTIVE_MSG.getCode());
						return respJson;
					}
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
					return respJson;
				}
			} else {
				String encryptedPIN = DecryptionUtility.encrypt(reqJson.getLoginPin());
				reg = registrationHome.getUserByIdAndMobNoAndPin(Integer.parseInt(reqJson.getRegId()),
						reqJson.getMobileNo(), encryptedPIN);
			}
			if (null == reg) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_WRONG_LOGIN_PIN.getCode());
				return respJson;
			}
			reg.setLoginpin2((null == reg.getLoginpin2()) ? "" : reg.getLoginpin2());
			reg.setLoginpin3((null == reg.getLoginpin3()) ? "" : reg.getLoginpin3());
			pspPin2 = reg.getLoginpin1();
			pspPin3 = reg.getLoginpin2();
			if (reg.getLoginpin1().equalsIgnoreCase(newPin) || reg.getLoginpin2().equalsIgnoreCase(newPin)
					|| reg.getLoginpin3().equalsIgnoreCase(newPin)) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PASSWORD_NOT__SAME_AS_LAST_THREE_PASSWORD.getCode());
				return respJson;
			} else {
				reg.setLoginpin1(newPin);
				reg.setLoginpin2(pspPin2);
				reg.setLoginpin3(pspPin3);
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, 180);
				reg.setExpdt(c.getTime());
				reg.setLastchangepassdt(new Date());
				if (registrationHome.updateRegVPA(reg)) {
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PASS_CHANGED_SUCCESS.getCode());
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				}
			}
		} catch (Exception e) {
			StringWriter s;
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return respJson;
	}

	private boolean checkPasswordAuth(String crypt, String originalPass) {
		if (crypt.equals(originalPass)) {
			return true;
		} else {
			return false;
		}
	}

	public RespJson checkSumGenertion(String reqStr) {
		log.info("reqStr[" + reqStr + "]");

		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		String serverToken = "", checkSum = "";
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ConstantI.FAILURE_STRING);
		if (null == registrationHome) {
			registrationHome = new RegistrationHome();
		}
		List<Registration> resultRegVpa = registrationHome.validateServerToken(reqJson);
		log.info("resultRegVpa[" + resultRegVpa + "]");
		try {
			for (Registration regvpa : resultRegVpa) {
				serverToken = regvpa.getServertoken();
				Date dt = regvpa.getServertokendt();
				Date current = new Date();
				if (current.before(dt)) {
					log.info("current.before(dt) true");
					checkSum = Util.CalculateChecksum(reqStr, serverToken);
					respJson.setCheckSum(checkSum);
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setMsg(ConstantI.SUCCESS_STRING);
					Calendar c = Calendar.getInstance();
					try {
						c.setTime(new Date());
						c.add(Calendar.MINUTE, Integer.parseInt(Util.getProperty("SERVERTOKENTIME")));
					} catch (Exception e) {
						StringWriter s;
						e.printStackTrace(new PrintWriter(s = new StringWriter()));
						e.printStackTrace();
						log.info(s);
					}
					regvpa.setServertokendt(c.getTime());
					registrationHome.updateRegVPA(regvpa);
				} else {
					log.info("current.before(dt) false");
					respJson.setMsgId(ConstantI.MSGID_EXPIRDTOKEN);
					respJson.setMsg(ConstantI.EXPIRE_TOKEN);
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("respJson[" + respJson.toString() + "]");
		return respJson;
	}

	public boolean checkVirtualId(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		boolean isvpaExists = false;

		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ConstantI.FAILURE_STRING);
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			isvpaExists = registrationHome.checkVPA(reqJson.getVirtualId());

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
		}
		log.info("return successfully with respJson:" + respJson);
		return isvpaExists;
	}

	private boolean chkLoginPin(String pspPin, RespJson respJson) {
		if (null == pspPin) {
			createErrorResponce(ConstantI.BLANK_LOGIN_PIN_MSG, respJson);
			return true;
		}
		return false;
	}

	private boolean chkMobNum(String mobNumber, RespJson respJson) {
		if (null == mobNumber) {
			createErrorResponce(ConstantI.BLANL_MOB_NO_MSG, respJson);
			return true;
		}
		return false;
	}

	private boolean chkRegId(String regVpaId, RespJson respJson) {
		if (null == regVpaId) {
			createErrorResponce(ConstantI.BLANK_REGVPAID_MSG, respJson);
			return true;
		}
		return false;
	}

	private RespJson createErrorResponce(String message, RespJson respJson) {
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(message);
		return respJson;
	}

	public void delete(String regId) {
		RespJson respJson = new RespJson();
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			registrationHome.delete(regId);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("error in " + s);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			respJson.setMsgId(ConstantI.MSGID_FAIL);
		}
		log.info("return successfully with respJson:" + respJson);
	}

	public String getServerToken(String deviceId) {
		Registration resultRegVpa = null;
		if (null == registrationHome) {
			registrationHome = new RegistrationHome();
		}
		try {
			resultRegVpa = registrationHome.getUserByDeviceId(deviceId.trim());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return resultRegVpa.getServertoken();
	}


	public Registration getServerToken(String deviceId, String regId) {
		Registration resultRegVpa = null;
		if (null == registrationHome) {
			registrationHome = new RegistrationHome();
		}
		try {
			resultRegVpa = registrationHome.getUserByDeviceId(regId, deviceId.trim());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return resultRegVpa;
	}

	public RespJson getUserDet(RespJson respJson) {
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			if (null == customerAccountHome) {
				customerAccountHome = new CustomeraccountHome();
			}
			List<Registration> results = registrationHome.getUserByMobNo(respJson.getMobileNo());
			log.info("result for getUserByMobileNo:::"+results.size());
			if (0 != results.size()) {
				for (Registration registration : results) {
					if (1 == registration.getStatus() || 2 == registration.getStatus() || 4 == registration.getStatus() || 5 == registration.getStatus() ) {
						registration.setServertoken(Util.uuidGen());
						Calendar c = Calendar.getInstance();
						try {
							c.setTime(new Date());
							c.add(Calendar.MINUTE, Integer.parseInt(Util.getProperty("SERVERTOKENTIME")));
						} catch (Exception e) {
							StringWriter s;
							e.printStackTrace(new PrintWriter(s = new StringWriter()));
							e.printStackTrace();
							log.info(s);
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
							return respJson;
						}
						registration.setServertokendt(c.getTime());
						registrationHome.updateRegVPA(registration);
						String lastLogin = "";
						try {
							Date lg = registration.getLastlogindt();
							lastLogin = this.dfLogin.format(lg);
						} catch (NullPointerException n) {
							StringWriter s;
							n.printStackTrace(new PrintWriter(s = new StringWriter()));
							n.printStackTrace();
							log.info(s);
						}
						respJson.setPayerName(registration.getFname());
						respJson.setLastLogin(lastLogin);
						respJson.setSecQue(registration.getSecque());
						respJson.setSecAns(registration.getSecans());
						respJson.setEmail(registration.getEmailid());
						respJson.setRegId(String.valueOf(registration.getRegid()));
						respJson.setRegAppStatus(ConstantI.REG_APP_STATUS_ACTIVE);
						respJson.setServerToken(registration.getServertoken());
						respJson.setVirtualId(registration.getDefvpa());
						respJson.setMsg(ConstantI.SUCCESS_STRING);
						respJson.setMsgId(ConstantI.MSGID_SUCCESS);

						// to check device id

						if (!respJson.getDeviceId().trim().equalsIgnoreCase(registration.getDeviceid().trim())) {
							respJson.setUserStatus(ConstantI.DEVICE_MISMATCH);
						} /*else if (!respJson.getImei().equalsIgnoreCase(registration.getDeviceimei())) {
							respJson.setUserStatus(ConstantI.IMEI_MISMATCH);
						} */else {
							respJson.setUserStatus(ConstantI.IMEI_DEVICE_CHECK_OK);
						}

						int count=customerAccountHome.getListAccountByRegVPAIdCount(registration.getRegid());

						if (count != 0) {
							respJson.setCountOfAccounts(count);
						}
						break;

					}
					else {
						respJson.setMsg(ConstantI.SUCCESS_STRING);
						respJson.setMsgId(ConstantI.MSGID_SUCCESS);
						respJson.setRegAppStatus(ConstantI.REG_APP_STATUS_DEREGISTER);
						respJson.setUserStatus(ConstantI.IMEI_DEVICE_CHECK_OK);
					}
				}
			} else {
				respJson.setMsg(ConstantI.SUCCESS_STRING);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setRegAppStatus(ConstantI.REG_APP_STATUS_NEW_USER);
				respJson.setUserStatus(ConstantI.IMEI_DEVICE_CHECK_OK);
				// regAppStatus(with other params)
				// status desc
				// 1=Active
				// 2=new user
				// 3=Deregister
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("return in:" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}

	public Registration getVpa(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		Registration registration = null;
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			registration = registrationHome.getUserByIdAndMobNo(Integer.parseInt(reqJson.getRegId()),
					reqJson.getMobileNo());

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("errro in :" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("return successfully with respJson:" + respJson);
		return registration;
	}

	private boolean isSupportedAppVersion(final String appOs, final String appversion) {
		if (null == versionManagementService) {
			versionManagementService = new VersionManagementService();
		}
		return versionManagementService.isExist(appversion, appOs);
	}

	private RespJson loginChecks(ReqJson reqJson, Registration regvpaFinal, RespJson respJson, String deviceId)
			throws UnsupportedEncodingException, GeneralSecurityException {
		switch (String.valueOf(regvpaFinal.getStatus())) {
		case "1":
			return validateSuccessLogin(reqJson, regvpaFinal, respJson, deviceId.trim());
		case "2":
			return createErrorResponce(ConstantI.NOT_ACTIVE_MSG, respJson);
		case "4":
			return createErrorResponce(ConstantI.NOT_ACTIVE_MSG, respJson);
		case "5":
			return createErrorResponce(ConstantI.NOT_ACTIVE_MSG, respJson);
		default:
			return respJson;
		}
	}

	private RespJson loginSuccess(Registration regvpaFinal, RespJson respJson, String deviceId) {
		try {
			RegistrationHome registrationHome = new RegistrationHome();
			String lastLoginDate = "";
			try {
				Date date = regvpaFinal.getLastlogindt();
				lastLoginDate = this.dfLogin.format(date);
			} catch (NullPointerException n) {
				lastLoginDate = "";
			}
			respJson.setLastLogin(lastLoginDate);
			regvpaFinal.setLastlogindt(new java.util.Date());
			regvpaFinal.setFaillogincount(0);
			regvpaFinal.setBlockdt(null);
			regvpaFinal.setDeviceid(deviceId);
			regvpaFinal.setServertoken(Util.uuidGen());
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(new Date());
				c.add(Calendar.MINUTE, Integer.parseInt(Util.getProperty("SERVERTOKENTIME")));
			} catch (Exception e) {
				StringWriter s;
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				e.printStackTrace();
				log.info(s);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
				return respJson;
			}
			regvpaFinal.setServertokendt(c.getTime());
			boolean isupdate = registrationHome.updateRegVPA(regvpaFinal);
			log.info("return success from regvpaHomeDao.updateRegVPA(regvpa) with isupdate:" + isupdate);
			if (isupdate) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_SUCCESS_LOGIN.getCode());
				respJson.setVirtualId(regvpaFinal.getDefvpa());
				if (null == timePayPropertyService) {
					timePayPropertyService = new TimePayPropertyService();
				}
				final List<TimePayPropertyVO> timePayProperties = timePayPropertyService.timePayProperties();
				respJson.setGlobal(timePayProperties);
			}
			respJson.setServerToken(regvpaFinal.getServertoken());
			// TODO change for pending collect
			try {
				if (null == reqrespauthdetailsHome) {
					reqrespauthdetailsHome = new ReqrespauthdetailsHome();
				}
				log.info("getting pending collect list count");
				List<Reqrespauthdetails> list = reqrespauthdetailsHome.getPendingCollect(regvpaFinal.getRegid());
				if (0 != list.size()) {
					respJson.setPendingCollectCount(String.valueOf(list.size()));
				} else {
					respJson.setPendingCollectCount("0");
				}
			} catch (Exception e) {

				StringWriter s;
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				e.printStackTrace();
				log.info("return in:" + s);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());

			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("return in:" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}

	public RespJson logOutService(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		if (null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId())) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_BLANK_RGISTRATIONID.getCode());
			return respJson;
		}
		if (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo())) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_BLANKMOBNO.getCode());
			return respJson;
		}

		if (null == registrationHome) {
			registrationHome = new RegistrationHome();
		}

		Registration registration = registrationHome.getUserById(Integer.parseInt(reqJson.getRegId()));

		registration.setLogoutdt(new Date());
		registration.setServertokendt(new Date());
		if (registrationHome.updateRegVPA(registration)) {
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_LOGOUT_SUCCESS.getCode());
		}
		return respJson;
	}

	public RespJson preCheck(String strBuild) {
		log.info("strBuild:" + strBuild);
		Session session = null;
		RespJson respJson = new RespJson();
		ReqJson reqJson = JSONConvert.getReqJson(strBuild);
		respJson.setReqId(reqJson.getReqId());
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			Registration rgistration = registrationHome.getUserByIdAndMobNo(Integer.parseInt(reqJson.getRegId()),
					reqJson.getMobileNo());
			log.info("return success from regvpaHomeDao.getUserByIdAndMobNo(" + "RegId:"
					+ Integer.parseInt(reqJson.getRegId()) + "Mobile:" + reqJson.getMobileNo() + ")");
			if (null != rgistration) {
				if (rgistration.getExpdt() != null) {
					if (new Date().after(rgistration.getExpdt())) {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FAIL_PASSWORD_EXPIRED.getCode());
					} else {
						respJson.setMsgId(ConstantI.MSGID_SUCCESS);
						respJson.setMsg(ConstantI.SUCCESS_STRING);
					}
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			return respJson;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.info("return successfully with respJson:" + respJson);
		return respJson;
	}

	public RespJson registerUser(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		//ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		respJson.setReqId(reqJson.getReqId());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FAIL_REGISTRATION_PROCESS.getCode());
		if (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo())) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_BLANKMOBNO.getCode());
			return respJson;
		}
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			if (null == customerAccountHome) {
				customerAccountHome = new CustomeraccountHome();
			}
			
			if (null == acqOtpService) {
				acqOtpService = new AcqOtpService();
			}
			log.info("Request received for RegisterApp:" + reqJson);
			respJson = acqOtpService.validate(reqJson,true);
			log.info("Response from validate OTP for register User:{}"+respJson);
			if (!ConstantI.MSGID_SUCCESS.equalsIgnoreCase(respJson.getMsgId())) {
				return respJson;
			}
			
			respJson = registrationHome.checkRegApp(reqJson);
			if (ConstantI.MSGID_SUCCESS.equalsIgnoreCase(respJson.getMsgId())) {
				if (null == reqJson.getDob() || "".equalsIgnoreCase(reqJson.getDob())) {
					reqJson.setDob("01 / 01 / 1900");
				}
				
				String dobString = reqJson.getDob().replaceAll("\\s+", "");
				Date dob = this.dfDob.parse(dobString);
				Registration registration = new Registration();
                if (null == reqJson.getSource() || "".equalsIgnoreCase(reqJson.getSource())) {
                	registration.setSource("SoPay");// null is like request from SoPay 13/01/2020
				}else {
					registration.setSource(reqJson.getSource());//for timepay
					registration.setNode(reqJson.getNode());
				}
				try {
					registration.setAppos(reqJson.getAppOs());
				} catch (Exception e) {
				}
				try {
					registration.setAppver(reqJson.getAppVer());
				} catch (Exception e) {
				}
				try {
					registration.setCreateddt(new Date());
				} catch (Exception e) {
				}
				try {
					registration.setDob(dob);
				} catch (Exception e) {
				}
				try {
					registration.setEmailid(reqJson.getEmail());
				} catch (Exception e) {
				}
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, 180);
				try {
					registration.setExpdt(c.getTime());
				} catch (Exception e) {
				}
				try {
					registration.setFaillogincount(0);
				} catch (Exception e) {
				}
				try {
					registration.setFname(null == reqJson.getfName() ? "" : reqJson.getfName().toUpperCase());
				} catch (Exception e) {
				}
				try {
					registration.setLname(null == reqJson.getlName() ? "" : reqJson.getlName().toUpperCase());
				} catch (Exception e) {
				}
				try {
					registration.setGcmtoken(reqJson.getGcmToken());
				} catch (Exception e) {
				}
				try {
					registration.setGender(reqJson.getGender());
				} catch (Exception e) {
				}
				try {
					registration.setLoginpin1(DecryptionUtility.encrypt(reqJson.getLoginPin()));
				} catch (Exception e) {
				}
				try {
					registration.setMname(null == reqJson.getmName() ? "" : reqJson.getmName().toUpperCase());
				} catch (Exception e) {
				}
				try {
					registration.setMobno(reqJson.getMobileNo());
				} catch (Exception e) {
				}
				try {
					registration.setServertoken(Util.uuidGen());
				} catch (Exception e) {
				}
				try {
					c.setTime(new Date());
					c.add(Calendar.MINUTE, Integer.parseInt(Util.getProperty("SERVERTOKENTIME")));
				} catch (Exception e) {
					StringWriter s;
					e.printStackTrace(new PrintWriter(s = new StringWriter()));
					e.printStackTrace();
					log.info(s);
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
					return respJson;
				}
				try {
					registration.setServertokendt(c.getTime());
				} catch (Exception e) {
				}
				try {
					registration.setStatus(1);
				} catch (Exception e) {
				}

				if (12 == reqJson.getMobileNo().length()) {
					try {
						reqJson.setMobileNo(reqJson.getMobileNo().substring(2));
					} catch (Exception e) {
					}
				}
				String defVirtualId = reqJson.getVirtualId();
				if (customerAccountHome.checkReservedVPA(defVirtualId)) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_VIRTUALID_EXIST_IN_RESERVEDVPA.getCode());
					log.info("virtualId exists in ReservedVPA List with respJson:" + respJson);
					return respJson;
				}
				if (registrationHome.checkVPA(defVirtualId)) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PRESENT_VIRTUALID.getCode());
					log.info("virtualId registered with some other regid:" + respJson);
					return respJson;
				}
				if (50 >= defVirtualId.length()) {
					registration.setDefvpa(defVirtualId);
				}
				try {
					registration.setLastlogindt(new Date());
				} catch (Exception e) {
				}
				try {
					registration.setUpdateddt(new Date());
				} catch (Exception e) {
				}
				try {
					if (null == silentsmsHomeService) {
						silentsmsHomeService = new SilentsmsHomeService();
					}
					respJson = silentsmsHomeService.getDevicaeIdAndImei("91" + reqJson.getMobileNo());
					if (respJson.getDeviceId().trim().equalsIgnoreCase(reqJson.getDeviceId().trim())&& respJson.getImei().equalsIgnoreCase(reqJson.getImei())) {
						registration.setDeviceid(respJson.getDeviceId().trim());
						registration.setDeviceimei(respJson.getImei());
					} else {
						respJson.setMsgId(ConstantI.DEVICE_MISMATCH);
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
						return respJson;
					}
				} catch (Exception e) {
					StringWriter s;
					e.printStackTrace(new PrintWriter(s = new StringWriter()));
					e.printStackTrace();
					log.info(s);
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());

				}
				CustomerVPA customerVpa = new CustomerVPA();
				customerVpa.setCreateDate(new Date());
				customerVpa.setModifyDate(new Date());
				respJson = registrationHome.registerUser(registration, customerVpa);

			} else {
				if (null == respJson.getMsgId() || "".equalsIgnoreCase(respJson.getMsgId())) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_TEMP_BLOCK_MSG.getCode());
				}
				return respJson;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}

	public RespJson regVpaDelete(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			Registration reg = registrationHome.getUserById(Integer.parseInt(reqJson.getRegId()));
			reg.setStatus(ConstantI.DEREGISTER);
			if (registrationHome.updateRegVPA(reg)) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_DEREGISTARTION_SUCCESS.getCode());
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_DEREGISTARTION_FAIL.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("error in " + s);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			respJson.setMsgId(ConstantI.MSGID_FAIL);
		}
		log.info("return successfully with respJson:" + respJson);
		return respJson;
	}

	private void saveDocument(Registration reg, String encImage, Long imageName) throws IOException {
		String mobileNumber = reg.getMobno();
		String date = Util.getStringDate();
		if (osName.toLowerCase().contains("windows")) {
			feedBackAttach = "c:\\conf\\" + date + "\\" + mobileNumber;
		} else {
			feedBackAttach = "/home/npst/" + date + "/" + mobileNumber;
		}
		String filePath = feedBackAttach + "/" + imageName;
		File f = new File(filePath);
		feedbackCount++;
		if (!f.exists()) {
			f.mkdirs();
		}
		BufferedImage image = null;
		byte[] imageByte;
		imageByte = Util.base64Decode(encImage);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		ImageIO.write(image, "jpg", f);
		bis.close();
	}

	public RespJson saveFeedback(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		String allImages = "";
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			List<Feedback> feedbacks = registrationHome.findByRegIdAndSDate(Integer.parseInt(reqJson.getRegId()),
					Util.getOnlyDate());
			if (null != feedbacks && feedbacks.size() < Integer.valueOf(feedbackLimit)) {
				Registration reg = registrationHome.findByRegId(Integer.parseInt(reqJson.getRegId()));
				if (null != reqJson.getImage() && reqJson.getImage().trim().length() > 0) {
					String[] value_split = reqJson.getImage().split("\\|");
					if (value_split.length > Integer.valueOf(imagesLimit)) {
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FEEDBACK_IMAGES_EXCED.getCode());
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						return respJson;
					}
					for (String encImage : value_split) {
						Long imageName = System.currentTimeMillis();
						saveDocument(reg, encImage, imageName);
						allImages = imageName + "~";
					}
					allImages = allImages.substring(0, allImages.length() - 1);
				}
				saveFeedback(reqJson.getFeedbackCategory(), reqJson.getFeedbackSeverity(), reqJson.getRemarks(),
						reqJson.getEmail(), reqJson.getFeedbackText(), reqJson.getScreenPath(), reqJson.getRegId(),
						reg.getMobno(), allImages, reqJson.getAppVersion(), reqJson.getOsName(), reqJson.getOsVersion(),
						reqJson.getHandsetName());
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_SUCCESS_FEEDBACK.getCode());
			} else {
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FEEDBACK_BLOCKED_MSG.getCode());
				respJson.setMsgId(ConstantI.MSGID_FAIL);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("return in:" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}

	private void saveFeedback(String feedbackCategory, String feedbackSeverity, String remarks, String email,
			String feedbackText, String screenPath, String regId, String mob, String allImages, String appVer,
			String osName, String osVersion, String handsetName) throws ParseException {
		String updatedScreenpath = screenPath.replaceAll("Fragment", "");
		/*
		 * List<MasterConfig> masterList=
		 * registrationHome.getScreenPathCode(updatedScreenpath); String
		 * screenPathCode=masterList.get(0).getCode();
		 */
		Feedback feedback = new Feedback();
		feedback.setAttachmentPath(feedBackAttach);
		feedback.setEmail(email);
		feedback.setFeedback_date(Util.getOnlyDate());
		feedback.setFeedbackCategory(feedbackCategory.trim().length() == 0 ? null : Integer.parseInt(feedbackCategory));
		feedback.setFeedbackSeverity(feedbackSeverity.trim().length() == 0 ? null : Integer.parseInt(feedbackSeverity));
		feedback.setFeedbackText(feedbackText);
		feedback.setRegId(Integer.parseInt(regId));
		feedback.setRemarks(remarks);
		feedback.setScreenPath(updatedScreenpath);
		feedback.setStatus(1);
		feedback.setMobile_no(mob);
		feedback.setApp_version(appVer);
		feedback.setHandset_name(handsetName);
		feedback.setImagesName(allImages);
		feedback.setOs_name(osName);
		feedback.setOs_version(osVersion);
		registrationHome.saveFeedback(feedback);
	}

	public RespJson saveGCMTokenInrgistration(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		if (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo())) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_BLANKMOBNO.getCode());
			return respJson;
		}

		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			boolean results = registrationHome.saveGCMTokenInRegVPA(Integer.parseInt(reqJson.getRegId()),
					reqJson.getMobileNo(), reqJson.getGcmToken());
			log.info("return success from rgistrationHome.saveGCMTokenInrgistration("
					+ Integer.parseInt(reqJson.getRegId()) + "Mobile:" + reqJson.getMobileNo() + "GcmToken:"
					+ reqJson.getGcmToken() + "result:" + results + ")");
			if (results) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}

		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		log.info("return successfully with respJson:" + respJson);
		return respJson;
	}

	public RespJson selectLogin(ReqJson reqJson, String deviceId) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		log.info("Convert Request for seclect login[" + reqJson + "]");
		log.info("Device Id for seclect login in body"+reqJson.getDeviceId().trim());
		List<Reqrespauthdetails> list = null;
		if (chkRegId(reqJson.getRegId(), respJson)) { return respJson; }
		if (chkLoginPin(reqJson.getLoginPin(), respJson)) { return respJson; }
		if (chkMobNum(reqJson.getMobileNo(), respJson)) { return respJson; }
		if (!isSupportedAppVersion(reqJson.getAppOs(), reqJson.getAppVer())) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSG_APP_VERSION_NOT_SUPPORTED.getCode());
			return respJson;
		}
		if (null == customerAccountHome) {
			customerAccountHome = new CustomeraccountHome();
		}
		List<Customeraccount> accountList = customerAccountHome.getListAccountByRegVPAId(Integer.parseInt(reqJson.getRegId()));
		if (!accountList.isEmpty()) {
			respJson.setCountOfAccounts(accountList.size());
		}
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			Registration registration = registrationHome.getUserByIdAndMobNo(Integer.parseInt(reqJson.getRegId()),reqJson.getMobileNo());
			log.info("Registration data for selectLogin"+registration);
			if (null == registration) {
				return createErrorResponce(ErrorCode.AcqErrorCode.MSGID_INVALID_LOGIN_DETAIL.getCode(), respJson);
			} else {
				log.info("Requested device Id for login user"+reqJson.getDeviceId().trim());
				log.info("Device Id for Registrated user"+registration.getDeviceid().trim());
				if (null == reqJson.getDeviceId()|| !reqJson.getDeviceId().trim().equalsIgnoreCase(registration.getDeviceid().trim())) {
					respJson.setUserStatus(ConstantI.DEVICE_MISMATCH);
					return createErrorResponce(ConstantI.DEVICE_MISMATCH, respJson);
				} else if (null == reqJson.getImei()|| !reqJson.getImei().equalsIgnoreCase(registration.getDeviceimei())) {
					respJson.setUserStatus(ConstantI.IMEI_MISMATCH);
					return createErrorResponce(ConstantI.IMEI_MISMATCH, respJson);
				} else {
					respJson.setUserStatus(ConstantI.IMEI_DEVICE_CHECK_OK);
					return loginChecks(reqJson, registration, respJson, deviceId.trim());
				}

			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("return in:" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}

		return respJson;
	}

	public RespJson selectProfileDet(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		Registration registration = null;
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			registration = registrationHome.getUserByIdAndMobNo(Integer.parseInt(reqJson.getRegId()),
					reqJson.getMobileNo());
			if (1 == registration.getStatus()) {
				if (null != registration) {
					respJson.setRegistrationVO(new RegistrationVO(registration));
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setMsg(ConstantI.SUCCESS_STRING);
				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NOT_ACTIVE_MSG.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("errro in :" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("return successfully with respJson:" + respJson);
		return respJson;
	}

	public RespJson updateUDetails(ReqJson reqJson) {
		Date dob = null;
		log.info("Request after convert json:" + reqJson);
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PROFILE_UPDATE_FAIL.getCode());
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			Registration registration = registrationHome.getUserById(Integer.parseInt(reqJson.getRegId()));
			if (null == registration) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
				return respJson;
			}
			try {
				if (null == reqJson.getDob() || "".equalsIgnoreCase(reqJson.getDob())) {
					reqJson.setDob("01 / 01 / 1900");
				}
				String dobString = reqJson.getDob().replaceAll("\\s+", "");
				dob = this.dfDob.parse(dobString);
			} catch (Exception e) {
				StringWriter s;
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				e.printStackTrace();
				log.info("return in:" + s);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}
			registration.setFname(reqJson.getfName().toUpperCase());
			registration.setGender(reqJson.getGender());
			registration.setDob(dob);
			registration.setSecque(DecryptionUtility.encrypt(reqJson.getSecQue()));
			registration.setSecans(DecryptionUtility.encrypt(reqJson.getSecAns()));
			registration.setEmailid(reqJson.getEmail());
			registration.setUpdateddt(new java.util.Date());
			registration.setAadhaarno(reqJson.getAddhaarNo());
			if (registrationHome.updateRegVPA(registration)) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PROFILE_UPDATE_SUCCESS.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("return in:" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}

	private RespJson validateSuccessLogin(ReqJson reqJson, Registration regvpaFinal, RespJson respJson, String deviceId)
			throws UnsupportedEncodingException, GeneralSecurityException {
		boolean authPass = checkPasswordAuth(DecryptionUtility.encrypt(reqJson.getLoginPin()),
				regvpaFinal.getLoginpin1());
		if (authPass) {
			regvpaFinal.setGcmtoken(reqJson.getGcmToken());
			regvpaFinal.setAppos(reqJson.getAppOs());
			regvpaFinal.setAppver(reqJson.getAppVer());
			if (regvpaFinal.getBlockdt() != null) {
				if (regvpaFinal.getBlockdt().before(new Date())) {
					return loginSuccess(regvpaFinal, respJson, deviceId.trim());
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACCOUNT_BLOCKED_MSG.getCode());
					respJson.setStatus("" + regvpaFinal.getStatus());
					return respJson;
				}
			}
			return loginSuccess(regvpaFinal, respJson, deviceId.trim());
		} else {
			if (regvpaFinal.getFaillogincount() < 4) {
				int i = regvpaFinal.getFaillogincount() + 1;
				regvpaFinal.setFaillogincount(i);
				registrationHome.updateRegVPA(regvpaFinal);
				createErrorResponce(ErrorCode.AcqErrorCode.MSGID_INVALID_ATTEMPT_N_MSG.getCode() + "|" + i, respJson);
				log.info("return success from regvpaHomeDao.updateRegVPA(updateRegvpa):");
			} else {
				if (regvpaFinal.getFaillogincount() == 5 && regvpaFinal.getBlockdt() != null) {
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACCOUNT_BLOCKED_MSG.getCode());
					respJson.setMsgId(ConstantI.MSGID_FAIL);
				} else {
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACCOUNT_BLOCKED_MSG.getCode());
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, 1);
					int i = regvpaFinal.getFaillogincount() + 1;
					regvpaFinal.setFaillogincount(i);
					regvpaFinal.setBlockdt(c.getTime());
					registrationHome.updateRegVPA(regvpaFinal);
					log.info("return success from regvpaHomeDao.updateRegVPA(updateRegvpa)");
				}
				log.info("return success from regvpaHomeDao.updateRegVPA(updateRegvpa)");
			}
		}
		return respJson;
	}

	public RespJson updateDeviceAndImei(ReqJson reqJson) {
		log.info("Request after convert json:" + reqJson);
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}

			if (reqJson.getRegId() != null && !reqJson.getRegId().equalsIgnoreCase("")) {
				if (registrationHome.updateDeviceIdIMEI(Integer.parseInt(reqJson.getRegId()), reqJson.getDeviceId(),
						reqJson.getImei())) {
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_UPDT_DEV_IMEI_SUCCESS.getCode());
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				} else {
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_UPDT_DEV_IMEI_FAILURE.getCode());
					respJson.setMsgId(ConstantI.MSGID_FAIL);
				}
			} else {

				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
				return respJson;

			}
		} catch (Exception e) {
			StringWriter s;
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return respJson;
	}
	
	
	
	//TimePay
	
	public RespJson selectLogin1(ReqJson reqJson, String deviceId) {
		log.info("Req Json String for login"+reqJson);
		RespJson respJson = new RespJson();
		log.info("Request after convert json:" + reqJson);
		List<Reqrespauthdetails> list = null;
		if (chkRegId(reqJson.getRegId(), respJson)) { return respJson; }
		if (chkMobNum(reqJson.getMobileNo(), respJson)) { return respJson; }
		if (!isSupportedAppVersion(reqJson.getAppOs(), reqJson.getAppVer())) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSG_APP_VERSION_NOT_SUPPORTED.getCode());
			return respJson;
		}
		if (null == customerAccountHome) {
			customerAccountHome = new CustomeraccountHome();
		}
		List<Customeraccount> accountList = customerAccountHome.getListAccountByRegVPAId(Integer.parseInt(reqJson.getRegId()));
		if (!accountList.isEmpty()) {
			respJson.setCountOfAccounts(accountList.size());
		}
		try {
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			Registration registration = registrationHome.getUserByIdAndMobNo(Integer.parseInt(reqJson.getRegId()),reqJson.getMobileNo());
			if (null == registration) {
				return createErrorResponce(ErrorCode.AcqErrorCode.MSGID_INVALID_LOGIN_DETAIL.getCode(), respJson);
			} else {
				if (null == reqJson.getDeviceId()|| !reqJson.getDeviceId().trim().equalsIgnoreCase(registration.getDeviceid().trim())) {
					respJson.setUserStatus(ConstantI.DEVICE_MISMATCH);
					return createErrorResponce(ConstantI.DEVICE_MISMATCH, respJson);
				} else if (null == reqJson.getImei()
						|| !reqJson.getImei().equalsIgnoreCase(registration.getDeviceimei())) {
					respJson.setUserStatus(ConstantI.IMEI_MISMATCH);
					return createErrorResponce(ConstantI.IMEI_MISMATCH, respJson);
				} else {
					respJson.setUserStatus(ConstantI.IMEI_DEVICE_CHECK_OK);
					return loginChecks1(reqJson, registration, respJson, deviceId);
				}

			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("return in:" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		return respJson;
	}
	
	private RespJson loginChecks1(ReqJson reqJson, Registration regvpaFinal, RespJson respJson, String deviceId)
			throws UnsupportedEncodingException, GeneralSecurityException {
		switch (String.valueOf(regvpaFinal.getStatus())) {
		case "1":
			return validateSuccessLogin1(reqJson, regvpaFinal, respJson, deviceId);
		case "2":
			return createErrorResponce(ConstantI.NOT_ACTIVE_MSG, respJson);
		case "4":
			return createErrorResponce(ConstantI.NOT_ACTIVE_MSG, respJson);
		case "5":
			return createErrorResponce(ConstantI.NOT_ACTIVE_MSG, respJson);
		default:
			return respJson;
		}
	}
	
	
	//TimePay Merchant 13/01/2020
	
	private RespJson validateSuccessLogin1(ReqJson reqJson, Registration regvpaFinal, RespJson respJson, String deviceId)
			throws UnsupportedEncodingException, GeneralSecurityException {
		/*boolean authPass = checkPasswordAuth(DecryptionUtility.encrypt(reqJson.getLoginPin()),
				regvpaFinal.getLoginpin1());*/
		boolean authPass = true;
		if (authPass) {
			regvpaFinal.setGcmtoken(reqJson.getGcmToken());
			regvpaFinal.setAppos(reqJson.getAppOs());
			regvpaFinal.setAppver(reqJson.getAppVer());
			if (regvpaFinal.getBlockdt() != null) {
				if (regvpaFinal.getBlockdt().before(new Date())) {
					return loginSuccess(regvpaFinal, respJson, deviceId);
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACCOUNT_BLOCKED_MSG.getCode());
					respJson.setStatus("" + regvpaFinal.getStatus());
					return respJson;
				}
			}
			return loginSuccess(regvpaFinal, respJson, deviceId);
		} else {
			if (regvpaFinal.getFaillogincount() < 4) {
				int i = regvpaFinal.getFaillogincount() + 1;
				regvpaFinal.setFaillogincount(i);
				registrationHome.updateRegVPA(regvpaFinal);
				createErrorResponce(ErrorCode.AcqErrorCode.MSGID_INVALID_ATTEMPT_N_MSG.getCode() + "|" + i, respJson);
				log.info("return success from regvpaHomeDao.updateRegVPA(updateRegvpa):");
			} else {
				if (regvpaFinal.getFaillogincount() == 5 && regvpaFinal.getBlockdt() != null) {
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACCOUNT_BLOCKED_MSG.getCode());
					respJson.setMsgId(ConstantI.MSGID_FAIL);
				} else {
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACCOUNT_BLOCKED_MSG.getCode());
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, 1);
					int i = regvpaFinal.getFaillogincount() + 1;
					regvpaFinal.setFaillogincount(i);
					regvpaFinal.setBlockdt(c.getTime());
					registrationHome.updateRegVPA(regvpaFinal);
					log.info("return success from regvpaHomeDao.updateRegVPA(updateRegvpa)");
				}
				log.info("return success from regvpaHomeDao.updateRegVPA(updateRegvpa)");
			}
		}
		return respJson;
	}
	
	public void registerSMerchant(final ReqJson reqJson,final RespJson respJson) {
		try {
			if(registrationHome==null) {
				registrationHome=new RegistrationHome();	
			}
			String mob=reqJson.getMobileNo();
			
			if(StringUtils.isNotBlank(mob) && mob.length()>=10) {
				if(mob.length()==10) {
					mob="91"+mob;
				}else if(mob.length()>12) {
					mob=mob.substring(mob.length()-12);
				}
				reqJson.setMobileNo(mob);
				List<Registration> list=registrationHome.getUserByMobNo(reqJson.getMobileNo());
				boolean f=false;
				if(list==null || list.size()==0) {
					f=true;
				}else {
					try {
						//f=list.stream().anyMatch(ob->String.valueOf(ob.getStatus()).matches(MERCHANT_REGISTER_ALLOW_REX));
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
				if(f) {
					Registration reg=prepareMerchantReg(reqJson);
					RespJson respT=registrationHome.registerMerchantUser(reg);
					if(StringUtils.isNotBlank(respT.getRegId())) {
						respJson.setRegId(respT.getRegId());
						respJson.setMsgId(ConstantI.MSGID_SUCCESS);
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_REGSUCCESS.getCode());
					}else {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						if(StringUtils.isNotBlank(respT.getMsg())){
							respJson.setMsg(respT.getMsg());
						}
					}
				}else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ALL_READY_ACTIVE_MSG.getCode());
				}
			}else {
				log.warn("");
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}
			
		}catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		 
		
	}
	
	
	private static Registration prepareMerchantReg(final ReqJson reqJson) {
		Registration r = new Registration();
		r.setMobno(reqJson.getMobileNo());
		r.setDefvpa(reqJson.getVirtualId().toLowerCase());
		r.setStatus(ConstantI.ACTIVE_MERCHANT);
		r.setTenantid(reqJson.getTenantId());
		r.setSource(reqJson.getSource());
		r.setNode(reqJson.getNode());
		r.setFname(reqJson.getName());
		r.setCreateddt(new Date());
		return r;
	}
}
