package com.npst.mobileservice.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.InitiateRequestDao;
import com.npst.mobileservice.object.ReqResp;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.service.AuditMobileHomeService;
import com.npst.mobileservice.service.RegistrationHomeService;
import com.npst.mobileservice.util.AesEncryption;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.InitiateRequest;

public class FilterSecurePspCon implements Filter {
	private static AesEncryption aESEncryptionUtility = null;
	private static com.npst.mobileservice.service.SecurityEncryptionService securityEncryptionService = null;

	private class BufferedRequestWrapper extends HttpServletRequestWrapper {

		ByteArrayInputStream bais;
		ByteArrayOutputStream baos;
		BufferedServletInputStream bsis;

		byte[] buffer;

		public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
			super(req);
			InputStream is = req.getInputStream();
			baos = new ByteArrayOutputStream();
			byte buf[] = new byte[1024];
			int letti;
			while ((letti = is.read(buf)) > 0) {
				baos.write(buf, 0, letti);
			}
			buffer = baos.toByteArray();
		}

		public byte[] getBuffer() {
			return buffer;
		}

		@Override
		public ServletInputStream getInputStream() {
			try {
				bais = new ByteArrayInputStream(buffer);
				bsis = new BufferedServletInputStream(bais);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return bsis;
		}

	}

	private class BufferedServletInputStream extends ServletInputStream {

		ByteArrayInputStream bais;

		public BufferedServletInputStream(ByteArrayInputStream bais) {
			this.bais = bais;
		}

		@Override
		public int available() {
			return bais.available();
		}

		@Override
		public int read() {
			return bais.read();
		}

		@Override
		public int read(byte[] buf, int off, int len) {
			return bais.read(buf, off, len);
		}

	}

	private static class ByteArrayPrintWriter {

		private ByteArrayOutputStream baos = new ByteArrayOutputStream();
		private PrintWriter pw = new PrintWriter(baos);
		private ServletOutputStream sos = new ByteArrayServletStream(baos);

		public ServletOutputStream getStream() {
			return sos;
		}

		public PrintWriter getWriter() {
			return pw;
		}

		byte[] toByteArray() {
			return baos.toByteArray();
		}
	}

	private static class ByteArrayServletStream extends ServletOutputStream {

		ByteArrayOutputStream baos;

		ByteArrayServletStream(ByteArrayOutputStream baos) {
			this.baos = baos;
		}

		@Override
		public void write(int param) throws IOException {
			baos.write(param);
		}
	}

	private static final Logger log = Logger.getLogger(FilterSecurePspCon.class);
	private RegistrationHomeService registrationHomeService = null;
	private AuditMobileHomeService auditMobileHomeService = null;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		String resp = null;
		Date reqDate = new Date();
		RespJson respJson = new RespJson();
		ReqResp reqResp = null;
		if (null == auditMobileHomeService) {
			auditMobileHomeService = new AuditMobileHomeService();
		}
		final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		XSSRequestWrapper wrappedRequest = new XSSRequestWrapper(httpRequest);
		final ByteArrayPrintWriter pw = new ByteArrayPrintWriter();
		HttpServletResponse wrappedResp = new HttpServletResponseWrapper(response) {
			@Override
			public ServletOutputStream getOutputStream() {
				return pw.getStream();
			}

			@Override
			public PrintWriter getWriter() {
				return pw.getWriter();
			}
		};

		String url = "", authToken = "", reqStr = "", encryptionKey = "", deviceId = "", serverToken = "",source="",node="";
		String  encAuthToken = "", encEncryptionKey = "", encDeviceId = "",encSource="",encNode="", encRegId="";
		try {
			url = httpRequest.getPathInfo();
			log.info(url);
		/*	authToken = httpRequest.getHeader("authToken");
			encryptionKey = httpRequest.getHeader("perkey");
			deviceId = httpRequest.getHeader("deviceId");
			serverToken=httpRequest.getHeader("deviceId");
			source = httpRequest.getHeader("sapp");
			node=httpRequest.getHeader("snode");*/
			
			encAuthToken = httpRequest.getHeader("authToken");
			encEncryptionKey = httpRequest.getHeader("perkey");
			encDeviceId = httpRequest.getHeader("deviceId");
			encSource =  httpRequest.getHeader("sapp");
			encNode =  httpRequest.getHeader("snode");
			encRegId=httpRequest.getHeader("sopay");
			encEncryptionKey=httpRequest.getHeader("encKey");
			log.info("Encrpted authToken[" + encAuthToken + "] Encrpted perkey[" + encryptionKey + "] Encrpted deviceId[" + encDeviceId + "] Url:["+ url + "] EncryptionKey:["+ encEncryptionKey +"]");
			
			reqStr = IOUtils.toString(wrappedRequest.getReader());
			log.info("encrypted REQUEST -> " + reqStr);
			if (null == aESEncryptionUtility) {
				aESEncryptionUtility = new AesEncryption();
			}
			if (null == reqResp) {
				reqResp = new ReqResp();
			}
			reqResp = JSONConvert.getReqResp(reqStr);
			reqStr = reqResp.getData();
			
			String encKey=ConstantI.KEY_DECRY_HEADER;
			//Decryption by Shujat
			try {
				authToken = aESEncryptionUtility.decrypt(encAuthToken, encKey);
				encryptionKey = aESEncryptionUtility.decrypt(encEncryptionKey, encKey);
				deviceId = aESEncryptionUtility.decrypt(encDeviceId, encKey);
				if(deviceId!=null) {
					deviceId=deviceId.trim();
				}
				log.info("Decrypted deviceId=" + deviceId);
				source = aESEncryptionUtility.decrypt(encSource, encKey);
				node = aESEncryptionUtility.decrypt(encNode, encKey);
			} catch (Exception e) {
				StringWriter s;
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				e.printStackTrace();
				log.error(s);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
				String finalRespJson = "";
				finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
				reqResp = new ReqResp();
				reqResp.setData(finalRespJson);
				response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
			}
			log.info("serverToken="+serverToken+"authToken[" + authToken + "] perkey[" + encryptionKey + "] deviceId[" + deviceId + "] Url:["+ url + "]");
			try {
						wrappedRequest.resetInputStream(reqStr.getBytes());
						filterChain.doFilter(wrappedRequest, wrappedResp);
						byte[] bytes = pw.toByteArray();
						resp = new String(bytes);
						log.debug("RESPONSE -> " + resp);
						String finalRespJson = "";
						finalRespJson = resp;
						reqResp = new ReqResp();
						reqResp.setData(finalRespJson);
						response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
						log.info("RESPONSE -> " + reqResp);
			} catch (Exception e) {
				StringWriter s;
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				e.printStackTrace();
				log.error(s);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
				String finalRespJson = "";
				resp = JSONConvert.getJsonStr(respJson);
				finalRespJson = resp;
				reqResp = new ReqResp();
				reqResp.setData(finalRespJson);
				response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
				log.info("RESPONSE -> " + reqResp);
			}
			 
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error("END ERROR ,may be response send ," + s);
		}
		finally {
			if (null != resp) {
				auditMobileHomeService.auditLogs(reqStr, resp, reqDate, new Date(), url, authToken,source,node);
			} else {
				auditMobileHomeService.auditLogs(reqStr, JSONConvert.getJsonStr(reqResp), reqDate, new Date(), url,authToken,source,node);
			}
			response.getOutputStream().close();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (null == registrationHomeService) {
			registrationHomeService = new RegistrationHomeService();
		}
		if (null == auditMobileHomeService) {
			auditMobileHomeService = new AuditMobileHomeService();
		}
	}

}
