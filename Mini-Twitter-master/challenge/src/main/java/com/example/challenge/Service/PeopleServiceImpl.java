package com.example.challenge.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.challenge.DAO.PeopleDAO;
import com.example.challenge.VO.People;
import com.example.challenge.VO.Followers;
import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService {
    
    @Autowired
    PeopleDAO peopleDao;
    
     @Override
	public List<People> getAllUsers(){
        return peopleDao.getAllUsers();
    }
    
    @Override
	public List<People> getFollowing(){
        return peopleDao.getFollowing();
    }
    
    @Override
	public List<People> getFollowers(){
       return peopleDao.getFollowers(); 
    }
    
     @Override
    public String follow(String handle){
        return peopleDao.follow(handle);
    }
    
    @Override
	public String unfollow(String handle){
        return peopleDao.unfollow(handle);
    }
    
    
    @Override
	public List<Followers> usersWithMostPopularFollower(){
        return peopleDao.usersWithMostPopularFollower();
    }
	
}
