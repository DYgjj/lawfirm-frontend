package com.group12.lawfirm.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.AutoLogs;
import com.group12.lawfirm.common.LogType;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.entity.LawCase;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.service.LawCaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/lawCase")
public class LawCaseController {

    @Resource
    private LawCaseService lawCaseService;

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<LawCase> info = lawCaseService.findBySearch(params);
        return Result.success(info);
    }
    @GetMapping("/search2")
    public Result findBySearch2(Params params){
        PageInfo<LawCase> info = lawCaseService.findBySearch2(params);
        return Result.success(info);
    }
    @GetMapping("/searchAcceptance")
    public Result findBySearchAcceptance(Params params){
        PageInfo<LawCase> info = lawCaseService.findBySearchAcceptance(params);
        return Result.success(info);
    }
    @GetMapping("/searchCompletion")
    public Result findBySearchCompletion(Params params){
        PageInfo<LawCase> info = lawCaseService.findBySearchCompletion(params);
        return Result.success(info);
    }
    @GetMapping("/searchRefusal")
    public Result findBySearchRefusal(Params params){
        PageInfo<LawCase> info = lawCaseService.findBySearchRefusal(params);
        return Result.success(info);
    }

    @AutoLogs(operation = "Law Case", type = LogType.ADD_OR_UPDATE)
    @PostMapping
    public Result save(@RequestBody LawCase lawCase) {
        if (lawCase.getId() == null) {
            lawCaseService.add(lawCase);
        } else {
            lawCaseService.update(lawCase);
        }
        return Result.success();
    }

    @AutoLogs(operation = "Law Case", type = LogType.DELETE)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        lawCaseService.delete(id);
        return Result.success();
    }

    @AutoLogs(operation = "Law Case", type = LogType.BATCH_DELETE)
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<LawCase> list) {

        for (LawCase lawCase : list) {
            lawCaseService.delete(lawCase.getId());
        }
        return Result.success();
    }


    @GetMapping("/echarts/bie")
    public Result bie() {
        // 查询出所有图书
        List<LawCase> list = lawCaseService.findAll();
        Map<String, Long> collect = list.stream()
                .filter(x -> ObjectUtil.isNotEmpty(x.getStatus()))
                .collect(Collectors.groupingBy(LawCase::getStatus, Collectors.counting()));
        // 最后返回给前端的数据结构
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(collect)) {
            for (String key : collect.keySet()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", key);
                map.put("value", collect.get(key));
                mapList.add(map);
            }
        }
        return Result.success(mapList);
    }

}
