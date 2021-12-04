package com.example.brauliomariano.despensamyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivitySumarRestar extends AppCompatActivity{

    private ZXingScannerView vistaEscaner;
    private StringBuilder fechaHoy = new StringBuilder();
    private String run;
    private String nombreUsuario;
    private Spinner sOrigen;
    //guarda seleccion origen
    private byte posOrigen = 0;

    private Spinner sCategoria;
    private Integer stockAnterior = 0;
    private EditText nvaCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main_sumar_restar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        leerOrigen();
        nvaCategoria = (EditText)findViewById(R.id.edtNvaCategoria);
        nvaCategoria.setEnabled(false);

        try {

            run = Globales.getRun();
            nombreUsuario = Globales.getNombre();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar gregorianCalendar = new GregorianCalendar();

            fechaHoy.append(String.valueOf(gregorianCalendar.get(Calendar.YEAR)))
                    .append("-")
                    .append(gregorianCalendar.get(Calendar.MONTH)+1)
                    .append("-")
                    .append(gregorianCalendar.get(Calendar.DAY_OF_MONTH));

        }catch (Exception e){
            Toast.makeText(this, "error:"+e, Toast.LENGTH_SHORT).show();
        }
        this.setTitle("Actualiza Stock ("+run+")");
    }


    public void leerOrigen(){
        try{
            AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                    "despensa", null, 1);
            SQLiteDatabase db = adm.getWritableDatabase();

            Cursor filas = db.rawQuery("select * from origen",null);
            ArrayList<String> lista = new ArrayList<String>();
            if(filas.getCount()>0){
                lista.add("seleccion origen");
                while(filas.moveToNext()) {
                    lista.add(filas.getString(1));
                    //Toast.makeText(this,filas.getString(1),Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(this,"origen:"+lista,Toast.LENGTH_LONG).show();

                sOrigen = (Spinner)findViewById(R.id.spinnerOrigen);

                ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, R.layout.spinner_item, lista);
                sOrigen.setAdapter(adaptador);

            }else{
                Toast.makeText(this,"no hay origen",Toast.LENGTH_LONG).show();
            }
            filas.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(this,"error origen:"+e,Toast.LENGTH_LONG).show();
        }
    }

    public void leerCategoria(){
        try{
            AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                    "despensa", null, 1);
            SQLiteDatabase db = adm.getWritableDatabase();

            Cursor filas = db.rawQuery("select * from categoria",null);
            ArrayList<String> lista = new ArrayList<String>();

            if(filas.getCount()>0){
                lista.add("seleccion categoria");
                while(filas.moveToNext()) {
                    lista.add(filas.getString(1));
                    //Toast.makeText(this,filas.getString(1),Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(this,"categoria:"+lista,Toast.LENGTH_LONG).show();

                sCategoria = (Spinner)findViewById(R.id.spinnerCategoria);

                ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, R.layout.spinner_item, lista);
                sCategoria.setAdapter(adaptador);
            }else{
                Toast.makeText(this,"no hay categorias",Toast.LENGTH_LONG).show();
            }
            filas.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(this,"error categoria:"+e,Toast.LENGTH_LONG).show();
        }
    }

    public void ingresarNvaCategoria(View view){
        nvaCategoria = (EditText)findViewById(R.id.edtNvaCategoria);
        String nc = nvaCategoria.getText().toString();
        if(!nc.isEmpty()){
            AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                    "despensa", null, 1);
            SQLiteDatabase db = adm.getWritableDatabase();
            Cursor filas = db.rawQuery("select * from categoria",null);
            int numcategorias = filas.getCount();
            numcategorias++;
            try{
                db.execSQL("INSERT INTO categoria VALUES ("+numcategorias+",'"+nc+"')");
                Toast.makeText(this,"se agregó categoría",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(this,"no se logró agregar nueva categoría",Toast.LENGTH_SHORT).show();
            }
            db.close();
            leerCategoria();
        }else{
            Toast.makeText(this,"debe ingresar categoría"+nc,Toast.LENGTH_SHORT).show();
        }
    }

    public void Scanear(View v){

        Spinner spOrigen = (Spinner) findViewById(R.id.spinnerOrigen);
        posOrigen = (byte) spOrigen.getSelectedItemId();
        limpiarCampos();


        vistaEscaner = new ZXingScannerView(this);
        vistaEscaner.setResultHandler(new MainActivitySumarRestar.ZxingScanner());
        setContentView(vistaEscaner);
        try{
            vistaEscaner.startCamera();
        }catch (Exception e){
            Toast.makeText(this,"Existe un problema con la cámara", Toast.LENGTH_SHORT).show();
        }
    }


    // para cerrar la camara si no se presiona el botón atrás y se estaba utilianndo la cámara
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            vistaEscaner.stopCamera();
        }
        return super.onKeyDown(keyCode, event);
    }

    class ZxingScanner implements ZXingScannerView.ResultHandler{

        @Override
        public void handleResult(Result result){

            String dato = result.getText();
            setContentView(R.layout.activity_main_sumar_restar);
            vistaEscaner.stopCamera();
            TextView codigo = (TextView)findViewById(R.id.tv_id);
            codigo.setText(dato);
            // recupera origen porque no es necesario pedir nuevo origen
            leerOrigen();
            Spinner origenSpinner = (Spinner) findViewById(R.id.spinnerOrigen);
            origenSpinner.setSelection(posOrigen);

            ///////////////////////////////////////////////////////////7

            leerCategoria();
            nvaCategoria.setEnabled(true);
            try {
                AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                        "despensa", null, 1);
                SQLiteDatabase db = adm.getWritableDatabase();

                Cursor filas = db.rawQuery("select * from articulos where serie ="+dato,null);

                if(filas.moveToFirst()){
                    EditText nombre = (EditText)findViewById(R.id.et_articulo);
                    EditText valor = (EditText)findViewById(R.id.et_valor);
                    EditText cantidad = (EditText)findViewById(R.id.et_cantidadsuma);
                    Spinner categoria = (Spinner)findViewById(R.id.spinnerCategoria);
                    Spinner origen = (Spinner)findViewById(R.id.spinnerOrigen);

                    nombre.setText(filas.getString(1));
                    valor.setText(String.valueOf(filas.getInt(2)));
                    cantidad.setText(String.valueOf(filas.getInt(7)));
                    categoria.setSelection(filas.getInt(4));
                    origen.setSelection(filas.getInt(5));
                    //lee stock anterior
                    stockAnterior = filas.getInt(7);
                }
                filas.close();
                db.close();

            }catch (Exception e){
                codigo.setText("error lectura");
            }
        }
    }


    public void limpiarCampos(){

        EditText nombre = (EditText)findViewById(R.id.et_articulo);
        EditText precio = (EditText)findViewById(R.id.et_valor);
        EditText cantidad = (EditText)findViewById(R.id.et_cantidadsuma);
        Spinner categoria = (Spinner)findViewById(R.id.spinnerCategoria);
        //Spinner origenSpinner = (Spinner) findViewById(R.id.spinnerOrigen);

        nombre.setText("");
        precio.setText("");
        cantidad.setText("");
        categoria.setSelection(0);

        //origenSpinner.setSelection(posOrigen);
        nvaCategoria.setEnabled(false);
    }

    public void sumarArticulo(View v){
        EditText cc = (EditText)findViewById(R.id.et_cantidadsuma);
        int c;
        try{
            c = Integer.parseInt(cc.getText().toString());
        }catch (Exception e){
            c=0;
        }
        c++;
        cc.setText(String.valueOf(c));
    }


    public void restarArticulo(View v){
        EditText cc = (EditText)findViewById(R.id.et_cantidadsuma);
        int c;
        try{
            c = Integer.parseInt(cc.getText().toString());
            if(c<1){
                return;
            }
        }catch (Exception e){
            c=0;
        }
        c--;
        cc.setText(String.valueOf(c));
    }

    public void grabarArticulo(View view){
        try {
            View v = this.getCurrentFocus();
            v.clearFocus();
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }catch (Exception e){}


        TextView codigo = (TextView) findViewById(R.id.tv_id);
        EditText nombre = (EditText) findViewById(R.id.et_articulo);
        EditText precio = (EditText) findViewById(R.id.et_valor);
        EditText cantidad = (EditText) findViewById(R.id.et_cantidadsuma);

        Spinner spCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
        Spinner spOrigen = (Spinner) findViewById(R.id.spinnerOrigen);

        String id = codigo.getText().toString();
        String nom = nombre.getText().toString();
        byte categoria = (byte) spCategoria.getSelectedItemId();
        byte origen = (byte) spOrigen.getSelectedItemId();

        if (!(id.isEmpty()) && !(nom.isEmpty()) && origen>0 && categoria>0
                && precio.getText().toString()!= "" && cantidad.getText().toString()!="") {

            try {
                int prec = Integer.valueOf(precio.getText().toString());
                int cant = Integer.valueOf(cantidad.getText().toString());

                AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(this, "despensa",
                        null, 1);
                SQLiteDatabase db = adm.getWritableDatabase();
                Cursor existe = db.rawQuery("select * from articulos where serie=" + id, null);
                byte subido = 0;
                ContentValues registro = new ContentValues();
                registro.put("serie", id);
                registro.put("nombre", nom);
                registro.put("valor", prec);
                registro.put("fecha", fechaHoy.toString());
                registro.put("categoria", categoria);
                registro.put("origen", origen);
                registro.put("subido", subido);
                //se suma cantidad ingresada al stock anterior
                registro.put("stock", stockAnterior+cant);
                registro.put("run", run);

                if (existe.moveToFirst()) {
                    long r = db.update("articulos", registro, "serie=" + id, null);
                    if (r > 0) {
                        Toast.makeText(this, "registro exitoso" + registro, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "error al actualizar " + registro, Toast.LENGTH_LONG).show();
                    }

                } else {
                    long r = db.insert("articulos", null, registro);
                    if (r > 0) {
                        Toast.makeText(this, "registro exitoso" + registro, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "error al registrar " + registro, Toast.LENGTH_LONG).show();
                    }
                }
                existe.close();
                db.close();
            } catch (Exception e) {
                Toast.makeText(this, "error: valor " + e, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "error debe completar información", Toast.LENGTH_LONG).show();
            if(!nom.isEmpty()){
                Toast.makeText(this, "SÓLO puede almacenarlo en el CARRITO", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void grabarCarrito(View view){

        EditText nombre = (EditText)findViewById(R.id.et_articulo);
        EditText precio = (EditText)findViewById(R.id.et_valor);
        EditText cantidad = (EditText)findViewById(R.id.et_cantidadsuma);
        String nom = nombre.getText().toString();
        String prec = precio.getText().toString();
        String cant = cantidad.getText().toString();

        if(!(nom.isEmpty())){
            try{
                int pre, can;
                if(prec.isEmpty()){
                    pre = 0;
                }else{
                    pre = Integer.parseInt(prec);
                }
                if(cant.isEmpty()){
                    can = 0;
                }else{
                    can = Integer.parseInt(cant);
                }

                AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(this, "despensa",
                        null, 1);
                SQLiteDatabase db = adm.getWritableDatabase();

                ContentValues registro = new ContentValues();
                registro.put("cantidad", can);
                registro.put("nombre", nom);
                registro.put("valor", pre);

                long r = db.insert("carrito", null, registro);
                if (r>0){
                    Toast.makeText(this, "registro exitoso", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "error al registrar", Toast.LENGTH_LONG).show();
                }
                db.close();
            }catch (Exception e){
                Toast.makeText(this, "error: valor "+e, Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "error debe completar información", Toast.LENGTH_LONG).show();
        }
    }

}
