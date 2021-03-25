package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.CustomerTxnsHome;
import com.npst.mobileservice.object.ComplaintVO;
import com.npst.mobileservice.object.CustomertxnsVO;
import com.npst.mobileservice.object.MasterConfigVO;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;

public class CustomerTxnsService {
	private static final Logger log = Logger.getLogger(CustomerTxnsService.class);
	CustomerTxnsHome customerTxnsHome = null;
	
	private static final Integer TXNDATEDIFF = Util.getProperty("TXNDATEDIFF") != null
			? Integer.parseInt(Util.getProperty("TXNDATEDIFF"))
			: 7;
	private static final int pageSize = Util.getProperty("pageSize") != null
			? Integer.parseInt(Util.getProperty("pageSize"))
			: 10;
	SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	private static final String MAX_TXN_AMOUNT_REPORT = Util.getProperty("maxAmtLimitReport") != null ? Util.getProperty("maxAmtLimitReport")
			: "100001";
	private static final String MIN_TXN_AMOUNT_REPORT = Util.getProperty("minAmtLimitReport") != null ? Util.getProperty("minAmtLimitReport")
			: "0";

	
	/*public static void main(String[] args) {
	new CustomerTxnsService().histotyByDate(
	"{\"eDate\":\"11/07/2018\",\"frmAmount\":\"1\",\"historyTo\":\"1\",\"mobileNo\":\"918655158439\",\"regId\":\"55\",\"sDate\":\"11/07/2018\",\"status\":0,\"toAmount\":\"100000\",\"txnStatus\":\"\",\"type\":0,\"virtualId\":\"8655158439@cosmos\"}");
	 }*/

