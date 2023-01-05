package com.example.fudanmarket.Service;

import com.example.fudanmarket.Entity.Request;
import com.example.fudanmarket.Entity.User;
import com.example.fudanmarket.Repository.RequestRepository;
import com.example.fudanmarket.util.LPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserService userService;

    public String addRequest(Request request, int ruid) {
        if (request.getState() == null) {
            request.setState("求购中");
        }
        if (request.getId() != null && request.getId() < 0) {
            return "id不能是负数";
        }
        if (request.getName() == null || request.getName().equals("")) {
            return "name不能为空";
        }
        request.setRequestTime(new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
        request.setRequestUserId(ruid);
        requestRepository.save(request);
        return "success";
    }

    public Request getRequestById(int id) {
        return requestRepository.findById(id).orElse(null);
    }

    public List<Request> getRequestByRequestUserId(int ruid) {
        return requestRepository.findByRequestUserId(ruid);
    }

    public List<Request> getRequestsUnfinished() {
        return requestRepository.findAllByState("求购中");
    }

    public List<Request> getRequestsFinished() {
        return requestRepository.findAllByState("已完成");
    }

    public Page<Request> getRequestsUnfinished(Pageable pageable) {
        List<Request> requests = requestRepository.findAllByState("求购中");
        List<Request> result = new ArrayList<>(Collections.emptyList());
        while (requests.size() != 0) {
            result.add(requests.get(0));
            requests.removeIf(request1 -> request1.similar(requests.get(0)));
        }
        return LPUtil.list2Page(result, pageable);
    }

    public String takeRequest(Request request, Integer fuid) {
        if (request.getTime().before(new Timestamp(System.currentTimeMillis()))) return "time";
        if (!request.getState().equals("求购中")) return "fail";
        if (request.getRequestUserId().equals(fuid)) {
            log.error(fuid + " wanted to take request " + request.getId() + " that belongs to" + request.getRequestUserId());
            return "yourself";
        }
        request.setState("待确认");
        request.setFinishUserId(fuid);
        requestRepository.save(request);
        return "success";
    }

    public String requestToo(Request request, Integer uid) {
        List<Request> requests = getRequestsUnfinished();
        int before = requests.size();
        requests.removeIf(request1 -> request1.similar(request) && Objects.equals(request.getRequestUserId(), uid));
        int after = requests.size();
        if (before == after) {
            addRequest(new Request(request.getName(), request.getMajor(), request.getMiddle(), request.getMinor(), request.getDescription()), uid);
            return "success";
        }
        return "fail";
    }

    public String confirmTake(Request request, Integer uid) {
        if (!request.getRequestUserId().equals(uid)) {
            log.error(uid + " wanted to confirm request " + request.getId() + " that belongs to" + request.getRequestUserId());
            return "fail";
        }
        if (!request.getState().equals("待确认")) return "fail";
        request.setState("待交易");
        requestRepository.save(request);
        return "success";
    }

    public String cancelTake(Request request, Integer uid) {
        if (!request.getRequestUserId().equals(uid)) {
            log.error(uid + " wanted to cancel request " + request.getId() + " that belongs to" + request.getRequestUserId());
            return "fail";
        }
        if (!request.getState().equals("待确认")) return "fail";
        request.setState("求购中");
        request.setFinishUserId(null);
        request.setRequestTime(null);
        request.setAddress(null);
        request.setContact(null);
        request.setPrice(null);
        requestRepository.save(request);
        return "success";
    }

    public String finishRequest(Request request, Integer uid) {
        if (!request.getRequestUserId().equals(uid)) {
            log.error(uid + " wanted to finish request " + request.getId() + " that belongs to" + request.getRequestUserId());
            return "fail";
        }
        if (!request.getState().equals("待交易")) return "fail";
        request.setFinishTime(new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
        request.setState("已完成");
        requestRepository.save(request);
        User take = userService.getUser(request.getFinishUserId());
        User publish = userService.getUser(request.getRequestUserId());
        take.setRequestTakenFinished(take.getRequestTakenFinished() + 1);
        publish.setRequestPublishedFinished(publish.getRequestPublishedFinished() + 1);
        userService.save(take);
        userService.save(publish);
        return "success";
    }

    public Page<Request> getRequestPageByRequestUserId(Integer ruid, Pageable pageable) {
        return requestRepository.findPageByRequestUserId(ruid, pageable);
    }

    public String deleteRequest(Request request, Integer uid) {
        if (!request.getRequestUserId().equals(uid)) {
            log.error(uid + " wanted to delete request " + request.getId() + " that belongs to" + request.getRequestUserId());
            return "fail";
        }
        if (!request.getState().equals("求购中")) return "fail";
        requestRepository.delete(request);
        return "success";
    }
}
