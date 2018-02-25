package com.example.challenge.DAO;

import java.util.List;
import com.example.challenge.VO.Messages;
import com.example.challenge.VO.People;
import java.util.Optional;

public interface MessageDAO {
    
    public List<Messages> getMessages(Optional<String> keyword);
	public List<Messages> searchMessages(String keyword);
	public int getId();
    String getHandleFromId(int id);
}
