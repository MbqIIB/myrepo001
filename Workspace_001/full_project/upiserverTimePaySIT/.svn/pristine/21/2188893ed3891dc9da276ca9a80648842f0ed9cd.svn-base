package com.npst.upiserver.config;

import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiddlewareConConfig {

	private static final Logger log = LoggerFactory.getLogger(NpciRestConnectionConfig.class);
	// The timeout when requesting a connection from the connection manager.
	@Value("${MIDDLEWARE_CONNECTION_REQUEST_TIMEOUT}")
	private int connectionRequestTimeout;
	// Determines the timeout in milliseconds until a connection is established.
	@Value("${MIDDLEWARE_CONNECT_TIMEOUT}")
	private int connectTimeout;
	// The timeout for waiting for data
	@Value("${MIDDLEWARE_SOCKET_TIMEOUT}")
	private int socketTimeout;

	@Value("${MIDDLEWARE_MAX_TOTAL_CONNECTIONS}")
	private int maxTotalConnections;

	@Value("${MIDDLEWARE_DEFAULT_KEEP_ALIVE_TIME_MILLIS}")
	private int DEFAULT_KEEP_ALIVE_TIME_MILLIS;

	@Value("${MIDDLEWARE_DEFAULT_MAX_PERROUTE}")
	private int defaultMaxPerRoute;

	@Bean("middlewarePoolingHttpClientConnectionManager")
	public PoolingHttpClientConnectionManager poolingConnectionManager() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext ssl_ctx = SSLContext.getInstance("TLS");
			TrustManager[] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null, trust_mgr, new SecureRandom());
			sslsf = new SSLConnectionSocketFactory(ssl_ctx, (hostname, session) -> true);
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			log.error("MIDDLEWARE Pooling Connection Manager creation error {}", e);
		}
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		poolingConnectionManager.setMaxTotal(maxTotalConnections);
		poolingConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		return poolingConnectionManager;
	}

	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		log.trace("");
		return new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				log.info("inside middleware ConnectionKeepAliveStrategy connectionKeepAliveStrategy()");
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();

					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				log.info("middleware DEFAULT_KEEP_ALIVE_TIME_MILLIS={}", DEFAULT_KEEP_ALIVE_TIME_MILLIS);
				return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
			}
		};
	}

	RequestConfig requestConfig() {
		log.trace("");
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
		return requestConfig;
	}

	@Bean("middlewareCloseableHttpClient")
	public CloseableHttpClient httpClient(
			@Qualifier("middlewarePoolingHttpClientConnectionManager") PoolingHttpClientConnectionManager middlewarePoolingConnectionManager) {
		log.info("start building middlewareCloseableHttpClient");
		RequestConfig requestConfig = requestConfig();
		ConnectionKeepAliveStrategy connectionKeepAliveStrategy = connectionKeepAliveStrategy();
		log.info("middleware requestConfig {}", requestConfig);
		log.info("middleware connectionKeepAliveStrategy {}", connectionKeepAliveStrategy);
		log.info("middleware PoolingHttpClientConnectionManager {}", middlewarePoolingConnectionManager);
		CloseableHttpClient closeableHttpClient_ = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(middlewarePoolingConnectionManager).setKeepAliveStrategy(connectionKeepAliveStrategy)
				.build();
		log.info("middleware closeableHttpClient_ {}", closeableHttpClient_);
		return closeableHttpClient_;
	}

	private static TrustManager[] get_trust_mgr() {
		log.trace("");
		TrustManager[] certs = new TrustManager[] { new X509ExtendedTrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1, Socket arg2)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1, SSLEngine arg2)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1, Socket arg2)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1, SSLEngine arg2)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

		} };
		return certs;
	}
}
