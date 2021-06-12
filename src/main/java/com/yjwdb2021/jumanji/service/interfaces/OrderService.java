package com.yjwdb2021.jumanji.service.interfaces;

import com.yjwdb2021.jumanji.data.Order;
import com.yjwdb2021.jumanji.data.Payment;
import com.yjwdb2021.jumanji.data.Shop;
import com.yjwdb2021.jumanji.data.User;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;

/** Order service Interface **/


public interface OrderService {

    List<Order> getList(String authorization, String userId);
    Order post(String loginId, User user, Shop shop, Order.Request request);
    Order patch(String authorization, Order.Request request);
//    void delete(String authorization, Timestamp cartId);
}
