package com.npst.mobileservice.main;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.MaxVpaPerUserCheck;
import com.npst.mobileservice.object.CustomerMandateDTO;
import com.npst.mobileservice.object.CustomerMandateHistoryDto;
import com.npst.mobileservice.object.MandatesHistory;
import com.npst.mobileservice.object.QRResponse;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.ReqRespAuthMandatedetailsVO;
import com.npst.mobileservice.object.ReqrespauthdetailsVO;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.object.VirtualIdVO;
import com.npst.mobileservice.service.AboutUsInfoService;
import com.npst.mobileservice.service.AcqOtpService;
import com.npst.mobileservice.service.AppVersionService;
import com.npst.mobileservice.service.BeneficiaryService;
import com.npst.mobileservice.service.BlockedbycustomerService;
import com.npst.mobileservice.service.ComplaintHomeService;
import com.npst.mobileservice.service.CrashAnalyticsInfoService;
import com.npst.mobileservice.service.CustomerMandateService;
import com.npst.mobileservice.service.CustomerTxnsService;
import com.npst.mobileservice.service.CustomeraccountHomeService;
import com.npst.mobileservice.service.ListAccountProviderService;
import com.npst.mobileservice.service.LovHomeService;
import com.npst.mobileservice.service.MandateServiceHome;
import com.npst.mobileservice.service.RegistrationHomeService;
import com.npst.mobileservice.service.ReqRespAuthMandatedetailsService;
import com.npst.mobileservice.service.ReqrespauthdetailsService;
import com.npst.mobileservice.service.SecurityEncryptionService;
import com.npst.mobileservice.service.SignedQrVerificationService;
import com.npst.mobileservice.service.SilentsmsHomeService;
import com.npst.mobileservice.service.UseractivityHomeService;
import com.npst.mobileservice.service.VersionManagementService;
import com.npst.mobileservice.util.AesEncryption;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.DecryptionUtility;
import com.npst.mobileservice.util.ECSignQRUtil;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.Customeraccount;
import com.npst.upi.hor.Registration;

/**
 * @author Mbyte
 */
@Path("/Mobile")
public class MobileService {
	private static final Logger					log							= Logger.getLogger(MobileService.class);
	
	private static RegistrationHomeService		registrationHomeService		= null;
	private static CustomeraccountHomeService	customeraccountHomeService	= null;
	private static ReqrespauthdetailsService	reqrespauthdetailsService	= null;
	private static ComplaintHomeService			customerQueryHomeService	= null;
	private static BeneficiaryService			beneficiaryService			= null;
	private static ListAccountProviderService	listAccountService			= null;
	private static UseractivityHomeService		useractivityHomeService		= null;
	private static CustomerTxnsService			customerTxnsService			= null;
	private static AesEncryption				aESEncryptionUtility		= new AesEncryption();
	private static CrashAnalyticsInfoService	exceptionLogService			= null;
	private static AboutUsInfoService			aboutUsInfoService			= null;
	private static SilentsmsHomeService			silentsmsService			= null;
	private static AcqOtpService				acqOtpService				= null;
	private static AppVersionService			appVersionService			= null;
	private static VersionManagementService		versionManagementService	= null;
	private static BlockedbycustomerService		blockedbycustomerService	= null;
	private static LovHomeService				lovHomeService				= null;
	private static SecurityEncryptionService	securityEncryptionService	= null;
	private static MandateServiceHome mandateservicehome=null;
	private static SignedQrVerificationService verificationSercice = null;
	 private static MaxVpaPerUserCheck maxVpaPerUserCheck;
	//mandate
	
