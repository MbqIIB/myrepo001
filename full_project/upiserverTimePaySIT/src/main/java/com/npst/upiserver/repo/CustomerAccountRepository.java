package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.npst.upiserver.dto.CustomerAccountDto;
import com.npst.upiserver.dto.ValAddrCustomerDto;
import com.npst.upiserver.entity.CustomerAccount;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {
	
	List<CustomerAccount> findByAccvirtualid(String accvirtualid);
	
	List<CustomerAccount> findByAccvirtualidAndStatus(String accvirtualid,Integer status);

	List<CustomerAccount> findByAccvirtualidAndStatusAndDefaccount(String accvirtualid,Integer status,Integer defaccount);
	
	@Query(value="select regid from customeraccount where accvirtualid =:accvirtualid ",nativeQuery=true)
	Long getRegId(@Param("accvirtualid")final String accvirtualid);
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT regid FROM customeraccount where ACCVIRTUALID =?1 and STATUS=?2")
	List<Object> getRegIdByAccvirtualidAndStatus(String vpa, int status);
	
	@Query("select new com.npst.upiserver.dto.CustomerAccountDto(ca.accrefnumber,ca.accvirtualid,ca.regid,ca.status,ca.defaccount) from CustomerAccount ca where ca.accvirtualid=?1")
	List<CustomerAccountDto> getCustForReqAuth(String vpa);
	
	@Query("select new com.npst.upiserver.dto.ValAddrCustomerDto(ca.regid, ca.status, ca.name, ca.acctype,ca.custtype,ca.ifsc,ca.custcode) from CustomerAccount ca where ca.accvirtualid=?1 AND ca.status=?2 AND ca.defaccount=?3")
	List<ValAddrCustomerDto> getCustForValAddr(String vpa, int status, int defAccount);

	@Query("select new com.npst.upiserver.dto.CustomerAccountDto(ca.accrefnumber,ca.accvirtualid,ca.regid,ca.name) from CustomerAccount ca where ca.accvirtualid=?1 AND ca.status=?2 AND ca.defaccount=?3")
	List<CustomerAccountDto> getForMobValAdd(String vpa, int status, int defaultAcc);

	@Query(nativeQuery = true, value = "SELECT * FROM customeraccount where accvirtualid =?1 and STATUS=?2")
	List<CustomerAccount> getAccDetailsByAccvirtualidAndStatus(String vpa, int status);
}
