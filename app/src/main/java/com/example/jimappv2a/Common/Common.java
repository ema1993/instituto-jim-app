package com.example.jimappv2a.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.jimappv2a.Model.AlumnoModel;
import com.example.jimappv2a.Model.TokenModel;
import com.example.jimappv2a.R;
import com.google.firebase.database.FirebaseDatabase;

public class Common {
    public static final String ALUMNOS_REF = "alumnos";
    public static final String CURSOS_REF = "cursos";
    public static final String CLAVE_REF = "claves";
    public static final String AVISOS_REF = "avisos";
    public static final String TOKEN_REF = "Tokens";

    public static final String NOTI_TITLE = "title";
    public static final String NOTI_CONTENT = "content";

    public static final String IS_OPEN_ACTIVITY_NEW_AVISO = "IsOpenActivityNewAviso";

    public static AlumnoModel selectedAlumno;

    public static String currentNombre="";

    public static String currentCurso="";

    public static void showNotification(Context context, int id, String title, String content, Intent intent) {
        PendingIntent pendingIntent = null;
        if(intent != null)
            pendingIntent = PendingIntent.getActivity(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        String NOTIFICATION_CHANNEL_ID = "jim_app_con_cursos";
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "JIM App con Cursos",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("JIM App con Cursos");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_mail_black_24dp));
        if(pendingIntent != null)
            builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notificationManager.notify(id,notification);
    }

    public static void updateToken(Context context, String newToken) {
        if(Common.selectedAlumno.getDni() != null)
        {
            FirebaseDatabase.getInstance()
                    .getReference(Common.TOKEN_REF)
                    .child(Common.selectedAlumno.getDni())
                    .setValue(new TokenModel(newToken))
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    public static String createTopicAviso() {
        return new StringBuilder("/topics/new_aviso").toString();
    }


}
