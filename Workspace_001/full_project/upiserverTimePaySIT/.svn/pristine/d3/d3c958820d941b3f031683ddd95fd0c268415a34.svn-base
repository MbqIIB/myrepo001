package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.OrphanDebitCreditRevDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.OrphanDebitCredit;

import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.repo.OrphanDebitCreditRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class OrphanDebitCreditRevDaoImpl implements OrphanDebitCreditRevDao {
	private static final Logger log = LoggerFactory.getLogger(OrphanDebitCreditRevDaoImpl.class);

	@Autowired
	private OrphanDebitCreditRepository orphanDebitCreditRevRepo;

	@Override
	public void insertReversal(ReqPay reqPay, RespPay respPay, Ack ack, Date reqDate, Date respDate, String revType,
			String cbsErrorCode, String cbsrrn) {
		try {
			log.info("Going to set the oprhan debit credit");
			OrphanDebitCredit orphanDebitCredit = new OrphanDebitCredit();
			orphanDebitCredit.setTxnType(revType);
			orphanDebitCredit.setOrgTxnId(reqPay.getTxn().getOrgTxnId());
			orphanDebitCredit.setRevCBSrrn(cbsrrn);
			orphanDebitCredit.setRevHeadMsgId(reqPay.getHead().getMsgId());
			orphanDebitCredit.setRevTxnId(reqPay.getTxn().getId());
			orphanDebitCredit.setRevRespResult(respPay.getResp().getResult().value());
			orphanDebitCredit.setRevRespErrCode(respPay.getResp().getErrCode());
			List<Ref> refList = respPay.getResp().getRef();
			String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK, refAddr = ConstantI.CONST_BLANK,
					refRegName = ConstantI.CONST_BLANK, refSettAmount = ConstantI.CONST_BLANK,
					refReversalRespCode = ConstantI.CONST_BLANK, refOrgAmount = ConstantI.CONST_BLANK,
					refSettCurrency = ConstantI.CONST_BLANK, refAcNum = ConstantI.CONST_BLANK,
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
			orphanDebitCredit.setRevRefType(refType);
			orphanDebitCredit.setRevRefSeqNum(refSeqNum);
			orphanDebitCredit.setRevRefAddr(refAddr);
			orphanDebitCredit.setRevRefRegName(refRegName);
			orphanDebitCredit.setRevRefSettAmount(refSettAmount);
			orphanDebitCredit.setRevRefSettCurrency(refSettCurrency);
			orphanDebitCredit.setRevRefAcNum(refAcNum);
			orphanDebitCredit.setRevRefApprovalNum(refApprovalNum);
			orphanDebitCredit.setRevRefRespCode(refRespCode);
			orphanDebitCredit.setRevRefReversalRespCode(refReversalRespCode);
			orphanDebitCredit.setRevRefOrgAmount(refOrgAmount);
			orphanDebitCredit.setRevCbserrorCode(cbsErrorCode);
			orphanDebitCredit.setRevMberrorCode(ConstantI.CONST_BLANK);
			orphanDebitCredit.setRevReqInsert(reqDate);
			orphanDebitCredit.setRevRespInsert(respDate);
			orphanDebitCredit.setAckRev(JsonMan.getJSONStr(ack));
			orphanDebitCreditRevRepo.save(orphanDebitCredit);
		} catch (Exception e) {
			log.error("error in insertReversal {}", e);
			ErrorLog.sendError(e, OrphanDebitCreditRevDaoImpl.class);
		}

	}

	@Override
	public void insertRevPreApproved(ReqResp reqResp, RespPay respPay, Date reqDate, Date respDate) {
		try {
			log.info("Going to set the oprhan debit credit");
			OrphanDebitCredit orphanDebitCredit = new OrphanDebitCredit();
			orphanDebitCredit.setTxnType(reqResp.getOrgTxnType());
			orphanDebitCredit.setOrgTxnId(reqResp.getOrgTxnId());
			orphanDebitCredit.setRevCBSrrn(reqResp.getRrn());
			orphanDebitCredit.setRevTxnId(reqResp.getTxnId());
			orphanDebitCredit.setRevRespResult(
					ConstantI.CODE_SUCCESS.equals(reqResp.getRespCode()) ? ConstantI.SUCCESS : ConstantI.FAILURE);
			orphanDebitCredit.setRevRespErrCode(reqResp.getRespCode());
			orphanDebitCredit.setOrgRespCode(respPay.getTxn().getOrgRespCode());
			orphanDebitCredit.setRevCbserrorCode(reqResp.getCbsErrorCode());

			List<Ref> refList = respPay.getResp().getRef();
			String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK, refAddr = ConstantI.CONST_BLANK,
					refRegName = ConstantI.CONST_BLANK, refSettAmount = ConstantI.CONST_BLANK,
					refOrgAmount = ConstantI.CONST_BLANK, refSettCurrency = ConstantI.CONST_BLANK,
					refAcNum = ConstantI.CONST_BLANK, refApprovalNum = ConstantI.CONST_BLANK,
					refRespCode = ConstantI.CONST_BLANK;
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
				refOrgAmount += ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE;
			}
			orphanDebitCredit.setRevRefType(refType);
			orphanDebitCredit.setRevRefSeqNum(refSeqNum);
			orphanDebitCredit.setRevRefAddr(refAddr);
			orphanDebitCredit.setRevRefRegName(refRegName);
			orphanDebitCredit.setRevRefSettAmount(refSettAmount);
			orphanDebitCredit.setRevRefSettCurrency(refSettCurrency);
			orphanDebitCredit.setRevRefAcNum(refAcNum);
			orphanDebitCredit.setRevRefApprovalNum(refApprovalNum);
			orphanDebitCredit.setRevRefRespCode(refRespCode);
			orphanDebitCredit.setRevRefOrgAmount(refOrgAmount);
			orphanDebitCredit.setRevReqInsert(reqDate);
			orphanDebitCredit.setRevRespInsert(respDate);
			orphanDebitCreditRevRepo.save(orphanDebitCredit);

		} catch (Exception e) {
			log.error("error in insertReversal {}", e);
			ErrorLog.sendError(e, OrphanDebitCreditRevDaoImpl.class);
		}

	}

}
