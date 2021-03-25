package com.npst.middleware.util;

import org.apache.log4j.Logger;

import com.npst.middleware.obj.ReqResp;

public class PreCheckTrans
{
	private static final Logger LOG = Logger.getLogger(PreCheckTrans.class);

	public static boolean check(final ReqResp reqResp)
	{
		if (!(ConstantNew.DEBIT.equalsIgnoreCase(reqResp.getTxnType()) || ConstantNew.REVERSAL.equalsIgnoreCase(reqResp.getTxnType()) || ConstantNew.CREDIT.equalsIgnoreCase(reqResp.getTxnType())||ConstantNew.MANDATE_DEBIT_BLOCK.equalsIgnoreCase(reqResp.getTxnType())))
		{
			reqResp.setRespCode(ConstantNew.CONST_12);
			reqResp.setRespMsg(ConstantNew.INVALID_TXN_TYPE);
			return true;
		}

		// DEBIT CASE PIN CHECK
		if (ConstantNew.DEBIT.equalsIgnoreCase(reqResp.getTxnType()))
		{
				if (null == reqResp.getCredMpin())
				{
					reqResp.setRespCode(ConstantNew.CONST_12);
					reqResp.setRespMsg(ConstantNew.INVALID_MPIN);
					return true;
				}
		}
		if (null != reqResp.getPayerAddrType())
		{
			if (ConstantNew.ACCOUNT.equalsIgnoreCase(reqResp.getPayerAddrType()))
			{
				if (null == reqResp.getPayerAcNum() || null == reqResp.getPayerIfsc())
				{ // 16-11-17 reqResp.getPayerAccNum()
					reqResp.setRespCode(ConstantNew.CONST_12);
					reqResp.setRespMsg(ConstantNew.INVALID_PAYER_ACC_NUM_IFSC);
					return true;
				}
			}

			// NOT TRANSACTION IS ALLOWED ON MMID
			if (ConstantNew.MOBILE.equalsIgnoreCase(reqResp.getPayerAddrType()))
			{
				if (null == reqResp.getPayerMobileNo() || null == reqResp.getPayerMmid())
				{
					reqResp.setRespCode(ConstantNew.CONST_12);
					reqResp.setRespMsg(ConstantNew.INVALID_PAYER_MOBILE_NO_MMID);
					return true;
				}
			}

		}
		else
		{
			reqResp.setRespCode(ConstantNew.CONST_12);
			reqResp.setRespMsg(ConstantNew.INVALID_PAYER_ADDR_TYPE);
			return true;

		}
		if (null == reqResp.getRrn() || "".equals(reqResp.getRrn()))
		{
			reqResp.setRespCode(ConstantNew.CONST_12);
			reqResp.setRespMsg(ConstantNew.INVALID_CUST_REF_RRN);
			return true;
		}
		return false;
	}
}
