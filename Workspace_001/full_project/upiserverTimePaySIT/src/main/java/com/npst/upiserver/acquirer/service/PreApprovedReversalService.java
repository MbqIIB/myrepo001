package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.npcischema.RespPay;

public interface PreApprovedReversalService {
	void preApprovedReversal(final long idPk, final RespPay respPay);
}
