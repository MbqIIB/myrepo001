package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.ListAccount;
import com.npst.upiserver.entity.Payees;

public interface PayeesRepository extends JpaRepository<Payees,Long>{
	
	List<Payees> findByTxnIdAndHeadMsgId(String txnId,String headMsgId);
	
}
