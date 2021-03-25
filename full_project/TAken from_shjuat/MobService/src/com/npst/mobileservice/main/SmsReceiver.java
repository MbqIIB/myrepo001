package com.npst.mobileservice.main;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.service.SilentsmsHomeService;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.GupShupSms;

@Path("/SmsReceiver")
public class SmsReceiver {
	private static final Logger log = Logger.getLogger(SmsReceiver.class);
	static SilentsmsHomeService silentsmsHomeService = null;

	@GET
	@Path("/mobileNoSend")
	public String mobileNoSend(@DefaultValue("") @QueryParam("keyword") String keyword,
			@DefaultValue("") @QueryParam("phonecode") String phonecode,
			@DefaultValue("") @QueryParam("location") String location,
			@DefaultValue("") @QueryParam("carrier") String carrier,
			@DefaultValue("") @QueryParam("content") String content,
			@DefaultValue("") @QueryParam("msisdn") String msisdn,
			@DefaultValue("") @QueryParam("timestamp") String timestamp) {

		log.info("keyword for mobileNoSend: " + keyword);
		log.info("phonecode for mobileNoSend: " + phonecode);
		log.info("location for mobileNoSend: " + location);
		log.info("carrier for mobileNoSend: " + carrier);
		log.info("content for mobileNoSend: " + content);
		log.info("msisdn for mobileNoSend: " + msisdn);
		log.info("timestamp for mobileNoSend: " + timestamp);
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);

		try {
			if (null == silentsmsHomeService) {
				silentsmsHomeService = new SilentsmsHomeService();
			}
			silentsmsHomeService.smsLog(keyword, phonecode, location, carrier, content, msisdn, timestamp);
			GupShupSms.send(msisdn, "registration sms");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return "";
	}

	/*
	 * @GET
	 * 
	 * @Path("/disableTid") public String disableTid(@QueryParam("tid") String tid)
	 * { RespJson respJson = new RespJson();
	 * respJson.setMsgId(ConstantI.MSGID_SUCCESS);
	 * respJson.setMsg(ConstantI.SUCCESS_STRING);
	 * 
	 * try { if (null == silentsmsHomeService) { silentsmsHomeService = new
	 * SilentsmsHomeService(); } silentsmsHomeService.disableTid(tid);
	 * 
	 * } catch (Exception e) { StringWriter s; e.printStackTrace(new PrintWriter(s =
	 * new StringWriter())); e.printStackTrace(); log.info(s);
	 * respJson.setMsgId(ConstantI.MSGID_FAIL);
	 * respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode()); }
	 * return ""; }
	 */

}
