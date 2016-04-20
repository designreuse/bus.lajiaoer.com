package com.demo.rest.front;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.demo.captcha.DCage;
import com.demo.rest.BaseRestService;
import com.demo.vo.RestResult;
import com.github.cage.Cage;
import com.sun.mail.util.BASE64EncoderStream;

@Path("/captcha")
public class CaptchaRestService extends BaseRestService {
	private static final Cage cage = new DCage();
	private static final String PRE_JPEG = "data:image/jpg;base64,";

	@GET
	@Path("/generate")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public RestResult generateCaptcha() {
		RestResult restResult = new RestResult();
		// this.enabledCrossDomain(response);
		final String token = cage.getTokenGenerator().next();

		byte[] b = cage.draw(token);
		byte[] o = BASE64EncoderStream.encode(b);
		final String strBase64 = new String(o);
		final String strAllBase64 = new StringBuilder(PRE_JPEG).append(strBase64).toString();

		Map<String, String> captchaMap = new HashMap<String, String>();
		captchaMap.put("token", token);
		captchaMap.put("base64", strAllBase64);

		restResult.setData(captchaMap);
		return restResult;
	}

	public static void main(String[] args) {
		final String token = cage.getTokenGenerator().next();

		byte[] b = cage.draw(token);
		byte[] o = BASE64EncoderStream.encode(b);
		final String strBase64 = new String(o);
		System.out.println(new StringBuilder(PRE_JPEG).append(strBase64).toString());
	}
}
