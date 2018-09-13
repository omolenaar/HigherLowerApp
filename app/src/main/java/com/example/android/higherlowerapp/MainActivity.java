package com.example.android.higherlowerapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int currentImageIndex = 0;
    private int [] mImageNames;
    private ImageView mImageView;
    private int prevRoll = 0;
    private boolean higher = true;
    private int score;
    private int highScore;
    private List mThrowList;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize vars
        score=0;
        mImageView = findViewById(R.id.imageView);
        mImageNames = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

        //OnClick methods for higher/lower buttons
        FloatingActionButton fabHigh = findViewById(R.id.fabHigher);
        fabHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the user text from the textfield
                higher=true;
                rollDice();
            }
        });

        FloatingActionButton fabLow = findViewById(R.id.fabLower);
        fabLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the user text from the textfield
                higher=false;
                rollDice();
            }
        });


    }
    private void rollDice() {
        {
            //update score and high score
            TextView tvs = (TextView) findViewById(R.id.textViewScore);
            tvs.setText("Score: "+score);
            TextView tvhs = (TextView) findViewById(R.id.textViewHighScore);
            tvhs.setText("High Score: "+highScore);

            //roll the dice
            double randomNumber;
            randomNumber = Math.random()*6;
            randomNumber = randomNumber + 1;
            int roll = (int) randomNumber;
            int i = roll-1;
            Log.i(TAG,"roll = "+roll+", i = "+i);
            if (i<mImageNames.length) {
                mImageView.setImageResource(mImageNames[i]);
            } else {
                Log.w(TAG,"Something wrong with i: "+i);
            }

            //check result
            if (roll > prevRoll && higher || roll<= prevRoll && !higher)
                score=score+1;
            else
            if (score > highScore) {
                highScore = score;
                tvhs.setText("High Score: "+highScore);
                score = 0;
                Toast.makeText(getApplicationContext(),"New high score! Start over", Toast.LENGTH_SHORT).show();
            }
            prevRoll=roll;
            Log.i(TAG,"score = "+score+", highScore = "+highScore);
        }
    }
}
