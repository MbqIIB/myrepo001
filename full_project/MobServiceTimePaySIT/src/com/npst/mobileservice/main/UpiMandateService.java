package com.npst.mobileservice.main;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.service.MandateMobupireqrespjsonidService;
import com.npst.mobileservice.service.MobUpiReqRespMandateJsonService;
import com.npst.mobileservice.service.MobupireqrespjsonService;
import com.npst.mobileservice.service.MobupireqrespjsonidService;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.mobileservice.util.Validations;
import com.npst.upi.hor.MobMandateReqRespJsonEntity;
import com.npst.upi.hor.MobMandateReqRespJsonIdEntity;
import com.npst.upi.hor.Mobupireqrespjson;
import com.npst.upi.hor.Mobupireqrespjsonid;

@Path(ConstantI.BASE_URL_MANDATE_UPI)
public class UpiMandateService {
	private static final Logger	log	= Logger.getLogger(UpiMandateService.class);
	final static String mandateAmountMax = Util.getProperty("mandate.amount.max");
	private static MobUpiReqRespMandateJsonService  		mobUpiReqRespMandateJsonService=null;
	private static MandateMobupireqrespjsonidService		mandateMobupireqrespjsonidService	= null;
	private static MobupireqrespjsonService		mobupireqrespjsonService	= null;
	private static MobupireqrespjsonidService	mobupireqrespjsonidService	= null;

	
	@POST
	@Path(ConstantI.BASE_URL_MANDATE_REQUEST)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String request(String reqStr) {
		// Remove Static.from code
		log.info("Execution start of request()");
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.MSGID_SUCCESS);
		log.info("Handling req for request"+reqStr);
		ReqJson reqJson = new ReqJson();
		try {
			if(mobUpiReqRespMandateJsonService==null) {
				mobUpiReqRespMandateJsonService=new MobUpiReqRespMandateJsonService();
			}
			
			if (null == mobupireqrespjsonService) {
				mobupireqrespjsonService = new MobupireqrespjsonService();
			}
			reqJson = JSONConvert.getReqJson(reqStr);
			
			log.info("Txn Flag is:{}"+reqJson.getTxnFlag());
			if("LISTKEYS".equalsIgnoreCase(reqJson.getTxnFlag())){
				log.info("inside LIST KYES");
				respJson = mobupireqrespjsonService.save(reqStr, reqJson);
				respJson.setTxnType(reqJson.getTxnType());
			}
			else {
			log.info("inside Mandate block with request:{}"+reqJson.toString());
			if(reqJson.getMandate().getMandateAmountValue() != null && Double.parseDouble(reqJson.getMandate().getMandateAmountValue()) > Double.parseDouble(mandateAmountMax)) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MANDATE_AMOUNT_MAX.getCode());
				return JSONConvert.getJsonStr(respJson);
			}
			if (Validations.jsonValidation(reqJson)) {
				respJson = mobUpiReqRespMandateJsonService.save(reqStr, reqJson);
				respJson.setTxnType(reqJson.getTxnType());
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.INVALID_UPI_REQ.getCode());
			}
			}
		} catch (Exception ex) {
			log.info(ex.getMessage());
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setReqId(reqJson.getReqId());
		return JSONConvert.getJsonStr(respJson);
	}
	
	

	
	@POST
	@Path(ConstantI.BASE_URL_MANDATE_RESPONSE)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String getResponse(String reqStr) {
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.MSGID_SUCCESS);
		int flag=1;
		ReqJson reqJson = JSONConvert.getReqJson(reqStr);
		try {
			respJson.setReqId(reqJson.getReqId());
			log.info("handling request of getResponse for request"+reqJson);
			respJson.setTxnType(reqJson.getTxnType());
			respJson.setIdPk(reqJson.getIdPk() + "");
			if (null == mandateMobupireqrespjsonidService) {
				mandateMobupireqrespjsonidService = new MandateMobupireqrespjsonidService();
			}
			if("ListKeys".equalsIgnoreCase(reqJson.getTxnType())){
				if (null == mobupireqrespjsonidService) {
					mobupireqrespjsonidService = new MobupireqrespjsonidService();
				}
				Mobupireqrespjsonid mobupireqrespjsonid = mobupireqrespjsonidService.findById(Integer.parseInt(reqJson.getIdPk()));
				log.info("DATA {}"+ mobupireqrespjsonid.toString());
				log.info("ID{} : "+Long.parseLong(reqJson.getIdPk()));
				flag = mobupireqrespjsonid.getFlag();
			}
			else {
				log.info("ID MANDATE{} : "+Long.parseLong(reqJson.getIdPk()));
				log.info("ID MANDATE {} "+reqJson.getIdPk());
				MobMandateReqRespJsonIdEntity mandateMobupireqrespjsonid= mandateMobupireqrespjsonidService.findById(Long.parseLong(reqJson.getIdPk()));
				log.info("Data recieve for mandateMobupireqrespjsonid :{}"+mandateMobupireqrespjsonid);
				flag = mandateMobupireqrespjsonid.getFlag();
			}
			log.info("Flag After getting Mobile UPI Resp :{}"+flag);
			if (ConstantI.UPI_REQ_RESP_D == flag) {
				if("ListKeys".equalsIgnoreCase(reqJson.getTxnType())){
					if (null == mobupireqrespjsonService) {
						mobupireqrespjsonService = new MobupireqrespjsonService();
					}
					Mobupireqrespjson instance = mobupireqrespjsonService.findById(Integer.parseInt(reqJson.getIdPk()));
					if (null != instance) {
						log.info("mobupireqrespjson {}"+instance);
						respJson = JSONConvert.getRespJson(instance.getResp());
					}
				}
				else {
				if (null == mobUpiReqRespMandateJsonService) {
					mobUpiReqRespMandateJsonService = new MobUpiReqRespMandateJsonService();
				}
				MobMandateReqRespJsonEntity instance = mobUpiReqRespMandateJsonService.findById(Integer.parseInt(reqJson.getIdPk()));
				if (null != instance) {
					log.info("mobupireqrespjson {}"+instance);
					respJson = JSONConvert.getRespJson(instance.getResp());
				}
				}
				
			} else if (1 == flag || 2 == flag) {
				log.info("Pending Response {}"+"");
				respJson.setMsg(ConstantI.MSGID_PENDING);
				respJson.setMsgId(ConstantI.MSGID_PENDING);
			} else {
				log.info("Failure Response {}"+"");
			}
		} catch (Exception ex) {
			log.info("Some thing went worng on get Response"+ex.getMessage(),ex);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		//return JSONConvert.getJsonStr(respJson);
		respJson.setReqId(reqJson.getReqId());
		String respback=JSONConvert.getJsonStr(respJson);
		return respback;
	}
	
	/*public static void main(String args[]) {
		//String s="{\"txnFlag\":\"REQMANDATE\",\"txnId\":\"CANSVVR4QOS7X1UGPY7JGUV444PL9T2C3HG\",\"txnNote\":\"yttt\",\"txnRefId\":\"CANCFB0F501864B45E086839F95EFED95EC\",\"txnRefUrl\":\"https:\\/\\/www.canarabank.in\\/\",\"txnType\":\"CREATE\",\"txnFlag\":\"REQMANDATE\",\"payerAmount\":\"1.00\",\"payerAddr\":\"7065187771@cnrb\",\"payerName\":\"CHANDAMAL\",\"payerSeqNum\":\"1\",\"payerType\":\"PERSON\",\"payerCode\":\"0000\",\"payerAcType\":\"SAVINGS\",\"payerAddrType\":\"ACCOUNT\",\"payerIfsc\":\"CNRB0000000\",\"payerAcNum\":\"0507101343559\",\"payerDeviceMobile\":\"+917065187771\",\"payerDeviceGeoCode\":\"28.584242,77.3136764\",\"payerDeviceLocation\":\"Noida\",\"payerDeviceIp\":\"10.3.2.47\",\"payerDeviceType\":\"MOB\",\"payerDeviceId\":\"5435cce7c65f5422\",\"payerDeviceOsType\":\"ANDROID 6.0.1\",\"payerDeviceAppId\":\"com.canarabank.mobility\",\"payerDeviceCapability\":\"520000020001000201301+917065187771\",\"regId\":\"1812971\",\"mobileNo\":\"+917065187771\",\"payeeAddr\":\"9911143801@cnrb\",\"payeeName\":\"KAMALA JAGADEESH\",\"payeeType\":\"PERSON\",\"payeeSeqNum\":\"1\",\"payeeCode\":\"0000\",\"initiationMode\":\"11\",\"purpose\":\"09\",\"mandate\":{\"mandateUmn\":\"\",\"mandateName\":\"tesy\",\"recurrencePattern\":\"Onetime\",\"recurrenceRuleType\":\"On\",\"mandateRevokeable\":\"Y\",\"mandateShareToPayee\":\"Y\",\"mandateAmountRule\":\"Max\",\"mandateAmountValue\":\"1.00\",\"mandateBlockFund\":\"Y\",\"mandateType\":\"CREATE\",\"validityStart\":\"11102019\",\"validityEnd\":\"12102019\"}}";
		String s="{\"txnFlag\":\"REQMANDATE\",\"txnId\":\"CANSVVR4QOS7X1UGPY7JGUV444PL9T2C3HG\",\"txnRefId\":\"CANSVVR4QOS7X1UGPY7JGUV444PL9T2C3HG\",\"txnRefUrl\":\"https:\\/\\/www.cosmosbank.in\",\"txnNote\":\"Weekly payment\",\"txnType\":\"CREATE\",\"initiationMode\":\"00\",\"txnInitiatedBy\":\"PAYEE\",\"payerAddr\":\"7678668996@cosmos\",\"payerSeqNum\":\"1\",\"payerType\":\"PERSON\",\"payerCode\":\"0000\",\"payerMobileNo\":\"9971345344\",\"payerName\":\"Rahul\",\"payerDeviceMobile\":\"9971345344\",\"payerDeviceGeoCode\":\"1234\",\"payerDeviceLocation\":\"Noida\",\"payerDeviceIp\":\"192.168.212.163\",\"payerDeviceType\":\"Mobile\",\"payerDeviceId\":\"000000000000001\",\"payerDeviceOsType\":\"Android\",\"payerDeviceAppId\":\"org.npci.upi.security.commonsapp\",\"payerDeviceCapability\":\"5200000200010004000639292929292\",\"payerDeviceTelecom\":\"\",\"payerIfsc\":\"COSB0000012\",\"payerAcType\":\"SAVINGS\",\"payerAcNum\":\"0410501017596\",\"payeeIfsc\":\"CNRB0000001\",\"payeeAddrType\":\"ACCOUNT\",\"payeeAcType\":\"SAVINGS\",\"payeeAcNum\":\"4793108000373\",\"payerAddrType\":\"\",\"payerMmid\":\"\",\"payerIin\":\"\",\"payerUidNum\":\"\",\"payerCardNum\":\"\",\"payeeAddr\":\"7906743578@cnrb\",\"payeeName\":\"rahul\",\"payeeSeqNum\":\"\",\"payeeType\":\"\",\"payeeCode\":\"\",\"mandate\":{\"mandateName\":\"Home Rent\",\"mandateUmn\":\"\",\"recurrencePattern\":\"ONETIME\",\"recurrenceRuleType\":\"ON\",\"mandateRevokeable\":\"N\",\"mandateShareToPayee\":\"N\",\"mandateType\":\"CREATE\",\"mandateBlockFund\":\"Y\",\"validityStart\":\"11122019\",\"validityEnd\":\"14122019\",\"mandateAmountRule\":\"EXACT\",\"mandateAmountValue\":\"1.00\"}}";
		String st=request(s);
		//String s="{\"idPk\":\"3269061\",\"txnType\":\"ListKeys\",\"mobileNo\":\"+917065187771\",\"regId\":\"1812971\"}";
		//String st=getResponse(s);
	}*/
	
	
	
}