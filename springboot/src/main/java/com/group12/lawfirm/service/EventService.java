package com.group12.lawfirm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.dao.EventDao;
import com.group12.lawfirm.entity.Event;
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

    public List<Event> findAll() {
        return eventDao.selectAll();
    }

    public void add(Event event) {
        if (event.getContent() == null || "".equals(event.getContent())) {
            throw new CustomException("Content cannot be empty");
        }

        eventDao.insertSelective(event);
    }

    public void update(Event event) {
        eventDao.updateByPrimaryKeySelective(event);
    }

    public List<Event> findByDate(String date) {

        return eventDao.findAllByDate(date);
    }

    public List<Event> findBySearch(Params params) {
        return eventDao.findBySearch(params);
    }
}
