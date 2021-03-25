package com.npst.middleware.iso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpos.iso.ISOMsg;

public class ISOUtility {
	private boolean retStatusFlag = false;
	private static final String logModule = ISOUtility.class.getName();

	public boolean isBitMap(ISOMsg isoInput, int[] bitMap) {
		System.out.println("Enter isBitMap() ");
		for (int i = 0; i < bitMap.length; i++) {
			System.out.println("isBitMap() Message in Bit MAp  " + i + "---" + bitMap[i]);
		}
		this.retStatusFlag = isoInput.hasFields(bitMap);
		return this.retStatusFlag;
	}

	public static String getTransmissionDateTime(Date p_date) {
		String dateTimeStr = "";
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			sdf.setLenient(false);
			dateTimeStr = sdf.format(p_date);
		} catch (Exception e) {
			e.printStackTrace();
			dateTimeStr = sdf.format(new Date());
		}
		return dateTimeStr;
	}

	public static String getTransmissionTimeDate10(Date p_date) {
		String dateTimeStr = "";
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat("MMddHHmmss");
			sdf.setLenient(false);
			dateTimeStr = sdf.format(p_date);
		} catch (Exception e) {
			e.printStackTrace();
			dateTimeStr = sdf.format(new Date());
		}
		return dateTimeStr;
	}
	
	public static String getTransmissionTimeDate11(Date p_date) {
		String dateTimeStr = "";
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat("ddMMHHmmss");
			sdf.setLenient(false);
			dateTimeStr = sdf.format(p_date);
		} catch (Exception e) {
			e.printStackTrace();
			dateTimeStr = sdf.format(new Date());
		}
		return dateTimeStr;
	}
	public static String getTransactionDate(Date p_date) {
		String dateStr = "";
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat("MMdd");
			sdf.setLenient(false);
			dateStr = sdf.format(p_date);
		} catch (Exception e) {
			e.printStackTrace();
			dateStr = sdf.format(new Date());
		}
		return dateStr;
	}

	public static String getTransactionTime(Date p_date) {
		String timeStr = "";
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat("HHmmss");
			sdf.setLenient(false);
			timeStr = sdf.format(p_date);
		} catch (Exception e) {
			e.printStackTrace();
			timeStr = sdf.format(new Date());
		}
		return timeStr;
	}

	public static String getTransactionAmountStr(long p_amount) {
		return String.valueOf(p_amount);
	}

	public static String asciiChar2binary(String asciiString) {
		if (asciiString == null) {
			return null;
		}
		String binaryString = "";
		String temp = "";
		int intValue = 0;
		for (int i = 0; i < asciiString.length(); i++) {
			intValue = asciiString.charAt(i);
			temp = "00000000" + Integer.toBinaryString(intValue);
			binaryString = binaryString + temp.substring(temp.length() - 8);
		}
		return binaryString;
	}

	public static Date getDateFromDateString(String p_string) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		sdf.setLenient(false);
		return sdf.parse(p_string);
	}

	public static Date getDateTimeFromDateString(String p_string) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
		sdf.setLenient(false);
		return sdf.parse(p_string);
	}

	public static String padZeros(String string, int maxLength) {
		int dec = string.length();
		for (int i = 0; i < maxLength - dec; i++) {
			string = "0" + string;
		}
		return string;
	}
	public static String isoAccListFormatMobileNo(String mobileNumber) {
		if (mobileNumber.length() < 10) {
			return mobileNumber;
		} else if (mobileNumber.length() < 14) {
			if (mobileNumber.length() == 13 && mobileNumber.startsWith("+")) {
				return mobileNumber;
			}
			if (mobileNumber.length() == 12) {
				return "+" + mobileNumber;
			}
			if (mobileNumber.length() == 10) {
				return "+91" + mobileNumber;
			}
		}
        return mobileNumber;
	}
	
	public static String getTransactionDate8(Date p_date)
	  {
	    String dateStr = "";
	    SimpleDateFormat sdf = null;
	    try
	    {
	      sdf = new SimpleDateFormat("yyyyMMdd");
	      sdf.setLenient(false);
	      dateStr = sdf.format(p_date);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      dateStr = sdf.format(new Date());
	    }
	    return dateStr;
	  }
	  
	
	public static String getTransactionDateTime14(Date p_date)
	  {
	    String dateStr = "";
	    SimpleDateFormat sdf = null;
	    try
	    {
	      sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	      sdf.setLenient(false);
	      dateStr = sdf.format(p_date);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      dateStr = sdf.format(new Date());
	    }
	    return dateStr;
	  }
	
	
}
