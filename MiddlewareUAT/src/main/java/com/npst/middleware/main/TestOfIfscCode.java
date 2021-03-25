package com.npst.middleware.main;

import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.cbs.service.impl.CBSProcessImpl;
import com.npst.middleware.obj.ReqResp;

public class TestOfIfscCode {

	public static void main(String[] args) {
		CBSProcess pro=new CBSProcessImpl();
		pro.fatchAccounts(new ReqResp(), "Ct001", "customer01", "1234567890");
	}
}
