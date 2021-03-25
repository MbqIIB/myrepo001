package com.npst.upiserver.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.Registration;
import com.npst.upiserver.repo.RegistrationRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.Util;


@Component
public class RegistrationDaoImpl implements RegistrationDao {
	
	private static final Logger	log	= LoggerFactory.getLogger(RegistrationDaoImpl.class);
	final static int STATUS_ACTIVE = 1;
	
	
	@Autowired
	RegistrationRepository			registrationRepo;
	
	@Override
	public boolean isActiveUser(Long regid) {
		try {
			if (registrationRepo.countByRegidAndStatus(regid, ConstantI.CONST_ACTIVE_REG)>0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	
	
	
	@Override
	public boolean isActiveRegistration(List<CustomerAccount> listaccounts) {
		if (listaccounts == null || listaccounts.size() == 0) {
			return false;
		}
		try {
			boolean f = true;
			long regId = listaccounts.get(0).getRegid();
			String custType = listaccounts.get(0).getCusttype();
			for (int i = 1; i < listaccounts.size(); i++) {
				if (regId != listaccounts.get(0).getRegid()) {
					f = false;
					log.error("Two different app registration ,regid has same vpa or Accvirtualid ie ={}",
							listaccounts.get(0).getAccvirtualid());
					ErrorLog.sendError("Two different app registration ,regid has same vpa or Accvirtualid ie",
							listaccounts.get(0).getAccvirtualid(), RegistrationDaoImpl.class);
					break;
				}
			}
			if (f) {
				if (ConstantI.ENTITY.equalsIgnoreCase(custType)) {
					return 1 == registrationRepo.countByRegidAndStatus(regId, 11);
				} else {
					return 1 == registrationRepo.countByRegidAndStatus(regId, STATUS_ACTIVE);
				}
			}
		} catch (Exception e) {
			log.error("error isActiveRegistration by listaccounts {}", e);
			ErrorLog.sendError(e, RegistrationDaoImpl.class);
		}
		return false;
	}

	@Override
	public Registration getRegistrationDetails(Long regId) {
		try {
			return registrationRepo.findByRegid(regId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Registration findActiveRegByMobno(String mobileNo) {
		try {
			return registrationRepo.findByMobnoAndStatus(mobileNo,ConstantI.CONST_ACTIVE_REG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public boolean updateRegistration(Registration registration) {
		try {
			registrationRepo.save(registration);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean delete(ReqResp reqJson) {
		try {
			Registration registration=registrationRepo.findByRegid(Long.valueOf(reqJson.getRegId()));
			if(registration!=null) {
				registrationRepo.delete(registration);
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	
	@Override
	public RegDto getGCMToken(long regid) {
		try {
			return registrationRepo.findDtoByRegId(regid);
		} catch (Exception e) {
			log.error("error in getGCMToken {}", e);
			ErrorLog.sendError(e, RegistrationDaoImpl.class);
		}
		return null;
	}
	
	
		/*@Override
	public boolean isActiveRegistration(List<CustomerAccount> listaccounts) {
		if (listaccounts == null || listaccounts.size() == 0) {
			return false;
		}
		try {
			boolean f = true;
			long regId = listaccounts.get(0).getRegid();
			String custType = listaccounts.get(0).getCusttype();
			for (int i = 1; i < listaccounts.size(); i++) {
				if (regId != listaccounts.get(0).getRegid()) {
					f = false;
					log.error("Two different app registration ,regid has same vpa or Accvirtualid ie ={}",
							listaccounts.get(0).getAccvirtualid());
					ErrorLog.sendError("Two different app registration ,regid has same vpa or Accvirtualid ie",
							listaccounts.get(0).getAccvirtualid(), RegistrationDaoImpl.class);
					break;
				}
			}
			if (f) {
				if (ConstantI.ENTITY.equalsIgnoreCase(custType)) {
					//return 1 == registrationRepo.countByRegidAndStatus(regId, MERCHNAT_ACTIVE_STATUS);
				} else {
					System.out.println("count"+registrationRepo.countByRegidAndStatus(regId, STATUS_ACTIVE));

					return 1 == registrationRepo.countByRegidAndStatus(regId, STATUS_ACTIVE);
				}
			}
		} catch (Exception e) {
			log.error("error isActiveRegistration by listaccounts {}", e);
			ErrorLog.sendError(e, RegistrationDaoImpl.class);
		}
		return false;
	}
*/
	@Override
	public long getActiveRegIdByDefVpa(String vpa) {
		long regId = 0;
		try {
			List<Object> list = registrationRepo.getRegIdByDefVpaAndStatus(vpa, STATUS_ACTIVE);
			if (list == null || list.isEmpty()) {
				log.info("no active regId found in registration for vpa={}", vpa);
			} else if (list.size() == 1) {
				regId = Util.fromNumberObj(list.get(0));
			} else {
				log.info("MORE THAN ONE ACTIVE REGISTARTION FOUND FOR DEFVPA={}", vpa);
			}
		} catch (Exception e) {
			log.error("error in getActiveRegIdByDefVpa {}", e);
			ErrorLog.sendError(e, RegistrationDaoImpl.class);
		}
		return regId;
	}

	@Override
	public boolean isActiveRegistration(long regId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getRegIdByDefVpa(String vpa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String regId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long regId) {
		// TODO Auto-generated method stub
		
	}
	
}
