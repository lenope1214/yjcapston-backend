package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.UserCoupon;
import com.yjwdb2021.jumanji.data.UserCouponId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserHasCouponRepository extends JpaRepository<UserCoupon, UserCouponId> {

}