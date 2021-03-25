package com.npst.upiserver.service.impl;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.TransNoDao;
import com.npst.upiserver.service.IdGeneratorService;

@Component
public class IdGeneratorServiceImpl implements IdGeneratorService {
	private static final Logger log = LoggerFactory.getLogger(IdGeneratorServiceImpl.class);
	private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyHHmmss");
	private static final String BANK_PREFIX = Constant.bKPrf.toLowerCase();
	private static final String BANK_HANDAL = Constant.BANK_HANDAL.toLowerCase();

	
	
	
	
	
	@Autowired
	private TransNoDao transNoDao;

	@Override
	public String getStan() {
		return String.format("%06d", transNoDao.getTransNo(Integer.parseInt(Constant.orgId)));
	}

	@Override
	public String getRrn() {
		String transNo = String.format("%06d", transNoDao.getTransNo(Integer.parseInt(Constant.orgId)));
		SimpleDateFormat f = new SimpleDateFormat("HH");
		f.setTimeZone(TimeZone.getTimeZone("IST"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		String yy = String.valueOf(c.get(Calendar.YEAR)).substring(3);
		String dd = String.valueOf(c.get(Calendar.DAY_OF_YEAR));
		dd = String.format("%03d", Integer.parseInt(dd));
		String hh = f.format(new Date());
		hh = String.format("%02d", Integer.parseInt(hh));
		String J1 = yy + dd + hh;
		return J1 + transNo;
	}

	@Override
	public String getUmn() {
		StringBuilder sb = new StringBuilder();
		sb.append(BANK_PREFIX);
		sb.append(UUID.randomUUID().toString()
						.replaceAll(ConstantI.CONST_SPCL_HIFN, ConstantI.CONST_BLANK).toUpperCase().substring(0, 29));
		sb.append(BANK_HANDAL);
		return sb.toString();
	}

}
