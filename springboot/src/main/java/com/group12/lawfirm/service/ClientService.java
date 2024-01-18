package com.group12.lawfirm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.JwtTokenUtils;
import com.group12.lawfirm.dao.ClientDao;
import com.group12.lawfirm.entity.Client;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ClientService {

    @Resource
    ClientDao clientDao;

    public PageInfo<Client> findBySearch(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Client> list = clientDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Client client) {
        clientDao.insertSelective(client);
    }

    public void update(Client client) {
        clientDao.updateByPrimaryKeySelective(client);
    }

    public void delete(Integer id) {
        clientDao.deleteByPrimaryKey(id);
    }

    public Client login(Client client) {
        if(client.getAccount() == null || "".equals(client.getAccount())){
            throw new CustomException("Account cannot be empty");
        }
        if(client.getPassword() == null || "".equals(client.getPassword())){
            throw new CustomException("Password cannot be empty");
        }

        Client user = clientDao.findByAccountAndPassword(client.getAccount(), client.getPassword());
        if(user == null){
            throw new CustomException("Account or password wrong!");
        }

        //生成token
        String token = JwtTokenUtils.genToken(user.getId().toString(), user.getPassword());
        user.setToken(token);
        return user;
    }

    public Client findById(Integer id) {
        return clientDao.selectByPrimaryKey(id);
    }
}
