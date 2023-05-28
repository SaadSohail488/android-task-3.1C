package com.example.Task31c;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText userNameEditText;
    private Button startQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.editTextUserName);
        startQuizButton = findViewById(R.id.buttonStartQuiz);

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString().trim();

                // Pass the user's name to the next activity
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }
}