package com.npst.upiserver.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerMandatesDao;
import com.npst.upiserver.dao.MandateTxnsDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.issuer.service.SwitchMandateCollectReqService;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.service.MandateTxnProcessorService;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.Util;

@Component
public class MandateTxnProcessorServiceImpl implements MandateTxnProcessorService {
	private static final Logger log = LoggerFactory.getLogger(MandateTxnProcessorServiceImpl.class);
	@Autowired
	private SwitchMandateCollectReqService switchMandateCollectReqService;
	@Autowired
	private CustomerMandatesDao customerMandatesDao;
	@Autowired
	private MandateTxnsDao mandateTxnsDao;

	@Override
	public void process() {
		try {
			final ReqResp reqJson = new ReqResp();
			final Set<CustomerMandatesEntity> customerMandatesList = customerMandatesDao.findMandatesTxnsToProcess();
			for (final CustomerMandatesEntity customerMandates : customerMandatesList) {
				if (mandateTxnsDao.checkIfTransactionIsProcessed(customerMandates.getMandateUmn())) {
					continue;
				}
				String txnId = Util.uuidGen();
				reqJson.setTxnId(txnId);
				reqJson.setTxnNote("Transaction for mandate: " + customerMandates.getMandateUmn());
				reqJson.setTxnRefId(txnId);
				reqJson.setTxnRefUrl("http://www.canarabank.in");
				reqJson.setPayerAmount(customerMandates.getMandateAmountvalue());
				reqJson.setPayeeAddr(customerMandates.getPayeeVpa());
				reqJson.setPayeeName(customerMandates.getPayeeName());
				reqJson.setPayeeSeqNum(customerMandates.getPayeeSeqNum());
				reqJson.setPayeeCode(customerMandates.getPayeeCode());
				reqJson.setPayeeType(customerMandates.getPayeeType());
				reqJson.setPayeeName(customerMandates.getPayeeName());
				/**
				 * Device signature
				 */
				// reqJson.setPayeeDeviceMobile();

				// reqJson.setPayeeDeviceGeoCode();

				// reqJson.setPayeeDeviceLocation();

				// reqJson.setPayeeDeviceIp();

				// reqJson.setPayeeDeviceType();

				// reqJson.setPayeeDeviceId();

				// reqJson.setPayeeDeviceOsType();

				// reqJson.setPayeeDeviceAppId();

				// reqJson.setPayeeDeviceCapability();

				reqJson.setPayeeAddrType(customerMandates.getPayeeAddrType());

				if (ConstantI.ACCOUNT.equalsIgnoreCase(reqJson.getPayeeAddrType())) {

					reqJson.setPayeeIfsc(customerMandates.getPayeeAccIFSC().toUpperCase());

					reqJson.setPayeeAcType(customerMandates.getPayeeAcType());

					reqJson.setPayeeAcNum(customerMandates.getPayeeAccNo());

				} else if (ConstantI.MOBILE.equalsIgnoreCase(reqJson.getPayeeAddrType())) {

					reqJson.setPayeeMmid(customerMandates.getPayeeMMID());

				} else if (ConstantI.CARD.equalsIgnoreCase(reqJson.getPayeeAddrType())) {

					reqJson.setPayeeAcType(customerMandates.getPayeeAcType());

					reqJson.setPayeeCardNum(customerMandates.getPayeeCardNum());

				}
				reqJson.setPayerAddr(customerMandates.getMandateUmn());// Mandate UMN as payer address
				reqJson.setPayerName(customerMandates.getPayerName());
				reqJson.setPayerSeqNum(customerMandates.getPayerSeqNum());
				reqJson.setPayerType(customerMandates.getPayerType());
				reqJson.setPayerCode(customerMandates.getPayerCode());
				/**
				 * Merchants tags
				 */
				if (PayerConstant.ENTITY.toString().equalsIgnoreCase(customerMandates.getPayeeType())) {
					reqJson.setMerchantSubCode(customerMandates.getMerchantSubCode());
					reqJson.setMerchantMid(customerMandates.getMerchantMid());
					reqJson.setMerchantSid(customerMandates.getMerchantSid());
					reqJson.setMerchantTid(customerMandates.getMerchantTid());
					reqJson.setMerchantType(customerMandates.getMerchantType());
					reqJson.setMerchantGenre(customerMandates.getMerchantGenre());
					reqJson.setMerchantOnboardingType(customerMandates.getMerchantOnboardingType());

					reqJson.setMerchantBrandName(customerMandates.getMerchantBrandName());
					reqJson.setMerchantFranchiseName(customerMandates.getMerchantFranchiseName());
					reqJson.setMerchantLegalName(customerMandates.getMerchantLegalName());

					reqJson.setMerchantOwnershipType(customerMandates.getMerchantOwnershipType());

				}
				/**
				 * end
				 */
				switchMandateCollectReqService.sendToNpci(reqJson);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MandateTxnProcessorServiceImpl.class);
		}
	}
}
