package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespPayCollectDao;
import com.npst.upiserver.dao.ReqRespPayCollectPayeesDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.ReqRespPayCollect;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.repo.ReqRespPayCollectRepository;
import com.npst.upiserver.util.ErrorLog;

@Component
public class ReqRespPayCollectDaoImpl implements ReqRespPayCollectDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespPayCollectDaoImpl.class);
	
	@Autowired
	ReqRespPayCollectRepository reqRespPayCollectRepo;
	
	@Autowired
	private ReqRespPayCollectPayeesDao reqRespPayCollectPayeesDao;
	
	private ReqRespPayCollect getByTxnId(String txnId) {
		try {
			List<ReqRespPayCollect> list = reqRespPayCollectRepo.findByTxnId(txnId);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("ReqRespPayCollectEntity not found for TxnId={}", txnId);
			} else {
				log.warn("More than one ReqRespPayCollectEntity found for TxnId={}", txnId);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespPayCollectDaoImpl.class);
		}
		return null;
	}
	
	@Override
	public void updateRespPay(RespPay respPay) {
		try{
			ReqRespPayCollect reqresppaycollect = getByTxnId(respPay.getTxn().getId());
			if (reqresppaycollect == null) {
				return;
			}
			reqresppaycollect.setRespHeadTs(respPay.getHead().getTs());
			reqresppaycollect.setRespHeadOrgId(respPay.getHead().getOrgId());
			reqresppaycollect.setRespHeadMsgId(respPay.getHead().getMsgId());

			reqresppaycollect.setRespInsert(new Date());
			reqresppaycollect.setRespResult(respPay.getResp().getResult().value());
			reqresppaycollect.setRespErrCode(respPay.getResp().getErrCode());
			List<Ref> refList = respPay.getResp().getRef();
			String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK, refAddr = ConstantI.CONST_BLANK, refRegName = ConstantI.CONST_BLANK, refSettAmount = ConstantI.CONST_BLANK,
					refReversalRespCode = ConstantI.CONST_BLANK, refOrgAmount = ConstantI.CONST_BLANK, refSettCurrency = ConstantI.CONST_BLANK, refAcNum = ConstantI.CONST_BLANK,
					refApprovalNum = ConstantI.CONST_BLANK, refRespCode = ConstantI.CONST_BLANK;
			
			for (Ref ref : refList) {
				refType += ref.getType() + ConstantI.CONST_SPCL_PIPE;
				refSeqNum += ref.getSeqNum() + ConstantI.CONST_SPCL_PIPE;
				refAddr += ref.getAddr() + ConstantI.CONST_SPCL_PIPE;
				refRegName += ref.getRegName() + ConstantI.CONST_SPCL_PIPE;
				refSettAmount += ref.getSettAmount() + ConstantI.CONST_SPCL_PIPE;
				refSettCurrency += ref.getSettCurrency() + ConstantI.CONST_SPCL_PIPE;
				// refAcNum+=ref.geta+ConstantI.CONST_SPCL_PIPE;
				refApprovalNum += ref.getApprovalNum() + ConstantI.CONST_SPCL_PIPE;
				refRespCode += ref.getRespCode() + ConstantI.CONST_SPCL_PIPE;
				refReversalRespCode += ref.getReversalRespCode() + ConstantI.CONST_SPCL_PIPE;
				refOrgAmount += ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE;
			}
			reqresppaycollect.setRefType(refType);
			reqresppaycollect.setRefSeqNum(refSeqNum);
			reqresppaycollect.setRefAddr(refAddr);
			reqresppaycollect.setRefRegName(refRegName);
			reqresppaycollect.setRefSettAmount(refSettAmount);
			reqresppaycollect.setRefSettCurrency(refSettCurrency);
			reqresppaycollect.setRefAcNum(refAcNum);
			reqresppaycollect.setRefApprovalNum(refApprovalNum);
			reqresppaycollect.setRefRespCode(refRespCode);
			reqresppaycollect.setRefReversalRespCode(refReversalRespCode);
			reqresppaycollect.setRefOrgAmount(refOrgAmount);
			reqRespPayCollectRepo.save(reqresppaycollect);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void insertReq(ReqPay reqPay, Ack ack) {
		try{
			
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void insertReqRespOnus(ReqResp reqResp) {
		try {
			ReqRespPayCollect reqresppaycollect = new ReqRespPayCollect();
			reqresppaycollect.setReqInsert(new Date());

			reqresppaycollect.setReqHeadMsgId(reqResp.getTxnId());

			reqresppaycollect.setTxnCustRef(reqResp.getRrn());
			reqresppaycollect.setTxnId(reqResp.getTxnId());
			reqresppaycollect.setTxnIdPrf(reqResp.getTxnId().substring(0, 3));
			reqresppaycollect.setTxnNote(reqResp.getTxnNote());
			reqresppaycollect.setTxnType(reqResp.getTxnType());

			reqresppaycollect.setPayerAddr(reqResp.getPayerAddr());
			reqresppaycollect.setPayerCode(reqResp.getPayerCode());
			reqresppaycollect.setPayerHandal(
					reqResp.getPayerAddr().substring(reqResp.getPayerAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));
			reqresppaycollect.setPayerName(reqResp.getPayerName());
			reqresppaycollect.setAmountCrr(reqResp.getPayeeAmount());
			reqresppaycollect.setAmountVal(reqResp.getPayeeAmount());

			reqresppaycollect.setAcAddrType(reqResp.getPayerAddrType());
			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, acAddrTypeDetail2 = ConstantI.CONST_BLANK,
					acAddrTypeDetail3 = ConstantI.CONST_BLANK;
			if (ConstantI.ACCOUNT.equalsIgnoreCase(reqResp.getPayerAddrType())) {
				acAddrTypeDetail1 = AccountDetailType.IFSC.toString() + ConstantI.CONST_SPCL_PIPE
						+ reqResp.getPayerIfsc().toUpperCase();
				acAddrTypeDetail2 = AccountDetailType.ACTYPE.toString() + ConstantI.CONST_SPCL_PIPE
						+ reqResp.getPayerAcType();
				acAddrTypeDetail3 = AccountDetailType.ACNUM.toString() + ConstantI.CONST_SPCL_PIPE
						+ reqResp.getPayerAcNum();
			}
			reqresppaycollect.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqresppaycollect.setAcAddrTypeDetail2(acAddrTypeDetail2);
			reqresppaycollect.setAcAddrTypeDetail3(acAddrTypeDetail3);

			reqresppaycollect.setCredType(CredType.PRE_APPROVED.toString());
			reqresppaycollect.setDeviceApp(reqResp.getPayerDeviceAppId());
			reqresppaycollect.setDeviceCapability(reqResp.getPayerDeviceCapability());
			reqresppaycollect.setDeviceGeocode(reqResp.getPayerDeviceGeoCode());
			reqresppaycollect.setDeviceId(reqResp.getPayerDeviceId());
			reqresppaycollect.setDeviceIp(reqResp.getPayerDeviceIp());
			reqresppaycollect.setDeviceLocation(reqResp.getPayerDeviceLocation());
			reqresppaycollect.setDeviceMobile(reqResp.getPayerDeviceMobile());
			reqresppaycollect.setDeviceOs(reqResp.getPayerDeviceOsType());
			reqresppaycollect.setDeviceType(reqResp.getPayerDeviceType());

			reqresppaycollect.setRespResult(reqResp.getRespMsg());
			reqresppaycollect.setRespErrCode(reqResp.getRespCode());
			reqRespPayCollectRepo.save(reqresppaycollect);
			reqRespPayCollectPayeesDao.insertPayees(reqResp, reqResp.getTxnId(), reqResp.getTxnId());
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespPayCollectDaoImpl.class);
		}
		
	}
}
