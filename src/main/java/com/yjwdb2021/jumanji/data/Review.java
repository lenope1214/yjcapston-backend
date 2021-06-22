package com.yjwdb2021.jumanji.data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Multipart;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


/**
 *  식당 id : 매장 앞 2 + yyyyMMdd + seq(0~9반복)
 */
@Getter
@Entity
@Table(name="reviews") @NoArgsConstructor
public class Review {
    @Id
    @Column(length = 14)
    private String id; //리뷰번호

    private String content;//리뷰내용
    @Column(name = "reg_time")
    private Timestamp regTime;//등록일
    @Column(name = "parent_id")
    private String parentId; //부모글번호
    private int score;//리뷰 점수
    @Column(name = "img_url")
    private String imgUrl;//이미지경로
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id",nullable = false) @JsonIgnore
    private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "shop_id", nullable = false) @JsonIgnore
    private Shop shop;// 식당번호

    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "order_id", nullable = false) @JsonIgnore
    private Order order;// 식당번호

    @Builder(builderMethodName = "init")
    public Review(String id, String content, Timestamp regTime, String parentId, int score, String imgUrl, User user, Shop shop, Order order){
        this.id = id;
        this.content = content;
        this.regTime = regTime;
        this.parentId = parentId;
        this.score = score;
        this.imgUrl = imgUrl;
        this.user = user;
        this.shop = shop;
        this.order = order;
    }



    @Getter @AllArgsConstructor @ToString // 얘는 NoArgs대신 AllArgs.. 왜?
    public class Request{
        private String reviewId;
        private String shopId;
        private String orderId;
        private String content;
        private String parentId;
        private int score;
        private MultipartFile img;
    }

    @Getter @AllArgsConstructor
    public static class Response{
        private String reviewId;
        private String userId;
        private String shopId;
        private String content;
        private String parentId;
        private String regDate; // 등록일
        private int score;
        private String imgUrl;

        public Response(Review review){
            this.userId = review.getUser().getId();
            this.reviewId = review.id;
            this.shopId = review.getShop().getId();
            this.content = review.content;
            this.parentId = review.parentId;
            this.regDate = DateOperator.SYYYYMMDDHHMMSS.format(review.regTime);
            this.score = review.score;
            this.imgUrl = review.imgUrl;
        }
    }

}
