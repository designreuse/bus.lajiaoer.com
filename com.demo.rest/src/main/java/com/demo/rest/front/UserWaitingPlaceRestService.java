package com.demo.rest.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections4.CollectionUtils;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.model.Driver;
import com.demo.model.DriverPlace;
import com.demo.rest.BaseRestService;
import com.demo.service.DriverPlaceService;
import com.demo.service.DriverService;
import com.demo.service.DriverWaitingService;
import com.demo.service.impl.DriverPlaceServiceImpl;
import com.demo.service.impl.DriverServiceImpl;
import com.demo.service.impl.DriverWaitingServiceImpl;
import com.demo.vo.RestResult;

@Path("/user/waiting/place")
public class UserWaitingPlaceRestService extends BaseRestService {

	private DriverPlaceService driverPlaceService = new DynamicInvocation(DriverPlaceServiceImpl.class).getProxy();
	private DriverWaitingService driverWaitingService = new DynamicInvocation(DriverWaitingServiceImpl.class).getProxy();
	private DriverService driverService = new DynamicInvocation(DriverServiceImpl.class).getProxy();

	@GET
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	public RestResult getWaitingPlace(@DefaultValue("1") @CookieParam("cityid") Short cityId, @Context HttpServletResponse response) {
		RestResult restResult = new RestResult();
		// Short realCityId = (null == cityId || cityId < 1 ? 1 : cityId);
		DriverPlace params = new DriverPlace();
		params.setaCityId(cityId);

		List<DriverPlace> listDriverPlace = driverPlaceService.queryDriverPlace(params);
		List<Map<String, Object>> listDriverWaiting = driverWaitingService.queryMaxDriverWaiting(cityId);

		List<Map<String, Object>> listWaiting = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (!CollectionUtils.sizeIsEmpty(listDriverPlace)) {
			for (DriverPlace driverPlace : listDriverPlace) {
				map = new HashMap<String, Object>();
				// Map<String, Object> togetherMap = new HashMap<String, Object>();
				// togetherMap.put("driverPlace", driverPlace);
				map.put("driverPlace", driverPlace);

				if (!CollectionUtils.sizeIsEmpty(listDriverWaiting)) {
					for (Map<String, Object> dwMap : listDriverWaiting) {
						String driverPlaceId = String.valueOf(dwMap.get("driver_place_id"));
						if (driverPlaceId.equals(String.valueOf(driverPlace.getId()))) {// same place
							// if (!togetherMap.containsKey("driverWaiting")) {
							// togetherMap.put("driverWaiting", dwMap);// first element
							// togetherMap.put("waitingCount", CollectionUtils.size(listDriverWaiting));// waiting total count
							map.put("driverWaiting", dwMap);
							map.put("waitingCount", dwMap.get("total_waiting"));
							String strDriverId = String.valueOf(dwMap.get("driver_id"));
							Driver driver = driverService.get(Long.valueOf(strDriverId));
							Map<String, String> mapDriver = new LinkedHashMap<String, String>(1);
							mapDriver.put("licensePlate", driver.getLicensePlate());
							map.put("driver", mapDriver);
							break;
							// }
						}
					}
				}
				listWaiting.add(map);
			}
		}

		restResult.setData(listWaiting);

		// this.enabledCrossDomain(response);
		return restResult;
	}
}
