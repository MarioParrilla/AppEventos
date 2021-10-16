package com.marioparrillamaroto.myeventsapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.gms.maps.MapView;

public class popUpCrearEventoMeeting extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_crear_evento_meeting);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

    }
}
