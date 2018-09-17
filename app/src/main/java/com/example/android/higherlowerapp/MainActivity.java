package com.example.android.higherlowerapp;

import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        mThrowList = new ArrayList<>();
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
            TextView tvs = (TextView) findViewById(R.id.textViewScore);
            tvs.setText("Score: " + score);
            TextView tvhs = (TextView) findViewById(R.id.textViewHighScore);
            tvhs.setText("High Score: " + highScore);

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
            mThrowList.add(" Throw is "+roll);
            updateUI();
            mListView.setSelection(mThrowList.size()-1);

            //check result; chosen to include equal in lower (as in = not higher)
            if (roll > prevRoll && higher || roll <= prevRoll && !higher)
                score = score + 1;
            else if (score > highScore) {
                highScore = score;
                tvhs.setText("High Score: " + highScore);
                score = 0;
                mThrowList.removeAll(mThrowList);
                updateUI();
                Toast.makeText(getApplicationContext(), "New high score! Start over", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "You lost. Start over", Toast.LENGTH_SHORT).show();
                score = 0;
                mThrowList.removeAll(mThrowList);
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
