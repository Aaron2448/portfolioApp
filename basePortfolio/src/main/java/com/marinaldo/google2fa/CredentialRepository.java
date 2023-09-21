package com.marinaldo.google2fa;

import com.warrenstrange.googleauth.ICredentialRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Transactional
@Component
public class CredentialRepository implements ICredentialRepository {

	@Autowired
	UserTOTPRepository totpRepository;

	@Override
	public String getSecretKey(String username) {

		return totpRepository.findByUsername(username).orElse(null).getSecretKey();
	}

	@Override
	public void saveUserCredentials(String username, String secretKey, int validationCode, List<Integer> scratchCodes) {
		
		if(getUser(username) != null) {
			totpRepository.deleteByUsername(username);
		}

		totpRepository.save(new UserTOTP(username, secretKey, validationCode, scratchCodes));
	}

	public UserTOTP getUser(String username) {
		return totpRepository.findByUsername(username).orElse(null);
	}
	
	public void deleteByUsername(String username) {
		totpRepository.deleteByUsername(username);
	}
}
