/**
 * 
 */
package com.npst.mobileservice.util;

import java.util.Date;

import com.npst.mobileservice.object.CrashAnalyticsInfoRequest;
import com.npst.upi.hor.CrashAnalyticsInfo;

/**
 * @author npst
 *
 */
public class CrashAnalyticsInfoUtil {

	public static CrashAnalyticsInfo convertToEntity(final CrashAnalyticsInfoRequest request) {
		CrashAnalyticsInfo exceptionLog = new CrashAnalyticsInfo();
		exceptionLog.setCreated(new Date(System.currentTimeMillis()));
		exceptionLog.setAppPackage(request.getApp_package());
		exceptionLog.setAppVersion(request.getApp_version());
		exceptionLog.setException(request.getExcption());
		exceptionLog.setHandsetName(request.getHandset_name());
		exceptionLog.setOsName(request.getOs_name());
		exceptionLog.setOsVersion(request.getOs_version());
		exceptionLog.setDeviceId(request.getDevice_id());
		exceptionLog.setMobileNumber(request.getMobile_number());
		exceptionLog.setExceptionType(request.getException_type());
		exceptionLog.setStatus(1);// Open
		exceptionLog.setProblemCategory(0);// NA
		exceptionLog.setProblemSeverity(0);// NA
		return exceptionLog;
	}

}
