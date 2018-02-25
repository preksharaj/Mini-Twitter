package com.example.challenge.Service;

import java.util.List;
import com.example.challenge.VO.People;
import com.example.challenge.VO.Followers;

public interface PeopleService {
    List<People> getAllUsers();
    List<People> getFollowing();
    List<People> getFollowers();
    String follow(String handle);
	String unfollow(String handle);
	List<Followers> usersWithMostPopularFollower();
}