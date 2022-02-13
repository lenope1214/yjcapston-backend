package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.data.User;
import com.yjwdb2021.jumanji.service.interfaces.BasicService;

import java.util.List;

public class TestService implements BasicService<String, User.Request, String> {
    // 개발 끝나기 한 2~3달전에
    @Override
    public String get(String authorization, String... str) {
        return null;
    }

    @Override
    public List<String> getList(String authorization, String... str) {
        return null;
    }

    @Override
    public String post(String authorization, User.Request request) {
        return null;
    }

    @Override
    public String patch(String authorization, User.Request request) {
        return null;
    }

    @Override
    public void delete(String authorization, String... str) {

    }

    @Override
    public String isPresent(String id) {
        return null;
    }

    @Override
    public boolean isEmpty(String id) {
        return false;
    }
}
