package com.fz.crowdfunding.manager.service;

import java.util.Map;

import com.fz.crowdfunding.bean.Role;
import com.fz.crowdfunding.util.Page;
import com.fz.crowdfunding.vo.Data;

public interface RoleService {

	Page pagingQueryRole(Map map);

	int addRole(Role role);

	int delRole(Integer id);

	int deleteBatchRole(Data data);

	Role getRoleById(Integer id);

	int updateRole(Role role);

	int saveRolePermission(Integer roleid, Data ids);

	

}
