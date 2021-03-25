package com.npst.upiserver.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespListKeys;

@Repository
public interface ReqRespListKeysRepository extends JpaRepository<ReqRespListKeys, Long> {
	ReqRespListKeys findByTxnId(String txnId);
	
	List<ReqRespListKeys> findByTxnIdOrderByReqInsertDesc(String txnId);
}
