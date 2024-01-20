package com.group12.lawfirm.controller;

import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.AutoLogs;
import com.group12.lawfirm.common.LogType;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.entity.Event;
import com.group12.lawfirm.entity.LawCase;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.service.EventService;
import com.group12.lawfirm.service.LawCaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController {

    @Resource
    private EventService eventService;

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Event> info = eventService.findBySearch(params);
        return Result.success(info);
    }

    @AutoLogs(operation = "Event", type = LogType.ADD_OR_UPDATE)
    @PostMapping
    public Result save(@RequestBody Event event) {
        if (event.getId() == null) {
            eventService.add(event);
        } else {
            eventService.update(event);
        }
        return Result.success();
    }
    @GetMapping("findAll")
    public Result findAll() { return Result.success(eventService.findAll());
    }


}
