package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Timestamp> {

    List<Order> findALLByUser_Id(String user_Id);

    // 이성복 돼지???

    List<Order> findAllByShop_IdOrderById(String shopId);

    @EntityGraph(value = "Statistics.between")
    List<Order> findByStatusAndShopAndIdIsBetweenOrderByIdDesc(String status, Shop shop, Date start, Date end);


    @Query(value = "select o.ID            id,\n" +
            "       o.STATUS,\n" +
            "       o.ORDER_REQUEST orderRequest,\n" +
            "       o.PEOPLE,\n" +
            "       o.USE_POINT     usePoint,\n" +
            "       o.AMOUNT,\n" +
            "       o.ARRIVE_TIME   arriveTime,\n" +
            "       o.PAY_TIME      payTime,\n" +
            "       o.PG,\n" +
            "       o.PAY_METHOD    payMethod,\n" +
            "       o.ACCEPT,\n" +
            "       o.SHOP_ID       shopId,\n" +
            "       o.USER_ID       userId,\n" +
            "       o.REASON,\n" +
            "       case\n" +
            "           when r.id is not null then 'Y'\n" +
            "           else 'N' end\n" +
            "                       reviewed\n" +
            "from orders o\n" +
            "         left join REVIEWS R on o.ID = R.ORDER_ID\n" +
            "where o.USER_ID = :userId\n" +
            "order by o.ID desc", nativeQuery = true)
    List<Order.MyInfo> myOrderListContainsReviewed(String userId);

//    List<Order.ContainsMenu> findBy(String userId);


    @Query(value = "select \n" +
            "       case\n" +
            "           when o.USER_ID = :userId then 'Y'\n" +
            "           else 'N'\n" +
            "           end result\n" +
            "from ORDERS o\n" +
            "where id = :id", nativeQuery = true)
    char isMyOrder(String userId, Timestamp id);

    @Query(value = "select distinct nvl((select sum(o2.AMOUNT)\n" +
            "                     from orders o2\n" +
            "                     where o2.STATUS = 'pd'\n" +
            "                       and o2.SHOP_ID = :shopId\n" +
            "                       and o2.id >= :aDate\n" +
            "                       and o2.id <= to_date(:bDate)+1)\n" +
            "                    , 0) sumPd,\n" +
            "                nvl((select sum(o2.AMOUNT)\n" +
            "                     from orders o2\n" +
            "                     where o2.STATUS = 'rf'\n" +
            "                       and o2.SHOP_ID = :shopId\n" +
            "                       and o2.id >= :aDate\n" +
            "                       and o2.id <= to_date(:bDate)+1)\n" +
            "                    , 0) sumRf\n" +
            "from dual", nativeQuery = true)
    Statistics.SumPdRf getSumPdRfBetween(String shopId, String aDate, String bDate);

    @Query(value = "select distinct nvl((select sum(o2.AMOUNT)\n" +
            "                     from orders o2\n" +
            "                     where o2.STATUS = 'pd'\n" +
            "                       and o2.SHOP_ID = :shopId\n" +
            "                       and o2.id >= TRUNC(to_date(:aDate), 'IW')\n" +
            "                       and o2.id < NEXT_DAY(to_date(:aDate), '월요일'))\n" +
            "                    , 0) sumPd,\n" +
            "                nvl((select sum(o2.AMOUNT)\n" +
            "                     from orders o2\n" +
            "                     where o2.STATUS = 'rf'\n" +
            "                       and o2.SHOP_ID = :shopId\n" +
            "                       and o2.id >= TRUNC(to_date(:aDate), 'IW')\n" +
            "                       and o2.id < NEXT_DAY(to_date(:aDate), '월요일'))\n" +
            "                    , 0) sumRf\n" +
            "from dual", nativeQuery = true)
    Statistics.SumPdRf getSumPdRfWeek(String shopId, String aDate);

    @Query(value = "select distinct nvl((select sum(o2.AMOUNT)\n" +
            "                     from orders o2\n" +
            "                     where o2.STATUS = 'pd'\n" +
            "                       and o2.SHOP_ID = :shopId\n" +
            "                       and to_char(o2.id,'yyyyMMdd') = to_date(:aDate,'yyyyMMdd'))\n" +
            "                    , 0) sumPd,\n" +
            "                nvl((select sum(o2.AMOUNT)\n" +
            "                     from orders o2\n" +
            "                     where o2.STATUS = 'rf'\n" +
            "                       and o2.SHOP_ID = :shopId\n" +
            "                       and to_char(o2.id,'yyyyMMdd') = to_date(:aDate,'yyyyMMdd'))\n" +
            "                    , 0) sumRf\n" +
            "from dual", nativeQuery = true)
    Statistics.SumPdRf getSumPdRfDate(String shopId, String aDate);

    @Query(value = "select distinct nvl((select sum(o2.AMOUNT)\n" +
            "                     from orders o2\n" +
            "                     where o2.STATUS = 'pd'\n" +
            "                       and o2.SHOP_ID = :shopId\n" +
            "                       and o2.id >= TRUNC(to_date(:aDate,'yyyyMMdd'),'MM')\n" +
            "                       and o2.id < ADD_MONTHS( TRUNC(to_date(:aDate,'yyyyMMdd'),'MM'), 1 ))\n" +
            "                    , 0) sumPd,\n" +
            "                nvl((select sum(o2.AMOUNT)\n" +
            "                     from orders o2\n" +
            "                     where o2.STATUS = 'rf'\n" +
            "                       and o2.SHOP_ID = :shopId\n" +
            "                       and o2.id >= TRUNC(to_date(:aDate,'yyyyMMdd'),'MM')\n" +
            "                       and o2.id < ADD_MONTHS( TRUNC(to_date(:aDate,'yyyyMMdd'),'MM'), 1 ))\n" +
            "                    , 0) sumRf\n" +
            "from dual", nativeQuery = true)
    Statistics.SumPdRf getSumPdRfMonth(String shopId, String aDate);


//    Order findByOrderAndShop(Order order, Shop shop);

}