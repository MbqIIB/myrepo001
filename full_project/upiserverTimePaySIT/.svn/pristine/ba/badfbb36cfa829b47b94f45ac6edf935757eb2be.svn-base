package com.npst.upiserver.service.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dao.ReqRespAuthMandateDao;
import com.npst.upiserver.dto.MerchantsTxnDto;
import com.npst.upiserver.dto.Message;
import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.entity.CustomerTxns;
import com.npst.upiserver.entity.MandatesHistoryEntity;
import com.npst.upiserver.entity.ReqRespAuthMandateEntity;
import com.npst.upiserver.npcischema.ExpireRuleConstant;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.ReqAuthMandate;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.RespMandate;
import com.npst.upiserver.npcischema.RulesType.Rule;
import com.npst.upiserver.service.NotificationService;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.Util;

@Component
public class NotificationServiceImpl implements NotificationService {
	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	private static final String collect_sms_dt_pattern = Util.getProperty("COLLECT_SMS_DATE_F_EXPIREAFTER");
	private static String M_TXN_QUEUE = Util.getProperty("MERCHANT_TXN_QUEUE");
	private static final MessageProperties msgProp = new MessageProperties();
	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private RabbitTemplate rmqTemplate;
	
	@Autowired
	private ReqRespAuthMandateDao reqRespAuthMandateDao;

	public NotificationServiceImpl() {
		msgProp.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
	}

	@Override
	public void sendString(String message, String queueName) {
		try {
			org.springframework.amqp.core.Message msg = new org.springframework.amqp.core.Message(message.getBytes(),
					msgProp);
			rmqTemplate.send(queueName, msg);
		} catch (Exception e) {
			log.error("error while send message={}", message);
			log.error("error {}", e);
			ErrorLog.sendError(e, NotificationServiceImpl.class);
		}

	}

	@Override
	public void convertInJsonAndSend(Object message, String queueName) {
		try {
			// will call separate pool
			rmqTemplate.convertAndSend(queueName, message);
		} catch (Exception e) {
			log.error("error while send message={}", JsonMan.getJSONStr(message));
			log.error("error {}", e);
			ErrorLog.sendError(e, NotificationServiceImpl.class);
		}

	}

	@Override
	public void sendNotification(Message message) {
		convertInJsonAndSend(message, ConstantI.NOTIFICATION_QUEUE);
	}

	@Override
	public void sendNotification(String mobileno, String osType, String message, String module) {
		try {
			if (message == null || ConstantI.CONST_BLANK.equalsIgnoreCase(message)) {
				log.info("message blank not sending notification");
				return;
			}
			Message notification = new Message();
			notification.setMobileNo(mobileno);
			notification.setType(osType);
			notification.setTitle(ConstantI.UPI);
			notification.setMessage(message);
			notification.setModule(module);
			convertInJsonAndSend(notification, ConstantI.NOTIFICATION_QUEUE);
		} catch (Exception e) {
			log.error("error in send sendNotification {}", e);
			ErrorLog.sendError(e, NotificationServiceImpl.class);
		}

	}

	@Override
	public void sendNotification(String mobileno, String osType, String message, String module, String sms) {
		try {
			if (message == null || ConstantI.CONST_BLANK.equals(message)) {
				log.info("message blank not sending notification");
				return;
			}
			Message notification = new Message();
			notification.setMobileNo(mobileno);
			notification.setType(osType);
			notification.setTitle(sms);
			notification.setMessage(message);
			notification.setModule(module);
			log.info("Send Request to Notification ",notification);
			convertInJsonAndSend(notification, ConstantI.NOTIFICATION_QUEUE);
		} catch (Exception e) {
			log.error("error in sendNotification {}", e);
			ErrorLog.sendError(e, NotificationServiceImpl.class);
		}

	}

	@Override
	public void sendSMS(Message message) {
		convertInJsonAndSend(message, ConstantI.SMS_QUEUE);
	}

