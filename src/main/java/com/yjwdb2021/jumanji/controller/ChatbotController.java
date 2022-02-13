package com.yjwdb2021.jumanji.controller;

import com.yjwdb2021.jumanji.data.Chatbot;
import com.yjwdb2021.jumanji.service.ChatbotServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ChatbotController  {
    private final ChatbotServiceImpl chatbotService;

    @GetMapping("shops/{shopId}/chatbots")
    public ResponseEntity<?> getShopsChatbotList(@PathVariable String shopId) throws Exception {
        List<Chatbot> chatbotList = chatbotService.getList(null, shopId);
        List<Object> response = new ArrayList<>();
        for(Chatbot chatbot: chatbotList){
            response.add(new Chatbot.Response(chatbot));
        }
a        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("chatbots")
    public ResponseEntity<?> postChatbot(@RequestHeader String authorization, @RequestBody Chatbot.Request request){
        Chatbot chatbot = chatbotService.post(authorization, request);
        Chatbot.Response response = new Chatbot.Response(chatbot);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("chatbots") // put <->
    public ResponseEntity<?> patchChatbot(@RequestHeader String authorization, @RequestBody Chatbot.Request request){
        Chatbot chatbot = chatbotService.patch(authorization, request);
        Chatbot.Response response = new Chatbot.Response(chatbot);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("chatbots/{chatbotId}")
    public ResponseEntity<?> deleteChatbot(@RequestHeader String authorization, @PathVariable Long chatbotId){
        chatbotService.delete(authorization, chatbotId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
