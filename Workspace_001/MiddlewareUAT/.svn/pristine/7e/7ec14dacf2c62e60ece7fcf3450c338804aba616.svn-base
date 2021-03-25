package com.npst.middleware.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoapXML2 {
	private static final Logger LOG = LoggerFactory.getLogger(SoapXML2.class);
	/*
	 * public static void main(String[] args) throws Exception {
	 * SoapXML2.sendSoapRequest();
	 * }
	 */
	
	public static void copy(InputStream in, OutputStream out)
			
			throws IOException {
		
		synchronized (in) {
			
			synchronized (out) {
				
				byte[] buffer = new byte[256];
				
				while (true) {
					
					int bytesRead = in.read(buffer);
					
					if (bytesRead == -1) {
						break;
					}
					
					out.write(buffer, 0, bytesRead);
					
				}
				
			}
			
		}
		
	}
	
	public static void sendSoapRequest() throws Exception {
		LOG.trace("sendSoapRequest");
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] {
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(X509Certificate[] certs, String authType) {
						}
						
						@Override
						public void checkServerTrusted(X509Certificate[] certs, String authType) {
						}
						
						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					}
			};
			// Install the all-trusting trust manager
			
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = (hostname, session) -> {
				if (hostname.equals("fin10uat.ccbl.com")) {
					return true;
				} else {
					return false;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			
			e.printStackTrace();
			
		}
		
		String SOAPUrl = "https://fin10uat.ccbl.com:12330/fiwebservice/FIWebService";
		String xmlFile2Send = "/opt/npst/Request.xml";
		
		// Create the connection with http
		
		URL url = new URL(SOAPUrl);
		
		// URLConnection connection = url.openConnection(proxy);
		URLConnection connection = url.openConnection();
		
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		
		FileInputStream fin = new FileInputStream(xmlFile2Send);
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		copy(fin, bout);
		
		fin.close();
		
		byte[] b = bout.toByteArray();
		
		// Set the appropriate HTTP parameters.
		
		httpConn.setRequestProperty("HOST", "fin10uat.ccbl.com");
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("Content-Length", "length");
		
		httpConn.setRequestProperty("SOAPAction", "executeServiceRequest");
		
		httpConn.setRequestMethod("POST");
		
		httpConn.setDoOutput(true);
		
		OutputStream out = httpConn.getOutputStream();
		
		out.write(b);
		
		out.close();
		
		// Read the response.
		
		httpConn.connect();
		
		LOG.debug("http connection status {}", httpConn.getResponseMessage());
		
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
		
		BufferedReader in = new BufferedReader(isr);
		
		String inputLine = "";
		
		while ((inputLine = in.readLine()) != null) {
			inputLine = inputLine.replaceAll("&lt;", "<");
			LOG.debug(inputLine.replaceAll("&gt;", ">"));
		}
		
		in.close();
		
	}
	
	public static String sendSoapRequest(String request) throws Exception {
		LOG.trace("sendSoapRequest {}",request);
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] {
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(X509Certificate[] certs, String authType) {
						}
						
						@Override
						public void checkServerTrusted(X509Certificate[] certs, String authType) {
						}
						
						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					}
			};
			// Install the all-trusting trust manager
			
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = (hostname, session) -> {
				if (hostname.equals("fin10uat.ccbl.com")) {
					return true;
				} else {
					return false;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
		} catch (KeyManagementException e) {
			
			e.printStackTrace();
		}
		//String SOAPUrl = "https://fin10uat.ccbl.com:12330/fiwebservice/FIWebService";
		String SOAPUrl = "https://finacle.ccbl.com:35050/fiwebservice/FIWebService";
		URL url = new URL(SOAPUrl);
		// URLConnection connection = url.openConnection(proxy);
		URLConnection connection = url.openConnection();
		
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		
		httpConn.setRequestProperty("HOST", "fin10uat.ccbl.com");
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("Content-Length", "length");
		
		httpConn.setRequestProperty("SOAPAction", "executeServiceRequest");
		
		httpConn.setRequestMethod("POST");
		
		httpConn.setDoOutput(true);
		try {
			OutputStream out = httpConn.getOutputStream();
			out.write(request.getBytes());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpConn.connect();
		
		LOG.debug("http connection status {}", httpConn.getResponseMessage());
		
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
		
		BufferedReader in = new BufferedReader(isr);
		
		String inputLine = "";
		StringBuffer buffer=new StringBuffer();
		
		/*while ((inputLine = in.readLine()) != null) {
			inputLine = inputLine.replaceAll("&lt;", "<");
			inputLine=inputLine.replaceAll("&gt;", ">");
			//System.out.println(inputLine.replaceAll("&gt;", ">"));
		}*/
		while ((inputLine = in.readLine()) != null) {
			buffer.append(inputLine);
		}
		String temp1=buffer.toString();
		temp1=temp1.replaceAll("&lt;", "<");
		temp1=temp1.replaceAll("&gt;", ">");
		LOG.debug("SoapXML2.java===>>> {}",temp1);
		in.close();
		return temp1;
	}
}
