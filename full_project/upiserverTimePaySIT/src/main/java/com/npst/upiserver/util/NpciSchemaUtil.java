package com.npst.upiserver.util;

import java.util.List;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.DeviceType.Tag;

public class NpciSchemaUtil {
	public static HeadType getNewHeadType() {
		HeadType head = new HeadType();
		head.setMsgId(Util.uuidGen());
		head.setOrgId(Constant.orgId);
		head.setTs(Util.getTS());
		head.setVer(Constant.headVer);
		return head;
	}

	public static HeadType getNewHeadType(String msgId) {
		HeadType head = new HeadType();
		head.setMsgId(msgId);
		head.setOrgId(Constant.orgId);
		head.setTs(Util.getTS());
		head.setVer(Constant.headVer);
		return head;
	}

	public static HeadType getNewHeadType(String msgId, String ts) {
		HeadType head = new HeadType();
		head.setMsgId(msgId);
		head.setOrgId(Constant.orgId);
		head.setTs(ts);
		head.setVer(Constant.headVer);
		return head;
	}

	public static String getTxnIdPrefix(String txnId) {
		if (txnId != null && txnId.length() > 3) {
			return txnId.substring(0, 3);
		} else {
			return null;
		}
	}

	public static String getHandler(String vpa) {
		if (vpa != null) {
			int i = vpa.indexOf(ConstantI.AT_SYMBOL);
			if (i >= 0) {
				return vpa.substring(i);
			}
		}
		return null;
	}

