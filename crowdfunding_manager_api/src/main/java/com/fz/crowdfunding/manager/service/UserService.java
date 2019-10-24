package com.fz.crowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import com.fz.crowdfunding.bean.Permission;
import com.fz.crowdfunding.bean.Role;
import com.fz.crowdfunding.bean.User;
import com.fz.crowdfunding.util.Page;
import com.fz.crowdfunding.vo.Data;

public interface UserService {

	User queryUserlogin(Map<String, Object> paramMap);
	//Deprecated表示方法已过时
	@Deprecated
	Page queryUserPage(Integer pageno, Integer pagesize);

	int saveUser(User user);
	
	Page queryUserPage(Map<String,Object> paramMap);
	User getUserById(Integer id);
	int updateUser(User user);
	int deleteUser(Integer id);
	int deleteBatchUser(Integer[] ids);
	List<Role> queryRoleList();
	List<Integer> queryRoleListById(Integer id);
	int saveUserRoleRelationship(Integer userid, Data data);
	int deleteUserRoleRelationship(Integer userid, Data data);
	List<Permission> queryPermissionByUserId(Integer userid);

}
