package com.npst.upiserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.ReqRespListAccPvdEntity;

public interface ReqRespListAccPvdRepository extends JpaRepository<ReqRespListAccPvdEntity, Long> {
	ReqRespListAccPvdEntity findByTxnid(String txnId);
	ReqRespListAccPvdEntity findTopByTxnidAndResinsertdateIsNull(String txnId);
}
