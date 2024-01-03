package com.group12.lawfirm.entity;

import lombok.Data;

@Data
public class Params {
    private String name;
    private String email;
    private Integer pageNum;
    private Integer pageSize;
}
