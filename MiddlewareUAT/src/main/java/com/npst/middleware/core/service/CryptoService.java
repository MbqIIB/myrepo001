package com.npst.middleware.core.service;

import com.npst.middleware.obj.CryptoResp;

public interface CryptoService {
	CryptoResp decrypt(String base64Data);
}
