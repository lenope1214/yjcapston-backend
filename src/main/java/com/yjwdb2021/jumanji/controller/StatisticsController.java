package com.yjwdb2021.jumanji.controller;

import com.yjwdb2021.jumanji.data.Statistics;
import com.yjwdb2021.jumanji.service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final PaymentServiceImpl paymentService;

    @Transactional
    @GetMapping("shops/{shopId}/payments/statistics")
    public ResponseEntity<?> getStatistics(@RequestHeader String authorization,
                                           @PathVariable String shopId,
                                           @Nullable @RequestParam String scope,
                                           @Nullable @RequestParam String aDate,
                                           @Nullable @RequestParam String bDate) {
        Statistics.SumPdRf statistics = paymentService.getShopStatistics(authorization, shopId, scope, aDate, bDate);
//        statistics
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

}
