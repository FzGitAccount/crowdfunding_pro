package com.fz.crowdfunding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.crowdfunding.Controller.BaseController;
import com.fz.crowdfunding.bean.Permission;
import com.fz.crowdfunding.manager.service.PermissionService;
import com.fz.crowdfunding.util.AjaxResult;

@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController{
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/index")
	public String index(){
		return "permission/index";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "permission/add";
	}
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id,Map map){
		Permission permission = permissionService.queryPermissionById(id);
		map.put("permission", permission);
		return "permission/update";
	}
	
	@ResponseBody
	@RequestMapping("/doUpdate")
	public Object doUpdate(Permission permission){
		AjaxResult result = new AjaxResult();
		
		try {
			int count = permissionService.updatePermission(permission);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("许可树生成失败");
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(Permission permission){
		AjaxResult result = new AjaxResult();
		
		try {
			System.out.println(permission.getName());
			int count = permissionService.addPermission(permission);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("许可树生成失败");
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(Integer id){
		start();
		try {
			int count = permissionService.delPermission(id);
			success(count==1);
		} catch (Exception e) {
			success(false);
			e.printStackTrace();
			error("许可数据删除失败");
		}
		return end();
	}
	/*@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(Integer id){
		AjaxResult result = new AjaxResult();
		
		try {
			int count = permissionService.delPermission(id);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("许可数据删除失败");
		}
		
		return result;
	}*/
	
	/*
	 *避免频繁连接数据库，造成的执行效率低下 
	 *将所有数据放入Map集合，然后操作Map集合做逻辑处理，省得频繁连接操作数据库
	 */
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData(){
		AjaxResult result = new AjaxResult();
		
		try {
			List<Permission> root = new ArrayList<Permission>();
			//查询出全部菜单数据
			List<Permission> childredPermission = permissionService.queryAllPermission();
			Map<Integer, Permission> map = new HashMap<Integer,Permission>();
			//将所有菜单数据放入Map集合
			for (Permission p : childredPermission) {
				map.put(p.getId(), p);
			}
			for (Permission permission : childredPermission) {
				//通过子菜单查找父菜单
				//子菜单
				Permission child = permission; //假设为子菜单
				//如果该对象的pid是0,则表示其为根菜单
				if(child.getPid() == 0){
					//放入集合
					root.add(permission);
				}else{
					//根据子菜单的pid得到对应的父菜单对象
					Permission parent = map.get(child.getPid());
					parent.getChildren().add(child);
				}
			}
				
			result.setSuccess(true);
			result.setData(root);//根菜单
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("许可树生成失败");
		}
		
		return result;
	}
	/*@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData(){
		AjaxResult result = new AjaxResult();
		
		try {
			List<Permission> root = new ArrayList<Permission>();
			//查询父节点
			Permission permission = permissionService.getRootPermission();
			root.add(permission);
			queryChildPermissions(permission);
			//查询父节点下的子节点
			List<Permission> children = permissionService.getChildrenPermissionByPid(permission.getId());
			//设置父子关系
			permission.setChildren(children);
			
			for (Permission child : children) {
				child.setOpen(true);
				//查询子节点下的子节点
				List<Permission> innerChildren = permissionService.getChildrenPermissionByPid(child.getId());
				child.setChildren(innerChildren);
			}
			result.setData(root);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("许可树生成失败");
		}
		
		return result;
	}*/
	/**
	 * 递归
	 * @param permission
	 */
	public void queryChildPermissions(Permission permission){
		
		List<Permission> children = permissionService.getChildrenPermissionByPid(permission.getId());
		
		permission.setChildren(children);
		
		for (Permission child : children) {
			queryChildPermissions(child);
		}
	}
	
	/*demo1
	 * @ResponseBody
	@RequestMapping("/loadData")
	public Object loadData(){
		AjaxResult result = new AjaxResult();
		
		try {
			Permission permission = new Permission();
			permission.setName("父节点");
			permission.setOpen(true);
			
			List<Permission> children = new ArrayList<Permission>();
			Permission permission1 = new Permission();
			permission1.setName("子节点1");
			Permission permission2 = new Permission();
			permission2.setName("子节点2");
			children.add(permission1);
			children.add(permission2);
			
			permission.setChildren(children);
			result.setData(permission);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("许可树生成失败");
		}
		
		return result;
	}*/
}
