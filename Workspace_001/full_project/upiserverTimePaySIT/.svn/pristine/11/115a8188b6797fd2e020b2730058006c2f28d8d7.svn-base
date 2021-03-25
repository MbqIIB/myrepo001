package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.ReqRespMandatesEntity;



public interface ReqRespMandatesRepo extends JpaRepository<ReqRespMandatesEntity, Long> {

	List<ReqRespMandatesEntity> findByTxnId(String txnId);
}
