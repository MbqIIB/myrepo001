package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.MandateTxnsEntity;

public interface MandateTxnsRepo extends JpaRepository<MandateTxnsEntity, Long> {
	List<MandateTxnsEntity> findByTxnId(String txnId);
}
