package com.demo.web.jsptag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.utils.ConfigUtils;
import com.demo.utils.StringUtils;

public class JspTitleTag extends SimpleTagSupport {

	private static final Logger logger = LoggerFactory.getLogger(JspTitleTag.class);

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void doTag() throws JspException, IOException {
		logger.debug("doTag 开始");
		JspWriter out = getJspContext().getOut();
		if (StringUtils.isNotBlank(name)) {
			logger.debug("doTag name={}", name);
			out.print(name);
		} else {
			final String strPropTitle = ConfigUtils.getStringValue("web.title");
			logger.debug("doTag strPropTitle={}", strPropTitle);
			out.print(strPropTitle);
		}
		logger.debug("doTag 结束");
	}
}
