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

    private HashMap<String, WordInfo> dictionary;
    private FirebaseFirestore db;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        inputWord = findViewById(R.id.inputWord);
        txtTranslation = findViewById(R.id.txtTranslation);
        btnTranslate = findViewById(R.id.btnTranslate);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Create dictionary
        dictionary = createDictionary();

        // Upload dictionary only once or conditionally
        uploadDictionaryToFirestore(dictionary);

        // Translate word
        btnTranslate.setOnClickListener(v -> {
            String word = inputWord.getText().toString().trim().toLowerCase();
            if (word.isEmpty()) {
                txtTranslation.setText("Please enter a word.");
                return;
            }
            WordInfo info = dictionary.get(word);
            if (info != null) {
                txtTranslation.setText(info.translation + " (" + info.language + ")");
            } else {
                txtTranslation.setText("Translation not found.");
            }
        });

        // Navigate to Quiz screen
        btnStartQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();  // Close the current activity
        });

    }

    private HashMap<String, WordInfo> createDictionary() {
        HashMap<String, WordInfo> data = new HashMap<>();
        data.put("hello", new WordInfo("hola", "Spanish"));
        data.put("world", new WordInfo("mundo", "Spanish"));
        data.put("thank you", new WordInfo("merci", "French"));
        data.put("goodbye", new WordInfo("adiós", "Spanish"));
        data.put("cat", new WordInfo("chat", "French"));
        data.put("dog", new WordInfo("chien", "French"));
        data.put("please", new WordInfo("per favore", "Italian"));
        data.put("apple", new WordInfo("manzana", "Spanish"));
        data.put("book", new WordInfo("livre", "French"));
        data.put("water", new WordInfo("agua", "Spanish"));
        data.put("friend", new WordInfo("amico", "Italian"));
        data.put("family", new WordInfo("famille", "French"));
        data.put("love", new WordInfo("amore", "Italian"));
        data.put("house", new WordInfo("casa", "Spanish"));
        data.put("good morning", new WordInfo("buongiorno", "Italian"));
        data.put("good night", new WordInfo("bonne nuit", "French"));
        data.put("peace", new WordInfo("paz", "Spanish"));
        data.put("bread", new WordInfo("pan", "Spanish"));
        data.put("soup", new WordInfo("soupe", "French"));
        data.put("salt", new WordInfo("sale", "Italian"));
        return data;
    }

    private void uploadDictionaryToFirestore(HashMap<String, WordInfo> dictionary) {
        for (Map.Entry<String, WordInfo> entry : dictionary.entrySet()) {
            String word = entry.getKey();
            WordInfo info = entry.getValue();

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

    // Reusable data class
    static class WordInfo {
        String translation;
        String language;

        WordInfo(String translation, String language) {
            this.translation = translation;
            this.language = language;
        }
    }
}
