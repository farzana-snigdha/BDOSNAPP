package com.example.bdosn_app_rescue;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        Map<String, String> extraData = remoteMessage.getData();

        String location = extraData.get("location");
        String image = extraData.get("image");
        String name = extraData.get("name");
        String contact = extraData.get("contact");
        String lastSeen = extraData.get("last_seen");
        String gender = extraData.get("gender");
        String age = extraData.get("age");
        String relation = extraData.get("relation");
        String height = extraData.get("height");
        String desc = extraData.get("desc");

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "RESCUE")
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.ic_launcher_background);

        Intent intent;
        if (image.equals(extraData.get("image"))) {
            intent = new Intent(this, ReceiveNotificationActivity.class);

        } else {
            intent = new Intent(this, ReceiveNotificationActivity.class);
        }

        intent.putExtra("location",location);
        intent.putExtra("image",image);
        intent.putExtra("name",name);
        intent.putExtra("contact",contact);
        intent.putExtra("last_seen",lastSeen);
        intent.putExtra("gender",gender);
        intent.putExtra("relation",relation);
        intent.putExtra("height",height);
        intent.putExtra("age",age);
        intent.putExtra("desc",desc);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        int id =  (int) System.currentTimeMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("RESCUE","demo",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(id,notificationBuilder.build());

    }
}
