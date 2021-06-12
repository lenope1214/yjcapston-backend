package com.yjwdb2021.jumanji.data;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Table(name= "USER_COUPONS")
public class UserCoupon {

    @EmbeddedId
    private UserCouponId id;
    @Column(name = "begin_date")
    private Date beginDate= new Date(); // 사용 마지막 기간
    @Column(name = "expiry_date")
    private Date expiryDate; // 만료기간

    public class Request{

    }
}

