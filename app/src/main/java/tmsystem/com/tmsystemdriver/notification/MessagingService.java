package tmsystem.com.tmsystemdriver.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.presentation.main.PrincipalActivity;


public class MessagingService extends FirebaseMessagingService
{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Map<String, String> data = remoteMessage.getData();
        String message = data.get("message");
        String title = data.get("title");
        Integer condition = Integer.parseInt(data.get("condition"));

        switch (condition){
            case 1:

                displayNotification(message,title);
                startActivity(new Intent(this, PrincipalActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case 2:

                displayNotification(message,title);
                startActivity(new Intent(this, PrincipalActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case 3:
                displayNotification(message,title);
                startActivity(new Intent(this, PrincipalActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void displayNotification(String message, String title) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}

