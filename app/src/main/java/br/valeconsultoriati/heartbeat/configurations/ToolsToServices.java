package br.valeconsultoriati.heartbeat.configurations;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ToolsToServices
{


    private static boolean activate = false;
    private static boolean action = false;

    private static String LOG_TAG = ToolsToServices.class.getName();

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
        ToolsToServices.action = value;
    }

    public static boolean isAction(){
        return ToolsToServices.action;
    }



    public static void callAlarm(Context ctx){
        if(!activate){
            activate = true;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent("BROADCAST_SERVICES");

            PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi);
            Log.v("ToolsToServices", "> Alerta:" + calendar.getTime());
        }else{
            Log.v("ToolsToServices", "> Alerta ja ativado.");
        }


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