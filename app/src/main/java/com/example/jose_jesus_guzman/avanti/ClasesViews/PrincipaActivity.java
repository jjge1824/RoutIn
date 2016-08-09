package com.example.jose_jesus_guzman.avanti.ClasesViews;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_perfil:
                startActivity(new Intent(PrincipaActivity.this, PerfilActivity.class));
                break;
            case R.id.action_acerca:
                //TODO Accion de acerca de

                startActivity(new Intent(PrincipaActivity.this, AcercaActivity.class));
                break;
            case R.id.action_cerrar:
                //TODO Aciión de cerrar sesión

                startActivity(new Intent(PrincipaActivity.this, PantallaInicialActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
