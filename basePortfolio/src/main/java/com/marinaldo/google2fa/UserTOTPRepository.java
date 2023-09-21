package com.marinaldo.google2fa;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTOTPRepository extends JpaRepository<UserTOTP, Long>{

	Optional<UserTOTP> findByUsername(String userName);

	void deleteByUsername(String username);

}
