package com.cts.moviecruiser.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Date;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import com.cts.moviecruiser.domain.User;
import com.cts.moviecruiser.exception.UserAlreadyExistsException;
import com.cts.moviecruiser.exception.UserNotFoundException;
import com.cts.moviecruiser.repository.UserRepository;

@RunWith(SpringRunner.class)

public class UserServiceTest {
	@Test
	public void simpleTest() {
		assertTrue(true);
	}

	private UserRepository userRepo;
	private UserService userService;
	
	

	User usera = new User("aa111", "aa", "111", "aa111", new Date());
	User userb = new User("bb111", "bb", "111", "bb111", new Date());
	User userAlreadyExists = new User("bb111", "bb", "111", "bb111", new Date());
	User userNotFound = new User("cc111", "cc", "111", "cc111", new Date());

	@Before
	public void setUp() {
		
		userRepo = mock(UserRepository.class);
		userService = new UserServiceImpl(userRepo);
		
		// save
		Mockito.when(userRepo.save(usera)).thenReturn(usera);
		Mockito.when(userRepo.findById(usera.getUserId())).thenReturn(Optional.empty());

		// save - fail
		Mockito.when(userRepo.findById(userAlreadyExists.getUserId())).thenReturn(Optional.of(userAlreadyExists));

		// find
		
		Mockito.when(userRepo.findById(userb.getUserId())).thenReturn(Optional.of(userb));
		Mockito.when(userRepo.findByUserIdAndPassword(userb.getUserId(), userb.getPassword())).thenReturn(userb);

		//find - fail
		Mockito.when(userRepo.findById(userNotFound.getUserId())).thenReturn(Optional.empty());
		
	}

	@Test
	public void whenSaveUser_thenReturnTrue_onSuccess() throws UserAlreadyExistsException {
		boolean savedUser = false;
		savedUser = userService.saveUser(usera);
		assertThat(savedUser).isEqualTo(true);
	}

	@Test
	public void whenSaveUser_thenThrowException_IfUserExists() {
		Exception ex = null;
		boolean savedUser = false;
		try {
			savedUser = userService.saveUser(userAlreadyExists);
		} catch (UserAlreadyExistsException e) {
			ex = e;
		}
		assertThat(savedUser).isFalse();
		assertThat(ex.toString()).isEqualTo(
				new UserAlreadyExistsException("User already exists", userAlreadyExists.getUserId()).toString());

	}
	
	@Test
	public void whenFindUser_thenReturnUser_onSuccess() throws UserNotFoundException {
		User userFound;
		userFound = userService.findByUserIdAndPassword(userb.getUserId(),userb.getPassword());
		assertThat(userFound.getPassword()).isEqualTo(userb.getPassword());
		assertThat(userFound.getFirstName()).isEqualTo(userb.getFirstName());
	}

	@Test
	public void whenFindUser_thenThrowException_IfUserNotFound() {
		Exception ex = null;
		User userFound = null;
		try {
			userFound = userService.findByUserIdAndPassword(userNotFound.getUserId(),userNotFound.getPassword());
		} catch (UserNotFoundException e) {
			ex = e;
		}
		assertThat(userFound).isNull();
		assertThat(ex.toString()).isEqualTo(
				new UserNotFoundException("User not found", userNotFound.getUserId()).toString());

	}


}