	@Override
	public void sendSMS(String mobileno, String message, String msgType) {
		try {
			if (message == null || ConstantI.CONST_BLANK.equalsIgnoreCase(message)) {
				log.info("message blank not sending SMS");
				return;
			}
			Message msg = new Message();
			msg.setMobileNo(mobileno);
			msg.setType(msgType);
			msg.setMessage(message);
			String queueName = ConstantI.SMS_QUEUE;
			if (!ConstantI.SMS.equalsIgnoreCase(msgType)) {
				queueName = ConstantI.OTP_QUEUE;
			}
			convertInJsonAndSend(msg, queueName);
		} catch (Exception e) {
			log.error("error in sendSMS {}", e);
			ErrorLog.sendError(e, NotificationServiceImpl.class);
		}

	}

	@Override
	public void sendOTP(Message message) {
		convertInJsonAndSend(message, ConstantI.OTP_QUEUE);
	}

	@Override
	public void sendMerchantTxn(MerchantsTxnDto merchantsTxnDto) {
		convertInJsonAndSend(merchantsTxnDto, M_TXN_QUEUE);
	}

	@Override
	public void sendNoti(ReqAuthDetails reqAuthDetails, RegDto regvpa) {
		try {
			Map<String, String> valuesMap = new HashMap<>();
			int ruleExpireAfter = 0;

			if (null != reqAuthDetails.getTxn().getRules()) {
				List<Rule> ruleList = reqAuthDetails.getTxn().getRules().getRule();
				for (int i = 0; i < ruleList.size(); i++) {

					if (ExpireRuleConstant.EXPIREAFTER.value().equalsIgnoreCase(ruleList.get(i).getName().toString())) {
						ruleExpireAfter = Integer.parseInt(ruleList.get(i).getValue());
					}
				}
			}
			Date collectTimeX = new Date();
			Date collectTime = new Date(collectTimeX.getTime() + (ruleExpireAfter * Constant.MILLIS_IN_MINUTE));
			valuesMap.put("payeeName", reqAuthDetails.getPayees().getPayee().get(0).getName());
			valuesMap.put("payeeVpa", reqAuthDetails.getPayees().getPayee().get(0).getAddr());
			valuesMap.put("amount", reqAuthDetails.getPayees().getPayee().get(0).getAmount().getValue());
			SimpleDateFormat collect_sms_sdf = new SimpleDateFormat(collect_sms_dt_pattern);
			valuesMap.put("expireTime", collect_sms_sdf.format(collectTime));
			String sms = Util.getMessageProperty("COLLECT_RECEIVE");

			JSONObject info = new JSONObject();
			info.put("type", reqAuthDetails.getTxn().getType().toString());
			info.put("payerName", reqAuthDetails.getPayer().getName());
			info.put("payerAddr", reqAuthDetails.getPayer().getAddr());
			List<PayeeType> payees = reqAuthDetails.getPayees().getPayee();
			for (PayeeType payee : payees) {
				info.put("payeeName", payee.getName());
				info.put("payeeAddr", payee.getAddr());
			}
			info.put("amount", reqAuthDetails.getPayer().getAmount().getValue());
			info.put("txnId", reqAuthDetails.getTxn().getId());
			info.put("msgId", reqAuthDetails.getHead().getMsgId());
			info.put("note", reqAuthDetails.getTxn().getNote());
			info.put("custRef", reqAuthDetails.getTxn().getCustRef());
			if (sms != null && !sms.equalsIgnoreCase("")) {
				sms = MessageFormat.format(sms, valuesMap.get("payeeName"), valuesMap.get("payeeVpa"),
						valuesMap.get("amount"), valuesMap.get("expireTime"));
				log.info("Final message for notification is as ", sms);
				sendSMS(regvpa.getMobno(), sms, "SMS");
				regvpa.setAppos(regvpa.getAppos().startsWith(ConstantI.ANDROID) ? ConstantI.ANDROID
						: (regvpa.getAppos().startsWith(ConstantI.IOS) ? ConstantI.IOS : ConstantI.CONST_BLANK));
//
				String refUrl= null;
				if ("Y".equalsIgnoreCase(Util.getProperty("INVOICE_IN_THE_BOX"))) {
					if (null != reqAuthDetails.getTxn().getRefCategory() && reqAuthDetails.getTxn().getRefCategory().equalsIgnoreCase(ConstantI.REF_CATEGORY)) {
						if (null != reqAuthDetails.getTxn().getRefUrl()) {

							refUrl = reqAuthDetails.getTxn().getRefUrl();
						}
						info.put("hyperLinkUrl", refUrl);
						sendNotification(regvpa.getGcmtoken(), regvpa.getAppos(), info.toString(), ConstantI.Collect_REQ, sms);
					}
					log.info("refCategory not matched");
					//info.put("hyperLinkUrl", "https://www.adobe.com/content/dam/acom/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf");//Temprary for invoice test
					sendNotification(regvpa.getGcmtoken(), regvpa.getAppos(), info.toString(), ConstantI.Collect_REQ, sms);
				}

				//info.put("hyperLinkUrl", "https://www.adobe.com/content/dam/acom/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf");//Temprary for invoice test
				sendNotification(regvpa.getGcmtoken(), regvpa.getAppos(), info.toString(), ConstantI.Collect_REQ, sms);
			} else {
				log.info("No message configured to send notification");
			}
			// COLLECT_RECEIVE=You have received a collect request from
			// ${payeeName} (${payeeVpa}) for amount Rs. ${amount}. This
			// transaction will expire on ${expireTime}.

		} catch (Exception e) {
			log.error("error sendNoti {}", e);
			ErrorLog.sendError(e, NotificationServiceImpl.class);
		}

	}

