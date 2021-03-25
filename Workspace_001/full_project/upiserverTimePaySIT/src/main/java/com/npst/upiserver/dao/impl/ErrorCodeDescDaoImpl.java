package com.npst.upiserver.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ErrorCodeDescDao;
import com.npst.upiserver.entity.ErrorCodeDesc;
import com.npst.upiserver.repo.ErrorCodeDescRepository;
import com.npst.upiserver.util.Util;

@Component
public class ErrorCodeDescDaoImpl implements ErrorCodeDescDao {

	private static final Logger log = LoggerFactory.getLogger(ErrorCodeDescDaoImpl.class);

	@Autowired
	ErrorCodeDescRepository errorCodeDesc;

	@Override
	public String errValiAddDesc(String errCode) {
		String errorDesc = null;
		try {
			errorDesc = errorCodeDesc.findErrdescByUpierrcode(errCode);
			errorDesc = Constant.VALIADDERRDESC + ConstantI.CONST_SINGLE_BLANK + errorDesc;
		} catch (Exception e) {
			errorDesc = ConstantI.CONST_BLANK;
			log.error(e.getMessage(), e);
		}
		return errorDesc;
	}

	@Override
	public String getErrorDescBalEnq(String errCode) {
		String errorDesc = null;
		try {
			errorDesc = errorCodeDesc.findErrdescByUpierrcode(errCode);
			errorDesc = Constant.BALERRDESC +  ConstantI.CONST_SINGLE_BLANK + errorDesc;
		} catch (Exception e) {
			errorDesc = ConstantI.CONST_BLANK;
			log.error(e.getMessage(), e);
		}
		return errorDesc;
	}

	@Override
	public String getErrorListAcc(String errCode) {
		String errorDesc = null;
		try {
			errorDesc = errorCodeDesc.findErrdescByUpierrcode(errCode);
			errorDesc = Constant.LISTERRDESC +  ConstantI.CONST_SINGLE_BLANK + errorDesc;
		} catch (Exception e) {
			errorDesc = ConstantI.CONST_BLANK;
			log.error(e.getMessage(), e);
		}
		return errorDesc;
	}

	@Override
	public String getErrorRespOtp(String errCode) {
		String errorDesc = null;
		try {
			errorDesc = errorCodeDesc.findErrdescByUpierrcode(errCode);
			errorDesc = Constant.BALERRDESC +  ConstantI.CONST_SINGLE_BLANK + errorDesc;
		} catch (Exception e) {
			errorDesc =ConstantI.CONST_BLANK;
			log.error(e.getMessage(), e);
		}
		return errorDesc;
	}
	
	@Override
	public String errDesc(String errString) {
		ArrayList<String> resplist = Util.strSplit(errString, ConstantI.CHAR_CONST_TILD);
		String msg = ConstantI.CONST_BLANK;
		int temp = 0;
		try {
			for (String string : resplist) {
				List<ErrorCodeDesc> results = errorCodeDesc.findByUpierrcode(string);
				for (ErrorCodeDesc errCodeDesc : results) {
					if (0 == temp) {
						if (results.size() == 1) {
							msg = errCodeDesc.getErrdesc();
							temp++;
						}
					} else {
						if (1 == temp) {
							msg = msg +  ConstantI.CONST_SINGLE_BLANK + Constant.ERRDESC +  ConstantI.CONST_SINGLE_BLANK + errCodeDesc.getErrdesc();
							temp++;
						} else {
							msg = msg + ConstantI.CONST_AND + errCodeDesc.getErrdesc();
						}
					}
				}
			}
		} catch (Exception e) {
			msg = ConstantI.CONST_BLANK;
			log.error(e.getMessage(), e);
		}
		return msg;
	}

	@Override
	public String errCreDesc(String errCode) {
		String errorDesc = null;
		try {
			errorDesc = errorCodeDesc.findErrdescByUpierrcode(errCode);
			errorDesc = Constant.ERRDESC  +  ConstantI.CONST_SINGLE_BLANK + errorDesc;
		} catch (Exception e) {
			errorDesc = ConstantI.CONST_BLANK;
			log.error(e.getMessage(), e);
		}
		return errorDesc;
	}
}
