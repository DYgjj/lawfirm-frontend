package com.group12.lawfirm.dao;

import com.group12.lawfirm.entity.Lawyer;
import com.group12.lawfirm.entity.Logs;
import com.group12.lawfirm.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface LogsDao extends Mapper<Logs> {

    List<Logs> findBySearch(@Param("params") Params params);
}
