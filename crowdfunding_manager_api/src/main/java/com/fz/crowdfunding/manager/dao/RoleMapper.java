package com.fz.crowdfunding.manager.dao;

import com.fz.crowdfunding.bean.Role;
import com.fz.crowdfunding.bean.RolePermission;
import com.fz.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

	List pageQuery(Map map);

	Integer queryCount();

	int batchDelete(@Param("data")Data data);

	Role getRole(Integer id);

	int update(Role role);

	int delPermissionByRoleId(Integer roleid);

	int insertRolePermission(RolePermission rolePermission);
}