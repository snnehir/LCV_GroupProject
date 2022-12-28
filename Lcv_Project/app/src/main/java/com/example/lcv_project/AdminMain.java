package com.example.lcv_project;

import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lcv_project.Fragment.ArrivalsFragment;
import com.example.lcv_project.Fragment.InnovationFragment;
import com.example.lcv_project.Fragment.KullanicilarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMain extends AppCompatActivity {
   private KullanicilarFragment kullanicilarFragment;
   private InnovationFragment innovationFragment;
   private ArrivalsFragment arrivalsFragment;
    private BottomNavigationView mBottomView;
    private FragmentTransaction transaction;
    private Toolbar mToolbar;
    private RelativeLayout mRelaNotif;
    private TextView txtBildirimSayisi;
    private void init(){
        mBottomView=(BottomNavigationView) findViewById(R.id.main_activity_bottomView);
        mToolbar=(Toolbar)findViewById( R.id.toolbar );
        mRelaNotif=(RelativeLayout)findViewById( R.id.bar_layout_relaNotif);
        txtBildirimSayisi=(TextView) findViewById( R.id.bar_layout_txtBildirimSayisi );
        kullanicilarFragment= new KullanicilarFragment();
        innovationFragment= new InnovationFragment();
        arrivalsFragment= new ArrivalsFragment();
        FragmentAyarla(innovationFragment);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        init();
        mBottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                      switch (item.getItemId()) {
                        case R.id.bottom_nav_ic_profile:
                            mRelaNotif.setVisibility( View.INVISIBLE );
                            FragmentAyarla(kullanicilarFragment);
                            return true;
                        case R.id.bottom_nav_ic_arrivals:
                            mRelaNotif.setVisibility( View.VISIBLE );
                            FragmentAyarla(arrivalsFragment);
                            return true;
                        case R.id.bottom_nav_ic_innvitation:
                            mRelaNotif.setVisibility( View.INVISIBLE );
                            FragmentAyarla(innovationFragment);
                            return true;
                        default:
                            return false;
                    }

                }

        });
    }
    private void FragmentAyarla(Fragment fragment){
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.MainAdmin_frameLayout, fragment);
        transaction.commit();
    }
}