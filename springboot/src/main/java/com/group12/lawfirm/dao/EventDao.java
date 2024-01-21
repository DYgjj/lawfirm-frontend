package com.group12.lawfirm.dao;

import com.group12.lawfirm.entity.Event;
import com.group12.lawfirm.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface EventDao extends Mapper<Event> {

    @Select("select * from event where date = #{date}")
    List<Event> findAllByDate(@Param("date") String date);

    List<Event> findBySearch(@Param("params")Params params);
}
