package com.npst.middleware.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.apache.log4j.Logger;

public class SMSWorker  {
	private static final Logger LOG = Logger.getLogger(SMSWorker.class);
	static {
	    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
	        {
	            public boolean verify(String hostname, SSLSession session)
	            {
	                return true;
	            }
	        });
	}

	
	public  void sendEMSNew(String mobileNo,String message){
		LOG.trace("sendEMSNew");
		LOG.info("Inside the sendEMSNew" +mobileNo );
		  HttpURLConnection conn = null;
		  OutputStreamWriter writer = null;
		  BufferedReader reader =null;
		  try{
		  SSLContext ssl_ctx = SSLContext.getInstance("TLSv1");
		        TrustManager[ ] trust_mgr = get_trust_mgr();
		        ssl_ctx.init(null,trust_mgr,null); // random number generator
		        HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());
		  Map<String,Object> params = new LinkedHashMap<>();
		     params.put("username","CanMlbr2");
		     params.put("password","smwlje8");
		     params.put("destination",mobileNo);
		     params.put("source", "BX-"+Util.getProperty("SMS_BANK_NAME")+"BANK");
		     params.put("message",message);
		     StringBuilder postData = new StringBuilder();
		     for (Map.Entry<String,Object> param : params.entrySet()) {
		         if (postData.length() != 0) postData.append('&');
		         postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		         postData.append('=');
		         postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		     }
		     String urlParameters = postData.toString();
		     URL url = new URL("https://125.17.165.6/sms?" + urlParameters);
		     conn = (HttpURLConnection)url.openConnection();
		     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        conn.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		     conn.setRequestProperty("Content-Language", "en-US"); 
		     conn.setRequestMethod("GET");
		     conn.connect();
		     String result = "";
		     String line;
		     reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		     while ((line = reader.readLine()) != null) {
		         result += line;
		     }
		     LOG.info(" result : "+ result);
//		     reader.close();
//		     System.out.println(result);
		     }catch(Exception e){
		      e.printStackTrace();
		     }finally{
		      if(conn!=null){
		       conn.disconnect();;
		      }
		      try{
		      if(writer!=null){
		       writer.close();
		      }
		      if(reader!=null){
		       reader.close();
		      }
		      }catch(Exception e){
		    	  LOG.error("error in sms send --->"+e);
		    	  e.printStackTrace();}
		     }
		  
		     
		 }
	
	private static TrustManager[ ] get_trust_mgr() {
	     TrustManager[ ] certs = new TrustManager[ ] {
	        new X509ExtendedTrustManager() {
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
				public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
//			() {
//	           public X509Certificate[ ] getAcceptedIssuers() { return null; }
//	           public void checkClientTrusted(X509Certificate[ ] certs, String t) { }
//	           public void checkServerTrusted(X509Certificate[ ] certs, String t) { }
//	         }
	      }};
	      return certs;
	  }

}
