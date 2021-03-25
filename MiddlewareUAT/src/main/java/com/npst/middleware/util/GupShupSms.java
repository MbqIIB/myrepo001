package com.npst.middleware.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GupShupSms
{
	private static String smsPassword = AESEncryptionUtility.decrypt(Util.getProperty("SMS_PASSWORD"), AESEncryptionUtility.secretKeys);
	private static String smsURL = Util.getProperty("GUPSHUP_URL");
	private static final Logger LOG = LoggerFactory.getLogger(GupShupSms.class);

	static
	{
		HttpsURLConnection.setFollowRedirects(false);
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
		{
			public boolean verify(String hostname, SSLSession session)
			{
				return true;
			}
		});
	}

	public String send(final String mobileNo, final String msg)
	{
		LOG.info("starting the send method of gupshup api for mobileNo {} with msg {} ",mobileNo ,msg);
		StringBuffer buffer = new StringBuffer();
		try
		{
			// Date mydate = new Date(System.currentTimeMillis());
			StringBuilder data = new StringBuilder();
			data.append("method=sendMessage");
			data.append("&userid=2000043506");
			data.append("&password=" + URLEncoder.encode(smsPassword));
			data.append("&msg=" + URLEncoder.encode(msg, "UTF-8"));
			data.append("&send_to=" + URLEncoder.encode(mobileNo, "UTF-8"));
			data.append("&msg_type=TEXT");
			data.append("&auth_scheme=PLAIN");
			URL url = new URL(smsURL + data);
			SSLContext ssl_ctx = SSLContext.getInstance("TLSv1");
			TrustManager[] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null, trust_mgr, null); // random number generator
			HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod(ConstantNew.HTTP_GET_METHOD);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			LOG.debug("Before connect with GUPSHUP_URL at {} ",new Date());
			// conn.connect();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
			{
				buffer.append(line).append(ConstantNew.NEW_LINE_CHAR);
			}
			rd.close();
			// conn.disconnect();
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e );
		}
		LOG.info("Ending of GupShup API with Response as {} ",buffer.toString());
		return buffer.toString();
	}

	private static TrustManager[] get_trust_mgr()
	{
		TrustManager[] certs = new TrustManager[]
		{ new X509ExtendedTrustManager()
		{
			@Override
			public X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{

			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2) throws CertificateException
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2) throws CertificateException
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException
			{
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
