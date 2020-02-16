package com.softwarica.lostandfound.notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.softwarica.lostandfound.R;

public class BroadcastReceiverExample extends BroadcastReceiver {
    private NotificationManagerCompat notificationManagerCompat;
    Context context;

    public BroadcastReceiverExample(Context context) {
        this.context = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean noConnectivity;
        notificationManagerCompat = NotificationManagerCompat.from(context);

        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
            );
            if (noConnectivity){
                Toast.makeText(context, "No wifi connected.Please connect to the interent", Toast.LENGTH_SHORT).show();
                DisconnectNotification();
            }else{
                Toast.makeText(context, "Wi-Fi Connected.", Toast.LENGTH_SHORT).show();
                ConnectedNotification();
            }
        }

    }
    private void DisconnectNotification(){
        Notification notification = new NotificationCompat.Builder(context, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.wifi_disconnected_notification)
                .setContentTitle("Meal Recipes Product")
                .setContentText("No internet Connection...")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1 , notification);
    }
    private void ConnectedNotification(){
        Notification notification = new NotificationCompat.Builder(context, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.wifi_connected_notification)
                .setContentTitle("Meal Recipes Product")
                .setContentText("Internet Connected...")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(2 , notification);
    }
}

