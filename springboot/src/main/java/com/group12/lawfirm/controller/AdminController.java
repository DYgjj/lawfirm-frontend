package com.group12.lawfirm.controller;

import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.entity.Admin;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping
    public Result getAdmin(){
        List<Admin> list = adminService.getAdmin();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Admin> info = adminService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Admin admin) {
        if (admin.getId() == null) {
            adminService.add(admin);
        } else {
            adminService.update(admin);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return Result.success();
    }

}
