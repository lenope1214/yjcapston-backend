package com.yjwdb2021.jumanji.controller;

import com.yjwdb2021.jumanji.data.Order;
import com.yjwdb2021.jumanji.data.Payment;
import com.yjwdb2021.jumanji.data.Statistics;
import com.yjwdb2021.jumanji.data.externalData.iamport.Iamport;
import com.yjwdb2021.jumanji.service.OrderServiceImpl;
import com.yjwdb2021.jumanji.service.PaymentServiceImpl;
import com.yjwdb2021.jumanji.service.external.IamportClientService;
import com.yjwdb2021.jumanji.service.external.iamportAndroid.response.IamportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Query;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/")
public class PaymentController {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    IamportClientService iamportClientService;

    @Transactional
    @GetMapping("payments/{orderId}")
    public ResponseEntity<?> getPayment(@RequestHeader String authorization, @PathVariable Timestamp orderId) {
        Payment payment = paymentService.get(authorization, orderId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("payments/complite")
    public ResponseEntity<?> complePayment(@RequestParam("imp_uid") String impUid, @RequestParam("merchant_uid") String merchantUid, HttpServletRequest request) throws Exception {
        System.out.println("request info" +
                "request.getQueryString" + request.getQueryString() + "\n" +
                "merchantUid.substring(merchant_.length()) : " + merchantUid.substring("merchant_".length()));
        Iamport.IamportResponse<Iamport.Payment> response = null;
        String mId = merchantUid;
        if (merchantUid.contains("_")) {
            mId = mId.substring(mId.indexOf('_') + 1);
        }
        System.out.println("mid : " + mId);
        if (merchantUid == null) throw new Exception("merchantUid is null");
        try {
//            String m_id = merchantUid;
            response = iamportClientService.paymentByMerchantUid(merchantUid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.getResponse().getStatus().equals("paid")) {
            System.out.println("요청 후mid : " + mId);
            int amount = orderService.isPresent(new Timestamp(Long.parseLong(mId))).getAmount();
            BigDecimal amountB = BigDecimal.valueOf(amount);
            System.out.println("response.getResponse().getAmount() : " + response.getResponse().getAmount());
            System.out.println("BigDecimal amount : " + amountB);
            if (response.getResponse().getAmount() == amountB) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //    @Transactional 결제시..
    @PostMapping("payments")
    public ResponseEntity<?> postPayment(@RequestHeader String authorization, @RequestBody Payment.Request request) {
        // response 형태로 바꿔줘야함.
        Payment payment = paymentService.post(authorization, request);
        if (payment == null) return new ResponseEntity<>("Payment is completed", HttpStatus.OK);
        Payment.Response response = new Payment.Response(payment);
        return new ResponseEntity(response, HttpStatus.OK);
    }



}
