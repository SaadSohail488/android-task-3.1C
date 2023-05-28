package com.example.Task31c;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView textViewUserName;
    private ProgressBar progressBar;
    private TextView textViewQuestionNumber;
    private TextView textViewQuestion;
    private Button buttonOption1;
    private Button buttonOption2;
    private Button buttonOption3;
    private Button buttonSubmit;
    private Button buttonNext;

    private ArrayList<Question> questionList;
    private int currentQuestionIndex;
    private int score; // Declare the score variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewUserName = findViewById(R.id.textViewUserName);
        progressBar = findViewById(R.id.progressBar);
        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        buttonOption1 = findViewById(R.id.buttonOption1);
        buttonOption2 = findViewById(R.id.buttonOption2);
        buttonOption3 = findViewById(R.id.buttonOption3);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonNext = findViewById(R.id.buttonNext);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        textViewUserName.setText("Welcome, " + userName + "!");

        questionList = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
        questionList.add(new Question(
                "What is the capital of Australia?",
                List.of("Canberra", "Sydney", "Norrway"),
                0
        ));
        questionList.add(new Question(
                "Which planet has the most moons?",
                List.of("Saturn", "Earth", "Mars"),
                0
        ));
        questionList.add(new Question(
                "What is 10 multiplied by 7?",
                List.of("70", "10", "7"),
                0
        ));
        questionList.add(new Question(
                "How many bones do we have in an ear?",
                List.of("3", "2", "0"),
                0
        ));
        questionList.add(new Question(
                "In what country is the Chernobyl nuclear plant located?",
                List.of("Australia", "Ukraine", "USA"),
                1
        ));

        // Load the first question
        loadQuestion(currentQuestionIndex);

        // Set click listeners for the option buttons
        buttonOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOption(buttonOption1);
            }
        });

        buttonOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOption(buttonOption2);
            }
        });

        buttonOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOption(buttonOption3);
            }
        });

        // Set a click listener for the submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected answer from the user
                int selectedAnswerIndex = getSelectedAnswerIndex();
                // Get the correct answer index for the current question
                int correctAnswerIndex = questionList.get(currentQuestionIndex).getCorrectAnswerIndex();
                // Check if the selected answer is correct
                boolean isCorrect = selectedAnswerIndex == correctAnswerIndex;
                // Update the score
                if (isCorrect) {
                    score++;
                }
                // Change the button colors based on the correctness
                updateButtonColors(isCorrect);
                // Move to the next question
                buttonNext.setVisibility(View.VISIBLE);
                buttonSubmit.setEnabled(false);
            }
        });

        // Set a click listener for the next button
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next question
                moveToNextQuestion();
                buttonNext.setVisibility(View.GONE);
                buttonSubmit.setEnabled(true);
            }
        });
    }

    private void selectOption(Button selectedButton) {
        // Reset the selection state for all option buttons
        resetOptionSelection();
        // Set the selected state for the clicked button
        selectedButton.setSelected(true);
    }

    private void resetOptionSelection() {
        // Reset the selection state for all option buttons
        buttonOption1.setSelected(false);
        buttonOption2.setSelected(false);
        buttonOption3.setSelected(false);
    }

    private int getSelectedAnswerIndex() {
        // Determine the selected answer index based on user interaction
        if (buttonOption1.isSelected()) {
            return 0; // Assuming option 1 corresponds to index 0
        } else if (buttonOption2.isSelected()) {
            return 1; // Assuming option 2 corresponds to index 1
        } else if (buttonOption3.isSelected()) {
            return 2; // Assuming option 3 corresponds to index 2
        } else {
            return -1; // Return a default value or handle the case when no option is selected
        }
    }

    private void updateButtonColors(boolean isCorrect) {
        // Get the selected answer button
        Button selectedButton = getSelectedButton();

        if (selectedButton != null) {
            if (isCorrect) {
                setButtonColor(selectedButton, Color.GREEN);
            } else {
                setButtonColor(selectedButton, Color.RED);
                setButtonColor(getCorrectAnswerButton(), Color.GREEN);
            }
        }
    }
    private Button getSelectedButton() {
        if (buttonOption1.isSelected()) {
            return buttonOption1;
        } else if (buttonOption2.isSelected()) {
            return buttonOption2;
        } else if (buttonOption3.isSelected()) {
            return buttonOption3;
        } else {
            return null;
        }
    }

    private void resetButtonColors() {
        // Reset the button colors to their default state
        setButtonColor(buttonOption1, Color.BLACK);
        setButtonColor(buttonOption2, Color.BLACK);
        setButtonColor(buttonOption3, Color.BLACK);
    }

    private void setButtonColor(Button button, int color) {
        // Set the background color of the button
        button.setBackgroundColor(color);
    }

    private Button getCorrectAnswerButton() {
        // Determine the correct answer index for the current question
        int correctAnswerIndex = questionList.get(currentQuestionIndex).getCorrectAnswerIndex();

        // Determine the corresponding button based on the correct answer index
        Button correctAnswerButton = null;
        if (correctAnswerIndex == 0) {
            correctAnswerButton = buttonOption1;
        } else if (correctAnswerIndex == 1) {
            correctAnswerButton = buttonOption2;
        } else if (correctAnswerIndex == 2) {
            correctAnswerButton = buttonOption3;
        }

        return correctAnswerButton;
    }

    private void moveToNextQuestion() {
        // Move to the next question
        currentQuestionIndex++;

        // Check if all questions have been answered
        if (currentQuestionIndex < questionList.size()) {
            // Load the next question
            loadQuestion(currentQuestionIndex);
        } else {
            // All questions have been answered
            // Start the LastActivity and pass the score and username
            Intent intent = new Intent(QuizActivity.this, LastActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("userName", getIntent().getStringExtra("userName"));
            startActivity(intent);
        }
    }

    private void loadQuestion(int questionIndex) {
        // Retrieve the question from the question list based on the index
        Question question = questionList.get(questionIndex);
        // Set the question number
        int questionNumber = questionIndex + 1;
        textViewQuestionNumber.setText("Question " + questionNumber);
        // Set the question text
        textViewQuestion.setText(question.getQuestionText());
        // Set the answer options
        List<String> answerOptions = question.getAnswerOptions();
        buttonOption1.setText(answerOptions.get(0));
        buttonOption2.setText(answerOptions.get(1));
        buttonOption3.setText(answerOptions.get(2));
        // Reset the button colors
        resetButtonColors();
        // Update the progress bar
        updateProgressBar();
    }

    private void updateProgressBar() {
        // Calculate the percentage of completed questions
        int progress = (int) (((float) currentQuestionIndex / questionList.size()) * 100);
        // Update the progress bar
        progressBar.setProgress(progress);
    }

    public class Question {
        private String questionText;
        private List<String> answerOptions;
        private int correctAnswerIndex;

        public Question(String questionText, List<String> answerOptions, int correctAnswerIndex) {
            this.questionText = questionText;
            this.answerOptions = answerOptions;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public String getQuestionText() {
            return questionText;
        }

        public List<String> getAnswerOptions() {
            return answerOptions;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }
}
