package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.npst.upiserver.entity.MandatesHistoryEntity;

public interface MandatesHistoryRepo extends JpaRepository<MandatesHistoryEntity, Long> {
	List<MandatesHistoryEntity> findByTxnId(String txnId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM MANDATES_HISTORY where txnId =?1 and txnType=?2" )
	List<MandatesHistoryEntity> findByMandateTxnIdAndTxnType(String txnId, String type);
	
	List<MandatesHistoryEntity> findByMandateUmn(String umn);
	List<MandatesHistoryEntity> findByTxnType(String type);
}
