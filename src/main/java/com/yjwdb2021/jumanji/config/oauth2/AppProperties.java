package com.yjwdb2021.jumanji.config.oauth2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
@Configuration // 이게 없으면 또 오류나네.. 전에는 잘만 됐는데
public class AppProperties {
//    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

//    @Getter
//    @RequiredArgsConstructor
//    public static class Auth{
//    }

    @Getter
    @RequiredArgsConstructor
    public static class OAuth2{
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris){
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
