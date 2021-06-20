package com.yjwdb2021.jumanji.data;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_shop_marks")
@Getter @NoArgsConstructor @ToString
public class UserShopMark {
    @EmbeddedId
    private UserShopMarkId id;

    @Transient
    private List<Shop> markList;

    public UserShopMark(UserShopMarkId id){
        this.id = id;
    }

    public UserShopMark(User user, Shop shop){
        this.id = new UserShopMarkId(user, shop);
    }

    @Getter @NoArgsConstructor
    public static class Request {
        private String shopId;
    }

    @Getter
    public static class Response{
//        private User.Response user;
//        private Shop.Response shop;
        private List<Shop.Response> shopList = new ArrayList<>();

//        public Response(UserShopMark usm){
//            this.user = new User.Response(usm.getId().getUser());
//            this.shop = new Shop.Response(usm.getId().getShop());
//        }
        public Response(List<Shop> markShopList){
            for(Shop shop : markShopList){
                Shop.ResponseMarked shopResponse = new Shop.ResponseMarked(shop, 'Y');
                this.shopList.add(shopResponse);
            }
        }
    }


}
