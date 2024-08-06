package com.example.demo.mybatis.mapper;

import com.example.demo.dto.data.Datas;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DataMapper {
    /**
     * 新增资料
     * @param data 资料实体类
     */
    void add(@Param("datas")Datas data);

    /**
     * 查询资料
     * @param datas 资料
     * @return 资料集合
     */
    List<Datas> selectDatas(@Param("datas") Datas datas);

    /**
     * 删除资料
     * @param id 资料ID
     * @return 删除了多少条数据
     */
    void delete(@Param("id")Integer id);
}
