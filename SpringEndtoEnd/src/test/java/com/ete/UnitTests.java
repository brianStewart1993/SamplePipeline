package com.ete;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;



import com.ete.models.User;
import com.ete.repo.UserRepository;
import com.ete.service.UserService;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UnitTests {
	

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    
    private final Logger log = LoggerFactory.getLogger(UnitTests.class);


    @SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private UserService userService;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

	 @Before
	    public void setup() throws Exception {
	        this.mockMvc = webAppContextSetup(webApplicationContext).build();
	 }

	    @Test
	    public void readUsers() throws Exception {
	    	List<User> users = new ArrayList<>();
	    	log.info("Executing unit test to get all users");
	      users = userService.getAllUsers();
	      assertNotNull(users);
	        
	    }
	    
	    @Test
	    public void readSingleUser() throws Exception {
	    	User user = new User();
	    	log.info("Executing unit test to get user with id: ", 48);
		      user = userService.getUser(48);
		      assertNotNull(user);
		        
	    }
	    
	    @Test
	    public void readSingleUserByUsername() throws Exception {
	       
	    	
	    	String user;
	    	log.info("Executing unit test to get user Briantest by username");
		      user = userService.getUserByUsername("Briantest");
		      assertEquals("[User [id=49, username=Briantest, password=testdataa]]", user);
		        
	    }

	    @Test
		@Transactional
	    public void Register() throws Exception {
	        User user = new User(44, "testmatrix", "testdatacus");
	        userService.Register(user.getUsername(), user.getPassword());
	        log.info("Executing unit test to register user then executes a login attempt for that user");
	      String result = userService.login(user.getUsername(), user.getPassword());
	      assertThat(result).contains("testmatrix");
	      assertThat(result).contains("testdatacus");
	 
	    }


	@Test
	public void Login() throws Exception {
		
		String result = userService.login("Briantest", "testdataa");
		log.info("Executing unit test to login user Briantest");
	      assertThat(result).contains("Briantest");
	      assertThat(result).contains("testdataa");
	}
	    
	    @Test
		@Transactional
	    public void deleteUser() throws Exception {
	        userService.deleteUser(48);
	        log.info("Executing unit test to delete user with id: ", 48);
	        User user = new User();
	       user = userService.getUser(48);
	        assertNull(user);
	    }
	    
	   @Test
	   @Transactional
	public void updateUser() throws Exception {
		User user = new User(48, "Tesssssstkkkk", "testdataa");
		User userResult = new User(48, "Tesssssstkkkk", "testdataa");
		log.info("Executing unit test to update user with id: ", 48);
		userService.updateUser(user);
		userResult = userService.getUser(48);
		assertThat(userResult.toString()).contains("Tesssssstkkkk");
	    assertThat(userResult.toString()).contains("testdataa");	
	}

	@SuppressWarnings("unchecked")
		protected String json(Object o) throws IOException {
	        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	        this.mappingJackson2HttpMessageConverter.write(
	                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	        return mockHttpOutputMessage.getBodyAsString();
	    }
	}