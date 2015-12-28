package br.valeconsultoriati.heartbeat.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Message;
import android.util.Log;

import br.valeconsultoriati.heartbeat.R;

public class HeartbeatNotification extends AbstractNotification
{

    private final String TAG = this.getClass().getSimpleName();

    public HeartbeatNotification(Context ctx) {
        super(ctx);
    }

    public void shoot(Message message) {
        try {

            int defaults = this.shouldVibrate() ? Notification.DEFAULT_VIBRATE : Notification.DEFAULT_LIGHTS;
            final Resources res = this.ctx.getResources();
            Intent intent = new Intent("BROADCAST_SERVICES");
            intent.putExtras(message.getData());
            final Notification.Builder builder = new Notification.Builder(this.ctx)
                    .setDefaults(defaults)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle((String) message.getData().get(TITLE))
                    .setContentText((String) message.getData().get(TICKER))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setTicker((String) message.getData().get(TICKER))
                    .setContentIntent(
                            PendingIntent.getBroadcast(
                                    this.ctx,
                                    0,
                                    intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            )

            )
                    .addAction(
                            R.drawable.ic_action_stat_share,
                                    res.getString(R.string.action_share),
                                    PendingIntent.getActivity(
                                            this.ctx,
                                            0,
                                            Intent.createChooser(
                                                    new Intent(Intent.ACTION_SEND)
                                                            .setType("text/plain")
                                                            .putExtra(
                                                                    Intent.EXTRA_TEXT,
                                                                    res.getString(R.string.msg_shared)
                                                            ),
                                                    (String) message.getData().get(CONTENT)
                                            ),
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    )
                    )
                    .setAutoCancel(true);

            notify(this.ctx, builder.build());
        } catch (Exception e) {
            Log.e(TAG, "> Notification Error");
        }
    }
}