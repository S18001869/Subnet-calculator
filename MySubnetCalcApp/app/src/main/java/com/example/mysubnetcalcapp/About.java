package com.example.mysubnetcalcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity
{
    Button aboutBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupbackbutton();
    }

private void setupbackbutton()
{
    aboutBackButton = findViewById(R.id.aboutBackButton);
    aboutBackButton.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
}

}
