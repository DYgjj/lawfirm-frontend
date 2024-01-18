package com.group12.lawfirm.controller;

import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.entity.Lawyer;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.service.LawyerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/lawyer")
public class LawyerController {

    @Resource
    private LawyerService lawyerService;

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Lawyer> info = lawyerService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Lawyer lawyer) {
        if (lawyer.getId() == null) {
            lawyerService.add(lawyer);
        } else {
            lawyerService.update(lawyer);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        lawyerService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Lawyer> list) {

        for (Lawyer lawyer : list) {
            lawyerService.delete(lawyer.getId());
        }
        return Result.success();
    }

}
