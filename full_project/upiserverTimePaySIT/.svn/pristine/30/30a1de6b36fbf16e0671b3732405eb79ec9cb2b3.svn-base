package com.npst.upiserver.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dto.CustomerAccountDto;
import com.npst.upiserver.dto.ValAddrCustomerDto;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.repo.CustomerAccountRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.Util;


@Component
public class CustomerAccountDaoImpl implements CustomerAccountDao {

	private static final Logger	log	= LoggerFactory.getLogger(CustomerAccountDaoImpl.class);
	
	
	@Autowired
	CustomerAccountRepository customerAccount;
	
	
	@Override
	public List<CustomerAccount> findAccounts(String virtualId) {
		List<CustomerAccount> customerAcc=new ArrayList<>();
		try {
			customerAcc=customerAccount.findByAccvirtualid(virtualId);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return customerAcc;
	}

	@Override	
	public Long getRegIdByVPA(String virtualId) {
		Long regId=0l;
		try	{
			regId=customerAccount.getRegId(virtualId);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
			regId=0l;
		}
		return regId;
	}

	@Override
	public List<CustomerAccount> findActiveAccounts(String virtualId,String flag) {
		List<CustomerAccount> customerAcc=new ArrayList<>();
		try {
			if(flag!=null && ConstantI.CONST_R.equalsIgnoreCase(flag)) {
				customerAcc=customerAccount.findByAccvirtualidAndStatusAndDefaccount(virtualId,ConstantI.CONST_ACTIVE_REG,ConstantI.CONST_DEF_ACC);
			}
			else {
				customerAcc=customerAccount.findByAccvirtualidAndStatus(virtualId,ConstantI.CONST_ACTIVE_REG);
			}
			
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return customerAcc;
	}
	
	@Override
	public long getRegIdOfActiveAccByVpa(String vpa) {
		long regId = 0;
		try {
			List<Object> list = customerAccount.getRegIdByAccvirtualidAndStatus(vpa, 1);
			if (list == null || list.size() == 0) {
				log.info("NO ACTIVE ACCOUNTS FOUND FOR VPA {}", vpa);
			} else if (list.size() == 1) {
				regId = Util.fromNumberObj(list.get(0));
			} else {
				log.error("MORE THAN ONE REGID FOUND FOR VPA {} ,regids={}", vpa, list);
				ErrorLog.sendError("MORE THAN ONE REGID FOUND FOR  VPA ", vpa, CustomerAccountDaoImpl.class);
			}
		} catch (Exception e) {
			log.error("error in findAccByAccvirtualidAndStatus {}", e);
			ErrorLog.sendError(e, CustomerAccountDaoImpl.class);
		}
		return regId;
	}

	@Override
	public boolean isBharatQR(String payeeAddr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValAddrCustomerDto getValAddrCustomerDto(String vpa) {
		try {
			List<ValAddrCustomerDto> list = customerAccount.getCustForValAddr(vpa, ConstantI.STATUS_1,
					ConstantI.STATUS_1);
			// TODO need to check whether diffrent regId
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, CustomerAccountDaoImpl.class);
		}
		return null;
	}

	@Override
	public List<CustomerAccountDto> getCustAccDtoForMobValAdd(String payerAddr) {
		try {
			return customerAccount.getForMobValAdd(payerAddr, ConstantI.STATUS_1, ConstantI.STATUS_1);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, CustomerAccountDaoImpl.class);
		}
		return new ArrayList<>();
	}

	
	@Override
	public CustomerAccount getAccDetailsByAccvirtualidAndStatus(String vpa) {
		CustomerAccount customerdetails=null;
		try {
		List<CustomerAccount> list = customerAccount.getAccDetailsByAccvirtualidAndStatus(vpa, 1);
		if (list == null || list.size() == 0) {
			log.info("NO ACTIVE ACCOUNTS FOUND FOR VPA {}", vpa);
		} else if (list.size() == 1) {
			customerdetails = list.get(0);
		} else {
			log.error("MORE THAN ONE REGID FOUND FOR VPA {} ,regids={}", vpa, list);
			ErrorLog.sendError("MORE THAN ONE REGID FOUND FOR  VPA ", vpa, CustomerAccountDaoImpl.class);
		}
	} catch (Exception e) {
		log.error("error in findAccByAccvirtualidAndStatus {}", e);
		//e.printStackTrace();
		ErrorLog.sendError(e, CustomerAccountDaoImpl.class);
	}
		return customerdetails;
	}



}
