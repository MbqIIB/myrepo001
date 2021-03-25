package com.npst.middleware.cbs.service;

import org.jpos.iso.ISOMsg;

public interface CBSConSocket {
	ISOMsg send(final ISOMsg isoObj) throws Exception;
	ISOMsg sendChannel(final ISOMsg isoObj) throws Exception;
	ISOMsg sendATMChannel(final ISOMsg isoObj) throws Exception;
}
