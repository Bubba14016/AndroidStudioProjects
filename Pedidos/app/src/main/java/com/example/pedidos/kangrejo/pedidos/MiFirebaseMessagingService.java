package com.example.pedidos.kangrejo.pedidos;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Bubba on 2/5/2018.
 */

public class MiFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
            try {
                if (remoteMessage.getNotification() != null) {
                    mostrarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                }
            }catch (Exception e){
                mostrarNotificacion("error", "no funciona");
            }
    }

    private void mostrarNotificacion(String titulo, String cuerpo) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0, intent,  PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(cuerpo)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(123445345, notificationBuilder.build() );

    }
}
