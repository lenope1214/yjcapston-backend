package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.Option;
import com.yjwdb2021.jumanji.data.OrderMenuOption;
import com.yjwdb2021.jumanji.data.OrderMenuOptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuOptionRepository extends JpaRepository<OrderMenuOption, OrderMenuOptionId> {

}
