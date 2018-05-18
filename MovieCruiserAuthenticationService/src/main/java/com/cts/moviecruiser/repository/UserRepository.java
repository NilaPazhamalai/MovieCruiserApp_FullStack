package com.cts.moviecruiser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.moviecruiser.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	@Query("select user from User user where userId=(?1) and password=(?2)")
	User validate(String userId, String password);
	
	User findByUserIdAndPassword(String userId, String password);
}
