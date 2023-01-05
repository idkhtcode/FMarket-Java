package com.example.fudanmarket.Controller;

import com.example.fudanmarket.Entity.Goods;
import com.example.fudanmarket.Entity.User;
import com.example.fudanmarket.Repository.UserRepository;
import com.example.fudanmarket.Service.GoodsService;
import com.example.fudanmarket.Service.RecommendService;
import com.example.fudanmarket.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin
@Slf4j
public class GoodsController {
    @Autowired
    private UserRepository userRepository;

    private final String filePath = "/root/czy/BackendPackage/image/";
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;
    @Autowired
    RecommendService recommendService;

    @PostMapping(value = "/uploadGoodsPic")
    @ResponseBody
    public String uploadGoodsPic(@RequestParam(value = "file") MultipartFile file) {
        int begin = file.getOriginalFilename().indexOf(".");
        int last = file.getOriginalFilename().length();
        String suffix = file.getOriginalFilename().substring(begin, last);
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        String fileName = s.replace("-", "") + suffix;
        File dest = new File(filePath + fileName).getAbsoluteFile();
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "image/" + fileName;
    }

    @PostMapping(value = "/release")
    @ResponseBody
    public String release(Goods goods,@RequestParam(name = "username") String username) {
        log.info("call release");
        System.out.println("username = " + username);
        goods.setState("售卖中");
        User user = userService.findByUserName(username);
//        User user = userService.parseToken(token);
        goods.setReleaseTime(new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
        goods.setUid(user.getId());
        goodsService.addGoods(goods, user);
        return "success";
    }

    //用户下单商品
    @PostMapping(value = "/order")
    @ResponseBody
    public String order(Goods goods, @RequestHeader("Authorization") String token) {
        User user = userService.parseToken(token);

        Goods goods1 = goodsService.getGoodsByGid(goods.getId());
        if (goods.getBuyTime().before(new Timestamp(System.currentTimeMillis()))) return "time";
        if (goods1.getUid().equals(user.getId())) {
            log.error(user.getId() + " wanted to buy goods " + goods1.getId() + " that belongs to " + goods1.getUid());
            return "yourself";
        }
        if (!goods1.getState().equals("售卖中")) return "fail";

        goods1.setAddress(goods.getAddress());
        goods1.setBid(user.getId());
        goods1.setBuyTime(goods.getBuyTime());
        goods1.setContact(goods.getContact());
        goods1.setState("已下单");
        goodsService.saveGoods(goods1);
        return "success";
    }

    //卖家同意线下交易
    @PostMapping(value = "/agree")
    @ResponseBody
    public String agree(Goods goods, @RequestHeader("Authorization") String token) {
        User user = userService.parseToken(token);

        Goods goods1 = goodsService.getGoodsByUidAndGid(user.getId(), goods.getId());
        goods1.setState("交易中");
        goodsService.saveGoods(goods1);
        return "success";
    }

    //取消订单
    @PostMapping(value = "/cancel")
    @ResponseBody
    public String cancel(int gid, @RequestHeader("Authorization") String token) {
        User user = userService.parseToken(token);

        Goods goods1 = goodsService.getGoodsByGid(gid);

        if (!(goods1.getUid().equals(user.getId()) || user.getId().equals(goods1.getBid()))) {
            return "fail";
        }

        goods1.setState("售卖中");
        goodsService.saveGoods(goods1);
        return "success";
    }

    //完成交易
    @PostMapping(value = "/finish")
    @ResponseBody
    public String finish(Goods goods, @RequestHeader("Authorization") String token) {
        User user = userService.parseToken(token);

        //完成交易后，权重加1
        goodsService.addWeight(user.getMajor(), goodsService.findById(goods.getId()).getGoodsType());

        Goods goods1 = goodsService.getGoodsByBidAndGid(user.getId(), goods.getId());
        goods1.setRate(goods.getRate());
        goods1.setState("已售罄");
        goodsService.saveGoods(goods1);

        User sell = userService.getUser(goods1.getUid());
        User buy = userService.getUser(goods1.getBid());
        sell.setGoodsSold(sell.getGoodsSold() + 1);
        buy.setGoodsBought(buy.getGoodsBought() + 1);
        sell.setRateGoods(sell.getRateGoods() + (goods1.getRate() == null ? 0 : goods1.getRate()));
        userService.save(sell);
        userService.save(buy);

        log.info("user " + buy.getId() + " rated " + goods1.getRate() + " for goods " + goods1.getId() + " of user " + sell.getId());

        return "success";
    }


    @GetMapping("/getGoods")
    @ResponseBody
    public List<Goods> getAllGoods() {
        log.info("call get goods");
        return goodsService.getAll();
    }

    @PostMapping("/getRecommendGoods")
    @ResponseBody
    public List<Goods> getRecommendGoods(@RequestParam("uid") Integer uid) {
        return recommendService.recommendGoodsByUserInformation(userService.getUser(uid));
    }

    //搜索
    @PostMapping("/search")
    @ResponseBody
    public Page<Goods> search(@RequestParam("name") String name, @RequestParam("searchType") String searchType, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        if (searchType.equals("类别/名字")) {
            return goodsService.findByNameContainingOrGoodsTypeContaining(name, name, PageRequest.of(page, size));
        }
        return goodsService.findByGoodsType(name, PageRequest.of(page, size));
    }

    @PostMapping("/getBuyingGoods")
    @ResponseBody
    public Page<Goods> getBuyingGoods(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return goodsService.getAllBuyingGoods(userService.parseToken(token).getId(), "已售罄", PageRequest.of(page, size));
    }

    //根据状态返回商品的方法
    @PostMapping("/getGoodsByState")
    @ResponseBody
    public List<Goods> getWaitingGoods(@RequestHeader("Authorization") String token, @RequestParam("state") String state) {
        return goodsService.getAllBuyingGoods(userService.parseToken(token).getId(), state);
    }

    @PostMapping("/getGoodsByMajorCondition")
    @ResponseBody
    public Page<Goods> getGoodsByMajorCondition(
            @RequestParam("name") String searchName,
            @RequestParam("semester") String semester,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return goodsService.findGoodsAndSemesterByMajor(semester, searchName, PageRequest.of(page, size));
    }

    @PostMapping("/bookRecommend")
    @ResponseBody
    public List<Goods> bookRecommend(@RequestParam(value = "username") String username) {
        log.info("call book recommend");
        System.out.println("username = " + username);
        User user = userService.findByUserName(username);
        String semester = goodsService.year2semester(user.getYear());
        System.out.println("semester = " + semester);
        String searchName = user.getMajor();
        System.out.println("searchName = " + searchName);
        return goodsService.findGoodsAndSemesterByMajorList(semester, searchName);
    }
}
//    public Page<Goods> bookRecommend(
//            @RequestHeader("Authorization") String token,
//            @RequestParam("page") Integer page,
//            @RequestParam("size") Integer size) {
//        User user = userService.parseToken(token);
//        String semester = goodsService.year2semester(user.getYear());
//        String searchName = user.getMajor();
//        return goodsService.findGoodsAndSemesterByMajor(semester, searchName, PageRequest.of(page, size));
//    }

