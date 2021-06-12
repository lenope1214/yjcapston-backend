package com.yjwdb2021.jumanji.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Getter
//@Entity
//@Table(name="payments")
public class Payment implements Serializable {
//    @Id
//    @JoinColumn
//    @ManyToOne
    private Timestamp orderId;

//    @Column(name="pay_time")
    private String payTime; // 결제 일자 yyyyMMdd
    private String status; // 주문 상태 rd : 준비중, pd : 결제완료, rf : 환불됨
//    @Column(length = 7)
    private int amount; // 정가합 금액
    private int compleAmount; // 결제 완료된 금액
    private int usePoint; // 사용한 총 포인트
//    @Column(name = "pay_method")
    private String payMethod; // 결제방식
//    @Column(length = 9)
    private String pg;


    public Payment(Order order){
        this.orderId = order.getId();
        this.payTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(order.getPayTime());
        this.status = order.getStatus();
        this.amount = order.getAmount();
        this.compleAmount = order.getCompleAmount();
        this.usePoint = order.getUsePoint();
        this.payMethod = order.getPayMethod();
        this.pg = order.getPg();
    }


    @Getter @AllArgsConstructor @NoArgsConstructor
    public static class Request{
        private Timestamp orderId; // 주문번호
        private String orderRequest;
        private int amount; //
        private int usePoint;
        private int people;
        private String pg;
        private String payMethod;
        private String holder; // 예금주
        private String reason;
        private int refundBank; // 환불 수령계좌 은행코드
        private String refundAccount; // 환불 계좌
    }

    @Getter @NoArgsConstructor
    public static class Response{
        private Timestamp orderId;
        private String payTime;
        private String holder; // 예금주
        private String status;
        private String reason;
        private int amount;
        private int compleAmount; // 결제 완료된 금액.
        private int usePoint; // 사용한 총 포인트
        private String payMethod;
        private String pg;


        public Response(Payment payment){
            this.orderId = payment.getOrderId();
            this.payTime = getPayTime();
            this.status = payment.getStatus();
            this.amount = payment.getAmount();
            this.compleAmount = payment.getCompleAmount();
            this.usePoint = payment.getUsePoint();
            this.payMethod = payment.getPayMethod();
            this.pg = payment.getPg();
        }
    }

    @Getter @NoArgsConstructor @AllArgsConstructor
    public static class Statistics{
        private Long pd;
        private Long rf;
    }


    public interface StatisticsDAO{
        Integer getSumPd();
        Integer getSumRf();
    }
}