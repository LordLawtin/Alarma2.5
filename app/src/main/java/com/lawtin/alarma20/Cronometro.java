package com.lawtin.alarma20;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Cronometro extends AppCompatActivity {

    private int timeSelected = 0;
    private CountDownTimer timeCountDown;
    private int timeProgress = 0;
    private long pauseOffSet = 0;
    private boolean isStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        ImageButton addBtn = findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeFunction();
            }
        });

        Button startBtn = findViewById(R.id.btnPlayPause);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimerSetup();
            }
        });

        ImageButton resetBtn = findViewById(R.id.ib_reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTime();
            }
        });

        TextView addTimeTv = findViewById(R.id.tv_addTime);
        addTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExtraTime();
            }
        });
    }

    private void addExtraTime() {
        ProgressBar progressBar = findViewById(R.id.pbTimer);
        if (timeSelected != 0) {
            timeSelected += 15;
            progressBar.setMax(timeSelected);
            timePause();
            startTimer(pauseOffSet);
            Toast.makeText(this, "15 sec added", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetTime() {
        if (timeCountDown != null) {
            timeCountDown.cancel();
            timeProgress = 0;
            timeSelected = 0;
            pauseOffSet = 0;
            timeCountDown = null;
            Button startBtn = findViewById(R.id.btnPlayPause);
            startBtn.setText("Staert");
            isStart = true;
            ProgressBar progressBar = findViewById(R.id.pbTimer);
            progressBar.setProgress(0);
            TextView timeLeftTv = findViewById(R.id.tvTimeLeft);
            timeLeftTv.setText("0");
        }
    }

    private void timePause() {
        if (timeCountDown != null) {
            timeCountDown.cancel();
        }
    }

    private void startTimerSetup() {
        Button startBtn = findViewById(R.id.btnPlayPause);
        if (timeSelected > timeProgress) {
            if (isStart) {
                startBtn.setText("Pause");
                startTimer(pauseOffSet);
                isStart = false;
            } else {
                isStart = true;
                startBtn.setText("Resume");
                timePause();
            }
        } else {
            Toast.makeText(this, "Enter Time", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTimer(long pauseOffSetL) {
        ProgressBar progressBar = findViewById(R.id.pbTimer);
        progressBar.setProgress(timeProgress);
        timeCountDown = new CountDownTimer((timeSelected * 1000) - pauseOffSetL * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeProgress++;
                pauseOffSet = timeSelected - millisUntilFinished / 1000;
                progressBar.setProgress(timeSelected - timeProgress);
                TextView timeLeftTv = findViewById(R.id.tvTimeLeft);
                timeLeftTv.setText(String.valueOf(timeSelected - timeProgress));
            }

            @Override
            public void onFinish() {
                resetTime();
                Toast.makeText(Cronometro.this, "Times Up!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void setTimeFunction() {
        Dialog timeDialog = new Dialog(this);
        timeDialog.setContentView(R.layout.add_dialog);
        EditText timeSet = timeDialog.findViewById(R.id.etGetTime);
        TextView timeLeftTv = findViewById(R.id.tvTimeLeft);
        Button btnStart = findViewById(R.id.btnPlayPause);
        ProgressBar progressBar = findViewById(R.id.pbTimer);
        timeDialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeSet.getText().toString().isEmpty()) {
                    Toast.makeText(Cronometro.this, "Enter Time Duration", Toast.LENGTH_SHORT).show();
                } else {
                    resetTime();
                    timeLeftTv.setText(timeSet.getText());
                    btnStart.setText("Start");
                    timeSelected = Integer.parseInt(timeSet.getText().toString());
                    progressBar.setMax(timeSelected);
                }
                timeDialog.dismiss();
            }
        });
        timeDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCountDown != null) {
            timeCountDown.cancel();
            timeProgress = 0;
        }
    }
}
