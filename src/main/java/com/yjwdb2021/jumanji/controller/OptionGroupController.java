package com.yjwdb2021.jumanji.controller;

import com.yjwdb2021.jumanji.data.Option;
import com.yjwdb2021.jumanji.data.OptionGroup;
import com.yjwdb2021.jumanji.service.OptionGroupServiceImpl;
import com.yjwdb2021.jumanji.service.OptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/")
public class OptionGroupController {

    @Autowired
    private OptionGroupServiceImpl optionGroupService;
    @Autowired
    private OptionServiceImpl optionService;

    @GetMapping("menus/options/groups/{menuId}")
    public ResponseEntity<?> getByMenuId(@PathVariable String menuId){
        List<OptionGroup> oGroupList = optionGroupService.getList(null, menuId);
        List<OptionGroup.Response> response = new ArrayList<>();
        for(OptionGroup optionGroup : oGroupList){
            response.add(new OptionGroup.Response(optionGroup));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("menus/options/groups")
    public ResponseEntity<?> postMenuOption(@RequestHeader String authorization, @RequestBody OptionGroup.Request request){
        OptionGroup oGroup = optionGroupService.post(authorization, request);
        OptionGroup.Response response = new OptionGroup.Response(oGroup);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
