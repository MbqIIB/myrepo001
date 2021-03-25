/**
 * 
 */
package com.npst.mobileservice.service;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.npst.mobileservice.util.Util;

/**
 * @author npst
 *
 */
public final class VersionManagementService {
	private static final String ANDROID_PROP_KEY = "app.android.versions";
	private static final String IOS_PROP_KEY = "app.ios.versions";
	private static final String ANDROID = "ANDROID";
	private static final String android = "android";
	private static final String IOS = "IOS";
	private static final String ios = "ios";

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(VersionManagementService.class);

	public final List<String> getAllVersions(final String apptype) {
		String versions = null;
		if (apptype != null && (apptype.contains(ANDROID) || apptype.toLowerCase().contains(android)
				|| apptype.contains(android))) {
			versions = Util.getProperty(ANDROID_PROP_KEY);
		}
		if (apptype != null
				&& (apptype.contains(IOS) || apptype.toLowerCase().contains(ios) || apptype.contains(ios))) {
			versions = Util.getProperty(IOS_PROP_KEY);
		}

		if (null != versions) {
			return Arrays.asList(versions.split("\\s*,\\s*"));
		}
		return null;
	}

	public final boolean isExist(final String appversion, final String apptype) {
		final List<String> versions = getAllVersions(apptype);
		if (null != versions)
			return versions.contains(appversion);
		return false;
	}

}
