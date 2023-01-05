package com.example.fudanmarket.Repository;

import com.example.fudanmarket.Entity.GoodsWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GoodsWeightRepository extends JpaRepository<GoodsWeight, Integer> {
    List<GoodsWeight> findByBuyerMajorOrderByWeight(String buyerMajor);

    List<GoodsWeight> findAll();

    GoodsWeight queryByBuyerMajorAndSortByGoods(String buyerMajor, String sortByGoods);
}
