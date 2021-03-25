package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.ReqRespHbtEntity;
import com.npst.upiserver.entity.ReqRespVaePspKeysEntity;

public interface ReqRespVaePspKeysRepository extends JpaRepository<ReqRespVaePspKeysEntity, Long> {
	//ReqRespHbtEntity findTopByTxnIdAndRespDateIsNull(String txnId);
	
	ReqRespVaePspKeysEntity findTopByTxnIdAndRespDateIsNull(String txnId);
	List<ReqRespVaePspKeysEntity> findByReqHeadMsgId(String reqHeadMsgId);
}
