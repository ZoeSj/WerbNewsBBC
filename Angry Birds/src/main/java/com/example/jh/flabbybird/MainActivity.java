package com.example.jh.flabbybird;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Mysurfaceview mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mGame = new Mysurfaceview(this);
        setContentView(mGame);
        try {
            InputStream open = getAssets().open("bg.png");

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"hah",Toast.LENGTH_LONG).show();
        }
    }
}
