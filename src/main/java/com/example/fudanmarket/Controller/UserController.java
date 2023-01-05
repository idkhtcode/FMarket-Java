package com.example.fudanmarket.Controller;

import com.example.fudanmarket.Entity.User;
import com.example.fudanmarket.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Controller
@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public String register(User user) {
        user.setRegisterTime(new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
        user.setEmail(user.getUsername() + "@fudan.edu.cn");
        user.setRequestPublishedFinished(0);
        user.setRequestTakenFinished(0);
        user.setRateGoods(0);
        user.setRateRequest(0);
        user.setGoodsBought(0);
        user.setGoodsSold(0);
        userService.addUser(user);
        log.info("user " + user.getUsername() + " register");
        return "success";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password){
        String token;
        log.info("login request name: " + username + " password: " + password);
        if (userService.existsByUsernameAndPassword(username, password)) {
//            User user = userService.findByUsernameAndPassword(username, password);
//            token = userService.getToken(user);
//            log.info("user " + username + " login");
            return "success";
        }
        return "failure";
    }

    @PostMapping("/getUser")
    @ResponseBody
    public User getUser(@RequestParam(value = "username") String username) {
        log.info("call get user");
        User user = userService.findByUserName(username);
        return user;
    }

    @PostMapping("/queryByUsername")
    @ResponseBody
    public boolean queryByUsername(@RequestBody User request) {
        return userService.existsByUsername(request.getUsername());
    }

    @PostMapping("/queryByEmail")
    @ResponseBody
    public boolean queryByEmail(@RequestBody User request) {
        return userService.existsByEmail(request.getEmail());
    }


}
