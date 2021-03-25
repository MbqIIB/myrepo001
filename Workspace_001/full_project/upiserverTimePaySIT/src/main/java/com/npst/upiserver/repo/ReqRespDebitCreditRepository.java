package com.npst.upiserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespDebitCredit;

@Repository
public interface ReqRespDebitCreditRepository extends JpaRepository<ReqRespDebitCredit, Long> {
	
	ReqRespDebitCredit findByTxnIdAndReqHeadMsgId(@Param("txnId")final String txnId , @Param("reqHeadMsgId")final String reqHeadMsgId);
	
	ReqRespDebitCredit findByTxnIdAndTxnType(final String txnId ,final String txnType);
	
	ReqRespDebitCredit findByTxnIdAndReqHeadMsgIdOrderByReqInsertDesc(String txnId, String reqHeadMsgId);
}
