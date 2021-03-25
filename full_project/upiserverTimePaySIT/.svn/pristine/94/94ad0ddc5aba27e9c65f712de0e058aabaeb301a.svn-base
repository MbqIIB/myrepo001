package com.npst.upiserver.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespSetCre;


@Repository
public interface ReqRespSetCreRepository extends JpaRepository<ReqRespSetCre,Long>{

	//ReqRespSetCre findByTxnId(String txnId);
	
	List<ReqRespSetCre> findByTxnId(String txnId);
}
