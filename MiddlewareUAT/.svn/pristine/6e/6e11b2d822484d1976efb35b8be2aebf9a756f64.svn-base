package com.npst.middleware.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.cache.CBSErrorCache;
import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.dao.TransRepository;
import com.npst.middleware.entity.Trans;
import com.npst.middleware.obj.Message;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.Reversal;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.ISOMsgConstant;
import com.npst.middleware.util.Util;

@Service
public class ReversalImpl implements Reversal
{
	private static final Logger LOG = LoggerFactory.getLogger(ReversalImpl.class);
	private static final String CREDITPREFIX = "CR";
	private static final String DEBITPREFIX = "DR";

	@Autowired
	CBSProcess cbsProcess;

	@Autowired
	TransRepository transRepository;

	@Autowired
	GupShupSmsServive gupShupSmsServive;

	@Autowired
	SmsProcess smsProcess;

	@Override
	public ReqResp perform(final ReqResp reqResp)
	{
		LOG.trace(" ");
		String smsMessage = null;
		String mobileNo = null;
		try
		{
			List<Trans> transList = transRepository.findByTxnIdAndRrnAndOpration(reqResp.getOrgTxnId(), reqResp.getRrn(), reqResp.getOrgTxnType()); // 16-11-17
			if (transList == null || transList.size() == 0)
			{
				if (ConstantNew.CREDIT.equalsIgnoreCase(reqResp.getOrgTxnType())) {
					// if original credit transaction not exit "OC"  96
					//reqResp.setRespCode(ErrorCode.CBS_OC.getUpiCode());
					reqResp.setRespCode(ConstantNew.ERROR_CODE_NPCI_96);
				} else {
					// if original debit transaction not exit "OD"
					reqResp.setRespCode(ErrorCode.CBS_OD.getUpiCode());
				}
				LOG.info("Returning with response code as: {} ",reqResp.getRespCode());
				return reqResp;
			}
			for (Trans trans : transList)
			{
				if (1 == trans.getStatus())
				{
					reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
					return reqResp;
				}
				else if (2 == trans.getStatus())
				{
					reqResp.setReversalInfo("1200" + trans.getCrrn() + trans.getTxnTime());
					reqResp.setField11(trans.getCrrn());
					reqResp.setTxnRefId(trans.getRefId());// 16-11-17
					reqResp.setTxnRefUrl(trans.getRefURL());// 16-11-17
					reqResp.setTxnNote(trans.getTxnNote());
					if (ConstantNew.DEBIT.equalsIgnoreCase(reqResp.getOrgTxnType()))
					{
						if (trans.getAmount() == Long.parseLong(Util.convertAmountInPaisa(reqResp.getPayerAmount())))
						{
							cbsProcess.debitReversalAccount(reqResp);
							if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(reqResp.getRespCode()))
							{
								try
								{
									trans.setIsReversal(1);
									transRepository.save(trans);
									if ("ACCOUNT".equalsIgnoreCase(reqResp.getPayerAddrType()))
									{
										smsMessage = MessageFormat.format(Util.getSMSProperty("REM_REVERSAL"),  Util.maskNumber(reqResp.getPayerAcNum()), reqResp.getPayerAmount(), new Date(), // 16-11-17
																																												// reqResp.getPayerAccNum()
												reqResp.getRrn());
										mobileNo = trans.getMobileNumber();
									}
									if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION")))
									{
										Message message = new Message();
										message.setMobileNo(mobileNo);
										message.setType(ConstantNew.MESSAGE_TYPE_SMS);
										message.setMessage(smsMessage);
										this.gupShupSmsServive.sendMessage(message);
									}
									else
									{
										smsProcess.sendSms(mobileNo, smsMessage);
									}
								}
								catch (Exception e)
								{
									LOG.error(e.getMessage(),e);
								}
								reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
							}
							else
							{
								String nPCICODE = CBSErrorCache.debitErrorCode.get(DEBITPREFIX + reqResp.getRespCode());
								if (nPCICODE != null)
								{
									reqResp.setRespCode(nPCICODE);
								}
								else
								{
									reqResp.setRespCode(ConstantNew.INVALID_TXN_XB);
								}
							}
						}
						else
						{
							reqResp.setRespCode(ErrorCode.CBS_R_XD.getUpiCode());
						}
						LOG.info("Returning with resp {}  ",reqResp);
						return reqResp;
					}
					if (ConstantNew.CREDIT.equalsIgnoreCase(reqResp.getOrgTxnType()))
					{
						LOG.trace("called credit reversal");
																									// reqResp.getOrgTxnid()
						if (trans.getAmount() == Long.parseLong(Util.convertAmountInPaisa(reqResp.getPayeeAmount())))
						{
							if ("ACCOUNT".equalsIgnoreCase(reqResp.getPayeeAddrType()))
							{
								cbsProcess.creditReversalAccount(reqResp);
							}
							if ("AADHAAR".equalsIgnoreCase(reqResp.getPayeeAddrType()))
							{ 
								cbsProcess.creditReversalAadhar(reqResp);
							}
							if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(reqResp.getRespCode()))
							{
								try
								{
									trans.setIsReversal(1);
									transRepository.save(trans);
									if ("ACCOUNT".equalsIgnoreCase(reqResp.getPayeeAddr()))
									{
										smsMessage = MessageFormat.format(Util.getSMSProperty("BEN_REVERSAL"),  Util.maskNumber(reqResp.getPayeeAcNum()), reqResp.getPayeeAmount(), new Date(), // 16-11-17
																																												// reqResp.getPayeeAccNum()
												reqResp.getRrn());
										mobileNo = trans.getMobileNumber();
									}
									if ("AADHAAR".equalsIgnoreCase(reqResp.getPayeeAddr()))
									{
										smsMessage = MessageFormat.format(Util.getSMSProperty("BEN_REVERSAL"), Util.maskNumber(reqResp.getPayeeAcNum()), reqResp.getPayeeAmount(), new Date(), // 16-11-17
																																												// reqResp.getPayeeAccNum()
												reqResp.getRrn());
										mobileNo = trans.getMobileNumber();
									}
									LOG.info("Before going Gupshup API with mobileNo {} and message {} ", mobileNo ,smsMessage);
									if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION")))
									{
										Message message = new Message();
										message.setMobileNo(mobileNo);
										message.setType(ConstantNew.MESSAGE_TYPE_SMS);
										message.setMessage(smsMessage);
										this.gupShupSmsServive.sendMessage(message);
									}
									else
									{
										smsProcess.sendSms(mobileNo, smsMessage);
									}
								}
								catch (Exception e)
								{
									LOG.error(e.getMessage(),e);
								}
								reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
							}
							else
							{
								String nPCICODE = CBSErrorCache.creditErrorCode.get(CREDITPREFIX + reqResp.getRespCode());
								if (nPCICODE != null)
								{
									reqResp.setRespCode(nPCICODE);
								}
								else
								{
									reqResp.setRespCode(ConstantNew.INVALID_TXN_XC);
								}
							}
						}
						else
						{
							reqResp.setRespCode(ErrorCode.CBS_B_XE.getUpiCode());
						}
						LOG.debug("Return success with reqResp {} " , reqResp);
						return reqResp;
					}
				}
				else
				{ // if original transaction failed
					if (ConstantNew.DEBIT.equalsIgnoreCase(reqResp.getOrgTxnType()))
					{
						LOG.info(" DEBIT original txn of failed Txn {} " ,reqResp.getOrgTxnId()); 
						reqResp.setRespCode(ErrorCode.CBS_ND.getUpiCode());
						return reqResp;
					}
					if (ConstantNew.CREDIT.equalsIgnoreCase(reqResp.getOrgTxnType()))
					{
						LOG.info("credit original txn id is {} " , reqResp.getOrgTxnId()); 
						reqResp.setRespCode(ErrorCode.CBS_NC.getUpiCode());
						return reqResp;
					}
					LOG.debug("Returning with {} ", reqResp);
					return reqResp;
				}
			}

		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		LOG.debug("ending the perfom method with response as {} ",reqResp);
		return reqResp;
	}
}