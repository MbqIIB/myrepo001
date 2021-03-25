package com.npst.upiserver.constant;

public enum InitiationMode {

	MANDATE("11"),
	MANDATEQR("13");

	private final String mode;

	InitiationMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

}
