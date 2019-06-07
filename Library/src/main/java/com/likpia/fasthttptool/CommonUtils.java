package com.likpia.fasthttptool;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.likpia.fasthttptool.ui.ApiLogListActivity;

import static android.content.ContentValues.TAG;

public class CommonUtils {


    public static void disableNotification(Context context) {
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.cancel(956096);
    }

    public static void enableNotification(Context context, int smallResId, int largeIconResId) {
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);

        if (!compat.areNotificationsEnabled()) {
//                Toast.makeText(context, "情先开启通知权限", Toast.LENGTH_LONG).show();
            return;
        }
        // 此处必须兼容android O设备，否则系统版本在O以上可能不展示通知栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                NotificationChannel channel = new NotificationChannel(context.getPackageName(), TAG, NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
        }
        Intent intent = new Intent(context, ApiLogListActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        Notification notification = new NotificationCompat.Builder(context, context.getPackageName()).setOngoing(true).setContentTitle("API运行信息").setContentIntent(PendingIntent.getActivity(context, 321, intent, PendingIntent.FLAG_UPDATE_CURRENT)).setSmallIcon(smallResId).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIconResId)).setAutoCancel(false).setContentText("API运行信息").build();


        compat.notify(956096, notification);


    }
}
