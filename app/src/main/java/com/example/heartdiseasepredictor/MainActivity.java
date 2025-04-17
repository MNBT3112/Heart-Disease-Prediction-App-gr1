package com.example.heartdiseasepredictor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapp.models.PredictionResponse;
import com.example.myapp.network.ApiClient;
import com.example.myapp.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // onCreate is where we’ll call the prediction API.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure you have a valid layout; for example, activity_main.xml
        setContentView(R.layout.activity_main);

        // Create the ApiService instance from our Retrofit client.
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Sample input values for prediction. You can replace these with values from
        // user input.
        int cp = 3;
        int thalach = 150;
        int slope = 0;
        int restecg = 0;
        float chol = 233.0f;
        float trestbps = 145.0f;
        int fbs = 1;
        float oldpeak = 2.3f;

        // Make the network request.
        Call<PredictionResponse> call = apiService.predictHeartDisease(cp, thalach, slope, restecg, chol, trestbps, fbs,
                oldpeak);

        // Enqueue the call - this makes it asynchronous.
        call.enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String prediction = response.body().getHeartDiseasePrediction();
                    Toast.makeText(MainActivity.this, "Heart Disease Prediction: " + prediction, Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(MainActivity.this, "API call unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to call API: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}