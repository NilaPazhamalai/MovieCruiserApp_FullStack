package com.cts.moviecruiser.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.moviecruiser.domain.User;
import com.cts.moviecruiser.exception.UserAlreadyExistsException;
import com.cts.moviecruiser.exception.UserNotFoundException;
import com.cts.moviecruiser.service.SecurityTokenGenerator;
import com.cts.moviecruiser.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

	private MockMvc mvc;
	
	
	@MockBean
	private UserService userService;
	@MockBean
	private SecurityTokenGenerator generator;
	
	@InjectMocks
	private UserController userController;
	
	@Test
	public void simpleTest() {
		assertTrue(true);
		
	}
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	final static String URI = "/user";

	User usera = new User("aa111", "aa", "111", "aa111", new Date());
	User userb = new User("bb111", "bb", "111", "bb111", new Date());
	User userAlreadyExists = new User("bb111", "bb", "111", "bb111", new Date());
	User userNotFound = new User("cc111", "cc", "111", "cc111", new Date());
	User userServerError = new User("dd111", "dd", "111", "dd111", new Date());
	User userValidationErrorUserIdEmpty = new User("", "ee", "vv", "ee111", new Date());
	User userValidationErrorPasswordEmpty = new User("aa", "ee", "cc", "", new Date());
	User userValidationErrorBothEmpty = new User("", "ee", "bb", "", new Date());
	User userValidationPasswordError = new User("ww11", "ee", "bb", "11", new Date());

	
	
	@Test
	public void whenRegisterUser_ReturnUser_OnSuccess() throws Exception {
		when(userService.saveUser(Mockito.any(User.class))).thenReturn(true);

		mvc.perform(post(URI+"/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(usera)))
				.andExpect(status().isCreated());
		// .andExpect(jsonPath("$",Matchers.hasEntry("id", "4")));
		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}
	
	
	@Test
	public void whenRegisterUser_ReturnError_IfUserAlreadyExists() throws Exception {
		when(userService.saveUser(Mockito.any(User.class)))
		.thenThrow(new UserAlreadyExistsException("User already exists", userAlreadyExists.getUserId()));

		mvc.perform(post(URI+"/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(userAlreadyExists)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("User already exists"));
		// .andExpect(jsonPath("$",Matchers.hasEntry("id", "4")));
		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}
	
	
	@Test
	public void whenRegisterUser_ReturnError_IfServerError() throws Exception {
		when(userService.saveUser(Mockito.any(User.class)))
		.thenReturn(false);

		mvc.perform(post(URI+"/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(userAlreadyExists)))
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.message").value("Server Error"));
		// .andExpect(jsonPath("$",Matchers.hasEntry("id", "4")));
		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}
	
	
	// login--
	
	@Test
	public void whenLoginUser_ReturnError_IfInputUserIdEmpty() throws Exception {

		mvc.perform(post(URI+"/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(userValidationErrorUserIdEmpty)))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("UserId and password cannot be empty"));
		verify(userService, times(0)).findByUserIdAndPassword("","");
		verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void whenLoginUser_ReturnError_IfInputPasswordEmpty() throws Exception {

		mvc.perform(post(URI+"/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(userValidationErrorPasswordEmpty)))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("UserId and password cannot be empty"));
		verify(userService, times(0)).findByUserIdAndPassword("","");
		verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void whenLoginUser_ReturnError_IfInputUserIdAndPasswordEmpty() throws Exception {
		//when(userService.findByUserIdAndPassword(userValidationError.getUserId(),userValidationError.getUserId())).thenReturn(null)

		mvc.perform(post(URI+"/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(userValidationErrorBothEmpty)))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("UserId and password cannot be empty"));
		verify(userService, times(0)).findByUserIdAndPassword("","");
		verifyNoMoreInteractions(userService);
	}
	
	
	@Test
	public void whenLoginUser_ReturnError_IfUserNotFound() throws Exception {
		when(userService.findByUserIdAndPassword(Mockito.anyString(),Mockito.anyString()))
		.thenThrow(new UserNotFoundException("User not found", userNotFound.getUserId()));

		mvc.perform(post(URI+"/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(userNotFound)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("User not found"));
		verify(userService, times(1)).findByUserIdAndPassword(Mockito.anyString(),Mockito.anyString());
		verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void whenLoginUser_ReturnError_IfPasswordNotFound() throws Exception {
		when(userService.findByUserIdAndPassword(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(null);

		mvc.perform(post(URI+"/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(userNotFound)))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("Incorrect Password"));
		verify(userService, times(1)).findByUserIdAndPassword(Mockito.anyString(),Mockito.anyString());
		verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void whenLoginUser_ReturnError_IfPasswordWrong() throws Exception {
		when(userService.findByUserIdAndPassword(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(userValidationPasswordError);

		mvc.perform(post(URI+"/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(userNotFound)))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("Password does not match"));
		verify(userService, times(1)).findByUserIdAndPassword(Mockito.anyString(),Mockito.anyString());
		verifyNoMoreInteractions(userService);
	}
	
	
	@Test
	public void whenLoginUser_ReturnToken_OnSuccess() throws Exception {
		when(userService.findByUserIdAndPassword(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(usera);
		

		Map<String, String> jwtMap = new HashMap<>();
		jwtMap.put("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ");
		jwtMap.put("message", "User successfully logged in");
		
		when(generator.generateToken(usera))
		.thenReturn(jwtMap);

		mvc.perform(post(URI+"/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(usera)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.message").value("User successfully logged in"))
				.andExpect(jsonPath("$.token").value("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"));
		verify(userService, times(1)).findByUserIdAndPassword(Mockito.anyString(),Mockito.anyString());
		verifyNoMoreInteractions(userService);
	}
	
	
	
	
	public static String jsonToString(User user) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			return "[]";
		}
	}

}
	
	
