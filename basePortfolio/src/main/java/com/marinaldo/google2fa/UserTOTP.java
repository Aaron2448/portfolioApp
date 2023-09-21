package com.marinaldo.google2fa;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserTOTP {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String secretKey;
    private int validationCode;
    private List<Integer> scratchCodes;
    
	public UserTOTP(String username, String secretKey, int validationCode, List<Integer> scratchCodes) {
		super();
		this.username = username;
		this.secretKey = secretKey;
		this.validationCode = validationCode;
		this.scratchCodes = scratchCodes;
	}
}