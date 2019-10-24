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

import com.fz.crowdfunding.bean.Permission;
import com.fz.crowdfunding.bean.Role;
import com.fz.crowdfunding.manager.service.PermissionService;
import com.fz.crowdfunding.manager.service.RoleService;
import com.fz.crowdfunding.util.AjaxResult;
import com.fz.crowdfunding.util.Page;
import com.fz.crowdfunding.vo.Data;

@RequestMapping("/role")
@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/index")
	public String index(){
		return "role/index";
	}
	
	@RequestMapping("/assignRole")
	public String assignRole(){
		return "role/assignPermission";
	}
	
	@ResponseBody
	@RequestMapping("/loadDataAsync")
	public Object loadDataAsync(Integer roleid){
		
			List<Permission> root = new ArrayList<Permission>();
			//查询出全部菜单数据
			List<Permission> childredPermission = permissionService.queryAllPermission();
			//查询该角色的已分配过的许可，用作回显
			List<Integer> permissionIdsForRoleid = permissionService.permissionidsByRoleid(roleid);
			
			Map<Integer, Permission> map = new HashMap<Integer,Permission>();
			
			//将所有菜单数据放入Map集合
			for (Permission p : childredPermission) {
				map.put(p.getId(), p);
				if(permissionIdsForRoleid.contains(p.getId())){
					//将该角色已分配的许可作为选中状态
					p.setChecked(true);
				}
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
		return root;
	}
	
	@ResponseBody
	@RequestMapping("/doIndex")
	public Object doIndex(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno,
			@RequestParam(value="pagesize",required=false,defaultValue="15") Integer pagesize,String queryText){
		AjaxResult result = new AjaxResult();
		try {
			Map map = new HashMap();
			map.put("pageno", pageno);
			map.put("pagesize", pagesize);
			if(queryText!=null && !queryText.equals("")){
				map.put("queryText", queryText);
			}
			Page page = roleService.pagingQueryRole(map);
			result.setPage(page);//将查询的结果封装到page对象中，然后将page对象放入AjaxResult对象
			result.setSuccess(true);
			result.setMessage("查询成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("查询异常！");
		}
		return result;
	}
	@RequestMapping("/add")
	public String add(){
		return "role/add";
	}
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(Role role){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.addRole(role);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setMessage("角色添加失败！");
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(Integer id){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.delRole(id);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setMessage("角色删除失败！");
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/doDeleteBatch")
	public Object doDeleteBatch(Data data){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.deleteBatchRole(data);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setMessage("角色删除失败！");
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id,Map map){
		Role role = roleService.getRoleById(id);
		map.put("role",role);
		return "role/edit";
	}
	@ResponseBody
	@RequestMapping("/doEdit")
	public Object doEdit(Role role){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.updateRole(role);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setMessage("角色修改失败！");
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doAssignPermission")
	public Object doAssignPermission(Integer roleid,Data ids){
		
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.saveRolePermission(roleid,ids);
			result.setSuccess(count==ids.getIds().size());
		} catch (Exception e) {
			result.setMessage("该角色许可权限分配失败！");
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
}
