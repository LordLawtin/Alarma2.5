package com.lawtin.alarma20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioP extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FirebaseUser user;

    TextView Saludotxt;

    Button LogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_p);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        Saludotxt = findViewById(R.id.Saludo);

        LogOutBtn = findViewById(R.id.LogOutBtn);

        Saludotxt.setText(user.getUid());

        LogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(UsuarioP.this, Registration.class));
            }
        });
    }


}