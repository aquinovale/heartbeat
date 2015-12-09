package br.valeconsultoriati.heartbeat.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.valeconsultoriati.heartbeat.configurations.ToolsToServices;
import br.valeconsultoriati.heartbeat.services.HeartbeatService;

public class BootAndroid extends BroadcastReceiver {
    public BootAndroid() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootAndroid", "Service Broadcast");
        if (!ToolsToServices.isServiceRunning("br.valeconsultoriati.heartbeat.HeartBeatService", context)) {
            context.startService(new Intent(context, HeartbeatService.class));
        }
    }
}
