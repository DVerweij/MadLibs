package com.example.danyllo.madlibs;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;


public class LibsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libs);
        Story storyObj = new Story(selectStory());
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
}
