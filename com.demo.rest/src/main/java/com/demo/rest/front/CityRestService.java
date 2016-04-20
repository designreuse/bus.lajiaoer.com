package com.demo.rest.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.enums.RestResultTypeEnum;
import com.demo.model.City;
import com.demo.rest.BaseRestService;
import com.demo.service.CityService;
import com.demo.service.impl.CityServiceImpl;
import com.demo.vo.RestResult;

@Path("/city")
public class CityRestService extends BaseRestService {

	private CityService cityService = new DynamicInvocation(CityServiceImpl.class).getProxy();

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public RestResult getCity(@CookieParam("cityid") final Short id) {
		RestResult restResult = new RestResult();

		if (null == id) {
			resultWrapper(restResult, RestResultTypeEnum.ERROR);
			return restResult;
		}

		City city = cityService.get(id);// from redis
		Map<String, City> mapData = new HashMap<String, City>();
		mapData.put("city", city);
		restResult.setData(mapData);

		return restResult;
	}

	@Path("list")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public RestResult listCity() {
		RestResult restResult = new RestResult();
		List<City> listCity = cityService.queryAllCities();

		restResult.setData(listCity);
		return restResult;
	}
}
