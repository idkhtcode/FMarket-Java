package com.example.fudanmarket.Controller;

import com.example.fudanmarket.Entity.Request;
import com.example.fudanmarket.Service.RequestService;
import com.example.fudanmarket.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;

    @GetMapping("/getRequestsByRuid")
    public List<Request> getRequestsByRuid(@RequestHeader("Authorization") String token) {
        Integer ruid = userService.parseToken(token).getId();
        return requestService.getRequestByRequestUserId(ruid);
    }

    @GetMapping("/getRequestsByRuid/{page}/{size}")
    public Page<Request> getRequestsByRuid(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestHeader("Authorization") String token) {
        Integer ruid = userService.parseToken(token).getId();
        return requestService.getRequestPageByRequestUserId(ruid, PageRequest.of(page, size));
    }

    @GetMapping("/getRequests/{page}/{size}")
    public Page<Request> getRequest(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return requestService.getRequestsUnfinished(PageRequest.of(page, size));
    }

    @PostMapping("/publishRequest")
    public String publishRequest(@RequestParam("name") String name, @RequestParam("major") String major, @RequestParam("middle") String middle, @RequestParam("minor") String minor, @RequestParam("description") String description, @RequestHeader("Authorization") String token) {
        Integer uid = userService.parseToken(token).getId();
        Request request = new Request(name, major, middle, minor, description);
        return requestService.addRequest(request, uid);
    }

    @PostMapping("/requestToo")
    public String requestToo(@RequestParam("rid") Integer rid, @RequestHeader("Authorization") String token) {
        Integer uid = userService.parseToken(token).getId();
        Request request = requestService.getRequestById(rid);
        return requestService.requestToo(request, uid);
    }

    @PostMapping("/takeRequest")
    public String takeRequest(@RequestParam("rid") Integer rid, @RequestParam("address") String address, @RequestParam("time") Timestamp time, @RequestParam("contact") String contact, @RequestParam("price") Double price, @RequestHeader("Authorization") String token) {
        Integer fuid = userService.parseToken(token).getId();
        Request request = requestService.getRequestById(rid);
        request.setRequestTime(time);
        request.setAddress(address);
        request.setContact(contact);
        request.setPrice(price);
        return requestService.takeRequest(request, fuid);
    }

    @PostMapping("/cancelRequest")
    public String cancelRequest(@RequestParam("rid") Integer rid, @RequestHeader("Authorization") String token) {
        Integer uid = userService.parseToken(token).getId();
        Request request = requestService.getRequestById(rid);
        return requestService.deleteRequest(request, uid);
    }

    @PostMapping("/confirmTake")
    public String confirmTake(@RequestParam("rid") Integer rid, @RequestHeader("Authorization") String token) {
        Integer uid = userService.parseToken(token).getId();
        Request request = requestService.getRequestById(rid);
        return requestService.confirmTake(request, uid);
    }

    @PostMapping("/cancelTake")
    public String cancelTake(@RequestParam("rid") Integer rid, @RequestHeader("Authorization") String token) {
        Integer uid = userService.parseToken(token).getId();
        Request request = requestService.getRequestById(rid);
        return requestService.cancelTake(request, uid);
    }

    @PostMapping("/finishRequest")
    public String FinishRequest(@RequestParam("rid") Integer rid, @RequestHeader("Authorization") String token) {
        Integer uid = userService.parseToken(token).getId();
        Request request = requestService.getRequestById(rid);
        return requestService.finishRequest(request, uid);
    }
}
