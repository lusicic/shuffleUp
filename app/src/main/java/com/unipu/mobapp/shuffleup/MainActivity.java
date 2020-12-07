package com.unipu.mobapp.shuffleup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int pressedCount = 0;
    private int maximumPressed;
    private String[] letterz;
    private String correctAns;
    private int correctAnswers=0;

    private int currentIndex = 0;

    private Word[] wordy = new Word[] {
            new Word("DOG", new String[]{"G", "D", "R", "O", "A"}, 3),
            new Word("MOUSE", new String[]{"O", "M", "U", "E", "S"}, 5),
            new Word("SEAL", new String[]{"E", "A", "N", "S", "L"}, 4),
            new Word("WOLF", new String[]{"F", "W", "M", "O", "L"},4),
            new Word("LION", new String[]{"N", "I", "A", "L", "O"}, 4),

    };


    TextView textTitle, textQuestion;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(0, 0);

        letterz = wordy[currentIndex].getLetters();
        correctAns = wordy[currentIndex].getAnswer();
        maximumPressed = wordy[currentIndex].getCount();

       startShuffle();

        btnBack = (Button) findViewById(R.id.backButton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);

            }
        });

    }

     private void startShuffle(){
         letterz = shuffleWord(letterz);

         for(String key : letterz){
             addView(((LinearLayout) findViewById(R.id.layoutParent)), key, ((EditText) findViewById(R.id.userAnswer)));
         }

         maximumPressed = wordy[currentIndex].getCount();
     }

    private String[] shuffleWord(String[] ar){
        Random rnd = new Random();

        for(int i = ar.length -1; i > 0; i--){
            int index = rnd.nextInt(i+1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText){
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.rightMargin = 20;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.letter));
        textView.setTextColor(this.getResources().getColor(R.color.colorWord));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        Typeface typeface = Typeface.SANS_SERIF;

        textView.setTypeface(typeface, Typeface.BOLD);

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textTitle = (TextView) findViewById(R.id.textTitle);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pressedCount < maximumPressed){
                    if(pressedCount == 0)
                        editText.setText("");

                        editText.setText(editText.getText().toString() + text);

                        textView.animate().alpha(0).setDuration(300);
                        pressedCount++;

                        if(pressedCount == maximumPressed) {
                            doValidate();
                        }
                    }
            }
        });

        viewParent.addView(textView);
    }

    private void doValidate(){
        pressedCount = 0;

        EditText editText = findViewById(R.id.userAnswer);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);

        if(editText.getText().toString().equals(correctAns)){
            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();

            editText.setText("");
            currentIndex++;
            correctAnswers++;

            if(correctAnswers==wordy.length){
                correctAnswers=0;
                currentIndex=0;
                finish();
                Intent i = new Intent(MainActivity.this, FinishActivity.class);
                startActivity(i);
            }

            letterz = wordy[currentIndex].getLetters();
            correctAns = wordy[currentIndex].getAnswer();
            maximumPressed = wordy[currentIndex].getCount();

            startShuffle();
        }
        else{
            Toast.makeText(MainActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }



        letterz = shuffleWord(letterz);
        linearLayout.removeAllViews();
        for(String key : letterz){
            addView(linearLayout, key, editText);
        }

    }
}
