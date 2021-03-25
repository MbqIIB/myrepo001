package com.npst.middleware.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.npst.middleware.entity.Trans;

// Added Repository Based Annotations on Class Level, Dated: 12-06-2017 ,
// Modified By : Abhishek Bhardwaj
@Repository
public interface TransRepository extends CrudRepository<Trans, Long> {
	List<Trans> findByTxnIdOrderByReqDesc(final String txnId);
	List<Trans> findByTxnIdAndRrnAndOpration(final String txnId,String rrn,String type);
	@Query(nativeQuery = true, value = " select ifnull(sum(amount),0) as amount,ifnull(count(amount),0) as totalcount from trans where date(request_date) = date(:req) and account_number = :accNo  and status =2 and opration = 'DEBIT' and is_reversal is NULL ")
	public List<Object[]> getLimitBasedonAccNum(@Param("req") Date req,@Param("accNo") String accNo);
}
