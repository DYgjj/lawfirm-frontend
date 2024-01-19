package com.group12.lawfirm.controller;

import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.entity.Client;
import com.group12.lawfirm.entity.Logs;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.service.LogsService;
import com.group12.lawfirm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    LogsService logsService;

    @Autowired
    UserService userService;

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Logs> info = logsService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Logs logs) {
        logsService.add(logs);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        logsService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Client> list) {

        for (Client client : list) {
            logsService.delete(client.getId());
        }
        return Result.success();
    }

}
