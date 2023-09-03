package com.marinaldo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marinaldo.model.UserInfo;
import com.marinaldo.repository.UserInfoRepository;

@Service
public class UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public String addUser(UserInfo userInfo) {

			try {
			
				userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
				System.out.println("it got to here 1");
				userInfoRepo.save(userInfo);
				return "User added to database";
			
			} catch (Exception e) {
				
				return "Unknown error";
				
			}
		
	}
	
}
