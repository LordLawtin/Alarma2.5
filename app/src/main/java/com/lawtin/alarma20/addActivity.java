package com.lawtin.alarma20;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class addActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private EditText editText;
    private Button buttonSave, buttomCancel;
    private Alarm alarm;
    private boolean needRefresh;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activyty_add);

        timePicker=findViewById(R.id.timeP);
        editText=findViewById(R.id.nameA);
        buttonSave=findViewById(R.id.GuardarA);
        buttomCancel=findViewById(R.id.cancelar);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour= timePicker.getCurrentHour();
                int minute=timePicker.getCurrentMinute();
                String name= editText.getText().toString();

                DatabaseHelper db=new DatabaseHelper(getApplicationContext());

                alarm=new Alarm(hour,minute,true,name);
                db.addAlarm(alarm);

                needRefresh=true;

                onBackPressed();
            }
        });

        buttomCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }


        });
    }

    @Override
    public void finish() {
        Intent data= new Intent();
        data.putExtra("needRefresh", needRefresh);
        this.setResult(RESULT_OK, data);
        super.finish();
    }
}
