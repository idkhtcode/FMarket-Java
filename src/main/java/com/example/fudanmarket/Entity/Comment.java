package com.example.fudanmarket.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//评论id
    private Integer userId;//
    private Integer goodId;
    private String content;//评论的内容
    private Timestamp time;//评论的时间
    private Integer additional;//追评id，如果是主评论的话，则id为0，否则为追评对应评论的id
    private Integer mainId;//主楼id，如果评论是主楼的话则为0
    private Integer score;//1到10
    private boolean isDelete;//该评论是否被删除
}
