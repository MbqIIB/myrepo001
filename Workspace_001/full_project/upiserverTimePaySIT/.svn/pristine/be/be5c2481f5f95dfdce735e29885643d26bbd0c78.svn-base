package com.npst.upiserver.dao.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.BlockedByCustomerDao;
import com.npst.upiserver.entity.BlockedByCustomer;
import com.npst.upiserver.repo.BlockedByCustomerRepository;
import com.npst.upiserver.util.ErrorLog;


@Component
public class BlockedByCustomerDaoImpl implements BlockedByCustomerDao {

	private static final Logger	log	= LoggerFactory.getLogger(BlockedByCustomerDaoImpl.class);
	
	@Autowired
	BlockedByCustomerRepository blockedByCustRepo;
	
	@Override
	public boolean isBlockedVpa(String blockedVpa, Long regId) {
		try {
			log.debug("before execute query");
			if(blockedByCustRepo.countByRegidAndBlockedvpa(regId.intValue(), blockedVpa)>0) {
				log.debug("after execute query");
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
			return false;
		}
	}

	@Override
	public void insert(String payeeAddr, Long regId) {
		try {
			BlockedByCustomer blockedbycustomer = new BlockedByCustomer();
			blockedbycustomer.setBlockeddate(new Date());
			blockedbycustomer.setBlockedvpa(payeeAddr);
			blockedbycustomer.setStatus(1);
			blockedbycustomer.setRegid(regId.intValue());
			blockedByCustRepo.save(blockedbycustomer);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void insertPayeeAddr(String payeeAddr, long regId) {
		try {
			BlockedByCustomer blockedbycustomer = new BlockedByCustomer();
			blockedbycustomer.setBlockeddate(new Date());
			blockedbycustomer.setBlockedvpa(payeeAddr);
			blockedbycustomer.setStatus(1);
			blockedbycustomer.setRegid(Integer.valueOf(String.valueOf(regId)));
			blockedByCustRepo.save(blockedbycustomer);
		} catch (Exception e) {
			log.error("error cause ={}", e.getMessage());
			//ErrorLog.sendError(e, BlockedByCustomerDaoImpl.class);
		}
	}
	
	
	@Override
	public boolean isBlockedByCustomer(String vpa, long regid) {
		try {
			// TODO need discussion
			log.info("regid is {} and vpa {}",regid, vpa);
			long cnt = blockedByCustRepo.countByRegidAndBlockedvpaAndStatus(Integer.valueOf(String.valueOf(regid)), vpa, ConstantI.STATUS_1);
			log.info("block count is {}",cnt);
			if (cnt >= 1) {
				return true;
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, BlockedByCustomerDaoImpl.class);
		}
		return false;
	}
}
