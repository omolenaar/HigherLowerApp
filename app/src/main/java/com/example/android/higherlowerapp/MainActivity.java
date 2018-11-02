package com.example.android.higherlowerapp;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int [] mImageNames;
    private ImageView mImageView;
    private int prevRoll = 0;
    private boolean higher = true;
    private int score;
    private int highScore;
    private List mThrowList;
    private ListView mListView;
    private static final String TAG = "MyActivity";
    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize vars
        score=0;
        mListView = findViewById(R.id.listView);
        mThrowList = new ArrayList<String>();
        mImageView = findViewById(R.id.imageView);
        mImageNames = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

        //OnClick methods for higher/lower buttons
        FloatingActionButton fabHigh = findViewById(R.id.fabHigher);
        fabHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                higher=true;
                rollDice();
            }
        });

        FloatingActionButton fabLow = findViewById(R.id.fabLower);
        fabLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                higher=false;
                rollDice();
            }
        });
    }
    private void rollDice() {
        {
            //update score and high score
            TextView tvs = findViewById(R.id.textViewScore);
            String scoreText = (getString(R.string.textScore) + score);
            tvs.setText(scoreText);
            TextView tVHS = findViewById(R.id.textViewHighScore);
            String highScoreText = (getString(R.string.textHighScore) + highScore);
            tVHS    .setText(highScoreText);

            //roll the dice
            double randomNumber;
            randomNumber = Math.random() * 6;
            randomNumber = randomNumber + 1;
            int roll = (int) randomNumber;
            int i = roll - 1;
            Log.i(TAG, "roll = " + roll + ", i = " + i);
            if (i < mImageNames.length) {
                mImageView.setImageResource(mImageNames[i]);
            }

            //populate the list
            mThrowList.add("Throw is " +roll);
            updateUI();
            mListView.setSelection(mThrowList.size()-1);

            //check result; chosen to include equal in lower (as in = not higher)
            if (roll > prevRoll && higher || roll <= prevRoll && !higher)
                score = score + 1;
            else if (score > highScore) {
                highScore = score;
                highScoreText = (getString(R.string.textHighScore) + highScore);
                tVHS.setText(highScoreText);
                score = 0;
                mThrowList.clear();
                updateUI();
                Snackbar.make(mImageView, R.string.SnackBarNewHighScore, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(mImageView, R.string.SnackBarYouLose, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                score = 0;
                mThrowList.clear();
            }

            prevRoll=roll;
            Log.i(TAG,"score = "+score+", highScore = "+highScore);
        }
    }
    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mThrowList);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
