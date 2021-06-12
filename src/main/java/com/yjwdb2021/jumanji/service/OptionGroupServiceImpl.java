package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.data.DateOperator;
import com.yjwdb2021.jumanji.data.Menu;
import com.yjwdb2021.jumanji.data.Option;
import com.yjwdb2021.jumanji.data.OptionGroup;
import com.yjwdb2021.jumanji.repository.OptionGroupRepository;
import com.yjwdb2021.jumanji.service.exception.optionGroupException.OptionGroupNotFoundException;
import com.yjwdb2021.jumanji.service.interfaces.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OptionGroupServiceImpl implements BasicService<OptionGroup, OptionGroup.Request, String> {
    @Autowired
    OptionGroupRepository optionGroupRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ShopServiceImpl shopService;
    @Autowired
    MenuServiceImpl menuService;


    @Override
    public OptionGroup get(@Nullable String authorization, String... str) {
        return null;
    }

    @Override
    public List<OptionGroup> getList(@Nullable String authorization, String... str) {
        String menuId = str[0];
//        String loginId = userService.getMyId(authorization);

        menuService.isPresent(menuId);

        List<OptionGroup> oGroupList = optionGroupRepository.findByMenu_Id(menuId);


        return oGroupList;
    }

    @Override
    public OptionGroup post(@Nullable String authorization, OptionGroup.Request request) {
        String ogId = request.getShopId().substring(0, 2) + "og" + DateOperator.dateToYYYYMMDDHHMMSS(new Date());
        String loginId = userService.getMyId(authorization);
        Menu menu;
        // 값 체크
        System.out.println("Option Group 생성요청Id : " + loginId);
        System.out.println("option group id : " + ogId);


        // 유효성 검사
        userService.isPresent(loginId);
        shopService.isOwnShop(loginId, request.getShopId());
        menu = menuService.isPresent(request.getMenuId());



        OptionGroup og = OptionGroup.builder()
                .id(ogId)
                .name(request.getName())
                .max(request.getMax())
                .min(request.getMin())
                .menu(menu)
                .build();

        return optionGroupRepository.save(og);
    }

    @Override
    public OptionGroup patch(@Nullable String authorization, OptionGroup.Request request) {
        return null;
    }

    @Override
    public void delete(@Nullable String authorization, String... str) {

    }

    @Override
    public OptionGroup isPresent(String id) {
        Optional<OptionGroup> optionGroup = optionGroupRepository.findById(id);
        if(optionGroup.isPresent())return optionGroup.get();
        throw new OptionGroupNotFoundException();
    }

    @Override
    public boolean isEmpty(String id) {
        return false;
    }
}
