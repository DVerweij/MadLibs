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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
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
        wordNote = (TextView) findViewById(R.id.wordNote);
        wordET = (EditText) findViewById(R.id.editText);
        playGame();
    }
    private java.io.InputStream selectStory(){
        AssetManager ast = getApplicationContext().getAssets();
        try {
            String[] filelist = ast.list("stories");
            //File file = new File("file:///android_asset/");
            //File[] filelist = file.listFiles();
            Log.d("LENGTH", String.valueOf(filelist.length));
            Log.d("LIST", filelist[0]);
            Random rand = new Random();
            int num = rand.nextInt(filelist.length);
            return ast.open(filelist[num]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void playGame() {
        int placeholders = storyObj.getPlaceholderRemainingCount();
        numbersLeft.setText(placeholders + " word(s) left.");
        wordET.setText("");
        String nextplaceholder = storyObj.getNextPlaceholder();
        wordET.setHint(nextplaceholder);
        wordNote.setText(generateExamples(nextplaceholder));
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
    private String generateExamples(String placeholder) {
        String readtext = "";
        placeholder = placeholder.replace(" ", "");
        AssetManager ast = getApplicationContext().getAssets();
        String filePath = "examples/" + placeholder.toLowerCase() + "example.txt";
        Log.d("FILEPATH", filePath);
        try {
            InputStream fileopen = ast.open(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileopen));
            String line;
            while ((line = br.readLine()) != null) {
                readtext = readtext + line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("READTEXT", readtext);
        return readtext;
    }
}
