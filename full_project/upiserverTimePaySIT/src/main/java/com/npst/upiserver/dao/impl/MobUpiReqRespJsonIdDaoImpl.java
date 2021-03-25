package com.npst.upiserver.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.entity.Mobupireqrespjsonid;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.MobUpiReqRespJsonIdRepository;
import com.npst.upiserver.repo.MobUpiReqRespJsonRepository;
import com.npst.upiserver.util.JsonMan;

@Component
public class MobUpiReqRespJsonIdDaoImpl implements MobUpiReqRespJsonIdDao {
	private static final Logger log = LoggerFactory.getLogger(MobUpiReqRespJsonIdDaoImpl.class);
	
	@Autowired
	MobUpiReqRespJsonRepository mobUpiReqRespJsonRepo;
	@Autowired
	MobUpiReqRespJsonIdRepository mobUpiReqRespJsonIdRepo;
	

	
	@Override
	public void updateFail(MobUpiReqRespJson mobreqrespjson) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setMsg(ResultType.FAILURE.toString());
			respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			mobreqrespjson.setResp(JsonMan.getJSONStr(respJson));
			mobreqrespjson.setRespDate(new java.util.Date());
			mobreqrespjson.setFlag(3);
			mobUpiReqRespJsonRepo.save(mobreqrespjson);
			Mobupireqrespjsonid mobJsonId=mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(mobreqrespjson.getIdPk());
			if (null != mobJsonId) {
				mobJsonId.setFlag(mobreqrespjson.getFlag());
				mobUpiReqRespJsonIdRepo.save(mobJsonId);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateDb(Ack ack, MobUpiReqRespJson mobreqrespjson, String txnType) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setTxnType(txnType);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				respJson.setUpiErrorCode(JsonMan.getJSONStr(ack));
				respJson.setMsg(Constant.ACK_ERROR);
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			} else {
				respJson.setMsg(ResultType.SUCCESS.toString());
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
			}
			mobreqrespjson.setResp(JsonMan.getJSONStr(respJson));
			mobreqrespjson.setRespDate(new java.util.Date());
			mobreqrespjson.setFlag(3);
			mobUpiReqRespJsonRepo.save(mobreqrespjson);
			Mobupireqrespjsonid logmobrecid=mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(mobreqrespjson.getIdPk());
			if(logmobrecid!=null) {
				logmobrecid.setFlag(mobreqrespjson.getFlag());
				mobUpiReqRespJsonIdRepo.save(logmobrecid);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateDb(MobUpiReqRespJson mobreqrespjson) {
		try {
			ReqResp respJson = new ReqResp();
			mobreqrespjson.setIdPk(mobreqrespjson.getIdPk());
			mobreqrespjson.setResp(JsonMan.getJSONStr(respJson));
			mobreqrespjson.setFlag(2);
			mobUpiReqRespJsonRepo.save(mobreqrespjson);
			Mobupireqrespjsonid logmobrecid=mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(mobreqrespjson.getIdPk());
			if(logmobrecid!=null) {
				logmobrecid.setFlag(mobreqrespjson.getFlag());
				mobUpiReqRespJsonIdRepo.save(logmobrecid);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateDb(MobUpiReqRespJson mobreqrespjson, ReqResp respJson) {
		try {
			mobreqrespjson.setIdPk(mobreqrespjson.getIdPk());
			mobreqrespjson.setResp(JsonMan.getJSONStr(respJson));
			mobreqrespjson.setFlag(2);
			mobUpiReqRespJsonRepo.save(mobreqrespjson);
			Mobupireqrespjsonid logmobrecid=mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(mobreqrespjson.getIdPk());
			if(logmobrecid!=null) {
				logmobrecid.setFlag(mobreqrespjson.getFlag());
				mobUpiReqRespJsonIdRepo.save(logmobrecid);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateDb(ReqPay reqpay, Ack ack, MobUpiReqRespJson mobreqrespjson) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setTxnType(reqpay.getTxn().getType().toString());
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				respJson.setUpiErrorCode(JsonMan.getJSONStr(ack));
				respJson.setMsg(Constant.ACK_ERROR);
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			} else {
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
				respJson.setMsg(ResultType.SUCCESS.toString());
			}
			
			respJson.setRrn(reqpay.getTxn().getCustRef());
			mobreqrespjson.setResp(JsonMan.getJSONStr(respJson));
			mobreqrespjson.setRespDate(new java.util.Date());
			mobreqrespjson.setFlag(3);
			mobUpiReqRespJsonRepo.save(mobreqrespjson);
			Mobupireqrespjsonid logmobrecid=mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(mobreqrespjson.getIdPk());
			if(mobreqrespjson!=null) {
				logmobrecid.setFlag(mobreqrespjson.getFlag());
				mobUpiReqRespJsonIdRepo.save(logmobrecid);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public Long getMobReqRespJsonId(String msgId) {
		Long mobReqRespJsonIdpK=0l;
		try {
			log.info("msgId is as {} ",msgId);
			List<Mobupireqrespjsonid> mobUpiJsonId=mobUpiReqRespJsonIdRepo.findByMsgid(msgId.trim());
			log.info("found data is {}",mobUpiJsonId);
			if(mobUpiJsonId!=null) {
				if (mobUpiJsonId.size() != 0) {
					mobReqRespJsonIdpK= mobUpiJsonId.get(0).getIdmobreqrespjsonid();
				}else {
					log.warn("MobUpiReqRespJsonIdEntity not found for reqMsgId={}", msgId);
				}
				//mobReqRespJsonIdpK= (long)mobUpiJsonId.get(0).getIdmobreqrespjsonid();
			}
			else {
				log.info("MobUpiReqRespJsonIdEntity null found for reqMsgId={}", msgId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			log.info("error in get data{}",e.getMessage());
		}
		return mobReqRespJsonIdpK;
	}
}
