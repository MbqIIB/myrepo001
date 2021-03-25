package com.npst.upiserver.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespPayCollect;


@Repository
public interface ReqRespPayCollectRepository extends JpaRepository<ReqRespPayCollect, Long>  {

	//ReqRespPayCollect findByTxnId(final String txnId);
	
	List<ReqRespPayCollect> findByTxnId(String txnId);
}
