package com.fz.crowdfunding.manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fz.crowdfunding.bean.Permission;


public interface PermissionService {

	Permission getRootPermission();

	List<Permission> getChildrenPermissionByPid(Integer id);

	List<Permission> queryAllPermission();

	int addPermission(Permission permission);

	Permission queryPermissionById(Integer id);

	int updatePermission(Permission permission);

	int delPermission(Integer id);

	List<Integer> permissionidsByRoleid(Integer roleid);

	
	

}
