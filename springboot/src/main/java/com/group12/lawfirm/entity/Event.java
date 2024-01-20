package com.group12.lawfirm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "event")
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String date;
}
