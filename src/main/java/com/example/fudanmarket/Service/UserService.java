package com.example.fudanmarket.Service;

import com.example.fudanmarket.Entity.Goods;
import com.example.fudanmarket.Entity.Request;
import com.example.fudanmarket.Entity.User;
import com.example.fudanmarket.Repository.GoodsRepository;
import com.example.fudanmarket.Repository.RequestRepository;
import com.example.fudanmarket.Repository.UserRepository;
import com.example.fudanmarket.Security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private RequestRepository requestRepository;

    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public String getToken(User user) {
        return jwtTokenUtil.generateToken(user);
    }

    public User parseToken(String token) {
        return jwtTokenUtil.parse(token);
    }

    public boolean existsByUsernameAndPassword(String username, String password) {
        return userRepository.existsByUsernameAndPassword(username, password);
    }


    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer uid) {
        userRepository.deleteById(uid);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<Goods> getList(int uid) {
        return goodsRepository.findByUid(uid);
    }

    public List<Request> getRequestsRequestedByHim(int uid) {
        return requestRepository.findByRequestUserId(uid);
    }

    public List<Request> getRequestsFinishedByHim(int fuid) {
        return requestRepository.findByFinishUserId(fuid);
    }

    public User getUser(int uid) {
        return userRepository.findById(uid);
    }
}
