package com.example.hacka.controller;

import com.example.hacka.dto.ChatGPTRequest;
import com.example.hacka.dto.ChatGPTResponse;
import com.example.hacka.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class PsychologistAIController {

    private ChatGPTRequest request = new ChatGPTRequest("gpt-3.5-turbo");

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt) {
        request.messages.add(new Message("user",prompt));
        ChatGPTResponse chatGptResponse = template.postForObject(apiURL,request,ChatGPTResponse.class);
        String response = chatGptResponse.getChoices().get(0).getMessage().getContent();
        request.messages.add(new Message("assistant", response));
        return response;
    }

}
