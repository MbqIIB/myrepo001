package com.npst.middleware.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.dao.TransRepository;

@Service
public class LimitCheck
{
	private static final Logger LOG = LoggerFactory.getLogger(LimitCheck.class);
	@Autowired
	TransRepository transRepository;
	final static long DAILYLIMIT = Long.valueOf(Util.getProperty("DAILYLIMIT"));
	final static long DAILYLITXRCOUNT = Long.valueOf(Util.getProperty("DAILYLITXRCOUNT"));
	
	final static long DAILYLIMITOC82 = Long.valueOf(Util.getProperty("DAILY_LIMIT200K"));

	public boolean[] isTxnAllowedOnDailyLimit(final String accountNumber, final long transactionAmount)
	{
		LOG.info("Inside with params account number as {} and  trans amount {}", accountNumber,transactionAmount);
		Date dayOfTxn = new Date();
		List<Object[]> transactedAmountList = new ArrayList<>();
		boolean isAllowed[] = new boolean[]
		{ false, false };
		try
		{
			transactedAmountList = transRepository.getLimitBasedonAccNum(dayOfTxn, accountNumber);
			Long amount = 0l;
			Long totalCount = 0l;
			for (Object[] b : transactedAmountList)
			{
				amount = Long.valueOf(b[0].toString());
				totalCount = Long.valueOf(b[1].toString());
			}
			if (transactionAmount + amount <= DAILYLIMIT)
			{
				isAllowed[0] = true;
			}
			if (totalCount + 1 <= DAILYLITXRCOUNT)
			{
				isAllowed[1] = true;
			}

		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		return isAllowed;
	}

	
	
	
	public boolean[] isTxnAllowedOnDailyLimitOC82(final String accountNumber, final long transactionAmount)
	{
		LOG.info("Inside with params account number as {} and  trans amount {}", accountNumber,transactionAmount);
		Date dayOfTxn = new Date();
		List<Object[]> transactedAmountList = new ArrayList<>();
		boolean isAllowed[] = new boolean[]
		{ false, false };
		try
		{
			transactedAmountList = transRepository.getLimitBasedonAccNum(dayOfTxn, accountNumber);
			Long amount = 0l;
			Long totalCount = 0l;
			for (Object[] b : transactedAmountList)
			{
				amount = Long.valueOf(b[0].toString());
				totalCount = Long.valueOf(b[1].toString());
			}
			if (transactionAmount + amount <= DAILYLIMITOC82)
			{
				isAllowed[0] = true;
			}
			if (totalCount + 1 <= DAILYLITXRCOUNT)
			{
				isAllowed[1] = true;
			}

		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		return isAllowed;
	}
}
