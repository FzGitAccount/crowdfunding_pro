package com.fz.crowdfunding.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.crowdfunding.bean.Permission;
import com.fz.crowdfunding.manager.dao.PermissionMapper;
import com.fz.crowdfunding.manager.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public Permission getRootPermission() {
		
		return permissionMapper.getRootPermission();
	}

	@Override
	public List<Permission> getChildrenPermissionByPid(Integer id) {
		return permissionMapper.getChildrenPermissionByPid(id);
	}

	@Override
	public List<Permission> queryAllPermission() {
		return permissionMapper.queryAllPermission();
	}

	@Override
	public int addPermission(Permission permission) {
		return permissionMapper.insert(permission);
	}

	@Override
	public Permission queryPermissionById(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updatePermission(Permission permission) {
		return permissionMapper.updateByPrimaryKey(permission);
	}

	@Override
	public int delPermission(Integer id) {
		return permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Integer> permissionidsByRoleid(Integer roleid) {
		return permissionMapper.permissionidsByRoleid(roleid);
	}

}
