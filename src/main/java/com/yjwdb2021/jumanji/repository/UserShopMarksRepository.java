package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.Shop;
import com.yjwdb2021.jumanji.data.UserShopMark;
import com.yjwdb2021.jumanji.data.UserShopMarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserShopMarksRepository extends JpaRepository<UserShopMark, UserShopMarkId> {

    @Query(value = "select usm.id.shop from UserShopMark usm where usm.id.user.id = :loginId")
    List<Shop> findMyMarks(String loginId);

    @Query(value = "select usm.* from USER_SHOP_MARKS usm where usm.USER_ID = :userId and usm.SHOP_ID = :shopId", nativeQuery = true)
    UserShopMark findByUserIdAndShopId(String userId, String shopId);

//    @Query(value = "delete from USER_SHOP_MARKS usm where usm.user_id = :id and usm.shop_id = :shopId" ,nativeQuery = true)
//    void deleteByShopID(String id, String shopId);

}
