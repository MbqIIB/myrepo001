package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.npst.mobileservice.dao.CustomeraccountHome;
import com.npst.mobileservice.dao.MaxVpaPerUserCheck;
import com.npst.mobileservice.dao.RegistrationHome;
import com.npst.mobileservice.object.CustomeraccountVO;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.CustomerVPA;
import com.npst.upi.hor.Customeraccount;
import com.npst.upi.hor.Custvpa;
import com.npst.upi.hor.Registration;

public class CustomeraccountHomeService {
	private static CustomeraccountHome		customeraccountHome		= null;
	private static final Logger				log						= Logger
			.getLogger(CustomeraccountHomeService.class);
	private static RegistrationHome			registrationHome		= null;
	private static String					DEFAULT_LIMIT_AMOUNT	= Util.getProperty("LIMITAMOUNT");
	private static String					MAX_LIMIT_AMOUNT_STR	= Util.getProperty("MAXLIMITAMOUNT");
	private static RegistrationHomeService	registrationHomeService	= null;
	private MaxVpaPerUserCheck maxVpaPerUserCheck;
	static {
		if (Util.isNullOrEmpty(DEFAULT_LIMIT_AMOUNT)) DEFAULT_LIMIT_AMOUNT = "10000";
		if (Util.isNullOrEmpty(MAX_LIMIT_AMOUNT_STR)) MAX_LIMIT_AMOUNT_STR = "100000";
	}
	
	public boolean checkVirtualId(String reqJsonStr) {
		log.info("reqJsonStr:" + reqJsonStr);
		RespJson respJson = new RespJson();
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		boolean results = false;
		try {
			ReqJson reqJson = JSONConvert.getReqJson(reqJsonStr);
			respJson.setReqId(reqJson.getReqId());
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			results = customeraccountHome.checkVPA(reqJson.getVirtualId());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
		}
		log.info("return successfully with respJson:" + respJson);
		return results;
	}
	
