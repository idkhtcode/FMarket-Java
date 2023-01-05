package com.example.fudanmarket.Service;

import com.example.fudanmarket.Entity.Goods;
import com.example.fudanmarket.Entity.GoodsWeight;
import com.example.fudanmarket.Entity.User;
import com.example.fudanmarket.Repository.GoodsRepository;
import com.example.fudanmarket.Repository.GoodsWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    GoodsWeightRepository goodsWeightRepository;

    public void addGoods(Goods goods, User user) {
        goods.setUid(user.getId());
        goods.setSortByMajor(user.getMajor());
        goodsRepository.save(goods);
    }

    public void addWeight(String sortByMajor, String goodsType) {
        GoodsWeight goodsWeight = goodsWeightRepository.queryByBuyerMajorAndSortByGoods(sortByMajor, goodsType);
        if (goodsWeight == null) {
            goodsWeight = new GoodsWeight();
            goodsWeight.setBuyerMajor(sortByMajor);
            goodsWeight.setSortByGoods(goodsType);
            goodsWeight.setWeight(0);
        }

        goodsWeight.setWeight(goodsWeight.getWeight() + 1);
        goodsWeightRepository.save(goodsWeight);
    }

    public void saveGoods(Goods goods) {
        goodsRepository.save(goods);
    }

    public List<Goods> getAll() {
        return goodsRepository.findAllByStateEquals("售卖中");
    }

    public List<Goods> getAllByState(String state) {
        return goodsRepository.findAllByStateEquals(state);
    }

    //分页
    public Page<Goods> getAllStateByPage(String state, Pageable pageable) {
        return goodsRepository.findAllByStateEqualsOrderByIdDesc(state, pageable);
    }

    //分页
    public Page<Goods> releaseRecord(int userId, Pageable pageable) {
        return goodsRepository.findByUid(userId, pageable);
    }

    public List<Goods> releaseRecordByUserId(int userId) {
        return goodsRepository.findByUid(userId);
    }

    public Page<Goods> getAllBuyingGoods(int bid, String state, Pageable pageable) {
        return goodsRepository.findByBidAndState(bid, state, pageable);
    }

    public List<Goods> getAllBuyingGoods(int bid, String state) {
        return goodsRepository.findByBidAndState(bid, state);
    }

    public Page<Goods> allFinishBuyGoods(int userId, Pageable pageable) {
        return goodsRepository.findByUid(userId, pageable);
    }

    public void updateGoods(Goods goods, User user) {
        Goods newGoods = goodsRepository.queryById(goods.getId());
        newGoods.setName(goods.getName());
        newGoods.setPrice(goods.getPrice());
        newGoods.setGoodsType(goods.getGoodsType());
        newGoods.setPic(goods.getPic());
        newGoods.setRemark(goods.getRemark());
        newGoods.setUid(user.getId());
        goodsRepository.save(newGoods);
    }

    public String addGoods(Goods goods, int uid) {
        goods.setState("售卖中");
        if (goods.getId() != null && goods.getId() < 0) {
            return "id不能是负数";
        }
        if (goods.getPrice() != 0.0d && goods.getPrice() <= 0) {
            return "请输入正确的价格";
        }
        if (goods.getName() == null || goods.getName().equals("")) {
            return "商品名不能为空";
        }
        if (goods.getRemark() != null && goods.getRemark().length() > 200) {
            return "描述长度小于200";
        }
        if (goods.getPic() != null) {
            int begin = goods.getPic().indexOf(".");
            int last = goods.getPic().length();
            String suffix = goods.getPic().substring(begin + 1, last);
            if (!suffix.equals("jpg")) {
                return "文件需要是图片格式";
            }
        }
        goods.setUid(uid);
        goodsRepository.save(goods);
        return "success";
    }

    public void deleteGood(int gid) {
        goodsRepository.deleteById(gid);
    }

    public Goods getGoodsByUidAndGid(int uid, int gid) {
        return goodsRepository.findByUidAndId(uid, gid);
    }

    public Goods getGoodsByBidAndGid(int bid, int gid) {
        return goodsRepository.findByBidAndId(bid, gid);
    }

    public Goods getGoodsByGid(int gid) {
        return goodsRepository.queryById(gid);
    }

    public List<Goods> findByName(String name) {
        return goodsRepository.findByNameContaining(name);
    }

    public List<Goods> findByType(String type) {
        return goodsRepository.findByGoodsTypeContaining(type);
    }

    public List<Goods> findByMajor(String type) {
        return goodsRepository.findByGoodsTypeContaining(type);
    }

    public List<Goods> findByNameAndType(String name) {
        return goodsRepository.findByNameContainingOrGoodsTypeContaining(name, name);
    }

    public Goods findById(int gid) {
        return goodsRepository.queryById(gid);
    }

    public Page<Goods> findByNameContainingOrGoodsTypeContaining(String name, String goodTypes, Pageable pageable) {
        return goodsRepository.findByNameContainingOrGoodsTypeContaining(name, goodTypes, pageable);
    }

    public Page<Goods> findByGoodsType(String goodTypes, Pageable pageable) {
        return goodsRepository.findByGoodsTypeContaining(goodTypes, pageable);
    }
    public List<Goods> findByGoodsType(String goodTypes) {
        return goodsRepository.findByGoodsTypeContaining(goodTypes);
    }


    public void buyGoods(int sid, int bid, int gid) {
    }

    //年级专业
    public List<Goods> findGoodsAndSemesterByMajorList(String semester, String searchName) {
        return goodsRepository.findByGoodsTypeContainingAndSemesterContainingOrderBySemesterAsc(searchName, semester);
    }

    public Page<Goods> findGoodsAndSemesterByMajor(String semester, String searchName, Pageable pageable) {
        return goodsRepository.findByGoodsTypeContainingAndSemesterEquals(searchName, semester, pageable);
    }

    public List<Goods> findGoodsAndSemesterByMajorList2(String semester, String searchName) {
        return goodsRepository.findByGoodsTypeContainingAndSemesterEquals(searchName, semester);
    }

    public String year2semester(String year) {
        String res;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        if (nowYear > Integer.parseInt(year)) {
            int temp = nowYear - Integer.parseInt(year);
            if (temp <= 4) {
                String[] Class = {"大一", "大二", "大三", "大四"};
                res = Class[temp - 1];
                if (nowMonth < 9) {
                    res += "下";
                    return res;
                } else {
                    res = Class[temp] + "上";
                    return res;
                }
            }
        }
        return null;
    }
}
