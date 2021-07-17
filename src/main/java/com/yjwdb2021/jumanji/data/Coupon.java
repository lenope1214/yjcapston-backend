package com.yjwdb2021.jumanji.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Table(name="coupons") @NoArgsConstructor
public class Coupon {
    @Id
//    @GeneratedValue(strategy= GenerationType.Identity)
    @Column(length = 12)
    private Long id;//쿠폰번호
    @Column(length = 30)
    private String name;//쿠폰이름
    @Column(name = "begin_date")
    private final Date beginDate= new Date(); // 사용 마지막 기간
    @Column(name = "expiry_date")
    private Date expiryDate; // 만료기간
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="account",nullable = false)
//    private Account id; //아이디


    public Coupon(Coupon.Request request){
        this.id = 1L;
        this.name = request.getName();
    }

    @Getter
    public static class Request{
        private String name;
    }

    @NoArgsConstructor @AllArgsConstructor @Getter
    public static class Response{
        private String name;

        public Response(Coupon coupon){
            this.name = coupon.getName();
        }
    }
}
