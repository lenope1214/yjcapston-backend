package com.yjwdb2021.jumanji.service.external;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddressSearchApiService implements ExternalApiService {


    private Gson gson;

    @PostConstruct
    private void setup()
    {
        gson=new GsonBuilder().create();
    }


    public String searchAddress(String apiUrl){

        Map<String, String> requestHeaders = new HashMap<>(); // 키 밸류 (json)으로 보내기.
        // 헤더에 더 필요한 값 넣으려면
        // requestHeaders.put("key", "value"); 넣기!
        String responseBody = get(apiUrl, requestHeaders, "POST");
        String result = gson.toJson(responseBody);
        Map<String, String> resultMap = new HashMap<>(); // 결과 값 키 밸류로 변신!
        return gson.toJson(responseBody);
    }
}