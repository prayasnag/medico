package com.example.medico.medico;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

@SuppressWarnings("deprecation")
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String Title = intent.getStringExtra(context.getString(R.string.appo_title));
        String location = intent.getStringExtra(context.getString(R.string.appo_location));
        String docName = intent.getStringExtra(context.getString(R.string.appo_doc));
        String content = "You have and appointment with " + docName + " in " + location;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_action_alarms)
                        .setContentTitle(Title)
                        .setContentText(content)
                        .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + context.getPackageName() + "/raw/notify"));

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
