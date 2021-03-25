package com.npst.upiserver.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.Unmarshaller;

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
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ErrorMessage;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MonitorLog;
import com.npst.upiserver.util.UnMarshalUpi;
import com.npst.upiserver.util.UpiSendLogs;
import com.npst.upiserver.util.Util;
import com.npst.upiserver.util.UtilLogger;

@Service
public class NpciUpiRestConServiceImpl implements NpciUpiRestConService {
	private static final Logger log = LoggerFactory.getLogger(NpciUpiRestConService.class);
	private static final String npciBaseUrl = Util.getProperty("NPCI_BASE_URL");
	public static final String VER_URN_TXNID = "/" + Constant.headVer + "/urn:txnid:";

	@Qualifier("npciCloseableHttpClient")
	@Autowired
	private CloseableHttpClient npciCloseableHttpClient;

	@Override
	public Ack send(String data, String apiName, String txnId) {
		log.info("api name | txnId {} :  {} ", apiName, txnId);
		StringBuffer outputSB = new StringBuffer();
		Ack ack = null;
		boolean isAck = false;
		long ts = 0;
		try {
			UpiSendLogs.send(data);
			String https_url = npciBaseUrl + apiName + VER_URN_TXNID + txnId;
			log.info("NPCI URL {}  ", https_url);
			HttpPost post = new HttpPost(https_url);
			post.setHeader(ConstantI.CONTENT_TYPE, ConstantI.CONST_APP_XML);
			post.setHeader(ConstantI.ACCEPT, ConstantI.CONST_APP_XML);
			post.setHeader(ConstantI.CACHE_CONTROL,ConstantI.NO_CACHE);
			StringEntity params = new StringEntity(data);
			post.setEntity(params);
			ts = System.currentTimeMillis();
			try (CloseableHttpResponse response = npciCloseableHttpClient.execute(post);) {
				int responseCode = response.getStatusLine().getStatusCode();
				log.info("NPCI Time {} ms with httpRespCode {} ", ts, responseCode);
				try (BufferedReader rd = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));) {
					String line = "";
					while ((line = rd.readLine()) != null) {
						outputSB.append(line);
					}
				}
				ts = System.currentTimeMillis() - ts;
				if (outputSB.toString().trim().isEmpty() || ConstantI.StatusSuccess_200 != responseCode) {
					MonitorLog.npciRestError(ts, apiName, txnId, "ACK NOT RECEIVED And HttpStatusCode=" + responseCode,
							outputSB.toString());
					ErrorLog.sendError("ACK NOT RECEIVED And HttpStatusCode ",String.valueOf(responseCode), NpciUpiRestConServiceImpl.class);
				} else {
					isAck = true;
					MonitorLog.npciRestInfo(ts, apiName, txnId, "Got Acknowledge");
				}
				UpiSendLogs.send(outputSB.toString());
				//log.info("This is the output from UPI : {}", outputSB.toString());
			}
		} catch (org.apache.http.conn.ConnectionPoolTimeoutException e) {
			e.printStackTrace();
			ts = System.currentTimeMillis() - ts;
			MonitorLog.npciRestError(ts, apiName, txnId, "HTTP CONNECTION_POOL_TIMEOUT_EXCEPTION", e.getMessage());
			log.error("FOR NPCI HTTP CONNECTION_POOL_TIMEOUT_EXCEPTION msg={} ,error={}", e.getMessage(), e);
			ErrorLog.sendError(e, NpciUpiRestConServiceImpl.class);
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			e.printStackTrace();
			ts = System.currentTimeMillis() - ts;
			MonitorLog.npciRestError(ts, apiName, txnId, "HTTP HOST_CONNECT_EXCEPTION", e.getMessage());
			log.error("NPCI HOST_CONNECT_EXCEPTION msg={} ,error={}", e.getMessage(), e);
			ErrorLog.sendError(e, NpciUpiRestConServiceImpl.class);
		} catch (java.net.SocketTimeoutException e) {
			e.printStackTrace();
			ts = System.currentTimeMillis() - ts;
			MonitorLog.npciRestError(ts, apiName, txnId, "HTTP SOCKET_TIMEOUT_EXCEPTION OR ReadTimeOut",
					e.getMessage());
			log.error("NPCI SOCKET_TIMEOUT_EXCEPTION msg={} ,error={}", e.getMessage(), e);
			ErrorLog.sendError(e, NpciUpiRestConServiceImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
			ts = System.currentTimeMillis() - ts;
			MonitorLog.npciRestError(ts, apiName, txnId, "EXCEPTION in send", e.getMessage());
			log.error("error {}", e);
			ErrorLog.sendError(e, NpciUpiRestConServiceImpl.class);
		}
		try {
			if (!isAck) {
				log.error("ACK NOT RECEIVED FOR TXNID= {} ", txnId);
			} else {
				ack = Util.unmarshal(outputSB.toString(), Ack.class);
				log.info("Ack Message Receive from NPCI {}", ack.toString());
				if (null != ack.getErr() || 0 < ack.getErrorMessages().size()) {
					MonitorLog.npciRestErrInAck(ts, apiName, txnId, "Error In Acknowledge", outputSB.toString());
					List<ErrorMessage> errorMessages = ack.getErrorMessages();
					log.info("Error in Ack");
					for (ErrorMessage errorMessage : errorMessages) {
						log.info("Error code {}", errorMessage.getErrorCd());
						log.info("Error code {}", errorMessage.getErrorDtl());
					}
				}
			}
		} catch (Exception e) {
			MonitorLog.npciRestErrInAck(ts, apiName, txnId, "Error In Acknowledge", outputSB.toString());
			log.error("error :{}", e);
			ErrorLog.sendError(e, NpciUpiRestConServiceImpl.class);
		}
		return ack;
	}

	
	
	//static String	ip	= Util.getProperty("UPIIP");

	private static TrustManager[] get_trust_mgr() {
		TrustManager[] certs = new TrustManager[] {
				new X509TrustManager() {
					@Override
					public void checkClientTrusted(X509Certificate[] certs, String t) {}
					
					@Override
					public void checkServerTrusted(X509Certificate[] certs, String t) {}
					
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
				}
		};
		return certs;
	}

