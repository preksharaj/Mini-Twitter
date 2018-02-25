package com.example.challenge.DAO;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.challenge.VO.People;
import com.example.challenge.VO.Messages;
import java.util.Optional;
import java.util.*;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Repository
public class MessageDAOImpl implements MessageDAO {
	private DataSource myDataSource; //DataSource instance
	private NamedParameterJdbcTemplate myJDBCTemplate; //Database instance

	/**
	* Constructor 
	*/
	@Autowired
	public MessageDAOImpl(DataSource myDataSource) {
		this.myDataSource = myDataSource;
		this.myJDBCTemplate = new NamedParameterJdbcTemplate(myDataSource);
	}

	/**
	* RowMapper to map data from the Messages SQL table into Messages objects.
	*/
	private RowMapper<Messages> myMessageMapper = (rs,rowNum) -> {
		Messages m = new Messages();
		m.setUserHandle(getHandleFromId(rs.getInt("person_id")));
		m.setMessage(rs.getString("content"));
		return m;
	};

	
	/**
	* Function that searches user messages by keyword
	* @return List of all messages of current user and messgaes sent by user whom the current user follows that
	* contain the keyword.
	*/
	@Override
	public List<Messages> searchMessages(String keyword) {
		int user_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("UserID",user_id);
		String sqlquery = "SELECT * FROM messages WHERE (person_id=:UserID OR person_id IN (SELECT follower_person_id FROM followers WHERE person_id=:UserID)) AND content LIKE '%" + keyword + "%' ORDER BY id";
		List<Messages> result = myJDBCTemplate.query(sqlquery,parameters,myMessageMapper);
		return result;
	}
    
    /**
	* Function that returns all the messages
	* @return List of all messages of current user and messgaes sent by user whom the current user followes that
	* contain the keyword.
	*/
    @Override
    public List<Messages> getMessages(Optional<String> keyword) {
        int user_id = getId();
        List<Messages> result = new ArrayList<Messages>();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("UserID",user_id);
        try {
            if (keyword.isPresent()) {
                String sqlquery = "SELECT * FROM messages WHERE (person_id=:UserID OR person_id IN (SELECT follower_person_id FROM followers WHERE person_id=:UserID)) AND content LIKE '%" + keyword + "%' ORDER BY id";
                result = myJDBCTemplate.query(sqlquery,parameters,myMessageMapper);
		          
            } else {
                String sqlquery = "SELECT * FROM messages WHERE person_id=:UserID OR person_id IN (SELECT follower_person_id FROM followers WHERE person_id=:UserID)";
                result = myJDBCTemplate.query(sqlquery,parameters,myMessageMapper);
            }
            return result;
        }
        catch (Exception e){
            return null;
        }

    }


	/**
	* Function to return the ID of the current user
	* @return ID of current user
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
