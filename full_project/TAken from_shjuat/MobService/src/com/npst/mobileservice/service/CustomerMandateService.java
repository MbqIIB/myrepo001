package com.npst.mobileservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.CustomerMandateDao;
import com.npst.mobileservice.dao.MandateHistoryDao;
import com.npst.mobileservice.object.CustomerMandateDTO;
import com.npst.mobileservice.object.CustomerMandateHistoryDto;
import com.npst.mobileservice.object.MandatesHistory;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.CustomerMandatesEntity;


public class CustomerMandateService {

	private static final Logger					log							= Logger.getLogger(CustomerMandateService.class);
	CustomerMandateDao customerMandateDao=null;
	MandateHistoryDao mandateHistoryDao=null;
	
	public List<CustomerMandateDTO> myMandate(ReqJson reqJson) {
		List<CustomerMandateDTO> customerMandateDTOlist=new ArrayList<>();
		List<CustomerMandatesEntity> customerMandateslist=null;
		try {
			if(customerMandateDao==null) {
				customerMandateDao=new CustomerMandateDao();
			}
			customerMandateslist=customerMandateDao.myMandate(reqJson.getRegId(),reqJson.getType());
			if(null!=customerMandateslist &&!customerMandateslist.isEmpty()) {
				customerMandateDTOlist=customerMandateslist.stream().map(item -> mapEntityToVO(item)).collect(Collectors.toList());
				Collections.sort(customerMandateDTOlist, new CustomerMandateDTO());
			}
		} catch (Exception ex) {
			log.info(ex.getMessage(),ex);
		}
		return customerMandateDTOlist;
		
	}

	private CustomerMandateDTO mapEntityToVO(CustomerMandatesEntity item) {
		
		CustomerMandateDTO customerMandateDTO=new CustomerMandateDTO();
		System.out.println("inside mapEntityToVO");
		try {
			if(com.npst.mobileservice.util.Util.isMandateExpired(item)){
					BeanUtils.copyProperties(customerMandateDTO, item);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(),ex);
		}
		return customerMandateDTO;
	}
	
	public List<CustomerMandateHistoryDto> mandateHistory(ReqJson reqJson ){
		List<MandatesHistory> mandateHistory=null;
		log.info("Inside mandateHistory method" );
		List<CustomerMandateHistoryDto> customerMandateHistoryDtos=new ArrayList<>();
		try {
			if(mandateHistoryDao==null) {
				mandateHistoryDao=new MandateHistoryDao();
			}
			mandateHistory=mandateHistoryDao.mandateHistory(reqJson.getRegId(),reqJson.getMandateType());
			if(null!=mandateHistory && !mandateHistory.isEmpty()) {
				log.info("Inside mandateHistory not null" );
				customerMandateHistoryDtos=mandateHistory.stream().map(item -> mapEntityToDto(item)).collect(Collectors.toList());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return customerMandateHistoryDtos;
	}

	private CustomerMandateHistoryDto mapEntityToDto(MandatesHistory item) {
		log.info("Inside mapEntityToDto" );
		CustomerMandateHistoryDto customerMandateHistoryDto=new CustomerMandateHistoryDto();
		try {
			BeanUtils.copyProperties(customerMandateHistoryDto, item);
			log.info("Inside mapEntityToDto 1" );
		} catch (Exception ex) {
			log.info("Inside mapEntityToDto 2" );
			log.info(ex.getMessage(),ex);
		}
		return customerMandateHistoryDto;
	}
}
