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
import java.util.Scanner;


public class LibsActivity extends AppCompatActivity {

    //activity objects
    TextView numbersLeft, wordNote;
    EditText wordET;
    SharedPreferences prefs;

    //global variables
    public static Story storyObj;
    public static String storytext, nextplaceholder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libs);
        //initialize sharedPreference object
        prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        boolean isStartUp = prefs.getBoolean("isFirstRun", true);
        // if first run, then initialize globals
        if (isStartUp) {
            storyObj = new Story(selectStory()); //selectStory() is java.io.inputStream
            numbersLeft = (TextView) findViewById(R.id.textView);
            wordNote = (TextView) findViewById(R.id.wordNote);
            wordET = (EditText) findViewById(R.id.editText);
        }
        playGame();
    }

    //function which randomly selects story to play with
    private java.io.InputStream selectStory(){
        //get assets
        AssetManager ast = getApplicationContext().getAssets();
        try {
            String[] filelist = ast.list("stories"); //get list of stories
            Log.d("LENGTH", String.valueOf(filelist.length));
            Log.d("LIST", filelist[0]);
            Random rand = new Random();
            int num = rand.nextInt(filelist.length);
            return ast.open("stories/" + filelist[num]); //open random story and return
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //underlying function that sets up the game for user to input words
    private void playGame() {
        //print remaining placeholders on screen
        int placeholders = storyObj.getPlaceholderRemainingCount();
        numbersLeft.setText(placeholders + " word(s) left.");
        wordET.setText(""); //refreshes text
        //nextplaceholder to lower case because of weird strings like "aDvErB"
        nextplaceholder = storyObj.getNextPlaceholder().toLowerCase();
        wordET.setHint(nextplaceholder); //set hint for user
        wordNote.setText(generateExamples(nextplaceholder)); //generates a hint for the user
    }

    //function of button click
    public void confirmWord(View view) {
        Log.d("WOW", "Button pushed");
        String word = wordET.getText().toString().trim(); //remove trailing whitespace
        if (!(word.length() == 0)) {
            parseWord(word); //if anything was put in, parse it
        } else { //else have user try it again
            Toast toast = Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT);
            toast.show();
        }
        //if all placeholders filled in, go to next screen
        if (storyObj.isFilledIn()) {
            //save the story to use in the next activity
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("storytext", storyObj.toString());
            edit.apply();
            //go to next screen
            finishStory();
        //otherwise play the game with remaining placeholders
        } else {
            playGame();
        }
    }

    //word parser function
    private void parseWord(String word) {
        //if number has to be input, make sure it's a number (integer)
        if (nextplaceholder.equals("number")) {
            Scanner numberScan = new Scanner(word);
            Log.d("NUMBERSCAN", String.valueOf(numberScan.hasNextInt()));
            //if not an integer, show toast to user and break out of function
            if(!numberScan.hasNextInt()) {
                Toast isntInt = Toast.makeText(this, "Not a number", Toast.LENGTH_SHORT);
                isntInt.show();
                return;
            }
        //otherwise: restrict input word
        } else {
            //No words that are too short. It can be capped at one or two letters.
            if (word.length() <= 1) {
                Toast tooShort = Toast.makeText(this, "A longer word, please", Toast.LENGTH_SHORT);
                tooShort.show();
                return;
            //only accept strings that do not contain anything but letters
            } else if (!word.matches("[a-zA-Z ]+")){
                Toast hasSymbols = Toast.makeText(this, "Only letters please", Toast.LENGTH_SHORT);
                hasSymbols.show();
                return;
            //if a name is to be put in, make sure the first letter is capitalized
            } else if (nextplaceholder.equals("person's name") || nextplaceholder.equals("male name")) {
                if (!Character.isUpperCase(word.charAt(0))) {
                    Toast upCase = Toast.makeText(this, "Please make the first letter upper case", Toast.LENGTH_SHORT);
                    upCase.show();
                    return;
                }
            }

        }
        //if word goes past all statements, fill it in the story
        storyObj.fillInPlaceholder(word);
    }

    //function that links this activity to the next activity
    public void finishStory(){
        storytext = prefs.getString("storytext", "");
        if(!(storytext.length() == 0)) {
            Intent storyactivity = new Intent(this, StoryActivity.class);
            startActivity(storyactivity);
            finish();
        }
    }

    //Function that reads a text file with an example based on the type of placeholder
    private String generateExamples(String placeholder) {
        String readtext = "";
        placeholder = placeholder.replace(" ", ""); //delete whitespace
        AssetManager ast = getApplicationContext().getAssets();
        //get specific example file for placeholder
        String filePath = "examples/" + placeholder.toLowerCase() + "example.txt";
        Log.d("FILEPATH", filePath);
        //read the lines of the file
        try {
            InputStream fileopen = ast.open(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileopen));
            String line;
            while ((line = br.readLine()) != null) {
                readtext = readtext + line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return readtext; //if not found, return empty string
        }
        Log.d("READTEXT", readtext);
        return readtext;
    }
}
