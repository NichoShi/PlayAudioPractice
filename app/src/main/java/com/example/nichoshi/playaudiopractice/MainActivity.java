package com.example.nichoshi.playaudiopractice;


import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView nameTextView;
    private SeekBar seekBar;
    private Button pauseBtn;
    private Button playBtn;
    private Button stopBtn;
    private MediaPlayer player;
    private TextView timeTextView;
    private String current = "00:00";
    private String total = "00:00";
    private SimpleDateFormat time;
    private Handler handler = new Handler();
    private Runnable update = new Runnable() {
        @Override
        public void run() {
            if(player.isPlaying()){
                seekBar.setProgress(player.getCurrentPosition());
                current = time.format(player.getCurrentPosition());
                setTime();
                handler.postDelayed(update,100);
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = new SimpleDateFormat("mm:ss");
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        pauseBtn = (Button) findViewById(R.id.pauseBtn);
        playBtn = (Button) findViewById(R.id.playBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);
        pauseBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        initialMediaPlayer();
        setSeekBar();
        setTime();
        Log.d("LifeCircle","onCreate");


    }

    public void setTime(){
        timeTextView.setText(current+" / "+total);
    }




    public void initialMediaPlayer(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(),"Test.mp3");
            nameTextView.setText(file.getName());
            player = new MediaPlayer();
            player.setDataSource(file.getPath());
            player.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setSeekBar(){
        seekBar.setMax(player.getDuration());
        total = time.format(player.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser && player!=null){
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCircle","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCircle","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCircle","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCircle","onStop");
    }

    @Override
    protected void onDestroy() {
        Log.d("LifeCircle","onDestroy");
        super.onDestroy();
        if(player != null){
            player.stop();
            player.release();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.playBtn:
                if(!player.isPlaying()){
                    player.start();
                    handler.post(update);

                }
                break;

            case R.id.pauseBtn:
                if(player.isPlaying()){
                    player.pause();
                }
                break;

            case R.id.stopBtn:
                if(player.isPlaying()){
                    player.reset();
                    initialMediaPlayer();
                }
                seekBar.setProgress(0);
                current = "00:00";
                setTime();
                break;

            default:
                break;
        }

    }
}
