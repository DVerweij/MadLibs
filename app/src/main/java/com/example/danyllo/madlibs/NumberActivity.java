package com.example.danyllo.madlibs;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        TextView welcome = (TextView) findViewById(R.id.textView2);
        welcome.setText("Welcome " + name + "! \n Please enter your lucky number!");
    }
    public void goOn(View view){
        EditText numberET = (EditText) findViewById(R.id.editText2);
        int number = Integer.parseInt(numberET.getText().toString());

        Intent goToCheck = new Intent(this, WinActivity.class);
        goToCheck.putExtra("LuckyNum", number);
        startActivity(goToCheck);
    }
}
