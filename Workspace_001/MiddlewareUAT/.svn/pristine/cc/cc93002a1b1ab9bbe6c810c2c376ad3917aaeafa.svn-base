package com.npst.middleware.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.npst.middleware.entity.UpiPin;

@Repository
public interface UpiPinRepository extends CrudRepository<UpiPin, Long> {
	List<UpiPin> findByMobileNo(final String mobileNo);
	
	List<UpiPin> findByMobileNoAndAccNo(final String mobileNo, final String accNo);
	
	
	@Query("select up from UpiPin up where accNo =:accNo and status!=5 ")
	List<UpiPin> findByAccNoAndStatus(@Param("accNo")final String accNo);
	
	@Modifying
	@Query("update UpiPin u set u.failAttempt = ?1 where u.mobileNo = ?2 and u.accNo =?3")
	int setFailAttemptCount(final int i, final String mobileNo, final String accNo);
	
	
	List<UpiPin>  findByMobileNoAndAccNoAndStatus(final String mobileNo,final String accNo,final Integer status);
}
