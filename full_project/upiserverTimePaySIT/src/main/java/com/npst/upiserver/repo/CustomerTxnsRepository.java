package com.npst.upiserver.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.npst.upiserver.entity.CustomerTxns;

@Repository
public interface CustomerTxnsRepository extends JpaRepository<CustomerTxns,Long> {
	
	CustomerTxns findByTxnIdAndTxnType(String txnId, Integer txnType);

	List<CustomerTxns> findByTxnIdAndTxnType(String txnId, int txnType);
	
	@Query("SELECT count(txn.CustomerTxnsId) FROM CustomerTxns txn WHERE txn.mandateUmn=?1 AND txn.payerVpa=?2 AND txn.reqDate=?4 AND txn.status=?3")
	long countByMandateUmnAndPayerVpa(String mandateUmn, String vpa, int status, Date date);

}
