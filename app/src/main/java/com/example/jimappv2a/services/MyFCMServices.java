package com.example.jimappv2a.services;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.jimappv2a.Common.Common;
import com.example.jimappv2a.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFCMServices extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Map<String,String> dataRecv = remoteMessage.getData();
        if(dataRecv != null)
        {
            if(dataRecv.get(Common.NOTI_TITLE).equals("Nuevo Aviso"))
            {
                //Here we need call MainActivity because we must assign value for Common.currentUser
                //So we must call MainActivity to do that, if you direct call homeactivity you will be crash
                //because Common.currentUser only be assign in MainActivity AFTER LOGIN
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra(Common.IS_OPEN_ACTIVITY_NEW_AVISO,true);//Use extra to detect if app is open from notification
                Common.showNotification(this,new Random().nextInt(),
                        dataRecv.get(Common.NOTI_TITLE),
                        dataRecv.get(Common.NOTI_CONTENT),
                        intent);
            }
            else {

                Common.showNotification(this, new Random().nextInt(),
                        dataRecv.get(Common.NOTI_TITLE),
                        dataRecv.get(Common.NOTI_CONTENT),
                        null);
            }
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Common.updateToken(this,s);
    }
}
