package com.example.heartdiseasepredictor;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Input fields
    private EditText inputCp, inputThalach, inputSlope, inputRestecg,
            inputChol, inputTrestbps, inputFbs, inputOldpeak;
    // UI elements
    private Button predictButton;
    private TextView resultTextView;
    private ProgressBar progressBar;
    // Info icons (for fields 2–8)
    private ImageView info2, info3, info4, info5, info6, info7, info8;

    // Acceptable ranges for critical inputs
    private static final double MIN_CHOL = 100.0;
    private static final double MAX_CHOL = 300.0;
    private static final double MIN_MAX_HEART_RATE = 60.0;
    private static final double MAX_MAX_HEART_RATE = 220.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind input fields
        inputCp = findViewById(R.id.input_cp);
        inputThalach = findViewById(R.id.input_thalach);
        inputSlope = findViewById(R.id.input_slope);
        inputRestecg = findViewById(R.id.input_restecg);
        inputChol = findViewById(R.id.input_chol);
        inputTrestbps = findViewById(R.id.input_trestbps);
        inputFbs = findViewById(R.id.input_fbs);
        inputOldpeak = findViewById(R.id.input_oldpeak);

        // Bind UI elements for prediction and progress
        predictButton = findViewById(R.id.predict_button);
        resultTextView = findViewById(R.id.result_text);
        progressBar = findViewById(R.id.progress_bar);

        // Bind info icons for fields 2 through 8
        info2 = findViewById(R.id.info2);
        info3 = findViewById(R.id.info3);
        info4 = findViewById(R.id.info4);
        info5 = findViewById(R.id.info5);
        info6 = findViewById(R.id.info6);
        info7 = findViewById(R.id.info7);
        info8 = findViewById(R.id.info8);

        // Set info pop-up listeners using a helper method
        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "It should display the max heart rate achieved by an individual.";
                infoDialog("Max Heart rate:", info);
            }
        });

        info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "Peak exercise ST segment:\n0 = upsloping\n1 = flat\n2 = downsloping";
                infoDialog("Exercise ST:", info);
            }
        });

        info4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "It should display resting ECG results:\n0 = normal\n1 = ST-T abnormality\n2 = left ventricular hypertrophy";
                infoDialog("Resting ECG:", info);
            }
        });

        info5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "Enter your serum cholesterol level in mg/dL (100 to 300).";
                infoDialog("Cholesterol:", info);
            }
        });

        info6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "Enter your resting blood pressure in mmHg (typically 90 to 200).";
                infoDialog("Resting Blood Pressure:", info);
            }
        });

        info7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "If fasting blood sugar > 120mg/dL, enter 1; otherwise, enter 0.";
                infoDialog("Fasting Blood Sugar:", info);
            }
        });

        info8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "Enter the ST depression induced by exercise (can be a float).";
                infoDialog("ST Depression:", info);
            }
        });

        // Set listener for Predict button to simulate API connection and perform fake
        // prediction
        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Connecting to API...", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                resultTextView.setText("");

                // Simulate a network delay of 2 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);

                        // Get cholesterol and max heart rate values
                        double cholesterolValue, maxHeartRate;
                        try {
                            cholesterolValue = Double.parseDouble(inputChol.getText().toString());
                        } catch (NumberFormatException e) {
                            cholesterolValue = -1;
                        }
                        try {
                            maxHeartRate = Double.parseDouble(inputThalach.getText().toString());
                        } catch (NumberFormatException e) {
                            maxHeartRate = -1;
                        }

                        // Validate critical ranges
                        if (cholesterolValue < MIN_CHOL || cholesterolValue > MAX_CHOL) {
                            Toast.makeText(MainActivity.this, "Cholesterol value out of range", Toast.LENGTH_LONG)
                                    .show();
                            return;
                        }
                        if (maxHeartRate < MIN_MAX_HEART_RATE || maxHeartRate > MAX_MAX_HEART_RATE) {
                            Toast.makeText(MainActivity.this, "Max Heart Rate value out of range", Toast.LENGTH_LONG)
                                    .show();
                            return;
                        }

                        // Fake prediction logic
                        int fakePercentage;
                        String outcome;
                        if (cholesterolValue > 250 && maxHeartRate > 150) {
                            fakePercentage = 90 + new Random().nextInt(11); // 90-100%
                            outcome = "Very High Chances of Heart Disease";
                        } else if (cholesterolValue > 150) {
                            fakePercentage = 80 + new Random().nextInt(11); // 80-90%
                            outcome = "Chances of Heart Disease";
                        } else {
                            fakePercentage = 70 + new Random().nextInt(11); // 70-80%
                            outcome = "Chances of No Heart Disease";
                        }

                        String fakeResultText = fakePercentage + "% " + outcome;
                        resultTextView.setText(fakeResultText);
                    }
                }, 2000);
            }
        });
    }

    // Helper method to show an info dialog using the custom layout info_dialog.xml
    private void infoDialog(String title, String message) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.info_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = dialog.findViewById(R.id.closeDialog);
        TextView nameDialog = dialog.findViewById(R.id.nameDialog);
        TextView infoDialog = dialog.findViewById(R.id.infoDialog);

        nameDialog.setText(title);
        infoDialog.setText(message);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}