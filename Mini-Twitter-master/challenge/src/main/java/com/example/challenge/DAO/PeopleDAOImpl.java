package com.example.challenge.DAO;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.challenge.VO.People;
import com.example.challenge.VO.Followers;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Repository
public class PeopleDAOImpl implements PeopleDAO {
	private DataSource myDataSource;  // DataSource instance
	private NamedParameterJdbcTemplate myJDBCTemplate; // Database instance

	/**
	* Constructor
	*/
	@Autowired
	public PeopleDAOImpl(DataSource myDataSource) {
		this.myDataSource = myDataSource;
		this.myJDBCTemplate = new NamedParameterJdbcTemplate(myDataSource);
	}

	/**
	* RowMapper that maps data from the People SQL table into People objects.
	*/
	private RowMapper<People> PeopleMapper = (rs,rowNum) -> {
		People p = new People();
		p.setId(rs.getInt("id"));
		p.setHandle(rs.getString("handle"));
		p.setName(rs.getString("name"));
		return p;
	};

	/**
	* RowMapper that maps data from the Follower SQL table into Followers objects.
	*/
	private RowMapper<Followers> FollowerMapper = (rs,rowNum) -> {
		Followers f = new Followers();
		f.setUserHandle(getHandleFromId(rs.getInt("person_id")));
		f.setFollowerHandle(getHandleFromId(rs.getInt("follower_person_id")));
		return f;
	};
    
    
    /**
	* Function that returns all the users.
	* @return List of all users.
	*/
	@Override
	public List<People> getAllUsers() {
		String peoplequery = "SELECT * FROM people";
		List<People> result = myJDBCTemplate.query(peoplequery,PeopleMapper);
		return result;
	}
    
     /**
	* Function that returns all the users that the current user is following.
	* @return List of all users following the current user.
	*/
    @Override
	public List<People> getFollowing() {
		int follower_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("follower_id",follower_id);
		String followingquery = "SELECT p.* from people p, followers f where p.id = f.person_id and f.follower_person_id = :follower_id";
		List<People> result = myJDBCTemplate.query(followingquery,parameters,PeopleMapper);
		return result;
	}
    
    /**
	* Function that returns all the users that are following the current user.
	* @return List of all users being followed by the current user.
	*/
	@Override
	public List<People> getFollowers() {
		int user_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("user_id",user_id);
		String followerquery = "SELECT p.* from people p, followers f where p.id = f.follower_person_id and f.person_id = :user_id";
		List<People> result = myJDBCTemplate.query(followerquery,parameters,PeopleMapper);
		return result;
	}


	/**
	* Function to follow another user.
	* @param handle of the user to be followed.
    */
	@Override
	public String follow(String handle) {
        int follower_id=getIdFromHandle(handle);
		int people_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>(); 
		parameters.put("User",people_id);
		parameters.put("Follower",follower_id);
		try {
			if(!isFollower(follower_id)){
                String followquery = "INSERT INTO followers (person_id, follower_person_id) VALUES(:Follower,:User)";
				myJDBCTemplate.update(followquery,parameters);
                return "You are now following "+handle;
            }
			else
				return "You are already following "+handle;
		}
		catch(DataIntegrityViolationException d) {
			return "User does not exist";
		}
	}
    

	/**
	* Function to follow another user.
	* @param handle of the user to be unfollowed.
    */
	@Override
	public String unfollow(String handle) {
        int follower_id=getIdFromHandle(handle);
		int people_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("User",people_id);
		parameters.put("Follower",follower_id);
		
		try {
            if(isFollower(follower_id)){
            String unfollowquery = "DELETE FROM followers WHERE person_id = :Follower AND follower_person_id = :User";
			myJDBCTemplate.update(unfollowquery,parameters);
            return "You have stopped following "+handle;
		}
            else
              return "You are not following "+handle+ " So cant unfollow!";  
        }
		catch(DataIntegrityViolationException d) {
			return "User does not exist";
		}
	}
	
	
	/**
	* Function that lists all the users along with their most popular follower.
	* @return List of all users with their most popular follower.
	*/
	public List<Followers> usersWithMostPopularFollower() {
		String popularquery = "SELECT f2.person_id,(SELECT f1.follower_person_id FROM followers f1 WHERE f1.person_id=f2.person_id GROUP BY f1.follower_person_id ORDER BY COUNT(f1.follower_person_id) DESC LIMIT 1) AS follower_person_id FROM followers f2 GROUP BY f2.person_id";
		List<Followers> result = myJDBCTemplate.query(popularquery,FollowerMapper);
		return result;
	}

    
    /**
	* Function to check the current user already follows another user.
	* @param follower_id ID of the user that the currentr user is following
	* @return boolean variable that indicates whether or not the current user
	* already follows the person.
	*/
	public boolean isFollower(int follower_id) {
		int user_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("user",user_id);
		parameters.put("follower",follower_id);
		String sqlquery = "SELECT COUNT(1) FROM followers WHERE person_id = :follower AND follower_person_id = :user";
		Long l = myJDBCTemplate.queryForObject(sqlquery,parameters,Long.class);
		return l > 0;
	}
    
    
	/**
	* Function that returns the ID of the current user.
	* @return ID of current user.
	*/
	public int getId() {
		String temp;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
			temp = ((UserDetails)principal).getUsername();
		else
			temp = principal.toString();
			
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("handle",temp);
		String sqlquery = "SELECT id FROM people WHERE handle = :handle";
		int Id = myJDBCTemplate.queryForObject(sqlquery,parameters,Integer.class);
		return Id;
	}

    
    /**
	* Function to get Id of the user given the handle
    */
    public int getIdFromHandle(String handle){
        String getIdQuery = "SELECT id from people where handle =:handle";
        SqlParameterSource nameParam = new MapSqlParameterSource("handle", handle );

        try {
            int id = myJDBCTemplate.queryForObject(getIdQuery, nameParam, Integer.class);
            return id;
        }
         catch (Exception e) {
            return -1;
    }

    }
    
    /**
	* Function to get handle of the user given the id
    */
    public String getHandleFromId(int id){
        String getHandleQuery = "SELECT handle from people where id =:id";
        SqlParameterSource nameParam = new MapSqlParameterSource("id", id );

        try {
            String handle = myJDBCTemplate.queryForObject(getHandleQuery, nameParam, String.class);
            return handle;
        }
         catch (Exception e) {
            return null;
    }

    }
}
