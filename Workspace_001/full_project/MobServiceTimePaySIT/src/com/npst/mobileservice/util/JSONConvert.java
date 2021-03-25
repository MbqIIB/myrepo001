package com.npst.mobileservice.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.ReqResp;
import com.npst.mobileservice.object.RespJson;

public class JSONConvert {
	static Logger log = Logger.getLogger(JSONConvert.class.getName());

	public static String getJsonStr(Object obj) {
		String jsonStr = null;
		try {
			log.trace("req[" + obj.toString() + "]");
			// Gson gson = new GsonBuilder().serializeNulls().create();
			Gson gson = new Gson();
			jsonStr = gson.toJson(obj);
			log.trace("return jsonStr[" + jsonStr + "]");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			log.error(s);
			throw new RuntimeException(e);
		}
		return jsonStr;
	}

	public static ReqJson getReqJson(String reqStr) {
		log.trace("reqStr[" + reqStr + "]");
		ReqJson reqJson = new ReqJson();
		// Gson gson = new GsonBuilder().serializeNulls().create();
		Gson gson = new Gson();
		reqJson = gson.fromJson(reqStr, reqJson.getClass());
		if (null == reqJson) {
			reqJson = new ReqJson();
		}
		log.trace("return reqJson[" + reqJson.toString() + "]");
		return reqJson;
	}

	public static ReqJson getReqJsonToken(String resp) {
		log.trace("resp[" + resp + "]");
		ReqJson reqJson = new ReqJson();
		// Gson gson = new GsonBuilder().serializeNulls().create();
		Gson gson = new Gson();
		reqJson = gson.fromJson(resp, reqJson.getClass());
		if (null == reqJson) {
			reqJson = new ReqJson();
		}
		log.trace("return reqJson[" + reqJson.toString() + "]");
		return reqJson;
	}

	public static ReqResp getReqResp(String resp) {
		log.trace("resp[" + resp + "]");
		ReqResp reqResp = new ReqResp();
		// Gson gson = new GsonBuilder().serializeNulls().create();
		Gson gson = new Gson();
		reqResp = gson.fromJson(resp, reqResp.getClass());
		if (null == reqResp) {
			reqResp = new ReqResp();
		}
		log.trace("return reqJson[" + reqResp.toString() + "]");
		return reqResp;
	}

	public static Object convertToObject(final String request, final Class<?> clazz) {
		log.trace("request[" + request + "]");
		Gson gson = new Gson();
		return gson.fromJson(request, clazz);
	}

	public static RespJson getRespJson(String respJsonStr) {
		log.trace("resp[" + respJsonStr + "]");
		RespJson respJson = new RespJson();
		// Gson gson = new GsonBuilder().serializeNulls().create();
		Gson gson = new Gson();
		respJson = gson.fromJson(respJsonStr, respJson.getClass());
		if (null == respJson) {
			respJson = new RespJson();
		}
		log.trace("return respJson[" + respJson.toString() + "]");
		return respJson;
	}

	// public static String jsonConvertor(String jsonString) {
	// org.json.JSONObject jsonObject = null;
	// try {
	// jsonObject = new org.json.JSONObject(jsonString);
	// jsonObject.remove("authToken");
	// } catch (ParseException e) {
	//
	// e.printStackTrace();
	// }
	// return jsonObject.toString();
	// }

}
