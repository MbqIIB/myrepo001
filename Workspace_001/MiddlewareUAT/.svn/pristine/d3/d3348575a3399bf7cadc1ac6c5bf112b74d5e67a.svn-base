package com.npst.middleware.iso;

import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

import com.npst.middleware.util.ISOMsgConstant;
import com.npst.middleware.util.Util;

public class ISOMessageReqResp {

	private static final DateFormat df4 = new SimpleDateFormat("yyyyMMddhhmmss");
	
	
	public static void main(String[] args) throws Exception {
	int port = 5001;
	String IPStr = "10.10.25.30";
	ISOMsg message = null;
//	message = balanceEnquireRequest();
		ISO93APackager packager = new ISO93APackager();
		Logger logger = new Logger();
		logger.addListener(new SimpleLogListener(System.out));
		ISOChannel channel = new ASCIIChannel(IPStr, port, packager);
		((LogSource) channel).setLogger(logger, "test-channel");
		byte[] data = message.pack();
		System.out.println("Request String :\n\n" + new String(data));
		System.out.println("\n\nRequest HEX String :\n\n" + ISOUtil.hexdump(data));
		channel.connect();
		channel.send(message);
		System.out.println("main() receiving.......: " + channel.isConnected());
		ISOMsg rg = channel.receive();
		System.out.println("main() received......." + new String(rg.pack()));
		if (rg != null) {
			rg.setPackager(packager);
			byte[] image_out = rg.pack();
			System.out.println("main() Out Byte Hex :::: " + ISOUtil.hexdump(image_out));
			System.out.println("main() Field 39 (Response code) :: " + rg.getString(39));
		}
		channel.disconnect();
	}
		
//**********************Start IOS Messaging of CBS for KGB and PKGB*********************************
	static ISOMsg balanceEnquireRequest(String accountNumber) throws ISOException {
		Date date = new Date();
		ISOMsg m = new ISOMsg();
		m.setPackager(new ISO93APackager());
		m.setMTI("0200");
		m.set(2, accountNumber); 
		m.set(3, ISOMsgConstant.BAL_ENG_PROCESSING_CODE);
		m.set(7, ISOUtility.getTransmissionDateTime(date)); 
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		m.set(11, ISOUtility.padZeros(n+"", 12));
		m.set(12, ISOUtility.getTransactionTime(date)); 
		m.set(13, ISOUtility.getTransactionDate(date));
		m.set(32,ISOMsgConstant.DE_32);  
		m.set(33, ISOMsgConstant.DE_33); 
		m.set(49,ISOMsgConstant.DE_49);  
		return m;
	}
	
	
	
	static ISOMsg getAccountListRequest(String mobileNumber) throws ISOException {
		ISOMsg m = new ISOMsg();
		m.setPackager(new ISO93APackager());
		Date date = new Date();
		m.setMTI("0220");
		m.set(3, ISOMsgConstant.GET_ACC_LIST_PROCESSING_CODE);
		m.set(7, ISOUtility.getTransmissionDateTime(date)); 
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		m.set(11, ISOUtility.padZeros(n+"", 12));
		m.set(12, ISOUtility.getTransactionTime(date)); 
		m.set(13, ISOUtility.getTransactionDate(date));
		m.set(32,ISOMsgConstant.DE_32);  
		m.set(33, ISOMsgConstant.DE_33); 
		m.set(49,ISOMsgConstant.DE_49);
		m.set(123,ISOUtility.isoAccListFormatMobileNo(mobileNumber));   
		return m;
	}
	
