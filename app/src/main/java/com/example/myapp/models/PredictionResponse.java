package com.example.myapp.models;

public class PredictionResponse {
    // The field name should match the one in your Flask API response JSON.
    // For example, if your API returns: {"heart_disease_prediction": "0"}
    private String heart_disease_prediction;

    public String getHeartDiseasePrediction() {
        return heart_disease_prediction;
    }

    public void setHeartDiseasePrediction(String heart_disease_prediction) {
        this.heart_disease_prediction = heart_disease_prediction;
    }
}