package com.group12.lawfirm.dao;

import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {
    List<User> findBySearch(@Param("params") Params params);

    @Select("select * from user where account = #{account} limit 1")
    User findByAccount(@Param("account") String account);

    @Select("select * from `user` where account = #{account} and password = #{password} limit 1")
    User findByAccountAndPassword(@Param("account") String account, @Param("password") String password);


}
