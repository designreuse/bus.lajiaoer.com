package com.demo.rest.admin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.glassfish.jersey.server.JSONP;

import com.demo.admin.service.AdminDriverService;
import com.demo.admin.service.impl.AdminDriverServiceImpl;
import com.demo.core.dynamic.DynamicInvocation;
import com.demo.enums.RestResultTypeEnum;
import com.demo.enums.ResultType;
import com.demo.model.Driver;
import com.demo.model.DriverAudit;
import com.demo.model.DriverPlace;
import com.demo.rest.BaseRestService;
import com.demo.utils.StringUtils;
import com.demo.vo.RestResult;

@Path("/admin/drivers")
@Produces("application/javascript;charset=UTF-8")
public class AdminDriversRestService extends BaseRestService {

	private AdminDriverService driverService = new DynamicInvocation(AdminDriverServiceImpl.class).getProxy();

	/**
	 * 列表
	 * 
	 * @param q
	 * @param offset
	 * @param limit
	 * @return
	 */
	@GET
	@JSONP(callback = "jsoncallback", queryParam = "jsoncallback")
	public RestResult list(@DefaultValue("") @QueryParam("q") String q, @DefaultValue("0") @QueryParam("s") Short offset, @DefaultValue("10") @QueryParam("n") Short limit) {
		RestResult restResult = new RestResult();

		List<Driver> listDrivers = driverService.queryByPage(offset, limit);

		restResult.setData(listDrivers);
		return restResult;
	}

