package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.ReqRespAuthMandateEntity;

public interface ReqRespAuthMandateRepo extends JpaRepository<ReqRespAuthMandateEntity, Long> {

	List<ReqRespAuthMandateEntity> findByTxnId(String txnId);
}
