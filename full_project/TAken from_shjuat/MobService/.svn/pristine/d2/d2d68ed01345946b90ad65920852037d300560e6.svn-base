/**
 * 
 */
package com.npst.mobileservice.validator;

import com.npst.mobileservice.service.NegativeMobileNumberService;

/**
 * @author npst
 *
 */
public final class NegativeMobileNumberValidator {

	private static NegativeMobileNumberService negativeMobileNumberService = null;

	public final boolean validate(final String negativeMobileNumber) {
		if (null == negativeMobileNumber || "".equals(negativeMobileNumber))
			return false;
		if (null == negativeMobileNumberService)
			negativeMobileNumberService = new NegativeMobileNumberService();
		return negativeMobileNumberService.isExist(negativeMobileNumber);
	}

}
