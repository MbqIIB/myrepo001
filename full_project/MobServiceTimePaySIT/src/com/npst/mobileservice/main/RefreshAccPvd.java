package com.npst.mobileservice.main;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.npst.mobileservice.cache.impl.AccountListProviderCache;

@Path("/RefreshAccPvd")

public class RefreshAccPvd {
	private static final Logger log = Logger.getLogger(RefreshAccPvd.class);

	@GET
	@Path("/mobileNoSend")
	public String mobileNoSend() {
		try {
			log.info("Acc Pvd List going to refresh");
			AccountListProviderCache.refresh();
			log.info("Acc Pvd List refresh");
			return "SUCCESS";
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return "ERROR";

	}
}
