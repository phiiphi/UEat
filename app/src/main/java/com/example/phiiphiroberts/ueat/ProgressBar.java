package com.example.phiiphiroberts.ueat;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class ProgressBar extends AppCompatActivity {

    RingProgressBar progressBar1;
    Handler myHandler;

    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        progressBar1 = (RingProgressBar) findViewById(R.id.progress1);
        ringProgress();
    }

    private void ringProgress() {
        progressBar1.setOnProgressListener(new RingProgressBar.OnProgressListener() {
            @Override
            public void progressToComplete() {
                Toast.makeText(ProgressBar.this, "Login Successful",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);

            }
        });

        myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0) {
                    if(progress < 100){
                        progress++;
                        progressBar1.setProgress(progress);
                    }
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(25);
                        myHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
