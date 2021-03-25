package com.npst.upiserver.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.npst.upiserver.entity.SpamVpa;

public interface SpamVpaRepository extends JpaRepository<SpamVpa, Long>{
	
	@Query(value="SELECT * FROM spamvpa where spamvirtualid=:spamvirtualid and addeddate = CURDATE() ", nativeQuery=true)
	List<SpamVpa> findBySpamvirtualid(@Param("spamvirtualid")final String spamVirtualId);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(1) FROM SPAM_VPA where SPAMVIRTUALID =?1 AND to_date(?2)=to_date(sysdate,'DD-MM-YY')")
	long getSpamCount(String spamVirtualId, Date date);
}
