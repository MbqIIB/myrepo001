package com.npst.upiserver.dao;

public interface AuditUpiXmlDao {

	void insert(String xmlMsg, String txnType, String reqOrResp);

}
