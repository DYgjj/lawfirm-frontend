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

    public PageInfo<LawCase> findBySearchAll(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<LawCase> list = lawCaseDao.findBySearchAll(params);
        return PageInfo.of(list);
    }

    public PageInfo<LawCase> findBySearch(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<LawCase> list = lawCaseDao.findBySearch(params);
        return PageInfo.of(list);
    }
    public PageInfo<LawCase> findBySearch2(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<LawCase> list = lawCaseDao.findBySearch2(params);
        return PageInfo.of(list);
    }

    public PageInfo<LawCase> findBySearchAcceptance(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<LawCase> list = lawCaseDao.findBySearchAcceptance(params);
        return PageInfo.of(list);
    }

    public PageInfo<LawCase> findBySearchCompletion(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<LawCase> list = lawCaseDao.findBySearchCompletion(params);
        return PageInfo.of(list);
    }

    public PageInfo<LawCase> findBySearchRefusal(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<LawCase> list = lawCaseDao.findBySearchRefusal(params);
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
        if(lawCase.getStatus() == null || "".equals(lawCase.getStatus())){
            throw new CustomException("Case status cannot be empty");
        }
        if(lawCase.getType() == null || "".equals(lawCase.getType())){
            throw new CustomException("Case type cannot be empty");
        }
//        LawCase cname = lawCaseDao.findByCname(lawCase.getCname());
//        if(cname == null) {
//            throw new CustomException("Client name is not found. Please re-enter it!");
//        }
//        LawCase lname = lawCaseDao.findByLname(lawCase.getLname());
//        if(lname == null) {
//            throw new CustomException("Lawyer name is not found. Please re-enter it!");
//        }
        lawCaseDao.insertSelective(lawCase);
    }

    public void update(LawCase lawCase) {
        lawCaseDao.updateByPrimaryKeySelective(lawCase);
    }

    public void delete(Integer id) {
        lawCaseDao.deleteByPrimaryKey(id);
    }

    public List<LawCase> findAll(){
        return lawCaseDao.findAll();
    }
}
