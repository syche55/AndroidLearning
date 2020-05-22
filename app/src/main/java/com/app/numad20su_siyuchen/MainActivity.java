package com.app.numad20su_siyuchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAuthorInfo(View view) {
        TextView textView = findViewById(R.id.textView);
        textView.setText(R.string.author_info);
    }

    public void PressButtons(View view) {
        Intent intent = new Intent(this, PressButtons.class);
        startActivity(intent);
    }

}