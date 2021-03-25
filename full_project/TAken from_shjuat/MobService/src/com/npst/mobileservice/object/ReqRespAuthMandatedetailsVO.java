package com.npst.mobileservice.object;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.npst.upi.hor.ReqRespAuthMandateEntity;
import com.npst.upi.hor.ReqRespAuthMandatePayeesEntity;
import com.npst.upi.hor.ReqrespauthdetailsPayees;

public class ReqRespAuthMandatedetailsVO implements Serializable, Comparator<ReqRespAuthMandatedetailsVO> {

	private static final long serialVersionUID = 1L;
	private Long							idReqRespAuthDetails;
	private Long							regid;
	private String							reqHeadTs;
	private String							reqHeadOrgId;
	private String							reqHeadMsgId;
	private String							respHeadTs;
	private String							respHeadOrgId;
	private String							respHeadMsgId;
	private String							txnId;
	private String							txnIdPrf;
	private String							txnNote;
	private String							txnRefid;
	private String							txnRefurl;
	private String							txnTs;
	private String							txnType;
	private String							txnCustRef;
	private Integer							ruleExpireAfter;
	private String							ruleMinAmount;
	private String							payerAddr;
	private String							payerName;
	private String							payerSeqNum;
	private String							payerType;
	private String							payerCode;
	private String							infoIdType;
	private String							infoId;
	private String							infoIdVerifiedName;
	private String							infoIdRatingvaddr;
	private String							acAddrType;
	private String							acIfsc;
	private String							acType;
	private String							acno;
	private String							credType;
	private String							credSubType;
	private String							deviceMobile;
	private String							deviceGeocode;
	private String							deviceLocation;
	private String							deviceIp;
	private String							deviceType;
	private String							deviceId;
	private String							deviceOs;
	private String							deviceApp;
	private String							deviceCapability;
	private String							amountCrr;
	private String							amountVal;
	private String							respResult;
	private String							respErrCode;
	private Date							reqInsert;
	private Date							respInsert;
	private String							respAuthAck;
	private String							txnConfOrgErrCode;
	private String							txnConfOrgStatus;
	private String							refOrgAmount;
	private String							refSettAmount;
	private String							refRespCode;
	private String							refApprovalNum;
	private String							refReversalRespCode;
	private String							txnConfAck;
	private String							payerHandal;
	private List<ReqRespAuthMandatePayeesdetailsVO>	payees;
	private Date							collectTime;
	private String 				txnInitiatedBy; 
	private String 						validityStart;
	private String 						validityEnd;
	private String payeeAddr;
	private String payeeName;
	private String payeeSeqNum;
	private String payeeType;
	private String payeeCode;
	private String mandateName;
	private String mandateUmn;
	public ReqRespAuthMandatedetailsVO() {
		// TODO Auto-generated constructor stub
	}
	
	public ReqRespAuthMandatedetailsVO(ReqRespAuthMandateEntity reqrespauthdetails) {
		
		this.acAddrType=reqrespauthdetails.getAcAddrType();
		this.acIfsc=reqrespauthdetails.getAcAddrTypeDetail1();
		this.acType=reqrespauthdetails.getAcAddrTypeDetail2();
		this.acType=reqrespauthdetails.getAcAddrTypeDetail3();
		this.amountCrr=reqrespauthdetails.getRefSettCurrency();
		this.amountVal=reqrespauthdetails.getMandateAmountvalue();
		this.payerAddr=reqrespauthdetails.getPayerAddr();
		this.payerName=reqrespauthdetails.getPayerName();
		this.txnCustRef=reqrespauthdetails.getTxnCustRef();
		this.txnId=reqrespauthdetails.getTxnId();
		this.txnInitiatedBy=reqrespauthdetails.getTxnInitiatedBy();
		this.validityStart=reqrespauthdetails.getMandateValidityStart();
		this.validityEnd=reqrespauthdetails.getMandateValidityEnd();
		this.mandateName=reqrespauthdetails.getMandateName();
	}
	
	
	public String getPayeeAddr() {
		return payeeAddr;
	}


