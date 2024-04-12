package com.lawtin.alarma20;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class addActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private EditText editText;
    private Button buttonSave, buttonCancel;

    private boolean needRefresh = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activyty_add);

        timePicker = findViewById(R.id.timeP);
        editText = findViewById(R.id.nameA);
        buttonSave = findViewById(R.id.GuardarA);
        buttonCancel = findViewById(R.id.cancelar);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String name = editText.getText().toString();

                // Validar entrada
                if (name.isEmpty()) {
                    editText.setError("Ingrese un nombre para la alarma");
                    return;
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

                Alarm alarm = new Alarm(hour, minute, true, name);
                databaseHelper.addAlarm(alarm);

                needRefresh = true;

                databaseHelper.close();

                // Proporcionar retroalimentación visual
                Toast.makeText(addActivity.this, "Alarma guardada", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("needRefresh", needRefresh);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
