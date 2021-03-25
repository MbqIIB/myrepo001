package com.npst.upiserver.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespRegMob;

@Repository
public interface ReqRespRegMobRepository extends JpaRepository<ReqRespRegMob, Long>  {
	//ReqRespRegMob findByTxnId(String txnId);
	
	List<ReqRespRegMob> findByTxnId(String txnId);
}
