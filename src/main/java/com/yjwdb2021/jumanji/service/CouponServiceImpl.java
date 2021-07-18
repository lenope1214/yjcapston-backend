package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.data.Coupon;
import com.yjwdb2021.jumanji.repository.CouponRepository;
import com.yjwdb2021.jumanji.service.interfaces.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CouponServiceImpl implements BasicService<Coupon, Coupon.Request, Long> {
    @Autowired
    CouponRepository couponRepository;


    @Override
    public Coupon get(@Nullable String authorization, String... str) {
        return null;
    }

    @Override
    public List<Coupon> getList(@Nullable String authorization, String... str) {
        return null;
    }

    @Override
    public Coupon post(@Nullable String authorization, Coupon.Request request) {
        Coupon coupon = new Coupon(request);
//        couponRepository;
        // select * from coupons where id = ?;
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon patch(@Nullable String authorization, Coupon.Request request) {
        return null;
    }

    @Override
    public void delete(@Nullable String authorization, String... str) {

    }

    @Override
    public Coupon isPresent(Long id) {
        return null;
    }

    @Override
    public boolean isEmpty(Long id) {
        return false;
    }

}