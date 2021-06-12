package com.yjwdb2021.jumanji.data;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter @AllArgsConstructor @NoArgsConstructor @Setter
public class Pos {
    private String tabNo;
    private String orderRequest;
    private int people;
    private Set<OrderMenu> orderMenuSet;

    public static class Request {
    }
}
