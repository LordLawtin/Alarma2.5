package com.lawtin.alarma20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView=findViewById(R.id.barraNavegacion);

        frameLayout=findViewById(R.id.framelayaout);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if (itemId==R.id.navAlarma){
                    loadFragment(new AlarmaFragment(), false);

                } else if (itemId==R.id.navCompartir) {
                    loadFragment(new CompartirFragment(), false);

                } else if (itemId==R.id.navCronometro) {
                    loadFragment(new CronometroFragment(),false);
                    
                } else if (itemId==R.id.navTemporizador) {
                    loadFragment(new temporizadorFragment(),false);
                    
                } else if (itemId==R.id.navHorario) {
                    loadFragment(new AlarmaFragment(), false);

                }else {
                    loadFragment(new PerfilFragment(),false);
                }
                loadFragment(new AlarmaFragment(), true);

            }
        });

    }
    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        if (isAppInitialized){
            fragmentTransaction.add(R.id.framelayaout, fragment);
        }else {
            fragmentTransaction.replace(R.id.framelayaout, fragment);
        }

        fragmentTransaction.commit();
    }
}