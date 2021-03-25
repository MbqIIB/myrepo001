package com.npst.mobileservice.service;

public enum SilentSMSStatus {

	ACTIVE(1),PENDING(2),INACTIVE(3);
	
	private final int status;
	
	SilentSMSStatus(int status) {
		this.status=status;
	}

	public int getStatus() {
		return status;
	}
	
	
}
