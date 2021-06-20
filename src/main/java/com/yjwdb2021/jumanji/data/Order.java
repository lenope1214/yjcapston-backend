package com.yjwdb2021.jumanji.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjwdb2021.jumanji.service.exception.orderException.OrderAmountCanNotZeroException;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@ToString(exclude = {"tableId", "reviewList"})
@Table(name = "ORDERS")
public class Order implements Serializable {
    @Id
    private Timestamp id; // 바구니번호 yyyyMMddhhmmss
    private String status = "rd"; // rd : 준비중, st : 착석, pd : 결제완료, rf : 환불
    @Column(name = "order_request")
    private String orderRequest; // 요청사항
    @Column(length = 2)
    private int people;
    @Column(name = "use_point", length = 5)
    private int usePoint; // 사용된 포인트
    @Column(length = 8)
    private int amount; // 가격 총합
    @Column(name = "comple_amount", length = 9)
    private int compleAmount;
    @Column(name = "arrive_time")
    private Timestamp arriveTime; // 가게 도착시간
    @Column(name = "pay_time")
    private Timestamp payTime; // 결제 일자 yyyyMMdd
    @Column(length = 9)
    private String pg;
    @Column(name = "pay_method")
    private String payMethod; // 결제방식

    private char accept = 'N';


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", updatable = false)
    @JsonIgnore // 이거 없으면 fetchType lazy라서 json 변환중에 오류남.
    private Shop shop;
    @JoinColumn(name = "user_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // 이거 없으면 fetchType lazy라서 json 변환중에 오류남.
    private User user;

//    @Column(name = "tab_id")
//    private String tabId; // 외래키 없어서 발생했던거 같은데..?
//    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToOne(mappedBy = "order")
    @Setter
    @JoinColumn
    private String tab_id;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList;

//    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderMenu> orderMenuList; // orderMenus DB 구조 바꿔야 함. Order_id, sequence 복합키


    @Builder
    public Order(Timestamp id, Shop shop, User user) {
        this.id = id;
        this.shop = shop;
        this.user = user;
    }

    public void init(Order.MyInfo o){
        this.id = o.getId();
        this.status = o.getStatus();
        this.orderRequest = o.getOrderRequest();
        this.usePoint = o.getUsePoint();
        this.arriveTime = o.getArriveTime();
        this.payTime = o.getPayTime();
        this.people = o.getPeople();
        this.pg = o.getPg();
        this.payMethod = o.getPayMethod();
        this.amount = o.getAmount();
        this.accept = o.getAccept();
    }





    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Timestamp orderId;
        private String status;
        private String orderRequest;
        private int people;
        private int usePoint; // 사용된 포인트
        private int amount; // 가격 총합
        private Timestamp arriveTime; // 가게 도착시간
        private Timestamp payTime; // 결제 일자 yyyyMMdd
        private String pg;
        private String payMethod; // 결제방식
        private String shopId;
        private String userId;
        private int tabNo;
        private List<OrderMenu> orderMenuList;
    }

    @Getter
    public static class Response {
        private Timestamp orderId;
        private String userId; // 암호화 해서 주고받자..
        private String userName;
        private String userPhone;
        private String shopId;
        private String shopName;
        private int people;
        private String orderRequest;
        private int usePoint; // 사용된 포인트
        private int amount; // 가격 총합
        private int compleAmount; // 결제된 금액
        private Timestamp arriveTime; // 가게 도착시간
        private String payTime; // 결제 일자 yyyyMMdd
        private String status;
        private String pg;
        private String payMethod; // 결제방식
        private char accept;
        private char reviewed;
        private String table_id;
        @Setter
        private List<OrderMenu.Response> orderMenuList;


        public Response(Order order) {
            if(order.getUser()!=null){
                if(order.getUser().getId() !=null)this.userId = order.getUser().getId(); // 암호화 해서 주고받자..
                if(order.getUser().getName()!=null)this.userName = order.getUser().getName();
                if(order.getUser().getPhone()!=null)this.userPhone = order.getUser().getPhone();
            }
            if(order.getShop()!=null){
                this.shopId = order.getShop().getId();
                this.shopName = order.getShop().getName();
            }
            this.orderId = order.getId();
            this.people = order.getPeople();
            this.orderRequest = order.getOrderRequest();
            this.usePoint = order.getUsePoint();
            this.status = order.getStatus();
            this.reviewed = order.getReviewList() != null ? 'Y' : 'N';
            this.amount = order.getAmount();
            this.arriveTime = order.getArriveTime();
            this.pg = order.getPg();
            this.payMethod = order.getPayMethod();
            this.accept = order.getAccept();
            if (order.getPayTime() != null)
                this.payTime = DateOperator.dateToYYYYMMDD(order.getPayTime(), true) + DateOperator.dateToHHMM(order.getPayTime(), true);
            this.compleAmount = order.getCompleAmount();
//            System.out.println(order.getTab().toString());
            if(order.getTab_id() != null)this.table_id = order.getTab_id();
        }


        public void setReviewed(char v){
            this.reviewed = v;
        }
    }

    public void patch(Request request) {
        if (request.getOrderRequest()!=null && request.orderRequest.length() > 0) {
            this.orderRequest = request.getOrderRequest();
        }
//        if(request.getAmount() == 0)throw new OrderAmountCanNotZeroException();
        this.amount += request.getAmount();
        if (request.people != 0) this.people = request.people;
        if(request.getArriveTime() != null)this.arriveTime = request.getArriveTime();
        this.status = "rd";
//        if(request.getTabNo() != 0)this.tabId = request.getShopId() + String.format("%02d", request.getTabNo()); // 일반 컬럼으로 했을 때 이렇게 사용.
    }

    public void pay(Payment.Request request) {
        this.payMethod = request.getPayMethod();
        this.payTime = new Timestamp(System.currentTimeMillis());
        this.pg = request.getPg();
        this.compleAmount += request.getAmount(); // 여기서의 amount : 결제 요청 금액
        this.usePoint += request.getUsePoint();
    }

    public void payComple(){ this.status = "pd"; }


    public void refund() {
        this.status = "rf";
    }

    public void accept() {
        this.accept = 'Y';
    }

    @NoArgsConstructor @Getter
    public static class ContainsMenu{
        Order order;
        OrderMenu orderMenu;
        char reviewed;
    }

    public interface MyInfo{
        Timestamp getId();
         String getStatus();
         String getOrderRequest();
         int getPeople();
         int getUsePoint();
         int getAmount();
         Timestamp getArriveTime();
         Timestamp getPayTime();
         String getPg();
         String getPayMethod();
         char getAccept();
         char getReviewed();
         String getShopId();
         String getUserId();
//        Order getOrders(); 이건 불가능
//        String getReviewed(); 이건 가능.
    }
}