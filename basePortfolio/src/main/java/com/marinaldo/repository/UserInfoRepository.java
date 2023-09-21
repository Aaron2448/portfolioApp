package com.marinaldo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.marinaldo.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{

	UserInfo findByEmail(String email);

	@Query(nativeQuery = true, value = "SELECT email FROM portfolio_user_info WHERE email = ?1")
    String doesUserEmailAlreadyExist(String email);

	boolean existsByEmail(String email);
	
}
