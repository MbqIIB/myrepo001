package com.npst.middleware.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CBSErrorCache {
	public static final Map<String, String> debitErrorCode = new ConcurrentHashMap<String, String>();
	public static final Map<String, String> listAccoutErrorCode = new ConcurrentHashMap<String, String>();
	public static final Map<String, String> creditErrorCode = new ConcurrentHashMap<String, String>();
	public static final Map<String, String> errorCode = new ConcurrentHashMap<String, String>();
	public static final Map<String, String> atmErrorCode = new ConcurrentHashMap<String, String>();
	public static final Map<String, String> balEnqErrorCode = new ConcurrentHashMap<String, String>();
	 static {
	     //CBS SUCCESS
	     errorCode.put("000", "0");
	     
	     // list ACCOUNT Error Code
	     // MobileNumber not registered
	     listAccoutErrorCode.put("003", "XH");
	     // Account inactive
	     listAccoutErrorCode.put("002", "B3");
	     // multiple cust id
	     listAccoutErrorCode.put("001", "BR");
	     // not having code yet
//	     listAccoutErrorCode.put("", "B2");
	     //Account inactive 
	     listAccoutErrorCode.put("114", "B3");
	    
	     listAccoutErrorCode.put("909", "");
	     
	     // Invalid beneficiary credential
	     
	     //CBS offline
	     debitErrorCode.put("DRDWN", "XY");
	     creditErrorCode.put("CRDWN", "Y1");
	     
	     //CBS offline
	     debitErrorCode.put("DR909", "XY");
	     creditErrorCode.put("CR909", "Y1");
	          
	     //UNKNOWN CBS ERROR
	     debitErrorCode.put("DRUKN", "UKN");
	     creditErrorCode.put("CRUKN", "UKN");
	     
	     //Dormant or Inactive account
	     debitErrorCode.put("DR115", "ZX");  
//	     debitErrorCode.put("DR119", "ZX");
	     creditErrorCode.put("CR115", "ZY");
	     
	  // inactive account or freezed
	     debitErrorCode.put("DRD119", "ZX");
	     debitErrorCode.put("DRF119", "YE");
	     
	     // inactive account or freezed
	     creditErrorCode.put("CRF119", "YF");
	     creditErrorCode.put("CRD119", "ZY");
	     
	     //Insufficient Fund
	     debitErrorCode.put("DR116", "Z9");
	     creditErrorCode.put("CR116", "Z9");  //not applicable
	     
	   // Transaction limit exceeds
	     debitErrorCode.put("DR180", "Z8");
//	     creditErrorCode.put("CR180", ""); // not applicable
	  
	   // Transaction frequency limit exceeds
	     debitErrorCode.put("DR913", "Z7");
	   
	     
	  // account does not exits
	     debitErrorCode.put("DR114", "XH");
	     creditErrorCode.put("CR114", "XI"); 
	     
	  // suspected fraud transaction
//	     debitErrorCode.put("", "K1");
//	     creditErrorCode.put("", "ZI");
	     
	  // invalid credential
	     creditErrorCode.put("CR902", "Z5");
	     
	
	     
	  // requested function not supported
//	     debitErrorCode.put("DR115", "XJ");
//	     creditErrorCode.put("CR115", "XK");
	     
	  // Account Blocked/Frozen
	     debitErrorCode.put("DR184", "YE");
	     creditErrorCode.put("CR184", "YF");
	     
//	     account closed
	     debitErrorCode.put("DR163", "ZX");
	     creditErrorCode.put("CR163", "ZY");
	  
	     
	     // ATM Error code
	   //atmErrorCode.put("00","");  //Successful approval
	     
	     atmErrorCode.put("52","XN");  //Invalid transaction
	    // atmErrorCode.put("14","");  //Invalid account number
	     atmErrorCode.put("14", "XN");
	     atmErrorCode.put("67","XR"); //Invalid card number (no such card number)
	     //atmErrorCode.put("67", "AJ");
	     //atmErrorCode.put("30","");  //Format Error
	     atmErrorCode.put("54","XL");  //Expired card  //ok
	     atmErrorCode.put("55","SP");  //Incorrect PIN
	     atmErrorCode.put("83","SP");  //Unable to verify PIN (HSM timeout)
	     
	   //Dated 20-03-2018 after discussing with Azad
	      balEnqErrorCode.put("184", "YE"); //due to account freeze (online mode)
	      balEnqErrorCode.put("906", "XY"); //due to CBS EOD Activity, since its is offline mode they unable to validate it hence got 906 from cbs (offline mode having minimum functionality)
	      balEnqErrorCode.put("119", "YE"); //Account freeze (offline mode)
	      balEnqErrorCode.put("114", "B3");
	      balEnqErrorCode.put("115", "ZX");
	      balEnqErrorCode.put("902", "Z5");
	 }

	 public static String getATMErrorCode(String atmCode){
		 String npciCode=null;
		 npciCode = atmErrorCode.get(atmCode); 
		 if(npciCode==null) {
			 npciCode="AJ";
		 }	 
		 return npciCode;
	 }
	}


