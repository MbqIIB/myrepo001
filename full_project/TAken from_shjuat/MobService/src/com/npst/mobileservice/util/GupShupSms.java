package com.npst.mobileservice.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.apache.log4j.Logger;

public class GupShupSms {
	private static String smsPassword = AESEncryptionUtility.decrypt(Util.getProperty("SMS_PASSWORD"),
			AESEncryptionUtility.secretKeys);
	private static String smsUserid = AESEncryptionUtility.decrypt(Util.getProperty("SMS_USERID"),
			AESEncryptionUtility.secretKeys);
	private static String smsURL = Util.getProperty("GUPSHUP_URL");
	private static final Logger LOG = Logger.getLogger(GupShupSms.class);

	static {
		HttpURLConnection.setFollowRedirects(false);
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}
	
	public static String send(final String mobileNo, final String msg) {
		LOG.info("starting the send method of gupshup api [mobileNo :" + mobileNo + "] and msg [" + msg + "]");
		StringBuffer buffer = new StringBuffer();
		try {
			// Date mydate = new Date(System.currentTimeMillis());
			StringBuilder data = new StringBuilder();
			data.append("method=sendMessage");
			data.append("&userid=" + URLEncoder.encode(smsUserid));
			data.append("&password=" + URLEncoder.encode(smsPassword));
			data.append("&msg=" + URLEncoder.encode(msg, "UTF-8"));
			data.append("&send_to=" + URLEncoder.encode(mobileNo, "UTF-8"));
			data.append("&msg_type=TEXT");
			data.append("&auth_scheme=PLAIN");
			LOG.info("GupShup API URL [" + "]" + smsURL);
			LOG.debug("GupShup API URL [" + "]" + smsURL + data);
			URL url = new URL(smsURL + data);
			SSLContext ssl_ctx = SSLContext.getInstance("TLSv1");
			TrustManager[] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null, trust_mgr, null); // random number generator
			HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod(ConstantI.HTTP_GET_METHOD);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			LOG.debug("Before connect with GUPSHUP_URL:" + new Date());
			// conn.connect();
			LOG.debug("Successfully connected with GUPSHUP_URL:" + new Date());
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				buffer.append(line).append(ConstantI.NEW_LINE_CHAR);
			}
			rd.close();
			// conn.disconnect();
		} catch (Exception e) {
			LOG.error("exception caught at gup shup API [" + e + "]");
			e.printStackTrace();
		}
		LOG.debug("Ending of GupShup API with Response" + buffer.toString());
		return buffer.toString();
	}

	/*
	 * public static void sendEMSNew(String mobileNo, String message) {
	 * LOG.trace("Starting from sendEMSNew with mobileNo:" + mobileNo +
	 * " & message:" + message); LOG.info("Inside the sendEMSNew" + mobileNo);
	 * HttpURLConnection conn = null; OutputStreamWriter writer = null;
	 * BufferedReader reader = null; try { SSLContext ssl_ctx =
	 * SSLContext.getInstance("TLSv1"); TrustManager[] trust_mgr = get_trust_mgr();
	 * ssl_ctx.init(null, trust_mgr, null); // random number generator
	 * HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory()) ;
	 * Map<String, Object> params = new LinkedHashMap<>(); params.put("username",
	 * "CanMlbr2"); params.put("password", "smwlje8"); params.put("destination",
	 * mobileNo); params.put("source", "BX-KGBANK"); params.put("message", message);
	 * StringBuilder postData = new StringBuilder(); for (Map.Entry<String, Object>
	 * param : params.entrySet()) { if (postData.length() != 0)
	 * postData.append('&'); postData.append(URLEncoder.encode(param.getKey(),
	 * "UTF-8")); postData.append('=');
	 * postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
	 * "UTF-8")); } String urlParameters = postData.toString(); URL url = new
	 * URL("https://125.17.165.6/sms?" + urlParameters); conn = (HttpURLConnection)
	 * url.openConnection(); conn.setRequestProperty("Content-Type",
	 * "application/x-www-form-urlencoded");
	 * conn.setRequestProperty("Content-Length", "" +
	 * Integer.toString(urlParameters.getBytes().length));
	 * conn.setRequestProperty("Content-Language", "en-US");
	 * conn.setRequestMethod("GET"); conn.connect(); String result = ""; String
	 * line; reader = new BufferedReader(new
	 * InputStreamReader(conn.getInputStream())); while ((line = reader.readLine())
	 * != null) { result += line; } LOG.info(" result : " + result); //
	 * reader.close(); // System.out.println(result); } catch (Exception e) {
	 * e.printStackTrace(); } finally { if (conn != null) { conn.disconnect(); ; }
	 * try { if (writer != null) { writer.close(); } if (reader != null) {
	 * reader.close(); } } catch (Exception e) { LOG.error("error in sms send --->"
	 * + e); e.printStackTrace(); } } }
	 */ private static TrustManager[] get_trust_mgr() {
		TrustManager[] certs = new TrustManager[] { new X509ExtendedTrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
					throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2)
					throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
					throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2)
					throws CertificateException {
				// TODO Auto-generated method stub

			}
			// () {
			// public X509Certificate[ ] getAcceptedIssuers() { return
			// null; }
			// public void checkClientTrusted(X509Certificate[ ] certs,
			// String t) { }
			// public void checkServerTrusted(X509Certificate[ ] certs,
			// String t) { }
			// }
		} };
		return certs;
	}
}
