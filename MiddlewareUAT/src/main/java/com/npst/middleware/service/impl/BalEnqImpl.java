package com.npst.middleware.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.cache.CBSErrorCache;
import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.dao.ErrorCodeRepository;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.BalEnq;
import com.npst.middleware.upipin.service.UpiPinProcess;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.PreCheckBalEnq;

@Service
public class BalEnqImpl implements BalEnq
{
	private static final Logger LOG = LoggerFactory.getLogger(BalEnqImpl.class);

	@Autowired
	public CBSProcess cbsProcess;
	@Autowired
	public ErrorCodeRepository errorCodeRepository;
	@Autowired
	public UpiPinProcess upiPinProcess;

	@Override
	public ReqResp fetch(ReqResp reqResp)
	{
		LOG.trace(" ");
		try
		{
			if (PreCheckBalEnq.check(reqResp))
			{
				LOG.info("Returning after Precheck with {} ",reqResp);
				return reqResp;
			}
			String upiErrorCode = upiPinProcess.upiPinValidate(reqResp.getCredMpin(), reqResp.getPayerDeviceMobile(), reqResp.getPayerAcNum());
			if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(upiErrorCode))
			{
				reqResp = cbsProcess.balEnq(reqResp);
				// when CBS is down then it will return XY
				if (reqResp.getRespCode().equalsIgnoreCase(ConstantNew.SUCCESS_CODE))
				{
					reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
				}
				else if (reqResp.getRespCode().equalsIgnoreCase("DWN"))
				{
					reqResp.setRespCode("XY");
				}
				else
				{
					String nPCICODE = CBSErrorCache.balEnqErrorCode.get(reqResp.getRespCode());
					if (nPCICODE != null)
					{
						reqResp.setRespCode(nPCICODE);
					}
					else
					{
						reqResp.setRespCode("XY");
					}
				}
				LOG.info("Returning with : {} " , reqResp);
				return reqResp;
			}
			else
			{
				reqResp.setRespCode(upiErrorCode);
				LOG.info("Returning with  : { } " , reqResp);
				return reqResp;
			}

		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
		LOG.debug("end the BalEnqImpl fetch Method {} " ,reqResp);
		return reqResp;
	}
}