package com.example.fudanmarket.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRelease {
    private Integer id;
    private Integer uid;//发布人id
    private Integer gid;//商品id
}
