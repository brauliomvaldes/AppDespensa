package com.example.brauliomariano.despensamyapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main__menu);
        this.setTitle("MENU MiDespensa ("+Globales.getRun()+")");

    }

    public void listarDespensa(View view){

        Intent i = new Intent(this, Tabla_Despensa.class);
        startActivity(i);
    }

    public void sumarRestarDespensa(View view){
        Intent i = new Intent(this, MainActivitySumarRestar.class);
        startActivity(i);
    }

    public void eliminarDespensa(View view){
        Intent i = new Intent(this, MainActivityEliminar.class);
        startActivity(i);
    }

    public void listaCompras(View view){
        Intent i = new Intent(this, MainActivityListaCompras.class);
        startActivity(i);
    }

    public void carritoCompras(View view){
        Intent i = new Intent(this, CarritoCompraActivity.class);
        startActivity(i);
    }

    public void cerrar(View view){
        finish();
    }
}
