package com.group12.lawfirm.entity;

import lombok.Data;

@Data
public class Params {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String title;
    private String role;
    private Integer pageNum;
    private Integer pageSize;
    private String expertise;
    private String operation;
    private String user;
    private String cname;
    private String eventtitle;
    private String description;
    private String eventdate;
    private String caseid;
}
