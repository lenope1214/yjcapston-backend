package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.Option;
import com.yjwdb2021.jumanji.data.OptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroup, String> {
    List<OptionGroup> findByMenu_Id(String menuId);
}