	static ISOMsg debitFundTxnRequest(String amount,String customerAccountNumber) throws ISOException {
		Date date=new Date();
		ISOMsg m = new ISOMsg();
		m.setPackager(new ISO93APackager());
		m.setMTI("0200");
		m.set(3,ISOMsgConstant.FUND_PROCESSING_CODE);
		m.set(4, ISOUtility.padZeros(amount,12)); 
		m.set(7, ISOUtility.getTransmissionDateTime(date)); 
		Random rnd = new Random();
		int n =  100000 + rnd.nextInt(900000);
		m.set(11,  ISOUtility.padZeros(n+"", 12));
		m.set(12,  ISOUtility.getTransactionTime(date)); 
		m.set(13,  ISOUtility.getTransactionDate(date));  
		m.set(32,  ISOMsgConstant.DE_32);  
		m.set(33,  ISOMsgConstant.DE_33); 
		m.set(37,  ISOUtility.padZeros(n+"", 12));   
		m.set(49,  ISOMsgConstant.DE_49);
		m.set(54,  "000000000000"); 
		m.set(102, customerAccountNumber);
		m.set(103, Util.getProperty("POOL_ACCOUNT"));
		return m;
	}
	
	static ISOMsg creditFundTxnRequest(String amount,String customerAccountNumber) throws ISOException {
		Date date=new Date();
		ISOMsg m = new ISOMsg();
		m.setPackager(new ISO93APackager());
		m.setMTI("0200");
		m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
		m.set(4, ISOUtility.padZeros(amount,12)); 
		m.set(7, ISOUtility.getTransmissionDateTime(date)); 
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		m.set(11, ISOUtility.padZeros(n+"", 12));
		m.set(12, ISOUtility.getTransactionTime(date)); 
		m.set(13, ISOUtility.getTransactionDate(date));  
		m.set(32, ISOMsgConstant.DE_32);  
		m.set(33, ISOMsgConstant.DE_33); 
		m.set(37, ISOUtility.padZeros(n+"", 12));   
		m.set(49, ISOMsgConstant.DE_49);
		m.set(54,"000000000000");
		m.set(102,Util.getProperty("POOL_ACCOUNT"));
		m.set(103, customerAccountNumber);
		return m;
	}
	
	static ISOMsg creditReversalFundTxnRequest(String amount,String customerAccountNumber) throws ISOException {
		Date date=new Date();
		ISOMsg m = new ISOMsg();
		m.setPackager(new ISO93APackager());
		m.setMTI("0400");
		m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
		m.set(4, ISOUtility.padZeros(amount,12)); 
		m.set(7, ISOUtility.getTransmissionDateTime(date)); 
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		m.set(11, ISOUtility.padZeros(n+"", 12));
		m.set(12, ISOUtility.getTransactionTime(date)); 
		m.set(13, ISOUtility.getTransactionDate(date)); 
		m.set(32, ISOMsgConstant.DE_32);
		m.set(33, ISOMsgConstant.DE_33);
		m.set(37, ISOUtility.padZeros(n+"", 12));   
		m.set(49, ISOMsgConstant.DE_49);
		m.set(54,"000000000000");
		m.set(102, customerAccountNumber); 
		m.set(103,Util.getProperty("POOL_ACCOUNT"));
		return m;
	}
	
	static ISOMsg debitReversalFundTxnRequest(String amount,String customerAccountNumber) throws ISOException {
		Date date=new Date();
		ISOMsg m = new ISOMsg();
		m.setPackager(new ISO93APackager());
		m.setMTI("0400");
		m.set(3, ISOMsgConstant.FUND_PROCESSING_CODE);
		m.set(4, ISOUtility.padZeros(amount,12)); 
		m.set(7, ISOUtility.getTransmissionDateTime(date));
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		m.set(11, ISOUtility.padZeros(n+"", 12));
		m.set(12, ISOUtility.getTransactionTime(date)); 
		m.set(13, ISOUtility.getTransactionDate(date));  
		m.set(32, ISOMsgConstant.DE_32);
		m.set(33,  ISOMsgConstant.DE_33); 
		m.set(37,ISOUtility.padZeros(n+"", 12));   
		m.set(49, ISOMsgConstant.DE_49);
		m.set(54,"000000000000");
		m.set(102,Util.getProperty("POOL_ACCOUNT"));
		m.set(103,customerAccountNumber);
		return m;
	}

}
