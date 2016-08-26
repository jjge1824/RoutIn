package com.example.jose_jesus_guzman.avanti.ClasesViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.jose_jesus_guzman.avanti.R;

public class AcercaActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);
        inicializarComponentes();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void inicializarComponentes() {
        toolbar = (Toolbar) findViewById(R.id.acerca_toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(AcercaActivity.this, PrincipalActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
