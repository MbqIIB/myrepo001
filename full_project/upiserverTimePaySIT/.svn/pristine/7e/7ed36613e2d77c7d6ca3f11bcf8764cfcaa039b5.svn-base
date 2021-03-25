package com.npst.upiserver.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerMandatesDao;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.entity.MandatesHistoryEntity;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.RespMandate;
import com.npst.upiserver.repo.CustomerMandatesRepo;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.Util;

@Component
public class CustomerMandatesDaoImpl implements CustomerMandatesDao {
	private static final Logger log = LoggerFactory.getLogger(CustomerMandatesDaoImpl.class);
	@Autowired
	private CustomerMandatesRepo customerMandatesRepo;

	@Override
	public MandatesHistoryEntity getMandatesHistory(String txnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertSuccessMandate(RespMandate respMandate) {
		// TODO Auto-generated method stub

	}

	@Override
	public CustomerMandatesEntity setCustomerMandates(MandatesHistoryEntity mandateHistory, String txnType, String umn,
			String txnInitiatedBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertSuccessMandate(ReqMandateConfirmation reqMandateConfirmation) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<CustomerMandatesEntity> findMandatesTxnsToProcess() {
		final Set<CustomerMandatesEntity> mandatesListToProcess = new HashSet<CustomerMandatesEntity>();
		try {
			List<CustomerMandatesEntity> mandatesList =customerMandatesRepo.getByPspHandleAndDateAndStatus(Constant.BANK_HANDAL, Util.getCurrent_ddMMyyyy(), ConstantI.STATUS_2);
					
			log.info("mandatesList size={}",mandatesList.size());
			/*
			 * CustomerMandates customerMandates = new CustomerMandates();
			 * customerMandates.setMandateUmn("12345678@cnrb");
			 * customerMandates.setMandateValidityStart("30122018");
			 * customerMandates.setMandateValidityEnd("22072019");
			 * customerMandates.setMandateRecurrencepattern("WEEKLY");
			 * customerMandates.setMandateRecurrenceRuletype("BEFORE");
			 * customerMandates.setStatus(MandateStatus.MANDATE_SUCCESS.getStatus());
			 * mandatesList.add(customerMandates);
			 */
			for (CustomerMandatesEntity mandates : mandatesList) {
				if (isValidTransactionToProcess(mandates)) {
					mandatesListToProcess.add(mandates);
				}
			}
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, CustomerMandatesDaoImpl.class);
		}
		return mandatesListToProcess;
	}

	@Override
	public CustomerMandatesEntity findByUmn(String umn) {
		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnOrderByIdDesc(umn);
			if (list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			log.error("error in findByUmn {}", e);
			ErrorLog.sendError(e, CustomerMandatesDaoImpl.class);
		}
		return null;
	}

	@Override
	public boolean isValidTransactionToProcess(CustomerMandatesEntity mandates) {
		// TODO discussion
		/*
		if(StringUtils.isEmpty(mandates.getMandateUmn())) {
			return false;
		}
		LocalDate today = LocalDate.now();
		String validityend = mandates.getMandateValidityEnd();
		String validitystart = mandates.getMandateValidityStart();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate validityenddate = LocalDate.parse(validityend, formatter);
		LocalDate validitystartdate = LocalDate.parse(validitystart, formatter);
		String recurrencepattern = mandates.getMandateRecurrencepattern();
		String recurrenceRuleType = mandates.getMandateRecurrenceRuletype();
		
		LocalDate dpattern = validitystartdate;
		
		if(MandateStatus.MANDATE_SUCCESS.getStatus() != mandates.getStatus() || !(validitystartdate.isBefore(today) || validitystartdate.isEqual(today)) ||  (validityenddate.isBefore(today) || validityenddate.isEqual(today))) {
			return false;
		}
		if(RecurrencePatternType.ASPRESENTED.toString().equalsIgnoreCase(recurrencepattern)) {
			return true;
		} else if(RecurrencePatternType.ONETIME.toString().equalsIgnoreCase(recurrencepattern)) {
			return true;
		} else if(RecurrencePatternType.DAILY.toString().equalsIgnoreCase(recurrencepattern)) {
			return true;
		} else if(RecurrencePatternType.WEEKLY.toString().equalsIgnoreCase(recurrencepattern)) {
			
				while(dpattern.isBefore(today)) {
					dpattern = validitystartdate.plusDays(7);
				}
		
			
		} else if(RecurrencePatternType.FORTNIGHTLY.toString().equalsIgnoreCase(recurrencepattern)) {
			
				while(dpattern.isBefore(today)) {
					dpattern = validitystartdate.plusDays(15);
				}
			
		} else if(RecurrencePatternType.MONTHLY.toString().equalsIgnoreCase(recurrencepattern)) {
			
				while(dpattern.isBefore(today)) {
					dpattern = validitystartdate.plusMonths(1);
				}
		
			
		} else if(RecurrencePatternType.BIMONTHLY.toString().equalsIgnoreCase(recurrencepattern)) {

				while(dpattern.isBefore(today)) {
					dpattern = validitystartdate.plusMonths(2);
				}
			
			
		} else if(RecurrencePatternType.QUARTERLY.toString().equalsIgnoreCase(recurrencepattern)) {
			
				while(dpattern.isBefore(today)) {
					dpattern = validitystartdate.plusMonths(3);
				}
				
			
			
		} else if(RecurrencePatternType.HALFYEARLY.toString().equalsIgnoreCase(recurrencepattern)) {
		
				while(dpattern.isBefore(today)) {
					dpattern = validitystartdate.plusMonths(6);
				}
		
			
			
		} else if(RecurrencePatternType.YEARLY.toString().equalsIgnoreCase(recurrencepattern)) {

				while(dpattern.isBefore(today)) {
					dpattern = validitystartdate.plusYears(1);
				}	
		}
		
		if(RecurrenceRuleType.AFTER.toString().equals(recurrenceRuleType)) {
			dpattern = dpattern.plusDays(1);
		}
		if(RecurrenceRuleType.BEFORE.toString().equals(recurrenceRuleType)) {
			dpattern = dpattern.minusDays(1);
		}
		
		return dpattern.isEqual(today); */
		return true;
	}

	@Override
	public CustomerMandatesEntity findByUmnAndCustIs(String umn) {
		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnAndCustIs(umn,ConstantI.PAYEE);
			
			log.info("got tge dta ");
			if (list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			log.error("error in findByUmn {}", e);
			ErrorLog.sendError(e, CustomerMandatesDaoImpl.class);
		}
		return null;
	}

	@Override
	public CustomerMandatesEntity findByUmnAndSignValue(String umn) {
		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnAndCustIs(umn,ConstantI.PAYEE);
			
			log.info("got tge dta ");
			if (list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			log.error("error in findByUmn {}", e);
			ErrorLog.sendError(e, CustomerMandatesDaoImpl.class);
		}
		return null;
	}
	
	@Override
	public CustomerMandatesEntity findByUmnAndSignValuenull(String umn) {
		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnAndCustIsSignValue(umn,ConstantI.PAYEE);
			
			log.info("got tge dta ");
			if (list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			log.error("error in findByUmn {}", e);
			ErrorLog.sendError(e, CustomerMandatesDaoImpl.class);
		}
		return null;
	}

	@Override
	public List<CustomerMandatesEntity> findByUmnAndType(String UMN) {
		List<String> type =new ArrayList<>();
		type.add("CREATE");
		type.add("UPDATE");
		List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnAndTxnTypeIn(UMN,type);
		if (list.size()==0) {
			return null;
		}
		return list;
	}
	

}
