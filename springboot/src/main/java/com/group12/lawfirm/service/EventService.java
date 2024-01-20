package com.group12.lawfirm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.dao.EventDao;
import com.group12.lawfirm.entity.Event;
import com.group12.lawfirm.entity.Lawyer;
import com.group12.lawfirm.entity.Params;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class EventService {

    @Resource
    EventDao eventDao;

    public List<Event> findAll() {
        return eventDao.selectAll();
    }

    public void add(Event event) { eventDao.insertSelective(event); }

    public void update(Event event) {
        eventDao.updateByPrimaryKeySelective(event);
    }

    public Event findByDate(String date) {
        return eventDao.findAllByDate(date);
    }

    public PageInfo<Event> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Event> list = eventDao.findBySearch(params);
        return PageInfo.of(list);
    }
}
