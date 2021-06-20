package com.yjwdb2021.jumanji.data;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor @AllArgsConstructor @Data
public class Statistics {

    private SumPdRf sumPdRf;
    private List<Order> pdOrderList;
    private List<Order> rfOrderList;

    public interface SumPdRf{
        Integer getSumPd();
        Integer getSumRf();
    }


    @Getter @NoArgsConstructor
    public static class Response{
        private SumPdRf sumPdRf;
        private List<Order.Response> pdOrderList = new ArrayList<>();
        private List<Order.Response> rfOrderList = new ArrayList<>();

        // 생성자 변환작업 .. 귀찮아~
        public Response(Statistics statistics){
            this.sumPdRf = statistics.sumPdRf;
            for(Order order : statistics.pdOrderList){
                this.pdOrderList.add(new Order.Response(order));
            }
            for(Order order : statistics.rfOrderList){
                this.rfOrderList.add(new Order.Response(order));
            }
        }
    }
}
