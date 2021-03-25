package com.npst.middleware.cbs.service.impl;

import java.util.Date;

import org.jpos.iso.ISOMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.npst.middleware.cbs.service.ATMProcess;
import com.npst.middleware.cbs.service.CBSConSocket;
import com.npst.middleware.dao.TransRepository;
import com.npst.middleware.entity.Trans;
import com.npst.middleware.iso.ATMPackager;
import com.npst.middleware.iso.ISOUtility;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.util.ISOMsgConstant;

@Service
public class ATMProcessImpl implements ATMProcess
{
	private static final Logger LOG = LoggerFactory.getLogger(ATMProcessImpl.class);
	@Autowired
	CBSConSocket cbsConSocket;
	@Autowired
	TransRepository transRepository;

	@Override
	public ReqResp getFullCardNumber(final ReqResp reqResp)
	{
		try
		{
			String f11 = reqResp.getAtmCardFiled11().substring(7);
			Date date = new Date();
			ISOMsg m = new ISOMsg();
			m.setPackager(new ATMPackager());
			m.setMTI("0200");
			m.set(2, reqResp.getRegCardDigits());
			m.set(3, "VC0000");//  //001010
			m.set(7, ISOUtility.getTransmissionTimeDate10(new Date()));//DDMMHHMMSS  11 //getTransmissionTimeDate10
			m.set(11, ISOUtility.padZeros(f11, 6));
			m.set(12, ISOUtility.getTransactionTime(date));
			m.set(13, ISOUtility.getTransactionDate(date));
			m.set(14, reqResp.getRegExpDate());
			m.set(37, ISOUtility.padZeros(reqResp.getAtmCardFiled11(), 12));
			m.set(102, reqResp.getPayerAcNum()); // 16-11-17
													// reqResp.getPayerAccNum()
			Trans trans = new Trans();
			try
			{
				trans.setAccNo(reqResp.getPayeeAcNum()); // 16-11-17
															// reqResp.getPayeeAccNum()
				trans.setOpration("ATMREQ1");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setRrn(reqResp.getRrn());
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				transRepository.save(trans);
			}
			catch (Exception e)
			{
				LOG.error(e.getMessage(),e);
			}
			LOG.info("Initiating ATM req with {} at {}",m,new Date());
			ISOMsg isoMsgRes = cbsConSocket.sendATMChannel(m);
			LOG.info("Got response from ATM with response {} at {}",isoMsgRes,new Date());
			if (isoMsgRes != null)
			{
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.ATM_SUCCESS_RESPONSE.equals(cbsResCode))
				{
					reqResp.setRegCardDigits(isoMsgRes.getString(103));
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				}
				else
				{
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			}
			else
			{
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try
			{
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			}
			catch (Exception e)
			{
				LOG.error(e.getMessage(),e);
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		LOG.info("Ending getFullCardNumber method with response as {} ",  reqResp );
		return reqResp;
	}

	@Override
	public ReqResp verifyATMPIN(final ReqResp reqResp)
	{
		try
		{
			LOG.debug("Going to Start ATM Pin validation ISO Request");
			String f11 = reqResp.getRrn().substring(7);
			ISOMsg m = new ISOMsg();
			m.setPackager(new ATMPackager());
			Date date = new Date();
			m.setMTI("0200");
			m.set(2, reqResp.getRegCardDigits());//added in new EFT
			//m.set(2, reqResp.getPayerAcNum());
			m.set(3, "PV0000");// //001020//identify for card or Pin PV//pin validation request
			m.set(7, ISOUtility.getTransmissionTimeDate10(date));//getTransmissionTimeDate10
			m.set(11, ISOUtility.padZeros(f11, 6));
			m.set(12, ISOUtility.getTransactionTime(date));
			m.set(13, ISOUtility.getTransactionDate(date));
			m.set(14, reqResp.getRegExpDate());
			m.set(37, ISOUtility.padZeros(reqResp.getRrn(), 12));
			m.set(52, reqResp.getCredAtmpin());
			m.set(102, reqResp.getPayerAcNum());
			//m.set(103, reqResp.getRegCardDigits()); //Not required as new EFT as per EURONET MAIL CONFORMATION
			Trans trans = new Trans();
			try
			{
				trans.setAccNo(reqResp.getPayerAcNum());
				trans.setOpration("ATMREQ2");
				trans.setReq(date);
				trans.setReqStr(new String(m.pack()));
				trans.setStatus(ISOMsgConstant.STATUS_PENDING);
				trans.setTxnId(reqResp.getTxnId());
				transRepository.save(trans);
			}
			catch (Exception e)
			{
				LOG.error(e.getMessage(),e);
			}
			LOG.info("Going for Connecting with the CBS Socket Connections at {} ",new Date());
			ISOMsg isoMsgRes = cbsConSocket.sendATMChannel(m);
			LOG.debug("Return success with isoMsgRes as {} at {} ",isoMsgRes,new Date());
			if (isoMsgRes != null)
			{
				String cbsResCode = isoMsgRes.getString(39);
				reqResp.setRespCode(cbsResCode);
				trans.setRespStr(new String(isoMsgRes.pack()));
				if (ISOMsgConstant.ATM_SUCCESS_RESPONSE.equals(cbsResCode))
				{
					trans.setStatus(ISOMsgConstant.STATUS_SUCCESS);
				}
				else
				{
					trans.setStatus(ISOMsgConstant.STATUS_FAIL);
				}
			}
			else
			{
				reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
			}
			try
			{
				trans.setCbsResponseCode(reqResp.getRespCode());
				trans.setResp(new Date());
				transRepository.save(trans);
			}
			catch (Exception e)
			{
				LOG.error(e.getMessage(),e);
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		return reqResp;
	}

	public String getPINBlock(String atmPIN)
	{
		return "ABCDEFGHIJK";
	}
}
