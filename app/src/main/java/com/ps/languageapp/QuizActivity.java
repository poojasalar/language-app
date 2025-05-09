package com.ps.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private List<RadioGroup> optionGroups;
    private Button btnSubmit;

    // Correct answers in order corresponding to the question RadioGroups
    private final String[] correctAnswers = {
            "Hola", "Gracias", "Adiós", "Gato", "Perro",
            "Manzana", "Agua", "Familia", "Soleil", "Amor"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Set up the toolbar with a back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> navigateBack());

        // Initialize RadioGroups
        optionGroups = new ArrayList<>();
        optionGroups.add(findViewById(R.id.optionsGroup1));
        optionGroups.add(findViewById(R.id.optionsGroup2));
        optionGroups.add(findViewById(R.id.optionsGroup3));
        optionGroups.add(findViewById(R.id.optionsGroup4));
        optionGroups.add(findViewById(R.id.optionsGroup5));
        optionGroups.add(findViewById(R.id.optionsGroup6));
        optionGroups.add(findViewById(R.id.optionsGroup7));
        optionGroups.add(findViewById(R.id.optionsGroup8));
        optionGroups.add(findViewById(R.id.optionsGroup9));
        optionGroups.add(findViewById(R.id.optionsGroup10));

        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> evaluateQuiz());
    }

    private void evaluateQuiz() {
        int score = 0;
        for (int i = 0; i < optionGroups.size(); i++) {
            if (checkAnswer(optionGroups.get(i), correctAnswers[i])) {
                score++;
            }
        }

        Toast.makeText(this,
                "You scored " + score + " out of " + correctAnswers.length + "!",
                Toast.LENGTH_LONG).show();
    }

    private boolean checkAnswer(RadioGroup group, String correctAnswer) {
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selected = findViewById(selectedId);
            String selectedText = selected.getText().toString().trim();
            return selectedText.equalsIgnoreCase(correctAnswer.trim());
        }
        return false;
    }

    private void navigateBack() {
        startActivity(new Intent(QuizActivity.this, MainActivity.class));
        finish(); // Optional: Close QuizActivity
    }

    @Override
    public boolean onSupportNavigateUp() {
        navigateBack();
        return true;
    }
}
