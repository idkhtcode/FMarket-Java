package com.example.fudanmarket.Service;

import com.example.fudanmarket.Entity.Goods;
import com.example.fudanmarket.Entity.GoodsWeight;
import com.example.fudanmarket.Entity.User;
import com.example.fudanmarket.Repository.GoodsRepository;
import com.example.fudanmarket.Repository.GoodsWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendService {
    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    GoodsWeightRepository goodsWeightRepository;

    //根据用户信息为用户推荐商品
    public List<Goods> recommendGoodsByUserInformation(User user) {
        List<GoodsWeight> goodsWeightList = goodsWeightRepository.findByBuyerMajorOrderByWeight(user.getMajor());

        List<Goods> goods = new ArrayList<>();

        for (int i = 0; i < (Math.min(goodsWeightList.size(), 3)); i++) {
            List<Goods> addGoods = goodsRepository.findByGoodsType(goodsWeightList.get(i).getSortByGoods());
            int size = addGoods.size();
            goods.addAll(addGoods.subList(0, Math.min(size, 4)));
        }

        return goods;
    }

    //随机为用户推荐某个商品
    public Goods recommendGoodsByRandom() {
        return null;
    }

    //为用户推荐热门的商品
    public List<Goods> recommendGoodsByGood() {
        //todo
        return null;
    }

    //当某个用户购买了某个商品以后，将该商品的权重加1
    public void addWeight(String major, String sort) {
        //todo
    }
}
