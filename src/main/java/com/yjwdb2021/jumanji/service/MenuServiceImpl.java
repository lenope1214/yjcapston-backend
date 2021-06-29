package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.data.DateOperator;
import com.yjwdb2021.jumanji.data.Menu;
import com.yjwdb2021.jumanji.data.Shop;
import com.yjwdb2021.jumanji.repository.MenuRepository;
import com.yjwdb2021.jumanji.service.exception.menuException.MenuHasExistException;
import com.yjwdb2021.jumanji.service.exception.menuException.MenuNotFoundException;
import com.yjwdb2021.jumanji.service.interfaces.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    HttpHeaders httpHeaders;
    @Autowired
    StorageServiceImpl storageService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ShopServiceImpl shopService;
    @Autowired
    MenuServiceImpl menuService;
//    public BigDecimal getMenuSeqNextVal() {
//        return menuRepository.getMenuSeqNextVal();
//    }

    public int count(String id) {
        return menuRepository.countMenusByIdContains(id);
    }

    
    @Transactional(readOnly = true)
    public Menu get(@Nullable String authorization, String... str) {
        String menuId = str[0];
        Menu menu = isPresent(menuId);
        return menu;
    }


    /**
     *
     * @param authorization  be null
     * @param str [0] : shopId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Menu> getList(@Nullable String authorization, String... str) {
        String shopId = str[0];
        shopService.isPresent(shopId);
        List<Menu> menuList;
        menuList = menuRepository.findByShopId(shopId);
        return menuList;
    }

    
    @Transactional
    public Menu post(String authorization, Menu.Request request) {
        Menu menu;
        Shop shop;
        System.out.println("request is null ? " + (request == null));
        System.out.println("shopID is  ? " + (request.getShopId()));
        System.out.println("shopID is null ? " + (request.getShopId() == null));
        String menuId = request.getShopId().substring(0, 2) + 'm' + DateOperator.dateToYYYYMMDDHHMMSS(new Date());

        isEmpty(menuId, request.getName());
        shop = shopService.isPresent(request.getShopId());
        String uri = "shop/" + request.getShopId() + "/" + "menu";
        String imgPath = null;
        if (request.getImg() != null && request.getImg().getSize() != 0)
            imgPath = storageService.store(request.getImg(), request.getImg().getResource().getFilename().replace(" ", "_"), uri.split("/"));
        menu = Menu.init()
                .id(menuId)
                .name(request.getName())
                .intro(request.getIntro())
                .price(request.getPrice())
                .duration(request.getDuration())
                .imgPath(imgPath)
                .shop(shop)
                .build();
        return menuRepository.saveAndFlush(menu);
    }

    public Menu patchStatus(String authorization,String shopId, String menuId, String target) {
        String loginId = userService.getMyId(authorization);
        shopService.isOwnShop(loginId, shopId);
        Menu menu = menuService.isPresent(menuId);
        menu.reverseStatus(target);
        return menuRepository.save(menu);
    }

    
    public Menu patch(String authorization, Menu.Request request) {
        // 변수
        String loginId = userService.getMyId(authorization);
        String uri = "shop/" + request.getShopId() + "/" + "menu";
        String imgPath = null;
        Menu menu;

        // 유효성 체크
        shopService.isOwnShop(loginId,request.getShopId());
        menu = isPresent(request.getMenuId());

        // 서비스
        if (request.getImg() != null && request.getImg().getSize() != 0)
            imgPath = storageService.store(request.getImg(), request.getImg().getResource().getFilename().replace(" ", "_"), uri.split("/"));
        menu.patch(request, imgPath);
        menuRepository.saveAndFlush(menu);

        return menu;
    }

    
    public void delete(@Nullable String authorization, String... str){
        String shopId = str[0];
        String menuId = str[1];
        String loginId = userService.getMyId(authorization);

        // 유효성 검사
        userService.isPresent(loginId); // 존재하는 유저인지
//        shopService.isPresent(shopId); // 존재하는 식당인지
        shopService.isOwnShop(loginId, shopId); // 내 식당인지 체크
        Menu menu = isPresent(menuId);
        menuRepository.delete(menu);
    }

    
    public Menu isPresent(String id) {
        Optional<Menu> menu = menuRepository.findById(id);
        if(menu.isPresent())return menu.get();
        throw new MenuNotFoundException();
    }

    
    public boolean isEmpty(String id, String name) {
        Menu menu = menuRepository.findByIdOrName(id,name);
        if(menu == null)return true;
        throw new MenuHasExistException();
    }
//    
//    @Transactional

}