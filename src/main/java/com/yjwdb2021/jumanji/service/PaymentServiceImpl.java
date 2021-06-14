package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.data.*;
import com.yjwdb2021.jumanji.repository.OrderRepository;
import com.yjwdb2021.jumanji.service.exception.orderException.PayAmountOverException;
import com.yjwdb2021.jumanji.service.exception.orderException.PayPointOverException;
import com.yjwdb2021.jumanji.service.interfaces.BasicService;
import com.yjwdb2021.jumanji.service.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ShopServiceImpl shopService;
    @Autowired
    OrderRepository orderRepository;


    public Statistics.SumPdRf getShopStatistics(String authorization, String shopId, String scope, String aDate, String bDate, String date) {
        String loginId = userService.getMyId(authorization);
        Statistics.SumPdRf statistics = null;

        // 유효성 체크
        if (shopId == null) throw new NullPointerException("Shop Id를 입력해 주세요.");
//        shopService.isOwnShop(loginId, shopId); // 내 식당인지
//        if (aDate == null) aDate = DateOperator.dateToYYYYMMDD(new Date(), false);

        if(aDate != null)aDate = deleteSign(aDate);
        if(bDate != null)bDate = deleteSign(bDate);
        if(date != null)date = deleteSign(date);

        System.out.println("목표 식당 : " + shopId);
        System.out.println("지정 날짜 : " + date);
        System.out.println("지정 기준 : " + scope);
        switch(scope){
            case "between" :
                statistics = orderRepository.getSumPdRfBetween(shopId, aDate, bDate);
                break;
            case "week" :
                statistics = orderRepository.getSumPdRfWeek(shopId, date);
                break;
            case "month" :
                statistics = orderRepository.getSumPdRfMonth(shopId, date);
                break;
            default:
                statistics = orderRepository.getSumPdRfDate(shopId, date);
                break;
        }
        System.out.println("====================\nstatistics.getSumPd() : " + statistics.getSumPd() + "\nstatistics.getSumRf() : " + statistics.getSumRf());
        return statistics;
    }

    private String deleteSign(String str) {
        str = str.replace("-", "");
        str = str.replace("/", "");
        str = str.replace(".", "");
        str = str.replace(",", "");
        return str;
    }


    @Override
    public Payment get(String authorization, Timestamp orderId) {
        return null;
    }

    @Override
    public List<Payment> getList(String orderId) {
        return null;
    }

    @Override
    @Transactional
    public Payment post(String authorization, Payment.Request request) {
        String loginId = userService.getMyId(authorization);
        User user = userService.isPresent(loginId); // 그 사용자가 맞는지 확인
        int remainPoint = user.getPoint() - request.getUsePoint();
        System.out.println("request.getAmount : " + request.getAmount());

        // order가 내거인지
        System.out.println("내 주문이 맞는지?");
        Order order = orderService.isOwnOrder(request.getOrderId(), loginId); // 해당 사용자의 주문이 맞는지
        System.out.println("맞는지 검사 후");
        // 맞다면, 금액이 전액 지불 됐는지 확인.
        if (order.getAmount() - order.getCompleAmount() - order.getUsePoint() <= 0) // 총 금액 - 결제 금액 - 사용 포인트가 0보다 작거나 같다면 결제가 완료 되었음.
            return null;
        if (order.getAmount() <  order.getCompleAmount() + request.getAmount() + request.getUsePoint()) throw new PayAmountOverException();



        order.pay(request);
        System.out.println("남은 포인트 : " + remainPoint);
        if (remainPoint >= 0)
            user.setPoint((int)(remainPoint + request.getAmount() /100));
        else
            throw new PayPointOverException();
        Payment payment = new Payment(order);
        System.out.println("결제시간 : " + payment.getPayTime());
        System.out.println();
        orderRepository.saveAndFlush(order);
        userService.save(user);
        return payment;
    }

    // 1620366111800
    // 1620366815170
    @Override
    public Payment patch(String authorization, Payment.Request request) {
        return null;
    }


    public Object isPresent(String id) {
        return false;
    }


    public boolean isEmpty(String id) {
        return false;
    }
}
