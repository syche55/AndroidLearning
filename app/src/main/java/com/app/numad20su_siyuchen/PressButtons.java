package com.app.numad20su_siyuchen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PressButtons extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_buttons);

        Button A = (Button) findViewById(R.id.buttonA);
        A.setOnClickListener(this); // calling onClick() method
        Button B = (Button) findViewById(R.id.buttonB);
        B.setOnClickListener(this);
        Button C = (Button) findViewById(R.id.buttonC);
        C.setOnClickListener(this);
        Button D = (Button) findViewById(R.id.buttonD);
        D.setOnClickListener(this);
        Button E = (Button) findViewById(R.id.buttonE);
        E.setOnClickListener(this);
        Button F = (Button) findViewById(R.id.buttonF);
        F.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // Do something in response to button click
        TextView statusText = findViewById(R.id.pressedTextView);

        switch (v.getId()) {

            case R.id.buttonA:
                statusText.setText("Pressed: A");
                break;

            case R.id.buttonB:
                statusText.setText("Pressed: B");
                break;

            case R.id.buttonC:
                statusText.setText("Pressed: C");
                break;

            case R.id.buttonD:
                statusText.setText("Pressed: D");
                break;

            case R.id.buttonE:
                statusText.setText("Pressed: E");
                break;

            case R.id.buttonF:
                statusText.setText("Pressed: F");
                break;
            default:
                break;
        }

    }









    }







