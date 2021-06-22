package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.OrderMenu;
import com.yjwdb2021.jumanji.data.Tab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, String> {
    @Query(value = "SELECT ORDER_SEQ.nextval FROM dual", nativeQuery = true)
    Long getOrderSeqNextVal();

    int countByIdContains(String orderId);

    Set<OrderMenu> findByTab_IdStartsWith(String tabId);

    Set<OrderMenu> findByIdStartsWith(String orderId);

    @Query(value = "select om.*\n" +
            "from ORDER_MENUS om\n" +
            "where om.TAB_ID  like :shopId || '%' and\n" +
            "     using = 'Y'",nativeQuery = true)
    public List<OrderMenu> getOrderMenuListByShopId(String shopId);

    @Query(value = "select om.tab_id\n" +
            "from ORDER_MenuS om\n" +
            "where om.TAB_ID  like :shopId || '%' and\n" +
            "     using = 'Y'\n" +
            "group by tab_id", nativeQuery = true)
    public List<String> getTabListByShopId(String shopId);

    @Query(value = "select om.*\n" +
            "from ORDER_MENUS om\n" +
            "where id like :orderId || 'o' || '%'" +
            "order by om.ID desc", nativeQuery = true)
    List<OrderMenu> getOrderMenuList(String orderId);


}
