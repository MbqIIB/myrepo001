package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.ComplaintHome;
import com.npst.mobileservice.dao.CustomerTxnsHome;
import com.npst.mobileservice.object.ComplaintVO;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.Complaint;

/**
 * @author Sumaiya Ahmad
 * @since 6/07/2017
 */

public class ComplaintHomeService {
	private static final Logger log = Logger.getLogger(ComplaintHomeService.class);
	CustomerTxnsHome customerTxnsHome = null;

	public RespJson addComplaint(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		try {
			ComplaintHome customerQueryHomeDao = new ComplaintHome();
			Complaint complaint = new Complaint();
			complaint.setAdjAmount(reqJson.getAmount());
			complaint.setComplaintDate(new java.util.Date());
			complaint.setCustRef(reqJson.getCustRef());
			complaint.setFlag("BB");
			complaint.setMobileNo(reqJson.getMobileNo());
			complaint.setName(reqJson.getName());
			if ("entity".equalsIgnoreCase(reqJson.getPayeeType()))
			{
				if ("SUCCESS".equalsIgnoreCase(reqJson.getStatus())) {
					complaint.setReasonCd("U008");
				} else {
					complaint.setReasonCd("U009");
				}
			}
			if ("person".equalsIgnoreCase(reqJson.getPayeeType())) {
				if ("SUCCESS".equalsIgnoreCase(reqJson.getStatus())) {
					complaint.setReasonCd("U010");
				} else {
					complaint.setReasonCd("U005");
				}
			}
			complaint.setTxndate(reqJson.getTxnDate());
			complaint.setTxnId(reqJson.getTxnNo());
			complaint.setVirtualId(reqJson.getVirtualId());
			complaint.setRemark(reqJson.getComplaintDesc());
			complaint.setStatus(ConstantI.OPEN);
			complaint.setApiType(Integer.parseInt(reqJson.getType()));
			complaint.setDisputeType(reqJson.getDisputeType());
			if (!customerQueryHomeDao.addComplaint(complaint)) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.COMPLAINT_ALREADY_ADDED.getCode());
				return respJson;
			}
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.COMPLAINT_ADD_SUCCESS.getCode());
		} catch (

		Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		log.info("Return str[" + respJson + "]");
		return respJson;
	}

	public RespJson getComplaint(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		try {
			ComplaintHome customerQueryHomeDao = new ComplaintHome();
			List<Complaint> listComplaint = customerQueryHomeDao.getComplaint(reqJson);
			List<ComplaintVO> compaintVO = new ArrayList<>();
			if (null != listComplaint && listComplaint.size() > 0) {
				for (Complaint complaint : listComplaint) {
					compaintVO.add(new ComplaintVO(complaint));
				}
				respJson.setCompaintVO(compaintVO);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
				return respJson;
			}
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		log.info("Return str[" + respJson + "]");
		return respJson;
	}
}
