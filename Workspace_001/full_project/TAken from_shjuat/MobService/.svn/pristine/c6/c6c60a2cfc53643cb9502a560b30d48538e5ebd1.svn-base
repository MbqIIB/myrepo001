package com.npst.mobileservice.main;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npst.mobileservice.dao.RegistrationHome;
import com.npst.mobileservice.object.CustomeraccountVO;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.service.AppVersionService;
import com.npst.mobileservice.service.CustomeraccountHomeService;
import com.npst.mobileservice.service.MobupireqrespjsonService;
import com.npst.mobileservice.service.MobupireqrespjsonidService;
import com.npst.mobileservice.service.RegistrationHomeService;
import com.npst.mobileservice.service.SecurityEncryptionService;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Validations;
import com.npst.upi.hor.Mobupireqrespjson;
import com.npst.upi.hor.Mobupireqrespjsonid;
import com.npst.upi.hor.Registration;

@Path("/SecurePsp")
public class SecurePspCon {
	private static final Logger log = Logger.getLogger(SecurePspCon.class);
	private static AppVersionService appVersionService;
	private static SecurityEncryptionService securityEncryptionService;

	private static MobupireqrespjsonService mobupireqrespjsonService = null;
	private static MobupireqrespjsonidService mobupireqrespjsonidService = null;
	private static RegistrationHomeService registrationHomeService = null;
	private static CustomeraccountHomeService customeraccountHomeService;
	private static RegistrationHome registrationHome;

