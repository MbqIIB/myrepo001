package com.npst.upiserver.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespValAdd;

@Repository
public interface ReqRespValAddRepository extends JpaRepository<ReqRespValAdd, Long>{

	@Query(value=" select * from reqrespvaladd where txnId =:txnId and respInsert is null  ",nativeQuery=true)
	List<ReqRespValAdd> findByTxnId(@Param("txnId")final String txnId); 
	
}
