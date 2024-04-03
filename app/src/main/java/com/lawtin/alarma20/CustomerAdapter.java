package com.lawtin.alarma20;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import static android.content.Context.ALARM_SERVICE;

import java.util.Calendar;
import java.util.List;



public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private List<Alarm> alarmList;
    private LayoutInflater layoutInflater;
    public CustomerAdapter(Context c, List<Alarm> alarmList){
        this.context=c;
        this.alarmList=alarmList;
        layoutInflater=(LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return alarmList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.row_items, null);
        final Alarm selecetAlarm=alarmList.get(position);
        final TextView nameTV= convertView.findViewById(R.id.nameTextView);
        final TextView alarmaTV= convertView.findViewById(R.id.timeTextView);
        final AlarmManager alarmManager=(AlarmManager) context.getSystemService(ALARM_SERVICE);

        nameTV.setText(selecetAlarm.getNameAlarm());
        alarmaTV.setText(selecetAlarm.toString());

        final Intent serviceIntent=new Intent(context, AlarmReciver.class);

        final Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, selecetAlarm.getHour());
        calendar.set(Calendar.MINUTE, selecetAlarm.getHour());
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis()< System.currentTimeMillis()){
            calendar.add(Calendar.DATE, 1);
        }
        ToggleButton toggleButton=convertView.findViewById(R.id.toggle);
        toggleButton.setChecked(selecetAlarm.getStatus());
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selecetAlarm.setStatus(isChecked);
                DatabaseHelper db= new DatabaseHelper(context);
                db.updateAlarm(selecetAlarm);

                AlarmaF.alarmList.clear();
                List<Alarm>list=db.getAllAlarms();
                AlarmaF.alarmList.addAll(list);
                notifyDataSetChanged();

                if (!isChecked && selecetAlarm.toString().equals(AlarmaF.activeAlarm)){
                    serviceIntent.putExtra("extra", "off");
                    PendingIntent pendingIntent= PendingIntent.getBroadcast(context,position,serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                    context.sendBroadcast(serviceIntent);

                }
            }

        });
        if (selecetAlarm.getStatus()){
            serviceIntent.putExtra("extra", "on");
            serviceIntent.putExtra("active", selecetAlarm.toString());
            PendingIntent pendingIntent= PendingIntent.getBroadcast(context,position,serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent );
        }
        return convertView;
    }
}
