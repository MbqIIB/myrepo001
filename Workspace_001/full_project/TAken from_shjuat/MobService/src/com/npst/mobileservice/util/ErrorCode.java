package com.npst.mobileservice.util;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
	public static enum AcqErrorCode {
		
		UNKNOWN_ERROR_CODE("---"), MSGID_NO_REC_FOUND("NRECF"), MSGID_EXCEPTIONMSG("EXP1"), MSGID_BLANKVIRTUALID(
				"MBV"), MSGID_BLANKMOBNO("MBM"), MSGID_PRESENT_VIRTUALID("APVPA"), MSGID_BLANK_RGISTRATIONID(
						"BREGID"), MSGID_REGSUCCESS("REG1"), MSGID_ALL_READY_ACTIVE_MSG(
								"REG2"), MSGID_LOGIN_FAIL_BLOCK_MSG("REG3"), MSGID_PSP_BLOCK_MSG(
										"REG4"), MSGID_FAIL_REGISTRATION_PROCESS("REG5"), MSGID_BLANK_LOGIN_PIN(
												"REG6"), MSGID_INVALID_LOGIN_DETAIL("REG7"), MSGID_WRONG_VPAID_OR_MOBNO(
														"REG8"), MSGID_INVALID_ATTEMPT_MSG(
																"REG9"), MSGID_INVALID_ATTEMPT_N_MSG(
																		"REG10"), MSGID_ACCOUNT_BLOCKED_MSG(
																				"REG11"), MSGID_SUCCESS_LOGIN(
																						"REG12"), MSGID_NOT_ACTIVE_MSG(
																								"REG13"), MSGID_FAIL_PASSWORD_EXPIRED(
																										"REG14"), MSGID_PROFILE_UPDATE_SUCCESS(
																												"REG15"), MSGID_PROFILE_UPDATE_FAIL(
																														"REG16"), MSGID_PASSWORD_NOT__SAME_AS_LAST_THREE_PASSWORD(
																																"REG17"), MSGID_PASS_CHANGED_SUCCESS(
																																		"REG18"), MSGID_DEREGISTARTION_SUCCESS(
																																				"REG19"), MSGID_DEREGISTARTION_FAIL(
																																						"REG20"), MSGID_LOGOUT_SUCCESS(
																																								"REG21"), MSGID_REG_NEGATIVE_MOBILE_NO(
																																										"REG22"), MSGID_VIRTUALID_EXIST_SAME_ACCOUNT(
																																												"REG23"), MSGID_VIRTUALID_EXIST_IN_RESERVEDVPA(
																																														"REG24"), MSGID_TEMP_BLOCK_MSG(
																																																"REG25"), MSGID_EXPIRE_SMS(
																																																		"SIL2"), MSGID_NOT_RECMOBNO_STRING(
																																																		"SIL1"), MSGID_FAIL_ACCOUNT_REG(
																																																				"CAC1"), MSGID_ACC_ADDED_SUCCESS(
																																																						"CAC2"), MSGID_ACTIVE_ACC_SUCCESS(
																																																								"CAC3"), MSGID_ACTIVE_ACC_FAIL(
																																																										"CAC4"), MSGID_DEF_ACC_SET_SUCCESS(
																																																												"CAC5"), MSGID_DEF_ACC_SET_FAIL(
																																																														"CAC6"), MSGID_FAIL_ACCOUNT_MBEBA(
																																																																"CAC7"), MSGID_NO_ACC_SET_FOR_REGVPA(
																																																																		"CAC8"), MSGID_NO_SECQUE_FOUND(
																																																																				"LOV1"), INVALID_UPI_REQ(
																																																																						"MU01"), MSGID_TOKEN_MISMATCH(
																																																																								"MT"), MSGID_TOKEN_BLANK(
																																																																										"BT"), BENEFICIERY_ADD_SUCCESS(
																																																																												"BEN1"), ALREADY_ADDED_BNF(
																																																																														"BEN2"), BLANK_PAYEE_NAME(
																																																																																"BEN3"), IFSC_NULL_MSG(
																																																																																		"BEN4"), IIN_NULL_MSG(
																																																																																				"BEN5"), MMID_NULL_MSG(
																																																																																						"BEN6"), NO_BENEFICIARY_FOUND_OR_INACTIVE_FOUND(
																																																																																								"BEN7"), MSGID_DELETED_SUCCESS(
																																																																																										"BEN8"), MSGID_UPDATE_SUCCESS(
																																																																																												"BEN9"), MSGID_UESR_ENTERED_SAME_CREDEN_AS_PRV(
																																																																																														"BEN10"), COMPLAINT_ADD_SUCCESS(
																																																																																																"COM1"), COMPLAINT_ALREADY_ADDED(
																																																																																																		"COM2"), MSGID_SUCCESS_UNBLOCK(
																																																																																																				"BVC1"), VIRTUALID_NOT_EXIST(
																																																																																																						"VPANE"), MSG_APP_VERSION_NOT_SUPPORTED(
																																																																																																								"APPVERNS"), MSGID_FEEDBACK_BLOCKED_MSG(
																																																																																																										"FDB2"), MSGID_SUCCESS_FEEDBACK(
																																																																																																												"FDB1"), MSGID_SUCCESS_ABOUTUS(
																																																																																																														"ABT1"), MSG_ACCOUNT_LIMIT_EXCEEDED(
																																																																																																																"ACCLIMEXCD"), MSG_DEFAULT_VIRTUALID(
																																																																																																																		"DEFVPA"), MSGID_WRONG_ACC_NO(
																																																																																																																				"CAC9"), MSGID_TECH_ISSUE(
																																																																																																																						"TECISSUE"), MSGID_SUCCESS_CHANGED_VPA_STATUS(
																																																																																																																								"CAC10"), MSGID_FAILED_CHANGED_VPA_STATUS(
																																																																																																																										"CAC11"), MSGID_FAIL_UNBLOCK(
																																																																																																																												"BVC2"), MSGID_FEEDBACK_IMAGES_EXCED(
																																																																																																																														"FDB3"), MSGID_WRONG_LOGIN_PIN(
																																																																																																																																"REG26"), MSGID_WRONG_APP_VER(
																																																																																																																																		"MAS1"), MSGID_UPDT_DEV_IMEI_SUCCESS(
																																																																																																																																				"REG123"),MSGID_UPDT_DEV_IMEI_FAILURE("REG122"),NOT_VERIFY("QR01"),MANDATE_AMOUNT_MAX("MAM"),ALREADY_INITIATE_REQUEST("AIR01"),INVALID_REQUEST_ID("IR01"),INVALID_REQUEST("IR02"),SOCITY_NO_MISMATCH("SMM01");
		
		private static Map<String, AcqErrorCode>	codeToEnumMap;
		
		private final String						code;
		
		AcqErrorCode(String code) {
			this.code = code;
		}
		
		public String getCode() {
			return code;
		}
		
		/**
		 * Looks up enum based on code. If code was not registered as enum, it
		 * returns
		 * UNKNOWN_ERROR_CODE
		 * 
		 * @param code
		 * @return
		 */
		public static AcqErrorCode fromCode(String code) {
			// Keep a hashmap of mapping between code and corresponding enum as
			// a cache. We need to initialize it only once
			if (codeToEnumMap == null) {
				codeToEnumMap = new HashMap<String, ErrorCode.AcqErrorCode>();
				for (AcqErrorCode aEnum : AcqErrorCode.values()) {
					codeToEnumMap.put(aEnum.getCode(), aEnum);
				}
			}
			
			AcqErrorCode enumForGivenCode = codeToEnumMap.get(code);
			if (enumForGivenCode == null) {
				enumForGivenCode = UNKNOWN_ERROR_CODE;
			}
			return enumForGivenCode;
		}
	}
	
}
