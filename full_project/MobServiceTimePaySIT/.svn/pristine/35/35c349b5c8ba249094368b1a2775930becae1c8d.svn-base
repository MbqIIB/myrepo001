package com.npst.mobileservice.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

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

import org.apache.log4j.Logger;

import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.service.AuditMobileHomeService;
import com.npst.mobileservice.service.RegistrationHomeService;

public class LogFilter implements Filter {
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

	private static final Logger log = Logger.getLogger(LogFilter.class);
	private RegistrationHomeService registrationHomeService = null;
	private AuditMobileHomeService auditMobileHomeService = null;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		String resp = null;
		RespJson respJson = new RespJson();
		final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpRequest);
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
		String url = "", reqStr = "";
		try {
			
			filterChain.doFilter(bufferedRequest, wrappedResp);
			byte[] bytes = pw.toByteArray();
			resp = new String(bytes);
			response.getOutputStream().write(resp.getBytes());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
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
