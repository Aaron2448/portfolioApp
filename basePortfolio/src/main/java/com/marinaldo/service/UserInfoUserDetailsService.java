package com.marinaldo.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.marinaldo.config.UserInfoUserDetails;
import com.marinaldo.model.UserInfo;
import com.marinaldo.repository.UserInfoRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserInfoRepository userInfoRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<UserInfo> userInfo = Optional.ofNullable(userInfoRepo.findByEmail(email));
		
		return userInfo.map(UserInfoUserDetails::new)
			.orElseThrow(()-> new UsernameNotFoundException("UserInfo not found " + email ));
		
	}

}
