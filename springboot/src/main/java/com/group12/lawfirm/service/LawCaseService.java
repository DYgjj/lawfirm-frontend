package com.group12.lawfirm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.dao.LawCaseDao;
import com.group12.lawfirm.entity.*;
import com.group12.lawfirm.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class LawCaseService {

    @Resource
    LawCaseDao lawCaseDao;

    public PageInfo<LawCase> findBySearch(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<LawCase> list = lawCaseDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(LawCase lawCase) {

        if(lawCase.getTitle() == null || "".equals(lawCase.getTitle())){
            throw new CustomException("Title cannot be empty");
        }
        if(lawCase.getContent() == null || "".equals(lawCase.getContent())){
            throw new CustomException("Content cannot be empty");
        }
        if(lawCase.getCname() == null || "".equals(lawCase.getCname())){
            throw new CustomException("Client name cannot be empty");
        }
        if(lawCase.getLname() == null || "".equals(lawCase.getLname())){
            throw new CustomException("Lawyer name cannot be empty");
        }
        LawCase cname = lawCaseDao.findByCname(lawCase.getCname());
        if(cname == null) {
            throw new CustomException("Client name is not found. Please re-enter it!");
        }
        LawCase lname = lawCaseDao.findByLname(lawCase.getLname());
        if(lname == null) {
            throw new CustomException("Lawyer name is not found. Please re-enter it!");
        }
        lawCaseDao.insertSelective(lawCase);
    }

    public void update(LawCase lawCase) {
        lawCaseDao.updateByPrimaryKeySelective(lawCase);
    }

    public void delete(Integer id) {
        lawCaseDao.deleteByPrimaryKey(id);
    }
}
