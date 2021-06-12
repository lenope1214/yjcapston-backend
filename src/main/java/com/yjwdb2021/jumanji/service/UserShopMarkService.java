package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.data.Shop;
import com.yjwdb2021.jumanji.data.User;
import com.yjwdb2021.jumanji.data.UserShopMark;
import com.yjwdb2021.jumanji.data.UserShopMarkId;
import com.yjwdb2021.jumanji.repository.UserShopMarksRepository;
import com.yjwdb2021.jumanji.service.exception.markException.MarkHasExistException;
import com.yjwdb2021.jumanji.service.exception.markException.MarkNotFoundException;
import com.yjwdb2021.jumanji.service.interfaces.BasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserShopMarkService  {
    private final UserServiceImpl userService;
    private final ShopServiceImpl shopService;
    private final UserShopMarksRepository usmRepository;


    /**
     *
     * @param authorization = jwt
     * @param str = shopId 하나만 보내기.
     * @return
     */
    public UserShopMark get(@Nullable String authorization, String... str) {
        UserShopMark usm = null;
        String shopId = str[0];
        String loginId = null;

        if(authorization!=null)loginId = userService.getMyId(authorization);
        if (loginId != null) {
            usm = usmRepository.findByUserIdAndShopId(loginId, shopId);
        }
        return usm;
    }


    public List<Shop> getList(@Nullable String authorization, String... str) {
        // 변수
        String loginId;
        // 값 체크
        // 유효성 체크
        loginId = userService.getMyId(authorization);
        // 서비스
        List<Shop> shopList = usmRepository.findMyMarks(loginId);
        // 값 체크
        System.out.println("shopList >>>>>>>>>>>>>>>>");
        for (Shop shop : shopList){
            System.out.println("shop id : " + shop.getId());
            System.out.println("shop name : " + shop.getName());
        }
        System.out.println("shopList <<<<<<<<<<<<<<<<");
        return shopList;
    }


    public void post(@Nullable String authorization, UserShopMark.Request request) {
        // 변수
        UserShopMark usm;
        UserShopMarkId usmId;
        User user;
        Shop shop;
        // 값 체크


        // 유효성 체크

        user = userService.isLogin(authorization);
        shop = shopService.isPresent(request.getShopId());
        usmId = new UserShopMarkId(user, shop);
//        usm = new UserShopMarkId();
        isEmpty(usmId);


        // 서비스
        usm = new UserShopMark(usmId);
        usmRepository.save(usm);

        // 값 확인

//        return usmRepository.save(usm);
    }


    public UserShopMark patch(@Nullable String authorization, UserShopMark.Request request) {
        return null;
    }


    public void delete(@Nullable String authorization, String... str) {
        // 변수
        String loginId;
        String shopId = str[0];

        // 값 체크

        // 유효성 체크
        loginId = userService.getMyId(authorization);
        UserShopMark usm = usmRepository.findByUserIdAndShopId(loginId, shopId);

        // 서비스
        usmRepository.delete(usm);

        // 값 체크
    }


    public UserShopMark isPresent(UserShopMarkId id) {
        Optional<UserShopMark> usm = usmRepository.findById(id);
        if(usm.isPresent())return usm.get();
        throw new MarkHasExistException(id.getShop().getId());
    }


    public void isEmpty(UserShopMarkId id) {
        Optional<UserShopMark> usm = usmRepository.findById(id);
        if(!usm.isEmpty())
            throw new MarkNotFoundException(id.getShop().getId());
    }
}
