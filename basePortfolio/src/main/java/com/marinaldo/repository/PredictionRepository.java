package com.marinaldo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.marinaldo.model.Prediction;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM prediction")
    List<Prediction> getAllPredictions();
    
    @Query(nativeQuery = true, value = "SELECT * FROM prediction WHERE email = ?1")
    Prediction getPredictionByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM prediction WHERE prediction_id = ?1")
	Prediction findByPredictionId(long id);

}
