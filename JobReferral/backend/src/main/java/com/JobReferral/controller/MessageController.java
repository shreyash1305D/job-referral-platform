package com.JobReferral.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JobReferral.entities.Message;
import com.JobReferral.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/sender/{id}")
    public List<Message> getMessagesBySender(@PathVariable int id) {
        return messageService.getMessagesBySender(id);
    }

    @GetMapping("/receiver/{id}")
    public List<Message> getMessagesByReceiver(@PathVariable int id) {
        return messageService.getMessagesByReceiver(id);
    }
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

}

