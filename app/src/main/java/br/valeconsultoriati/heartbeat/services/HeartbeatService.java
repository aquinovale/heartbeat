package br.valeconsultoriati.heartbeat.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import br.valeconsultoriati.heartbeat.R;
import br.valeconsultoriati.heartbeat.api.HeartbeatApi;
import br.valeconsultoriati.heartbeat.configurations.ToolsToServices;
import br.valeconsultoriati.heartbeat.notifications.HeartbeatNotification;

public class HeartbeatService extends Service {


    public HeartbeatService() {
    }
    private final String TAG = this.getClass().getSimpleName();

    private static final int INTERVAL_TIMER = 60000; //60000;
    private static final int START_TIMER = 1000;

    //private boolean stop;
    private Context ctx;
    private HeartbeatNotification heartbeatNotification;
    private final IBinder mBinder = new LocalBinder();
    private HeartbeatApi heartbeatApi;


    private Timer timer;
    private TimerTask task = new TimerTask()
    {
        public void run()
        {
            if(heartbeatApi.finishSuccess()){
//            if(true){
                Log.v(TAG, "> Problem in Server");
                if(false){ // Status OK

                }else{ // Status False
                    if(true){ // Critico
                        //makeNoise();
                    }else{ // Não Critico
                        sendMessage(heartbeatApi.getResult());
                    }
                }
            }else{
                if(!heartbeatApi.isRun()){
                    stopSelf();
                }
            }
        }
    };

    private void makeNoise(){
        ToolsToServices.callAlarm(getApplicationContext());
    }


    private void sendMessage(Object mensagens) {
        if(mensagens != null){
            Message message = Message.obtain();
            Bundle bundle = new Bundle();

            bundle.putString(HeartbeatNotification.TITLE, ctx.getResources().getString(R.string.app_name));
            bundle.putString(HeartbeatNotification.TICKER, ctx.getResources()
                    .getString(R.string.ticker));
            bundle.putString(HeartbeatNotification.CONTENT, mensagens.toString());

            message.setData(bundle);

            handler.sendMessage(message);
        }
    }

    public class LocalBinder extends Binder
    {
        public HeartbeatService getService()
        {
            return HeartbeatService.this;
        }
    }

    @Override
    public void onCreate()
    {
        try {
            timer = new Timer();
            ctx = getApplicationContext();
            heartbeatNotification = new HeartbeatNotification(ctx);
            Log.v(TAG, "> onCreate");
        } catch (Exception e) {
            stopSelf();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            heartbeatNotification.shoot(msg);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        try {
            heartbeatApi = new HeartbeatApi();
            timer.schedule(task, START_TIMER, INTERVAL_TIMER);
        } catch (Exception e) {
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "> onDestroy");
        timer.cancel();

        Log.v(TAG, "> Create new Service");
        startService(new Intent(this, HeartbeatService.class));
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

}
