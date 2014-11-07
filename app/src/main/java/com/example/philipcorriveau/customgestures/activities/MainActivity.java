package com.example.philipcorriveau.customgestures.activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.philipcorriveau.customgestures.views.DrawingView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingView(this));
    }

}
