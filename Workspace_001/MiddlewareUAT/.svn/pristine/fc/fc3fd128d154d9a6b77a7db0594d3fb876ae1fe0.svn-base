package com.npst.middleware.util;

import org.apache.log4j.Logger;
import org.jline.utils.Log;

import com.npst.middleware.obj.ReqResp;

public class PreCheckBalEnq
{
	private static final Logger LOG = Logger.getLogger(PreCheckBalEnq.class);

	public static boolean check(final ReqResp reqResp)
	{
		if (!ConstantNew.BAL_ENQ.equalsIgnoreCase(reqResp.getTxnType()))
		{
			reqResp.setRespCode(ConstantNew.CONST_12);
			reqResp.setRespMsg(ConstantNew.INVALID_TXN_TYPE);
			return true;
		}
		if (null == reqResp.getPayerAcNum() || null == reqResp.getPayerIfsc())
		{ 
			reqResp.setRespCode(ConstantNew.CONST_12);
			reqResp.setRespMsg(ConstantNew.INVALID_PAYER_ACC_NUM_IFSC);
			return true;
		}
		/*if (null == reqResp.getRrn() || "".equals(reqResp.getRrn()))
		{
			reqResp.setRespCode(ConstantNew.CONST_12);
			reqResp.setRespMsg(ConstantNew.INVALID_CUST_REF_RRN);
			return true;
		}*/
		if (null == reqResp.getCredMpin())
		{
			reqResp.setRespCode(ConstantNew.CONST_12);
			reqResp.setRespMsg(ConstantNew.INVALID_MPIN);
			return true;
		}
		return false;
	}
}
