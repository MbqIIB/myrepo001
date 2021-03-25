package com.npst.middleware.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.npst.middleware.entity.OtpRecord;

@Repository
public interface OtpRepository extends CrudRepository<OtpRecord, Long> {
	
	List<OtpRecord> findByMobileNoAndIsUsed(String mobileNo, int isUsed);
}
