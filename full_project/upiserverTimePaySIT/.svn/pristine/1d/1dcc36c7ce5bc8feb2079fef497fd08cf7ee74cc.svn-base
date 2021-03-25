package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.ReqRespHbtEntity;

public interface ReqRespHbtRepository extends JpaRepository<ReqRespHbtEntity, Long> {
	List<ReqRespHbtEntity> findByTxnId(String txnId);

	ReqRespHbtEntity findTopByTxnIdAndRespInsertIsNull(String txnId);

	ReqRespHbtEntity findTopByTxnIdOrderByReqInsertDesc(String txnId);

}