	@POST
	@Path("/isVpaAvailable")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkVpa(String reqStr) {
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PRESENT_VIRTUALID.getCode());
		try {
			respJson.setReqId(reqJson.getReqId());
			String vpa = reqJson.getVirtualId();
			if (StringUtils.isNotBlank(vpa)) {
				if (null == customeraccountHomeService) {
					customeraccountHomeService = new CustomeraccountHomeService();
				}
				if (customeraccountHomeService.isVpaAvailForNewAddition(vpa)) {
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setMsg(ConstantI.SUCCESS_STRING);
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
		return JSONConvert.getJsonStr(respJson);
	}
	
	
	@POST
	@Path("/addUpdateAcnts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addUpdateAccount(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		try {
			respJson.setReqId(reqJson.getReqId());
			log.info("getPrivateF="+reqJson.getPrivateF());
			TypeToken<List<CustomeraccountVO>> list = new TypeToken<List<CustomeraccountVO>>() {};
			List<CustomeraccountVO> reqAccountList=new Gson().fromJson((String)reqJson.getPrivateF(),list.getType());
			log.info(">>>>>>>>>>>>>>>reqAccountList="+reqAccountList);
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson=customeraccountHomeService.addUpdateAccount(reqJson,reqAccountList);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		return JSONConvert.getJsonStr(respJson);
	}
	
	


	@POST
	@Path("/getAddedMAc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAccounts(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		try {
			respJson.setReqId(reqJson.getReqId());
			String regId = reqJson.getRegId();
			if (StringUtils.isNotBlank(regId) && StringUtils.isNotBlank(reqJson.getMobileNo())) {
				if (null == customeraccountHomeService) {
					customeraccountHomeService = new CustomeraccountHomeService();
				}
				respJson = customeraccountHomeService.getAddedAccount(reqJson);
			} else {
				log.warn("error validation");
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
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path("/merchRegVpa")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String merchRegVpa(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			if (customeraccountHomeService.isVpaAvailForNewAddition(reqJson.getVirtualId())) {
				registrationHomeService.registerSMerchant(reqJson, respJson);
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PRESENT_VIRTUALID.getCode());
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
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path("/getAddedAcc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAddedAcc(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			reqJson.getMobileNo();
			respJson.setReqId(reqJson.getReqId());
			if (StringUtils.isNotBlank(reqJson.getMobileNo())) {
				if (reqJson.getMobileNo().length() == 10) {
					reqJson.setMobileNo("91" + reqJson.getMobileNo());
				}
			}
			// reqJson.getDeviceId();
			if (registrationHome == null) {
				registrationHome = new RegistrationHome();

			}

			List<Registration> listOut = registrationHome.getUserByMobNo(reqJson.getMobileNo());
			if (listOut != null && listOut.size() > 0) {
				for (Registration rr : listOut) {
					if (rr.getStatus() == 1)
						reqJson.setRegId(rr.getRegid() + "");
					reqStr = JSONConvert.getJsonStr(reqJson);
					break;
				}
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
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path("/isCustomerExist") //
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String isCustomerExist(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			respJson.setReqId(reqJson.getReqId());
			reqJson.getMobileNo();
			if (StringUtils.isNotBlank(reqJson.getMobileNo())) {
				if (reqJson.getMobileNo().length() == 10) {
					reqJson.setMobileNo("91" + reqJson.getMobileNo());
				}
			}
			// reqJson.getDeviceId();
			if (registrationHome == null) {
				registrationHome = new RegistrationHome();

			}
			List<Registration> listOut = registrationHome.getUserByMobNo(reqJson.getMobileNo());
			if (listOut != null && listOut.size() > 0) {
				for (Registration rr : listOut) {
					if (rr.getStatus() == 1)
						reqJson.setRegId(rr.getRegid() + "");
					reqStr = JSONConvert.getJsonStr(reqJson);
					break;
				}
			}

			respJson = customeraccountHomeService.getAddedAccount(reqJson);
			if (respJson != null && respJson.getCustomeraccountVOs() != null) {
				respJson.setCustomeraccountVOs(null);
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
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path("/getAppVerId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAppVersion(String reqStr, @HeaderParam("deviceId") String deviceId,
			@HeaderParam("sapp") String source, @HeaderParam("snode") String node) {
		log.info("");
		log.info("getAppVerId source= " + source + ",node=" + node + "deviceId=" + deviceId + ",reqStr=" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		try {
			if (null == appVersionService) {
				appVersionService = new AppVersionService();
			}
			reqJson.setSource(source);
			reqJson.setNode(node);
			respJson.setReqId(reqJson.getReqId());
			respJson = appVersionService.getAppVerOtherPsp(reqJson, deviceId);
			if (null == securityEncryptionService) {
				securityEncryptionService = new SecurityEncryptionService();
			}
			respJson = securityEncryptionService.insertDeviceId(deviceId, respJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("final response=" + respJson);
		respJson.setReqId(reqJson.getReqId());
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path("/chkTxnByTpId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkTxnBytpId(String reqStr, @HeaderParam("deviceId") String deviceId,
			@HeaderParam("sapp") String source, @HeaderParam("snode") String node) {
		log.info("");
		log.info("chkTxnByTpId source= " + source + ",node=" + node + "deviceId=" + deviceId + ",reqStr=" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.MSGID_PENDING);
		respJson.setMsgId(ConstantI.MSGID_PENDING);
		try {
			if (null == appVersionService) {
				appVersionService = new AppVersionService();
			}
			respJson.setReqId(reqJson.getReqId());
			log.info("tpId=" + reqJson.getTpId() + " ,idPk=" + reqJson.getIdPk());
			reqJson.setSource(source);
			reqJson.setNode(node);
			return getResponseByTpIdOrIdPk(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("final response=" + respJson);
		respJson.setReqId(reqJson.getReqId());
		return JSONConvert.getJsonStr(respJson);
	}

	public static String getResponseByTpIdOrIdPk(final ReqJson reqJson) {
		log.info("");
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.MSGID_PENDING);
		respJson.setMsgId(ConstantI.MSGID_PENDING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			log.info("TpId=" + reqJson.getTpId());
			respJson.setTpId(reqJson.getTpId());
			respJson.setIdPk(reqJson.getIdPk());
			if (null == mobupireqrespjsonidService) {
				mobupireqrespjsonidService = new MobupireqrespjsonidService();
			}
			Mobupireqrespjsonid mobupireqrespjsonid = null;
			if (StringUtils.isNotBlank(reqJson.getTpId())) {
				mobupireqrespjsonid = mobupireqrespjsonidService.findByTpId(reqJson.getTpId());
			} else if (StringUtils.isNotBlank(reqJson.getIdPk())) {
				mobupireqrespjsonid = mobupireqrespjsonidService.findById(Integer.parseInt(reqJson.getIdPk()));
			}
			log.info("mobupireqrespjsonid=" + mobupireqrespjsonid);
			if (mobupireqrespjsonid != null) {
				int flag = mobupireqrespjsonid.getFlag();
				log.info("Flag After getting Mobile UPI Resp " + flag);
				if (ConstantI.UPI_REQ_RESP_D == flag) {
					reqJson.setIdPk(mobupireqrespjsonid.getIdmobreqrespjsonid() + "");
					log.info("idPk=" + reqJson.getIdPk());
					respJson.setIdPk(reqJson.getIdPk());
					log.info("YOYO Got Response ");
					if (null == mobupireqrespjsonService) {
						mobupireqrespjsonService = new MobupireqrespjsonService();
					}
					Mobupireqrespjson instance = mobupireqrespjsonService.findById(Integer.parseInt(reqJson.getIdPk()));
					if (null != instance) {
						log.info("mobupireqrespjson[" + instance + "]");
					}
					respJson = JSONConvert.getRespJson(instance.getResp());
				} else if (1 == flag || 2 == flag) {
					log.info("NONO Wait not Got Response ");

					respJson.setMsg(ConstantI.MSGID_PENDING);
					respJson.setMsgId(ConstantI.MSGID_PENDING);
				} else {
					log.info("NONO Wait Failure Response ");
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
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path("/reqChkTxn")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String npciChkTxn(String reqStr, @HeaderParam("deviceId") String deviceId,
			@HeaderParam("sapp") String source, @HeaderParam("snode") String node) {
		log.info("");
		log.info("Acq reqChkTxn source= " + source + ",node=" + node + "deviceId=" + deviceId + ",reqStr=" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.MSGID_PENDING);
		respJson.setMsgId(ConstantI.MSGID_PENDING);
		respJson.setTxnFlag("ChkTxn");
		respJson.setTxnType("ChkTxn");
		try {
			log.info("tpId=" + reqJson.getTpId());
			log.info("IdPk=" + reqJson.getIdPk());
			reqJson.setSource(source);
			respJson.setReqId(reqJson.getReqId());
			reqJson.setNode(node);
			if (Validations.isValidChkTxn(reqJson)) {
				reqStr = JSONConvert.getJsonStr(reqJson);
				reqJson.setSource(source);
				reqJson.setNode(node);
				if (null == mobupireqrespjsonService) {
					mobupireqrespjsonService = new MobupireqrespjsonService();
				}
				respJson = mobupireqrespjsonService.save(reqStr, reqJson);
				respJson.setTxnType(reqJson.getTxnType());
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.INVALID_UPI_REQ.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("final response=" + respJson);
		respJson.setReqId(reqJson.getReqId());
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path("/respChkTxn")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String respChkTxn(String reqStr, @HeaderParam("deviceId") String deviceId,
			@HeaderParam("sapp") String source, @HeaderParam("snode") String node) {
		log.info("");
		log.info("acq respChkTxn source= " + source + ",node=" + node + "deviceId=" + deviceId + ",reqStr=" + reqStr);
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.MSGID_PENDING);
		respJson.setMsgId(ConstantI.MSGID_PENDING);
		try {
			if (null == appVersionService) {
				appVersionService = new AppVersionService();
			}
			log.info("tpId=" + reqJson.getTpId());
			reqJson.setSource(source);
			respJson.setReqId(reqJson.getReqId());
			reqJson.setNode(node);
			return getResponseByIdPk(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("final response=" + respJson);
		respJson.setReqId(reqJson.getReqId());
		return JSONConvert.getJsonStr(respJson);
	}

	public static String getResponseByIdPk(final ReqJson reqJson) {
		log.info("");
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.MSGID_PENDING);
		respJson.setMsgId(ConstantI.MSGID_PENDING);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			log.info("idPk=" + reqJson.getTpId());
			respJson.setTpId(reqJson.getTpId());
			if (null == mobupireqrespjsonidService) {
				mobupireqrespjsonidService = new MobupireqrespjsonidService();
			}
			Mobupireqrespjsonid mobupireqrespjsonid = mobupireqrespjsonidService
					.findById(Integer.parseInt(reqJson.getIdPk()));
			log.info("mobupireqrespjsonid=" + mobupireqrespjsonid);
			if (mobupireqrespjsonid != null) {
				int flag = mobupireqrespjsonid.getFlag();
				log.info("Flag After getting Mobile UPI Resp " + flag);
				if (ConstantI.UPI_REQ_RESP_D == flag) {
					reqJson.setIdPk(mobupireqrespjsonid.getIdmobreqrespjsonid() + "");
					log.info("idPk=" + reqJson.getIdPk());
					respJson.setIdPk(reqJson.getIdPk());
					log.info("YOYO Got Response ");
					if (null == mobupireqrespjsonService) {
						mobupireqrespjsonService = new MobupireqrespjsonService();
					}
					Mobupireqrespjson instance = mobupireqrespjsonService.findById(Integer.parseInt(reqJson.getIdPk()));
					if (null != instance) {
						log.info("mobupireqrespjson[" + instance + "]");
					}
					respJson = JSONConvert.getRespJson(instance.getResp());
				} else if (1 == flag || 2 == flag) {
					log.info("NONO Wait not Got Response ");
					respJson.setMsg(ConstantI.MSGID_PENDING);
					respJson.setMsgId(ConstantI.MSGID_PENDING);
				} else {
					log.info("NONO Wait Failure Response ");
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
		return JSONConvert.getJsonStr(respJson);
	}
}
