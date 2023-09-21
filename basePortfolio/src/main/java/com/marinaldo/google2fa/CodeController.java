package com.marinaldo.google2fa;

import com.marinaldo.service.UserInfoService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/code")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CodeController {

    private final GoogleAuthenticator gAuth;
    private final CredentialRepository credentialRepository;
    private final UserInfoService userInfoService;

    @SneakyThrows
    @GetMapping("/generate/{username}")
    public void generate(@PathVariable String username, HttpServletResponse response) {
    	System.out.println("Starting Generate Method");
    	
    	if(credentialRepository.getUser(username) != null) {
    		System.out.println("Email Exists");
    		credentialRepository.deleteByUsername(username);
    	}
    	
        final GoogleAuthenticatorKey key = gAuth.createCredentials(username);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("Howzat", username, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    @PostMapping("/validate/key")
    public Validation validateKey(@RequestBody ValidateCodeDto body) { 
    	
    	Validation validate = new Validation(gAuth.authorizeUser(body.getUsername(), body.getCode()));
    	
    	if(validate.isValid()) {
    
    		userInfoService.update2FAStatus(body.getUsername(), true);
    	}
    	
        return validate;
    }

    @GetMapping("/scratches/{username}")
    public List<Integer> getScratches(@PathVariable String username) {
        return getScratchCodes(username);
    }

    private List<Integer> getScratchCodes(@PathVariable String username) {
        return credentialRepository.getUser(username).getScratchCodes();
    }

    @PostMapping("/scratches")
    public Validation validateScratch(@RequestBody ValidateCodeDto body) {
        List<Integer> scratchCodes = getScratchCodes(body.getUsername());
        Validation validation = new Validation(scratchCodes.contains(body.getCode()));
        scratchCodes.remove(body.getCode());
        
        if(validation.isValid()) {
        	userInfoService.update2FAStatus(body.getUsername(), false);
        	credentialRepository.deleteByUsername(body.getUsername());
        }
        
        return validation;
    }
}
