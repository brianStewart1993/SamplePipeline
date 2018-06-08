package com.ete;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
	        mockMvc.perform(get("/users/"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(contentType));
	        
	    }
	    
	    @Test
	    public void readSingleUser() throws Exception {
	        mockMvc.perform(get("/users/48"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(contentType));
	        
	    }
	    
	    @Test
	    public void readSingleUserByUsername() throws Exception {
	        mockMvc.perform(get("/users/GetByUsername/Brian"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType("text/plain;charset=UTF-8"));
	        
	    }

	    @Test
	    public void Register() throws Exception {
<<<<<<< HEAD
	        User user = new User(44, "testingggsssa", "testdatacus");
=======
	        User user = new User(44, "testagain", "testdatacus");
>>>>>>> 5260cb15cacf9a97358ef8df7b21757d7b687274
	        String bookmarkJson = json(user);

	        this.mockMvc.perform(post("/users/addCustom")
	                .contentType(contentType)
	                .content(bookmarkJson))
	                .andExpect(status().isCreated());
	    }
	    
	    @Test
	    public void Login() throws Exception {

<<<<<<< HEAD
	        this.mockMvc.perform(get("/users/login/?username=Briantest&password=testdataa"))
=======
	        this.mockMvc.perform(get("/users/login/?username=Rogerlee&password=password"))
>>>>>>> 5260cb15cacf9a97358ef8df7b21757d7b687274
	        .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"));
	    }
	    
	    @Test
	    public void deleteUser() throws Exception {

<<<<<<< HEAD
	        this.mockMvc.perform(delete("/users/delete/60"))
=======
	        this.mockMvc.perform(delete("/users/delete/59"))
>>>>>>> 5260cb15cacf9a97358ef8df7b21757d7b687274
	        .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"));
	    }
	    
	    @Test
	    public void updateUser() throws Exception {
<<<<<<< HEAD
	        User user = new User(48, "Rogerlee", "testdataa");
=======
	        User user = new User(49, "Briantest", "testdataa");
>>>>>>> 5260cb15cacf9a97358ef8df7b21757d7b687274
	        String bookmarkJson = json(user);

	        this.mockMvc.perform(put("/users/update")
	                .contentType(contentType)
	                .content(bookmarkJson))
	                .andExpect(status().isOk());
	    }
	    

	    @SuppressWarnings("unchecked")
		protected String json(Object o) throws IOException {
	        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	        this.mappingJackson2HttpMessageConverter.write(
	                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	        return mockHttpOutputMessage.getBodyAsString();
	    }
	}