	/**
	 * 获取
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public RestResult get(@PathParam("id") Long id) {
		RestResult restResult = new RestResult();
		if (null == id || id <= 0) {
			resultWrapper(restResult, AdminDriverEnum.PARAM_IS_NULL);
			return restResult;
		}

		try {
			Driver driver = driverService.getDriverById(id);
			// Driver newDriver = new Driver();
			// newDriver.setActived(driver.getActived());
			// newDriver.setUsed(driver.getUsed());
			// newDriver.setId(id);
			restResult.setData(driver);
		} catch (Exception e) {
			resultWrapper(restResult, RestResultTypeEnum.ERROR);
		}
		return restResult;
	}

	@GET
	@Path("/simple")
	@JSONP(callback = "jsoncallback", queryParam = "jsoncallback")
	public RestResult simple(@QueryParam("id") Long id) {
		RestResult restResult = new RestResult();
		if (null == id || id <= 0) {
			resultWrapper(restResult, AdminDriverEnum.PARAM_IS_NULL);
			return restResult;
		}

		try {
			Driver driver = driverService.getDriverById(id);
			Driver newDriver = new Driver();
			newDriver.setActived(driver.getActived());
			newDriver.setUsed(driver.getUsed());
			newDriver.setId(id);
			restResult.setData(newDriver);
		} catch (Exception e) {
			resultWrapper(restResult, RestResultTypeEnum.ERROR);
		}
		return restResult;
		// throw new RuntimeException();
	}

	/**
	 * 更新actived状态
	 * 
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/actived")
	public RestResult updateActived(@FormParam("id") Long id) {
		RestResult restResult = new RestResult();
		if (null == id || id <= 0) {
			resultWrapper(restResult, AdminDriverEnum.PARAM_IS_NULL);
			return restResult;
		}

		try {
			Driver driver = driverService.getDriverById(id);
			if (null == driver) {
				resultWrapper(restResult, AdminDriverEnum.DRIVER_IS_NULL);
				return restResult;
			}

			if (driver.getActived() == 1) {
				resultWrapper(restResult, AdminDriverEnum.DRIVER_IS_ACTIVED);
				return restResult;
			}
			int rows = driverService.updateActivedById(id);
			if (rows <= 0) {
				logger.error("updateActivedById's rows is {}", rows);
				throw new RuntimeException("执行更新actived不正确");
			}
			return restResult;
		} catch (Exception e) {
			logger.error("更新actived异常", e);
			resultWrapper(restResult, RestResultTypeEnum.ERROR);
		}
		return restResult;
	}

	/**
	 * 更新used禁用状态
	 * 
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/forbidUsed")
	public RestResult forbidUsed(@FormParam("id") Long id) {
		RestResult restResult = new RestResult();
		if (null == id || id <= 0) {
			resultWrapper(restResult, AdminDriverEnum.PARAM_IS_NULL);
			return restResult;
		}

		try {
			Driver driver = driverService.getDriverById(id);
			if (null == driver) {
				resultWrapper(restResult, AdminDriverEnum.DRIVER_IS_NULL);
				return restResult;
			}

			if (driver.getUsed() == 0) {
				resultWrapper(restResult, AdminDriverEnum.DRIVER_IS_FORBID);
				return restResult;
			}
			int rows = driverService.forbidById(id);
			if (rows <= 0) {
				logger.error("forbidUsed's rows is {}", rows);
				throw new RuntimeException("执行更新forbidUsed不正确");
			}
			return restResult;
		} catch (Exception e) {
			logger.error("更新used异常", e);
			resultWrapper(restResult, RestResultTypeEnum.ERROR);
		}
		return restResult;
	}

	/**
	 * 更新used启用状态
	 * 
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/used")
	public RestResult canUsed(@FormParam("id") Long id) {
		RestResult restResult = new RestResult();
		if (null == id || id <= 0) {
			resultWrapper(restResult, AdminDriverEnum.PARAM_IS_NULL);
			return restResult;
		}

		try {
			Driver driver = driverService.getDriverById(id);
			if (null == driver) {
				resultWrapper(restResult, AdminDriverEnum.DRIVER_IS_NULL);
				return restResult;
			}

			if (driver.getUsed() == 1) {
				resultWrapper(restResult, AdminDriverEnum.DRIVER_IS_USED);
				return restResult;
			}
			int rows = driverService.usedById(id);
			if (rows <= 0) {
				logger.error("canUsed rows is {}", rows);
				throw new RuntimeException("执行更新canUsed不正确");
			}
			return restResult;
		} catch (Exception e) {
			logger.error("更新used异常", e);
			resultWrapper(restResult, RestResultTypeEnum.ERROR);
		}
		return restResult;
	}

	@GET
	@Path("/place")
	public RestResult listPlace(@QueryParam("cityid") final String cityId) {
		RestResult restResult = new RestResult();

		if (StringUtils.isBlank(cityId)) {
			List<DriverPlace> listDriverPlace = driverService.queryDriverPlaceByPage((short) 0, (short) 100);
			restResult.setData(listDriverPlace);
		} else {
			DriverPlace driverPlace = driverService.getDriverPlace(Long.valueOf(cityId));
			restResult.setData(driverPlace);
		}

		return restResult;
	}

	@PUT
	@Path("/place")
	public RestResult updateDriverPlaceId(@FormParam("id") final String id, @FormParam("dpId") final String driverPlaceId) {
		RestResult restResult = new RestResult();

		if (StringUtils.isAnyBlank(id, driverPlaceId)) {
			resultWrapper(restResult, AdminDriverEnum.PARAM_IS_NULL);
			return restResult;
		}

		driverService.updateDriverPlaceAndAudit(Long.valueOf(id), Long.valueOf(driverPlaceId));
		return restResult;
	}

	/**
	 * 获取司机审核信息
	 * 
	 * @param driverId
	 * @return
	 */
	@GET
	@Path("/audit")
	public RestResult getDriverAudit(@QueryParam("dId") final Long driverId) {
		RestResult restResult = new RestResult();

		if (null == driverId) {
			resultWrapper(restResult, AdminDriverEnum.PARAM_IS_NULL);
			return restResult;
		}

		DriverAudit driverAudit = driverService.getDriverAudit(driverId);
		if (null == driverAudit) {
			resultWrapper(restResult, AdminDriverEnum.AUDIT_IS_NULL);
			return restResult;
		}
		short score = 100;
		if (driverAudit.getCell() != 1) {
			score -= 5;
		}
		if (driverAudit.getName() != 1) {
			score -= 5;
		}
		if (driverAudit.getLicensePlate() != 1) {
			score -= 5;
		}
		if (driverAudit.getSfzA() != 1) {
			score -= 5;
		}
		if (driverAudit.getSfzB() != 1) {
			score -= 5;
		}
		if (driverAudit.getJszA() != 1) {
			score -= 5;
		}
		if (driverAudit.getXszA() != 1) {
			score -= 5;
		}
		if (driverAudit.getDriverPlaceId() != 1) {
			score -= 5;
		}

		Driver driver = driverService.getDriverById(driverId);
		if (null == driver) {
			resultWrapper(restResult, AdminDriverEnum.DRIVER_IS_NULL);
			return restResult;
		}

		if (driver.getActived() == 0) {
			score -= 10;
		}
		if (driver.getUsed() == 0) {
			score -= 100;
		}

		if (score < 0) {
			score = 0;
		}
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		resultMap.put("score", String.valueOf(score));
		resultMap.put("audit", driverAudit);
		restResult.setData(resultMap);
		return restResult;
	}

	@PUT
	@Path("/audit")
	public RestResult updateDriverPlaceId(@FormParam("dId") final Long driverId, @BeanParam DriverAudit driverAudit) {
		RestResult restResult = new RestResult();

		if (null == driverId) {
			resultWrapper(restResult, AdminDriverEnum.PARAM_IS_NULL);
			return restResult;
		}

		driverAudit.setDriverId(driverId);
		driverService.updateDriverAudit(driverAudit);
		return restResult;
	}

	/**
	 * 状态枚举
	 * 
	 * @author xuzhongliang
	 *
	 */
	private static enum AdminDriverEnum implements ResultType {
		PARAM_IS_NULL(10001, "参数不能为空"), DRIVER_IS_NULL(10002, "数据不存在"), DRIVER_IS_ACTIVED(10003, "已经激活状态"), DRIVER_IS_USED(10004, "已经是启用状态"), DRIVER_IS_FORBID(10005,
				"已经是禁用状态"), AUDIT_IS_NULL(10006, "审核数据不存在");
		private AdminDriverEnum(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		private int code;
		private String msg;

		@Override
		public int getCode() {
			return code;
		}

		@Override
		public String getMsg() {
			return msg;
		}

	}
}
