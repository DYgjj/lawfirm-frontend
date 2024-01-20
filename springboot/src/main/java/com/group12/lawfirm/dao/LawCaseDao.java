package com.group12.lawfirm.dao;

import com.group12.lawfirm.entity.LawCase;
import com.group12.lawfirm.entity.Lawyer;
import com.group12.lawfirm.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface LawCaseDao extends Mapper<LawCase> {

    List<LawCase> findBySearch(@Param("params") Params params);
    List<LawCase> findBySearch2(@Param("params") Params params);
    List<LawCase> findBySearchAcceptance(@Param("params") Params params);
    List<LawCase> findBySearchCompletion(@Param("params") Params params);
    List<LawCase> findBySearchRefusal(@Param("params") Params params);

    @Select("select * from client where name = #{cname} limit 1")
    LawCase findByCname(@Param("cname") String cname);

    @Select("select * from lawyer where name = #{lname} limit 1")
    LawCase findByLname(@Param("lname") String lname);

    @Select("select * from lawcase")
    List<LawCase> findAll();
}
