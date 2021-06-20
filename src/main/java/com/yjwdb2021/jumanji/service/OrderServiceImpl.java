package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.data.*;
import com.yjwdb2021.jumanji.repository.OrderRepository;
import com.yjwdb2021.jumanji.repository.ReviewRepository;
import com.yjwdb2021.jumanji.repository.TableRepository;
import com.yjwdb2021.jumanji.service.exception.orderException.OrderHasExistException;
import com.yjwdb2021.jumanji.service.exception.orderException.OrderNotFoundException;
import com.yjwdb2021.jumanji.service.exception.shopException.ShopNotOpenException;
import com.yjwdb2021.jumanji.service.exception.tableException.TableAlreadUsingException;
import com.yjwdb2021.jumanji.service.interfaces.OrderService;
import jdk.jfr.TransitionTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ShopServiceImpl shopService;
    @Autowired
    TableServiceImpl tableService;
    @Autowired
    TableRepository tableRepository;

//    public ResponseEntity<?> getCartId() {
//        @Getter
//        @Setter
//        class Response {
//            private Timestamp cartId;
//        }
//        Response response = new Response();
//        response.setCartId(new Timestamp(System.currentTimeMillis()));
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    public Order get(Timestamp orderId){
        return isPresent(orderId);
    }

    public Order get(String authorization, Timestamp orderId){
        String userId = userService.getMyId(authorization);
        isOwnOrder(orderId, userId);
        return isPresent(orderId);
    }

    public List<Order> getList(String authorization) {
        String loginId = userService.getMyId(authorization);
        List<Order> orderList = new ArrayList<>();
        Order o;
        Shop shop;
        for (Order.MyInfo order : orderRepository.myOrderListContainsReviewed(loginId)) {
            shop = shopService.isPresent(order.getShopId());
            o = new Order(order.getId(), shop, null);
            o.init(order);
            orderList.add(o);
        }

        return orderList;
    }




    @Override
    public List<Order> getList(String authorization, String userId) {
        String loginId = userService.getMyId(authorization);
        User user = userService.isPresent(loginId);
        userService.isAuth(user.getRole(), "ADMIN");

        List<Order> orderList = new ArrayList<>();
        for (Order order : orderRepository.findALLByUser_Id(userId)) {
            orderList.add(order);
        }
        return orderList;
    }

    public Order post(String loginId, User user, Shop shop, Order.Request request) {
        Order order;
        order = Order.builder()
                .id(new Timestamp(System.currentTimeMillis()))
//                .orderRequest(request.getOrderRequest()) // 얘와 밑의 얘네 둘은 결제 완료 후에 들어갈 예정
//                .people(request.getPeople())
                .shop(shop)
                .user(user)
                .build();
        return order;
    }

    @Override
    @Transactional
    public Order patch(String authorization, Order.Request request) {
        Order order;
        User user;
        Shop shop;
        Tab table;
        // 주문번호가 같이 들어오면, 있는 주문번호를 수정하는것이기 때문에 그대로 검색해서 수정. 없으면 새로운 주문이므로 생성

        String loginId = userService.getMyId(authorization);

        // 유효성 검사.
        user = userService.isPresent(loginId);
        shop = shopService.isPresent(request.getShopId());

        isOpen(shop);
        order = request.getOrderId() != null ? isOwnOrder(request.getOrderId(), loginId) : post(loginId, user, shop, request);
        order.patch(request);

        if(request.getTabNo() != 0){
            table = tableService.isPresent(request.getShopId() + String.format("%02d",request.getTabNo()));
            if(table.getOrder() != null && !table.getOrder().getId().equals(request.getOrderId()))throw new TableAlreadUsingException();
            else table.setOrder(order);
            tableRepository.save(table);
            order.setTab_id(table.getId());
//            System.out.println("테이블 정보 추가... get Id : " + order.getTab().getId());
        }
        orderRepository.saveAndFlush(order);
        return order;
    }

    private void isOpen(Shop shop) {
        if(shop.getIsOpen() == 'N')throw new ShopNotOpenException();
    }

//    @Override
//    public void delete(String authorization, Timestamp orderId) {
//        isPresent(orderId);
//        Order order = orderRepository.findById(orderId).get();
//        orderRepository.delete(order);
//    }

    public Order isPresent(Timestamp orderId) {
        Optional<Order> order =orderRepository.findById(orderId);
        if (order.isPresent()) return order.get();
        throw new OrderNotFoundException();
    }

    public boolean isEmpty(Timestamp orderId) {
        if (orderRepository.findById(orderId).isEmpty()) return true;
        throw new OrderHasExistException();
    }

    public Order isOwnOrder(Timestamp orderId, String userId){
        Order order = isPresent(orderId);
        String _uId =order.getUser().getId();
        if(_uId.equals(userId))return order;
        else throw new OrderNotFoundException(""+orderId.getTime());
    }

    public List<Order> getListByShopId(String authorization, String shopId) {
        String loginId;

        // 유효성 검사
        loginId = userService.getMyId(authorization);
        shopService.isOwnShop(loginId, shopId);

        List<Order> orderList;
        orderList = orderRepository.findAllByShop_IdOrderById(shopId);
        return orderList;
    }

    public Order orderAccept(String authorization, Order.Request request) {
        // 변수
        String loginId;
        Order order;

        // 값 체크

        // 유효성 체크
        loginId = userService.getMyId(authorization);
        shopService.isOwnShop(loginId, request.getShopId());
        order = isPresent(request.getOrderId());

        // 서비스
        order.accept();


        // 값 체크
        return orderRepository.save(order);
    }

    public void statusUpdate(Order order) {
        orderRepository.save(order);
    }

    public Object getList() {
        return orderRepository.findAll();
    }



}
