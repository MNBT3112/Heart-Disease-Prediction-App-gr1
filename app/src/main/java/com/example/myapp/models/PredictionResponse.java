package com.example.myapp.models;

public class PredictionResponse {
    private String heart_disease_prediction;
    private String confidence_percentage;
    private String description;

    // Getters and setters
    public String getHeartDiseasePrediction() {
        return heart_disease_prediction;
    }

    public void setHeartDiseasePrediction(String heart_disease_prediction) {
        this.heart_disease_prediction = heart_disease_prediction;
    }

    public String getConfidencePercentage() {
        return confidence_percentage;
    }

    public void setConfidencePercentage(String confidence_percentage) {
        this.confidence_percentage = confidence_percentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}