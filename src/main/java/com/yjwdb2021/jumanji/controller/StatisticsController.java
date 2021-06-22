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
@RequestMapping("/api/v1")
public class StatisticsController {
    private final PaymentServiceImpl paymentService;

    @Transactional
    @GetMapping("shops/{shopId}/payments/statistics")
    public ResponseEntity<?> getStatistics(@RequestHeader String authorization,
                                           @PathVariable String shopId,
                                           @Nullable @RequestParam String scope,
                                           @Nullable @RequestParam String date,
                                           @Nullable @RequestParam String aDate,
                                           @Nullable @RequestParam String bDate) {
        Statistics statistics = paymentService.getShopStatistics(authorization, shopId, scope, aDate, bDate, date);
//        statistics
        Statistics.Response response = new Statistics.Response(statistics);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