	@Override
	public void sendNoti(CustomerTxns customerTxns) {
		try {
			String message = "";
			int status = 0;
			if (customerTxns.getStatus() != 1 && customerTxns.getStatus() != 2) {
				status = 3;
			} else {
				status = customerTxns.getStatus();
			}

			if (customerTxns.getTxnType() == 3 || customerTxns.getTxnType() == 8 || customerTxns.getTxnType() == 6) {

				String notiPat = formTxnTypeStr(customerTxns.getTxnType()) + "_" + status
						+ (status == 3 ? "_" + customerTxns.getErrorCode() : "");
				message = Util.getMessageProperty(notiPat);

				if ((null == message || "".equals(message)) && 2 != customerTxns.getStatus()) {
					message = Util.getMessageProperty(formTxnTypeStr(customerTxns.getTxnType()) + "_DEF_NOTI");
					message = MessageFormat.format(message, Util.maskNumber(customerTxns.getPayerAccNo()));

				}

				else if (message != null && !message.equalsIgnoreCase("")) {
					message = MessageFormat.format(message, Util.maskNumber(customerTxns.getPayerAccNo()));
				}
			} else if (customerTxns.getTxnType() == 5) {

				String notiPat = formTxnTypeStr(customerTxns.getTxnType()) + "_" + status
						+ (status == 3 ? "_" + customerTxns.getErrorCode() : "");
				message = Util.getMessageProperty(notiPat);

				if ((null == message || "".equals(message)) && 2 != customerTxns.getStatus()) {
					message = Util.getMessageProperty(formTxnTypeStr(customerTxns.getTxnType()) + "_DEF_NOTI");
					message = MessageFormat.format(message, customerTxns.getPayerBankName());
				}

				else if (message != null && !message.equalsIgnoreCase("")) {
					message = MessageFormat.format(message, customerTxns.getPayerBankName(),
							customerTxns.getPayerVpa());
				}
			} else if (customerTxns.getTxnType() == 1) {

				if (customerTxns.getPayeeVpa().endsWith(".ifsc.npci")) {

					customerTxns.setPayeeVpa(Util.maskNumber(
							customerTxns.getPayeeVpa().substring(0, customerTxns.getPayeeVpa().lastIndexOf("@")))
							+ customerTxns.getPayeeVpa().substring(customerTxns.getPayeeVpa().indexOf("@")));

				}

				if (customerTxns.getPayeeVpa().endsWith("mmid.npci")) {

					customerTxns.setPayeeVpa(
							customerTxns.getPayeeVpa().substring(0, customerTxns.getPayeeVpa().lastIndexOf("@"))
									+ customerTxns.getPayeeVpa().substring(customerTxns.getPayeeVpa().indexOf("@")));

				}

				String notiPat = formTxnTypeStr(customerTxns.getTxnType()) + "_" + status
						+ (status == 3 ? "_" + customerTxns.getErrorCode() : "");
				message = Util.getMessageProperty(notiPat);

				if ((null == message || "".equals(message)) && 2 != customerTxns.getStatus()) {
					message = Util.getMessageProperty(formTxnTypeStr(customerTxns.getTxnType()) + "_DEF_NOTI");
					message = MessageFormat.format(message, customerTxns.getPayeeVpa(), customerTxns.getAmount(),
							customerTxns.getTxncustRef());

				} else if (customerTxns.getStatus() == 2) {

					if (message != null && !message.equalsIgnoreCase("")) {
						message = MessageFormat.format(message, customerTxns.getPayerVpa(),
								customerTxns.getPayerBankName(), Util.maskNumber(customerTxns.getPayerAccNo()),
								customerTxns.getAmount(), customerTxns.getPayeeVpa(), customerTxns.getTxncustRef());
					}
				}

				else {

					if (message != null && !message.equalsIgnoreCase("")) {
						message = MessageFormat.format(message, customerTxns.getPayeeVpa(), customerTxns.getAmount(),
								customerTxns.getTxncustRef());
					}

				}

			} else if (customerTxns.getTxnType() == 2) {

				String notiPat = formTxnTypeStr(customerTxns.getTxnType()) + "_" + status
						+ (status == 3 ? "_" + customerTxns.getErrorCode() : "");
				message = Util.getMessageProperty(notiPat);

				if ((null == message || "".equals(message)) && 2 != customerTxns.getStatus()) {
					message = Util.getMessageProperty(formTxnTypeStr(customerTxns.getTxnType()) + "_DEF_NOTI");
				}

				else if (message != null && !message.equalsIgnoreCase("")) {
					message = MessageFormat.format(message, customerTxns.getPayeeVpa(),
							Util.maskNumber(customerTxns.getPayeeAccNo()), customerTxns.getAmount(),
							customerTxns.getPayerVpa(), customerTxns.getTxncustRef());

				}
			} else if (customerTxns.getTxnType() == 21) {

				String notiPat = formTxnTypeStr(customerTxns.getTxnType()) + "_" + status
						+ (status == 3 ? "_" + customerTxns.getErrorCode() : "");
				message = Util.getMessageProperty(notiPat);

				if ((null == message || "".equals(message)) && 2 != customerTxns.getStatus()) {
					message = Util.getMessageProperty(formTxnTypeStr(customerTxns.getTxnType()) + "_DEF_NOTI");

					message = MessageFormat.format(message, customerTxns.getPayerVpa(), customerTxns.getAmount(),
							customerTxns.getTxncustRef());

				} else if (customerTxns.getStatus() == 2) {

					if (message != null && !message.equalsIgnoreCase("")) {
						message = MessageFormat.format(message, customerTxns.getPayerVpa(),
								customerTxns.getPayerBankName(), Util.maskNumber(customerTxns.getPayerAccNo()),
								customerTxns.getAmount(), customerTxns.getPayeeVpa(), customerTxns.getTxncustRef());
					}
				}

				else {

					if (message != null && !message.equalsIgnoreCase("")) {
						message = MessageFormat.format(message, customerTxns.getPayerVpa(), customerTxns.getAmount(),
								customerTxns.getCollectExpiry());
					}

				}

			}

			log.debug("after getting message from file" + message);
			RegDto regDto = registrationDao.getGCMToken(customerTxns.getRegId());
			if (regDto != null) {
				String os = ConstantI.CONST_BLANK;
				if (regDto.getAppos().startsWith(ConstantI.ANDROID)) {
					os = ConstantI.ANDROID;
				} else if (regDto.getAppos().startsWith(ConstantI.IOS)) {
					os = ConstantI.IOS;
				}
				log.debug("On customerTxns", customerTxns.toString());
				if (StringUtils.isNotBlank(customerTxns.getPayerMobileNo()) && StringUtils.isNotBlank(message)) {
					sendSMS(regDto.getMobno(), message, ConstantI.SMS);
				}
				sendNotification(regDto.getGcmtoken(), os, message, "AS");
			}
		} catch (Exception e) {
			log.error("error in sendNoti customerTxns :{}", e);
			ErrorLog.sendError(e, NotificationServiceImpl.class);
		}
	}

