package com.yjwdb2021.jumanji.controller;


import com.yjwdb2021.jumanji.data.Menu;
import com.yjwdb2021.jumanji.data.Review;
import com.yjwdb2021.jumanji.data.Shop;
import com.yjwdb2021.jumanji.data.UserShopMark;
import com.yjwdb2021.jumanji.service.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/")
public class ShopController {
//    Logger logger;


    @Autowired
    ShopServiceImpl shopService;

    @Autowired
    UserServiceImpl userService;
    @Autowired
    MenuServiceImpl menuService;
    @Autowired
    ReviewServiceImpl reviewService;
    @Autowired
    UserShopMarkService usmService;


    @Autowired
    StorageServiceImpl storageService;


    @Transactional(readOnly = true) // get /shop/{shopId}
    @GetMapping("shops/{shopId}")  // get shop/{shopId} 식당번호로 식당 조회
    public ResponseEntity<?> getShopById(
            @Nullable @RequestHeader String authorization,
            @PathVariable String shopId) {
        Shop shop = shopService.getShopByShopId(authorization, shopId);
        UserShopMark usm = usmService.get(authorization, shopId);
        char marked = usm == null ? 'N' : 'Y';
        Shop.ResponseMarked response = new Shop.ResponseMarked(shop, marked);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/users/shops") // get /myShop
    public ResponseEntity<?> getMyShop(@RequestHeader String authorization) { // 수정해야함.
        List<Shop> shopList = shopService.getMyShop(authorization);
        List<Shop.Response> response = new ArrayList<>();
        for (Shop shop : shopList) {
            response.add(new Shop.Response(shop));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/users/shops/{shopId}") // get /myShop
    public ResponseEntity<?> getMyShopByShopId(@RequestHeader String authorization,
                                               @PathVariable String shopId) {
        Shop shop = shopService.getShopByShopId(authorization, shopId);
        List<Menu> menuList = menuService.getList(null, shopId);
        List<Review> reviewList = reviewService.getList(null, shopId);
        UserShopMark usm = usmService.get(authorization, shopId);
        char marked = usm == null ? 'N' : 'Y';
        Shop.Info response = new Shop.Info(shop, menuList, reviewList, marked);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    @GetMapping("shops/list") // get shopsList
    public ResponseEntity<?> selectShopList(
            @Nullable @RequestHeader String authorization,
            @Nullable @RequestParam String category,
            @Nullable @RequestParam String sortTarget,
            @Nullable @RequestParam String shopName) {

        System.out.println("shops list - Query Date Check!");
        System.out.println("category : " + category);
        System.out.println("sortTarget : " + sortTarget);
        System.out.println("shopName : " + shopName);
        char marked;
        List<Shop> usmList;
        List<Shop.Dao> daoList = shopService.getList(category, sortTarget, shopName); // 얘는 이렇게 하는게 좋을듯..
        List<Shop.ResponseMarked> response = new ArrayList<>();
        if (authorization != null) {
            usmList = usmService.getList(authorization);
            for (Shop.Dao dao : daoList) {
                marked = 'N';
                for (Shop shop : usmList) {
                    if (shop.getId().equals(dao.getShopId())) {
                        marked = 'Y';
                        usmList.remove(shop);
                        if (usmList.isEmpty()) break; // remove 하기 때문에. 0 이상일 때는 있다는 말이 됨. null check로는 해결 불가능

                    }
                }
                response.add(new Shop.ResponseMarked(dao, marked));
            }
        } else {
            for (Shop.Dao dao : daoList) {
                response.add(new Shop.ResponseMarked(dao, 'N'));
            }
        }



        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("shops") // post shops 매장등록     Form-data로 받음 => Param. requestbody를 안적으면 자동 param 매핑 해주는듯
    public ResponseEntity<?> postShop(Shop.PostRequest request, @RequestHeader String authorization) throws ParseException {
        Shop result = shopService.post(authorization, request);
        Shop.Response response = new Shop.Response(result);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 생성이므로 201번을 리턴.
    }

    @Transactional // delete
    @DeleteMapping("shops/{shopId}")
    public ResponseEntity<?> deleteShop(@RequestHeader String authorization, @PathVariable String shopId) {
        shopService.delete(authorization, shopId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Transactional
    @PatchMapping("shops") // patch shops
    public ResponseEntity<?> patchShop(@RequestHeader String authorization, @RequestBody Shop.PatchRequest request) {
        System.out.println("매장수정 시작");
        Shop shop = shopService.patch(authorization, request);
        Shop.Response response = new Shop.Response(shop);
        System.out.println("매장수정 종료");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("shops/{shopId}/open")
    public ResponseEntity<?> updateShopIsOpen(@RequestHeader String authorization, @PathVariable String shopId) {
        return new ResponseEntity<>(shopService.patchShopIsOpen(authorization, shopId), HttpStatus.OK);
    }


    //
    @Transactional
    @PatchMapping("shops/{shopId}/reserve")
    public ResponseEntity<?> updateShopIsRsPos(@RequestHeader String authorization, @PathVariable String shopId) {
        return new ResponseEntity<>(shopService.patchSHopIsRsPos(authorization, shopId), HttpStatus.OK);
    }

//    private List<Shop> getMyShopList(String loginId) {
//        return shopService.findByOwnerId(loginId);
//    }


}