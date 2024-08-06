package com.example.demo.service;

import com.example.demo.common.dto.ApiResponseEntity;
import com.example.demo.dto.data.Datas;
import com.example.demo.mybatis.mapper.DataMapper;
import com.example.demo.utils.ApiResponseUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataService {

    @Autowired
    private DataMapper dataMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(Datas data) {
        dataMapper.add(data);
    }

    public ApiResponseEntity<PageInfo<Datas>> list(Datas datas, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Datas> list = dataMapper.selectDatas(datas);
        PageInfo<Datas> pageInfo = new PageInfo<>(list);
        return ApiResponseUtils.success(pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        dataMapper.delete(id);
    }
}
