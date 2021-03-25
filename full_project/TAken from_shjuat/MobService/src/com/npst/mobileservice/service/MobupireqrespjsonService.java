package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.CustomerMandateDao;
import com.npst.mobileservice.dao.MobupireqrespjsonHome;
import com.npst.mobileservice.dao.ReqrespauthdetailsPayeesHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.RabbitMQSend;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.CustomerMandatesEntity;
import com.npst.upi.hor.Customeraccount;
import com.npst.upi.hor.Mobupireqrespjson;
import com.npst.upi.hor.Reqrespauthdetails;
import com.npst.upi.hor.ReqrespauthdetailsPayees;

public class MobupireqrespjsonService {
	private static final Logger log = Logger.getLogger(MobupireqrespjsonService.class);
	ReqrespauthdetailsPayeesHome payeeHome = null;
	private static MobupireqrespjsonHome mobupireqrespjsonHome = null;
	List<ReqrespauthdetailsPayees> listPayees = null;
	private static ReqrespauthdetailsService reqrespauthdetailsService = null;
	private static MobupireqrespjsonidService mobupireqrespjsonidService = null;
	private static CustomeraccountHomeService customeraccountHomeService = null;
	List<Reqrespauthdetails> list = null;
	private static AcqTxnLimitService acqTxnLimitService;
	private static Mobupireqrespjson mobupireqrespjson=null;
	private static final String LIMIT200KMCC = Util.getProperty("LIMIT200KMCC");
	private static final long LIMIT100K = Long.parseLong(Util.getProperty("LIMIT100K"));
	private static final String PURPOSECODE200K = Util.getProperty("PURPOSECODE200K");
	CustomerMandateDao customerMandateDao=null;
	List<CustomerMandatesEntity> customerMandateslist=null;
	public Mobupireqrespjson findById(int parseInt) {
		log.info("id[" + parseInt + "]");
		if (null == mobupireqrespjsonHome) {
			mobupireqrespjsonHome = new MobupireqrespjsonHome();
		}
		return mobupireqrespjsonHome.findById(parseInt);
	}

	public RespJson save(String reqStr, ReqJson reqJson) {
		log.info("inside MobupireqrespjsonService class with request:{}"+reqJson.toString());
		RespJson respJson = new RespJson();
		try {
			if (null == mobupireqrespjsonHome) {
				mobupireqrespjsonHome = new MobupireqrespjsonHome();
			}
			if (null == mobupireqrespjsonidService) {
				mobupireqrespjsonidService = new MobupireqrespjsonidService();
			}
			if (null == customeraccountHomeService) {
				customeraccountHomeService = new CustomeraccountHomeService();
			}
			if (ConstantI.TXN_TYPE_SEND_MONEY.equals(reqJson.getTxnType())&& ConstantI.TXN_FLAG_SEND_MONEY.equals(reqJson.getTxnFlag())) {
				Customeraccount account = null;
				if (ConstantI.PAYER_ADDR_TYPE_VAL_ACCOUNT.equals(reqJson.getPayerAddrType())) {
					account = customeraccountHomeService.getActiveAccountByAccountAndVpaAndRegId(reqJson.getPayerAcNum(), Long.parseLong(reqJson.getRegId()), reqJson.getPayerAddr());
					if (account != null) {
						try {
							if (Double.parseDouble(reqJson.getPayerAmount()) > Double
									.parseDouble(account.getLimitamount())) {
								respJson.setMsgId(ConstantI.MSGID_FAIL);
								respJson.setMsg(ErrorCode.AcqErrorCode.MSG_ACCOUNT_LIMIT_EXCEEDED.getCode());
								return respJson;
							}
						} catch (NumberFormatException ex) {
						}
					}
				}
			}
			// new  10-04-2020   barun
			if (ConstantI.TXN_TYPE_SEND_MONEY.equals(reqJson.getTxnType())|| ConstantI.TXN_TYPE_COLLECT_MONEY.equals(reqJson.getTxnType())) {
				if (acqTxnLimitService == null) {
					acqTxnLimitService = AcqTxnLimitService.getInstance();
				}

				if(!reqJson.getInitiationMode().equalsIgnoreCase(ConstantI.MANDATE_INITMODE)||!reqJson.getInitiationMode().equalsIgnoreCase("13")){
				String errorCode = acqTxnLimitService.checkAcqLimit(reqJson.getMobileNo(),
						Integer.parseInt(Util.convertAmountInPaisa(reqJson.getPayerAmount())), reqJson.getTxnFlag(),
						reqJson.getTxnType(), reqJson.getTxnId());
				log.info("ERROR CODE FROM CHECK  ="+errorCode);
				if (!ConstantI.MSGID_SUCCESS.equals(errorCode)) {
					log.info("failed acquirer limit check ="+reqJson.getMobileNo());
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(errorCode);
					return respJson;
				}
				
			}
				
				//Check first if Mandate is Executed or not
				if(reqJson.getInitiationMode().equalsIgnoreCase(ConstantI.MANDATE_INITMODE)||reqJson.getInitiationMode().equalsIgnoreCase("13"))
				{
					String cusrtis="PAYEE";
					if(customerMandateDao==null) {
						customerMandateDao=new CustomerMandateDao();
					}
					customerMandateslist=customerMandateDao.getmandateofpayee(reqJson.getPayerAddr(),cusrtis);
					if(!customerMandateslist.isEmpty()) {
						log.info("Mandate collect req fount in DB for unn {} and payee"+reqJson.getPayerAddr());
						if(customerMandateslist.get(0).getStatus()==3) {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ConstantI.MANDATEREVOKOREXECUTED);
							return	respJson;
						}
						
						else if(!customerMandateslist.get(0).getPayeeVpa().equalsIgnoreCase(reqJson.getPayeeAddr())) {
							log.info("Mandate collect req fount in DB for unn {} and payee but payee is diffrent"+reqJson.getPayerAddr());
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ConstantI.NOMANDATESCREATED);
							return	respJson;
						}
					}else {
						log.info("Mandate collect req NOT fount in DB for unn {} and payee"+reqJson.getPayerAddr());
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ConstantI.NOMANDATESCREATED);
						return respJson;
					}
				}
				// CR - OC82 Advanced Limit
				