	public RespJson deleteAll(ReqJson reqJson) {
		log.info("Request received:" + reqJson);
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			List<Customeraccount> listCustAcc = customeraccountHome
					.getListAccountByRegVPAId(Integer.parseInt(reqJson.getRegId()));
			
			for (Customeraccount customeraccount : listCustAcc) {
				customeraccount.setDefaccount(ConstantI.DEFAULT_ACC_N);
				customeraccount.setStatus(ConstantI.DEREGISTER_ACCOUNT);
				if (customeraccountHome.updateAccount(customeraccount)) {
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_DEREGISTARTION_SUCCESS.getCode());
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_DEREGISTARTION_FAIL.getCode());
				}
			}
			if(registrationHomeService==null) {
				registrationHomeService = new RegistrationHomeService();
			}
			respJson = registrationHomeService.regVpaDelete(reqJson);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("error in " + s);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			respJson.setMsgId(ConstantI.MSGID_FAIL);
		}
		log.info("return successfully with respJson:" + respJson);
		return respJson;
	}
	
	public RespJson getAddedAccount(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		
		if (null == reqJson.getMobileNo()) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_BLANKMOBNO.getCode());
			return respJson;
		}
		try {
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			StringBuilder noDefaultAccList = new StringBuilder();
			List<Customeraccount> accountList = customeraccountHome
					.getListAccountByRegVPAId(Integer.parseInt(reqJson.getRegId()));
			
			if (accountList.isEmpty()) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
				respJson.setReqId(reqJson.getReqId());
				return respJson;
			} else {
				List<CustomeraccountVO> customeraccountVOs = new ArrayList<>();
				int countDefAccount = 0;
				String virtualId = accountList.get(0).getAccvirtualid();
				for (Customeraccount customeraccount : accountList) {
					if (virtualId.equals(customeraccount.getAccvirtualid())
							&& customeraccount.getDefaccount().equals(1)) {
						countDefAccount++;
					} else if (!virtualId.equals(customeraccount.getAccvirtualid())) {
						if (countDefAccount == 0) {
							noDefaultAccList.append(virtualId).append("|");
						}
						virtualId = customeraccount.getAccvirtualid();
						countDefAccount = 0;
					}
					
					if (countDefAccount == 0) {
						noDefaultAccList.append(virtualId).append("|");
					}
					
					int sizeOfnoDefaultAccList = noDefaultAccList.length();
					if (sizeOfnoDefaultAccList > 0) {
						respJson.setNoDefaultAccVPA(
								noDefaultAccList.deleteCharAt(sizeOfnoDefaultAccList - 1).toString());
					} else {
						respJson.setNoDefaultAccVPA("");
					}
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setMsg(ConstantI.SUCCESS_STRING);
					
					respJson.setCountOfAccounts(accountList.size());
					if (Util.isNullOrEmpty(customeraccount.getLimitamount()))
						customeraccount.setLimitamount(DEFAULT_LIMIT_AMOUNT);
					customeraccountVOs.add(new CustomeraccountVO(customeraccount));
					
				}
				respJson.setCustomeraccountVOs(customeraccountVOs);
				
			}
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		} finally {
		}
		respJson.setReqId(reqJson.getReqId());
		log.info("Return str[" + respJson + "]");
		return respJson;
	}
	
	public List<Customeraccount> getVpa(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		respJson.setReqId(reqJson.getReqId());
		List<Customeraccount> accountList = null;
		try {
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			accountList = customeraccountHome.getAllVpas(Integer.parseInt(reqJson.getRegId()));
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		log.info("Return str[" + respJson + "]");
		return accountList;
	}
	
	public RespJson insertBankAcc(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		if(maxVpaPerUserCheck==null) {
			maxVpaPerUserCheck=MaxVpaPerUserCheck.getInstance();
		}
		if(!maxVpaPerUserCheck.isAllow(Long.parseLong(reqJson.getRegId()),reqJson.getPayerAddr())) {
			log.info("maxVpaPerUserCheck exceed for regId="+reqJson.getRegId());
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(MaxVpaPerUserCheck.ERROR_CODE);
			return respJson;
		}
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACC_ADDED_SUCCESS.getCode());
		if (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo())) {
			respJson.setMsgId(ErrorCode.AcqErrorCode.MSGID_BLANKMOBNO.getCode());
			respJson.setMsg(ConstantI.MSGID_FAIL);
			return respJson;
		}
		if (null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId())) {
			respJson.setMsgId(ErrorCode.AcqErrorCode.MSGID_BLANK_RGISTRATIONID.getCode());
			respJson.setMsg(ConstantI.MSGID_FAIL);
			return respJson;
		}
		if ("N".equalsIgnoreCase(reqJson.getMbeba())) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FAIL_ACCOUNT_MBEBA.getCode());
			return respJson;
		}
		
		try {
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			if (customeraccountHome.checkReservedVPA(reqJson.getVirtualId())) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_VIRTUALID_EXIST_IN_RESERVEDVPA.getCode());
				log.info("virtualId exists in ReservedVPA List with respJson:" + respJson);
				return respJson;
			}
			List<Customeraccount> resultCustomeraccount = customeraccountHome
					.getListAccountByRegVPAId(Integer.parseInt(reqJson.getRegId()));
			log.info("return success from listAccountHomeDao with resultListAccount:" + resultCustomeraccount);
			if (null != resultCustomeraccount && resultCustomeraccount.size() > 0) {
				for (Customeraccount element : resultCustomeraccount) {
					
					if (null != reqJson.getAccRefNumber() && "" != reqJson.getAccRefNumber()) {
						if (reqJson.getAccRefNumber().trim().equalsIgnoreCase(element.getAccrefnumber())
								&& reqJson.getVirtualId().trim().equalsIgnoreCase(element.getAccvirtualid().trim())) {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_VIRTUALID_EXIST_SAME_ACCOUNT.getCode());
							log.debug("return success with respJson:" + respJson);
							return respJson;
						}
					}
				}
			}
			String isPrimaryAccountSetByMethod = null;
			Customeraccount customerAccount = new Customeraccount();
			customerAccount.setRegid(Integer.parseInt(reqJson.getRegId()));
			customerAccount.setAcctype(reqJson.getAccType().toUpperCase());
			customerAccount.setName(reqJson.getPayerAddr());
			customerAccount.setAeba(reqJson.getAeba());
			customerAccount.setBankname(reqJson.getBankName());
			customerAccount.setCredjson(reqJson.getCredsAllowed().replace("\\", ""));
			customerAccount.setMbeba(reqJson.getMbeba());
			
			customerAccount.setMaskedaccnumber(reqJson.getMaskedAccnumber());
			customerAccount.setName(reqJson.getName().toUpperCase().replaceAll("[^a-zA-Z ]", ""));
			customerAccount.setAccaddeddate(new Date());
			customerAccount.setAccrefnumber(reqJson.getAccRefNumber());
			customerAccount.setAccvirtualid(reqJson.getVirtualId());
			customerAccount.setCustcode("0000");
			customerAccount.setCusttype("PERSON");
			List<Customeraccount> listByVpa = customeraccountHome.getListAccountByAccvirtualid(reqJson.getVirtualId());
			if (0 == listByVpa.size()) {
				customerAccount.setDefaccount(ConstantI.DEFAULT_ACC_Y);
				isPrimaryAccountSetByMethod = ConstantI.IS_PRIMARY_ACCOUNT_SET_NO;
			} else {
				for (Customeraccount customeraccount2 : listByVpa) {
					if (1 == customeraccount2.getDefaccount()) {
						customerAccount.setDefaccount(ConstantI.DEFAULT_ACC_N);
						isPrimaryAccountSetByMethod = null;
						break;
					} else {
						customerAccount.setDefaccount(ConstantI.DEFAULT_ACC_Y);
						isPrimaryAccountSetByMethod = ConstantI.IS_PRIMARY_ACCOUNT_SET_NO;
					}
				}
			}
			customerAccount.setMobileno(reqJson.getMobileNo());
			customerAccount.setIfsc(reqJson.getIfsc());
			customerAccount.setMmid(reqJson.getMmid());
			customerAccount.setStatus(ConstantI.NORMAL_ACCOUNT);
			
			customerAccount.setAccPvdIfsc(reqJson.getAccPvdIfsc());
			if (customerAccount.getCredjson() != null && ( customerAccount.getCredjson().contains("ATM") || customerAccount.getCredjson().contains("atm"))) {
				customerAccount.setAccPvdFormat(ConstantI.FORMAT2);
			} else {
				customerAccount.setAccPvdFormat(ConstantI.FORMAT1);
			}
			
			if (!Util.isNullOrEmpty(reqJson.getLimitamount())) customerAccount.setLimitamount(reqJson.getLimitamount());
			else customerAccount.setLimitamount(DEFAULT_LIMIT_AMOUNT);
			
			// setting values to insert in customervpa
			CustomerVPA customerVpa = new CustomerVPA(
					new Custvpa(customerAccount.getRegid(), customerAccount.getAccvirtualid()), new Date());
			
			if (customeraccountHome.saveListAccount(customerAccount, customerVpa)) {
				log.info("just before checking of default account for VPA");
				if (null == isPrimaryAccountSetByMethod
						&& ConstantI.IS_DEFAULT_VIRTUALID.equals(reqJson.getIsDefaultAcc())) {
					try {
						setDefAccountForVPA(reqJson, customerAccount);
					} catch (Exception e) {
						log.info(e.getMessage(), e);
					}
					
				}
			} else {
				if (customerVpa.isDuplicateVPA()) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PRESENT_VIRTUALID.getCode());
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ConstantI.ERROR_ACC_ADD);
				}
				
			}
			
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		log.info("Return successfully with respJson:" + respJson);
		return respJson;
	}
	
	private Boolean setDefAccountForVPA(ReqJson reqJson, Customeraccount customerAccount) {
		Boolean flag = false;
		log.info("inside setDefAccountForVPA");
		if (null != reqJson.getIsDefaultAcc() && reqJson.getIsDefaultAcc().equals("1")) {
			log.info("setting default account for vpa");
			customerAccount.setDefaccount(ConstantI.DEFAULT_ACC_Y);
			flag = customeraccountHome.deregisterDefaultAcc(reqJson.getVirtualId(), customerAccount);
			
		} else if (null != reqJson.getIsDefaultAcc() && reqJson.getIsDefaultAcc().equals("2")) {
			log.info("setting normal account for vpa");
			customerAccount.setDefaccount(ConstantI.DEFAULT_ACC_N);
			flag = customeraccountHome.saveListAccount(customerAccount);
		}
		return flag;
	}
	
	public RespJson setDefault(ReqJson reqJson) {
		Session session = null;
		RespJson respJson = new RespJson();
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_DEF_ACC_SET_FAIL.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			List<Customeraccount> listaccount = customeraccountHome
					.getActiveListAccountByVPAAndRegVPAId(reqJson.getVirtualId(), Integer.parseInt(reqJson.getRegId()));
			for (Customeraccount customeraccount2 : listaccount) {
				if (customeraccount2.getAccrefnumber().equalsIgnoreCase(reqJson.getAccRefNumber())) {
					if (setDefAccountForVPA(reqJson, customeraccount2)) {
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_DEF_ACC_SET_SUCCESS.getCode());
						respJson.setMsgId(ConstantI.MSGID_SUCCESS);
						break;
					}
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.info("return successfully with respJson:" + respJson);
		return respJson;
	}
	
	public Customeraccount getActiveAccountByAccountAndVpaAndRegId(final String accrefnumber, final String regId,
			final String accvirtualid) {
		RespJson respJson = new RespJson();
		
		Customeraccount customeraccount = null;
		
		try {
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			customeraccount = customeraccountHome.getActiveAccountByAccountAndVpaAndRegId(accrefnumber, regId,
					accvirtualid);
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return customeraccount;
	}
	
	public boolean checkReservedVPA(String virtualId, RespJson respJson) {
		Boolean flag = false;
		if (null == customeraccountHome) {
			customeraccountHome = new CustomeraccountHome();
		}
		if (customeraccountHome.checkReservedVPA(virtualId)) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_VIRTUALID_EXIST_IN_RESERVEDVPA.getCode());
			log.debug("virtualId exists in ReservedVPA List with respJson:" + respJson);
			flag = true;
		}
		return flag;
	}
	
	public RespJson getAllVPAByAccount(ReqJson reqJson) {
		log.info("reqJsonStr:" + reqJson);
		RespJson respJson = new RespJson();
		
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			List<String> customerAccList = customeraccountHome.getActiveInactiveVPAForAccount(reqJson.getAccRefNumber(),
					reqJson.getRegId());
			if (customerAccList != null && !customerAccList.isEmpty()) {
				respJson.setVirtualIdsForAcc(customerAccList);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}
	
	public RespJson getAccountByVpaAndRegId(final String regIdStr, final String accvirtualid) {
		final RespJson respJson = new RespJson(ConstantI.MSGID_SUCCESS, ConstantI.SUCCESS_STRING);
		final List<CustomeraccountVO> accounts = new ArrayList<>();
		int regId = 0;
		try {
			regId = Integer.parseInt(regIdStr);
		} catch (NumberFormatException ex) {
		}
		try {
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			final List<Customeraccount> customeraccounts = customeraccountHome
					.getActiveListAccountByVPAAndRegVPAId(accvirtualid, regId);
			for (final Customeraccount customeraccount : customeraccounts) {
				if (null != customeraccount) accounts.add(new CustomeraccountVO(customeraccount));
			}
			respJson.setCustomeraccountVOs(accounts);
		} catch (final Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return respJson;
	}
	
	public RespJson getNonPrimaryAccVPA(ReqJson reqJson) {
		log.info("Execution start of getNonPrimaryAccVPA() method");
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		List<Customeraccount> custAccList = null;
		StringBuilder noDefaultAccList = new StringBuilder();
		try {
			log.info("reqJsonStr:" + reqJson);
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			custAccList = customeraccountHome.getNonPrimaryAccVPA(reqJson.getRegId());
			if (custAccList != null && !custAccList.isEmpty()) {
				int countDefAccount = 0;
				String virtualId = custAccList.get(0).getAccvirtualid();
				for (Customeraccount custAcc : custAccList) {
					if (virtualId.equals(custAcc.getAccvirtualid()) && custAcc.getDefaccount().equals(1)) {
						countDefAccount++;
					} else if (!virtualId.equals(custAcc.getAccvirtualid())) {
						if (countDefAccount == 0) {
							noDefaultAccList.append(virtualId).append("|");
						}
						virtualId = custAcc.getAccvirtualid();
						countDefAccount = 0;
					}
				}
				if (countDefAccount == 0) {
					noDefaultAccList.append(virtualId).append("|");
				}
			}
			int sizeOfnoDefaultAccList = noDefaultAccList.length();
			if (sizeOfnoDefaultAccList > 0) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
				respJson.setNoDefaultAccVPA(noDefaultAccList.deleteCharAt(sizeOfnoDefaultAccList - 1).toString());
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ConstantI.FAILURE_STRING);
				respJson.setNoDefaultAccVPA("");
			}
			
		} catch (final Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		log.info("Execution end of getNonPrimaryAccVPA() method");
		return respJson;
	}
	
	public RespJson accountStatus(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACTIVE_ACC_FAIL.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			log.info("Request after convert json:" + reqJson.toString());
			respJson.setReqId(reqJson.getReqId());
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			
			if (customeraccountHome.updateAccount(reqJson.getRegId(), reqJson.getVirtualId(), reqJson.getAccRefNumber(),
					reqJson.getStatus())) {
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACTIVE_ACC_SUCCESS.getCode());
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				return respJson;
			} else {
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACTIVE_ACC_FAIL.getCode());
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				return respJson;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("return successfully with respJson :" + respJson);
		return respJson;
	}
	
	public RespJson vpaStatus(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			if (customeraccountHome.updateVpa(reqJson.getRegId(), reqJson.getVirtualId(), reqJson.getStatus())) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_SUCCESS_CHANGED_VPA_STATUS.getCode());
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FAILED_CHANGED_VPA_STATUS.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.debug("return successfully with respJson :" + respJson);
		return respJson;
	}
	
	public RespJson getMaskedAcc(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ACTIVE_ACC_FAIL.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			Registration registration = registrationHome.getActiveUserByMobNo(reqJson.getMobileNo()).get(0);
			List<Customeraccount> custAccList = customeraccountHome.getRecByMobileNo(reqJson.getMobileNo(),
					registration.getRegid(), registration.getDefvpa());
			if (!custAccList.isEmpty()) {
				for (Customeraccount customeraccount : custAccList) {
					respJson.setMaskedAccnumber(customeraccount.getMaskedaccnumber());
				}
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("return successfully with respJson :" + respJson);
		return respJson;
	}
	
	public RespJson getDefInfo(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == customeraccountHome) {
				customeraccountHome = new CustomeraccountHome();
			}
			if (null == registrationHome) {
				registrationHome = new RegistrationHome();
			}
			Registration registration = registrationHome.getUserById(Integer.parseInt(reqJson.getRegId()));
			Customeraccount custAcc = customeraccountHome.getDefAcc(Integer.parseInt(reqJson.getRegId()),
					registration.getDefvpa());
			if (null != custAcc) {
				respJson.setPayerName(custAcc.getName());
				respJson.setVirtualId(registration.getDefvpa());
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		log.info("return successfully with respJson :" + respJson);
		return respJson;
	}
	
	public boolean isVpaAvailForNewAddition(String vpa) throws Exception {
		boolean f = false;
		if (null == customeraccountHome) {
			customeraccountHome = new CustomeraccountHome();
		}
		if (null == registrationHome) {
			registrationHome = new RegistrationHome();
		}
		if (!registrationHome.checkVPA(vpa)) {
			if (!customeraccountHome.checkVPA(vpa)) {
				if (!customeraccountHome.checkReservedVPA(vpa)) {
					return true;
				}
			}
		}
		return f;
	}
	
	
	public RespJson addUpdateAccount(ReqJson reqJson, List<CustomeraccountVO> reqAccountList) {
		// TODO ATAL
		boolean f = false;
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		List<Customeraccount> inList = getCustAccountList(reqAccountList);
		List<Customeraccount> newCustList = getCustAccountList(reqAccountList);
		List<Customeraccount> existsingAccount = customeraccountHome
				.getListAccountByRegVPAId(Integer.parseInt(reqJson.getRegId()));
		boolean temp = true;
		if (existsingAccount != null && existsingAccount.size() > 0) {
			for (Customeraccount in : inList) {
				for (Customeraccount e : existsingAccount) {
					if (e.getAccrefnumber().equalsIgnoreCase(in.getAccrefnumber())) {
						log.info("going to update acc=" + e.getAccrefnumber());
						mergeInExistingAcc(e, in);
						boolean isEDef = (e.getDefaccount() != null && e.getDefaccount() == 1);
						f = customeraccountHome.saveListAccount(e);
						if (!isEDef) {
							if (e.getDefaccount() != null && e.getDefaccount() == 1) {
								temp = true;
								f = customeraccountHome.updateNonPrimaryByCaidNotIn(e.getRegid(), e.getCaid(),
										ConstantI.NORMAL_ACCOUNT);
							}
						}
					} else {
						newCustList.add(in);
					}
				}
				if (newCustList != null && newCustList.size() > 0) {
					f = false;
					if (temp) {
						existsingAccount = customeraccountHome
								.getListAccountByRegVPAId(Integer.parseInt(reqJson.getRegId()));
					}
					Integer cc = defaultActiveCaid(existsingAccount);
					if (cc == null) {
						checkIfNotMakeOnePrimary(newCustList);
						f = customeraccountHome.saveListOfCustAccount(newCustList);
					} else {
						if (checkPrimaryAcc(newCustList)) {
							checkIfNotMakeOnePrimary(newCustList);
							f = customeraccountHome.saveListOfCustAccount(newCustList);
							cc = defaultActiveCaid(newCustList);
							if (cc != null) {
								f = customeraccountHome.updateNonPrimaryByCaidNotIn(
										Integer.parseInt(reqJson.getRegId()), cc, ConstantI.NORMAL_ACCOUNT);
							}
						} else {
							f = customeraccountHome.saveListOfCustAccount(newCustList);
						}
					}
					
				}
			}

		} else {
			checkIfNotMakeOnePrimary(inList);
			f = customeraccountHome.saveListOfCustAccount(inList);
		}
		if (f) {
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
		}
		return respJson;
	}
	
	private static List<Customeraccount> getCustAccountList(List<CustomeraccountVO> reqAccountList) {
		List<Customeraccount> outlist=new ArrayList<Customeraccount>();
		Customeraccount acc=null;
		for(CustomeraccountVO vo:reqAccountList) {
			log.info("vo="+vo);
			if(StringUtils.isNotBlank(vo.getAccrefnumber()) && StringUtils.isNotBlank(vo.getIfsc()) && StringUtils.isNotBlank(vo.getMobileno()) && StringUtils.isNotBlank(vo.getAccvirtualid()))
			{
				acc=new Customeraccount();
				//acc.setAeba("N");
				acc.setAccPvdIfsc(vo.getIfsc());
				//acc.setAccPvdFormat("FORMAT1");
				//acc.setMbeba("N");
				acc.setAccaddeddate(new Date());
				//acc.setLimitamount("1000000");
				
				acc.setCaid(vo.getCaid());
				acc.setAccrefnumber(vo.getAccrefnumber());
				acc.setIfsc(vo.getIfsc());
				acc.setBankname(vo.getBankname());
				if(vo.getStatus()!=null && vo.getStatus()!=0) {
					acc.setStatus(vo.getStatus());	
				}else {
					acc.setStatus(ConstantI.NORMAL_ACCOUNT);	
				}
				acc.setAccvirtualid(vo.getAccvirtualid());
				if(StringUtils.isNotBlank(vo.getAcctype())){
					acc.setAcctype(vo.getAcctype().toUpperCase());
				}else {
					acc.setAcctype("DEFAULT");	
				}
				acc.setRegid(vo.getRegid());
				acc.setDefaccount(vo.getDefaccount());
				if(acc.getDefaccount()==null) {
					acc.setDefaccount(0);
				}
				acc.setName(vo.getName());
				acc.setCustcode(vo.getCustcode());
				acc.setCusttype("ENTITY");
				acc.setMobileno(vo.getMobileno());
				outlist.add(acc);
			}else {
				log.info("invalid vo=");
			}
		}
		return outlist;
	}
	
	private static void makeLastPrimary(List<Customeraccount> list) {
		if(list!=null && list.size()>0) {
			int l=list.size();
			Customeraccount c=null;
			for(int i=0;i<l;i++) {
				c=list.get(i);
				c.setDefaccount(0);
			}
			c.setDefaccount(1);
		}
	}
	
	
	private static Integer defaultActiveCaid(List<Customeraccount> list) {
		Integer i=null;
		if(list!=null && list.size()>0) {
			for(Customeraccount c:list) {
				if(c.getDefaccount()!=null && c.getStatus()!=null && c.getDefaccount()==1 && 1==c.getStatus()) {
					i=c.getCaid();
					break;
				}
			}
		}
		return i;
	}
	
	
	private static void checkIfNotMakeOnePrimary(List<Customeraccount> list) {
		boolean f = false;
		int cnt = 0;
		if (list != null && list.size() > 0) {
			for (Customeraccount c : list) {
				if (c.getStatus() != null && c.getDefaccount() == 1 && 1 == c.getStatus()) {
					if (cnt == 0) {
						cnt = 1;
						f = true;
					} else if (cnt == 1) {
						c.setDefaccount(0);
					}
				}
			}
			if(!f) {
				for (Customeraccount c : list) {
					if (c.getStatus() != null && 1 == c.getStatus()) {
						c.setDefaccount(1);
					}
				}
			}
		}
	}
	
	private static boolean  checkPrimaryAcc(List<Customeraccount> list) {
		boolean f = false;
		if (list != null && list.size() > 0) {
			for (Customeraccount c : list) {
				if (c.getStatus() != null && c.getDefaccount() == 1 && 1 == c.getStatus()) {
					f=true;
					break;
				}
			}
		}
		return f;
	}
	
	private static void mergeInExistingAcc(Customeraccount acc,Customeraccount updateDeatil) {
		try {
			acc.setAccPvdIfsc(updateDeatil.getIfsc());
			acc.setIfsc(updateDeatil.getIfsc());
			acc.setBankname(updateDeatil.getBankname());
			if(updateDeatil.getStatus()!=null && updateDeatil.getStatus()!=0) {
				acc.setStatus(updateDeatil.getStatus());	
			}else {
				acc.setStatus(ConstantI.NORMAL_ACCOUNT);	
			}
			if(StringUtils.isNotBlank(updateDeatil.getAcctype())){
				acc.setAcctype(updateDeatil.getAcctype().toUpperCase());
			}else {
				acc.setAcctype("DEFAULT");	
			}
			acc.setDefaccount(updateDeatil.getDefaccount());
			if(updateDeatil.getDefaccount()!=null) {
				acc.setDefaccount(updateDeatil.getDefaccount());
			}
			acc.setName(updateDeatil.getName());
			acc.setCustcode(updateDeatil.getCustcode());
		}catch (Exception e) {
		 
		}
	}
}
