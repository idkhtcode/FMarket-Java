package com.example.fudanmarket.Entity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String major;
    private String year;
    private Timestamp registerTime;
    private Integer goodsBought;
    private Integer goodsSold;
    private Integer requestPublishedFinished;
    private Integer requestTakenFinished;
    private Integer rateGoods;
    private Integer rateRequest;

    public User(Integer id, String username) {
        this.id=id;
        this.username = username;
    }

    public User(Integer id, String username, String year) {
        this.id=id;
        this.username = username;
        this.year = year;
    }
    public User(Integer id, String username, String year, String major) {
        this.id=id;
        this.username = username;
        this.year = year;
        this.major = major;
    }
}
