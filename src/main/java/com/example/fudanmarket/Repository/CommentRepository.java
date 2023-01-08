package com.example.fudanmarket.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fudanmarket.Entity.Comment;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {


    // Comment findById(Integer id);



    List<Comment> findByGoodId(Integer bookId);

    Page<Comment> findByMainId(Integer mainId, Pageable pageable);

    List<Comment> findByMainId(Integer mainId);

    Page<Comment> findByUserId(Integer userId, Pageable pageable);
}
