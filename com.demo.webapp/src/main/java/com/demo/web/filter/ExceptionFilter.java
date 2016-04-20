package com.demo.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.enums.RestResultTypeEnum;
import com.demo.utils.StringUtils;
import com.demo.vo.RestResult;
import com.fasterxml.jackson.databind.ObjectMapper;

//@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class ExceptionFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

	/**
	 * Default constructor.
	 */
	public ExceptionFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		logger.debug("销毁exception过滤");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		HtmlResponseWrapper capturingResponseWrapper = new HtmlResponseWrapper((HttpServletResponse) response);
		try {
			chain.doFilter(request, capturingResponseWrapper);
			String content = capturingResponseWrapper.getCaptureAsString();
			response.getWriter().write(content);
		} catch (Exception e) {
			// if (response.getContentType() != null && response.getContentType().contains("text/html")) {
			//

			// replace stuff here
			// String replacedContent = "abc";// content.replaceAll("", "<h3>$1 - HTML replaced</h3>");

			// System.out.println(replacedContent);

			// response.getWriter().write(replacedContent);

			// }
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			String strHeader = resp.getHeader("x-requested-with");
			if (StringUtils.isNotBlank(strHeader) && StringUtils.equalsIgnoreCase(strHeader, "XMLHttpRequest")) {// ajax request
				RestResult restResult = new RestResult();
				restResult.setCode(RestResultTypeEnum.ERROR.getCode());
				restResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
				String strJson = new ObjectMapper().writeValueAsString(restResult);
				response.getWriter().write(strJson);
			} else if (StringUtils.containsAny(req.getQueryString(), "jsoncallback=", "callback=")) {
				String strCallback = req.getParameter("jsoncallback");
				if (StringUtils.isBlank(strCallback)) {
					strCallback = req.getParameter("callback");
					if (StringUtils.isBlank(strCallback)) {
						request.getRequestDispatcher("/500").forward(request, response);
						return;
					}
				}

				RestResult restResult = new RestResult();
				restResult.setCode(RestResultTypeEnum.ERROR.getCode());
				restResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
				String strJson = new ObjectMapper().writeValueAsString(restResult);
				response.setContentType("application/javascript");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(strCallback + "(" + strJson + ")");
			} else {
				request.getRequestDispatcher("/500").forward(request, response);
			}
		}
		// pass the request along the filter chain
		// HttpServletRequest req = (HttpServletRequest) request;
		// HttpServletResponse resp = (HttpServletResponse) response;
		// try {
		// chain.doFilter(request, response);
		// } catch (Exception e) {
		//
		// String strHeader = req.getHeader("x-requested-with");
		// if (StringUtils.isNotBlank(strHeader) && StringUtils.equalsIgnoreCase(strHeader, "XMLHttpRequest")) {// ajax request
		// RestResult restResult = new RestResult();
		// restResult.setCode(RestResultTypeEnum.ERROR.getCode());
		// restResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
		// String strJson = new ObjectMapper().writeValueAsString(restResult);
		// response.getWriter().print(strJson);
		// response.getWriter().flush();
		// } else if (StringUtils.containsAny(req.getQueryString(), "jsoncallback=", "callback=")) {
		// String strCallback = req.getParameter("jsoncallback");
		// if (StringUtils.isBlank(strCallback)) {
		// strCallback = req.getParameter("callback");
		// if (StringUtils.isBlank(strCallback)) {
		// req.getRequestDispatcher("/500").forward(req, resp);
		// return;
		// }
		// }
		//
		// RestResult restResult = new RestResult();
		// restResult.setCode(RestResultTypeEnum.ERROR.getCode());
		// restResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
		// String strJson = new ObjectMapper().writeValueAsString(restResult);
		// resp.setContentType("application/javascript;charset=UTF-8");
		// resp.getOutputStream().write((strCallback + "(" + strJson + ")").getBytes("UTF-8"));
		// } else {
		// req.getRequestDispatcher("/500").forward(req, resp);
		// }
		// }

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.debug("初始化exception过滤");
	}

	class HtmlResponseWrapper extends HttpServletResponseWrapper {

		private final ByteArrayOutputStream capture;
		private ServletOutputStream output;
		private PrintWriter writer;

		public HtmlResponseWrapper(HttpServletResponse response) {
			super(response);
			capture = new ByteArrayOutputStream(response.getBufferSize());
		}

		@Override
		public ServletOutputStream getOutputStream() {
			if (writer != null) {
				throw new IllegalStateException("getWriter() has already been called on this response.");
			}

			if (output == null) {
				output = new ServletOutputStream() {
					@Override
					public void write(int b) throws IOException {
						capture.write(b);
					}

					@Override
					public void flush() throws IOException {
						capture.flush();
					}

					@Override
					public void close() throws IOException {
						capture.close();
					}

					@Override
					public boolean isReady() {
						return false;
					}

					@Override
					public void setWriteListener(WriteListener arg0) {
					}
				};
			}

			return output;
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (output != null) {
				throw new IllegalStateException("getOutputStream() has already been called on this response.");
			}

			if (writer == null) {
				writer = new PrintWriter(new OutputStreamWriter(capture, getCharacterEncoding()));
			}

			return writer;
		}

		@Override
		public void flushBuffer() throws IOException {
			super.flushBuffer();

			if (writer != null) {
				writer.flush();
			} else if (output != null) {
				output.flush();
			}
		}

		public byte[] getCaptureAsBytes() throws IOException {
			if (writer != null) {
				writer.close();
			} else if (output != null) {
				output.close();
			}

			return capture.toByteArray();
		}

		public String getCaptureAsString() throws IOException {
			return new String(getCaptureAsBytes(), getCharacterEncoding());
		}

	}
}
