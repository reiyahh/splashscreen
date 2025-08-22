package com.example.splashscreen;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification_reciever extends BroadcastReceiver {

    private static final String CHANNEL_ID = "donation_channel";
    private static final String CHANNEL_NAME = "Blood Donation";
    private static final String CHANNEL_DESC = "Reminders for blood donation appointments";

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");
        String message = "Hey " + name + ", it's time to donate blood!";

        // ✅ Save to SharedPreferences
        saveNotification(context, message);

        // ✅ Create channel if needed
        createNotificationChannel(context);

        // ✅ Intent to open the notification screen
        Intent openIntent = new Intent(context, notification_activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // ✅ Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notify) // Make sure this drawable exists in res/drawable
                .setContentTitle("Blood Donation Reminder")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // ✅ Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1001, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESC);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void saveNotification(Context context, String message) {
        SharedPreferences prefs = context.getSharedPreferences("notifications", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String existing = prefs.getString("all_notifications", "");
        existing += message + "\n"; // append
        editor.putString("all_notifications", existing);
        editor.apply();
    }
}
