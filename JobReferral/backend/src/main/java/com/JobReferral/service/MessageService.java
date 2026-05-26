package com.JobReferral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobReferral.entities.Message;
import com.JobReferral.entities.User;
import com.JobReferral.repository.MessageRepository;
import com.JobReferral.repository.UserRepository;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(Message message) {
        User sender = userRepository.findById(message.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(message.getReceiver().getId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        message.setSender(sender);
        message.setReceiver(receiver);

        return messageRepository.save(message);
    }

    public List<Message> getMessagesBySender(int senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    public List<Message> getMessagesByReceiver(int receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

	public List<Message> getAllMessages() {
		// TODO Auto-generated method stub
		return null;
	}
}
