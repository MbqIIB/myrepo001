package com.npst.upiserver.constant;

public enum MandateStatus {

	MANDATE_INITIATED(0), MANDATE_FAILED(1), MANDATE_SUCCESS(2), MANDATE_REVOKED(3), MANDATE_BLOCKED(
			4), MANDATE_SPAMMED(5), MANDATE_DECLINED(7), MANDATE_PAUSED(8);

	private final int status;

	MandateStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
