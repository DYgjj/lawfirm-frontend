package com.group12.lawfirm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.JwtTokenUtils;
import com.group12.lawfirm.common.MD5Util;
import com.group12.lawfirm.dao.UserDao;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.entity.User;
import com.group12.lawfirm.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public List<User> getUser(){
        return userDao.selectAll();
    }

    public PageInfo<User> findBySearch(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<User> list = userDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(User user) {

        if(user.getName() == null || "".equals(user.getName())){
            throw new CustomException("Name cannot be empty");
        }
        if(user.getAccount() == null || "".equals(user.getAccount())){
            throw new CustomException("Account cannot be empty");
        }
        if(user.getPassword() == null || "".equals(user.getPassword())){
            throw new CustomException("Password cannot be empty");
        }
        if(user.getPhone() == null || "".equals(user.getPhone())){
            throw new CustomException("Phone cannot be empty");
        }
        if(user.getEmail() == null || "".equals(user.getEmail())){
            throw new CustomException("Email cannot be empty");
        }

        User userAccount = userDao.findByAccount(user.getAccount());
        if(userAccount != null) {
            throw new CustomException("This account already exists, please do not  repeat operation!");
        }
        userDao.insertSelective(user);
    }

    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    public void delete(Integer id) {
        userDao.deleteByPrimaryKey(id);
    }

    public User login(User user) {

        if(user.getAccount() == null || "".equals(user.getAccount())){
            throw new CustomException("Account cannot be empty");
        }
        if(user.getPassword() == null || "".equals(user.getPassword())){
            throw new CustomException("Password cannot be empty");
        }

        User userLogin = userDao.findByAccountAndPassword(user.getAccount(), user.getPassword());
        if(userLogin == null){
            throw new CustomException("Account or password wrong!");
        }

        // 生成jwt token给前端
        String token = JwtTokenUtils.genToken(userLogin.getId().toString(), userLogin.getPassword());
        userLogin.setToken(token);
        return userLogin;
    }

    public User findById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    public void resetPassword(User user) {

        if(user.getAccount() == null || "".equals(user.getAccount())){
            throw new CustomException("Account cannot be empty");
        }
        if(user.getPhone() == null || "".equals(user.getPhone())){
            throw new CustomException("Phone cannot be empty");
        }

        User dbUser = userDao.findByAccount(user.getAccount());
        if (dbUser == null) {
            throw new CustomException("User not found");
        }
        if (!user.getPhone().equals(dbUser.getPhone())) {
            throw new CustomException("Validation error");
        }

        dbUser.setPassword(MD5Util.MD5(dbUser.getAccount() + "123"));
    }

    public void changePassword(User user) {
        if (Objects.equals(user.getOldPassword(), user.getPassword())) {
            user.setPassword(MD5Util.MD5(user.getNewPassword()));
            userDao.updateByPrimaryKeySelective(user);
        } else {
            throw new CustomException("Old password is incorrect!");
        }
    }
}
