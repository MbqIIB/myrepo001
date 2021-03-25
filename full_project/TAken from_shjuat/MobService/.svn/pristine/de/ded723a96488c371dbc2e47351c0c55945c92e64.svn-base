package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.UseractivityHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.Useractivity;

public class UseractivityHomeService {

	private static UseractivityHome useractivityHome = null;
	private static final Logger log = Logger.getLogger(UseractivityHomeService.class);

	public void insertDetails(ReqJson reqJson, RespJson respJson, Integer type, Date dt) {
		respJson.setReqId(reqJson.getReqId());
		try {
			if (null == useractivityHome) {
				useractivityHome = new UseractivityHome();
			}
			Useractivity userDetails = new Useractivity();
			userDetails.setActivitydt(dt);
			userDetails.setDescription(Integer.parseInt(respJson.getMsgId()));
			userDetails.setRegid(Integer.parseInt(reqJson.getRegId()));
			userDetails.setType(type);
			userDetails.setPassword(reqJson.getNewLoginPin());
			useractivityHome.insertDetails(userDetails);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
	}
	
	
	public void insertDetails1(String reqStr, RespJson respJson, Integer type, Date dt) {
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		try {
			if (null == useractivityHome) {
				useractivityHome = new UseractivityHome();
			}
			Useractivity userDetails = new Useractivity();
			userDetails.setActivitydt(dt);
			userDetails.setDescription(Integer.parseInt(respJson.getMsgId()));
			userDetails.setRegid(Integer.parseInt(reqJson.getRegId()));
			userDetails.setType(type);
			userDetails.setPassword(reqJson.getNewLoginPin());
			useractivityHome.insertDetails(userDetails);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
	}
}
