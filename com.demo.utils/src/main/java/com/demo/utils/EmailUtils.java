package com.demo.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Assert;

/**
 * 发邮件工具
 * 
 * @author xuzhongliang
 *
 */
public class EmailUtils {

	private static Configuration configuration = null;

	private static String strEmailCharset = "UTF-8";

	private static String strHostName = null;

	private static String strUserName = null;

	private static String strPassword = null;

	private static String strEmailFrom = null;

	private static String strEmailFromName = null;

	static {
		try {
			configuration = new PropertiesConfiguration(EmailUtils.class.getClassLoader().getResource("config/email.properties"));
			strEmailCharset = configuration.getString("email.charset");
			strHostName = configuration.getString("email.hostname");
			Assert.assertNotNull("主机名为空", strHostName);
			strUserName = configuration.getString("email.username");
			Assert.assertNotNull("邮箱用户名为空", strUserName);
			strPassword = configuration.getString("email.password");
			Assert.assertNotNull("邮箱密码为空", strPassword);
			strEmailFrom = configuration.getString("email.from");
			Assert.assertNotNull("邮箱发件人地址为空", strEmailFrom);
			strEmailFromName = configuration.getString("email.from.name");
			Assert.assertNotNull("邮箱发件人名称为空", strEmailFromName);
		} catch (ConfigurationException e) {
			throw new Error("加载配置文件失败");
		}
	}

	public static final void sendEmail(String email, String subject, String msg) throws EmailException {
		SimpleEmail simpleEmail = new SimpleEmail();
		simpleEmail.setHostName(strHostName);
		simpleEmail.setAuthentication(strUserName, strPassword);
		simpleEmail.addTo(email, email, strEmailCharset);
		simpleEmail.setFrom(strEmailFrom, strEmailFromName, strEmailCharset);
		simpleEmail.setSubject(subject);
		simpleEmail.setCharset(strEmailCharset);
		simpleEmail.setMsg(msg);
		simpleEmail.send();
	}

	public static final void sendHtmlEmail(String email, String subject, String html) throws EmailException {
		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setHostName(strHostName);
		htmlEmail.setAuthentication(strUserName, strPassword);
		htmlEmail.addTo(email, email, strEmailCharset);
		htmlEmail.setFrom(strEmailFrom, strEmailFromName, strEmailCharset);
		htmlEmail.setSubject(subject);
		htmlEmail.setCharset(strEmailCharset);
		htmlEmail.setHtmlMsg(html);
		htmlEmail.send();
	}

	// public static final void sendHtmlEmail(String[] emails, String subject, String html) throws EmailException, UnsupportedEncodingException {
	// HtmlEmail htmlEmail = new HtmlEmail();
	// htmlEmail.setHostName("smtp.qq.com");
	// htmlEmail.setAuthentication("", "");
	// Collection<InternetAddress> internetAddresses = new LinkedList<InternetAddress>();
	// for (String email : emails) {
	// InternetAddress internetAddress = new InternetAddress(email, email, "UTF-8");
	// internetAddresses.add(internetAddress);
	// }
	// htmlEmail.setsetTo(internetAddresses);
	// htmlEmail.setFrom("", "");
	// htmlEmail.setSubject(subject);
	// htmlEmail.setCharset("UTF-8");
	// htmlEmail.setHtmlMsg(html);
	// htmlEmail.send();
	// }

	public static void main(String[] args) throws EmailException, UnsupportedEncodingException {
		sendHtmlEmail("zhongliang.xu@vipshop.com", "天天更新2", "<a href=\"\">天天更新2</a>");
	}
}
