package com.npst.upiserver.scheduler;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.npst.upiserver.util.ErrorLog;

@Component
public class NpciIdleConnectionMonitorScheduler {
	private static final Logger log = LoggerFactory.getLogger(NpciIdleConnectionMonitorScheduler.class);
	@Value("${NPCI_CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS}")
	private int CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS;

	@Qualifier("npciPoolingHttpClientConnectionManager")
	@Autowired
	private PoolingHttpClientConnectionManager connectionManager;

	@Scheduled(initialDelayString = "${NPCI_IDLE_CONNECTIONMONITOR_INITIALDELAY}", fixedDelayString = "${NPCI_IDLE_CONNECTIONMONITOR_FIXEDDELAY}")
	public void closeNpciIdleConn() {
		try {
			log.info(
					"Scheduler Run for NPCI IdleConnectionMonitor - Going to close if any expired and idle connections..");
			connectionManager.closeExpiredConnections();
			connectionManager.closeIdleConnections(CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("Scheduler Run for NPCI IdleConnectionMonitor Error {}", e);
			ErrorLog.sendError(e,NpciIdleConnectionMonitorScheduler.class);
		}
	}
}
