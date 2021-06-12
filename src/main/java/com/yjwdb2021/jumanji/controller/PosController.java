package com.yjwdb2021.jumanji.controller;

import com.yjwdb2021.jumanji.data.*;
import com.yjwdb2021.jumanji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PosController {
    @Autowired
    ShopServiceImpl shopService;
    @Autowired
    OrderMenuServiceImpl orderMenuService;
    @Autowired
    TableServiceImpl tableService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    PosService posService;


    @Transactional(readOnly = true)
    @GetMapping("shops/{shopId}/pos")
    public ResponseEntity<?> getShopPos(@PathVariable String shopId){
        List<Pos> posList = posService.getList(null, shopId);
        return new ResponseEntity<>(posList, HttpStatus.OK);
    }
}
