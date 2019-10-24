package com.fz.crowdfunding.manager.dao;

import com.fz.crowdfunding.bean.Permission;
import com.fz.crowdfunding.bean.Role;
import com.fz.crowdfunding.bean.User;
import com.fz.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
    //查询验证用户登录信息
	User queryUserlogin(Map<String, Object> paramMap);
	//查询用户数据
	//静态传递
	//mybatis在传入多个参数时，必须添加@Param注解，并且注解参数名要@Param("startIndex")和传入Mapper文件中的条件参数名一致#{startIndex}
	List queryList(@Param("startIndex") Integer startIndex,@Param("pagesize") Integer pagesize);
	
	//动态传递参数方式
	List queryList(Map<String,Object> paramMap);
	//查询总记录数
	Integer queryCount(Map<String, Object> paramMap);
	//静态
	Integer queryCount();

	List<Role> queryRoleList();

	List<Integer> queryRoleListById(Integer id);

	int saveUserRoleRelationship(@Param("userid") Integer userid, @Param("data") Data data);

	int deleteUserRoleRelationship(@Param("userid") Integer userid, @Param("data") Data data);

	List<Permission> queryPermissionByUserId(Integer userid);

}