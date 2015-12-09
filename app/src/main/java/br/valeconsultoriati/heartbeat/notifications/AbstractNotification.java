package br.valeconsultoriati.heartbeat.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import android.os.Build;

abstract class AbstractNotification
{
    private static final String NOTIFICATION_TAG = "HeartBeatNotification";
    private static final String TAG = "AbstractNotification";

    public static final String TITLE = "title";
    public static final String TICKER = "ticker";
    public static final String CONTENT = "content";

    protected Context ctx;

    public AbstractNotification(Context ctx)
    {
        this.ctx = ctx;
    }

    protected boolean shouldVibrate()
    {
       return true;
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    protected static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}