package com.ps.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private EditText inputWord;
    private TextView txtTranslation;
    private Button btnTranslate, btnFavorite, btnStartQuiz;

    private HashMap<String, WordInfo> dictionary; // Change to store WordInfo objects
    private ArrayList<String> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputWord = findViewById(R.id.inputWord);
        txtTranslation = findViewById(R.id.txtTranslation);
        btnTranslate = findViewById(R.id.btnTranslate);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);

        // Initialize data structures
        favorites = new ArrayList<>();
        dictionary = loadDictionaryFromJSON();

        // Handle translation
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = inputWord.getText().toString().trim().toLowerCase();
                if (dictionary.containsKey(word)) {
                    WordInfo wordInfo = dictionary.get(word);
                    String translated = wordInfo.translation;
                    String language = wordInfo.language;
                    // Display translation and language
                    txtTranslation.setText(translated + " (" + language + ")");
                } else {
                    txtTranslation.setText("Translation not found.");
                }
            }
        });

        // Start quiz activity
        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to load dictionary from JSON file in assets
    private HashMap<String, WordInfo> loadDictionaryFromJSON() {
        HashMap<String, WordInfo> result = new HashMap<>();
        try {
            InputStream inputStream = getAssets().open("dictionary.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            // Read the JSON file
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            // Parse the JSON
            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());

            // Iterate through the JSON object and add keys and values to the dictionary
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject wordDetails = jsonObject.getJSONObject(key);
                String translation = wordDetails.getString("translation");
                String language = wordDetails.getString("language");

                // Store WordInfo object with translation and language
                result.put(key.toLowerCase(), new WordInfo(translation, language));
            }

            // Close resources
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load dictionary", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    // WordInfo class to store translation and language
    private class WordInfo {
        String translation;
        String language;

        WordInfo(String translation, String language) {
            this.translation = translation;
            this.language = language;
        }
    }
}
