package com.fz.crowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.crowdfunding.bean.Role;
import com.fz.crowdfunding.bean.RolePermission;
import com.fz.crowdfunding.manager.dao.RoleMapper;
import com.fz.crowdfunding.manager.service.RoleService;
import com.fz.crowdfunding.util.Page;
import com.fz.crowdfunding.vo.Data;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Page pagingQueryRole(Map map) {
		Page page = new Page((Integer)map.get("pageno"), (Integer)map.get("pagesize"));//将pageno和pagesize传入page对象的构造函数
		Integer startIndex = page.getStartIndex();//获取查询索引，limit(startIndex,pagesize),从索引位置到要查询的条数
		map.put("startIndex",startIndex);
		List datas = roleMapper.pageQuery(map);
		page.setDatas(datas);//将查询数据结果放入page对象
		Integer count = roleMapper.queryCount();//查询总记录数
		page.setTotalsize(count);
		return page;
	}

	@Override
	public int addRole(Role role) {
		return roleMapper.insert(role);
	}

	@Override
	public int delRole(Integer id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBatchRole(Data data) {
		return roleMapper.batchDelete(data);
	}

	@Override
	public Role getRoleById(Integer id) {
		
		return roleMapper.getRole(id);
	}

	@Override
	public int updateRole(Role role) {
		
		return roleMapper.update(role);
	}

	@Override
	public int saveRolePermission(Integer roleid, Data ids) {
		int totalCount = 0;
		roleMapper.delPermissionByRoleId(roleid);
		List<Integer> datas = ids.getIds();
		for (Integer permissionid : datas) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionid(permissionid);
			rolePermission.setRoleid(roleid);
			int count = roleMapper.insertRolePermission(rolePermission);
			totalCount += count;
		};
		return totalCount;
	}
	

}
