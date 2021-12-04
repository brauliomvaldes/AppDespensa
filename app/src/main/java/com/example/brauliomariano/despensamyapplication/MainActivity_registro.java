package com.example.brauliomariano.despensamyapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.StringPrepParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.itextpdf.text.Annotation.URL;

public class MainActivity_registro extends AppCompatActivity {

    private EditText inputRun;
    private TextView lblGotoLogin;
    private EditText inputNombre;
    private EditText inputEmail;
    private EditText inputPassword;
    private Spinner sComuna;
    private Spinner sCiudad;
    private Spinner sEdad;
    private RequestQueue requestQueue;
    private boolean existeRun;
    private boolean seRegistroUsuario = false;
    ProgressDialog progres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        inputRun = findViewById(R.id.txtRun);
        inputNombre = findViewById(R.id.txtNombre);
        inputEmail = findViewById(R.id.txtEmail);
        inputPassword = findViewById(R.id.txtPass);

        leerComuna();
        leerCiudad();
        leerEdad();
        lblGotoLogin = findViewById(R.id.link_to_login);
        lblGotoLogin.setVisibility(View.INVISIBLE);
        lblGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(MainActivity_registro.this, Despensa_login_MainActivity.class);
                MainActivity_registro.this.startActivity(login_intent);
            }
        });
    }

    public void leerComuna(){
        try{
            AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                    "despensa", null, 1);
            SQLiteDatabase db = adm.getWritableDatabase();

            Cursor filas = db.rawQuery("select * from comuna",null);
            ArrayList<String> lista = new ArrayList<String>();
            if(filas.getCount()>0){
                lista.add("comuna");
                while(filas.moveToNext()) {
                    lista.add(filas.getString(1));
                }
                sComuna = (Spinner)findViewById(R.id.spinnerComuna);

                ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, R.layout.spinner_item, lista);
                sComuna.setAdapter(adaptador);

            }else{
                Toast.makeText(this,"no hay comunas",Toast.LENGTH_LONG).show();
            }
            filas.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(this,"error comuna:"+e,Toast.LENGTH_LONG).show();
        }
    }

    public void leerCiudad(){
        try{
            AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                    "despensa", null, 1);
            SQLiteDatabase db = adm.getWritableDatabase();
            Cursor filas = db.rawQuery("select * from ciudad",null);
            ArrayList<String> lista = new ArrayList<String>();
            if(filas.getCount()>0){
                lista.add("ciudad");
                while(filas.moveToNext()) {
                    lista.add(filas.getString(1));
                }
                sCiudad = (Spinner)findViewById(R.id.spinnerCiudad);

                ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, R.layout.spinner_item, lista);
                sCiudad.setAdapter(adaptador);

            }else{
                Toast.makeText(this,"no hay ciudades",Toast.LENGTH_LONG).show();
            }
            filas.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(this,"error ciudad:"+e,Toast.LENGTH_LONG).show();
        }
    }

    public void leerEdad(){
        ArrayList<String> lista = new ArrayList<String>();

        lista.add("0-14 años");
        lista.add("15-24 años");
        lista.add("25-54 años");
        lista.add("55-64 años");
        lista.add("65 años y más");

        sEdad = (Spinner)findViewById(R.id.spinnerEdad);

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, R.layout.spinner_item, lista);
        sEdad.setAdapter(adaptador);
    }

    //boton registrar usuario en el equipo
    public void registrarUsuario(View v){
        String runRegistro = inputRun.getText().toString();
        String n = inputNombre.getText().toString();
        String e = inputEmail.getText().toString();
        String p = inputPassword.getText().toString();
        byte co = (byte)sComuna.getSelectedItemId();
        byte ci = (byte)sCiudad.getSelectedItemId();

        if(runRegistro.isEmpty() || n.isEmpty()  || e.isEmpty()
                || p.isEmpty() || co==0 && ci==0){
            Toast.makeText(this, "Debe ingresar datos requeridos, reintente", Toast.LENGTH_SHORT).show();
        }else {

            boolean validaRun = modulo11(runRegistro);
            if (validaRun) {
                //buscar usuario en el servidor

                buscarUsuario(Globales.protocolo + Globales.miIP + Globales.linkBuscarUsuario + runRegistro);
            } else {
                Toast.makeText(this, "RUN NO VALIDO, reintente", Toast.LENGTH_SHORT).show();
            }
        }
    }



    //busca usuario en el servidor
    private void buscarUsuario(String URL){

        // barra progreso

        progres = new ProgressDialog(this);
        progres.setMessage("conectando...");
        progres.show();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>(){
            @Override

            public void onResponse(JSONArray response){
                progres.hide();
                Toast.makeText(getApplicationContext(), "Atención! RUN ya esta registrado en el Servidor", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //registrar usuario en el servidor
                registroNuevoUsuario();
            }
        }
        );

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy( 0, -1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    //registrar usuario en servidor y equipo
    public void registroNuevoUsuario(){

        String n = inputNombre.getText().toString();
        String e = inputEmail.getText().toString();
        String p = inputPassword.getText().toString();
        byte co = (byte)sComuna.getSelectedItemId();
        byte ci = (byte)sCiudad.getSelectedItemId();

        ejecutarServicio(Globales.protocolo+Globales.miIP+Globales.linkInsertaUsuario);
    }



    private String hashClave(String clave) {
        try { java.security.MessageDigest md = java.security.MessageDigest.getInstance("md5");
            byte[] array = md.digest(clave.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {

        }
        return null;
    }


    private void ejecutarServicio(String URL){
        // se graba nuevo usuario
        // de la App


        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String responde){
                progres.hide();
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                grabarUsuarioEnEquipoCelular();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                seRegistroUsuario=false;
                progres.hide();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String hash;
                hash = hashClave(inputPassword.getText().toString());
                hash = hashClave(hash);

                Map<String,String> parametros=new HashMap<String, String>();


                parametros.put("run", inputRun.getText().toString());
                parametros.put("nombre", inputNombre.getText().toString());

                //el item id inicia en 0
                //asi el itemid
                parametros.put("comuna", String.valueOf((byte)sComuna.getSelectedItemId()));
                parametros.put("ciudad", String.valueOf((byte)sCiudad.getSelectedItemId()));

                //asi la edad coincide con la informacion
                parametros.put("edad", String.valueOf((byte)sEdad.getSelectedItemId()));
                parametros.put("email", inputEmail.getText().toString());

                parametros.put("password", hash);

                return parametros;

            }
        };

        // configura tiempo de reintento en la publicacion de la informacion al servidor
        // para evitar doble publicacion por el reintento
        // esto hace que no se realicen reintentos

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 0, -1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





    public void grabarUsuarioEnEquipoCelular(){
        String runRegistro = inputRun.getText().toString();
        String nombre = inputNombre.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(this, "despensa",
                null, 1);
        SQLiteDatabase db = adm.getWritableDatabase();
        try{
            ContentValues parametros = new ContentValues();
            parametros.put("run", runRegistro);
            parametros.put("nombre", nombre);
            parametros.put("comuna", String.valueOf((byte)sComuna.getSelectedItemId()));
            parametros.put("ciudad", String.valueOf((byte)sCiudad.getSelectedItemId()));
            parametros.put("edad", String.valueOf((byte)sEdad.getSelectedItemId()));
            parametros.put("email", email);
            parametros.put("password", password);
            long r = db.insert("usuario", null, parametros);
            if (r>0){
                Cursor filas = db.rawQuery("select * from usuario",null);
                if(filas.getCount()>0){
                    inputRun.setVisibility(View.INVISIBLE);
                    inputNombre.setVisibility(View.INVISIBLE);
                    inputEmail.setVisibility(View.INVISIBLE);
                    inputPassword.setVisibility(View.INVISIBLE);
                    sComuna.setVisibility(View.INVISIBLE);
                    sCiudad.setVisibility(View.INVISIBLE);
                    sEdad.setVisibility(View.INVISIBLE);
                    Button btnRegistro = (Button)findViewById(R.id.btnRegister);
                    btnRegistro.setVisibility(View.INVISIBLE);
                    lblGotoLogin.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "registro usuario exitoso en la App", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Error, no se registro usuario en la App", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "error al registrar usuario en la App"+parametros, Toast.LENGTH_LONG).show();
            }
            db.close();
        }catch (Exception e){
            adm.onUpgrade(db,1,1);
            Toast.makeText(this, "error: valor "+e, Toast.LENGTH_LONG).show();
        }
    }




    public boolean modulo11(String rn){
        // valida digito verificador

        boolean valida = false;
        try {
            rn = rn.toUpperCase();
            rn = rn.replace(".", "");
            rn = rn.replace("-", "");
            int rutAux = Integer.parseInt(rn.substring(0, rn.length() - 1));
            char dv = rn.charAt(rn.length() - 1);
            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                valida = true;
            }
        } catch (Exception e) {}
        return valida;
    }



}
