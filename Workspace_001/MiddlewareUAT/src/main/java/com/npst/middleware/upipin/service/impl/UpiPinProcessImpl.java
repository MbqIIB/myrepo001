package com.npst.middleware.upipin.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.core.service.CryptoService;
import com.npst.middleware.dao.UpiPinRepository;
import com.npst.middleware.entity.UpiPin;
import com.npst.middleware.obj.CryptoResp;
import com.npst.middleware.upipin.service.UpiPinProcess;
import com.npst.middleware.util.Constant;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.HashUtil;
import com.npst.middleware.util.Util;

@Service
public class UpiPinProcessImpl implements UpiPinProcess
{
	private static final Logger LOG = LoggerFactory.getLogger(UpiPinProcessImpl.class);
	
	@Autowired
	UpiPinRepository upiPinRepository;
	final static String HSM_PRODUCTION = Util.getProperty("HSM_PRODUCTION");
	
	@Autowired
	CryptoService decryptService;

	@Override
	public String changeUpiPin(final String upiPin, final String newUpiPin, final String mobileNo, final String accNo)
	{
		LOG.info("Inside changeUpiPin with upipin {}, newupipin {} , mobileno {} , accno {} ",upiPin,newUpiPin,mobileNo,accNo);
		try
		{
			List<UpiPin> upiPinList = upiPinRepository.findByAccNoAndStatus(accNo); // barun
			if (null == upiPinList || ConstantNew.CHK_LIST_SIZE == upiPinList.size())
			{
				return ErrorCode.MB_AM.getUpiCode();
			}
			else
			{
				UpiPin upiPinObj = upiPinList.get(0); // BARUN HIDE 07-09-2017
				int totalFailAttempt = upiPinObj.getFailAttempt();
				int maxFailAttempt = upiPinObj.getMaxFailAttempt();
				if (0 == upiPinObj.getStatus())
				{
					if (totalFailAttempt >= maxFailAttempt)
					{
						return ErrorCode.MB_Z6.getUpiCode();
					}
					else
					{
						/*String UPIPIN = null;
						if ("YES".equalsIgnoreCase(HSM_PRODUCTION))
						{
							UPIPIN = HSMUtil.decryptData(Base64.getDecoder().decode(upiPin));
							if (null == UPIPIN)
							{
								return ErrorCode.PSP_ZD.getUpiCode();
							}
						}
						else
						{
							UPIPIN = Util.decrypt(Base64.getDecoder().decode(upiPin));
						}*/
						
						String UPIPIN = null;
						CryptoResp decryptResp = decryptService.decrypt(upiPin);
						if (Constant.SUCCESS_STATUS_ZERO.equals(decryptResp.getStatus())) {
							UPIPIN = decryptResp.getData();
							decryptResp = null;
						} else {
							return Constant.HS;
						}
						
						String salt = upiPinObj.getSalt();
						UPIPIN = HashUtil.crypt(UPIPIN, salt);
						if (upiPinObj.getUpiPin().equals(UPIPIN))
						{
							/*String newUPIPIN = null;
							if ("YES".equalsIgnoreCase(HSM_PRODUCTION))
							{
								newUPIPIN = HSMUtil.decryptData(Base64.getDecoder().decode(newUpiPin));
								if (null == newUPIPIN)
								{
									return ErrorCode.PSP_ZD.getUpiCode();
								}
							}
							else
							{
								newUPIPIN = Util.decrypt(Base64.getDecoder().decode(newUpiPin));
							}*/
							
							String newUPIPIN = null;
							CryptoResp decryptResp1 = decryptService.decrypt(newUpiPin);
							if (Constant.SUCCESS_STATUS_ZERO.equals(decryptResp1.getStatus())) {
								newUPIPIN = decryptResp1.getData();
								decryptResp = null;
							} else {
								return Constant.HS;
							}
							
							newUPIPIN = HashUtil.crypt(newUPIPIN, salt);
							if (UPIPIN.equals(newUPIPIN))
							{
								return ErrorCode.MB_RM.getUpiCode();
							}
							upiPinObj.setUpiPin(newUPIPIN);
							upiPinObj.setLastModTime(new Date());
							upiPinObj.setLastActionTime(new Date());
							upiPinObj.setFailAttempt(0);
							upiPinObj.setReason("PINRESET");
							upiPinRepository.save(upiPinObj);
							return ConstantNew.SUCCESS_CODE;
						}
						else
						{
							// optimization required
							upiPinObj.setFailAttempt(upiPinObj.getFailAttempt() + 1);
							upiPinObj.setLastModTime(new Date());
							upiPinObj.setLastActionTime(new Date());
							upiPinObj.setReason("Invalid PIN");
							upiPinRepository.save(upiPinObj);
							return ErrorCode.MB_ZM.getUpiCode();
						}
					}
				}
				else
				{
					return ErrorCode.MB_RM.getUpiCode();
				}

			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
		return "XY";
				
	}

	@Override
	public boolean isUpiPinCreated(final String mobileNo, final String accNo)
	{
		try
		{
			List<UpiPin> upiPinList = upiPinRepository.findByMobileNoAndAccNo(mobileNo, accNo);
			if (null == upiPinList || ConstantNew.CHK_LIST_SIZE == upiPinList.size())
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public String setUpiPin(final String upiPin, final String mobileNo, final String accNo, String channel, String who, String payerName)
	{
		LOG.info("starting the setUpiPin  with mobileNo:{} , accNo {} and PayerName: {}"  ,mobileNo,accNo,payerName);
		try
		{
			List<UpiPin> lstAccount = upiPinRepository.findByAccNoAndStatus(accNo);
			if (lstAccount != null && lstAccount.size() > 0)
			{
				for (UpiPin upiPinOld : lstAccount)
				{
					if (!upiPinOld.getMobileNo().equals(mobileNo))
					{
						upiPinOld.setStatus(ConstantNew.EXPIRED);
						upiPinOld.setLastModTime(new Date());
						upiPinOld.setReason("LinkToNewMobile");
						upiPinRepository.save(upiPinOld);
					}
				}
			}
			List<UpiPin> upiPinList = upiPinRepository.findByMobileNoAndAccNo(mobileNo, accNo);
			/*String UPIPIN = null;
			if ("YES".equalsIgnoreCase(HSM_PRODUCTION))
			{
				UPIPIN = HSMUtil.decryptData(Base64.getDecoder().decode(upiPin));
				if (null == UPIPIN)
				{
					return ErrorCode.PSP_ZD.getUpiCode();
				}
			}
			else
			{
				UPIPIN = Util.decrypt(Base64.getDecoder().decode(upiPin));
			}*/
			
			String UPIPIN = null;
			CryptoResp decryptResp = decryptService.decrypt(upiPin);
			if (Constant.SUCCESS_STATUS_ZERO.equals(decryptResp.getStatus())) {
				UPIPIN = decryptResp.getData();
				decryptResp = null;
			} else {
				return Constant.HS;
			}
			
			String salt = HashUtil.getSalt();
			UPIPIN = HashUtil.crypt(UPIPIN, salt);
			if (null == upiPinList || ConstantNew.CHK_LIST_SIZE == upiPinList.size())
			{
				UpiPin upiPIN = new UpiPin();
				upiPIN.setSalt(salt);
				upiPIN.setAccNo(accNo);
				upiPIN.setCreTime(new Date());
				upiPIN.setLastActionTime(new Date());
				upiPIN.setLastModTime(new Date());
				upiPIN.setLastUseTime(new Date());
				// TODO value of max pick from property
				upiPIN.setMaxFailAttempt(5);
				upiPIN.setFailAttempt(0);
				upiPIN.setWhoReq(who);
				upiPIN.setPayerName(payerName);
				upiPIN.setChannel(channel);
				upiPIN.setMobileNo(mobileNo);
				upiPIN.setStatus(0);
				upiPIN.setUpiPin(UPIPIN);
				upiPIN.setRiskStatus(ConstantNew.TXNRISK_STAT_0);
				upiPinRepository.save(upiPIN);
				return ConstantNew.SUCCESS_CODE;
			}
			else
			{
				UpiPin upiPIN = upiPinList.get(0);
				if (ConstantNew.ISSUER_BLOCK == upiPIN.getStatus())
				{
					return ErrorCode.CBS_R_YE.getUpiCode();
				}
				else
				{
					upiPIN.setLastModTime(new Date());
					upiPIN.setFailAttempt(0);
					upiPIN.setStatus(0);
					upiPIN.setSalt(salt);
					upiPIN.setUpiPin(UPIPIN);
					upiPinRepository.save(upiPIN);
					return ConstantNew.SUCCESS_CODE;
				}
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
		return "XY";
	}

	@Override
	public String upiPinValidate(final String upiPin, final String mobileNo, final String accNo)
	{
		try
		{
			List<UpiPin> upiPinList = upiPinRepository.findByMobileNoAndAccNo(mobileNo, accNo);
			if (null == upiPinList || ConstantNew.CHK_LIST_SIZE == upiPinList.size())
			{
				return ErrorCode.MB_AM.getUpiCode();
			}
			else
			{
				UpiPin upiPinObj = upiPinList.get(0);
				if (ConstantNew.EXPIRED == upiPinObj.getStatus())
				{
					return ErrorCode.MB_B1.getUpiCode();
				}
				if (ConstantNew.ISSUER_BLOCK == upiPinObj.getStatus())
				{
					return ErrorCode.CBS_R_YE.getUpiCode();
				}
				int totalFailAttempt = upiPinObj.getFailAttempt();
				int maxFailAttempt = upiPinObj.getMaxFailAttempt();
				if (0 == upiPinObj.getStatus())
				{
					if (totalFailAttempt >= maxFailAttempt)
					{
						return ErrorCode.MB_Z6.getUpiCode();
					}
					else
					{
						// Decrypt UPI PIN
						/*String UPIPIN = null;
						if ("YES".equalsIgnoreCase(HSM_PRODUCTION))
						{
							UPIPIN = HSMUtil.decryptData(Base64.getDecoder().decode(upiPin));
							if (null == UPIPIN)
							{
								return ErrorCode.PSP_ZD.getUpiCode();
							}
						}
						else
						{

							UPIPIN = Util.decrypt(Base64.getDecoder().decode(upiPin));
						}*/
						
						String UPIPIN = null;
						CryptoResp decryptResp = decryptService.decrypt(upiPin);
						if (Constant.SUCCESS_STATUS_ZERO.equals(decryptResp.getStatus())) {
							UPIPIN = decryptResp.getData();
							decryptResp = null;
						} else {
							return Constant.HS;
						}
						String salt = upiPinObj.getSalt();
						UPIPIN = HashUtil.crypt(UPIPIN, salt);
						if (upiPinObj.getUpiPin().equals(UPIPIN))
						{
							return ConstantNew.SUCCESS_CODE;
						}
						else
						{
							upiPinObj.setFailAttempt(upiPinObj.getFailAttempt() + 1);
							upiPinObj.setLastModTime(new Date());
							upiPinObj.setLastActionTime(new Date());
							upiPinObj.setReason("Invalid PIN");
							upiPinRepository.save(upiPinObj);
							return ErrorCode.MB_ZM.getUpiCode();
						}
					}
				}
				else
				{
					return ErrorCode.MB_RM.getUpiCode();
				}
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
		return "XY";
	}

	@Override
	public String upiPinValidate(final String upiPin, final String mobileNo, final String accNo,final String payerAmount)
	{
		try
		{
			List<UpiPin> upiPinList = upiPinRepository.findByMobileNoAndAccNo(mobileNo, accNo);
			if (null == upiPinList || ConstantNew.CHK_LIST_SIZE == upiPinList.size())
			{
				return ErrorCode.MB_AM.getUpiCode();
			}
			else
			{
				UpiPin upiPinObj = upiPinList.get(0);
				if (ConstantNew.EXPIRED == upiPinObj.getStatus())
				{
					return ErrorCode.MB_B1.getUpiCode();
				}
				if (ConstantNew.ISSUER_BLOCK == upiPinObj.getStatus())
				{
					return ErrorCode.CBS_R_YE.getUpiCode();
				}
				int totalFailAttempt = upiPinObj.getFailAttempt();
				int maxFailAttempt = upiPinObj.getMaxFailAttempt();
				if (0 == upiPinObj.getStatus())
				{
					if (totalFailAttempt >= maxFailAttempt)
					{
						return ErrorCode.MB_Z6.getUpiCode();
					}
					else
					{
						// Decrypt UPI PIN
						/*String UPIPIN = null;
						if ("YES".equalsIgnoreCase(HSM_PRODUCTION))
						{
							UPIPIN = HSMUtil.decryptData(Base64.getDecoder().decode(upiPin));
							if (null == UPIPIN)
							{
								return ErrorCode.PSP_ZD.getUpiCode();
							}
						}
						else
						{

							UPIPIN = Util.decrypt(Base64.getDecoder().decode(upiPin));
						}*/
						
						String UPIPIN = null;
						CryptoResp decryptResp = decryptService.decrypt(upiPin);
						if (Constant.SUCCESS_STATUS_ZERO.equals(decryptResp.getStatus())) {
							UPIPIN = decryptResp.getData();
							decryptResp = null;
						} else {
							return Constant.HS;
						}
						String salt = upiPinObj.getSalt();
						UPIPIN = HashUtil.crypt(UPIPIN, salt);
						if (upiPinObj.getUpiPin().equals(UPIPIN))
						{
							if (upiPinObj.getRiskStatus() == ConstantNew.TXNRISK_STAT_0) {
								String txnAmount = payerAmount;
								if(Util.isValidRiskTxnAmount(txnAmount)) {
									return ConstantNew.SUCCESS_CODE;
								}
								else {
									return ErrorCode.PSP_FL.getUpiCode();
								}
							} 
							else if(upiPinObj.getRiskStatus() == ConstantNew.TXNRISK_STAT_1 && upiPinObj.getFirstTransDate() != null) {
								if(Util.isValidRiskTxnDate(upiPinObj.getFirstTransDate())) {
									return ConstantNew.SUCCESS_CODE;
								}
								else {
									return ErrorCode.PSP_FP.getUpiCode();
								}
							}
							else {
								LOG.trace("Valid transaction as txn risk status is 2 ");
								return ConstantNew.SUCCESS_CODE;
							}
						}
						else
						{
							upiPinObj.setFailAttempt(upiPinObj.getFailAttempt() + 1);
							upiPinObj.setLastModTime(new Date());
							upiPinObj.setLastActionTime(new Date());
							upiPinObj.setReason("Invalid PIN");
							upiPinRepository.save(upiPinObj);
							return ErrorCode.MB_ZM.getUpiCode();
						}
					}
				}
				else
				{
					return ErrorCode.MB_RM.getUpiCode();
				}
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
		return "XY";
	}
	public boolean insertRegMOB(final String mobileNo, final String accNo, final String uPIN)
	{
		try
		{
			// UpiPin upiPIN = new UpiPin();
			// upiPIN.setAccNo(accNo);
			// upiPIN.setChannel("NPCI");
			// upiPIN.setMobileNo(mobileNo);
			// upiPIN.setStatus(0);
			// upiPIN.setUpiPin(uPIN);
			// upiPinRepository.save(upiPIN);
			// return true;
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
		return false;
	}
}
