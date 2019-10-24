package com.fz.crowdfunding.manager.dao;

import com.fz.crowdfunding.bean.Permission;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Permission record);

	Permission getRootPermission();

	List<Permission> getChildrenPermissionByPid(Integer id);

	List<Permission> queryAllPermission();

	List<Integer> permissionidsByRoleid(Integer roleid);
}