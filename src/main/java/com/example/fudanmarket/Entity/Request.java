package com.example.fudanmarket.Entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String major;       //大分类
    private String middle;      //中分类
    private String minor;       //小分类
    private Integer requestUserId;
    private Integer finishUserId;
    private String description;
    private Timestamp requestTime;
    private Timestamp finishTime;
    private String state;

    //交易相关的新元素
    private Timestamp time; //交易时间
    private String address; //交易地点
    private String contact; // 联系地址
    private Double price;

    public Request(String name) {
        this.name = name;
    }

    public Request(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Request(String name, String major, String middle, String minor, String description) {
        this.name = name;
        this.major = major;
        this.middle = middle;
        this.minor = minor;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Request request = (Request) o;
        return id != null && Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean similar(Request request) {
        boolean mid = request.description.equals(this.description) && request.name.equals(this.name);
        return mid && request.major.equals(this.major) && request.middle.equals(this.middle)
                && request.minor.equals(this.minor);
    }
}

