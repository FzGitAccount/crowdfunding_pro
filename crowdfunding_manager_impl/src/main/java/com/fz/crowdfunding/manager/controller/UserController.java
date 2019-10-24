package com.fz.crowdfunding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.crowdfunding.bean.Role;
import com.fz.crowdfunding.bean.User;
import com.fz.crowdfunding.manager.service.UserService;
import com.fz.crowdfunding.util.AjaxResult;
import com.fz.crowdfunding.util.Page;
import com.fz.crowdfunding.util.StringUtil;
import com.fz.crowdfunding.vo.Data;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	/***
	 * 异步查询
	 */
	@RequestMapping("index")
	public String index(){
		return "user/ybindex";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "user/add";
	}
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id,Map map){
		User user = userService.getUserById(id);
		map.put("user",user);
		return "user/update";
	}
	@ResponseBody
	@RequestMapping("/doIndex")
	//默认参数
	public Object doIndex(@RequestParam(value="pageno",required=false,defaultValue="1")  Integer pageno, 
			@RequestParam(value="pagesize",required=false,defaultValue="15") Integer pagesize,
			String queryText){
		AjaxResult result = new AjaxResult();
		try {
			Map paramMap = new HashMap();
			paramMap.put("pageno", pageno);
			paramMap.put("pagesize", pagesize);
			if(StringUtil.isNotEmpty(queryText)){
				paramMap.put("queryText", queryText);
			}
			Page page = userService.queryUserPage(paramMap);
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("数据查询失败！");
		}
		return result;
	}
	/*@RequestMapping("/index")
	//默认参数
	public String index(@RequestParam(value="pageno",required=false,defaultValue="1")  Integer pageno, 
			@RequestParam(value="pagesize",required=false,defaultValue="15") Integer pagesize, Map map){
		
		Page page = userService.queryUserPage(pageno,pagesize);
		
		map.put("page", page);
		
		return "user/index";
	}*/
	/**
	 * 添加用户
	 * @param pageno
	 * @param pagesize
	 * @param queryText
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doAdd")
	//直接定义参数类型为user对象，框架会自动将前台数据封装到User对象中，前提是属性名一致
	public Object doAdd(User user){
		AjaxResult result = new AjaxResult();
		try {
			int count = userService.saveUser(user);
			//count=1返回ture，=0返回false
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("新增用户失败！");
		}
		return result;
	}
	
	/**
	 * 修改用户信息
	 * @param User
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doUpdate")
	public Object doUpdate(User user){
		AjaxResult result = new AjaxResult();
		try {
			int count = userService.updateUser(user);
			//count=1返回ture，=0返回false
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("修改信息失败！");
		}
		return result;
	}
	
	/**
	 * 删除用户信息
	 * @param User
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(Integer id){
		AjaxResult result = new AjaxResult();
		try {
			int count = userService.deleteUser(id);
			//count=1返回ture，=0返回false
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("删除用户失败！");
		}
		return result;
	}
	/**
	 * 批量删除用户信息
	 * @param User
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDeleteBatch")
	public Object doDeleteBatch(Integer[] id){
		AjaxResult result = new AjaxResult();
		try {
			int count = userService.deleteBatchUser(id);
			//count=1返回ture，count=0返回false
			result.setSuccess(count==id.length);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("删除用户失败！");
		}
		return result;
	}
	/**
	 * 用户权限管理页面跳转
	 */
	@RequestMapping("/assignRole")
	public String assignRole(Integer id,Map map){
		//查询所有角色
		List<Role> queryAllRoleList = userService.queryRoleList();
		//查询选中的用户已分配的角色
		List<Integer> queryRoleListById = userService.queryRoleListById(id);
		//未分配角色
		List<Role> unassignedRoleList = new ArrayList<Role>();
		//已分配角色
		List<Role> existingRoleList = new ArrayList<Role>();
		
		//循环所有角色的集合
		for (Role role : queryAllRoleList) {
			//判断已分配角色集合中是否包含相同的id，如果包含则放入已分配集合中,否则就放入未分配集合中
			if(queryRoleListById.contains(role.getId())){
				existingRoleList.add(role);
			}else{
				unassignedRoleList.add(role);
			}
		}
		map.put("wfpList", unassignedRoleList);
		map.put("yfpList",existingRoleList);
		
		return "user/assignRole";
	}
	/**
	 *给用户分配角色 
	 */
	@ResponseBody
	@RequestMapping("/doAssignRole")
	public Object doAssignRole(Integer userid,Data data){
		AjaxResult result = new AjaxResult();
		try {
			userService.saveUserRoleRelationship(userid,data);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("分配用户角色失败！");
		}
		return result;
	}
	
	/**
	 *取消用户角色 
	 */
	@ResponseBody
	@RequestMapping("/doUnAssignRole")
	public Object doUnAssignRole(Integer userid,Data data){
		AjaxResult result = new AjaxResult();
		try {
			userService.deleteUserRoleRelationship(userid,data);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("删除用户角色失败！");
		}
		return result;
	}
}
