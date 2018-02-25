package com.example.challenge.Service;

import java.util.List;
import com.example.challenge.VO.Messages;
import java.util.Optional;
public interface MessageService {
    
    List<Messages> getMessages(Optional<String> keyword);
    
    List<Messages> searchMessages(String keyword);
}