	public void setPayeeAddr(String payeeAddr) {
		this.payeeAddr = payeeAddr;
	}


	public String getPayeeName() {
		return payeeName;
	}


	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}


	public String getPayeeSeqNum() {
		return payeeSeqNum;
	}


	public void setPayeeSeqNum(String payeeSeqNum) {
		this.payeeSeqNum = payeeSeqNum;
	}


	public String getPayeeType() {
		return payeeType;
	}


	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}


	public String getPayeeCode() {
		return payeeCode;
	}


	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}


	public String getMandateName() {
		return mandateName;
	}


	public void setMandateName(String mandateName) {
		this.mandateName = mandateName;
	}


	public String getMandateUmn() {
		return mandateUmn;
	}


	public void setMandateUmn(String mandateUmn) {
		this.mandateUmn = mandateUmn;
	}


	public String getValidityStart() {
		return validityStart;
	}


	public void setValidityStart(String validityStart) {
		this.validityStart = validityStart;
	}


	public String getValidityEnd() {
		return validityEnd;
	}


	public void setValidityEnd(String validityEnd) {
		this.validityEnd = validityEnd;
	}


	public String getTxnInitiatedBy() {
		return txnInitiatedBy;
	}

	public void setTxnInitiatedBy(String txnInitiatedBy) {
		this.txnInitiatedBy = txnInitiatedBy;
	}

	public Long getIdReqRespAuthDetails() {
		return idReqRespAuthDetails;
	}
	public void setIdReqRespAuthDetails(Long idReqRespAuthDetails) {
		this.idReqRespAuthDetails = idReqRespAuthDetails;
	}
	public Long getRegid() {
		return regid;
	}
	public void setRegid(Long regid) {
		this.regid = regid;
	}
	public String getReqHeadTs() {
		return reqHeadTs;
	}
	public void setReqHeadTs(String reqHeadTs) {
		this.reqHeadTs = reqHeadTs;
	}
	public String getReqHeadOrgId() {
		return reqHeadOrgId;
	}
	public void setReqHeadOrgId(String reqHeadOrgId) {
		this.reqHeadOrgId = reqHeadOrgId;
	}
	public String getReqHeadMsgId() {
		return reqHeadMsgId;
	}
	public void setReqHeadMsgId(String reqHeadMsgId) {
		this.reqHeadMsgId = reqHeadMsgId;
	}
	public String getRespHeadTs() {
		return respHeadTs;
	}
	public void setRespHeadTs(String respHeadTs) {
		this.respHeadTs = respHeadTs;
	}
	public String getRespHeadOrgId() {
		return respHeadOrgId;
	}
	public void setRespHeadOrgId(String respHeadOrgId) {
		this.respHeadOrgId = respHeadOrgId;
	}
	public String getRespHeadMsgId() {
		return respHeadMsgId;
	}
	public void setRespHeadMsgId(String respHeadMsgId) {
		this.respHeadMsgId = respHeadMsgId;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getTxnIdPrf() {
		return txnIdPrf;
	}
	public void setTxnIdPrf(String txnIdPrf) {
		this.txnIdPrf = txnIdPrf;
	}
	public String getTxnNote() {
		return txnNote;
	}
	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}
	public String getTxnRefid() {
		return txnRefid;
	}
	public void setTxnRefid(String txnRefid) {
		this.txnRefid = txnRefid;
	}
	public String getTxnRefurl() {
		return txnRefurl;
	}
	public void setTxnRefurl(String txnRefurl) {
		this.txnRefurl = txnRefurl;
	}
	public String getTxnTs() {
		return txnTs;
	}
	public void setTxnTs(String txnTs) {
		this.txnTs = txnTs;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnCustRef() {
		return txnCustRef;
	}
	public void setTxnCustRef(String txnCustRef) {
		this.txnCustRef = txnCustRef;
	}
	public Integer getRuleExpireAfter() {
		return ruleExpireAfter;
	}
	public void setRuleExpireAfter(Integer ruleExpireAfter) {
		this.ruleExpireAfter = ruleExpireAfter;
	}
	public String getRuleMinAmount() {
		return ruleMinAmount;
	}
	public void setRuleMinAmount(String ruleMinAmount) {
		this.ruleMinAmount = ruleMinAmount;
	}
	public String getPayerAddr() {
		return payerAddr;
	}
	public void setPayerAddr(String payerAddr) {
		this.payerAddr = payerAddr;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayerSeqNum() {
		return payerSeqNum;
	}
	public void setPayerSeqNum(String payerSeqNum) {
		this.payerSeqNum = payerSeqNum;
	}
	public String getPayerType() {
		return payerType;
	}
	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}
	public String getPayerCode() {
		return payerCode;
	}
	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}
	public String getInfoIdType() {
		return infoIdType;
	}
	public void setInfoIdType(String infoIdType) {
		this.infoIdType = infoIdType;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getInfoIdVerifiedName() {
		return infoIdVerifiedName;
	}
	public void setInfoIdVerifiedName(String infoIdVerifiedName) {
		this.infoIdVerifiedName = infoIdVerifiedName;
	}
	public String getInfoIdRatingvaddr() {
		return infoIdRatingvaddr;
	}
	public void setInfoIdRatingvaddr(String infoIdRatingvaddr) {
		this.infoIdRatingvaddr = infoIdRatingvaddr;
	}
	public String getAcAddrType() {
		return acAddrType;
	}
	public void setAcAddrType(String acAddrType) {
		this.acAddrType = acAddrType;
	}
	
	public String getAcIfsc() {
		return acIfsc;
	}


	public void setAcIfsc(String acIfsc) {
		this.acIfsc = acIfsc;
	}


	public String getAcType() {
		return acType;
	}


	public void setAcType(String acType) {
		this.acType = acType;
	}


	public String getAcno() {
		return acno;
	}


	public void setAcno(String acno) {
		this.acno = acno;
	}


	public String getCredType() {
		return credType;
	}
	public void setCredType(String credType) {
		this.credType = credType;
	}
	public String getCredSubType() {
		return credSubType;
	}
	public void setCredSubType(String credSubType) {
		this.credSubType = credSubType;
	}
	public String getDeviceMobile() {
		return deviceMobile;
	}
	public void setDeviceMobile(String deviceMobile) {
		this.deviceMobile = deviceMobile;
	}
	public String getDeviceGeocode() {
		return deviceGeocode;
	}
	public void setDeviceGeocode(String deviceGeocode) {
		this.deviceGeocode = deviceGeocode;
	}
	public String getDeviceLocation() {
		return deviceLocation;
	}
	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}
	public String getDeviceIp() {
		return deviceIp;
	}
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceOs() {
		return deviceOs;
	}
	public void setDeviceOs(String deviceOs) {
		this.deviceOs = deviceOs;
	}
	public String getDeviceApp() {
		return deviceApp;
	}
	public void setDeviceApp(String deviceApp) {
		this.deviceApp = deviceApp;
	}
	public String getDeviceCapability() {
		return deviceCapability;
	}
	public void setDeviceCapability(String deviceCapability) {
		this.deviceCapability = deviceCapability;
	}
	public String getAmountCrr() {
		return amountCrr;
	}
	public void setAmountCrr(String amountCrr) {
		this.amountCrr = amountCrr;
	}
	public String getAmountVal() {
		return amountVal;
	}
	public void setAmountVal(String amountVal) {
		this.amountVal = amountVal;
	}
	public String getRespResult() {
		return respResult;
	}
	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}
	public String getRespErrCode() {
		return respErrCode;
	}
	public void setRespErrCode(String respErrCode) {
		this.respErrCode = respErrCode;
	}
	public Date getReqInsert() {
		return reqInsert;
	}
	public void setReqInsert(Date reqInsert) {
		this.reqInsert = reqInsert;
	}
	public Date getRespInsert() {
		return respInsert;
	}
	public void setRespInsert(Date respInsert) {
		this.respInsert = respInsert;
	}
	public String getRespAuthAck() {
		return respAuthAck;
	}
	public void setRespAuthAck(String respAuthAck) {
		this.respAuthAck = respAuthAck;
	}
	public String getTxnConfOrgErrCode() {
		return txnConfOrgErrCode;
	}
	public void setTxnConfOrgErrCode(String txnConfOrgErrCode) {
		this.txnConfOrgErrCode = txnConfOrgErrCode;
	}
	public String getTxnConfOrgStatus() {
		return txnConfOrgStatus;
	}
	public void setTxnConfOrgStatus(String txnConfOrgStatus) {
		this.txnConfOrgStatus = txnConfOrgStatus;
	}
	public String getRefOrgAmount() {
		return refOrgAmount;
	}
	public void setRefOrgAmount(String refOrgAmount) {
		this.refOrgAmount = refOrgAmount;
	}
	public String getRefSettAmount() {
		return refSettAmount;
	}
	public void setRefSettAmount(String refSettAmount) {
		this.refSettAmount = refSettAmount;
	}
	public String getRefRespCode() {
		return refRespCode;
	}
	public void setRefRespCode(String refRespCode) {
		this.refRespCode = refRespCode;
	}
	public String getRefApprovalNum() {
		return refApprovalNum;
	}
	public void setRefApprovalNum(String refApprovalNum) {
		this.refApprovalNum = refApprovalNum;
	}
	public String getRefReversalRespCode() {
		return refReversalRespCode;
	}
	public void setRefReversalRespCode(String refReversalRespCode) {
		this.refReversalRespCode = refReversalRespCode;
	}
	public String getTxnConfAck() {
		return txnConfAck;
	}
	public void setTxnConfAck(String txnConfAck) {
		this.txnConfAck = txnConfAck;
	}
	public String getPayerHandal() {
		return payerHandal;
	}
	public void setPayerHandal(String payerHandal) {
		this.payerHandal = payerHandal;
	}
	public List<ReqRespAuthMandatePayeesdetailsVO> getPayees() {
		return payees;
	}
	public void setPayees(List<ReqRespAuthMandatePayeesdetailsVO> payees) {
		this.payees = payees;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReqRespAuthMandatedetailsVO [");
		if (idReqRespAuthDetails != null) {
			builder.append("idReqRespAuthDetails=");
			builder.append(idReqRespAuthDetails);
			builder.append(", ");
		}
		if (regid != null) {
			builder.append("regid=");
			builder.append(regid);
			builder.append(", ");
		}
		if (reqHeadTs != null) {
			builder.append("reqHeadTs=");
			builder.append(reqHeadTs);
			builder.append(", ");
		}
		if (reqHeadOrgId != null) {
			builder.append("reqHeadOrgId=");
			builder.append(reqHeadOrgId);
			builder.append(", ");
		}
		if (reqHeadMsgId != null) {
			builder.append("reqHeadMsgId=");
			builder.append(reqHeadMsgId);
			builder.append(", ");
		}
		if (respHeadTs != null) {
			builder.append("respHeadTs=");
			builder.append(respHeadTs);
			builder.append(", ");
		}
		if (respHeadOrgId != null) {
			builder.append("respHeadOrgId=");
			builder.append(respHeadOrgId);
			builder.append(", ");
		}
		if (respHeadMsgId != null) {
			builder.append("respHeadMsgId=");
			builder.append(respHeadMsgId);
			builder.append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=");
			builder.append(txnId);
			builder.append(", ");
		}
		if (txnIdPrf != null) {
			builder.append("txnIdPrf=");
			builder.append(txnIdPrf);
			builder.append(", ");
		}
		if (txnNote != null) {
			builder.append("txnNote=");
			builder.append(txnNote);
			builder.append(", ");
		}
		if (txnRefid != null) {
			builder.append("txnRefid=");
			builder.append(txnRefid);
			builder.append(", ");
		}
		if (txnRefurl != null) {
			builder.append("txnRefurl=");
			builder.append(txnRefurl);
			builder.append(", ");
		}
		if (txnTs != null) {
			builder.append("txnTs=");
			builder.append(txnTs);
			builder.append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=");
			builder.append(txnType);
			builder.append(", ");
		}
		if (txnCustRef != null) {
			builder.append("txnCustRef=");
			builder.append(txnCustRef);
			builder.append(", ");
		}
		if (ruleExpireAfter != null) {
			builder.append("ruleExpireAfter=");
			builder.append(ruleExpireAfter);
			builder.append(", ");
		}
		if (ruleMinAmount != null) {
			builder.append("ruleMinAmount=");
			builder.append(ruleMinAmount);
			builder.append(", ");
		}
		if (payerAddr != null) {
			builder.append("payerAddr=");
			builder.append(payerAddr);
			builder.append(", ");
		}
		if (payerName != null) {
			builder.append("payerName=");
			builder.append(payerName);
			builder.append(", ");
		}
		if (payerSeqNum != null) {
			builder.append("payerSeqNum=");
			builder.append(payerSeqNum);
			builder.append(", ");
		}
		if (payerType != null) {
			builder.append("payerType=");
			builder.append(payerType);
			builder.append(", ");
		}
		if (payerCode != null) {
			builder.append("payerCode=");
			builder.append(payerCode);
			builder.append(", ");
		}
		if (infoIdType != null) {
			builder.append("infoIdType=");
			builder.append(infoIdType);
			builder.append(", ");
		}
		if (infoId != null) {
			builder.append("infoId=");
			builder.append(infoId);
			builder.append(", ");
		}
		if (infoIdVerifiedName != null) {
			builder.append("infoIdVerifiedName=");
			builder.append(infoIdVerifiedName);
			builder.append(", ");
		}
		if (infoIdRatingvaddr != null) {
			builder.append("infoIdRatingvaddr=");
			builder.append(infoIdRatingvaddr);
			builder.append(", ");
		}
		if (acAddrType != null) {
			builder.append("acAddrType=");
			builder.append(acAddrType);
			builder.append(", ");
		}
		if (acIfsc != null) {
			builder.append("acAddrTypeDetail1=");
			builder.append(acIfsc);
			builder.append(", ");
		}
		if (acType != null) {
			builder.append("acAddrTypeDetail2=");
			builder.append(acType);
			builder.append(", ");
		}
		if (acno != null) {
			builder.append("acAddrTypeDetail3=");
			builder.append(acno);
			builder.append(", ");
		}
		if (credType != null) {
			builder.append("credType=");
			builder.append(credType);
			builder.append(", ");
		}
		if (credSubType != null) {
			builder.append("credSubType=");
			builder.append(credSubType);
			builder.append(", ");
		}
		if (deviceMobile != null) {
			builder.append("deviceMobile=");
			builder.append(deviceMobile);
			builder.append(", ");
		}
		if (deviceGeocode != null) {
			builder.append("deviceGeocode=");
			builder.append(deviceGeocode);
			builder.append(", ");
		}
		if (deviceLocation != null) {
			builder.append("deviceLocation=");
			builder.append(deviceLocation);
			builder.append(", ");
		}
		if (deviceIp != null) {
			builder.append("deviceIp=");
			builder.append(deviceIp);
			builder.append(", ");
		}
		if (deviceType != null) {
			builder.append("deviceType=");
			builder.append(deviceType);
			builder.append(", ");
		}
		if (deviceId != null) {
			builder.append("deviceId=");
			builder.append(deviceId);
			builder.append(", ");
		}
		if (deviceOs != null) {
			builder.append("deviceOs=");
			builder.append(deviceOs);
			builder.append(", ");
		}
		if (deviceApp != null) {
			builder.append("deviceApp=");
			builder.append(deviceApp);
			builder.append(", ");
		}
		if (deviceCapability != null) {
			builder.append("deviceCapability=");
			builder.append(deviceCapability);
			builder.append(", ");
		}
		if (amountCrr != null) {
			builder.append("amountCrr=");
			builder.append(amountCrr);
			builder.append(", ");
		}
		if (amountVal != null) {
			builder.append("amountVal=");
			builder.append(amountVal);
			builder.append(", ");
		}
		if (respResult != null) {
			builder.append("respResult=");
			builder.append(respResult);
			builder.append(", ");
		}
		if (respErrCode != null) {
			builder.append("respErrCode=");
			builder.append(respErrCode);
			builder.append(", ");
		}
		if (reqInsert != null) {
			builder.append("reqInsert=");
			builder.append(reqInsert);
			builder.append(", ");
		}
		if (respInsert != null) {
			builder.append("respInsert=");
			builder.append(respInsert);
			builder.append(", ");
		}
		if (respAuthAck != null) {
			builder.append("respAuthAck=");
			builder.append(respAuthAck);
			builder.append(", ");
		}
		if (txnConfOrgErrCode != null) {
			builder.append("txnConfOrgErrCode=");
			builder.append(txnConfOrgErrCode);
			builder.append(", ");
		}
		if (txnConfOrgStatus != null) {
			builder.append("txnConfOrgStatus=");
			builder.append(txnConfOrgStatus);
			builder.append(", ");
		}
		if (refOrgAmount != null) {
			builder.append("refOrgAmount=");
			builder.append(refOrgAmount);
			builder.append(", ");
		}
		if (refSettAmount != null) {
			builder.append("refSettAmount=");
			builder.append(refSettAmount);
			builder.append(", ");
		}
		if (refRespCode != null) {
			builder.append("refRespCode=");
			builder.append(refRespCode);
			builder.append(", ");
		}
		if (refApprovalNum != null) {
			builder.append("refApprovalNum=");
			builder.append(refApprovalNum);
			builder.append(", ");
		}
		if (refReversalRespCode != null) {
			builder.append("refReversalRespCode=");
			builder.append(refReversalRespCode);
			builder.append(", ");
		}
		if (txnConfAck != null) {
			builder.append("txnConfAck=");
			builder.append(txnConfAck);
			builder.append(", ");
		}
		if (payerHandal != null) {
			builder.append("payerHandal=");
			builder.append(payerHandal);
			builder.append(", ");
		}
		if (payees != null) {
			builder.append("payees=");
			builder.append(payees);
			builder.append(", ");
		}
		if (collectTime != null) {
			builder.append("collectTime=");
			builder.append(collectTime);
		}
		builder.append("]");
		return builder.toString();
	}


	public void setPayeedetails(ReqRespAuthMandatedetailsVO reqrespauthdetailsVO2,
			List<ReqRespAuthMandatePayeesEntity> reqRespAuthMandatePayees) {
		reqrespauthdetailsVO2.setPayeeAddr(reqRespAuthMandatePayees.get(0).getPayeeAddr());
		reqrespauthdetailsVO2.setPayeeCode(reqRespAuthMandatePayees.get(0).getPayeeCode());
		reqrespauthdetailsVO2.setPayeeName(reqRespAuthMandatePayees.get(0).getPayeeName());
		reqrespauthdetailsVO2.setPayeeSeqNum(reqRespAuthMandatePayees.get(0).getPayeeSeqNum());
		reqrespauthdetailsVO2.setPayeeType(reqRespAuthMandatePayees.get(0).getPayeeType());
		reqrespauthdetailsVO2.setAcIfsc(reqRespAuthMandatePayees.get(0).getAcAddrTypeDetail1());
		reqrespauthdetailsVO2.setAcType(reqRespAuthMandatePayees.get(0).getAcAddrTypeDetail2());
		reqrespauthdetailsVO2.setAcno(reqRespAuthMandatePayees.get(0).getAcAddrTypeDetail3());
	}

	
	@Override
	public int compare(ReqRespAuthMandatedetailsVO o1, ReqRespAuthMandatedetailsVO o2) {
		
		if(o1.getReqInsert().before(o2.getReqInsert())) {
			return +1;
		}
		if(o1.getReqInsert().after(o2.getReqInsert())) {
			return -1;

		}
		else {
			return 0;
		}
			
		}
}
