package com.group12.lawfirm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.dao.LawyerDao;
import com.group12.lawfirm.entity.Lawyer;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class LawyerService {

    @Resource
    LawyerDao lawyerDao;

    public PageInfo<Lawyer> findBySearch(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Lawyer> list = lawyerDao.findBySearch(params);
        return PageInfo.of(list);
    }



    public void add(Lawyer lawyer) { lawyerDao.insertSelective(lawyer); }

    public void update(Lawyer lawyer) {
        lawyerDao.updateByPrimaryKeySelective(lawyer);
    }

    public void delete(Integer id) {
        lawyerDao.deleteByPrimaryKey(id);
    }
}
