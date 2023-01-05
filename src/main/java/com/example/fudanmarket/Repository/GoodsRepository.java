package com.example.fudanmarket.Repository;

import com.example.fudanmarket.Entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {
    Goods queryById(int goodId);
    Page<Goods> findByUid(int userId,Pageable pageable);
    List<Goods> findByUid(int userId);
    List<Goods> findAll();

    List<Goods> findByGoodsType(String goodsType);

    Page<Goods> findAllByStateEqualsOrderByIdDesc(String  state, Pageable pageable);

    Goods findByUidAndId(int uid,int gid);

    Goods findByBidAndId(int bid, int gid);

    List<Goods> findByBidAndState(int bid, String state);
    Page<Goods> findByBidAndState(int bid, String state, Pageable pageable);

    List<Goods> findByNameContaining(String name);

    List<Goods> findByGoodsTypeContaining(String type);

    List<Goods> findByNameContainingOrGoodsTypeContaining(String name, String type);

    Page<Goods> findByNameContainingOrGoodsTypeContaining(String name, String type, Pageable pageable);

    List<Goods> findAllByStateEquals(String state);

    List<Goods> findByGoodsTypeContainingAndSemesterContainingOrderBySemesterAsc(String major, String semester);
    Page<Goods> findByGoodsTypeContainingAndSemesterEquals(String major, String semester,Pageable pageable);

    List<Goods> findByGoodsTypeContainingAndSemesterEquals(String major, String semester);
    Page<Goods> findByGoodsTypeContaining(String type, Pageable pageable);

}
