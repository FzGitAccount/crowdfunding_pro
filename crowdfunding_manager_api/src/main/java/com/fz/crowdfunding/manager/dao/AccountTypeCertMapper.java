package com.fz.crowdfunding.manager.dao;

import java.util.List;

import com.fz.crowdfunding.bean.AccountTypeCert;

public interface AccountTypeCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    AccountTypeCert selectByPrimaryKey(Integer id);

    List<AccountTypeCert> selectAll();

    int updateByPrimaryKey(AccountTypeCert record);
}