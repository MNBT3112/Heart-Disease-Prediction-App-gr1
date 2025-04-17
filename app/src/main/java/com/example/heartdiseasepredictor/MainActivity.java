package com.example.heartdiseasepredictor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapp.models.PredictionResponse;
import com.example.myapp.network.ApiClient;
import com.example.myapp.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView predictionTextView, confidenceTextView, descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure activity_main.xml exists in res/layout

        // Link UI elements
        predictionTextView = findViewById(R.id.prediction_text);
        confidenceTextView = findViewById(R.id.confidence_text);
        descriptionTextView = findViewById(R.id.description_text);

        // Example input values for prediction (you can later gather these from user
        // input)
        int cp = 3;
        int thalach = 150;
        int slope = 0;
        int restecg = 0;
        float chol = 233.0f;
        float trestbps = 145.0f;
        int fbs = 1;
        float oldpeak = 2.3f;

        // Create API service and call the backend
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<PredictionResponse> call = apiService.predictHeartDisease(cp, thalach, slope, restecg, chol, trestbps, fbs,
                oldpeak);

        call.enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update the UI with detailed response data
                    PredictionResponse res = response.body();
                    predictionTextView.setText("Prediction: " + res.getHeartDiseasePrediction());
                    confidenceTextView.setText("Confidence: " + res.getConfidencePercentage());
                    descriptionTextView.setText("Description: " + res.getDescription());
                } else {
                    predictionTextView.setText("API call unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {
                predictionTextView.setText("Error: " + t.getMessage());
            }
        });
    }
}