package com.npst.middleware.util;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonMan {
	static Logger log = Logger.getLogger(JsonMan.class.getName());
	
	public static String getJSONStr(final Object req) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonStr = gson.toJson(req);
		return jsonStr;
	}
}
