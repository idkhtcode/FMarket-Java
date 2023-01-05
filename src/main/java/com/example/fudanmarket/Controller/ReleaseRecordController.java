package com.example.fudanmarket.Controller;

import com.example.fudanmarket.Entity.Goods;
import com.example.fudanmarket.Entity.User;
import com.example.fudanmarket.Service.GoodsService;
import com.example.fudanmarket.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class ReleaseRecordController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @PostMapping("/getRecord")
    @ResponseBody
    public List<Goods> getUserReleaseRecord(@RequestParam(value = "username")String username){
        User user = userService.findByUserName(username);
        return goodsService.releaseRecordByUserId(user.getId());
    }

    @PostMapping("updateGoodsInfo")
    @ResponseBody
    public String updateGoodsInfo(Goods goods, @RequestHeader("Authorization") String token){
        User user = userService.parseToken(token);
        goodsService.updateGoods(goods,user);
        return "success";
    }
}