	private static String formTxnTypeStr(Integer txnType) {
		if (3 == txnType) {
			return "BAL_ENQ";
		} else if (5 == txnType) {
			return "LIST_ACCOUNT";
		} else if (6 == txnType) {
			return "REG_MOB";
		} else if (7 == txnType) {
			return "REQ_OTP";
		} else if (8 == txnType) {
			return "SET_PIN";
		} else if (1 == txnType) {
			return "PAYER_PAY";
		} else if (2 == txnType) {
			return "PAYEE_PAY";
		} else if (21 == txnType) {
			return "PAYEE_COLLECT";
		} else if (22 == txnType) {
			return "PAYER_COLLECT";
		} else {
			return "NO";
		}
	}

	@Override
	public void sendNoti(ReqMandateConfirmation customerMandates,RegDto regvpa) {
		
		Map<String, String> valuesMap = new HashMap<>();
		valuesMap.put("amount", customerMandates.getMandate().getAmount().getValue());
		List<Ref> ref = customerMandates.getTxnConfirmation().getRef();
		log.debug("GetData for ref noti");
		String payeevpa=null;
		
		for (Ref ref2 : ref) {
			log.info("Ref type is{}",ref2.getType());
			if ("PAYEE".equalsIgnoreCase(ref2.getType().toString())) {
				log.info("inside loop resp pay umn no{}",ref2.getAddr());
				payeevpa=ref2.getAddr();
			}
		}
		ReqRespAuthMandateEntity reqrespauthmandates = reqRespAuthMandateDao.getByTxnId(customerMandates.getTxn().getId());
		String payervpa=null;
		if (null != reqrespauthmandates) {
			log.info("Noti Payer address is {}",reqrespauthmandates.getPayerAddr());
			payervpa=reqrespauthmandates.getPayerAddr();
			
		}
		log.info("for mandate noti payer and payee add is {}, and {}",payervpa,payeevpa);
		String sms = Util.getMessageProperty("CREATE_MANDATE");
		if (sms != null && !sms.equalsIgnoreCase("")) {
			sms = MessageFormat.format(sms, payervpa, valuesMap.get("amount"),
					payeevpa);
			log.info("Final message for notification is as ", sms);
			sendSMS(regvpa.getMobno(), sms, "SMS");
			
			regvpa.setAppos(regvpa.getAppos().startsWith(ConstantI.ANDROID) ? ConstantI.ANDROID
					: (regvpa.getAppos().startsWith(ConstantI.IOS) ? ConstantI.IOS : ConstantI.CONST_BLANK));
			
			sendNotification(regvpa.getGcmtoken(), regvpa.getAppos(), sms, "AS");
			
		}
		log.warn("Not implemented");
	}

