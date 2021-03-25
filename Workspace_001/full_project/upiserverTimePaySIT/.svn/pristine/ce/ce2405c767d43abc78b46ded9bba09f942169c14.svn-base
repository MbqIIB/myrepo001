package com.npst.upiserver.rmqlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npst.upiserver.entity.MobMandateReqRespJsonEntity;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.service.MobIncomingService;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class MobIncomingListener {
	private static final Logger log = LoggerFactory.getLogger(MobIncomingListener.class);

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private MobIncomingService mobIncomingService;

	@RabbitListener(queues = "${MOB_UPI_QUEUE}")
	public void mobToUpiListener(byte[] body) {
		try {
			MobUpiReqRespJson jsonObj = JsonMan.getObjectFromStringBytes(body, MobUpiReqRespJson.class);
			log.info("MOB_UPI_QUEUE ={}", jsonObj);
			taskExecutor.execute(() -> {
				mobIncomingService.proMobReq(jsonObj);
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error in mobToUpiListener {}", e);
			ErrorLog.sendError(e, MobIncomingListener.class);
		}

	}

	@RabbitListener(queues = "${MOB_ONUS_QUEUE}")
	public void onusListener(byte[] body) {
		try {
//			OnusMobReqRespJsonEntity obj = JsonMan.getObjectFromStringBytes(body, OnusMobReqRespJsonEntity.class);
			String msg = new String(body, "UTF-8");
			log.debug("MOB_ONUS_QUEUE ={}", msg);
			taskExecutor.execute(() -> {
				mobIncomingService.proOnus(msg);
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error in onusListener {}", e);
			ErrorLog.sendError(e, MobIncomingListener.class);
		}
	}

	@RabbitListener(queues = "${MOB_MANDATE_QUEUE}")
	public void mandateListener(byte[] body) {
		try {
			String msg = new String(body, "UTF-8");
		MobMandateReqRespJsonEntity obj = JsonMan.getObjectFromStringBytes(body, MobMandateReqRespJsonEntity.class);
		log.debug("MOB_MANDATE_QUEUE ={}", msg);
	//MobMandateReqRespJsonEntity obj = JsonMan.getObjectFromStringBytes(body, MobMandateReqRespJsonEntity.class);//09012019
			taskExecutor.execute(() -> {
				mobIncomingService.proMandates(obj);
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error in mandateListener {}", e);
			ErrorLog.sendError(e, MobIncomingListener.class);
		}
	}
}
