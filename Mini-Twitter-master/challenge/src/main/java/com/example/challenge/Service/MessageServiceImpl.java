package com.example.challenge.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.challenge.DAO.MessageDAO;
import com.example.challenge.VO.Messages;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDAO messageDao;


    @Override
    public List<Messages> getMessages(Optional<String> keyword) {

        return messageDao.getMessages(keyword);
    }
    
    @Override
    public List<Messages> searchMessages(String keyword) {

        return messageDao.searchMessages(keyword);
    }
}