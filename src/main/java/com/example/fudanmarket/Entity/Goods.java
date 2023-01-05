package com.example.fudanmarket.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String remark;
    private double price;
    private String semester;
    private String sortByMajor;
    private String goodsType;
    private Integer uid;//发布人的id
    private String pic;
    private Timestamp releaseTime;
    private String state;
    private String degree;

    //交易相关的新元素
    private Integer bid; //买家id
    private Timestamp buyTime; //交易时间
    private String address; //交易地点
    private String contact; // 联系地址
    private Integer rate; // 评分

    public Goods(Integer id) {
        this.id = id;
    }

    public Goods(String name,String goodsType, String semester) {
        this.name = name;
        this.semester = semester;
        this.goodsType = goodsType;
    }

    public Goods(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Goods(Integer id, Timestamp buyTime, String address, String contact) {
        this.id = id;
        this.buyTime = buyTime;
        this.address = address;
        this.contact = contact;
    }

    public Goods(Integer id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public Goods(Integer id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }
    public Goods(Integer id, String name,double price, String goodsType) {
        this.id = id;
        this.name = name;
        this.goodsType = goodsType;
        this.price = price;
    }

    public Goods(String name) {
        this.name = name;
    }

    public Goods(Integer id, String name, double price, String degree, String goodsType, String remark, String pic, Integer uid, String state) {
        this.id=id;
        this.name = name;
        this.degree = degree;
        this.remark = remark;
        this.price = price;
        this.goodsType = goodsType;
        this.uid = uid;
        this.pic = pic;
        this.state = state;
    }
    public Goods(Integer id, String name, double price, String degree, String goodsType,String semester, String remark, String pic) {
        this.id=id;
        this.name = name;
        this.degree = degree;
        this.remark = remark;
        this.price = price;
        this.goodsType = goodsType;
        this.semester = semester;
        this.pic = pic;
    }
    public Goods(Integer id, String name, double price, String degree, String goodsType, String remark, String pic) {
        this.id=id;
        this.name = name;
        this.degree = degree;
        this.remark = remark;
        this.price = price;
        this.goodsType = goodsType;
        this.pic = pic;
    }
    public Goods(String name, double price, String degree, String goodsType, String remark, String pic) {
        this.name = name;
        this.degree = degree;
        this.remark = remark;
        this.price = price;
        this.goodsType = goodsType;
        this.pic = pic;
    }

    public Goods(String name, String degree, String goodsType, int uid) {
        this.name = name;
        this.degree = degree;
        this.goodsType = goodsType;
        this.uid = uid;
    }
    public Goods(String name, double price, String degree, String goodsType, String remark, String pic, String semester) {
        this.name = name;
        this.degree = degree;
        this.remark = remark;
        this.price = price;
        this.goodsType = goodsType;
        this.pic = pic;
        this.semester = semester;
    }


    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", price=" + price +
                ", semester='" + semester + '\'' +
                ", sortByMajor='" + sortByMajor + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", uid=" + uid +
                ", pic='" + pic + '\'' +
                ", releaseTime=" + releaseTime +
                ", state='" + state + '\'' +
                ", degree='" + degree + '\'' +
                ", bid=" + bid +
                ", buyTime=" + buyTime +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Goods goods = (Goods) o;
        return id != null && Objects.equals(id, goods.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
