package com.npst.upiserver.dao;

import java.util.List;

import com.npst.upiserver.dto.CustomerAccountDto;
import com.npst.upiserver.dto.ValAddrCustomerDto;
import com.npst.upiserver.entity.CustomerAccount;

public interface CustomerAccountDao {
	List<CustomerAccount> findAccounts(String virtualId);
	List<CustomerAccount> findActiveAccounts(String virtualId,String flag);
	Long getRegIdByVPA(String virtualId);
	public long getRegIdOfActiveAccByVpa(String vpa);
	boolean isBharatQR(String payeeAddr);
	ValAddrCustomerDto getValAddrCustomerDto(String vpa);
	List<CustomerAccountDto> getCustAccDtoForMobValAdd(String payerAddr);
	
	CustomerAccount getAccDetailsByAccvirtualidAndStatus(String vpa);
}
