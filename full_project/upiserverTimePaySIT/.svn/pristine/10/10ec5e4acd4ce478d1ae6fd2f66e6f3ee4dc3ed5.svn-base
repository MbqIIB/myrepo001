package com.npst.upiserver.dao;

import java.util.List;

import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.Registration;

public interface RegistrationDao {
	boolean isActiveUser(Long regid);
	Registration getRegistrationDetails(Long regId); 
	Registration findActiveRegByMobno(String mobileNo);
	boolean updateRegistration(Registration registration);
	boolean delete(ReqResp reqJson);
	boolean isActiveRegistration(long regId);
	long getActiveRegIdByDefVpa(String vpa);

	long getRegIdByDefVpa(String vpa);

	RegDto getGCMToken(long regid);

	void delete(String regId);

	void delete(long regId);
	boolean isActiveRegistration(List<CustomerAccount> listaccounts);
}
