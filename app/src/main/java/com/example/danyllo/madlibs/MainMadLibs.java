package com.example.danyllo.madlibs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

public class MainMadLibs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mad_libs);
    }

    //function that on a click goes to the next activity
    public void gettingStarted(View view) {
        Intent getStarted = new Intent(this, LibsActivity.class);
        startActivity(getStarted);
        finish();
    }
}
