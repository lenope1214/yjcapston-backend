package com.yjwdb2021.jumanji.controller;

import com.yjwdb2021.jumanji.data.Menu;
import com.yjwdb2021.jumanji.service.MenuServiceImpl;
import com.yjwdb2021.jumanji.service.ShopServiceImpl;
import com.yjwdb2021.jumanji.service.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/")
public class MenuController  {

    @Autowired
    MenuServiceImpl menuService;
    @Autowired
    ShopServiceImpl shopService;

    @Autowired
    StorageServiceImpl storageService;

    @Autowired
    HttpHeaders httpHeaders;


    //    @Transactional(readOnly = true)
//    @GetMapping("/menu")
//    public ResponseEntity<?> selectMenu(){
//        return null;
//    }

    @GetMapping("menus/{menuId}")
    public ResponseEntity<?> selectMenuById(@PathVariable String menuId) {
//        String menuId = request.getShopId() + request.getName();
        Menu menu = menuService.get(null, menuId);
        Menu.Response response = new Menu.Response(menu);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("menus/list/{shopId}")
    public ResponseEntity<?> selectMenuList(@PathVariable String shopId) {
        List<Menu> menuList = menuService.getList(null, shopId);
        List<Menu.Response> response = new ArrayList<>();
        for(Menu menu : menuList){
            response.add(new Menu.Response(menu));
        }
        if(menuList.isEmpty())return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping("menus") // post
    public ResponseEntity<?> postMenu(@RequestHeader String authorization,
                                      // required = false 해줌으로써 img가 null이 아니어도 되게 만듦.
                                      @RequestParam(required = false) Menu.Request request) {
        Menu menu = menuService.post(authorization, request);
        Menu.Response response = new Menu.Response(menu);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PatchMapping("menus") // patch
    public ResponseEntity<?> patchMenu(@RequestHeader String authorization, @RequestBody Menu.Request request) {
        Menu menu =  menuService.patch(authorization, request);
        Menu.Response response = new Menu.Response(menu);
        System.out.println("menuId : " + menu.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("shops/{shopId}/menus/{menuId}") // Delete
    public ResponseEntity<?> deleteMenu(@RequestHeader String authorization, @PathVariable String shopId, @PathVariable String menuId) throws AuthenticationException {
        System.out.println("메뉴 삭제 요청");
        menuService.delete(authorization, shopId, menuId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }// 05m20210426163000


    @PatchMapping("shops/{shopId}/menus/{menuId}/popular")
    public ResponseEntity<?> updateShopIsOpen(@RequestHeader String authorization, @PathVariable String shopId, @PathVariable String menuId) {
        Menu menu = menuService.patchStatus(authorization, shopId, menuId, "popular");
        return new ResponseEntity<>(menu.getIsPopular(), HttpStatus.OK);
    }


    //

    @PatchMapping("shops/{shopId}/menus/{menuId}/sale")
    public ResponseEntity<?> updateShopIsRsPos(@RequestHeader String authorization, @PathVariable String shopId, @PathVariable String menuId) {
        Menu menu = menuService.patchStatus(authorization, shopId, menuId, "sale");
        return new ResponseEntity<>(menu.getIsSale(), HttpStatus.OK);
    }
}