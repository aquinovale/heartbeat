package br.valeconsultoriati.heartbeat.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.valeconsultoriati.heartbeat.R;
import br.valeconsultoriati.heartbeat.configurations.ToolsToServices;

public class HeartbeatActionActivity extends AppCompatActivity {

    private MediaPlayer mSound;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSound.release();
        ToolsToServices.setActivate(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartbeat_action);

        mSound = MediaPlayer.create(this, R.raw.heartstop);

        Log.d("HearbeatActionActivity", "> Create Activity");
    }


    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i = 0 ; i < 5 ; i++){
                        Log.d("HearbeatActionActivity", "> Action:" + ToolsToServices.isAction());
                        if(ToolsToServices.isAction()) {
                            break;
                        }else{
                            mSound.start();
                            SystemClock.sleep(8000);
                        }                        
                    }
                }catch(Exception e){
                    Log.d("HearbeatActionActivity", "> Thread Exception");
                }

            }
        }).start();
    }

}
