package com.group12.lawfirm.dao;

import com.group12.lawfirm.entity.Client;
import com.group12.lawfirm.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ClientDao extends Mapper<Client> {

    List<Client> findBySearch(@Param("params") Params params);

    @Select("select * from client where account = #{account} and password = #{password} limit 1")
    Client findByAccountAndPassword(@Param("account") String account, @Param("password") String password);
}
