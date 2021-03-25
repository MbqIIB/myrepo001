package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.npst.upiserver.entity.CustomerMandatesEntity;

public interface CustomerMandatesRepo extends JpaRepository<CustomerMandatesEntity, Long> {

	List<CustomerMandatesEntity> findByMandateUmnOrderByIdDesc(String umn);
	
	@Query("select cm from CustomerMandatesEntity cm where cm.payeeVpa like %?1 AND (cm.mandateValidityStart >= ?2 AND cm.mandateValidityEnd <= ?2) AND cm.status=?3")
	List<CustomerMandatesEntity> getByPspHandleAndDateAndStatus(String pspHandle, String  ddMMyyyy,int status);

	List<CustomerMandatesEntity> findByMandateUmn(String mandateUmn);

	@Query("select cm from CustomerMandatesEntity cm where cm.mandateUmn = ?1 AND cm.custis=?2")
	List<CustomerMandatesEntity> findByMandateUmnandpayeevpa(String umn, String vpa);
	
	@Query("select cm from CustomerMandatesEntity cm where cm.mandateUmn = ?1 AND cm.custis=?2")
	List<CustomerMandatesEntity> findByMandateUmnandpayervpa(String umn, String vpa);
	
	@Query("select cm from CustomerMandatesEntity cm where cm.mandateUmn = ?1 AND cm.mandateSignValue is not null")
	List<CustomerMandatesEntity> findByMandateUmnAndCustIs(String umn, String string);
	
	@Query("select cm from CustomerMandatesEntity cm where cm.mandateUmn = ?1 AND cm.mandateSignValue is null")
	List<CustomerMandatesEntity> findByMandateUmnAndCustIsSignValue(String umn, String string);
	
	List<CustomerMandatesEntity> findByMandateUmnAndTxnTypeIn(String umn, List<String> type);
}
