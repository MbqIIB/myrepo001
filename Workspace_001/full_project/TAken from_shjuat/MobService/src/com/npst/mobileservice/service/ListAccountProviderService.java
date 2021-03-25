package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.npst.mobileservice.cache.impl.AccountListProviderCache;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;

public class ListAccountProviderService {
	private static final Logger log = Logger.getLogger(ListAccountProviderService.class);

	public RespJson selectAccountProviderList() {
		log.info("");
		RespJson respJson = new RespJson();
		try {

			AccountListProviderCache cache = AccountListProviderCache.getInstance();
			if (null != cache) {
				respJson.setAccountProvidersList(cache.get("accountProviders"));
			}
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ConstantI.FAILURE_STRING);
		} finally {

		}
		log.debug("return successfully with respJson:" + respJson);
		return respJson;
	}

}
