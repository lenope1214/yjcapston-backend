package com.yjwdb2021.jumanji.controller;

import com.yjwdb2021.jumanji.data.Order;
import com.yjwdb2021.jumanji.data.User;
import com.yjwdb2021.jumanji.service.OrderServiceImpl;
import com.yjwdb2021.jumanji.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/")
public class UserController  {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    OrderServiceImpl orderService;


    @Transactional(readOnly = true)
    @GetMapping("users")  // myInfo
    public ResponseEntity<?> getMyInfo(@RequestHeader String authorization) {
        String loginId = userService.getMyId(authorization);
        User user = userService.isPresent(loginId);
        System.out.println("user.info : " + user.toString() );
        List<Order> orderList = orderService.getList(authorization);
        User.MyInfo response = new User.MyInfo(user, orderList);
        return new ResponseEntity<>(response, HttpStatus.OK);
//        return new ResponseEntity<>("is not match login id <-> request id.", HttpStatus.FORBIDDEN);
    }


    @Transactional(readOnly = true)
    @GetMapping("users/list") // getUserList
    public ResponseEntity<?> getUserList(@RequestHeader String authorization) {
        String loginId = userService.getMyId(authorization);
        String userRole = userService.isPresent(loginId).getRole();
        userService.isAuth(userRole, "ADMIN");
        List<User.Response> response = new ArrayList<>();
        for (User user : userService.getList(authorization)) {
            response.add(new User.Response(user));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Transactional(readOnly = true)
//    @GetMapping("users/{id}")
//    public ResponseEntity<?> selectUserInfo(@PathVariable String id) {
//        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
//    }

//    @Transactional
//    @DeleteMapping("usersDelAll")
//    public ResponseEntity<?> userDelAll( @RequestHeader String authorization) {
//        return userService.deleteAll(authorization);
//    }

    @Transactional
    @PatchMapping("users") // patch user
    public ResponseEntity<?> patchUser(@RequestHeader String authorization, @RequestBody User.Request request) {
        User.Response response = new User.Response(userService.patch(authorization, request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("users") // delete user
    public ResponseEntity<?> deleteUser(@RequestHeader String authorization){
        userService.delete(authorization);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제가 잘 되면 ok 반환.
    }
}