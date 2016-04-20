package com.demo.admin.service;

import com.demo.model.admin.AdminUser;

public interface AdminUserService {

	boolean login(String name, String password);

	AdminUser getAdminUser(String name);
}
