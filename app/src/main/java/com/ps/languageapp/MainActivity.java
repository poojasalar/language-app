package com.ps.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private EditText inputWord;
    private TextView txtTranslation;
    private Button btnTranslate, btnStartQuiz;

    private HashMap<String, wordInfo> dictionary;
    private FirebaseFirestore db;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        // Initialize UI
        inputWord = findViewById(R.id.inputWord);
        txtTranslation = findViewById(R.id.txtTranslation);
        btnTranslate = findViewById(R.id.btnTranslate);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();

        // Define the dictionary manually
        dictionary = createDictionary();

        // Upload dictionary to Firestore
        uploadDictionaryToFirestore(dictionary);

        // Set Translate button behavior
        btnTranslate.setOnClickListener(v -> {
            String word = inputWord.getText().toString().trim().toLowerCase();
            if (dictionary.containsKey(word)) {
                wordInfo info = dictionary.get(word);
                txtTranslation.setText(info.translation + " (" + info.language + ")");
            } else {
                txtTranslation.setText("Translation not found.");
            }
        });

        // Set Quiz button (stub)
        btnStartQuiz.setOnClickListener(v -> {
            // Navigate back to MainActivity when back button is clicked
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();  // Close the current activity
        });
    }

    // Define dictionary data directly in code
    private HashMap<String, wordInfo> createDictionary() {
        HashMap<String, wordInfo> data = new HashMap<>();

        data.put("hello", new wordInfo("hola", "Spanish"));
        data.put("world", new wordInfo("mundo", "Spanish"));
        data.put("thank you", new wordInfo("merci", "French"));
        data.put("goodbye", new wordInfo("adiós", "Spanish"));
        data.put("cat", new wordInfo("chat", "French"));
        data.put("dog", new wordInfo("chien", "French"));
        data.put("please", new wordInfo("per favore", "Italian"));
        data.put("apple", new wordInfo("manzana", "Spanish"));
        data.put("book", new wordInfo("livre", "French"));
        data.put("water", new wordInfo("agua", "Spanish"));
        data.put("friend", new wordInfo("amico", "Italian"));
        data.put("family", new wordInfo("famille", "French"));
        data.put("love", new wordInfo("amore", "Italian"));
        data.put("house", new wordInfo("casa", "Spanish"));
        data.put("good morning", new wordInfo("buongiorno", "Italian"));
        data.put("good night", new wordInfo("bonne nuit", "French"));
        data.put("peace", new wordInfo("paz", "Spanish"));
        data.put("bread", new wordInfo("pan", "Spanish"));
        data.put("soup", new wordInfo("soupe", "French"));
        data.put("salt", new wordInfo("sale", "Italian"));

        // Add more if needed...

        return data;
    }

    // Upload dictionary to Firestore
    private void uploadDictionaryToFirestore(HashMap<String, wordInfo> dictionary) {
        for (Map.Entry<String, wordInfo> entry : dictionary.entrySet()) {
            String word = entry.getKey();
            wordInfo info = entry.getValue();

            Map<String, Object> data = new HashMap<>();
            data.put("translation", info.translation);
            data.put("language", info.language);

            db.collection("dictionary")
                    .document(word)
                    .set(data)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Uploaded: " + word))
                    .addOnFailureListener(e -> Log.e(TAG, "Failed to upload: " + word, e));
        }
        Toast.makeText(this, "Dictionary uploaded to Firestore", Toast.LENGTH_SHORT).show();
    }

    // Simple word data structure
    static class wordInfo {
        String translation;
        String language;

        wordInfo(String translation, String language) {
            this.translation = translation;
            this.language = language;
        }
    }
}
