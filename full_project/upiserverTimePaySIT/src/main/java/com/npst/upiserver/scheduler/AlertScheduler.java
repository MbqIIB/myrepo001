//package com.npst.upiserver.scheduler;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.stereotype.Component;
//
//import com.npst.upiserver.dao.TransNoDao;
//
//@Component
//public class AlertScheduler {
//
//	private static final Logger log = LoggerFactory.getLogger(AlertScheduler.class);
//	@Autowired
//	private TransNoDao transNoDao;
//	@Autowired
//	private TaskExecutor taskExecutor;
//
//	private static ConcurrentHashMap<Integer, Long> map = new ConcurrentHashMap<Integer, Long>(999999);
//
//	@PostConstruct
//	public void alert() {
//	new Thread(() -> {
//	try {
//	for (int j = 0; j < 5000; j++) {
//	taskExecutor.execute(() -> {
//	try {
//	Integer i = this.transNoDao.getTransNo(159164);
//	System.out.println("i=" + i);
//	if (map.get(i) == null) {
//	map.put(i, System.currentTimeMillis());
//	} else {
//	log.error(">>>>>>>DUPLICATE transNo={}", i);
//	System.out.println(">>>>>>>>>>>>>>>>>>DUPLICATE transNo=" + i);
//	}
//	} catch (Exception e) {
//	log.error("taskexce error={}", e.getMessage());
//	}
//
//	});
//	}
//	} catch (Exception e) {
//	log.error("final error={}", e.getMessage());
//	}
//
//	}).start();
//	;
//	}
//
//}