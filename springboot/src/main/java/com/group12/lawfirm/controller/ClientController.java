package com.group12.lawfirm.controller;

import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.entity.*;
import com.group12.lawfirm.service.ClientService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/client")
public class ClientController {

    @Resource
    private ClientService clientService;

    @PostMapping("/login")
    public Result login(@RequestBody Client client){
        Client loginUser = clientService.login(client);
        return Result.success(loginUser);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Client> info = clientService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Client client) {
        if (client.getId() == null) {
            clientService.add(client);
        } else {
            clientService.update(client);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        clientService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Client> list) {

        for (Client client : list) {
            clientService.delete(client.getId());
        }
        return Result.success();
    }

}
