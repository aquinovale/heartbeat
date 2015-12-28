package br.valeconsultoriati.heartbeat.configurations;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ToolsToServices
{


    private static boolean activate = false;

    private static boolean action = false;

    private static int withouConnection = 0;

    private static String LOG_TAG = ToolsToServices.class.getName();
    private static Message message;

    public static boolean isServiceRunning(String serviceClassName, Context ctx)
    {
        final ActivityManager activityManager = (ActivityManager)ctx.getSystemService(ACTIVITY_SERVICE);
        final List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)){
                return true;
            }
        }
        return false;
     }


    public static void setActivate(boolean value){
        ToolsToServices.activate = value;
    }

    public static void setAction(boolean value){
        Log.d("HearbeatActionActivity", "> Action:" + value);
        ToolsToServices.action = value;
    }

    public static boolean isAction(){
        return ToolsToServices.action;
    }



    public static void callAlarm(Context ctx, Message message){
        if(!activate){
            activate = true;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent("BROADCAST_SERVICES");
            intent.putExtra("Services", message.getData());

            PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi);
            Log.v("ToolsToServices", "> Alerta:" + calendar.getTime());
        }else{
            Log.d("ToolsToServices", "> Alerta ja ativado.");
        }
   }

    public static int getWithouConnection() {
        ToolsToServices.CountWithouConnection();
        return withouConnection;
    }

    public static void CountWithouConnection() {
        ToolsToServices.withouConnection += 1;
        if(ToolsToServices.withouConnection > 3){
            ToolsToServices.withouConnection = 0;
        }
    }

    public static boolean checkInternetConnection(Context ctx){
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()){
            return true;
        }else{
            Log.i("ToolsToServices", "> Sem acesso a internet.");
            return false;
        }
    }

    public static String buildReturn(String message) {        ;
        return "["+buildJSON(message)+"]";
    }

    public static String buildJSON(String message){
        JSONObject connection = new JSONObject();
        try {
            Log.i("ApiCall", "> " + withouConnection);
            connection.put("re", true);
            connection.put(HeartbeatModel.COMPANY.toString(), "Vale Consultoria em TI");
            connection.put(HeartbeatModel.HOST.toString(), "viniciusvale.com/valeconsultoriati.com");
            connection.put(HeartbeatModel.CRITICO.toString(), (ToolsToServices.getWithouConnection() >= 3 ) ? true : false);
            connection.put(HeartbeatModel.DISPARAR.toString(), true);
            connection.put(HeartbeatModel.ATUALIZADO.toString(), Calendar.getInstance().getTime().toString());
            connection.put(HeartbeatModel.MESSAGE.toString(), message);
            connection.put(HeartbeatModel.SERVICE.toString(), "Internet");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("ApiCall", "> " + connection.toString());
        return connection.toString();
    }

    public static void setMessage(Message message) {
        ToolsToServices.message = message;
    }


    public static Message getMessage() {
        return ToolsToServices.message;
    }

    private void cancelAlarm(Context ctx){
        Intent intent = new Intent("BROADCAST_SERVICES");
        PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }

    /**
      * Create here a method to stop some service
      */
     public void stopService()
     {
        // TODO
     }

  }