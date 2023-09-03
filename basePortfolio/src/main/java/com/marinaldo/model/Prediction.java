package com.marinaldo.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "prediction")
public class Prediction {

    public Prediction() {
		// TODO Auto-generated constructor stub
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long prediction_id;
    
    @Column(nullable = false)
    private String email;
    
    @Column
    private String create_date;
    
    @Column 
    private String predition_for_premiers_2023;
   
}