				// Get data from reqauthDetails
				
				if(ConstantI.TXN_FLAG_COLLECT_MONEY_ACCEPT.equalsIgnoreCase(reqJson.getTxnFlag())) {
					if (null == payeeHome) {
					payeeHome = new ReqrespauthdetailsPayeesHome();
					}
					listPayees = payeeHome.getPayee(reqJson.getTxnId());
					reqJson.setPayeeCode(listPayees.get(0).getPayeeCode());
					reqJson.setPayeeAddr(listPayees.get(0).getPayeeType());
					//String isTxnAllowed = isTxnAmountAllowed(reqJson.getPayeeCode(),reqJson.getPayeeAddr(),"",Long.parseLong(Util.convertAmountInPaisa(reqJson.getPayerAmount())));	
					String isTxnAllowed="0";
					if (!ConstantI.MSGID_SUCCESS.equals(isTxnAllowed)) {
					log.info("failed acquirer Advanced Limit {} =  {}"+reqJson.getMobileNo()+ reqJson.getPayerAmount());
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(isTxnAllowed);
					return respJson;
				}
			}
		}		
			if(mobupireqrespjson==null) {
				mobupireqrespjson = new Mobupireqrespjson();
			}
			mobupireqrespjson.setFlag(ConstantI.UPI_REQ_INSERT_D_Q);
			mobupireqrespjson.setReq(reqStr);
			mobupireqrespjson.setReqDate(new Date());
			mobupireqrespjson.setType(reqJson.getTxnFlag());
			mobupireqrespjson.setSource(reqJson.getSource());
			mobupireqrespjson.setNode(reqJson.getNode());
			mobupireqrespjson.setTpId(reqJson.getTpId());//add for timepay request
			log.info("Entering save request into DB {} =  {}"+mobupireqrespjson.toString());
			mobupireqrespjsonHome.save(mobupireqrespjson);
			mobupireqrespjsonidService.save(mobupireqrespjson);
			log.info("Request sending into RabbitMQ{} =  {}"+mobupireqrespjson.toString());
			RabbitMQSend.send(JSONConvert.getJsonStr(mobupireqrespjson), Util.getProperty("MOBREQ"));
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setIdPk(mobupireqrespjson.getIdPk() + "");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("Some error occured:{}"+e);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
		}

		return respJson;
	}
	//	CR - OC82
	private String isTxnAmountAllowed(final String payeeMCC_Code,final String payeeVPA,final String purposeCode,final long txnAmount) {
		String respCode = "0";
		if (txnAmount > LIMIT100K) {
			if (LIMIT200KMCC.contains(payeeMCC_Code) || PURPOSECODE200K.equalsIgnoreCase(purposeCode)) {
				log.info("Payee code or Purpose Varifyed "+payeeMCC_Code+purposeCode);
				respCode = ConstantI.MSGID_SUCCESS;
			}else if(isVerifiedMerchant(payeeVPA)) {
				respCode = ConstantI.MSGID_SUCCESS;
			}else {
				respCode = Util.getProperty("message_txn_amt_greater_than_allowable_limit");
			}
		} else {
			respCode = ConstantI.MSGID_SUCCESS;
		}
		return respCode;
	}

	private boolean isVerifiedMerchant(final String merchantVPA) {
		boolean isVerifiedMerchant =true;
		return isVerifiedMerchant;
		
		//FROM List VAE
	}
}
