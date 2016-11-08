package com.example.danyllo.madlibs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;


public class LibsActivity extends AppCompatActivity {

    public static TextView numbersLeft, wordNote;
    public static EditText wordET;
    public static Story storyObj;

    SharedPreferences prefs;
    public static String storytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libs);
        storyObj = new Story(selectStory());
        prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        numbersLeft = (TextView) findViewById(R.id.textView);
        wordET = (EditText) findViewById(R.id.editText);
        playGame();
    }
    private java.io.InputStream selectStory(){
        AssetManager ast = getApplicationContext().getAssets();
        try {
            return ast.open("madlib1_tarzan.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void playGame() {
        int placeholders = storyObj.getPlaceholderRemainingCount();
        numbersLeft.setText(placeholders + " word(s) left.");
        wordET.setText("");
        wordET.setHint(storyObj.getNextPlaceholder());
    }
    public void confirmWord(View view) {
        Log.d("WOW", "Button pushed");
        //EditText wordET = (EditText) findViewById(R.id.editText);
        String word = wordET.getText().toString();
        if (!(word.length() == 0)) {
            storyObj.fillInPlaceholder(word);
        } else {
            Toast toast = Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (storyObj.isFilledIn()) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("storytext", storyObj.toString());
            edit.apply();
            finishStory();
        } else {
            playGame();
        }
    }
    public void finishStory(){
        storytext = prefs.getString("storytext", "");
        if(!(storytext.length() == 0)) {
            Intent storyactivity = new Intent(this, StoryActivity.class);
            startActivity(storyactivity);
            finish();
        }
    }
}
