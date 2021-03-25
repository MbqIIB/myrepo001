package com.npst.middleware.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.npst.middleware.entity.ErrorCode;

@Repository
public interface ErrorCodeRepository extends CrudRepository<ErrorCode, Long> {
	List<ErrorCode> findBycbsErrorCode(final String mbCode);
	
	List<ErrorCode> findByCmsErrorCode(final String mbCode);
	
	List<ErrorCode> findByMbErrorCode(final String mbCode);
	
	List<ErrorCode> findBypspErrorCode(final String mbCode);
}
