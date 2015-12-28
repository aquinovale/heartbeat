package br.valeconsultoriati.heartbeat.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.valeconsultoriati.heartbeat.R;
import br.valeconsultoriati.heartbeat.configurations.HeartbeatModel;
import br.valeconsultoriati.heartbeat.configurations.ToolsToServices;
import br.valeconsultoriati.heartbeat.notifications.HeartbeatNotification;

public class HeartbeatActionActivity extends AppCompatActivity {

    private MediaPlayer mSound;
    private boolean ativarSom = true;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSound.release();
        ToolsToServices.setActivate(false);
    }

    private View.OnClickListener clickButton = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            mSound.release();
            ToolsToServices.setAction(!ToolsToServices.isAction());
            Toast.makeText(getApplicationContext(), "Botão Action:" +ToolsToServices.isAction(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HearbeatActionActivity", "> Create Activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartbeat_action);

        final Button btAction = (Button) findViewById(R.id.btAction);

        mSound = MediaPlayer.create(this, R.raw.heartstop);

        savedInstanceState = ToolsToServices.getMessage().getData();
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(HeartbeatNotification.CONTENT)) {
                try {
                    JSONObject json = new JSONObject(savedInstanceState.getString(HeartbeatNotification.CONTENT));

                    final TextView tCompany = (TextView) findViewById(R.id.tOk);
                    final TextView tHost = (TextView) findViewById(R.id.tHost);
                    final TextView tService = (TextView) findViewById(R.id.tService);
                    final TextView tCriticidade = (TextView) findViewById(R.id.tCriticidade);
                    final TextView tMessage = (TextView) findViewById(R.id.tMessage);
                    final TextView tDtAtualizado = (TextView) findViewById(R.id.tDtAtualizado);

                    tCompany.setText(json.getString(HeartbeatModel.COMPANY.toString()));
                    tHost.setText(json.getString(HeartbeatModel.HOST.toString()));
                    tService.setText(json.getString(HeartbeatModel.SERVICE.toString()));
                    tMessage.setText(json.getString(HeartbeatModel.MESSAGE.toString()));
                    tCriticidade.setText(json.getString(HeartbeatModel.CRITICO.toString()));
                    tDtAtualizado.setText(json.getString(HeartbeatModel.ATUALIZADO.toString()));

                    ativarSom = json.getBoolean(HeartbeatModel.CRITICO.toString());

                    Log.d("HeartbeatActionActivity", "> AtivarSom: " + ativarSom);
                } catch (JSONException e) {
                    Log.w("HeartbeatActionActivity", "> Error in parser JSONObject:" + e.getMessage());

                }
            }
        }

        btAction.setOnClickListener(clickButton);


    }


    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        Log.d("HearbeatActionActivity", "> Action:" + ToolsToServices.isAction());
                        if(ToolsToServices.isAction()) {
                            break;
                        }else{
                            if(ativarSom){
                                mSound.start();
                            }
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
