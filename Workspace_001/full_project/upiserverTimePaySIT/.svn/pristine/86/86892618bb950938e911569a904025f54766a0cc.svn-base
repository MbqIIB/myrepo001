package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespSetCreDao;
import com.npst.upiserver.entity.ReqRespSetCre;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.ReqSetCre;
import com.npst.upiserver.npcischema.RespSetCre;
import com.npst.upiserver.repo.ReqRespSetCreRepository;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespSetCreDaoImpl implements ReqRespSetCreDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespSetCreDaoImpl.class);

	@Autowired
	ReqRespSetCreRepository reqRespSetCre;

	@Override
	public void insertReqResp(ReqSetCre reqSetCre, RespSetCre respSetCre, Ack ack, Date reqDate) {
		try {
			ReqRespSetCre reqrespsetcre = new ReqRespSetCre();
			reqrespsetcre.setReqHeadMsgId(reqSetCre.getHead().getMsgId());
			reqrespsetcre.setReqHeadOrgId(reqSetCre.getHead().getOrgId());
			reqrespsetcre.setReqHeadTs(reqSetCre.getHead().getTs());

			reqrespsetcre.setRespHeadMsgId(respSetCre.getHead().getMsgId());
			reqrespsetcre.setRespHeadOrgId(respSetCre.getHead().getOrgId());
			reqrespsetcre.setRespHeadTs(respSetCre.getHead().getTs());

			reqrespsetcre.setTxnId(reqSetCre.getTxn().getId());
			reqrespsetcre.setTxnIdPrf(reqSetCre.getTxn().getId().substring(0, 3));
			reqrespsetcre.setTxnNote(reqSetCre.getTxn().getNote());
			reqrespsetcre.setTxnRefid(reqSetCre.getTxn().getRefId());
			reqrespsetcre.setTxnRefurl(reqSetCre.getTxn().getRefUrl());
			reqrespsetcre.setTxnTs(reqSetCre.getTxn().getTs());
			reqrespsetcre.setTxnType(reqSetCre.getTxn().getType().value());

			reqrespsetcre.setPayerAddr(reqSetCre.getPayer().getAddr());
			reqrespsetcre.setPayerCode(reqSetCre.getPayer().getCode());
			reqrespsetcre.setPayerName(reqSetCre.getPayer().getName());
			reqrespsetcre.setPayerSeqNum(reqSetCre.getPayer().getSeqNum());
			reqrespsetcre.setPayerType(reqSetCre.getPayer().getType().value());

			reqrespsetcre.setAcAddrType(reqSetCre.getPayer().getAc().getAddrType().value());
			List<Detail> acDetails = reqSetCre.getPayer().getAc().getDetail();

			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 = ConstantI.CONST_BLANK, AcAddrTypeDetail3 = ConstantI.CONST_BLANK;
			for (int i = 0; i < acDetails.size(); i++) {
				String temp = acDetails.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL + acDetails.get(i).getValue();
				if (0 == i) {
					acAddrTypeDetail1 = temp;
				} else if (1 == i) {
					AcAddrTypeDetail2 = temp;
				} else {
					AcAddrTypeDetail3 = temp;
				}
			}
			reqrespsetcre.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqrespsetcre.setAcAddrTypeDetail2(AcAddrTypeDetail2);
			reqrespsetcre.setAcAddrTypeDetail3(AcAddrTypeDetail3);

			List<CredsType.Cred> creadsList = reqSetCre.getPayer().getCreds().getCred();
			String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
			for (int i = 0; i < creadsList.size(); i++) {
				creadsSubType += creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
				creadsType += creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
			}
			List<CredsType.Cred> newCreadList = reqSetCre.getPayer().getNewCred().getCred();
			for (int i = 0; i < newCreadList.size(); i++) {
				creadsSubType += newCreadList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
				creadsType += newCreadList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
			}

			reqrespsetcre.setCredsubType(creadsSubType);
			reqrespsetcre.setCredtype(creadsType);

			reqrespsetcre.setReqInsertDate(reqDate);
			reqrespsetcre.setResInsertDate(new Date());

			reqrespsetcre.setRespResult(respSetCre.getResp().getResult());
			reqrespsetcre.setRespErrCode(respSetCre.getResp().getErrCode());

			reqrespsetcre.setAckResp(JsonMan.getJSONStr(ack));
			reqRespSetCre.save(reqrespsetcre);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	
	private ReqRespSetCre getByTxnId(String txnId) {
		List<ReqRespSetCre> list = reqRespSetCre.findByTxnId(txnId);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 0) {
			log.warn("ReqRespSetCreEntity not found for TxnId={}", txnId);
		} else {
			log.warn("More than one ReqRespSetCreEntity found for TxnId={}", txnId);
		}
		return null;
	}
	@Override
	public void updateReqResp(RespSetCre respSetCre) {
		try {
			ReqRespSetCre reqrespsetcre = getByTxnId(respSetCre.getTxn().getId());
			if (reqrespsetcre == null) {
				return;
			}
				reqrespsetcre.setRespHeadMsgId(respSetCre.getHead().getMsgId());
				reqrespsetcre.setRespHeadOrgId(respSetCre.getHead().getOrgId());
				reqrespsetcre.setRespHeadTs(respSetCre.getHead().getTs());
				reqrespsetcre.setResInsertDate(new Date());
				reqrespsetcre.setRespResult(respSetCre.getResp().getResult());
				reqrespsetcre.setRespErrCode(respSetCre.getResp().getErrCode());
				reqRespSetCre.save(reqrespsetcre);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void insertReq(ReqSetCre reqSetCre, Ack ack) {
		try {
			ReqRespSetCre reqrespsetcre = new ReqRespSetCre();
			try {
				reqrespsetcre.setReqHeadMsgId(reqSetCre.getHead().getMsgId());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setReqHeadOrgId(reqSetCre.getHead().getOrgId());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setReqHeadTs(reqSetCre.getHead().getTs());
			} catch (NullPointerException npe) {}
			try {
				
				reqrespsetcre.setTxnId(reqSetCre.getTxn().getId());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setTxnIdPrf(reqSetCre.getTxn().getId().substring(0, 3));
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setTxnNote(reqSetCre.getTxn().getNote());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setTxnRefid(reqSetCre.getTxn().getRefId());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setTxnRefurl(reqSetCre.getTxn().getRefUrl());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setTxnTs(reqSetCre.getTxn().getTs());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setTxnType(reqSetCre.getTxn().getType().value());

			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setPayerAddr(reqSetCre.getPayer().getAddr());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setPayerCode(reqSetCre.getPayer().getCode());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setPayerName(reqSetCre.getPayer().getName());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setPayerSeqNum(reqSetCre.getPayer().getSeqNum());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setPayerType(reqSetCre.getPayer().getType().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespsetcre.setPayerHandal(
						reqSetCre.getPayer().getAddr().substring(reqSetCre.getPayer().getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));
			} catch (Exception e) {}
			reqrespsetcre.setAcAddrType(reqSetCre.getPayer().getAc().getAddrType().value());
			List<Tag> deviceTag = reqSetCre.getPayer().getDevice().getTag();
			if (0 < deviceTag.size()) {
				for (int i = 0; i < deviceTag.size(); i++) {
					if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceApp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceCapability(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceGeocode(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceId(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceIp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceLocation(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceMobile(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceOs(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespsetcre.setDeviceType(deviceTag.get(i).getValue());
					}
				}
			}
			List<Detail> acDetails = reqSetCre.getPayer().getAc().getDetail();
			if (0 < acDetails.size()) {
				String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 = ConstantI.CONST_BLANK, AcAddrTypeDetail3 = ConstantI.CONST_BLANK;
				if (0 < acDetails.size()) {
					for (int i = 0; i < acDetails.size(); i++) {
						String temp = acDetails.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL + acDetails.get(i).getValue();
						if (0 == i) {
							acAddrTypeDetail1 = temp;
						} else if (1 == i) {
							AcAddrTypeDetail2 = temp;
						} else {
							AcAddrTypeDetail3 = temp;
						}
					}
				}

				reqrespsetcre.setAcAddrTypeDetail1(acAddrTypeDetail1);
				reqrespsetcre.setAcAddrTypeDetail2(AcAddrTypeDetail2);
				reqrespsetcre.setAcAddrTypeDetail3(AcAddrTypeDetail3);
			}
			List<CredsType.Cred> creadsList = reqSetCre.getPayer().getCreds().getCred();
			if (0 < acDetails.size()) {
				String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
				for (int i = 0; i < creadsList.size(); i++) {
					creadsSubType += creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
					creadsType += creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
				}
				List<CredsType.Cred> newCreadList = reqSetCre.getPayer().getNewCred().getCred();
				for (int i = 0; i < newCreadList.size(); i++) {
					creadsSubType += newCreadList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
					creadsType += newCreadList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
				}
				
				reqrespsetcre.setCredsubType(creadsSubType);
				reqrespsetcre.setCredtype(creadsType);
			}
			reqrespsetcre.setReqInsertDate(new Date());
			if(ack!=null) {
				reqrespsetcre.setAckReq(JsonMan.getJSONStr(ack));
			}
			reqRespSetCre.save(reqrespsetcre);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
}
