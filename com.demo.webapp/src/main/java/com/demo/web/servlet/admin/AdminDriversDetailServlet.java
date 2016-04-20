package com.demo.web.servlet.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import com.demo.enums.RestResultTypeEnum;
import com.demo.utils.ConfigUtils;
import com.demo.utils.StringUtils;
import com.demo.vo.RestResult;
import com.demo.web.servlet.AdminBaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/admin/drivers/detail" })
public class AdminDriversDetailServlet extends AdminBaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String MAIN_PATH = "/WEB-INF/jsp/admin/drivers/drivers_detail.jsp";

	/**
	 * Default constructor.
	 */
	public AdminDriversDetailServlet() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = getJspParameter(request, "id");
		if (StringUtils.isBlank(id)) {
			logger.error("id is null");
			throw new ServletException("id is null");
		}
		final String strHost = ConfigUtils.getStringValue("rest.host");
		JerseyClient jerseyClient = JerseyClientBuilder.createClient();
		RestResult restResult = jerseyClient.target(strHost).path("/admin").path("/drivers").path(id).request().get(RestResult.class);
		Object driver = restResult.getData();
		if (restResult.getCode() != RestResultTypeEnum.SUCCESS.getCode() || null == driver) {
			throw new ServletException();
		}

		RestResult driverAuditRestResult = jerseyClient.target(strHost).path("admin").path("drivers").path("audit").queryParam("dId", id).request().get(RestResult.class);
		Object objDriverAudit = driverAuditRestResult.getData();
		if (restResult.getCode() != RestResultTypeEnum.SUCCESS.getCode() || null == objDriverAudit) {
			throw new ServletException();
		}

		RestResult driverPlaceRestResult = jerseyClient.target(strHost).path("admin").path("drivers").path("place").request().get(RestResult.class);
		Object objDriverPlaceList = driverPlaceRestResult.getData();
		if (restResult.getCode() != RestResultTypeEnum.SUCCESS.getCode() || null == objDriverPlaceList) {
			throw new ServletException();
		}
		List<Map<String, String>> listDriverPlace = (List<Map<String, String>>) objDriverPlaceList;
		MultivaluedMap<String, Map<String, String>> multiValuedMap = new MultivaluedHashMap<String, Map<String, String>>();
		for (Map<String, String> dpMap : listDriverPlace) {
			multiValuedMap.add(dpMap.get("aCityName"), dpMap);
		}
		setJspAttribute(request, "dplace", multiValuedMap);
		setJspAttribute(request, "driver", driver);
		setJspAttribute(request, "auditResult", objDriverAudit);
		setJspDispatcher(request, response, MAIN_PATH);
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
