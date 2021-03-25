package com.npst.middleware.util;

public enum SMSMapping {
	// P_ListAccount_00("You have successfully added your {0} Account in {1}.",
	// "PSP", "List Account", 2),
	// P_SetPin_00("You have successfully created your UPI-PIN for {0} Account
	// in {1}.", "PSP", "Set PIN", 2),
	B_VPA_00("Your a/c no. {0} is credited for Rs.{1} on {2} and debited from a/c no. {3} (UPI Ref no {4})",
			"Beneficiary", "Pay using VPA", 5),
	B_AC_IFSC_00("Your a/c no. {0} is credited for Rs.{1} on {2} and debited from a/c no. {3} (UPI Ref no {4})",
			"Beneficiary", "Pay using a/c no. + IFSC code", 5),
	B_MOB_MMID_00(
			"Your a/c no. {0} is credited by Rs.{1} on {2} by a/c linked to mobile {3} is debited (UPI Ref no {4}).",
			"Beneficiary", "Pay using Mobile No. + MMID", 5),
	B_AADHAAR_00("Your a/c no. {0} linked to Aadhar No.{1} is credited for Rs.{2} on {3} (UPI Ref no {4}).",
			"Beneficiary", "Pay using Aadhaar No.", 5),
	B_REVERSAL_00("Your a/c no. {0} is debited for Rs.{1} on {2} for reversal of transaction (UPI Ref no {3}).",
			"Beneficiary", "Reversal", 4),
	R_VPA_00("Your a/c no. {0} is debited for Rs.{1} on {2} and credited to a/c no. {3} (UPI Ref no {4})", "Remitter",
			"Pay using VPA", 5),
	R_AC_IFSC_00("Your a/c no. {0} is debited for Rs.{1} on {2} and credited to a/c no. {3} (UPI Ref no {4})",
			"Remitter", "Pay using a/c no. + IFSC code", 5),
	R_MOB_MMID_00(
			"Your a/c no. {0} is debited for Rs.{1} on {2} and a/c linked to mobile {3} is credited (UPI Ref no {4}).",
			"Remitter", "Pay using Mobile No. + MMID", 5),
	R_AADHAAR_00(
			"Your a/c no. {0} is debited for Rs.{1} on {2} and credited to a/c linked to Aadhar No.{3} (UPI Ref no {4}).",
			"Remitter", "Pay using Aadhaar No.", 5),
	R_REVERSAL_00("Your a/c no. {0} is credited for Rs.{1} on {2} for reversal of transaction (UPI Ref no {3}).",
			"Remitter", "Reversal", 4);
	private final String	sms;
	private final String	sentBy;
	private final String	typeOfTrans;
	private final int		pCount;

	private SMSMapping(String sms, String sentBy, String typeOfTrans, int pCount) {
		this.sms = sms;
		this.sentBy = sentBy;
		this.typeOfTrans = typeOfTrans;
		this.pCount = pCount;

	}

	public int getpCount() {
		return pCount;
	}

	public String getSentBy() {
		return sentBy;
	}

	public String getSms() {
		return sms;
	}

	public String getTypeOfTrans() {
		return typeOfTrans;
	}

	@Override
	public String toString() {
		return sms + "|" + sentBy + "|" + typeOfTrans;
	}
}
