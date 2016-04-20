package com.demo.job.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.EmailException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.service.UserSignupEmailService;
import com.demo.service.impl.UserSignupEmailServiceImpl;
import com.demo.utils.ConfigUtils;
import com.demo.utils.EmailUtils;
import com.demo.utils.StringUtils;
import com.demo.vo.UserVo;

public class UserSignupEmailJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(UserSignupEmailJob.class);

	private UserSignupEmailService signupEmailService = UserSignupEmailServiceImpl.getInstance();

	private static final String SIGNUP_CONFIRM_HTML;

	static {
		try {
			String filePath = UserSignupEmailJob.class.getClassLoader().getResource("template/signup.html").getFile();
			filePath = URLDecoder.decode(filePath, "UTF-8");
			File file = new File(filePath);
			SIGNUP_CONFIRM_HTML = FileUtils.readFileToString(file, "UTF-8");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件没找到异常", e);
		} catch (IOException e) {
			throw new RuntimeException("IO异常", e);
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.debug("执行时间：{}", Calendar.getInstance().getTime());
		List<UserVo> listUserVo = signupEmailService.querySchedulerByLimit(20);

		if (CollectionUtils.isEmpty(listUserVo)) {
			return;
		}

		Long seId = null;
		try {
			for (UserVo userVo : listUserVo) {
				String strEmail = userVo.getEmail();
				String strUserName = userVo.getUserName();
				String token = userVo.getToken();
				seId = userVo.getSeId();
				Map<String, String> params = new HashMap<String, String>();
				params.put("siteName", ConfigUtils.getStringValue("web.title"));
				params.put("userName", strUserName);
				params.put("token", token);
				EmailUtils.sendHtmlEmail(strEmail, "[" + ConfigUtils.getStringValue("web.title") + "]请确认您的注册邮件", replaceHtml(SIGNUP_CONFIRM_HTML, params));
				signupEmailService.updateSended(seId);
			}
		} catch (EmailException e) {
			logger.error("发送邮件异常={}", e);
			signupEmailService.updateSendCount(seId);
		}
	}

	private String replaceHtml(String html, Map<String, String> params) {
		if (MapUtils.isEmpty(params)) {
			return null;
		}

		List<String> searchList = new LinkedList<String>();
		List<String> replacementList = new LinkedList<String>();
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String val = entry.getValue();
			searchList.add("${" + key + "}");
			replacementList.add(val);
		}

		return StringUtils.replaceEachRepeatedly(html, searchList.toArray(new String[] {}), replacementList.toArray(new String[] {}));
	}

}
