package com.cts.moviecruiser.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.cts.moviecruiser.domain.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class UserRepositoryTest {
	
	@Autowired
	private transient UserRepository userRepository;
	public void setRepo(final UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	User usera = new User("aa111", "aa", "111", "aa111", new Date());
	@Test
	public void testSaveNewUser() throws Exception{
		userRepository.save(usera);
		final User user = userRepository.getOne(usera.getUserId());
		assertThat(user.getPassword()).isEqualTo(usera.getPassword());
	}
	
	@Test
	public void testFindByUserIdAndPassword() throws Exception{
		userRepository.save(usera);
		final User user = userRepository.findByUserIdAndPassword(usera.getUserId(),usera.getPassword());
		assertThat(user.getFirstName()).isEqualTo(usera.getFirstName());
		assertThat(user.getLastName()).isEqualTo(usera.getLastName());
	}
}
