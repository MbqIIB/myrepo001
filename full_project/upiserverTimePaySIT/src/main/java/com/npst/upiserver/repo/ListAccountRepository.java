package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.npst.upiserver.entity.ListAccount;
import org.springframework.stereotype.Repository;


@Repository
public interface ListAccountRepository extends JpaRepository<ListAccount, Long>{
	List<ListAccount> findByVpa(final String vpa);
	List<ListAccount> findByVpaAndPstatus(final String vpa,final String pstatus);
	List<ListAccount> findByVpaAndPstatusAndDefaultAcc(final String vpa,final String pstatus,final String defaultAcc);
	
}
