package com.fz.crowdfunding.manager.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.crowdfunding.manager.dao.TestDao;
import com.fz.crowdfunding.manager.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestDao testDao;
	@Override
	public void insert() {
		Map map = new HashMap();
		map.put("id", 2);
		map.put("name", "张三");
		try {
			testDao.insert(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
