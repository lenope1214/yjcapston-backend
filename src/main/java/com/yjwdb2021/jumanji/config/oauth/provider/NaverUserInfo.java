package com.yjwdb2021.jumanji.config.oauth.provider;

import java.util.Arrays;
import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; // oAuth2User.getAttributes() 속성들 값 보기.

    public NaverUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    public String getPhone(){
        return String.join("", attributes.get("mobile").toString().split("-"));
    }
}
