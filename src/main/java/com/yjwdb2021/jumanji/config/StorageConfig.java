package com.yjwdb2021.jumanji.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="storage")
@Configuration // 이게 없으면 또 오류나네.. 전에는 잘만 됐는데
public class StorageConfig {
    private String location;

    public StorageConfig() {
//        System.out.println("StorageConfig created...");
//        this.location = location;
    }
    public String getLocation() {
//        System.out.println("StorageConfig location:" + location);
        return location;
    }
    public void setLocation(String location) {
//        System.out.println("StorageConfig setLocation:" + location);
        this.location=location;
    }
}