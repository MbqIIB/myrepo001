package com.npst.upiserver.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.service.MiddlewareRestConService;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MonitorLog;

@Service
public class MiddlewareRestConServiceImpl implements MiddlewareRestConService {
	private static final Logger log = LoggerFactory.getLogger(MiddlewareRestConServiceImpl.class);
	private static String httpUrl = Constant.middleWareUrl;

	@Qualifier("middlewareCloseableHttpClient")
	@Autowired
	private CloseableHttpClient middlewareCloseableHttpClient;

	@Override
	public ReqResp send(ReqResp reqResp) {
		log.debug("middleware request {} ", reqResp.toString());
		log.info("Sending Req to MiddleWare TYPE {}", reqResp.getTxnType());
		StringBuffer outputSB = new StringBuffer();
		long ts = 0;
		try {
			// UtilLogger.writeTextFile(reqResp.toString(), ConstantI.MiddleWareSendLogs);
			log.info(httpUrl, reqResp.getTxnType().toLowerCase());
			HttpPost post = new HttpPost(httpUrl + reqResp.getTxnType().toLowerCase());
			post.setHeader("Content-Type", "application/json");
			post.setHeader("Accept", "application/json");
			post.setHeader("cache-control", "no-cache");
			StringEntity params = new StringEntity(JsonMan.getJSONStr(reqResp)); 
			post.setEntity(params);
			log.info("Full req {} ", post);
			ts = System.currentTimeMillis();
			try (CloseableHttpResponse response = middlewareCloseableHttpClient.execute(post);) {
				log.info("Middleware ReqResp TxnType={} ,Time={} ,TxnId={}", reqResp.getTxnType(), ts,
						reqResp.getTxnId());
				int responseCode = response.getStatusLine().getStatusCode();
				log.info("middleware httpStatusCode {} ", responseCode);
				try (BufferedReader rd = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));) {
					String line = "";
					while ((line = rd.readLine()) != null) {
						outputSB.append(line);
					}
				}
				ts = System.currentTimeMillis() - ts;
				// UtilLogger.writeTextFile(outputSB.toString(), ConstantI.MiddleWareSendLogs);
				log.info("middleware response : {}" + outputSB.toString());
				ReqResp resp =JsonMan.getReqResp(outputSB.toString()); // JsonMan.getReqResp(outputSB.toString());
				MonitorLog.switchcomInfo(ts, reqResp.getTxnType(), resp.getRespCode(), resp.getCbsErrorCode(),
						reqResp.getRrn(), reqResp.getTxnId(), resp.getTxnId(), "Got Response");
				if (resp.getRespCode() == null || resp.getRespCode().trim().isEmpty()) {
					resp.setRespCode(ConstantI.UKN);
					log.info("Blank RespCode from Middleware TxnType={} ,TxnId={} ,RRN={}", reqResp.getTxnType(),
							reqResp.getTxnId(), reqResp.getRrn());
					ErrorLog.sendError("Blank RespCode from Middleware for TxnType,TxnId,RRN ",
							new String[] { reqResp.getTxnType(), reqResp.getTxnId(), reqResp.getRrn() },
							MiddlewareRestConServiceImpl.class);
				}
				return resp;
			}
		} catch (org.apache.http.conn.ConnectionPoolTimeoutException e) {
			e.printStackTrace();
			ts = System.currentTimeMillis() - ts;
			reqResp.setRespCode(ConstantI.UKN);
			log.error("FOR MIDDLEWARE CONNECTION_POOL_TIMEOUT_EXCEPTION :TxnId={} ,error={}", reqResp.getTxnId(), e);
			MonitorLog.switchcomError(ts, reqResp.getTxnType(), reqResp.getRespCode(), reqResp.getRrn(),
					reqResp.getTxnId(), "CONNECTION_POOL_TIMEOUT_EXCEPTION", e.getMessage());
			ErrorLog.sendError(e, MiddlewareRestConServiceImpl.class);
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			// TODO need discussion
			e.printStackTrace();
			ts = System.currentTimeMillis() - ts;
			reqResp.setRespCode(getHCEErrorRespCode(reqResp.getTxnType()));
			log.error("MIDDLEWARE HTTP_HOST_CONNECT_EXCEPTION :TxnId={} ,error={}", reqResp.getTxnId(), e);
			MonitorLog.switchcomError(ts, reqResp.getTxnType(), reqResp.getRespCode(), reqResp.getRrn(),
					reqResp.getTxnId(), "HTTP_HOST_CONNECT_EXCEPTION", e.getMessage());
			ErrorLog.sendError(e, MiddlewareRestConServiceImpl.class);
		} catch (java.net.SocketTimeoutException e) {
			// Read timed out
			e.printStackTrace();
			ts = System.currentTimeMillis() - ts;
			reqResp.setRespCode(ConstantI.UKN);
			log.error("MIDDLEWARE SOCKET_TIMEOUT_EXCEPTION :TxnId={} ,error={}", reqResp.getTxnId(), e);
			MonitorLog.switchcomError(ts, reqResp.getTxnType(), reqResp.getRespCode(), reqResp.getRrn(),
					reqResp.getTxnId(), "SOCKET_TIMEOUT_EXCEPTION", e.getMessage());
			ErrorLog.sendError(e, MiddlewareRestConServiceImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
			ts = System.currentTimeMillis() - ts;
			reqResp.setRespCode(ConstantI.UKN);
			log.error("EXCEPTION IN SWITCHCOM :TxnId={} ,error {}", reqResp.getTxnId(), e);
			MonitorLog.switchcomError(ts, reqResp.getTxnType(), reqResp.getRespCode(), reqResp.getRrn(),
					reqResp.getTxnId(), "SWITCHCOM Exception", e.getMessage());
			ErrorLog.sendError(e, MiddlewareRestConServiceImpl.class);
		}
		return reqResp;
	}

	private static String getHCEErrorRespCode(final String txnType) {
		String respCode = ConstantI.UKN;
		if (ConstantI.DEBIT.equals(txnType)) {
			respCode = ConstantI.SWITCHCOM_HCE_DEBIT;
		} else if (ConstantI.CREDIT.equals(txnType)) {
			respCode = ConstantI.SWITCHCOM_HCE_CREDIT;
		} else if (ConstantI.REVERSAL.equals(txnType)) {
			respCode = ConstantI.SWITCHCOM_HCE_REVERSAL;
		}
		return respCode;
	}

}
