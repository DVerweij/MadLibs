package com.example.danyllo.madlibs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainMadLibs extends AppCompatActivity {

    //SharedPreferences prefs;
    //String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mad_libs);

        //prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        //forward();
    }
    public void gettingStarted(View view) {
        Intent getStarted = new Intent(this, LibsActivity.class);
        startActivity(getStarted);
        finish();
    }

    /*public void save(View view) {
        EditText nameET = (EditText) findViewById(R.id.editText);
        name = nameET.getText().toString();
        if(!(name.length == 0)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name", name);
            editor.commit();
            forward();
        } else {
            Toast toast = Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void forward(){
        name = prefs.getString("name", "");
        if(!(name.length == 0)) {
            Intent gotonumberactivity = new Intent(this, NumberActivity.class);
            startActivity(gotonumberactivity);
            finish();
        }
    }*/
}