	@Override
	public void sendNoti(ReqAuthMandate reqAuthMandate, RegDto regvpa) {
		try {
			Map<String, String> valuesMap = new HashMap<>();
			int ruleExpireAfter = 0;

			if (null != reqAuthMandate.getTxn().getRules()) {
				List<Rule> ruleList = reqAuthMandate.getTxn().getRules().getRule();
				for (int i = 0; i < ruleList.size(); i++) {

					if (ExpireRuleConstant.EXPIREAFTER.value().equalsIgnoreCase(ruleList.get(i).getName().toString())) {
						ruleExpireAfter = Integer.parseInt(ruleList.get(i).getValue());
					}
				}
			}
			//Date collectTimeX = new Date();
			//Date collectTime = new Date(collectTimeX.getTime() + (ruleExpireAfter * Constant.MILLIS_IN_MINUTE));
			valuesMap.put("payeeName", reqAuthMandate.getPayees().getPayee().get(0).getName());
			valuesMap.put("payeeVpa", reqAuthMandate.getPayees().getPayee().get(0).getAddr());
			valuesMap.put("amount", reqAuthMandate.getMandate().getAmount().getValue());
			//SimpleDateFormat collect_sms_sdf = new SimpleDateFormat(collect_sms_dt_pattern);
			//valuesMap.put("expireTime", collect_sms_sdf.format(collectTime));
			String sms = Util.getMessageProperty("MANDATE_RECEIVE");

			JSONObject info = new JSONObject();
			info.put("type", reqAuthMandate.getTxn().getType().toString());
			info.put("payerName", reqAuthMandate.getPayer().getName());
			info.put("payerAddr", reqAuthMandate.getPayer().getAddr());
			List<PayeeType> payees = reqAuthMandate.getPayees().getPayee();
			for (PayeeType payee : payees) {
				info.put("payeeName", payee.getName());
				info.put("payeeAddr", payee.getAddr());
			}
			info.put("amount", reqAuthMandate.getMandate().getAmount().getValue());
			info.put("txnId", reqAuthMandate.getTxn().getId());
			info.put("initiationMode", reqAuthMandate.getTxn().getInitiationMode());
			info.put("purpose", reqAuthMandate.getTxn().getPurpose());
			info.put("msgId", reqAuthMandate.getHead().getMsgId());
			info.put("note", reqAuthMandate.getTxn().getNote());
			info.put("custRef", reqAuthMandate.getTxn().getCustRef());
			if (sms != null && !sms.equalsIgnoreCase("")) {
				sms = MessageFormat.format(sms, valuesMap.get("payeeName"), valuesMap.get("payeeVpa"),
						valuesMap.get("amount"));
				log.info("Final message for notification is as ", sms);
				sendSMS(regvpa.getMobno(), sms, "SMS");
				regvpa.setAppos(regvpa.getAppos().startsWith(ConstantI.ANDROID) ? ConstantI.ANDROID
						: (regvpa.getAppos().startsWith(ConstantI.IOS) ? ConstantI.IOS : ConstantI.CONST_BLANK));
//
				String refUrl= null;
				if ("Y".equalsIgnoreCase(Util.getProperty("INVOICE_IN_THE_BOX"))) {
					if (null != reqAuthMandate.getTxn().getRefCategory() && reqAuthMandate.getTxn().getRefCategory().equalsIgnoreCase(ConstantI.REF_CATEGORY)) {
						if (null != reqAuthMandate.getTxn().getRefUrl()) {

							refUrl = reqAuthMandate.getTxn().getRefUrl();
						}
						info.put("hyperLinkUrl", "https://www.adobe.com/content/dam/acom/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf");
						//info.put("hyperLinkUrl", refUrl);
						sendNotification(regvpa.getGcmtoken(), regvpa.getAppos(), info.toString(), ConstantI.Collect_REQ, sms);
					}
					log.info("refCategory not matched");
					info.put("hyperLinkUrl", "https://www.adobe.com/content/dam/acom/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf");//Temprary for invoice test
					sendNotification(regvpa.getGcmtoken(), regvpa.getAppos(), info.toString(), ConstantI.Collect_REQ, sms);
				}

				info.put("hyperLinkUrl", "https://www.adobe.com/content/dam/acom/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf");//Temprary for invoice test
				sendNotification(regvpa.getGcmtoken(), regvpa.getAppos(), info.toString(), ConstantI.Collect_REQ, sms);
			} else {
				log.info("No message configured to send notification");
			}
			// COLLECT_RECEIVE=You have received a collect request from
			// ${payeeName} (${payeeVpa}) for amount Rs. ${amount}. This
			// transaction will expire on ${expireTime}.

		} catch (Exception e) {
			log.error("error sendNoti {}", e);
			ErrorLog.sendError(e, NotificationServiceImpl.class);
		}
		
	}

}
