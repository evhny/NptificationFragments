package com.example.jkgi.natificotionfragments.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import com.example.jkgi.natificotionfragments.MainActivity;
import com.example.jkgi.natificotionfragments.R;


public class NotificationUtil {

    private static final String NOTIFICATION_CHANNEL_ID = "notificationChannelId";

    private static Builder defaultNotificationBuilder(Context context, String title,
                                                     String message) {
        return new Builder(context)
                .setContentTitle(title != null ? title : "")
                .setContentText(message != null ? message : "")
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
    }



    @TargetApi(Build.VERSION_CODES.O)
    private static NotificationChannel createNotificationChannel(Context context) {
        NotificationChannel notificationChannel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.notification),
                NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        return notificationChannel;
    }

    public static void createNotification(Context context, NotificationManager notificationManager, int notificationId) {
        Notification.Builder builder =
                defaultNotificationBuilder(context,
                        context.getString(R.string.you_create_notification),
                        context.getString(R.string.notification) + notificationId)
                        .setContentIntent(createPendingIntent(context, notificationId));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(createNotificationChannel(context));
        }

        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification);
    }

    private static PendingIntent createPendingIntent(Context context, int page) {
        return TaskStackBuilder.create(context)
                .addNextIntent(MainActivity.newInstance(context, page))
                .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);

    }
}
