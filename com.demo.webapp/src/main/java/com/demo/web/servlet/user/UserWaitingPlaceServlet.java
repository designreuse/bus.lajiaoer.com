package com.demo.web.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/user/waiting/place" })
public class UserWaitingPlaceServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private static final String WAITING_PLACE_PATH = "/WEB-INF/jsp/user/waiting_place.jsp";

	private static final Logger LOGGER = LoggerFactory.getLogger(UserWaitingPlaceServlet.class);

	// private DriverPlaceService driverPlaceService = new DynamicInvocation(DriverPlaceServiceImpl.class).getProxy();
	// private DriverWaitingService driverWaitingService = new DynamicInvocation(DriverWaitingServiceImpl.class).getProxy();

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("进入");
		/*
		 * Cookie cookie = CookieUtils.findCookieByName(request, "cityid"); final String strCityId = (null != cookie ? cookie.getValue() : "1"); DriverPlace params = new
		 * DriverPlace(); params.setaCityId(Short.valueOf(strCityId));
		 * 
		 * List<DriverPlace> listDriverPlace = driverPlaceService.queryDriverPlace(params); List<Map<String, Object>> listDriverWaiting =
		 * driverWaitingService.queryMaxDriverWaiting(Short.valueOf(strCityId));
		 * 
		 * List<Map<String, Object>> listWaiting = new ArrayList<Map<String, Object>>(); Map<String, Object> map = null; if (!CollectionUtils.sizeIsEmpty(listDriverPlace)) { for
		 * (DriverPlace driverPlace : listDriverPlace) { map = new HashMap<String, Object>(); // Map<String, Object> togetherMap = new HashMap<String, Object>(); //
		 * togetherMap.put("driverPlace", driverPlace); map.put("driverPlace", driverPlace);
		 * 
		 * if (!CollectionUtils.sizeIsEmpty(listDriverWaiting)) { for (Map<String, Object> dwMap : listDriverWaiting) { String driverPlaceId =
		 * String.valueOf(dwMap.get("driver_place_id")); if (driverPlaceId.equals(String.valueOf(driverPlace.getId()))) {// same place // if
		 * (!togetherMap.containsKey("driverWaiting")) { // togetherMap.put("driverWaiting", dwMap);// first element // togetherMap.put("waitingCount",
		 * CollectionUtils.size(listDriverWaiting));// waiting total count map.put("driverWaiting", dwMap); map.put("waitingCount", dwMap.get("total_waiting")); break; // } } } }
		 * listWaiting.add(map); } } setJspAttribute(request, "listWaiting", listWaiting);
		 */
		setJspDispatcher(request, response, WAITING_PLACE_PATH);
		LOGGER.debug("跳转完成");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doJspGet(request, response);
	}

}
