package com.yjwdb2021.jumanji.service.interfaces;

import com.yjwdb2021.jumanji.data.Payment;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;

public interface PaymentService {
    public Payment get(String authorization, Timestamp orderId);
    public List<Payment> getList(String orderId);
    public Payment post(String authorization, Payment.Request request);
    public Payment patch(String authorization, Payment.Request request);
//    public ResponseEntity<?> delete(String paymentId);
}
