package com.yjwdb2021.jumanji.service.interfaces;

import com.yjwdb2021.jumanji.data.Shop;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShopService {

    public List<Shop.Dao> getList(String category, String sortTarget);

    public Shop post(String authorization, Shop.PostRequest request);

    public Shop patch(String authorization, Shop.PatchRequest request);

    public void delete(String authorization, String shopId);
}
