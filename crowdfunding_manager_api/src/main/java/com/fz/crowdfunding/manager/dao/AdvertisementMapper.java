package com.fz.crowdfunding.manager.dao;

import java.util.List;

import com.fz.crowdfunding.bean.Advertisement;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);
}