	private static ReqRespAuthMandatedetailsService reqRespAuthMandatedetailsService=null;
	private static CustomerMandateService customerMandateService=null;
	public static final String secretKeys ="Npst@!@##@@!!NPSt";
	@POST
	@Path("/changeAccStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String changeAccStatus(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		RespJson respJson = new RespJson();
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for ChangeAccStatus=" + deviceid);
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.accountStatus(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for changeAccStatus=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/changeVpaStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String changeVpaStatus(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for change VPA Status=" + deviceid);
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.vpaStatus(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for changeVpaStatus=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/unblockVpa")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String unblockVpa(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for unblock VPA=" + deviceid);
			if (null == blockedbycustomerService) {
				blockedbycustomerService = new BlockedbycustomerService();
			}
			respJson = blockedbycustomerService.unblockVpa(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for unblockVpa=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getBlockVpa")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getBlockVpa(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			if (null == blockedbycustomerService) {
				blockedbycustomerService = new BlockedbycustomerService();
			}
			
			respJson = blockedbycustomerService.getBlockVpa(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getBlockVpa=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/addBankAcc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addBankAcc(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for add bank account=" + deviceid);
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.insertBankAcc(reqJson);
			
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for addBankAcc=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/addBeneficiary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addBeneficiary(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for add beneficiary=" + deviceid);
			if (null == beneficiaryService) {
				beneficiaryService = new BeneficiaryService();
			}
			respJson = beneficiaryService.addBeneficiary(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for addBeneficiary=" + respJson);
		return JSONConvert.getJsonStr(respJson);
		
	}
	
	@POST
	@Path("/addComplaint")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addComplaint(String reqStr) {
		log.info("");
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		try {
			
			if (null == customerQueryHomeService) {
				customerQueryHomeService = new ComplaintHomeService();
			}
			respJson = customerQueryHomeService.addComplaint(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for addComplaint=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/changePassInside") // see it latter
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String changePassInside(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for change pass inside=" + deviceid);
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.changeAppPass(reqJson);
			if (null == useractivityHomeService) {
				useractivityHomeService = new UseractivityHomeService();
			}
			useractivityHomeService.insertDetails(reqJson, respJson, 2, new Date());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for changePassInside=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/changePassOutside") // see it latter
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String changePassOutside(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for change pass outSide=" + deviceid);
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.changeAppPass(reqJson);
			if (null == useractivityHomeService) {
				useractivityHomeService = new UseractivityHomeService();
			}
			useractivityHomeService.insertDetails(reqJson, respJson, 2, new Date());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for changePassOutside=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/forgetPass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String forgetPass(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for forgot pass=" + deviceid);
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.changeAppPass(reqJson);
			if (null == useractivityHomeService) {
				useractivityHomeService = new UseractivityHomeService();
			}
			useractivityHomeService.insertDetails(reqJson, respJson, 2, new Date());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for forgetPass=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/checkVpa") // Done 05-07-2017
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkVpa(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			respJson.setReqId(reqJson.getReqId());
			String vpa = reqJson.getVirtualId();
			String regId=reqJson.getRegId();
			
			if (vpa != null && regId != null && !(regId = regId.trim()).isEmpty() && !"0".equalsIgnoreCase(regId)) {
				if (maxVpaPerUserCheck == null) {
					maxVpaPerUserCheck = MaxVpaPerUserCheck.getInstance();
				}
				if (!maxVpaPerUserCheck.isAllow(Long.parseLong(regId), vpa)) {
					log.info("maxVpaPerUserCheck is exceeding for regId="+reqJson.getRegId());
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(MaxVpaPerUserCheck.ERROR_CODE);
					respJson.setReqId(reqJson.getReqId());
					return JSONConvert.getJsonStr(respJson);
				}
			}
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			boolean flag = registrationHomeService.checkVirtualId(reqJson);
			
			if (flag) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PRESENT_VIRTUALID.getCode());
			} else {
				flag = customeraccountHomeService.checkVirtualId(reqStr);
				if (flag) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PRESENT_VIRTUALID.getCode());
				} else {
					customeraccountHomeService.checkReservedVPA(reqJson.getVirtualId(),respJson);
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
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for checkVpa=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/deleteBeneficiary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteBeneficiary(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == beneficiaryService) {
				beneficiaryService = new BeneficiaryService();
			}
			respJson = beneficiaryService.deleteBeneficiary(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for deleteBeneficiary=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/deleteProfile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteProfile(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		
		try {
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.deleteAll(reqJson);
			if (null == useractivityHomeService) {
				useractivityHomeService = new UseractivityHomeService();
			}
			useractivityHomeService.insertDetails(reqJson, respJson, 3, new Date());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for deleteProfile=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getAddedAcc") //
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAddedAcc(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		log.info("Request id for getAddedAcc is:{}"+reqJson.getReqId());
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.getAddedAccount(reqJson);
			
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getAddedAcc=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getAllVPAs") // Done 05-07-2017
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllVPAs(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			Registration registration = registrationHomeService.getVpa(reqJson);
			List<Customeraccount> customerAccounts = customeraccountHomeService.getVpa(reqJson);
			VirtualIdVO virtualIdVO = null;
			List<VirtualIdVO> list = new ArrayList<>();
			List<VirtualIdVO> resultList = new ArrayList<>();
			if (!customerAccounts.isEmpty()) {
				VirtualIdVO defVirtualIdVO = new VirtualIdVO();
				for (Customeraccount customeraccount : customerAccounts) {
					if (registration.getDefvpa() != null
							&& registration.getDefvpa().equalsIgnoreCase(customeraccount.getAccvirtualid())) {
						defVirtualIdVO.setIsDefault(ConstantI.IS_DEFAULT_VIRTUALID);
						defVirtualIdVO.setVirtualId(customeraccount.getAccvirtualid());
						defVirtualIdVO.setName(customeraccount.getName());
						defVirtualIdVO.setStatus(customeraccount.getStatus());
					} else {
						virtualIdVO = new VirtualIdVO();
						virtualIdVO.setIsDefault(ConstantI.NOT_DEFAULT_VIRTUALID);
						virtualIdVO.setVirtualId(customeraccount.getAccvirtualid());
						virtualIdVO.setName(customeraccount.getName());
						virtualIdVO.setStatus(customeraccount.getStatus());
						list.add(virtualIdVO);
					}
				}
				resultList.add(defVirtualIdVO);
				resultList.addAll(list);
			} else {
				virtualIdVO = new VirtualIdVO();
				virtualIdVO.setVirtualId(registration.getDefvpa());
				virtualIdVO.setIsDefault(ConstantI.IS_DEFAULT_VIRTUALID);
				virtualIdVO.setName("");
				resultList.add(virtualIdVO);
			}
			respJson.setVirtualIdList(resultList);
		} catch (final Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getAllVPAs=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getBeneficiary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getBeneficiary(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == beneficiaryService) {
				beneficiaryService = new BeneficiaryService();
			}
			respJson = beneficiaryService.getBeneficiary(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getBeneficiary=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getComplaint")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getComplaint(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == customerQueryHomeService) {
				customerQueryHomeService = new ComplaintHomeService();
			}
			respJson = customerQueryHomeService.getComplaint(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getComplaint=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getLov")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getLov(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == lovHomeService) {
				lovHomeService = new LovHomeService();
			}
			respJson = lovHomeService.selectLovByLovType(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getLov=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getMobile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMobile(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			if (null == silentsmsService) {
				silentsmsService = new SilentsmsHomeService();
			}
			respJson = silentsmsService.getMobileNoService(reqJson);
			if ("0".equalsIgnoreCase(respJson.getMsgId())) {
				respJson = registrationHomeService.getUserDet(respJson);
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("error in:" + s);
			respJson.setMsgId(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getMobile=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/historyByDate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String historyByDate(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == customerTxnsService) {
				customerTxnsService = new CustomerTxnsService();
			}
			respJson = customerTxnsService.histotyByDate(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for historyByDate=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/historyLastTrans")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String historyLastTrans(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			try {
				if (null == customerTxnsService) {
					customerTxnsService = new CustomerTxnsService();
				}
			} catch (Exception ex) {
				ex.getMessage();
				ex.printStackTrace();
			}
			respJson = customerTxnsService.histoty(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for historyLastTrans=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(String reqStr, @HeaderParam("deviceId")
	String deviceId) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			log.info("Encrypted Deviceid for login in header[" + deviceId + "]");
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			log.info("Decrypted Deviceid for login in header[" + deviceid + "]");
			respJson = registrationHomeService.selectLogin(reqJson, deviceid);
			
			if (null == useractivityHomeService) {
				useractivityHomeService = new UseractivityHomeService();
			}
			useractivityHomeService.insertDetails(reqJson, respJson, 1, new Date());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for login=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/logOut")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String logOut(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.logOutService(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("error in:" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for logOut=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/myProfile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String myProfile(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.selectProfileDet(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for myProfile=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/pendingCollect")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String pendingCollect(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == reqrespauthdetailsService) {
				reqrespauthdetailsService = new ReqrespauthdetailsService();
			}
			respJson.setReqId(reqJson.getReqId());
			List<ReqrespauthdetailsVO> list = reqrespauthdetailsService.getPendingCollect(reqJson);
			if (!list.isEmpty()) {
				respJson.setReqrespauthdetailsVO(list);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.MSGID_SUCCESS);
			} else {
				
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for pendingCollect=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/pendingCollectCount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String pendingCollectCount(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == reqrespauthdetailsService) {
				reqrespauthdetailsService = new ReqrespauthdetailsService();
			}
			respJson.setReqId(reqJson.getReqId());
			int count = reqrespauthdetailsService.getPendingCollectCount(reqJson);
			if (0 != count) {
				respJson.setPendingCollectCount(count + "");
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.MSGID_SUCCESS);
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for pendingCollectCount=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/regApp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String regApp(String reqStr , @HeaderParam("sapp")
	String source ,@HeaderParam("snode") String node ) {
		log.info("Request received for RegApp:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			reqJson.setSource(source);
			reqJson.setNode(node);
			respJson = registrationHomeService.registerUser(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for regApp=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/regGCMId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String regGCMId(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.saveGCMTokenInrgistration(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for regGCMId=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/reqListAccPvd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String reqListAccPvd(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		if (null == listAccountService) {
			listAccountService = new ListAccountProviderService();
		}
		try {
			respJson = listAccountService.selectAccountProviderList();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for reqListAccPvd=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/setDefAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String setDefAccount(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.setDefault(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for setDefAccount=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/updateBeneficiary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateBeneficiary(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == beneficiaryService) {
				beneficiaryService = new BeneficiaryService();
			}
			respJson = beneficiaryService.updateBeneficiary(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for updateBeneficiary=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/updateProfile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateProfile(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.updateUDetails(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for updateProfile=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/sendOtp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String sendOtp(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		respJson.setMsgId("0");
		respJson.setMsg("SUCCESS");
		try {
			if (null == acqOtpService) {
				acqOtpService = new AcqOtpService();
			}
			acqOtpService.send(JSONConvert.getReqJson(reqStr));
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for sendOtp=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/validateOtp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String validateOtp(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId("0");
		respJson.setMsg("SUCCESS");
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for validate OTP=" + deviceid);
			if (null == acqOtpService) {
				acqOtpService = new AcqOtpService();
			}
			respJson = acqOtpService.validate(reqJson,false);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for validateOtp=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/addCrashAnalyticsInfo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addExceptionLog(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson(ConstantI.MSGID_SUCCESS, ConstantI.SUCCESS_STRING);
		respJson.setReqId(reqJson.getReqId());
		try {
			if (null == exceptionLogService) {
				exceptionLogService = new CrashAnalyticsInfoService();
			}
			respJson = exceptionLogService.addExceptionLog(reqStr);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for addCrashAnalyticsInfo=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/aboutUs")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAboutUs(String reqStr) {
		log.info("Request received:" + reqStr);
		RespJson respJson = new RespJson(ConstantI.MSGID_SUCCESS, ConstantI.SUCCESS_STRING);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		respJson.setReqId(reqJson.getReqId());
		String aboutUs = null;
		try {
			if (null == aboutUsInfoService) {
				aboutUsInfoService = new AboutUsInfoService();
			}
			aboutUs = aboutUsInfoService.getAboutUs();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_SUCCESS_ABOUTUS.getCode());
			respJson.setWebsite(aboutUs);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for aboutUs=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/feedback")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String provideFeedback(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.saveFeedback(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for feedback=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getVpaByAcc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getVpaByAccount(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.getAllVPAByAccount(reqJson);
		} catch (final Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getVpaByAcc=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	/**
	 * @param inputStream
	 * @return list all accounts binds with a @param virtualId
	 */
	@POST
	@Path("/getAllAccountsByVpa")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllAccountsByVpa(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson(ConstantI.MSGID_SUCCESS, ConstantI.SUCCESS_STRING);
		if (null == customeraccountHomeService) customeraccountHomeService = new CustomeraccountHomeService();
		
		try {
			respJson = customeraccountHomeService.getAccountByVpaAndRegId(reqJson.getRegId(), reqJson.getVirtualId());
			respJson.setReqId(reqJson.getReqId());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setReqId(reqJson.getReqId());
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getAllAccountsByVpa=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	/**
	 * @param inputStream,
	 *            appOs
	 * @return list all versions supported by a particular app [android, ios]
	 */
	@POST
	@Path("/getSupportedVersions")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getSupportedVersions(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson(ConstantI.MSGID_SUCCESS, ConstantI.SUCCESS_STRING);
		if (null == versionManagementService) versionManagementService = new VersionManagementService();
		
		try {
			respJson.setReqId(reqJson.getReqId());
			if (Util.isNullOrEmpty(reqJson.getAppOs())) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ConstantI.FAILURE_STRING);
				String finalRespJson = "";
				try {
					finalRespJson = DecryptionUtility.encrypt(JSONConvert.getJsonStr(respJson));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				}
				return finalRespJson;
			}
			
			final List<String> appversions = versionManagementService.getAllVersions(reqJson.getAppOs());
			respJson.setAppversions(appversions);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getSupportedVersions=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getNonPrimaryAccVPA")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getNonPrimaryAccVPA(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson(ConstantI.MSGID_SUCCESS, ConstantI.SUCCESS_STRING);
		try {
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.getNonPrimaryAccVPA(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getNonPrimaryAccVPA=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getMaskedAccDet")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMaskedAccDet(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.getMaskedAcc(reqJson);
			
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getMaskedAccDet=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getAppVersion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAppVersion(String reqStr, @HeaderParam("deviceId")
	String deviceId) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			if(!StringUtils.isEmpty(deviceId)) {
				deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			}
			log.info("Decrypted deviceId for getAppVersion=" + deviceid);
			if (null == appVersionService) {
				appVersionService = new AppVersionService();
			}
			respJson = appVersionService.getAppVersion(reqJson, deviceid);
			if (null == securityEncryptionService) {
				securityEncryptionService = new SecurityEncryptionService();
			}
			respJson = securityEncryptionService.insertDeviceId(deviceid, respJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getAppVersion=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/getDefInfo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getDefInfo(String reqStr, @HeaderParam("deviceId")
	String deviceId) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for getDefInfo=" + deviceid);
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson = customeraccountHomeService.getDefInfo(reqJson);
			
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for getDefInfo=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/changeDeviceIMEI")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String chengeDeviceIMEI(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for change device IMEI=" + deviceid);
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
				respJson = registrationHomeService.updateDeviceAndImei(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for changeDeviceIMEI=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	
	@POST
	@Path("/changeDeviceWithOtp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String chengeDeviceMismatch(String reqStr, @HeaderParam("encryptionKey")
	String encryptionKey, @HeaderParam("deviceId")
	String deviceId, @HeaderParam("authToken")
	String authToken) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String encKey=ConstantI.KEY_DECRY_HEADER;
		String deviceid="";
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			log.info("Decrypted deviceId for change divice with OTP=" + deviceid);
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			if (null == acqOtpService) {
				acqOtpService = new AcqOtpService();
			}
			respJson = acqOtpService.validate(reqJson,false);
			log.info("Return validate otp status "+respJson.getMsgId());
			if (respJson.getMsgId()=="0") {
				respJson = registrationHomeService.updateDeviceAndImei(reqJson);
			}
			
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for changeDeviceWithOtp=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/updtSilentDeviceIMEI")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updtSilentDeviceIMEI(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == silentsmsService) {
				silentsmsService = new SilentsmsHomeService();
			}//deviceid null update in registration
			respJson = silentsmsService.updateDeviceIMEI(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for updtSilentDeviceIMEI=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	//Mandate
	
	@POST
	@Path(ConstantI.REQUEST_RECEVIED_MANDAT)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String requestreceviedmandate(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			log.info("Handling request for pendingCollect "+ reqStr);
			respJson.setReqId(reqJson.getReqId());
			if (null == reqRespAuthMandatedetailsService) {
				reqRespAuthMandatedetailsService = new ReqRespAuthMandatedetailsService();
			}
			List<ReqRespAuthMandatedetailsVO> list = reqRespAuthMandatedetailsService.getPendingMandateCollect(reqJson);
			if (list != null && !list.isEmpty()) {
				respJson.setReqRespAuthMandatedetailsVO(list);
				respJson.setPendingCollectCount(String.valueOf(list.size()));
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			} else {
				respJson.setReqRespAuthMandatedetailsVO(list);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
		} catch (Exception ex) {
			log.info(ex.getMessage(), ex);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for requestReceviedMandate=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path(ConstantI.MY_MANDATE)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String myMandate(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			log.info("Handling request for myMandate "+ reqStr);
			respJson.setReqId(reqJson.getReqId());
			if(customerMandateService==null) {
				customerMandateService=new CustomerMandateService();
			}
			List<CustomerMandateDTO> customerMandateDTOs=customerMandateService.myMandate(reqJson);
			if (customerMandateDTOs != null && !customerMandateDTOs.isEmpty()) {
				respJson.setCustomerMandateDTO(customerMandateDTOs);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			} else {
				respJson.setCustomerMandateDTO(customerMandateDTOs);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
		} catch (Exception ex) {
			log.info(ex.getMessage(), ex);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for myMandate=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}

	//not in use

	@POST
	@Path(ConstantI.MY_MANDATE_HISTORY)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String mandateHistory(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			log.info("Handling request for myMandate "+ reqStr);
			respJson.setReqId(reqJson.getReqId());
			if(customerMandateService==null) {
				customerMandateService=new CustomerMandateService();
			}
			List<CustomerMandateHistoryDto> customerMandateHistoryDtos=customerMandateService.mandateHistory(reqJson);
			if (customerMandateHistoryDtos != null && !customerMandateHistoryDtos.isEmpty()) {
				respJson.setCustomerMandateHistory(customerMandateHistoryDtos);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.MSGID_SUCCESS);
			} else {
				respJson.setCustomerMandateHistory(customerMandateHistoryDtos);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
		} catch (Exception ex) {
			log.info(ex.getMessage(), ex);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for mandateHistory=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	@POST
	@Path("/historymandates")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String getMadates(String reqStr) {
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			log.info("Handling request for GetAll Mandates"+reqStr);
			respJson.setReqId(reqJson.getReqId());
			if (null == mandateservicehome) {
				mandateservicehome = new MandateServiceHome();
			}
			List<MandatesHistory> list = mandateservicehome.getAllMandatesReq(reqJson);
			if (list != null && !list.isEmpty()) {
				respJson.setMandatehistory(list);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			} else {
				respJson.setMandatehistory(list);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
		} catch (Exception ex) {
			log.info(ex.getMessage(), ex);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for historymandates=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
	
	
	//For payee side get pending mendiates
	
		@POST
		@Path("/pendingmandate")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String getPendingMadates(String reqStr) {
			log.info("Request received:" + reqStr);
			ReqJson reqJson = JSONConvert.getReqJson(reqStr);
			RespJson respJson = new RespJson();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
			try {
				log.info("Handling request for pending Mandates :"+ reqStr);
				respJson.setReqId(reqJson.getReqId());
				if (null == mandateservicehome) {
					mandateservicehome = new MandateServiceHome();
				}
				List<MandatesHistory> list = mandateservicehome.getPendingMandatesReq(reqJson);
				if (list != null && !list.isEmpty()) {
					respJson.setMandatehistory(list);
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setMsg(ConstantI.MSGID_SUCCESS);
				} else {
					respJson.setMandatehistory(list);
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
				}
			} catch (Exception ex) {
				log.info(ex.getMessage(), ex);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}
			respJson.setReqId(reqJson.getReqId());
			log.info("response send for pendingmandate=" + respJson);
			return JSONConvert.getJsonStr(respJson);
		}

		@POST
		@Path("/signedQrVerify")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String verifySignedQr(String reqStr) {
			log.info("Request received:" + reqStr);
			ReqJson reqJson = JSONConvert.getReqJson(reqStr);
			RespJson respJson = new RespJson();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
			try {
				log.info("Handling request for signed QR : {}"+reqStr);
				respJson.setReqId(reqJson.getReqId());
				if (null == verificationSercice) {
					verificationSercice = new SignedQrVerificationService();
				}
				
			boolean isVerified = verificationSercice.findByAddr(reqJson);
			if (isVerified) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.MSGID_SUCCESS);
			}else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.NOT_VERIFY.getCode());
			}
			} catch (Exception ex) {
				log.info(ex.getMessage(), ex);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}
			respJson.setReqId(reqJson.getReqId());
			log.info("response send for signedQrVerify=" + respJson);
			return JSONConvert.getJsonStr(respJson);
		}
		
		@POST
		@Path("/qrSignDetails")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String getQrSignInfo(String reqStr) {
			log.info("Request received:" + reqStr);
			ReqJson reqJson = JSONConvert.getReqJson(reqStr);
			RespJson respJson = new RespJson();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
			try {
				log.info("Handling request for qrSignDetails QR : {}"+reqStr);
              QRResponse qrResp = null;
              log.info("Request after convert json:" + reqJson);
      		  respJson.setReqId(reqJson.getReqId());
              if (null == qrResp) {
            	  qrResp = new QRResponse();
			}
            PrivateKey privateKey = null;
  			PublicKey publicKey = null;
  			List<Object> list = ECSignQRUtil.readKey("keyfile.txt");
  			for (Object obj : list) {
  				if (obj instanceof PrivateKey) {
  					privateKey = (PrivateKey) obj;
  				} else if (obj instanceof PublicKey) {
  					publicKey = (PublicKey) obj;
  				} else {
  				}
  			}
  			String out= ECSignQRUtil.signQR(privateKey, reqJson.getQrParam());
              log.info("Encripted qrparam is"+out);
              qrResp.setSign(out);
              qrResp.setQrParam(reqJson.getQrParam()+"&sign="+out+"&orgId=159164");
              qrResp.setStatus(reqJson.getStatus());
              qrResp.setType(reqJson.getType());
				if (qrResp != null) {
					qrResp.setOrgId("159164");
					respJson.setQrDetatls(qrResp);
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setMsg(ConstantI.MSGID_SUCCESS);
				} else {
					respJson.setQrDetatls(qrResp);
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
				}
			} catch (Exception ex) {
				log.info(ex.getMessage(), ex);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}
			respJson.setReqId(reqJson.getReqId());
			log.info("response send for qrSignDetails=" + respJson);
			return JSONConvert.getJsonStr(respJson);
		}
		
			//Time Pay
	
	//is validate customer check  login without passcode by @Barun bhai
	@POST
	@Path("/loginwops")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginwithoutPasscode(String reqStr, @HeaderParam("deviceId") String deviceId,
			@HeaderParam("sapp") String sapp, @HeaderParam("snode") String snode) {
		log.info(">>>loginwops: snode=" + snode + " ,sapp" + sapp + " ,deviceId=" + deviceId + ",reqStr=" + reqStr);
		log.info("Request received:" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		String deviceid="",source="",node="";
		String encKey=ConstantI.KEY_DECRY_HEADER;
		try {
			deviceid = aESEncryptionUtility.decrypt(deviceId, encKey);
			source = aESEncryptionUtility.decrypt(sapp, encKey);
			node = aESEncryptionUtility.decrypt(snode, encKey);
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.selectLogin1(reqJson, deviceid);

			if (null == useractivityHomeService) {
				useractivityHomeService = new UseractivityHomeService();
			}
			useractivityHomeService.insertDetails1(reqStr, respJson, 1, new Date());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("response send for loginwops=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}
}
