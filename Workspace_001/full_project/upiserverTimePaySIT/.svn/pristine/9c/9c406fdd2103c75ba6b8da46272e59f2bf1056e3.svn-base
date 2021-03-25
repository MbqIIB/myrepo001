package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.dao.ReqRespValAddDao;
import com.npst.upiserver.entity.ReqRespValAdd;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqValAdd;
import com.npst.upiserver.npcischema.RespValAdd;
import com.npst.upiserver.repo.ReqRespValAddRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.NpciSchemaUtil;

@Component
public class ReqRespValAddDaoImpl implements ReqRespValAddDao {
	private static final Logger	log	= LoggerFactory.getLogger(ReqRespValAddDaoImpl.class);
	
	@Autowired
	ReqRespValAddRepository reqRespValAddRepo;
	
	@Override
	public void insertReqResp(ReqValAdd reqValAdd, RespValAdd respValAdd, Ack ack) {
		try {
			ReqRespValAdd reqrespvaladd = new ReqRespValAdd();
			try {
				reqrespvaladd.setReqHeadMsgId(reqValAdd.getHead().getMsgId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setReqHeadOrgId(reqValAdd.getHead().getOrgId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setReqHeadTs(reqValAdd.getHead().getTs());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnId(reqValAdd.getTxn().getId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnIdPrf(reqValAdd.getTxn().getId().substring(0, 3));
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnNote(reqValAdd.getTxn().getNote());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnRefid(reqValAdd.getTxn().getRefId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnRefurl(reqValAdd.getTxn().getRefUrl());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnTs(reqValAdd.getTxn().getTs());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnType(reqValAdd.getTxn().getType().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerAddr(reqValAdd.getPayer().getAddr());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerHandal(
						reqValAdd.getPayer().getAddr().substring(reqValAdd.getPayer().getAddr().indexOf("@")));
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerCode(reqValAdd.getPayer().getCode());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerName(reqValAdd.getPayer().getName());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerSeqNum(reqValAdd.getPayer().getSeqNum());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerType(reqValAdd.getPayer().getType().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setInfoId(reqValAdd.getPayer().getInfo().getIdentity().getId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd
						.setInfoIdRatingvaddr(reqValAdd.getPayer().getInfo().getRating().getVerifiedAddress().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setInfoIdType(reqValAdd.getPayer().getInfo().getIdentity().getType().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setInfoIdVerifiedName(reqValAdd.getPayer().getInfo().getIdentity().getVerifiedName());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setReqInsert(new Date());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setRespErrCode(respValAdd.getResp().getErrCode());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setRespResult(respValAdd.getResp().getResult());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setMaskName(respValAdd.getResp().getMaskName());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setRespInsert(new Date());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setRespHeadMsgId(respValAdd.getHead().getMsgId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setRespHeadOrgId(respValAdd.getHead().getOrgId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setRespHeadTs(respValAdd.getHead().getTs());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setAckResp(JsonMan.getJSONStr(ack));
			} catch (NullPointerException npe) {}
			
			reqRespValAddRepo.save(reqrespvaladd);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	private ReqRespValAdd getByTxnId(String txnId) {
		try {
			List<ReqRespValAdd> list = reqRespValAddRepo.findByTxnId(txnId);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("ReqRespValAddEntity not found for txnId={}", txnId);
			} else {
				log.warn("More than one ReqRespValAddEntity found for txnId={}", txnId);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespValAddDaoImpl.class);
		}

		return null;
	}

	@Override
	public void updateResp(RespValAdd respValAdd) {
		try {
			ReqRespValAdd reqrespvaladd = getByTxnId(respValAdd.getTxn().getId());
			if (null == reqrespvaladd) {
				reqrespvaladd = new ReqRespValAdd();
			}
			reqrespvaladd.setTxnId(respValAdd.getTxn().getId());
			reqrespvaladd.setTxnIdPrf(NpciSchemaUtil.getTxnIdPrefix(respValAdd.getTxn().getId()));
			reqrespvaladd.setTxnNote(respValAdd.getTxn().getNote());
			reqrespvaladd.setTxnRefid(respValAdd.getTxn().getRefId());
			reqrespvaladd.setTxnRefurl(respValAdd.getTxn().getRefUrl());
			reqrespvaladd.setTxnTs(respValAdd.getTxn().getTs());
			reqrespvaladd.setTxnType(respValAdd.getTxn().getType().value());
			reqrespvaladd.setRespHeadTs(respValAdd.getHead().getTs());
			reqrespvaladd.setRespHeadOrgId(respValAdd.getHead().getOrgId());
			reqrespvaladd.setRespHeadMsgId(respValAdd.getHead().getMsgId());
			reqrespvaladd.setRespInsert(new Date());
			reqrespvaladd.setRespResult(respValAdd.getResp().getResult());
			reqrespvaladd.setRespErrCode(respValAdd.getResp().getErrCode());
			reqrespvaladd.setMaskName(respValAdd.getResp().getMaskName());
			reqRespValAddRepo.save(reqrespvaladd);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void insertReq(ReqValAdd reqValAdd, Ack ack) {
		try {
			ReqRespValAdd reqrespvaladd = new ReqRespValAdd();
			try {
				reqrespvaladd.setReqHeadMsgId(reqValAdd.getHead().getMsgId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setReqHeadOrgId(reqValAdd.getHead().getOrgId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setReqHeadTs(reqValAdd.getHead().getTs());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnId(reqValAdd.getTxn().getId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnIdPrf(reqValAdd.getTxn().getId().substring(0, 3));
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnNote(reqValAdd.getTxn().getNote());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnRefid(reqValAdd.getTxn().getRefId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnRefurl(reqValAdd.getTxn().getRefUrl());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnTs(reqValAdd.getTxn().getTs());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setTxnType(reqValAdd.getTxn().getType().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerAddr(reqValAdd.getPayer().getAddr());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerCode(reqValAdd.getPayer().getCode());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerName(reqValAdd.getPayer().getName());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerSeqNum(reqValAdd.getPayer().getSeqNum());
			} catch (NullPointerException npe) {}
			try {
				
				reqrespvaladd.setPayerType(reqValAdd.getPayer().getType().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setPayerHandal(
						reqValAdd.getPayer().getAddr().substring(reqValAdd.getPayer().getAddr().indexOf("@")));
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setInfoId(reqValAdd.getPayer().getInfo().getIdentity().getId());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd
						.setInfoIdRatingvaddr(reqValAdd.getPayer().getInfo().getRating().getVerifiedAddress().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setInfoIdType(reqValAdd.getPayer().getInfo().getIdentity().getType().value());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setInfoIdVerifiedName(reqValAdd.getPayer().getInfo().getIdentity().getVerifiedName());
			} catch (NullPointerException npe) {}
			try {
				reqrespvaladd.setReqInsert(new Date());
			} catch (NullPointerException npe) {}
			try {
				if(ack!=null) {
					reqrespvaladd.setAckReq(JsonMan.getJSONStr(ack));
				}
			} catch (NullPointerException npe) {}
			reqRespValAddRepo.save(reqrespvaladd);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
}
