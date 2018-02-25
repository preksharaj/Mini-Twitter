/**
* Controller to handle All Follow and Following Actions by User
* @author Preksha Raj
*/

package com.example.challenge.Controllers;

import com.example.challenge.DAO.PeopleDAO;
import com.example.challenge.Service.PeopleService;
import com.example.challenge.VO.People;
import com.example.challenge.VO.Followers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RestController
public class PeopleController {

	@Autowired
    PeopleService peopleService;
    
    
    /**
	* Route to get all the available users.
	* @return List of all users.
	*/
	@RequestMapping(value="/people", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<People> getAllUsers() {
		return peopleService.getAllUsers();
	}
    
    /**
	* Route that gets a list of all the people the current user is following.
	* @return List of all users the current user is following.
	*/
	@RequestMapping(value="people/following", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<People> getFollowing() {
		return peopleService.getFollowing();
	}
    
    /**
	* Route that returns all the followers of the current user.
	* @return List of all followers.
	*/
	@RequestMapping(value="people/followers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<People> getFollowers() {
		return peopleService.getFollowers();
	}

    /**
	* Route to follow another user
    * @param handle of the user to be followed.
    */
    @RequestMapping(value="/people/follow/{handle}", method=RequestMethod.POST)
	@ResponseBody
	public String followUser(@PathVariable String handle) {
		return peopleService.follow(handle);
    }
    
	/**
	* Route to unfollow another user
	* @param handle of the user to be unfollowed.
	*/
	@RequestMapping(value="/people/unfollow/{handle}", method=RequestMethod.POST)
	@ResponseBody
	public String unfollowUser(@PathVariable String handle) {
		return peopleService.unfollow(handle);
	}

	/**
	* Route that returns all users along with their most popular follower. The
	* most popular follower is a person who the user follows, that has the most
	* followers. 
	* @return List of users along with their most popular follower
	*/
	@RequestMapping(value="/people/popular", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Followers> usersWithTheirMostPopularFollower() {
		return peopleService.usersWithMostPopularFollower();
	}
}
