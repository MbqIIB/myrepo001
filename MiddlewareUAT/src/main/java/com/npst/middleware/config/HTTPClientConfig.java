package com.npst.middleware.config;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = { "httprestconnection" })
public class HTTPClientConfig {
	private static final Logger log = LoggerFactory.getLogger(HTTPClientConfig.class);

	// The timeout when requesting a connection from the connection manager.
	@Value("${CONNECTION_REQUEST_TIMEOUT}")
	private int connectionRequestTimeout;
	// Determines the timeout in milliseconds until a connection is established.
	@Value("${CONNECT_TIMEOUT}")
	private int connectTimeout;
	// The timeout for waiting for data
	@Value("${SOCKET_TIMEOUT}")
	private int socketTimeout;

	@Value("${MAX_TOTAL_CONNECTIONS}")
	private int maxTotalConnections;

	@Value("${DEFAULT_KEEP_ALIVE_TIME_MILLIS}")
	private int DEFAULT_KEEP_ALIVE_TIME_MILLIS;

	@Value("${Default_Max_PerRoute}")
	private int defaultMaxPerRoute;

	@Primary
	@Bean
	public PoolingHttpClientConnectionManager poolingConnectionManager() {
		// SSLContextBuilder builder = new SSLContextBuilder();
		// try {
		// builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		// } catch (NoSuchAlgorithmException | KeyStoreException e) {
		// LOGGER.error("Pooling Connection Manager Initialisation failure because of "
		// + e.getMessage(), e);
		// }
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext ssl_ctx = SSLContext.getInstance("TLS");
			TrustManager[] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null, trust_mgr, new SecureRandom());
			sslsf = new SSLConnectionSocketFactory(ssl_ctx, (hostname, session) -> true);
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			log.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
		}
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		poolingConnectionManager.setMaxTotal(maxTotalConnections);
		poolingConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		return poolingConnectionManager;
	}

	@Primary
	@Bean
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		log.trace("");
		return new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				System.out.println("inside ConnectionKeepAliveStrategy connectionKeepAliveStrategy()");
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
				System.out.println("DEFAULT_KEEP_ALIVE_TIME_MILLIS=" + DEFAULT_KEEP_ALIVE_TIME_MILLIS);
				System.out.println("End ConnectionKeepAliveStrategy connectionKeepAliveStrategy()");
				return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
			}
		};
	}

	@Primary
	@Bean
	RequestConfig requestConfig() {
		log.trace("");
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
		return requestConfig;
	}
	
	@Primary
	@Bean
	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingConnectionManager,
			RequestConfig requestConfig, ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
		log.trace("");
		CloseableHttpClient closeableHttpClient_ = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolingConnectionManager).setKeepAliveStrategy(connectionKeepAliveStrategy)
				.build();
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
