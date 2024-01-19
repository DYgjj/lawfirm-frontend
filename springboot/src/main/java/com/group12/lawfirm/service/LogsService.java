package com.group12.lawfirm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.dao.LogsDao;
import com.group12.lawfirm.entity.Logs;
import com.group12.lawfirm.entity.Params;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class LogsService{

    @Resource
    LogsDao logsDao;

    public PageInfo<Logs> findBySearch(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Logs> list = logsDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Logs logs) {
        logsDao.insertSelective(logs);
    }

    public void delete(Integer id) {
        logsDao.deleteByPrimaryKey(id);
    }

}
