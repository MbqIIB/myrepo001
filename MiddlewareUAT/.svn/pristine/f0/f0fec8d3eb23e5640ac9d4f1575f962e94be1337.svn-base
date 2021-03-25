package com.npst.middleware.core.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;


import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.npst.middleware.core.service.HttpSendService;
import com.npst.middleware.util.Util;

@Component
public class HttpSendServiceImpl implements HttpSendService {
	private static final Logger log = LoggerFactory.getLogger(HttpSendServiceImpl.class);

	final String DEFAULT_POST_HEADER = "DEFAULT_POST_HEADER";
	final String DEFAULT_GET_HEADER = "DEFAULT_GET_HEADER";

	// @Value("#{'${HTTP_HEADERS}'.split('\\|\\|')}")
	// private List<String> headersStrArr;

	@Autowired
	private CloseableHttpClient closeableHttpClient;

	private static ConcurrentHashMap<String, Header[]> headersCache = new ConcurrentHashMap<String, Header[]>();

	@Override
	public String sendPost(String reqStr, String url, Header[] header) {
		log.info("Inside Sending Post Req {}", reqStr);
		StringBuffer outputSB = new StringBuffer();
		String resp = "LOCAL_ERROR";
		long ts = 0;
		try {
			HttpPost post = new HttpPost(url);
			post.setHeaders(header);
			StringEntity params = new StringEntity(reqStr);
			post.setEntity(params);
			ts = System.currentTimeMillis();
			try (CloseableHttpResponse response = closeableHttpClient.execute(post);) {
				int responseCode = response.getStatusLine().getStatusCode();
				log.info("HttpPost StatusCode {} ", responseCode);
				try (BufferedReader rd = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));) {
					String line = "";
					while ((line = rd.readLine()) != null) {
						outputSB.append(line);
					}
				}
				ts = System.currentTimeMillis() - ts;
				log.info("POST response : {}" + outputSB.toString());
				resp = outputSB.toString();
				return resp;
			}
		} catch (org.apache.http.conn.ConnectionPoolTimeoutException e) {
			ts = System.currentTimeMillis() - ts;
			log.error("CONNECTION_POOL_TIMEOUT_EXCEPTION POST reqStr={} ,error={}", reqStr, e);
			resp = "CONNECTION_POOL_TIMEOUT_EXCEPTION POST";
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			ts = System.currentTimeMillis() - ts;
			log.error("HTTP_HOST_CONNECT_EXCEPTION POST,reqStr={} ,error={}", reqStr, e);
			resp = "HTTP_HOST_CONNECT_EXCEPTION POST";
		} catch (java.net.SocketTimeoutException e) {
			ts = System.currentTimeMillis() - ts;
			log.error("SOCKET_TIMEOUT_EXCEPTION POST,reqStr={} ,error={}", reqStr, e);
			resp = "SOCKET_TIMEOUT_EXCEPTION";
		} catch (Exception e) {
			ts = System.currentTimeMillis() - ts;
			log.error("EXCEPTION_IN_SEND_HTTP POST REQ reqStr={} ,error {}", reqStr, e);
			resp = "EXCEPTION_IN_SEND_HTTP POST";
		} finally {
			log.info("ELAPSED TIME={} ms", ts);
		}
		return resp;
	}

	private Header[] getHeaders(String property) {
		Header[] headers = headersCache.get(property);
		Header[] headersTemp = null;
		if (headers == null) {
			String headersStr = Util.getProperty(property);
			String[] headersStrArr = null;
			if (StringUtils.isEmpty(headersStr)) {
				headersStrArr = headersStr.split("\\|\\|");
			}
			synchronized (HttpSendServiceImpl.class) {
				log.info("synchronized (HttpSendServiceImpl.class)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				headers = headersCache.get(property);
				if (headers == null) {
					try {
						log.info(">>>>>>>>>>>>>>>>headersStr={}", headersStr);
						if (headersStrArr != null && headersStrArr.length > 0) {
							headersTemp = new Header[headersStrArr.length];
							int i = 0;
							for (String headerStr : headersStrArr) {
								String[] tmp = headerStr.split("\\|");
								headersTemp[i] = new BasicHeader(tmp[0], tmp[1]);
								i++;
							}
							headers = headersTemp;
							headersCache.put(property, headers);
						}
					} catch (Exception e) {
						log.error("ERROR in loading headers ={}", e.getMessage());
						headers = null;
					} finally {
						if (headers == null) {
							log.info("hardcoded headers>>>>>>>>>>");
							headersTemp = new Header[3];
							headersTemp[0] = new BasicHeader("Content-Type", "application/json");
							headersTemp[1] = new BasicHeader("Accept", "application/json");
							headersTemp[2] = new BasicHeader("cache-control", "no-cache");
							headers = headersTemp;
							headersCache.put(property, headers);
						}
						if (headers != null && headers.length > 0) {
							for (int i = 0; i < headers.length; i++) {
								log.info("headers{}={}", i + 1, headers[i]);
							}
						} else {
							log.warn("no headers found >>>>>");
						}
					}
				}
			}
		}
		return headers;
	}

	@Override
	public String sendPostWithDefHeader(String reqStr, String url) {
		return sendPost(reqStr, url, getHeaders(DEFAULT_POST_HEADER));
	}

	@Override
	public String sendGetWithDefHeader(String url) {
		return sendGet(url, getHeaders(DEFAULT_GET_HEADER));
	}

	@Override
	public String sendGet(String url, Header[] header) {
		log.info("Inside Sending Get url= {}", url);
		StringBuffer outputSB = new StringBuffer();
		String resp = "LOCAL_ERROR";
		long ts = 0;
		try {
			HttpGet get = new HttpGet(url);
			get.setHeaders(header);
			ts = System.currentTimeMillis();
			try (CloseableHttpResponse response = closeableHttpClient.execute(get);) {
				int responseCode = response.getStatusLine().getStatusCode();
				log.info("HttpGet StatusCode {} ", responseCode);
				try (BufferedReader rd = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));) {
					String line = "";
					while ((line = rd.readLine()) != null) {
						outputSB.append(line);
					}
				}
				ts = System.currentTimeMillis() - ts;
				log.info("Get response : {}" + outputSB.toString());
				resp = outputSB.toString();
				return resp;
			}
		} catch (org.apache.http.conn.ConnectionPoolTimeoutException e) {
			ts = System.currentTimeMillis() - ts;
			log.error("CONNECTION_POOL_TIMEOUT_EXCEPTION GET, url={} ,error={}", url, e);
			resp = "CONNECTION_POOL_TIMEOUT_EXCEPTION GET";
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			ts = System.currentTimeMillis() - ts;
			log.error("HTTP_HOST_CONNECT_EXCEPTION GET,url={} ,error={}", url, e);
			resp = "HTTP_HOST_CONNECT_EXCEPTION GET";
		} catch (java.net.SocketTimeoutException e) {
			ts = System.currentTimeMillis() - ts;
			log.error("SOCKET_TIMEOUT_EXCEPTION GET,url={} ,error={}", url, e);
			resp = "SOCKET_TIMEOUT_EXCEPTION GET";
		} catch (Exception e) {
			ts = System.currentTimeMillis() - ts;
			log.error("EXCEPTION_IN_SEND_HTTP GET REQ url={} ,error {}", url, e);
			resp = "EXCEPTION_IN_SEND_HTTP GET";
		} finally {
			log.info("ELAPSED TIME={} ms", ts);
		}
		return resp;
	}
}
