package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.BeneficiaryDao;
import com.npst.mobileservice.object.BeneficiaryDto;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.DecryptionUtility;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.Beneficiary;

public class BeneficiaryService {
	private static final Logger		log				= Logger.getLogger(BeneficiaryService.class);
	private static BeneficiaryDao	beneficiaryDao	= null;
	
	DateFormat						dfDob			= new SimpleDateFormat("dd/MM/yyyy");
	
	public RespJson addBeneficiary(ReqJson reqJson) {
		Beneficiary beneficiary = null;
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		if (null == beneficiaryDao) {
			beneficiaryDao = new BeneficiaryDao();
		}
		try {
			beneficiary = new Beneficiary();
			beneficiary.setRegid(Integer.parseInt(reqJson.getRegId()));
			beneficiary.setStatus(ConstantI.ACTIVE);
			if (null != reqJson.getPayeeaddr() && null != reqJson.getRegId()) {
				if (null != reqJson.getPayeeaddr()) {
					List<Beneficiary> results = beneficiaryDao.getBeneficiary(reqJson, "VPA");
					if (results.size() == 1) {
						if (results.get(0).getStatus() == ConstantI.INACTIVE) {
							respJson = updateBeneficiary(results.get(0), reqJson, ConstantI.BENEFICIERY_VPA);
							return respJson;
						} else {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_ADDED_BNF.getCode());
							return respJson;
						}
					}
					respJson = addBeneficiary(beneficiary, reqJson, ConstantI.BENEFICIERY_VPA);
					return respJson;
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.BLANK_PAYEE_NAME.getCode());
					return respJson;
				}
			} else if (null != reqJson.getPayeeaccno() && null != reqJson.getRegId()) {
				if (null != reqJson.getPayeeifsc()) {
					List<Beneficiary> results = beneficiaryDao.getBeneficiary(reqJson, "ACCIFSC");
					if (results.size() == 1) {
						if (results.get(0).getStatus() == ConstantI.INACTIVE) {
							respJson = updateBeneficiary(results.get(0), reqJson, ConstantI.BENEFICIERY_ACCIFSC);
							return respJson;
						} else {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_ADDED_BNF.getCode());
							return respJson;
						}
					}
					respJson = addBeneficiary(beneficiary, reqJson, ConstantI.BENEFICIERY_ACCIFSC);
					return respJson;
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.IFSC_NULL_MSG.getCode());
					return respJson;
				}
				
			} else if (null != reqJson.getPayeeadharno() && null != reqJson.getRegId()) {
				List<Beneficiary> results = beneficiaryDao.getBeneficiary(reqJson, "AADHARIIN");
				if (results.size() == 1) {
					if (results.get(0).getStatus() == ConstantI.INACTIVE) {
						respJson = updateBeneficiary(results.get(0), reqJson, ConstantI.BENEFICIERY_AADHARIIN);
						return respJson;
					} else {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_ADDED_BNF.getCode());
						return respJson;
					}
				}
				respJson = addBeneficiary(beneficiary, reqJson, ConstantI.BENEFICIERY_AADHARIIN);
				return respJson;
			} else if (null != reqJson.getPayeemobilen() && null != reqJson.getRegId()) {
				if (null != reqJson.getPayeemmid()) {
					List<Beneficiary> results = beneficiaryDao.getBeneficiary(reqJson, "MOBMMID");
					if (results.size() == 1) {
						if (results.get(0).getStatus() == ConstantI.INACTIVE) {
							respJson = updateBeneficiary(results.get(0), reqJson, ConstantI.BENEFICIERY_MOBMMID);
							return respJson;
						} else {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_ADDED_BNF.getCode());
							return respJson;
						}
					}
					respJson = addBeneficiary(beneficiary, reqJson, ConstantI.BENEFICIERY_MOBMMID);
					return respJson;
				} else {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MMID_NULL_MSG.getCode());
					return respJson;
					
				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ConstantI.FAILURE_STRING);
				return respJson;
			}
		} catch (
		
		Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return respJson;
	}
	
	private RespJson updateBeneficiary(Beneficiary beneficiary, ReqJson reqJson, Integer beneficieryType) {
		RespJson respJson = new RespJson();
		beneficiary.setUpdated(new Date());
		beneficiary.setStatus(ConstantI.ACTIVE);
		try {
			switch (String.valueOf(beneficieryType)) {
				case "1":
					beneficiary.setPayeename(reqJson.getPayeename());
					beneficiary.setPayeeaddr(reqJson.getPayeeaddr());
					return reAddBenficiary(beneficiary);
				case "2":
					beneficiary.setPayeename(reqJson.getPayeename());
					beneficiary.setPayeeaccno(DecryptionUtility.encrypt(reqJson.getPayeeaccno()));
					beneficiary.setPayeeifsc(DecryptionUtility.encrypt(reqJson.getPayeeifsc()));
					return reAddBenficiary(beneficiary);
				case "3":
					beneficiary.setPayeename(reqJson.getPayeename());
					beneficiary.setPayeeadharno(DecryptionUtility.encrypt(reqJson.getPayeeadharno()));
					if (null != reqJson.getPayeeiin() || "".equalsIgnoreCase(reqJson.getPayeeiin())) {
						beneficiary.setPayeeiin(DecryptionUtility.encrypt(reqJson.getPayeeiin()));
					}
					return reAddBenficiary(beneficiary);
				case "4":
					beneficiary.setPayeename(reqJson.getPayeename());
					beneficiary.setPayeemobilen(DecryptionUtility.encrypt(reqJson.getPayeemobilen()));
					beneficiary.setPayeemmid(DecryptionUtility.encrypt(reqJson.getPayeemmid()));
					return reAddBenficiary(beneficiary);
				default:
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
					return respJson;
				
			}
		} catch (Exception e) {
			
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			
		}
		return respJson;
	}
	
	private RespJson addBeneficiary(Beneficiary beneficiary, ReqJson reqJson, Integer beneficieryVpa) {
		RespJson respJson = new RespJson();
		try {
			beneficiary.setAddeddt(new Date());
			beneficiary.setUpdated(new Date());
			switch (String.valueOf(beneficieryVpa)) {
				case "1":
					beneficiary.setType(ConstantI.BENEFICIERY_VPA);
					beneficiary.setPayeename(reqJson.getPayeename());
					beneficiary.setPayeeaddr(reqJson.getPayeeaddr());
					break;
				case "2":
					beneficiary.setType(ConstantI.BENEFICIERY_ACCIFSC);
					beneficiary.setPayeename(reqJson.getPayeename());
					beneficiary.setPayeeaccno(DecryptionUtility.encrypt(reqJson.getPayeeaccno()));
					beneficiary.setPayeeifsc(DecryptionUtility.encrypt(reqJson.getPayeeifsc()));
					break;
				case "3":
					beneficiary.setType(ConstantI.BENEFICIERY_AADHARIIN);
					beneficiary.setPayeename(reqJson.getPayeename());
					beneficiary.setPayeeadharno(DecryptionUtility.encrypt(reqJson.getPayeeadharno()));
					if (null != reqJson.getPayeeiin() || "".equalsIgnoreCase(reqJson.getPayeeiin())) {
						beneficiary.setPayeeiin(DecryptionUtility.encrypt(reqJson.getPayeeiin()));
					}
					break;
				case "4":
					beneficiary.setType(ConstantI.BENEFICIERY_MOBMMID);
					beneficiary.setPayeename(reqJson.getPayeename());
					beneficiary.setPayeemobilen(DecryptionUtility.encrypt(reqJson.getPayeemobilen()));
					beneficiary.setPayeemmid(DecryptionUtility.encrypt(reqJson.getPayeemmid()));
					break;
				default:
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
					return respJson;
			}
			if (beneficiaryDao.insertBenef(beneficiary)) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.BENEFICIERY_ADD_SUCCESS.getCode());
				return respJson;
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return respJson;
	}
	
	private RespJson reAddBenficiary(Beneficiary beneficiary) {
		RespJson respJson = new RespJson();
		boolean updateBen = false;
		try {
			if (null == beneficiaryDao) {
				beneficiaryDao = new BeneficiaryDao();
			}
			updateBen = beneficiaryDao.updateBenf(beneficiary);
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		if (updateBen) {
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.BENEFICIERY_ADD_SUCCESS.getCode());
			return respJson;
		}
		return respJson;
	}
	
	public RespJson deleteBeneficiary(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.FAILURE_STRING);
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setReqId(reqJson.getReqId());
		if (null == beneficiaryDao) {
			beneficiaryDao = new BeneficiaryDao();
		}
		Beneficiary beneficiary = beneficiaryDao.getBenefbyBeneIdAndStatus(reqJson);
		if (beneficiary != null) {
			beneficiary.setStatus(ConstantI.INACTIVE);
			if (beneficiaryDao.updateBenf(beneficiary)) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_DELETED_SUCCESS.getCode());
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.NO_BENEFICIARY_FOUND_OR_INACTIVE_FOUND.getCode());
			}
		} else {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.NO_BENEFICIARY_FOUND_OR_INACTIVE_FOUND.getCode());
		}
		return respJson;
	}
	
	public RespJson getBeneficiary(ReqJson reqJson) {
		List<BeneficiaryDto> beneficiaryList = new ArrayList<BeneficiaryDto>();
		BeneficiaryDao beneficiaryDao = null;
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		if (null == beneficiaryDao) {
			beneficiaryDao = new BeneficiaryDao();
		}
		List<Beneficiary> results = beneficiaryDao.getBeneficiary(reqJson);
		if (results.isEmpty()) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ConstantI.FAILURE_STRING);
			return respJson;
		}
		try {
			for (Beneficiary beneficiary : results) {
				BeneficiaryDto beneficiaryDto = new BeneficiaryDto();
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
				beneficiaryDto.setPayeename(beneficiary.getPayeename());
				if (beneficiary.getPayeeaddr() != null) {
					beneficiaryDto.setPayeeaddr(beneficiary.getPayeeaddr());
				} else if (beneficiary.getPayeeaccno() != null && beneficiary.getPayeeifsc() != null) {
					beneficiaryDto.setPayeeaccno(DecryptionUtility.decrypt(beneficiary.getPayeeaccno()));
					beneficiaryDto.setPayeeifsc(DecryptionUtility.decrypt(beneficiary.getPayeeifsc()));
				} else if (beneficiary.getPayeemobilen() != null) {
					beneficiaryDto.setPayeemobilen(DecryptionUtility.decrypt(beneficiary.getPayeemobilen()));
					beneficiaryDto.setPayeemmid(DecryptionUtility.decrypt(beneficiary.getPayeemmid()));
				} else if (beneficiary.getPayeeadharno() != null) {
					beneficiaryDto.setPayeeadharno(DecryptionUtility.decrypt(beneficiary.getPayeeadharno()));
					if (null != beneficiary.getPayeeiin() || "".equalsIgnoreCase(beneficiary.getPayeeiin())) {
						beneficiaryDto.setPayeeiin(DecryptionUtility.decrypt(beneficiary.getPayeeiin()));
					}
				}
				beneficiaryDto.setBeneid(beneficiary.getBeneid());
				beneficiaryDto.setRegid(beneficiary.getRegid());
				beneficiaryDto.setAddeddt(beneficiary.getAddeddt());
				beneficiaryDto.setUpdated(beneficiary.getUpdated());
				beneficiaryDto.setStatus(beneficiary.getStatus());
				beneficiaryDto.setType(beneficiary.getType());
				beneficiaryList.add(beneficiaryDto);
			}
			respJson.setListBeneficiary(beneficiaryList);
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			
		}
		return respJson;
	}
	
	public boolean isNotNullOrEmpty(final Object object) {
		if (object != null) { return true; }
		return false;
	}
	
	public RespJson updateBeneficiary(ReqJson reqJson) {
		BeneficiaryDao beneficiaryDao = null;
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		if (null == beneficiaryDao) {
			beneficiaryDao = new BeneficiaryDao();
		}
		Beneficiary beneficiary = beneficiaryDao.getBeneficiaryByBeneIdAndRegId(reqJson);
		if (null == beneficiary) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ConstantI.FAILURE_STRING);
			return respJson;
		}
		try {
			beneficiary.setStatus(Integer.parseInt(reqJson.getStatus()));
			if (beneficiaryDao.updateBenf(beneficiary)) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_UPDATE_SUCCESS.getCode());
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.NO_BENEFICIARY_FOUND_OR_INACTIVE_FOUND.getCode());
			}

		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			
		}
		return respJson;
		
	}
	
}
