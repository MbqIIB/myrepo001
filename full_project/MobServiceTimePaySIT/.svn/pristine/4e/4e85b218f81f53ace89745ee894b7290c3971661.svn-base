package com.npst.mobileservice.service;
import java.util.Date;

import com.npst.mobileservice.dao.MandateMobupireqrespjsonHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.RabbitMQSend;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.MobMandateReqRespJsonEntity;

public class MobUpiReqRespMandateJsonService {

	private static MandateMobupireqrespjsonHome mandateMobupireqrespjsonHome=null;
	private static MandateMobupireqrespjsonidService mandateMobupireqrespjsonidService=null;
	
	public MobMandateReqRespJsonEntity findById(int parseInt) {
		if (null == mandateMobupireqrespjsonHome) {
			mandateMobupireqrespjsonHome = new MandateMobupireqrespjsonHome();
		}
		return mandateMobupireqrespjsonHome.findById(parseInt);
	}
	public RespJson save(String reqStr, ReqJson reqJson) {
		RespJson respJson = new RespJson();
		try {
			if(mandateMobupireqrespjsonHome==null) {
				mandateMobupireqrespjsonHome=new MandateMobupireqrespjsonHome();
			}
			if(mandateMobupireqrespjsonidService==null) {
				mandateMobupireqrespjsonidService=new MandateMobupireqrespjsonidService();
			}
			MobMandateReqRespJsonEntity mobupireqrespjson = new MobMandateReqRespJsonEntity();
			mobupireqrespjson.setFlag(ConstantI.UPI_REQ_INSERT_D_Q);
			mobupireqrespjson.setReq(reqStr);
			mobupireqrespjson.setReqDate(new Date());
			mobupireqrespjson.setType(reqJson.getTxnFlag());
			
			//for By passs
			
			/*mobupireqrespjson.setFlag(ConstantI.UPI_REQ_RESP_D);
			mobupireqrespjson.setRespDate(new Date());
			if(reqJson.getTxnType().equalsIgnoreCase("CREATE")) {
				mobupireqrespjson.setResp("{\"rrn\":\"928300743251\",\"txnType\":\"CREATE\",\"onUs\":false,\"txnId\":\"CAN49630832351340A89586CA2F0238EF93\",\"respCode\":\"00\",\"respMsg\":\"SUCCESS\",umn:\"GHBDDHDHCCBDHDBHDHNDHCDH8789HDH876H@cnrb\",\"msgId\":\"0\",\"msg\":\"SUCCESS\",\"refApprovalNum\":\"743251\",\"merchTxn\":false,\"internalM\":false,\"bharatQR\":false,\"upi2Req\":true}");
			}
			else {
				mobupireqrespjson.setResp("{\"rrn\":\"928300743251\",\"txnType\":\"CREATE\",\"onUs\":false,\"txnId\":\"CAN49630832351340A89586CA2F0238EF93\",\"respCode\":\"00\",\"respMsg\":\"SUCCESS\",\"msgId\":\"0\",\"msg\":\"SUCCESS\",\"refApprovalNum\":\"743251\",\"merchTxn\":false,\"internalM\":false,\"bharatQR\":false,\"upi2Req\":true}");
			}*/
			//
			mandateMobupireqrespjsonHome.save(mobupireqrespjson);
			mandateMobupireqrespjsonidService.save(mobupireqrespjson);
			RabbitMQSend.send(JSONConvert.getJsonStr(mobupireqrespjson), Util.getProperty("MANDATE_MOB_TO_PSP"));
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setIdPk(mobupireqrespjson.getIdPk() + "");
		} catch (Exception ex) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
		}

		return respJson;
	}

}
