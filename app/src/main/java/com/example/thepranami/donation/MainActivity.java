package com.example.thepranami.donation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(1000);
                    Intent intent = new Intent(MainActivity.this, AdminRegistrationActivity.class);
                    startActivity(intent);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
