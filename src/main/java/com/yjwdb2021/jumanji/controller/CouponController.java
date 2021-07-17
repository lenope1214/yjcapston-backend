package com.yjwdb2021.jumanji.controller;

import com.yjwdb2021.jumanji.data.Coupon;
import com.yjwdb2021.jumanji.service.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/")
public class CouponController  {

    @Autowired
    private CouponServiceImpl couponService;

//    @GetMapping("as")
//    public Coupon.Response get(){
//
//    }

    @PostMapping("coupon")
    public Coupon.Response postCoupon(@RequestBody Coupon.Request request){
        Coupon coupon = couponService.post(null, request);
        Coupon.Response response = new Coupon.Response(coupon);
        return response;
    }

}
