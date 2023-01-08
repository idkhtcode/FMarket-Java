package com.example.fudanmarket.Controller;


import com.example.fudanmarket.Entity.*;
import com.example.fudanmarket.Service.CommentService;
import com.example.fudanmarket.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    //发表对书的主要评论或追评
    @PostMapping("/addGoodComment")
    public String addBookComment(@RequestParam("id") Integer goodId, @RequestParam("username") String username,
                               @RequestParam("content") String content) {
        log.info("call add good comments");
        Integer userId = userService.findByUserName(username).getId();
        //System.out.println("评论细节\n书本id:" + bookId + "\n用户名：" + username + "\n用户id：" + userId + "\n内容：" + content + "\n分数：" + score);
        Comment comment = new Comment();
        comment.setGoodId(goodId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setDelete(false);
        comment.setTime(new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
        commentService.save(comment);
        return "Success";
    }

    //获取自己的评论
//    @PostMapping("/getOwnComment")
//    public Comment getOwnComment(@RequestParam("bookId") Integer bookId, @RequestParam("username") String username) {
//        Integer userId = userService.getUserId(username);
//        return commentService.getComment(userId, bookId, 0);
//    }

    //判断用户是否曾经还过这本书
//    @PostMapping("/isReturned")
//    public boolean isReturned(@RequestParam("bookId") Integer bookId, @RequestParam("username") String username) {
//        Integer userId = userService.getUserId(username);
//        return commentService.isBookReturned(userId, bookId);
//    }

    //判断用户是否曾经对该书进行评论
//    @PostMapping("/isCommented")
//    public boolean isCommented(@RequestParam("bookId") Integer bookId, @RequestParam("username") String username) {
//        Integer userId = userService.getUserId(username);
//        return commentService.isBookCommented(userId, bookId);
//    }

    //获取书的id所有的评论
    @PostMapping("/getBookComments")
    public List<Comment> getAllCommentByGoodId(@RequestParam("id") Integer goodId) {
        log.info("call book comment");
        return commentService.getAllMainCommentByBookId(goodId);
    }

//    //获取书的id所有的评论
//    @PostMapping("/getListComments")
//    public List<PackagedComment> getAllListCommentByBookId(@RequestParam("mainId") Integer mainId) {
//        List<Comment> comments = commentService.getAllAdditionalCommentByMainId(mainId);
//        return commentService.transformPage(comments);
//    }



    //删除评论
    @PostMapping("/deleteComment")
    public void deleteComment(@RequestParam("id") Integer id) {
        Comment comment = commentService.getCommentById(id);
        comment.setDelete(true);
        commentService.save(comment);
    }

    //恢复评论
    @PostMapping("/reComment")
    public void reComment(@RequestParam("id") Integer id) {
        Comment comment = commentService.getCommentById(id);
        comment.setDelete(false);
        commentService.save(comment);
    }



//    @PostMapping("/getScore")
//    public Double getScore(@RequestParam("bookId") Integer bookId) {
//        List<Comment> comments = commentService.getAllMainCommentByBookId(bookId, 0);
//        double score = 0;
//        for (int i = 0; i < comments.size(); i++) {
//            score += comments.get(i).getScore();
//        }
//        return score / (comments.size() > 0 ? comments.size() : 1);
//    }
}
