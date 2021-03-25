package com.npst.upiserver.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespOtp;

@Repository
public interface ReqRespOtpRepository extends JpaRepository<ReqRespOtp,Long>{
	//ReqRespOtp findByTxnId(String txnId);
	
	List<ReqRespOtp> findByTxnId(String txnId);
}
