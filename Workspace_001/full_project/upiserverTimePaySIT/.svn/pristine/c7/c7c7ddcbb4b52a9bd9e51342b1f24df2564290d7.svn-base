package com.npst.upiserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIR_Info")
public class FirInfo implements Serializable {
	private static final long serialVersionUID = -2630842569370378263L;
	private Long idfirinfo;
	private String txnId;
	private String reqHeadMsgId;
	private String txnCustRef;
	private String InstitutionType;
	private String InstitutionRoute;
	private String NameValue;
	private String NameAcNum;
	private String PurposeCode;
	private String PurposeNote;
	private String OriginatorName;
	private String OriginatorType;
	private String OriginatorRefNo;
	private String AddressLocation;
	private String AddressCity;
	private String AddressCountry;
	private String AddressGeocode;
	private String BeneficiaryName;
	private String TxnTs;
	private String IdentityType;
	private String IdentityValue;
	private String PayerName;
	
	public void setPayerName(String payerName) {
		PayerName = payerName;
	}
	public void setIdentityType(String identityType) {
		IdentityType = identityType;
	}
	public void setIdentityValue(String identityValue) {
		IdentityValue = identityValue;
	}
	public void setIdfirinfo(Long idfirinfo) {
		this.idfirinfo = idfirinfo;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public void setReqHeadMsgId(String reqHeadMsgId) {
		this.reqHeadMsgId = reqHeadMsgId;
	}
	public void setTxnCustRef(String txnCustRef) {
		this.txnCustRef = txnCustRef;
	}
	public void setInstitutionType(String institutionType) {
		InstitutionType = institutionType;
	}
	public void setInstitutionRoute(String institutionRoute) {
		InstitutionRoute = institutionRoute;
	}
	public void setNameValue(String nameValue) {
		NameValue = nameValue;
	}
	public void setNameAcNum(String nameAcNum) {
		NameAcNum = nameAcNum;
	}
	public void setPurposeCode(String purposeCode) {
		PurposeCode = purposeCode;
	}
	public void setPurposeNote(String purposeNote) {
		PurposeNote = purposeNote;
	}
	public void setOriginatorName(String originatorName) {
		OriginatorName = originatorName;
	}
	public void setOriginatorType(String originatorType) {
		OriginatorType = originatorType;
	}
	public void setOriginatorRefNo(String originatorRefNo) {
		OriginatorRefNo = originatorRefNo;
	}
	public void setAddressLocation(String addressLocation) {
		AddressLocation = addressLocation;
	}
	public void setAddressCity(String addressCity) {
		AddressCity = addressCity;
	}
	public void setAddressCountry(String addressCountry) {
		AddressCountry = addressCountry;
	}
	public void setAddressGeocode(String addressGeocode) {
		AddressGeocode = addressGeocode;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		BeneficiaryName = beneficiaryName;
	}
	public void setTxnTs(String txnTs) {
		TxnTs = txnTs;
	}
	
	@Id
	@Column(name = "idfirinfo", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdfirinfo() {
		return idfirinfo;
	}
	@Column(name = "txnid", length = 36)
	public String getTxnId() {
		return txnId;
	}
	@Column(name = "reqheadmsgid", length = 36)
	public String getReqHeadMsgId() {
		return reqHeadMsgId;
	}
	@Column(name = "txncustref", length = 13)
	public String getTxnCustRef() {
		return txnCustRef;
	}
	@Column(name = "InstitutionType", length = 30)
	public String getInstitutionType() {
		return InstitutionType;
	}
	@Column(name = "InstitutionRoute", length = 30)
	public String getInstitutionRoute() {
		return InstitutionRoute;
	}
	@Column(name = "NameValue", length = 15)
	public String getNameValue() {
		return NameValue;
	}
	@Column(name = "NameAcNum", length = 30)
	public String getNameAcNum() {
		return NameAcNum;
	}
	@Column(name = "PurposeCode", length = 15)
	public String getPurposeCode() {
		return PurposeCode;
	}
	@Column(name = "PurposeNote", length = 500)
	public String getPurposeNote() {
		return PurposeNote;
	}
	@Column(name = "OriginatorName", length = 15)
	public String getOriginatorName() {
		return OriginatorName;
	}
	@Column(name = "OriginatorType", length = 30)
	public String getOriginatorType() {
		return OriginatorType;
	}
	@Column(name = "OriginatorRefNo", length = 15)
	public String getOriginatorRefNo() {
		return OriginatorRefNo;
	}
	@Column(name = "AddressLocation", length = 30)
	public String getAddressLocation() {
		return AddressLocation;
	}
	@Column(name = "AddressCity", length = 30)
	public String getAddressCity() {
		return AddressCity;
	}
	@Column(name = "AddressCountry", length = 30)
	public String getAddressCountry() {
		return AddressCountry;
	}
	@Column(name = "AddressGeocode", length = 50)
	public String getAddressGeocode() {
		return AddressGeocode;
	}
	@Column(name = "BeneficiaryName", length = 50)
	public String getBeneficiaryName() {
		return BeneficiaryName;
	}
	@Column(name = "txnts", length = 30)
	public String getTxnTs() {
		return TxnTs;
	}
	
	@Column(name = "IdentityType", length = 30)
	public String getIdentityType() {
		return IdentityType;
	}
	@Column(name = "IdentityValue", length = 30)
	public String getIdentityValue() {
		return IdentityValue;
	}
	@Column(name = "PayerName", length = 30)
	public String getPayerName() {
		return PayerName;
	}
	public FirInfo(Long idfirinfo, String txnId, String reqHeadMsgId, String txnCustRef, String institutionType,
			String institutionRoute, String nameValue, String nameAcNum, String purposeCode, String purposeNote,
			String originatorName, String originatorType, String originatorRefNo, String addressLocation,
			String addressCity, String addressCountry, String addressGeocode, String beneficiaryName, String txnTs,
			String identityType, String identityValue, String payerName) {
		super();
		this.idfirinfo = idfirinfo;
		this.txnId = txnId;
		this.reqHeadMsgId = reqHeadMsgId;
		this.txnCustRef = txnCustRef;
		InstitutionType = institutionType;
		InstitutionRoute = institutionRoute;
		NameValue = nameValue;
		NameAcNum = nameAcNum;
		PurposeCode = purposeCode;
		PurposeNote = purposeNote;
		OriginatorName = originatorName;
		OriginatorType = originatorType;
		OriginatorRefNo = originatorRefNo;
		AddressLocation = addressLocation;
		AddressCity = addressCity;
		AddressCountry = addressCountry;
		AddressGeocode = addressGeocode;
		BeneficiaryName = beneficiaryName;
		TxnTs = txnTs;
		IdentityType = identityType;
		IdentityValue = identityValue;
		PayerName = payerName;
	}
	public FirInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FirInfo [");
		if (idfirinfo != null) {
			builder.append("idfirinfo=");
			builder.append(idfirinfo);
			builder.append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=");
			builder.append(txnId);
			builder.append(", ");
		}
		if (reqHeadMsgId != null) {
			builder.append("reqHeadMsgId=");
			builder.append(reqHeadMsgId);
			builder.append(", ");
		}
		if (txnCustRef != null) {
			builder.append("txnCustRef=");
			builder.append(txnCustRef);
			builder.append(", ");
		}
		if (InstitutionType != null) {
			builder.append("InstitutionType=");
			builder.append(InstitutionType);
			builder.append(", ");
		}
		if (InstitutionRoute != null) {
			builder.append("InstitutionRoute=");
			builder.append(InstitutionRoute);
			builder.append(", ");
		}
		if (NameValue != null) {
			builder.append("NameValue=");
			builder.append(NameValue);
			builder.append(", ");
		}
		if (NameAcNum != null) {
			builder.append("NameAcNum=");
			builder.append(NameAcNum);
			builder.append(", ");
		}
		if (PurposeCode != null) {
			builder.append("PurposeCode=");
			builder.append(PurposeCode);
			builder.append(", ");
		}
		if (PurposeNote != null) {
			builder.append("PurposeNote=");
			builder.append(PurposeNote);
			builder.append(", ");
		}
		if (OriginatorName != null) {
			builder.append("OriginatorName=");
			builder.append(OriginatorName);
			builder.append(", ");
		}
		if (OriginatorType != null) {
			builder.append("OriginatorType=");
			builder.append(OriginatorType);
			builder.append(", ");
		}
		if (OriginatorRefNo != null) {
			builder.append("OriginatorRefNo=");
			builder.append(OriginatorRefNo);
			builder.append(", ");
		}
		if (AddressLocation != null) {
			builder.append("AddressLocation=");
			builder.append(AddressLocation);
			builder.append(", ");
		}
		if (AddressCity != null) {
			builder.append("AddressCity=");
			builder.append(AddressCity);
			builder.append(", ");
		}
		if (AddressCountry != null) {
			builder.append("AddressCountry=");
			builder.append(AddressCountry);
			builder.append(", ");
		}
		if (AddressGeocode != null) {
			builder.append("AddressGeocode=");
			builder.append(AddressGeocode);
			builder.append(", ");
		}
		if (BeneficiaryName != null) {
			builder.append("BeneficiaryName=");
			builder.append(BeneficiaryName);
			builder.append(", ");
		}
		if (TxnTs != null) {
			builder.append("TxnTs=");
			builder.append(TxnTs);
			builder.append(", ");
		}
		if (IdentityType != null) {
			builder.append("IdentityType=");
			builder.append(IdentityType);
			builder.append(", ");
		}
		if (IdentityValue != null) {
			builder.append("IdentityValue=");
			builder.append(IdentityValue);
			builder.append(", ");
		}
		if (PayerName != null) {
			builder.append("PayerName=");
			builder.append(PayerName);
		}
		builder.append("]");
		return builder.toString();
	}
}
