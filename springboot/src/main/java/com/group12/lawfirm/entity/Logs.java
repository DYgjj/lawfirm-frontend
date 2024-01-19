package com.group12.lawfirm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Logs")
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "operation")
    private String operation;
    @Column(name = "type")
    private String type;
    @Column(name = "user")
    private String user;
    @Column(name = "time")
    private String time;
    @Column(name = "ip")
    private String ip;

}
