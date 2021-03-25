package com.npst.upiserver.dao;

public interface BlockedByCustomerDao {
	boolean isBlockedVpa(String blockedVpa, Long regId);
	void insert(String payeeAddr, Long regId);
	public void insertPayeeAddr(String payeeAddr, long regId);
	boolean isBlockedByCustomer(String vpa, long regid);
}
