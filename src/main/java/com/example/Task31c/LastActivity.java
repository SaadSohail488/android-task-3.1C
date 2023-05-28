package com.example.Task31c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LastActivity extends AppCompatActivity {
    private TextView textViewScore;
    private Button buttonTakeNewQuiz;
    private Button buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        textViewScore = findViewById(R.id.textViewScore);
        buttonTakeNewQuiz = findViewById(R.id.buttonTakeNewQuiz);
        buttonFinish = findViewById(R.id.buttonFinish);

        // Get the score value from the intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0); // 0 is the default value if score is not found

        // Retrieve the user name from the previous activity
        String userName = intent.getStringExtra("userName");

        // Create the score message
        String scoreMessage = "Congratulations " + userName + "\n            Your score:\n                   " + score + "/5";

        // Set the score message in the TextView
        textViewScore.setText(scoreMessage);

        // Set a click listener for the "Take New Quiz" button
        buttonTakeNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity to take a new quiz
                Intent intent = new Intent(LastActivity.this, MainActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });

        // Set a click listener for the "Finish" button
        Button buttonFinish = findViewById(R.id.buttonFinish);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the app
                finish();
            }
        });
    }
}