// public static Ack send(String data, String apiName, String txnId) {
//
// Ack ack = null;
// StringBuffer outputSB = new StringBuffer();
// try {
// UtilLogger.writeTextFile(outputSB.toString(), "UPISendLogs");
// log.info("Got output from UPI");
// log.debug("This is the output from UPI : " + outputSB.toString());
//
// ack = (Ack) Constant.umAck.unmarshal(new StringReader(
// "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:Ack
// xmlns:ns2=\"http://npci.org/upi/schema/\" api=\"RespPay\"
// reqMsgId=\"PKG458224B97F534A10B85C703B9C45CF00\"
// ts=\"2017-12-20T10:40:05+05:30\"><errorMessages><errorCd>E12</errorCd><errorDtl>Ref
// approvalNum is mandatory text</errorDtl></errorMessages></ns2:Ack>"));
// log.info("Ack Message Receive from NPCI " + ack.toString());
// if (null != ack.getErr() || 0 < ack.getErrorMessages().size()) {
// List<ErrorMessage> errorMessages = ack.getErrorMessages();
// log.info("Error in Ack");
// for (ErrorMessage errorMessage : errorMessages) {
// log.info("Error code " + errorMessage.getErrorCd());
// log.info("Error code " + errorMessage.getErrorDtl());
// }
// }
// } catch (Exception e) {
// StringWriter s;
// e.printStackTrace();
// e.printStackTrace(new PrintWriter(s = new StringWriter()));
// log.error(s);
// }
// return ack;
// }
	/*public static Ack send1(String data, String apiName, String txnId) {

		log.trace("data[" + data + "] apiName[" + apiName + "] txnId[" + txnId + "]");
		StringBuffer outputSB = null;
		Ack ack = null;
		HttpURLConnection conn = null;
		try {
			log.debug("This is the Input to UPI :[" + apiName + "]XML" + data);
			URL url;
			UtilLogger.writeTextFile(data, "UPISendLogs");
			SSLContext ssl_ctx = SSLContext.getInstance("TLS");
			TrustManager[] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null, trust_mgr, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
			String https_url = "https://" + ip + "/upi/" + apiName + "/2.0/urn:txnid:" + txnId;
			log.info("NPCI URL " + https_url);
			url = new URL(https_url);
			conn = (HttpURLConnection) url.openConnection();
			// conn.setHostnameVerifier((host, sess) -> {
			// return true;
			// });
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestProperty("accept", "application/xml");
			conn.setRequestProperty("cache-control", "no-cache");
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();
			int responseCode = conn.getResponseCode();
			log.info("NPCI Response Code on UPI Server is [" + responseCode + "] and apiName[" + apiName + "] txnId["+ txnId + "]");
			InputStream inputStream = null;
			if (responseCode == 200) {
				inputStream = conn.getInputStream();
			} else {
				inputStream = conn.getErrorStream();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String output = null;
			outputSB = new StringBuffer();
			while ((output = br.readLine()) != null) {
				outputSB.append(output);
			}
			UtilLogger.writeTextFile(outputSB.toString(), "UPISendLogs");
			log.info("Got output from UPI");
			log.debug("This is the output from UPI : " + outputSB.toString());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			//log.error(s);
		} finally {
			if (null != conn) {
				conn.disconnect();
				conn = null;
			}
		}
		try {
			Unmarshaller unmarshaller = null;
			unmarshaller = UnMarshalUpi.ackJaxB().createUnmarshaller();
			ack = (Ack) unmarshaller.unmarshal(new StringReader(outputSB.toString()));
			log.info("Ack Message Receive from NPCI " + ack.toString());
			if (null != ack.getErr() || 0 < ack.getErrorMessages().size()) {
				List<ErrorMessage> errorMessages = ack.getErrorMessages();
				log.info("Error in Ack");
				for (ErrorMessage errorMessage : errorMessages) {
					log.info("Error code " + errorMessage.getErrorCd());
					log.info("Error code " + errorMessage.getErrorDtl());
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			//log.error(s);
		}
		return ack;
	}*/
}
