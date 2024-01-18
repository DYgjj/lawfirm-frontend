package com.group12.lawfirm.controller;

import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.entity.LawCase;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.service.LawCaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping
    public Result save(@RequestBody LawCase lawCase) {
        if (lawCase.getId() == null) {
            lawCaseService.add(lawCase);
        } else {
            lawCaseService.update(lawCase);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        lawCaseService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<LawCase> list) {

        for (LawCase lawCase : list) {
            lawCaseService.delete(lawCase.getId());
        }
        return Result.success();
    }

}
