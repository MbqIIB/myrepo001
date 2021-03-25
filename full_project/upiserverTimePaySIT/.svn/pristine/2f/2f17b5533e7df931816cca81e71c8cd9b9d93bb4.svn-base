package com.npst.upiserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.npst.upiserver.dto.ReqJson;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.dto.RespJson;
import com.npst.upiserver.entity.MobReqRespJson;

public class JsonMan {
	private static final Logger log = LoggerFactory.getLogger(JsonMan.class.getName());
	
	public static String getJSONStr(Object req) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonStr = gson.toJson(req);
		return jsonStr;
	}
	
	public static MobReqRespJson getMobreqrespjson(String message) {
		MobReqRespJson mobreqrespjson = new MobReqRespJson();
		Gson gson = new GsonBuilder().serializeNulls().create();
		mobreqrespjson = gson.fromJson(message, mobreqrespjson.getClass());
		return mobreqrespjson;
	}
	
	public static ReqResp getReqResp(String reqRespStr) {
		ReqResp reqResp = new ReqResp();
		Gson gson = new GsonBuilder().serializeNulls().create();
		reqResp = gson.fromJson(reqRespStr, reqResp.getClass());
		return reqResp;
	}
	
	public static ReqJson reqJsonObj(String resp) {
		ReqJson reqJson = new ReqJson();
		Gson gson = new GsonBuilder().serializeNulls().create();
		reqJson = gson.fromJson(resp, reqJson.getClass());
		return reqJson;
	}
	
	public static RespJson respJsonObj(String resp) {
		RespJson respJson = new RespJson();
		Gson gson = new GsonBuilder().serializeNulls().create();
		respJson = gson.fromJson(resp, respJson.getClass());
		return respJson;
	}

	public static <T> T getObjectFromStringBytes(byte[] reqRespStrBytes, Class<T> t) {
		String reqRespStr = null;
		try {
			reqRespStr = new String(reqRespStrBytes, "UTF-8");
			log.info("reqRespStr is as {} ",reqRespStr);
			Gson gson = new GsonBuilder().serializeNulls().create();
			log.info("JSON {} ",gson.fromJson(reqRespStr, t));
			return gson.fromJson(reqRespStr, t);
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("error in json to ReqResp conversion ie reqRespStr={} ,error={}", reqRespStr, e);
			ErrorLog.sendError(e, JsonMan.class);
		}
		return null;
	}
}
