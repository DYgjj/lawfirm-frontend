package com.group12.lawfirm.dao;

import com.group12.lawfirm.entity.Admin;
import com.group12.lawfirm.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminDao extends Mapper<Admin> {
    List<Admin> findBySearch(@Param("params") Params params);
}
