package com.yjwdb2021.jumanji.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter @NoArgsConstructor
@ConfigurationProperties(prefix="iamport")
@Configuration // 이게 없으면 또 오류나네.. 전에는 잘만 됐는데
public class IamportConfig {
    private String key;
    private String secret;
}
