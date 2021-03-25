package com.npst.upiserver.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.RiskXH;

@Repository
public interface RiskXHRepository extends JpaRepository<RiskXH, Long> {
	@Query(value="select count from risk_xh where regid=:regid and CREATE_UPDT_DATE > :lastlogindt",nativeQuery=true )
	Long findCountByRegidAndCreateUpdDate(Long regid, Date lastlogindt);
	
	RiskXH findByRegid(Long regId); 
}
