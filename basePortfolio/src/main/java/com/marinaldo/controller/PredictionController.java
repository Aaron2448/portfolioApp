package com.marinaldo.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.marinaldo.model.Prediction;
import com.marinaldo.model.UserInfo;
import com.marinaldo.repository.PredictionRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/prediction")
public class PredictionController {

    private final PredictionRepository predictionRepository;
   

    public PredictionController(PredictionRepository predictionRepository) {
        
    	this.predictionRepository = predictionRepository;
    	
    }
    
    // @PreAuthorize("hasAuthority")
    // @PreAuthorize("hasAuthority('ADMIN')")

    // R E A D of CRUD
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Prediction> getAllPredictions_CONT() {

    	return predictionRepository.getAllPredictions();
        
    }

    // R E A D of CRUD
    @GetMapping("/email/{email}")
    public Prediction getPredictionByEmail_CONT(@PathVariable String email) {
        
    	Prediction prediction = predictionRepository.getPredictionByEmail(email);
        
        return prediction;
        
    }
       
    // C R E A T E of CRUD
    @PostMapping("/create")
    public Prediction createPrediction(@RequestBody Prediction prediction) {
        
    	System.out.println(prediction.toString());
        predictionRepository.save(prediction);
        
        return prediction;
        
    }

    // U P D A T E of CRUD
   
    // D E L E T E of CRUD
    @DeleteMapping("/delete/{id}")
    public String deletePrediction(@PathVariable long id) {
        
    	String outcomeString = "";
    	
	    	try {
	    	
	    	Prediction prediction = predictionRepository.findByPredictionId(id);
	        
				if (prediction != null) {
				predictionRepository.deleteById(prediction.getPrediction_id());
			    outcomeString = "prediction " + prediction.getPrediction_id() + " has been deleted.";
			    
				} else {
	
	    		outcomeString = "Prediction was not deleted.";
	    		
				}
				
	    	} catch ( Exception e ) {
	        
	    		outcomeString = "Prediction was not deleted (catch)";
	        
	    	}
    	
    	return outcomeString;
    
    }
    
    static String regex = "([0-9]\\.|[0-9][0-9]\\.|0[0-9][0-9]\\.|1[0-9][0-9]\\.|2[0-5][0-5]\\.|2[0-4][0-9]\\.|25[0-5]\\.){3}([0-9]$|[0-9][0-9]$|0[0-9][0-9]$|1[0-9][0-9]$|2[0-5][0-5]$|2[0-4][0-9]$|25[0-5]$).*";
	static Pattern pattern = Pattern.compile(regex); 
  
    @PostMapping("/checkIp")
    public String checkThisIp(@RequestBody UserInfo userInfo) {
        
    	    Matcher matcher = pattern.matcher(userInfo.getName());	
	        boolean matchFound = matcher.find();
	    	System.out.println(userInfo.getName());
	        
	        
		    if(matchFound) {
		      return "Valid";
		    } else {
		      return "Invalid";
		    }
        
    }
    
}
