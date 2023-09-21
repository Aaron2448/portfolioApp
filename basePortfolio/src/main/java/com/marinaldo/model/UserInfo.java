package com.marinaldo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolio_user_info")
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String password;
	
	private String roles = "ROLE_USER";
	
	private boolean use2FA;
	    
	private String resetRecently = "no";

	public Object map(Object object) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
}
