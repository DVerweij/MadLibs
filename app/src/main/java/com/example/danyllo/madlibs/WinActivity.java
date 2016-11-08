package com.example.danyllo.madlibs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle extras = getIntent().getExtras();
        int savedNumber = extras.getInt("LuckyNum", 9008);

        TextView message = (TextView) findViewById(R.id.textView3);
        message.setText("The winning number is " + savedNumber + "!");
    }
    public void numberAgain(View view) {
        Intent goBack = new Intent(this, NumberActivity.class);
        startActivity(goBack);
    }
}
