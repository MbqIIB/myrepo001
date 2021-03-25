package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.MandatesEntity;

public interface MandatesRepo extends JpaRepository<MandatesEntity, Long>{

	List<MandatesEntity> findByMandateUmn(String umn);

//	@Query("SELECT a FROM MANDATES a WHERE a.acAddrTypeDetail3=:acAddrTypeDetail3 and a.txnType=:txnType or a.txnType=:TxnType")
//	List<MandatesEntity> findByacAddrTypeDetail3AndTxnType(String acAddrTypeDetail3,String txnType);
	List<MandatesEntity> findByacAddrTypeDetail3AndTxnTypeOrTxnType(String acAddrTypeDetail3,String txnType,String TxnType);

}
