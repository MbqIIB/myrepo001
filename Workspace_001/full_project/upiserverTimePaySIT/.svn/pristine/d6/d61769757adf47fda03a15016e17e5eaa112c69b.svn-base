package com.npst.upiserver.rmqlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.npst.upiserver.service.NpciIncomingApiService;
import com.npst.upiserver.util.ErrorLog;

@Component
public class NpciIncomingListener {
	private static final Logger log = LoggerFactory.getLogger(NpciIncomingListener.class);
	private final TaskExecutor taskExecutor;
	private final NpciIncomingApiService npciApiCheckService;

	@Autowired
	NpciIncomingListener(TaskExecutor taskExecutor, NpciIncomingApiService npciApiCheckService) {
		this.taskExecutor = taskExecutor;
		this.npciApiCheckService = npciApiCheckService;
	}

	@RabbitListener(queues = "${NPCI_INCOMING_QUEUE}")
	public void getRec(byte[] body) {
		try {
			String msg = new String(body, "UTF-8");
			log.info("npci incoming MSG {}",msg);
			taskExecutor.execute(() -> {
				npciApiCheckService.checkApiAndProcess(msg);
			});
		} catch (Exception e) {
			log.error("error in getting npcimsg {}", e);
			ErrorLog.sendError(e, NpciIncomingListener.class);
		}

	}
}
