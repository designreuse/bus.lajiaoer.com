package com.demo.admin.dao;

import com.demo.model.admin.AdminUser;

public interface AdminUserDao extends AdminBaseDao {

	AdminUser getAdminUser(Long id);

	AdminUser getAdminUser(String name);
}
