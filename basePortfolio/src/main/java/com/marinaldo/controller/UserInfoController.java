package com.marinaldo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.marinaldo.dto.AuthRequest;
import com.marinaldo.model.UserInfo;
import com.marinaldo.repository.UserInfoRepository;
import com.marinaldo.service.JwtService;
import com.marinaldo.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/user")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserInfoRepository userInfoRepo;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public String addNewUser(@RequestBody UserInfo userInfo) {
	
		return userInfoService.addUser(userInfo);
		
	}
	
	
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())); 
			
		if(authentication.isAuthenticated()) {
			
			return jwtService.generateToken(authRequest.getEmail());
			
		} else {
			
			throw new UsernameNotFoundException("Invalid credentials.");
			
		}
		
	}

   	@GetMapping("/getProfile")
    public ResponseEntity<UserInfo> getUserProfile(@AuthenticationPrincipal UserInfo userInfo) {
       	System.out.println("Start of getProfile method"); 
	   	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        System.out.println("controller method called getProfile " + currentUserName);
        UserInfo currentUserDto = userInfoRepo.findByEmail(currentUserName);

        return ResponseEntity.ok(currentUserDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
           
            request.getSession().invalidate();
            return ResponseEntity.ok("User logged out successfully.");
        }
       
        return ResponseEntity.ok("No user to log out.");
    }
	  
}
