package com.yjwdb2021.jumanji.data;

import lombok.*;


import javax.persistence.*;


@Entity (name = "order_menu_options")
@NoArgsConstructor @AllArgsConstructor @Getter
@Builder
public class OrderMenuOption {
    @EmbeddedId
    OrderMenuOptionId id;

    @Column(length = 2)
    private int quantity;


    @Getter @NoArgsConstructor
    public static class Request{
        private String orderMenuId;
        private String optionId;
        private int quantity;
    }

    @AllArgsConstructor @Getter
    public static class Response{
        private String orderMenuId;
        private String optionId;
        private int quantity;
        public Response(OrderMenuOption omo){
            this.orderMenuId = omo.getId().getOrderMenu().getId();
            this.optionId = omo.getId().getOption().getId();
            this.quantity = omo.getQuantity();
        }

    }

}

