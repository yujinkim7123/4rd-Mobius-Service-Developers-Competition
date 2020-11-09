package com.lifefriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TodayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        Button todayButton = (Button)findViewById(R.id.todayButton);
        todayButton.setBackgroundColor(Color.GRAY);
        todayButton.setTextColor(Color.WHITE);
    }

    public void graphBtnOnClickHandler(View view){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void mainBtnOnClickHandler(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}