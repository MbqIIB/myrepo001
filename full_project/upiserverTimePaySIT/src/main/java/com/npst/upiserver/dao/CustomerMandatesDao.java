package com.npst.upiserver.dao;

import java.util.List;
import java.util.Set;

import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.entity.MandatesHistoryEntity;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.RespMandate;

public interface CustomerMandatesDao {
	public MandatesHistoryEntity getMandatesHistory(String txnId);
	void insertSuccessMandate(final RespMandate respMandate);
	CustomerMandatesEntity setCustomerMandates(MandatesHistoryEntity mandateHistory, String txnType, String umn, String txnInitiatedBy);
	void insertSuccessMandate(ReqMandateConfirmation reqMandateConfirmation);
	Set<CustomerMandatesEntity> findMandatesTxnsToProcess();
	CustomerMandatesEntity findByUmn(String umn);
	boolean isValidTransactionToProcess(CustomerMandatesEntity mandates);
	public CustomerMandatesEntity findByUmnAndCustIs(String umn);
	public CustomerMandatesEntity findByUmnAndSignValue(String UMN);
	
	public List<CustomerMandatesEntity> findByUmnAndType(String UMN);
	public CustomerMandatesEntity findByUmnAndSignValuenull(String UMN);
	
}