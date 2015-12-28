package br.valeconsultoriati.heartbeat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.valeconsultoriati.heartbeat.R;
import br.valeconsultoriati.heartbeat.configurations.ToolsToServices;
import br.valeconsultoriati.heartbeat.services.HeartbeatService;

public class HeartbeatActivity extends AppCompatActivity {

    private final int REQ_SIGNUP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartbeat);

        boolean running = ToolsToServices.isServiceRunning("br.valeconsultoriati.heartbeat.HeartBeatService", getApplicationContext());

        Log.i("HeartbeatActivity", "> Service is running: " + running);
        if (! running) {
            startService(new Intent(HeartbeatActivity.this, HeartbeatService.class));
        }

        Log.d("HeartBeatActivity", "> Create MainActivity");



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // The sign up activity returned that the user has successfully created
        // an account
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
