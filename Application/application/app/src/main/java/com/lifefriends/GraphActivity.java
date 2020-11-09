package com.lifefriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GraphActivity extends AppCompatActivity {

    ImageView graphActImage;
    TextView graphActText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Button graphButton = (Button)findViewById(R.id.graphButton);
        graphButton.setBackgroundColor(Color.GRAY);
        graphButton.setTextColor(Color.WHITE);

        graphActImage = (ImageView) findViewById(R.id.graphActImageView);
        graphActText= (TextView) findViewById(R.id.graphActTextView);
    }

    public void todayBtnOnClickHandler(View view){
        Intent intent = new Intent(this, TodayActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void mainBtnOnClickHandler(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void washBtnOnClickHandler(View view){
        graphActImage.setImageResource(R.drawable.sticker_wash);
        graphActText.setText("손 씻기 습관");
    }
    public void eatBtnOnClickHandler(View view){
        graphActImage.setImageResource(R.drawable.sticker_eat);
        graphActText.setText("밥 먹기");
    }
    public void maskBtnOnClickHandler(View view){
        graphActImage.setImageResource(R.drawable.sticker_mask);
        graphActText.setText("마스크 쓰기 습관");
    }
    public void brushBtnOnClickHandler(View view){
        graphActImage.setImageResource(R.drawable.sticker_tooth);
        graphActText.setText("이빨 닦기 습관");
    }
    public void bookBtnOnClickHandler(View view){
        graphActImage.setImageResource(R.drawable.sticker_book);
        graphActText.setText("책 읽기");
    }
    public void playBtnOnClickHandler(View view){
        graphActImage.setImageResource(R.drawable.sticker_play);
        graphActText.setText("놀기 :)");
    }
}