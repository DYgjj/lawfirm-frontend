package com.group12.lawfirm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.dao.EventDao;
import com.group12.lawfirm.dao.LawCaseDao;
import com.group12.lawfirm.entity.Event;
import com.group12.lawfirm.entity.LawCase;
import com.group12.lawfirm.entity.Lawyer;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class EventService {

    @Resource
    EventDao eventDao;


    public PageInfo<Event> findBySearch(Params params) {
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Event> list = eventDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Event event) {


//        LawCase cname = lawCaseDao.findByCname(lawCase.getCname());
//        if(cname == null) {
//            throw new CustomException("Client name is not found. Please re-enter it!");
//        }
//        LawCase lname = lawCaseDao.findByLname(lawCase.getLname());
//        if(lname == null) {
//            throw new CustomException("Lawyer name is not found. Please re-enter it!");
//        }
        eventDao.insertSelective(event);
    }

    public void update(Event event) {
        eventDao.updateByPrimaryKeySelective(event);
    }

    public void delete(Integer id) {
        eventDao.deleteByPrimaryKey(id);
    }
    public List<Event> findAll() { return eventDao.selectAll(); }

}
