package com.example.heartdiseasepredictor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Declare the UI elements
    private EditText inputCp, inputThalach, inputSlope, inputRestecg,
            inputChol, inputTrestbps, inputFbs, inputOldpeak;
    private Button predictButton, tipsButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Link to your XML

        // Bind views from the layout
        inputCp = findViewById(R.id.input_cp);
        inputThalach = findViewById(R.id.input_thalach);
        inputSlope = findViewById(R.id.input_slope);
        inputRestecg = findViewById(R.id.input_restecg);
        inputChol = findViewById(R.id.input_chol);
        inputTrestbps = findViewById(R.id.input_trestbps);
        inputFbs = findViewById(R.id.input_fbs);
        inputOldpeak = findViewById(R.id.input_oldpeak);

        predictButton = findViewById(R.id.predict_button);
        tipsButton = findViewById(R.id.tips_button);
        resultTextView = findViewById(R.id.result_text);

        // Initially hide the TIPS button
        tipsButton.setVisibility(View.GONE);

        // Set listener for Predict button
        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // (Fake code) Ignore the actual input values.
                // For a fake demo, you can either use a static value,
                // or generate a random percentage. We'll use a random percentage.
                Random random = new Random();
                // Generate a random percentage between 80 and 90 (for realism); you can adjust
                // as needed.
                int fakePercentage = 70 + random.nextInt(11); // 80 to 90

                // Optionally, simulate two types of predictions based on a random condition
                // Here, randomly decide if it's heart disease or no heart disease.
                boolean hasDisease = random.nextBoolean();
                String diseaseResult = hasDisease ? "Chances of Heart Disease" : "Chances of No Heart Disease";

                // Display the fake result in the result TextView
                String fakeResultText = fakePercentage + "% " + diseaseResult;
                resultTextView.setText(fakeResultText);

                // Make the TIPS button visible after prediction
                tipsButton.setVisibility(View.VISIBLE);
            }
        });

        // Optional: Set listener for TIPS button
        tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // For the fake demo, simply update the result view with a tip.
                String tipText = "Tip: Exercise regularly, eat healthily, and monitor your cholesterol!";
                resultTextView.setText(resultTextView.getText() + "\n" + tipText);
            }
        });
    }
}