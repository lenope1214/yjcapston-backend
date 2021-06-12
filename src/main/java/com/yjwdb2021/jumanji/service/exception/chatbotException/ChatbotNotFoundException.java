package com.yjwdb2021.jumanji.service.exception.chatbotException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class ChatbotNotFoundException extends BasicException {
    public ChatbotNotFoundException(){
        super("error-1201", "챗봇 정보를 찾을 수 없습니다 !");
    }

    public ChatbotNotFoundException(String id){
        super("error-1201", "챗봇 정보를 찾을 수 없습니다 ! : " + id);
    }

    public ChatbotNotFoundException(String code, String message) {
        super(code, message);
    }


}