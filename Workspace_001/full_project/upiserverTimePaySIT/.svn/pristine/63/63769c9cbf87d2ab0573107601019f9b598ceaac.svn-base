package com.npst.upiserver.dao.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.dao.ReqRespListKeysDao;
import com.npst.upiserver.entity.ReqRespListKeys;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqListKeys;
import com.npst.upiserver.npcischema.RespListKeys;
import com.npst.upiserver.repo.ReqRespListKeysRepository;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespListKeysDaoImpl implements ReqRespListKeysDao{
	private static final Logger log = LoggerFactory.getLogger(ReqRespListKeysDaoImpl.class);
	
	@Autowired
	ReqRespListKeysRepository reqRespListKeysRepo;
	
	@Override
	public void updateResp(RespListKeys respListKeys) {
		try {
			ReqRespListKeys reqRespListKeys=reqRespListKeysRepo.findByTxnId(respListKeys.getTxn().getId());
			if(reqRespListKeys!=null) {
				reqRespListKeys.setRespHeadTs(respListKeys.getHead().getTs());
				reqRespListKeys.setRespHeadOrgId(respListKeys.getHead().getOrgId());
				reqRespListKeys.setRespHeadMsgId(respListKeys.getHead().getMsgId());
				reqRespListKeys.setRespInsert(new Date());
				reqRespListKeys.setRespResult(respListKeys.getResp().getResult());
				reqRespListKeys.setRespErrCode(respListKeys.getResp().getErrCode());
				reqRespListKeys.setKeyList(JsonMan.getJSONStr(respListKeys.getKeyList()));
				reqRespListKeysRepo.save(reqRespListKeys);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void insertReq(ReqListKeys reqListKeys, Ack ack) {
		try {
			ReqRespListKeys reqresplistkeys = new ReqRespListKeys();
			reqresplistkeys.setReqHeadMsgId(reqListKeys.getHead().getMsgId());
			reqresplistkeys.setReqHeadOrgId(reqListKeys.getHead().getOrgId());
			reqresplistkeys.setReqHeadTs(reqListKeys.getHead().getTs());

			reqresplistkeys.setTxnId(reqListKeys.getTxn().getId());
			reqresplistkeys.setTxnIdPrf(reqListKeys.getTxn().getId().substring(0, 3));
			reqresplistkeys.setTxnNote(reqListKeys.getTxn().getNote());
			reqresplistkeys.setTxnRefid(reqListKeys.getTxn().getRefId());
			reqresplistkeys.setTxnRefurl(reqListKeys.getTxn().getRefUrl());
			reqresplistkeys.setTxnTs(reqListKeys.getTxn().getTs());
			reqresplistkeys.setTxnType(reqListKeys.getTxn().getType().value());
			if (null != reqListKeys.getCreds()) {
				reqresplistkeys.setCreds(JsonMan.getJSONStr(reqListKeys.getCreds()));
			}
			reqresplistkeys.setReqInsert(new Date());
			reqresplistkeys.setAckReq(JsonMan.getJSONStr(ack));
			reqRespListKeysRepo.save(reqresplistkeys);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
}