	public RespJson histoty(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.FAILURE_STRING);
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == customerTxnsHome) {
				customerTxnsHome = new CustomerTxnsHome();
			}
			int historyTo = 0;
			int historyFrom = 0;

			final String historyFromStr = reqJson.getHistoryFrom();
			final String historyToSt = reqJson.getHistoryTo();
			final Integer historyToStr = ((Integer.parseInt(historyToSt) - 1) * pageSize);
			try {
				if (null != historyToStr)
					historyTo = historyToStr;
				if (null != historyFromStr)
					historyFrom = Integer.parseInt(historyFromStr);

			} catch (NumberFormatException ex) {

			}
			;
			List<Object[]> results = customerTxnsHome.history(reqJson.getRegId(), historyFrom, historyTo, pageSize,
					reqJson.getTxnType());
			if (results.isEmpty()) {
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
				respJson.setMsgId(ConstantI.MSGID_FAIL);
			} else {
				List<CustomertxnsVO> listCustomertxns = new ArrayList<>();
				CustomertxnsVO customertxnsVO = null;
				for (Object obj[] : results) {
					customertxnsVO = custTransWithComplaints(obj);
					listCustomertxns.add(customertxnsVO);
				}
				for (CustomertxnsVO customertxnsVO2 : listCustomertxns) {
					if ((!"".equals(customertxnsVO2.getCollectExpiry()))
							&& null != customertxnsVO2.getCollectExpiry()) {
						long reqDateInMins = dtFormat.parse(customertxnsVO2.getReqDate()).getTime();

						Date collectTime = new Date(reqDateInMins
								+ (Integer.parseInt(customertxnsVO2.getCollectExpiry()) * ConstantI.MILLIS_IN_MINUTE));
						customertxnsVO2.setCollectExpiry(dtFormat.format(collectTime));
					}
				}
				Map<String, String> disputeMap = createDisputeMap();
				respJson.setDisputeTypes(disputeMap);
				respJson.setCustomertxnsVO(listCustomertxns);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ConstantI.ERROR_STRING);
		}
		return respJson;
	}

	private Map<String, String> createDisputeMap() {
		Map<String, String> disputeMap = new HashMap<>();
		MasterConfigService configService = new MasterConfigService();
		List<MasterConfigVO> configVOs = configService.findByCodeType(ConstantI.DISPUTE);
		for (MasterConfigVO configVO : configVOs) {
			disputeMap.put(configVO.getCode(), configVO.getValue());
		}
		return disputeMap;
	}

	private CustomertxnsVO custTransWithComplaints(Object[] obj) {
		CustomertxnsVO customertxnsVOs = new CustomertxnsVO();
		customertxnsVOs.setCustomerTxnsId(null == obj[0] ? null : (Integer) obj[0]);
		customertxnsVOs.setRegvpaId(null == obj[19] ? null : (Integer) obj[19]);
		customertxnsVOs.setTxnNote(null == obj[24] ? null : filterDataForTxnHistory(obj[24]));
		customertxnsVOs.setTxnType(null == obj[25] ? null : (Integer) obj[25]);
		customertxnsVOs.setTxnId(null == obj[23] ? null : filterDataForTxnHistory(obj[23]));
		customertxnsVOs.setTxncustRef(null == obj[26] ? null : filterDataForTxnHistory(obj[26]));
		customertxnsVOs.setCollectExpiry(null == obj[2] ? null : filterDataForTxnHistory(obj[2]));
		customertxnsVOs.setCustomerHistory(null == obj[3] ? null : filterDataForTxnHistory(obj[3]));
		customertxnsVOs.setStatus(null == obj[22] ? null : (Integer) obj[22]);
		customertxnsVOs.setErrorCode(null == obj[4] ? null : filterDataForTxnHistory(obj[4]));
		customertxnsVOs.setPayerVpa(null == obj[18] ? null : filterDataForTxnHistory(obj[18]));
		customertxnsVOs.setPayerName(null == obj[17] ? null : filterDataForTxnHistory(obj[17]));
		customertxnsVOs.setPayerAccNo(null == obj[14] ? null : filterDataForTxnHistory(obj[14]));
		customertxnsVOs.setPayerAccIFSC(null == obj[13] ? null : filterDataForTxnHistory(obj[13]));
		customertxnsVOs.setPayerMobileNo(null == obj[16] ? null : filterDataForTxnHistory(obj[16]));
		customertxnsVOs.setPayerMMID(null == obj[15] ? null : filterDataForTxnHistory(obj[15]));
		customertxnsVOs.setPayerAcType(null == obj[12] ? null : filterDataForTxnHistory(obj[12]));
		customertxnsVOs.setPayeeName(null == obj[10] ? null : filterDataForTxnHistory(obj[10]));
		customertxnsVOs.setPayeeVpa(null == obj[11] ? null : filterDataForTxnHistory(obj[11]));
		customertxnsVOs.setPayeeAccNo(null == obj[7] ? null : filterDataForTxnHistory(obj[7]));
		customertxnsVOs.setPayeeAccIFSC(null == obj[6] ? null : filterDataForTxnHistory(obj[6]));
		customertxnsVOs.setPayeeMobileNo(null == obj[9] ? null : filterDataForTxnHistory(obj[9]));
		customertxnsVOs.setPayeeMMID(null == obj[8] ? null : filterDataForTxnHistory(obj[8]));
		customertxnsVOs.setPayeeAcType(null == obj[5] ? null : filterDataForTxnHistory(obj[5]));
		customertxnsVOs.setAmount(null == obj[1] ? null : filterDataForTxnHistory(obj[1]));
		customertxnsVOs
				.setReqDate(null == obj[20] ? null : obj[20].toString().substring(0, obj[20].toString().length() - 2));
		customertxnsVOs
				.setRespDate(null == obj[21] ? null : obj[21].toString().substring(0, obj[21].toString().length() - 2));
		customertxnsVOs.setComplaintFlag("0");
		customertxnsVOs.setPayeeType(null == obj[27] ? null : filterDataForTxnHistory(obj[27]));
		if (!(null == obj[28])) {
			customertxnsVOs.setComplaintFlag("1");
			customertxnsVOs = createComplaint(customertxnsVOs, obj);
		}
		return customertxnsVOs;
	}

	private String filterDataForTxnHistory(Object obj) {
		if (((String) obj).trim().length() == 0)
			return "";
		else
			return (String) obj;
	}

	private CustomertxnsVO createComplaint(CustomertxnsVO customertxnsVOs, Object[] obj) {

		String status = null != obj[40] ? getMasterValue(ConstantI.COMP_STATUS, obj[40].toString()) : "";
		String disputeType = null != obj[40] ? getMasterValue(ConstantI.DISPUTE, obj[45].toString()) : "";
		ComplaintVO complaintVO = new ComplaintVO();
		complaintVO.setAdjAmount(null == obj[29] ? null : filterDataForTxnHistory(obj[29]));
		complaintVO.setApiType(null == obj[30] ? null : Integer.parseInt(obj[30].toString()));
		complaintVO.setBankAdjRefNo(null == obj[28] ? null : (Integer) obj[28]);
		complaintVO.setComplaintsDate(
				null == obj[31] ? null : obj[31].toString().substring(0, obj[31].toString().length() - 2));
		complaintVO.setCustRef(null == obj[32] ? null : filterDataForTxnHistory(obj[32]));
		complaintVO.setFlag(null == obj[33] ? null : filterDataForTxnHistory(obj[33]));
		complaintVO.setMobileNo(null == obj[34] ? null : filterDataForTxnHistory(obj[34]));
		complaintVO.setName(null == obj[35] ? null : filterDataForTxnHistory(obj[35]));
		complaintVO.setPspStatus(null == obj[36] ? null : filterDataForTxnHistory(obj[36]));
		complaintVO.setReasonCd(null == obj[37] ? null : filterDataForTxnHistory(obj[37]));
		complaintVO.setReasonCode(null == obj[38] ? null : filterDataForTxnHistory(obj[38]));
		complaintVO.setRemark(null == obj[39] ? null : filterDataForTxnHistory(obj[39]));
		complaintVO.setStatus(status);
		complaintVO
				.setTxndate(null == obj[43] ? null : obj[43].toString().substring(0, obj[43].toString().length() - 2));
		complaintVO.setTxnId(null == obj[41] ? null : filterDataForTxnHistory(obj[41]));
		complaintVO.setVirtualId(null == obj[44] ? null : filterDataForTxnHistory(obj[44]));
		complaintVO.setDisputeType(disputeType);
		customertxnsVOs.setComplaintVo(complaintVO);
		return customertxnsVOs;
	}

	/*public static void main(String[] args) {
		new CustomerTxnsService().histoty("{\"historyTo\":\"1\",\"mobileNo\":\"917065187771\",\"regId\":\"1\"}");
	}*/

	public RespJson histotyByDate(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.FAILURE_STRING);
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == customerTxnsHome) {
				customerTxnsHome = new CustomerTxnsHome();
			}
			String streDate = reqJson.geteDate();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date eDate = null;
			Date d = new Date();
			eDate = streDate != null ? format.parse(streDate) : d;
			String strsDate = reqJson.getsDate();
			Date sDate = null;
			sDate = strsDate != null ? format.parse(strsDate) : Util.dateStartingHours(d, TXNDATEDIFF);
			eDate = Util.dateEndingHours(eDate);
			String frmAmnt = !Util.isNullOrEmpty(reqJson.getFrmAmount()) ? reqJson.getFrmAmount() : MIN_TXN_AMOUNT_REPORT;
			String toAmnt = !Util.isNullOrEmpty(reqJson.getToAmount()) ? reqJson.getToAmount() : MAX_TXN_AMOUNT_REPORT;
			String txnStatus = !Util.isNullOrEmpty(reqJson.getTxnStatus()) ? reqJson.getTxnStatus() : null;
			int historyTo = 0;
			final String historyToSt = reqJson.getHistoryTo();
			final Integer historyToStr = ((Integer.parseInt(historyToSt) - 1) * 10);
			if (null != historyToStr)
				historyTo = historyToStr;

			List<Object[]> results = customerTxnsHome.historyByDate(reqJson.getRegId(), sDate, eDate, frmAmnt, toAmnt,
					txnStatus, historyTo, pageSize, reqJson.getTxnType(), reqJson.getVirtualId());
			if (0 == results.size()) {

				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			} else {
				List<CustomertxnsVO> listCustomertxns = new ArrayList<>();
				CustomertxnsVO customertxnsVO = null;
				for (Object obj[] : results) {
					customertxnsVO = custTransWithComplaints(obj);
					listCustomertxns.add(customertxnsVO);
				}
				Map<String, String> disputeMap = createDisputeMap();
				respJson.setDisputeTypes(disputeMap);
				for (CustomertxnsVO customertxnsVO2 : listCustomertxns) {
					if ((!"".equals(customertxnsVO2.getCollectExpiry()))
							&& null != customertxnsVO2.getCollectExpiry()) {
						long reqDateInMins = dtFormat.parse(customertxnsVO2.getReqDate()).getTime();

						Date collectTime = new Date(reqDateInMins
								+ (Integer.parseInt(customertxnsVO2.getCollectExpiry()) * ConstantI.MILLIS_IN_MINUTE));
						customertxnsVO2.setCollectExpiry(dtFormat.format(collectTime));
					}
				}

				respJson.setCustomertxnsVO(listCustomertxns);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ConstantI.ERROR_STRING);
		}
		return respJson;
	}

	private String getMasterValue(String codeType, String code) {
		MasterConfigService configService = new MasterConfigService();
		List<MasterConfigVO> configVOs = configService.findByCodeType(codeType);
		for (MasterConfigVO configVO : configVOs) {
			if (configVO.getCode().equals(code))
				return configVO.getValue();
		}
		return code;
	}
}
