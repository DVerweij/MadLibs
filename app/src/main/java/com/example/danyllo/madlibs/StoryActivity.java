package com.example.danyllo.madlibs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    public static TextView story;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        //get story from last activity and display it on-screen
        prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        String storytext = prefs.getString("storytext", "");
        story = (TextView) findViewById(R.id.textView2);
        story.setText(Html.fromHtml(storytext)); //parses <b></b> also
    }

    //the restart button
    public void restart(View view) {
        Intent newActivity = new Intent(this, MainMadLibs.class);
        startActivity(newActivity);
        finish();
    }
}
