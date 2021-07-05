package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.Tab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TableRepository extends JpaRepository<Tab, String > {

    public List<Tab> findByIdContains(String shopId);

    Tab findByOrderId(Timestamp orderId);

    List<Tab> findByOrderNotNull();
}