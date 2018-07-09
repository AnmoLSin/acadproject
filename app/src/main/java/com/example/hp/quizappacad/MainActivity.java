package com.example.hp.quizappacad;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    Button start, button0, button1, button2, button3, playAgain,buttonji;
    int n=0;
    TextView qtion, timer;
    TextView result;
    TextView pointsText;
    RelativeLayout layout;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int correctAnswerLoc,
            score = 0,
            numberOfQuestions = 0;

    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        timer.setText("30s");
        pointsText.setText("0/0");
        result.setText("");
        playAgain.setVisibility(View.INVISIBLE);
        buttonji.setVisibility(View.INVISIBLE);
        generate();

        new CountDownTimer(30000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgain.setVisibility(VISIBLE);
                buttonji.setVisibility(VISIBLE);
                timer.setText("0s");
                data(score,numberOfQuestions);
                result.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            }
        }.start();
    }

    public void data(int score,int numberOfQuestions)
    {
        if(n<=0) {
            try {
                SQLiteDatabase db;
                db = this.openOrCreateDatabase("notesDBB", MODE_PRIVATE, null);
                db.execSQL("create table if not exists list1 (corr VARCHAR,incorr VARCHAR,scor VARCHAR,per VARCHAR,tot VARCHAR)");
                //String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                int p=(score*100)/numberOfQuestions;
                int s=numberOfQuestions-score;
                db.execSQL("insert into list1 values('" + score + "','" + s + "','" + score + "','" + p + "','" + numberOfQuestions + "')");
                Log.i("notes", "Success");
                Toast.makeText(getApplicationContext(), "Data Updated successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        n++;
    }


    public void generate(){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        qtion.setText(Integer.toString(a) + " + " + Integer.toString(b)+" ? ");

        correctAnswerLoc = rand.nextInt(4);
        answers.clear();

        int wrongAnswer;

        for(int i=0; i<4; i++){
            if(i == correctAnswerLoc){
                answers.add(a + b);
            }else{
                wrongAnswer = rand.nextInt(41);
                while(wrongAnswer == a + b){
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view){

        if(view.getTag().toString().equals(Integer.toString(correctAnswerLoc))){
            score++;
            result.setText("Correct!!");
        }else{
            result.setText("Incorrect!!");
        }

        numberOfQuestions++;
        pointsText.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generate();

    }

    public void onClick(View v) {
        Log.d("ButtonState","Button Clicked");
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        startActivity(intent);
    }

    public void start(View view){
        start.setVisibility(View.INVISIBLE);
        layout.setVisibility(VISIBLE);
        playAgain(playAgain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button)findViewById(R.id.start);
        qtion = (TextView)findViewById(R.id.qtion);
        buttonji=(Button)findViewById(R.id.buttonji);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        result = (TextView)findViewById(R.id.result);
        pointsText = (TextView)findViewById(R.id.points);
        timer = (TextView)findViewById(R.id.timer);
        playAgain = (Button)findViewById(R.id.playAgain);
        layout = (RelativeLayout)findViewById(R.id.rl);
        playAgain(playAgain);
    }
}
