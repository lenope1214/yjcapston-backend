package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, String> {

//    @Query(value = "select s from Shop s order by (select sum(?1) )")
//    List<Shop> findBySort(String sort);

    //from shops s
    //where s.category = nvl('일식', s.category)
    //order by (select sum(score) / count(score) from REVIEWS ) desc;
//    @Query(value = "select * from shops s where s.category = coalesce(:category, s.category) order by (select sum(r.score) / count(r.score) from REVIEWS r)", nativeQuery = true)
//    @Query(value = "select s from  Shop s  where s.category = coalesce(?1, s.category) order by (select sum(score)/count(score) from Review ) desc ")
    @Query(value = "select s.ID                                                           shopID,\n" +
            "       NAME,\n" +
            "       INTRO,\n" +
            "       OPEN_TIME                                                      OPENTIME,\n" +
            "       CLOSE_TIME                                                     CLOSETIME,\n" +
            "       ADDRESS,\n" +
            "       ADDRESS_DETAIL                                                 ADDRESSDETAIL,\n" +
            "       IS_RS_POS                                                      ISRSPOS,\n" +
            "       CATEGORY,\n" +
            "       OWNER_ID                                                       OWNERID,\n" +
            "       IS_OPEN                                                        ISOPEN,\n" +
            "       IMG_PATH                                                       IMGPATH,\n" +
            "       PHONE,\n" +
            "       coalesce(to_char(sum(r.score) / count(r.score), 'FM0.0'), '0') score,\n" +
            "       count(r.ID)                                                    reviews\n" +
            "from shops s\n" +
            "         left join REVIEWS r\n" +
            "                   on s.ID = r.SHOP_ID\n" +
            "where s.category = coalesce(:category, s.category) and\n" +
            "        s.name like '%' || coalesce(:shopName, s.name) || '%'\n" +
            "group by s.ID, NAME, INTRO, OPEN_TIME, CLOSE_TIME, ADDRESS, ADDRESS_DETAIL, IS_RS_POS, CATEGORY, OWNER_ID, IS_OPEN,\n" +
            "         IMG_PATH, PHONE\n" +
            "order by case :sortTarget\n" +
            "             when 'score' then score\n" +
            "             else IS_OPEN\n" +
            "             end desc", nativeQuery = true)
    List<Shop.Dao> getShopListByCategorySortTarget(String category, String sortTarget, String shopName);

//    @Query(value = "select * from shops s where s.category = coalesce(:category, s.category) order by (select sum(r.score) / count(r.score) from REVIEWS r) desc", nativeQuery = true)
////    @Query(value = "select * from shops s where s.category = coalesce(:category, s.category) order by (select sum(r.score) / count(r.score) from REVIEWS r)", nativeQuery = true)
////    @Query(value = "select s from  Shop s  where s.category = coalesce(?1, s.category) order by (select sum(score)/count(score) from Review ) desc ")
//    List<Shop> ShopByCategoryOrderByTarget(String category, String target);

    //    @Query(value = "select :category from dual", nativeQuery = true)
//    List<Shop> test(String category);


    List<Shop> findByCategory(String category);

    List<Shop> findAllByOwner_Id(String owner_id);

    Shop findByOwner(String id);

    @Query(value = "select s from Shop s where s.owner.id=?1")
    List<Shop> findByOwnerId(String ownerId);
}
