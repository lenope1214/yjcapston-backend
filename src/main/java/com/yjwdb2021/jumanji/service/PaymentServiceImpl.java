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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ShopServiceImpl shopService;
    @Autowired
    TableServiceImpl tableService;
    @Autowired
    OrderRepository orderRepository;


    public Statistics getShopStatistics(String authorization, String shopId, String scope, String aDate, String bDate, String date) {
        String loginId = userService.getMyId(authorization);
        Statistics statistics = new Statistics();
        Shop shop = null;

        Date start = null, end = null;

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

        Calendar cal = Calendar.getInstance(Locale.KOREA);
        if(!scope.equals("between") && !date.isEmpty() && !date.equals("")){
//            System.out.println("!between + date : " + date);
            start = DateOperator.strToDate(date, false);
            end = DateOperator.strToDate(date, false);
        }

        shop = shopService.isPresent(shopId);

        // 시작날짜부터 정하고 마지막 날짜 정하기.

        switch(scope){
            case "between" :
                statistics.setSumPdRf(orderRepository.getSumPdRfBetween(shopId, aDate, bDate));
                start = DateOperator.strToDate(aDate, false);
                end = DateOperator.strToDate(bDate, false);
                start = DateOperator.trim(start);
                end = DateOperator.trim(end);
                break;
            case "week" :
                statistics.setSumPdRf(orderRepository.getSumPdRfWeek(shopId, date));
                // 주간 날짜 정하기
                cal.setTime(start);
                cal.add(Calendar.DATE, 2 - cal.get(Calendar.DAY_OF_WEEK));
                start = cal.getTime();
                System.out.println("주의 첫번째 요일(월요일)날짜:"+DateOperator.YYYYMMDD.format(cal.getTime()) + ",  start : " + start.getTime());

                cal.setTime(end);
                cal.add(Calendar.DATE, 8 - cal.get(Calendar.DAY_OF_WEEK));
                end = cal.getTime();
                System.out.println("주의 마지막 요일(일요일)날짜:"+DateOperator.YYYYMMDD.format(cal.getTime()) + ",  end : " + end.getTime());
                break;
            case "month" :
                statistics.setSumPdRf(orderRepository.getSumPdRfMonth(shopId, date));

                cal.setTime(start);
                cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                start = cal.getTime();
                System.out.println("달의 첫번째 요일 :"+DateOperator.YYYYMMDD.format(cal.getTime()) + ",  start : " + start.getTime());

                cal.setTime(end);
                cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // 해당 달의 마지막 날짜로 설정
                end = cal.getTime();
                System.out.println("달의 마지막 요일 :"+DateOperator.YYYYMMDD.format(cal.getTime()) + ",  end : " + end.getTime());

                break;
            case "date":
            default:
                start = DateOperator.trim(start);
                cal.setTime(end); // 1일 후 00:00을 가져오기 위해 cal 사용.
                cal.add(Calendar.DATE, 1); // 1일 더하기.
                end = cal.getTime(); // 1일 더한 후의 값을 end로 지정해줌.
                end = DateOperator.trim(end); // 00:00으로 변경.
                statistics.setSumPdRf(orderRepository.getSumPdRfDate(shopId, date));
                break;
        }
        System.out.println("DATE/ start : " + start + ", getTime() : " + start.getTime());
        System.out.println("DATE/ end : " + end + ", getTime() : " + end.getTime());
        statistics.setPdOrderList(orderRepository.findByStatusAndShopAndIdIsBetweenOrderByIdDesc("pd",shop, start, end));
        statistics.setRfOrderList(orderRepository.findByStatusAndShopAndIdIsBetweenOrderByIdDesc("rf",shop, start, end));
        System.out.println("====================\nsumPdRf.getSumPd() : " + statistics.getSumPdRf().getSumPd() + "\nsumPdRf.getSumRf() : " + statistics.getSumPdRf().getSumRf());
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
        Tab table;
        Order order;
        int remainPoint = user.getPoint() - request.getUsePoint();

        // order가 내거인지
        order = orderService.isOwnOrder(request.getOrderId(), loginId); // 해당 사용자의 주문이 맞는지
        table = tableService.get(order.getId());
        // 맞다면, 금액이 전액 지불 됐는지 확인.
        if (order.getAmount() - order.getCompleAmount() - order.getUsePoint() <= 0) // 총 금액 - 결제 금액 - 사용 포인트가 0보다 작거나 같다면 결제가 완료 되었음.
            return null;
        if (order.getAmount() <  order.getCompleAmount() +  request.getUsePoint()) throw new PayAmountOverException();



        order.pay(request);

        if (remainPoint >= 0)
            user.setPoint((int)(remainPoint + request.getAmount() /100));
        else
            throw new PayPointOverException();
        Payment payment = new Payment(order);
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

    public void payComplite(Payment.Request request) {
        Tab table;
        Order order;

        // order가 내거인지
        order = orderService.isPresent(request.getOrderId()); // 주문이 있는지
        table = tableService.get(order.getId());


        order.payComple();
        if(table!= null){
            table.pay();
            tableService.save(table);
        }
        orderRepository.saveAndFlush(order);
    }
}
