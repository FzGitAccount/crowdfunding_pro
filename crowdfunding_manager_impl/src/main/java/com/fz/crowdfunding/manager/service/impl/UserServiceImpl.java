package com.fz.crowdfunding.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.crowdfunding.bean.Permission;
import com.fz.crowdfunding.bean.Role;
import com.fz.crowdfunding.bean.User;
import com.fz.crowdfunding.exception.LoginFailException;
import com.fz.crowdfunding.manager.dao.UserMapper;
import com.fz.crowdfunding.manager.service.UserService;
import com.fz.crowdfunding.util.Const;
import com.fz.crowdfunding.util.MD5Util;
import com.fz.crowdfunding.util.Page;
import com.fz.crowdfunding.vo.Data;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User queryUserlogin(Map<String, Object> paramMap) {
		User user = userMapper.queryUserlogin(paramMap);
		if(user==null){
			throw new LoginFailException("登录失败，用户账号验证不通过");
		}
		return user;
	}
	
	@Override
	public Page queryUserPage(Map<String,Object> paramMap) {
		Page page = new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		
		//获取索引
		Integer startIndex = page.getStartIndex();
		paramMap.put("startIndex", startIndex);
		//根据传入的索引和查询条数，获得数据集合
		List datas = userMapper.queryList(paramMap);
		//将数据集合传入page对象的datas属性
		page.setDatas(datas);
		//查询总记录数
		Integer totalsize = userMapper.queryCount(paramMap);
		//放入page对象的totalsize属性
		page.setTotalsize(totalsize);
		
		return page;
	}

	@Override
	public int saveUser(User user) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String createTime = sdf.format(date);
		user.setCreatetime(createTime);
		user.setUserpswd(MD5Util.digest(Const.PASSWORD));
		return userMapper.insert(user);
	}

	@Override
	public Page queryUserPage(Integer pageno, Integer pagesize) {

		Page page = new Page(pageno,pagesize);
		
		//获取索引
		Integer startIndex = page.getStartIndex();
		//根据传入的索引和查询条数，获得数据集合
		List datas = userMapper.queryList(startIndex,pagesize);
		//将数据集合传入page对象的datas属性
		page.setDatas(datas);
		//查询总记录数
		Integer totalsize = userMapper.queryCount();
		//放入page对象的totalsize属性
		page.setTotalsize(totalsize);
		
		return page;
	
	}

	@Override
	public User getUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateUser(User user) {
		
		return userMapper.updateByPrimaryKey(user);
	}

	@Override
	public int deleteUser(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBatchUser(Integer[] ids) {
		int totalCount = 0;
		for (Integer id : ids) {
			//返回删除条数
			int count = userMapper.deleteByPrimaryKey(id);
			totalCount +=count;
		}
		//如果删除条数不等于传入的id数组长度，则删除失败，抛出异常并数据回滚
		if(totalCount!=ids.length){
			throw new RuntimeException("删除失败");
		}
		return totalCount;
	}

	@Override
	public List<Role> queryRoleList() {
		return userMapper.queryRoleList();
	}

	@Override
	public List<Integer> queryRoleListById(Integer id) {
		return userMapper.queryRoleListById(id);
	}

	@Override
	public int saveUserRoleRelationship(Integer userid, Data data) {
		return userMapper.saveUserRoleRelationship(userid,data);
	}

	@Override
	public int deleteUserRoleRelationship(Integer userid, Data data) {
		return userMapper.deleteUserRoleRelationship(userid,data);
	}

	@Override
	public List<Permission> queryPermissionByUserId(Integer userid) {
		return userMapper.queryPermissionByUserId(userid);
	}

}
