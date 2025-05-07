package com.ps.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup optionsGroup1, optionsGroup2, optionsGroup3, optionsGroup4, optionsGroup5,
            optionsGroup6, optionsGroup7, optionsGroup8, optionsGroup9, optionsGroup10;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Set up the Toolbar with the back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Enable the back button
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set the navigation click listener for the back button
        toolbar.setNavigationOnClickListener(v -> {
            // Navigate back to MainActivity when back button is clicked
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Close the current activity
        });

        optionsGroup1 = findViewById(R.id.optionsGroup1);
        optionsGroup2 = findViewById(R.id.optionsGroup2);
        optionsGroup3 = findViewById(R.id.optionsGroup3);
        optionsGroup4 = findViewById(R.id.optionsGroup4);
        optionsGroup5 = findViewById(R.id.optionsGroup5);
        optionsGroup6 = findViewById(R.id.optionsGroup6);
        optionsGroup7 = findViewById(R.id.optionsGroup7);
        optionsGroup8 = findViewById(R.id.optionsGroup8);
        optionsGroup9 = findViewById(R.id.optionsGroup9);
        optionsGroup10 = findViewById(R.id.optionsGroup10);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = 0;

                // Check answers for each question
                if (checkAnswer(optionsGroup1, "Hola")) score++;
                if (checkAnswer(optionsGroup2, "Gracias")) score++;
                if (checkAnswer(optionsGroup3, "Adiós")) score++;
                if (checkAnswer(optionsGroup4, "Gato")) score++;
                if (checkAnswer(optionsGroup5, "Perro")) score++;
                if (checkAnswer(optionsGroup6, "Manzana")) score++;
                if (checkAnswer(optionsGroup7, "Agua")) score++;
                if (checkAnswer(optionsGroup8, "Familia")) score++;
                if (checkAnswer(optionsGroup9, "Soleil")) score++;
                if (checkAnswer(optionsGroup10, "Amor")) score++;

                // Show the score result
                Toast.makeText(QuizActivity.this,
                        "You scored " + score + " out of 10!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkAnswer(RadioGroup group, String correctAnswer) {
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedButton = findViewById(selectedId);
            return selectedButton.getText().toString().equalsIgnoreCase(correctAnswer);
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Navigate back to MainActivity
        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // Close the current activity
        return true;
    }
}
