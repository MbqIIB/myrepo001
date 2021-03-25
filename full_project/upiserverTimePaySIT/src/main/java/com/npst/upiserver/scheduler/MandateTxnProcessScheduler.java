package com.npst.upiserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.npst.upiserver.service.MandateTxnProcessorService;
import com.npst.upiserver.util.ErrorLog;

@Component
public class MandateTxnProcessScheduler {

	private static final Logger log = LoggerFactory.getLogger(MandateTxnProcessScheduler.class);

	@Autowired
	private MandateTxnProcessorService mandateTxnProcessorService;

	/*@Value("${IS_ENABLE_MANDATETXNPROCESS}")
	private boolean isEnable;

	@Scheduled(initialDelayString = "${MANDATETXNPROCESS_INITIALDELAY}", fixedDelayString = "${MANDATETXNPROCESS_FIXEDDELAY}")
	public void alert() {
		try {
			log.info("IS_ENABLE_MANDATETXNPROCESS ={}", isEnable);
			if (isEnable) {
				log.info("MandateTxnProcessScheduler call starting");
				mandateTxnProcessorService.process();
			}
		} catch (Exception e) {
			log.error("error in MANDATETXNPROCESS={}", e);
			ErrorLog.sendError(e, MandateTxnProcessScheduler.class);
		}
	}*/
}
