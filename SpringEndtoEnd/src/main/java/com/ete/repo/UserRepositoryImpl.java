package com.ete.repo;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ete.models.User;

@Repository
@Transactional(readOnly = false)
public class UserRepositoryImpl {
		
		@PersistenceContext
	    EntityManager entityManager;
		
		private final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

	    public String Login(String username, String password) throws NoResultException  {
	    	
	    	String result = "";
	    	try
	    	{
	    		log.info("Querying database for user login credentials");
	   
	        Query query = entityManager.createNativeQuery("SELECT * FROM User u " +
	                "WHERE u.username = :uname and u.password = :pwd ", User.class);
	        query.setParameter("uname", username);
	        query.setParameter("pwd", password);
	        
	        result = query.getSingleResult().toString();
	        log.info("User login found");

	        }
	    	
	    	 catch (NoResultException e) {
	    	        System.out.println("PostingDaoImpl Error -> " + e.getLocalizedMessage());
	    	        result = "Username or Password Incorrect!";
	    	        log.error("Error message while logging in: ", e.getLocalizedMessage());
	      
		}
	    	return result;
	    }
	    
public String Register(String username, String password) throws NoResultException  {
	    	
	    	String result;
	    	try
	    	{
	    		log.info("Creating new user");
	   
	        Query query = entityManager.createNativeQuery("INSERT INTO User (username, password) VALUES (?, ?)"
	      , User.class);
	        query.setParameter(1, username);
	        query.setParameter(2, password);
	        
	        query.executeUpdate();
	        result = "User Created Successfully";
	        log.info(result);

	        }
	    	
	    	 catch (NoResultException e) {
	    	        System.out.println("PostingDaoImpl Error -> " + e.getLocalizedMessage());
	    	        result = e.getLocalizedMessage();
	    	        log.error("Error message while creating user: ", e.getLocalizedMessage());
	      
		}
	    	return result;
	    }

public String getUserByUsername(String username) throws NoResultException  {
	
	String result = "";
	try
	{
		log.info("Querying database for user with username: ", username);
		

    Query query = entityManager.createNativeQuery("SELECT * FROM User u " +
            "WHERE u.username = :uname ", User.class);
    query.setParameter("uname", username);
    
    result = query.getResultList().toString();
    log.info(result);

    }
	
	 catch (NoResultException e) {
	        System.out.println("PostingDaoImpl Error -> " + e.getLocalizedMessage());
	        result = e.getLocalizedMessage();
	        log.error("Error message while getting user: ", e.getLocalizedMessage());
	        
  
}
	return result;
}

public String getUserByUsernameAndId(String username, Integer id) throws NoResultException  {
	
	String result = "";
	try
	{
		log.info("Querying database for user with username and ID: ", username, id);

    Query query = entityManager.createNativeQuery("SELECT * FROM User u " +
            "WHERE u.username = :uname and u.id != :uid", User.class);
    query.setParameter("uname", username);
    query.setParameter("uid", id);
    
    result = query.getResultList().toString();
    log.info(result);

    }
	
	 catch (NoResultException e) {
	        System.out.println("PostingDaoImpl Error -> " + e.getLocalizedMessage());
	        result = e.getLocalizedMessage();
	        log.error("Error message while getting user: ", e.getLocalizedMessage());
  
}
	return result;
}
}
