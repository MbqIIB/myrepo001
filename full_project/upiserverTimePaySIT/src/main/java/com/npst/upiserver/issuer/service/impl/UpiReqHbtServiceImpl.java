package com.npst.upiserver.issuer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.issuer.service.UpiReqHbtService;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.ReqHbt;
import com.npst.upiserver.npcischema.RespHbt;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqHbtServiceImpl implements UpiReqHbtService {

	private static final Logger log = LoggerFactory.getLogger(UpiReqHbtServiceImpl.class);

	@Autowired
	NpciUpiRestConServiceImpl  npciResponseService;
	
	@Override
	public void issuerProcess(final ReqHbt reqHbt) {
		log.debug("reqHbt {}", reqHbt);
		try {
			String ts = Util.getTS();
			String msgId = Util.uuidGen();
			RespHbt respHbt = new RespHbt();
			HeadType head = new HeadType();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			respHbt.setHead(head);
			
			respHbt.setTxn(reqHbt.getTxn());
			RespType resp = new RespType();
			resp.setResult(ConstantI.SUCCESS);
			resp.setReqMsgId(reqHbt.getTxn().getId());
			respHbt.setResp(resp);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respHbt).toString());
			npciResponseService.send(xmlStr, ConstantI.API_RESP_HBT, respHbt.getTxn().getId());
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
