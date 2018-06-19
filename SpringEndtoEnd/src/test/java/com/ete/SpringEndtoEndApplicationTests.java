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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;



import com.ete.models.User;


@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SpringEndtoEndApplicationTests {
	

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private final Logger log = LoggerFactory.getLogger(SpringEndtoEndApplicationTests.class);


    @SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
	    	log.info("Using mockito to execute integration test to get all users");
	        mockMvc.perform(get("/users/"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(contentType));
	        
	    }
	    
	    @Test
	    public void readSingleUser() throws Exception {
	    	log.info("Using mockito to execute integration test to get user with id: ", 48);
	        mockMvc.perform(get("/users/48"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(contentType));
	        
	    }

	@Test
	public void readSingleUserFailure() throws Exception {
		log.info("Using mockito to execute integration test to get user with id: ", 1);
		mockMvc.perform(get("/users/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

	}
	    
	    @Test
	    public void readSingleUserByUsername() throws Exception {
	    	log.info("Using mockito to execute integration test to get user Briantest by username");
	        mockMvc.perform(get("/users/GetByUsername/Briantest"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType("text/plain;charset=UTF-8"));
	        
	    }
	    //Needs to be checked
	@Test
	public void readSingleUserByUsernameFailure() throws Exception {
		log.info("Using mockito to execute integration test to get user Brian1 by username");
		mockMvc.perform(get("/users/GetByUsername/Brian1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

	}

	    @Test
		@Transactional
	    public void Register() throws Exception {
	        User user = new User(44, "testingonetwo", "testdatacus");
	        String bookmarkJson = json(user);
            log.info("Using mockito to execute integration test to register a new user");
	        this.mockMvc.perform(post("/users/addCustom")
	                .contentType(contentType)
	                .content(bookmarkJson))
	                .andExpect(status().isCreated());
	    }

	@Test
	@Transactional
	public void RegisterAlreadyTaken() throws Exception {
		User user = new User(44, "Briantest", "testdatacus");
		String bookmarkJson = json(user);
        log.info("Using mockito to execute integration test to register a new user with a username already taken");
		this.mockMvc.perform(post("/users/addCustom")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isBadGateway());
	}

	@Test
	@Transactional
	public void RegisterNullFieldsOrTooManyOrTooFewFields() throws Exception {
		User user = new User(44, "", "testdatacus");
		String bookmarkJson = json(user);
        log.info("Using mockito to execute integration test to register a user with empty field");
		this.mockMvc.perform(post("/users/addCustom")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	public void RegisterDefaultRepo() throws Exception {
		User user = new User(44, "testingonetwothree", "testdatacus");
		String bookmarkJson = json(user);
        log.info("Using mockito to execute integration test register with default crud repo add function");
		this.mockMvc.perform(post("/users/add")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isOk());
	}


	@Test
	public void Login() throws Exception {
        log.info("Using mockito to execute integration test to test login with user Briantest");
		this.mockMvc.perform(get("/users/login/?username=Briantest&password=testdataa"))

				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));
	}

	@Test
	public void LoginCharacter() throws Exception {
		 log.info("Using mockito to execute integration test to test login with empty fields");
		this.mockMvc.perform(get("/users/login/?username=&password="))

				.andExpect(status().isBadGateway())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));
	}

	@Test
	public void LoginWrongCredentials() throws Exception {
		
		 log.info("Using mockito to execute integration test to test login with wrong credentials");
		this.mockMvc.perform(get("/users/login/?username=Brianffffff&password=testdataa"))

				.andExpect(status().isOk())
				.andExpect(content().string("Username or Password Incorrect!"))
				.andExpect(content().contentType("text/plain;charset=UTF-8"));
	}
	    
	    @Test
		@Transactional
	    public void deleteUser() throws Exception {
	    	log.info("Using mockito to execute integration test to delete user with id, 62");
	        this.mockMvc.perform(delete("/users/delete/62"))
	        .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"));
	    }
	    
	   @Test
	   @Transactional
	public void updateUser() throws Exception {
		User user = new User(48, "Tesssssstkkkk", "testdataa");
		String bookmarkJson = json(user);
		log.info("Using mockito to execute integration test to delete user with id, 48");

		this.mockMvc.perform(put("/users/update")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isOk());
	}

	@Test
	@Transactional
	public void updateUserNameAlreadyTaken() throws Exception {
		User user = new User(48, "Briantest", "testdataa");
		String bookmarkJson = json(user);
		log.info("Using mockito to execute integration test to delete user with id, 48 using an existing username for another user");

		this.mockMvc.perform(put("/users/update")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isInternalServerError());
	}

	@SuppressWarnings("unchecked")
		protected String json(Object o) throws IOException {
	        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	        this.mappingJackson2HttpMessageConverter.write(
	                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	        return mockHttpOutputMessage.getBodyAsString();
	    }
	}
