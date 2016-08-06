package com.example.jose_jesus_guzman.avanti.ClasesViews;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jose_jesus_guzman.avanti.R;

public class PrincipaActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principa);
        iniciarComponentes();
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));

    }

    private void iniciarComponentes(){
        toolbar = (Toolbar) findViewById(R.id.principal_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.principal_TabLayout);
    }

}
