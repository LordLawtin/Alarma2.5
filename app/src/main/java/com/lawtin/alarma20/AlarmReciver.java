package com.lawtin.alarma20;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import static android.content.Context.ACTIVITY_SERVICE;

public class AlarmReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isRunnig=false;
        String string=intent.getExtras().getString("");
        ActivityManager manager=(ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service:manager.getRunningServices(Integer.MAX_VALUE)){

            if (Music.class.getName().equals(service.service.getClassName())){
                isRunnig=true;
            }
        }
        Intent mIntent=new Intent(context, Music.class);
        if (string.equals("on") && !isRunnig){
            context.startService(mIntent);
            AlarmaF.activeAlarm=intent.getExtras().getString("active");
        }else if (string.equals("off")){
            context.startService(mIntent);
            AlarmaF.activeAlarm= "";
        }
    }
}
