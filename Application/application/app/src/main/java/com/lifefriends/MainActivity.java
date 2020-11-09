package com.lifefriends;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.lifefriends.onem2m.IReceived;
import com.lifefriends.onem2m.JsonParser;
import com.lifefriends.request.CinGetRequest;
import com.lifefriends.request.CinPostRequest;
import java.util.ArrayList;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {
    public Handler handler;
    public MainActivity() { handler = new Handler();}
    private  static String TAG = "MainActivity";

    // container name
    String[] timeCntArr = {"school_time", "eat_time", "sleep_time"};
    String[] timeState = new String[3];

    private JsonParser jsonParser = new JsonParser();


    // layout: alarm set toggle button
    ArrayList<ToggleButton> ToggleButtonList = new ArrayList<ToggleButton>();

    // layout: show alarm time (edit text)
    ArrayList<EditText> EditTextList = new ArrayList<EditText>();

    Button stopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button homeButton = (Button) findViewById(R.id.homeButton) ;
        Button todayButton = (Button) findViewById(R.id.todayButton);
        Button graphButton = (Button) findViewById(R.id.graphButton);

        // disable button
        homeButton.setClickable(false);
        homeButton.setBackgroundColor(Color.GRAY);
        homeButton.setTextColor(Color.WHITE);

        // add toggle buttons to array
        ToggleButtonList.add((ToggleButton) findViewById(R.id.schoolAlarmToggle));
        ToggleButtonList.add((ToggleButton) findViewById(R.id.eatAlarmToggle));
        ToggleButtonList.add((ToggleButton) findViewById(R.id.sleepAlarmToggle));

        // add edit text to array
        EditTextList.add((EditText) findViewById((R.id.schoolAlarmEditText)));
        EditTextList.add((EditText) findViewById((R.id.eatAlarmEditText)));
        EditTextList.add((EditText) findViewById((R.id.sleepAlarmEditText)));

        stopButton = (Button) findViewById(R.id.stopBandButton);

        initAlarmTime();
        stopBandBtnOnClickHandler();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void todayBtnOnClickHandler(View view){
        Intent intent = new Intent(this, TodayActivity.class);
//        intent.putExtra(timeState[0], "time_state_1");
//        intent.putExtra(timeState[1], "time_state_2");
//        intent.putExtra(timeState[2], "time_state_3");

        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void graphBtnOnClickHandler(View view){
        Intent intent = new Intent(this, GraphActivity.class);
//        intent.putExtra(timeState[0], "time_state_1");
//        intent.putExtra(timeState[1], "time_state_2");
//        intent.putExtra(timeState[2], "time_state_3");
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void stopBandBtnOnClickHandler(){
        stopButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                CinPostRequest req = new CinPostRequest(MainActivity.this, "trigger", "0");
                req.setReceiver(new IReceived() {
                    public void getResponseBody(String msg) {
                        Log.d(TAG, "post request" + msg);
                    }
                });
                req.start();
            }
        });


        CinPostRequest req = new CinPostRequest(MainActivity.this, "trigger", "0");
        req.setReceiver(new IReceived() {
            public void getResponseBody(String msg) {
                Log.d(TAG, "post request" + msg);
            }
        });
        req.start();
    }


    private void initAlarmTime(){
        for(int i =0; i<EditTextList.size(); i++){
            CinGetRequest req = new CinGetRequest(this, timeCntArr[i]);
            final int finalI = i;
            req.setReceiver(new IReceived() {
                public void getResponseBody(final String msg) {
                    handler.post(new Runnable() {
                        public void run() {
                            String con = jsonParser.getContainerContent(msg);
                            Log.d(TAG, con);

                            if(con.toUpperCase().contains(getString(R.string.alarmOff).toUpperCase())) {
                                ToggleButtonList.get(finalI).setChecked(false);
                                EditTextList.get(finalI).setText(getString(R.string.alarmOff));  // change text
                            }
                            else {
                                ToggleButtonList.get(finalI).setChecked(true);
                                EditTextList.get(finalI).setText(con);  // change text
                            }
                            if(finalI == EditTextList.size()-1){
                                // register event handler
                                toggleBtnChangedEventHandler();
                                setTimeTextChangedEventHandler();
                            }
                        }
                    });
                }
            });
            req.start();
        }
    }

    private void toggleBtnChangedEventHandler(){
        for(int i =0; i<ToggleButtonList.size(); i++){
            final int finalI = i;
            ToggleButtonList.get(finalI).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        TimePickerDialog.OnTimeSetListener TimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time = "";
                                if(hourOfDay < 10){ time = "0" + hourOfDay + ":";}
                                else{               time = hourOfDay + ":";}
                                if(minute < 10){    time += "0" + minute;}
                                else{               time += minute;}
                                EditTextList.get(finalI).setText(time);
                                timeState[finalI] = time;
                            }
                        };
                        TimePickerDialog TimeDialog = new TimePickerDialog(MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                                TimeSetListener, 0, 0, false);
                        TimeDialog.setTitle("Select Alarm Time");
                        TimeDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        TimeDialog.show();
                    }else{
                        EditTextList.get(finalI).setText(getString(R.string.alarmOff));
                        timeState[finalI] = getString(R.string.alarmOff);

                    }
                }
            });
        }
    }

    // if button text changed -> user changed alarm setting    -> post mobius
    public void setTimeTextChangedEventHandler(){
        for(int i =0; i<EditTextList.size(); i++){
            final int finalI = i;
            EditTextList.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                    Log.d(TAG, "onTextChanged: "+charSequence);
                    CinPostRequest req = new CinPostRequest(MainActivity.this, timeCntArr[finalI], charSequence.toString());
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(String msg) {
                            Log.d(TAG, "post request" + msg);
                        }
                    });
                    req.start();
                }
                @Override
                public void afterTextChanged(Editable editable) {}
            });
        }
    }
}