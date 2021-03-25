package com.npst.middleware.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.npst.middleware.cache.CBSErrorCache;
import com.npst.middleware.dao.TransRepository;
import com.npst.middleware.entity.Trans;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.ChkTxn;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.ISOMsgConstant;

@Service
public class ChkTxnImpl implements ChkTxn
{
	private static final String DEBITPREFIX = "DR";
	private static final String CREDITPREFIX = "CR";
	private static final Logger LOG = LoggerFactory.getLogger(ChkTxnImpl.class);
	private static int count = 0;
	@Autowired
	TransRepository transRepository;

	@Override
	public ReqResp fetch(final ReqResp reqResp)
	{
		LOG.trace(" ");
		try
		{
			List<Trans> transList = transRepository.findByTxnIdAndRrnAndOpration(reqResp.getTxnId(), reqResp.getRrn(), reqResp.getOrgTxnType());
			if (null !=transList && transList.size() > 0)
			{
				LOG.debug("ReqChkTxn sending resp middleware to upiserver");
				if ("DEBIT".equals(reqResp.getOrgTxnType())) {
					LOG.debug("ReqChkTxn for Debit IFSC {} , AcType {}:",transList.get(0).getIfsc(),transList.get(0).getPayerAcType());
					reqResp.setPayerIfsc(transList.get(0).getIfsc());
					reqResp.setPayerAcType(transList.get(0).getPayerAcType());
				}else {
					LOG.debug("ReqChkTxn for Credit IFSC {} , AcType {}:",transList.get(0).getIfsc(),transList.get(0).getPayeeAcType());
					reqResp.setPayeeIfsc(transList.get(0).getIfsc());
					reqResp.setPayeeAcType(transList.get(0).getPayeeAcType());
				}
				Integer transStatus = transList.get(0).getStatus();
				LOG.info("TXN Status for txn id {} is as {} :" ,transStatus , reqResp.getTxnId());
				if (ConstantNew.TXN_STATUS_1 == transStatus)
				{
					reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
				}
				else if (ConstantNew.TXN_STATUS_2 == transStatus)
				{
					reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
					
				}
				else if (ConstantNew.TXN_STATUS_3 == transStatus)
				{
					if ("DEBIT".equals(reqResp.getOrgTxnType()))
					{
						String nPCICODE = CBSErrorCache.debitErrorCode.get(DEBITPREFIX + transList.get(0).getCbsResponseCode());
						if (nPCICODE != null)
						{
							reqResp.setRespCode(nPCICODE);
						}
						else
						{
							reqResp.setRespCode(ConstantNew.INVALID_TXN_XB);
						}
					}
					else
					{
						String nPCICODE = CBSErrorCache.creditErrorCode.get(CREDITPREFIX + transList.get(0).getCbsResponseCode());
						if (nPCICODE != null)
						{
							reqResp.setRespCode(nPCICODE);
						}
						else
						{
							//reqResp.setRespCode(ConstantNew.INVALID_TXN_XC);
							reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);//05122018
						}
					}
				}
				else
				{
					reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
				}
			}
			else
			{
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
				// Abhishek and Barun to clear doubt on XY case for RE38 or reqcheckTxn
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
			//String responseCode="UKN";
			reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
		}
		LOG.debug("ending the fetch method  {} ", reqResp);
		return reqResp;
	}
}
