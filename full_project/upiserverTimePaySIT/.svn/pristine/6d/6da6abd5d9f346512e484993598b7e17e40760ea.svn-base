package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.npst.upiserver.entity.Regvpa;

@Repository
public interface RegVpaRepository extends JpaRepository<Regvpa,Long> {
	List<Regvpa> findByVpa(final String vpa);
	List<Regvpa> findByRegvpaidAndPstatus(final Long regvpaid,final Integer pstatus);
	
	String findGcmtokenByRegvpaid(@Param("regvpaid")final Long regvpaid);
}
