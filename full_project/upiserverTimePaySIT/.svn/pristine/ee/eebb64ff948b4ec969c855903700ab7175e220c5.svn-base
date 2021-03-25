package com.npst.upiserver.dao.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npst.upiserver.dao.ReqRespHbtDao;
import com.npst.upiserver.entity.ReqRespHbtEntity;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqHbt;
import com.npst.upiserver.npcischema.RespHbt;
import com.npst.upiserver.repo.ReqRespHbtRepository;

@Component
public class ReqRespHbtDaoImpl implements ReqRespHbtDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespHbtDaoImpl.class);
	@Autowired
	private ReqRespHbtRepository reqRespHbtRepo;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void insertReq(final ReqHbt reqHbt, Ack ack) {
		try {
			ReqRespHbtEntity reqresphbt = new ReqRespHbtEntity();
			reqresphbt.setReqHeadMsgId(reqHbt.getHead().getMsgId());
			reqresphbt.setReqHeadOrgId(reqHbt.getHead().getOrgId());
			reqresphbt.setReqHeadTs(reqHbt.getHead().getTs());
			reqresphbt.setTxnId(reqHbt.getTxn().getId());
			reqresphbt.setTxnIdPrf(reqHbt.getTxn().getId().substring(0, 3));
			reqresphbt.setTxnNote(reqHbt.getTxn().getNote());
			reqresphbt.setTxnRefid(reqHbt.getTxn().getRefId());
			reqresphbt.setTxnRefurl(reqHbt.getTxn().getRefUrl());
			reqresphbt.setTxnTs(reqHbt.getTxn().getTs());
			reqresphbt.setTxnType(reqHbt.getTxn().getType().value());
			reqresphbt.setHbtMsgType(reqHbt.getHbtMsg().getType().toString());
			reqresphbt.setHbtMsgValue(reqHbt.getHbtMsg().getValue());
			reqresphbt.setReqInsert(new Date());
			try {
				if (ack != null) {
					reqresphbt.setAckReq(objectMapper.writeValueAsString(ack));
				}
			} catch (Exception e) {
				log.error("error while parsing ack as string {}", e);
			}
			reqRespHbtRepo.save(reqresphbt);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error in insertReqHbt in db {}", e);
		}
	}

	@Override
	public void updateResp(final RespHbt respHbt) {
		try {
			ReqRespHbtEntity reqresphbt = reqRespHbtRepo.findTopByTxnIdAndRespInsertIsNull(respHbt.getTxn().getId());
			if (reqresphbt == null) {
				log.warn("Original ReqHBT not found in db for TxnId={}", respHbt.getTxn().getId());
				reqresphbt = new ReqRespHbtEntity();
			}
			reqresphbt.setRespHeadTs(respHbt.getHead().getTs());
			reqresphbt.setRespHeadOrgId(respHbt.getHead().getOrgId());
			reqresphbt.setRespHeadMsgId(respHbt.getHead().getMsgId());
			reqresphbt.setRespInsert(new Date());
			reqresphbt.setRespResult(respHbt.getResp().getResult());
			reqresphbt.setRespErrCode(respHbt.getResp().getErrCode());
			reqRespHbtRepo.save(reqresphbt);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error in updatating updateRespHbt {}", e);
		}
	}

}
