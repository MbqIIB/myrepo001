package com.npst.mobileservice.main;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.service.MobupireqrespjsonService;
import com.npst.mobileservice.service.MobupireqrespjsonidService;
import com.npst.mobileservice.service.RegistrationHomeService;
import com.npst.mobileservice.util.AesEncryption;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Validations;
import com.npst.upi.hor.Mobupireqrespjson;
import com.npst.upi.hor.Mobupireqrespjsonid;

@Path("/Upi")
public class UpiService {
	private static final Logger log = Logger.getLogger(UpiService.class);
	private static MobupireqrespjsonService mobupireqrespjsonService = null;
	private static MobupireqrespjsonidService mobupireqrespjsonidService = null;
	private static RegistrationHomeService registrationHomeService = null;
	private static AesEncryption aESEncryptionUtility = new AesEncryption();

	@POST
	@Path("/resp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getResponse(String reqStr) {
		log.info("");
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.MSGID_SUCCESS);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			log.info("IdPk[" + reqJson.getIdPk() + "]");
			respJson.setTxnType(reqJson.getTxnType());
			respJson.setIdPk(reqJson.getIdPk() + "");
			if (null == mobupireqrespjsonidService) {
				mobupireqrespjsonidService = new MobupireqrespjsonidService();
			}
			Mobupireqrespjsonid mobupireqrespjsonid = mobupireqrespjsonidService.findById(Integer.parseInt(reqJson.getIdPk()));
			int flag = mobupireqrespjsonid.getFlag();
			log.info("Flag After getting Mobile UPI Resp " + flag);
			if (ConstantI.UPI_REQ_RESP_D == flag) {
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
			
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("Upi service response send=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}

	@POST
	@Path("/req")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String req(String reqStr , @HeaderParam("sapp")
	String source , @HeaderParam("snode")
	String node) { 
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.MSGID_SUCCESS);
		log.info("Request received for Upi service:" + reqStr);
		try {
			if (null == registrationHomeService) {
				registrationHomeService = new RegistrationHomeService();
			}
			log.info("Request paring for upiservice:" + reqJson);
			
			if (null == mobupireqrespjsonService) {
				mobupireqrespjsonService = new MobupireqrespjsonService();
			}
			if (Validations.jsonValidation(reqJson)) {
			reqJson.setSource(source);
			reqJson.setNode(node);
			log.info("flag of upiservice:" + reqJson.getTxnFlag());
				if ("ChkTxn".equalsIgnoreCase(reqJson.getTxnFlag())) {
					reqStr = JSONConvert.getJsonStr(reqJson);
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
		respJson.setReqId(reqJson.getReqId());
		log.info("Upi service request send=" + respJson);
		return JSONConvert.getJsonStr(respJson);
	}

}
