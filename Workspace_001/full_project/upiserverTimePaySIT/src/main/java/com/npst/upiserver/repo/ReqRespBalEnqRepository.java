package com.npst.upiserver.repo;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespBal;

@Repository
public interface ReqRespBalEnqRepository extends JpaRepository<ReqRespBal, Long> {
	//ReqRespBal findByTxnId(final String txnId);
	
	List<ReqRespBal> findByTxnId(String txnId);
}
