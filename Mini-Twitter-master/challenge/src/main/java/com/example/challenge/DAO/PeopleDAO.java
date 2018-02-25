package com.example.challenge.DAO;

import java.util.List;
import com.example.challenge.VO.People;
import com.example.challenge.VO.Followers;

public interface PeopleDAO {
    List<People> getAllUsers();
    List<People> getFollowing();
    List<People> getFollowers();
    String follow(String handle);
	String unfollow(String handle);
	List<Followers> usersWithMostPopularFollower();
    boolean isFollower(int follower_id);
	int getId();
    int getIdFromHandle(String handle);
    String getHandleFromId(int id);
	
}
