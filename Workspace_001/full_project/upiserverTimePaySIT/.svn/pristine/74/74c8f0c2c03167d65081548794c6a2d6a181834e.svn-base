package com.npst.upiserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorLog {
	private static final Logger log = LoggerFactory.getLogger(MonitorLog.class);

	public static void switchcomInfo(long time, String reqType, String respCode, String cbsRespCode, String rrn,
			String txnId, String respTxnId, String remark) {
		log.info(
				"MIDDLEWARE Time={} ms ,ReqType={} ,RespCode={} ,CbsRespCode={} ,RRN={} ,TxnId={} ,RespTxnId={} ,Remark={}",
				time, reqType, respCode, cbsRespCode, rrn, txnId, respTxnId, remark);
	}

	public static void switchcomError(long time, String reqType, String respCode, String rrn, String txnId,
			String remark, String error) {
		log.error("MIDDLEWARE Time={} ms ,ReqType={} ,RespCode={} ,RRN={} ,TxnId={} ,Remark={} ,Error={}", time,
				reqType, respCode, rrn, txnId, remark, error);
	}

	public static void npciRestInfo(long time, String apiName, String txnId, String remark) {
		log.info("NPCI Time={} ms ,Api={} ,TxnId={} ,Remark={}", time, apiName, txnId, remark);
	}

	public static void npciRestErrInAck(long time, String apiName, String txnId, String remark, String ack) {
		log.error("NPCI Time={} ms ,Api={} ,TxnId={} ,Remark={} ,ack={}", time, apiName, txnId, remark, ack);
	}

	public static void npciRestError(long time, String apiName, String txnId, String remark, String error) {
		log.error("NPCI Time={} ms ,Api={} ,TxnId={} ,Remark={} ,Error={}", time, apiName, txnId, remark, error);
	}

	public static void statusLogWriterTP(int activeCount, long taskCount, int poolSize, long completedTaskCount) {
		log.info(
				"UtilLogThreadPool Approximation: ActiveCount={} ,TaskCount={} ,Current PoolSize={} ,CompletedTaskCount={}",
				activeCount, taskCount, poolSize, completedTaskCount);
	}

	public static void statusThreadPool(int activeCount, long taskCount, int poolSize, long completedTaskCount) {
		log.info("ThreadPool Approximation: ActiveCount={} ,TaskCount={} ,Current PoolSize={} ,CompletedTaskCount={}",
				activeCount, taskCount, poolSize, completedTaskCount);

	}
	
	public static void statusThreadPool(int activeCount,int poolSize) {
		log.info("ThreadPool Approximation: ActiveCount={} ,Current PoolSize={}",
				activeCount, poolSize);

	}
}
