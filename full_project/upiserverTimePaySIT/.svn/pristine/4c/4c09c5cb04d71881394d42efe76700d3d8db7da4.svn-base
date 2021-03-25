package com.npst.upiserver.repo;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.SpamVpaRule;

@Repository
public interface SpamVpaRuleRepository extends JpaRepository<SpamVpaRule, Long>{
	
	@Query(value="SELECT r FROM spamvparule r where r.spamvpa = :payeeAddr and r.ruleexpireddate > NOW() order by r.ruleexpireddate desc",nativeQuery=true)
	List<SpamVpaRule> findBySpamvpa(@Param("payeeAddr")final String payeeAddr);
	
	@Query(nativeQuery=true ,value="SELECT rule FROM spamvparule where SPAMVPA =?1 and RULEEXPIREDDATE > ?2 order by RULEEXPIREDDATE DESC")
	List<String> getSpamvpaRule(String vpa, Date currentDate);
	
}