	public static DeviceType getPayerDeviceTypeHdd(final ReqResp reqResp) {
		DeviceType device = new DeviceType();
		List<Tag> tags = device.getTag();
		{
			{
				Tag tag = new Tag();
				tag.setName(DeviceTagNameType.MOBILE);
				tag.setValue(Util.getProperty("DEVICE_MOB"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.GEOCODE);
				tag.setValue(Util.getProperty("GEOC_ODE"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.LOCATION);
				tag.setValue(Util.getProperty("DEVICE_LOCATION"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.IP);
				tag.setValue(Util.getProperty("DEVICE_IP"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.TYPE);
				tag.setValue(Util.getProperty("DEVICE_TYPE"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.ID);
				tag.setValue(Util.getProperty("DEVICE_ID"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.OS);
				tag.setValue(Util.getProperty("DEVICE_OS"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.APP);
				tag.setValue(Util.getProperty("DEVICE_APP"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.CAPABILITY);
				tag.setValue(Util.getProperty("DEVICE_CAPABILITY"));
				tags.add(tag);
				tag = new Tag();
				if (reqResp.getPayerDeviceTelecom() != null && !reqResp.getPayerDeviceTelecom().isEmpty()) {
					tag = new Tag();
					tag.setName(DeviceTagNameType.TELECOM);
					tag.setValue(Util.getProperty("DEVICE_TELECOM"));
					tags.add(tag);
				}
			}
		}
		return device;
	}
	
	
	public static DeviceType getPayerDeviceType(final ReqResp reqResp) {
		DeviceType device = new DeviceType();
		List<Tag> tags = device.getTag();
		{
			{
				Tag tag = new Tag();
				tag.setName(DeviceTagNameType.MOBILE);
				tag.setValue(reqResp.getPayerDeviceMobile());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.GEOCODE);
				tag.setValue(reqResp.getPayerDeviceGeoCode());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.LOCATION);
				tag.setValue(reqResp.getPayerDeviceLocation());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.IP);
				tag.setValue(reqResp.getPayerDeviceIp());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.TYPE);
				tag.setValue(reqResp.getPayerDeviceType());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.ID);
				tag.setValue(reqResp.getPayerDeviceId());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.OS);
				tag.setValue(reqResp.getPayerDeviceOsType());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.APP);
				tag.setValue(reqResp.getPayerDeviceAppId());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.CAPABILITY);
				tag.setValue(reqResp.getPayerDeviceCapability().trim());
				tags.add(tag);
				tag = new Tag();
				if(reqResp.getPayerDeviceTelecom()!=null && !reqResp.getPayerDeviceTelecom().isEmpty()) {
					tag = new Tag();
					tag.setName(DeviceTagNameType.TELECOM);
					tag.setValue(reqResp.getPayerDeviceTelecom());
					tags.add(tag);	
				}
			}
		}
		return device;
	}
	
	
	public static DeviceType getPayerDeviceTypetest1() {
		
		DeviceType device = new DeviceType();
		List<Tag> tags = device.getTag();
		{
			{
				Tag tag = new Tag();
				tag.setName(DeviceTagNameType.MOBILE);
				tag.setValue("919911143801");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.GEOCODE);
				tag.setValue("28.58436,77.31361166666667");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.LOCATION);
				tag.setValue("Noida");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.IP);
				tag.setValue("25.80.67.65");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.TYPE);
				tag.setValue("MOB");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.ID);
				tag.setValue("fd0c3cb2dac93c57");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.OS);
				tag.setValue("ANDROID 8.1.0");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.APP);
				tag.setValue("com.canarabank.mobility");
				
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.CAPABILITY);
				tag.setValue("520000020001000201301917906743578");
				tags.add(tag);
				tag = new Tag();
				
			}
		}
		return device;
	}

	
	public static DeviceType getPayerDeviceTypetest() {
		
		DeviceType device = new DeviceType();
		List<Tag> tags = device.getTag();
		{
			{
				Tag tag = new Tag();
				tag.setName(DeviceTagNameType.MOBILE);
				tag.setValue("919911143801");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.GEOCODE);
				tag.setValue("28.58436,77.31361166666667");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.LOCATION);
				tag.setValue("Noida");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.IP);
				tag.setValue("25.80.67.65");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.TYPE);
				tag.setValue("MOB");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.ID);
				tag.setValue("fd0c3cb2dac93c57");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.OS);
				tag.setValue("ANDROID 8.1.0");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.APP);
				tag.setValue("com.cosmos.mobility");
				
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.CAPABILITY);
				tag.setValue("520000020001000201301917906743578");
				tags.add(tag);
				tag = new Tag();
				
			}
		}
		return device;
	}
	public static DeviceType getPayeeDeviceType(final ReqResp reqResp) {
		DeviceType device = new DeviceType();
		List<Tag> tags = device.getTag();
		{
			{
				Tag tag = new Tag();
				tag.setName(DeviceTagNameType.MOBILE);
				tag.setValue(reqResp.getPayeeDeviceMobile());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.GEOCODE);
				tag.setValue(reqResp.getPayeeDeviceGeoCode());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.LOCATION);
				tag.setValue(reqResp.getPayeeDeviceLocation());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.IP);
				tag.setValue(reqResp.getPayeeDeviceIp());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.TYPE);
				tag.setValue(reqResp.getPayeeDeviceType());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.ID);
				tag.setValue(reqResp.getPayeeDeviceId());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.OS);
				tag.setValue(reqResp.getPayeeDeviceOsType());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.APP);
				tag.setValue(reqResp.getPayeeDeviceAppId());
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.CAPABILITY);
				tag.setValue(reqResp.getPayeeDeviceCapability().trim());
				tags.add(tag);
				if (reqResp.getPayeeDeviceTelecom() != null && !reqResp.getPayeeDeviceTelecom().isEmpty()) {
					tag = new Tag();
					tag.setName(DeviceTagNameType.TELECOM);
					tag.setValue(reqResp.getPayeeDeviceTelecom());
					tags.add(tag);
				}
			}
		}
		return device;
	}

	public static AccountType getPayerAccountType(final ReqResp reqResp) {
		AccountType ac = new AccountType();
		//ac.setAddrType(AddressType.fromValue(reqResp.getPayerAddrType()));
		ac.setAddrType(AddressType.ACCOUNT);
		List<Detail> details = ac.getDetail();
		//if (ConstantI.ACCOUNT.equalsIgnoreCase(reqResp.getPayerAddrType())) {
			Detail detail = new Detail();
			detail.setName(AccountDetailType.IFSC);
			detail.setValue(reqResp.getPayerIfsc().toUpperCase());//reqResp.getPayerIfsc().toUpperCase();
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.ACTYPE);
			detail.setValue(reqResp.getPayerAcType());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.ACNUM);
			detail.setValue(reqResp.getPayerAcNum());
			details.add(detail);
		/*} else if (ConstantI.MOBILE.equalsIgnoreCase(reqResp.getPayerAddrType())) {
			Detail detail = new Detail();
			detail.setName(AccountDetailType.MMID);
			detail.setValue(reqResp.getPayerMmid());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.MOBNUM);
			detail.setValue(reqResp.getPayerMobileNo());
			details.add(detail);
		} else if (ConstantI.AADHAAR.equalsIgnoreCase(reqResp.getPayerAddrType())) {
			Detail detail = new Detail();
			detail.setName(AccountDetailType.IIN);
			detail.setValue(reqResp.getPayerIin());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.UIDNUM);
			detail.setValue(reqResp.getPayerUidNum());
			details.add(detail);
		} else if (ConstantI.CARD.equalsIgnoreCase(reqResp.getPayerAddrType())) {
			Detail detail = new Detail();
			detail.setName(AccountDetailType.ACTYPE);
			detail.setValue(reqResp.getPayerAcType());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.CARDNUM);
			detail.setValue(reqResp.getPayerCardNum());
			details.add(detail);
		}*/
		return ac;
	}

	public static AccountType getPayeeAccountType(final ReqResp reqResp) {
		AccountType ac = new AccountType();
		ac.setAddrType(AddressType.fromValue(reqResp.getPayeeAddrType()));
		List<Detail> details = ac.getDetail();
		if (ConstantI.ACCOUNT.equalsIgnoreCase(reqResp.getPayeeAddrType())) {
			Detail detail = new Detail();
			detail.setName(AccountDetailType.IFSC);
			detail.setValue(reqResp.getPayeeIfsc().toUpperCase());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.ACTYPE);
			detail.setValue(reqResp.getPayeeAcType());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.ACNUM);
			detail.setValue(reqResp.getPayeeAcNum());
			details.add(detail);
		} else if (ConstantI.MOBILE.equalsIgnoreCase(reqResp.getPayeeAddrType())) {
			Detail detail = new Detail();
			detail.setName(AccountDetailType.MMID);
			detail.setValue(reqResp.getPayeeMmid());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.MOBNUM);
			detail.setValue(reqResp.getPayeeMobileNo());
			details.add(detail);
		} else if (ConstantI.AADHAAR.equalsIgnoreCase(reqResp.getPayeeAddrType())) {
			Detail detail = new Detail();
			detail.setName(AccountDetailType.IIN);
			detail.setValue(reqResp.getPayeeIin());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.UIDNUM);
			detail.setValue(reqResp.getPayeeUidNum());
			details.add(detail);
		} else if (ConstantI.CARD.equalsIgnoreCase(reqResp.getPayeeAddrType())) {
			Detail detail = new Detail();
			detail.setName(AccountDetailType.ACTYPE);
			detail.setValue(reqResp.getPayeeAcType());
			details.add(detail);
			detail = new Detail();
			detail.setName(AccountDetailType.CARDNUM);
			detail.setValue(reqResp.getPayeeCardNum());
			details.add(detail);
		}
		return ac;
	}
	
	
	
	public static DeviceType getPayerDeviceTypeHddMandate() {
		DeviceType device = new DeviceType();
		List<Tag> tags = device.getTag();
		{
			{
				Tag tag = new Tag();
				tag.setName(DeviceTagNameType.MOBILE);
				tag.setValue(Util.getProperty("DEVICE_MOB"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.GEOCODE);
				tag.setValue(Util.getProperty("GEOC_ODE"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.LOCATION);
				tag.setValue(Util.getProperty("DEVICE_LOCATION"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.IP);
				tag.setValue(Util.getProperty("DEVICE_IP"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.TYPE);
				tag.setValue(Util.getProperty("DEVICE_TYPE"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.ID);
				tag.setValue(Util.getProperty("DEVICE_ID"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.OS);
				tag.setValue(Util.getProperty("DEVICE_OS"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.APP);
				tag.setValue(Util.getProperty("DEVICE_APP"));
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.CAPABILITY);
				tag.setValue(Util.getProperty("DEVICE_CAPABILITY"));
				tags.add(tag);
				tag = new Tag();
			}
		}
		return device;
	}
}
