package com.example.fudanmarket.Service;

import com.example.fudanmarket.Entity.Comment;
import com.example.fudanmarket.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@RestController
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    public UserType getUserType(String userType){
//        return userTypeRepository.findByUsertype(userType);
//    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }
    //生成一条评论
    public void addComment(Integer additional, Integer userId, Integer goodId, String content, Integer score) {
        Comment comment = new Comment();
        comment.setAdditional(additional);
        comment.setUserId(userId);
        comment.setGoodId(goodId);
        comment.setContent(content);
        comment.setScore(score);
        comment.setTime(new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
        comment.setDelete(false);
        commentRepository.save(comment);
    }


    public List<Comment> getAllMainCommentByBookId(Integer goodId) {
        return commentRepository.findByGoodId(goodId);
    }

    //显示一页中所有该评论的追平
    public Page<Comment> getAllAdditionalCommentByMainId(Integer mainId, Integer page, Integer size) {
        return commentRepository.findByMainId(mainId, PageRequest.of(page, size));
    }

    //所有符合条件的追平
    public List<Comment> getAllAdditionalCommentByMainId(Integer mainId) {
        return commentRepository.findByMainId(mainId);
    }


    public Page<Comment> getAllCommentByUserId(Integer userId, Integer page, Integer size) {
        return commentRepository.findByUserId(userId, PageRequest.of(page, size));
    }







    //根据id获取任意一条评论
    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).get();
    }

    //保存
    public void save(Comment comment) {
        commentRepository.save(comment);
    }


}
