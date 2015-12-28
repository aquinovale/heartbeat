package br.valeconsultoriati.heartbeat.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.valeconsultoriati.heartbeat.activities.HeartbeatActionActivity;

/**
 * Created by vinicius on 08/12/15.
 */
public class HeartbeatBroadcast  extends BroadcastReceiver {

    public HeartbeatBroadcast(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("HeartbeatBroadcast", "Service Broadcast");

        Intent i = new Intent(context, HeartbeatActionActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }
}
