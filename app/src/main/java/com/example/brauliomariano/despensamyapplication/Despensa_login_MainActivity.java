package com.example.brauliomariano.despensamyapplication;
import android.annotation.SuppressLint;
import android.content.ContentValues;
//import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Despensa_login_MainActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private boolean registrado = false;
    private boolean visualiza;

    //variables para almacenar registros que deben ser sincronizados
    ArrayList<Articulos> registros;
    Articulos art;
    int punteroRegistros1;
    int punteroRegistros2;
    //
    public RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despensa_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        registrado=buscarUsuarioRegistrado();
        inputEmail = findViewById(R.id.txtEmail);
        inputPassword = findViewById(R.id.txtPass);

        //tablas origen, categoria, comunas, ciudades
        ListView l = findViewById(R.id.ListView_lista);
        TextView c = findViewById(R.id.edtCantidad);
        TextView n = findViewById(R.id.edtNombre);
        Button b = findViewById(R.id.btnLista);
        l.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        n.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        visualiza=true;
        chequearTablas();
        listadoDeCompras();

        if(!registrado){
            Toast.makeText(this, "No existe registro de usuario en la App", Toast.LENGTH_LONG).show();
            Intent register_intent = new Intent(Despensa_login_MainActivity.this, MainActivity_registro.class);
            Despensa_login_MainActivity.this.startActivity(register_intent);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.action_settings: {

                ListView l = findViewById(R.id.ListView_lista);
                TextView c = findViewById(R.id.edtCantidad);
                TextView n = findViewById(R.id.edtNombre);
                Button b = findViewById(R.id.btnLista);
                visualiza=!visualiza;
                if(visualiza){
                    l.setVisibility(View.INVISIBLE);
                    c.setVisibility(View.INVISIBLE);
                    n.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.INVISIBLE);
                }else{
                    l.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                    n.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                }

            }
            break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void ingresaRecordatorio(View view){
        EditText c = findViewById(R.id.edtCantidad);
        EditText n = findViewById(R.id.edtNombre);

        if(!(c.getText().toString().isEmpty()) &&
                !(n.getText().toString().isEmpty())){
            int cc = Integer.valueOf(c.getText().toString());
            String nn = n.getText().toString();
            if(nn!="" && cc>0){
                boolean resultado = grabarRecordatorio(cc,nn);
                if(resultado){
                    listadoDeCompras();
                }
            }
        }
        c.setText("");
        n.setText("");
    }

    public boolean grabarRecordatorio(int c, String n){
        AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                    "despensa", null, 1);
        SQLiteDatabase db = adm.getWritableDatabase();
        try {
            ContentValues registro = new ContentValues();
            registro.put("cantidad", c);
            registro.put("nombre", n);
            long r = db.insert("listadecompra", null, registro);
            if (r > 0) {
                Toast.makeText(this, "registro exitoso" + registro, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "error al registrar " + registro, Toast.LENGTH_LONG).show();
                return false;
            }
        }catch (Exception e){
            Toast.makeText(this,"no se registro recordatorio",Toast.LENGTH_SHORT).show();
            return false;
        }
        db.close();
        return true;
    }

    public void listadoDeCompras(){

            try{
                // lee string-array del string.xml
                final ListView lista = (ListView)findViewById(R.id.ListView_lista);
                AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                        (this,"despensa",null,1);
                SQLiteDatabase bd = admin.getWritableDatabase();
                Cursor filas = bd.rawQuery("select * from listadecompra",null);

                int index = filas.getCount();
                if(index > 0){

                    // luego de leer los articulos y dejarlos en un cursor, se agregan a un array
                    String articulos [] = new String[index];
                    int i = 0;
                    while(filas.moveToNext()){
                        articulos[i] = "> "+filas.getInt(0)+": "+filas.getString(1);
                        i++;
                    }

                    // una vez cargado el array con los articulos, se envia a la ListView
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_list_item_1, articulos);
                    lista.setAdapter(adapter);
                    // accion sobre click en la lista

                    lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                            int item = position;
                            String itemval = (String)lista.getItemAtPosition(position);
                        }
                    });


                }else {
                    Toast.makeText(this, "listado de compas vacio", Toast.LENGTH_LONG).show();
                }
                filas.close();
                bd.close();
            }catch(Exception e){
                Toast.makeText(this, "error:"+e, Toast.LENGTH_LONG).show();
            }
    }



    public void validarLogin(View v){
        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();
        if(email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "debe completar los campos", Toast.LENGTH_LONG).show();
        }else{
            if(email.equals(Globales.getEmail()) && pass.equals(Globales.getClave())){

                //Para borrar todas las actividades mientras se abre una nueva, haga lo siguiente:

                Intent i = new Intent(getApplicationContext(), MainActivity_Menu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }else{
                Toast.makeText(this, "usuario o password incorrectos", Toast.LENGTH_LONG).show();
            }
        }
    }


    public boolean buscarUsuarioRegistrado(){
        AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                (this,"despensa",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        try {
            Cursor filas = bd.rawQuery("select * from usuario",null);
            if(filas.getCount() > 0) {
                while(filas.moveToNext()) {
                    Globales.setNombre(filas.getString(1));
                    Globales.setEmail(filas.getString(5));
                    Globales.setClave(filas.getString(6));
                    Globales.setRun(filas.getString(0));
                }
                registrado = true;
            }else{
                Toast.makeText(this, "usuario no registrado en App", Toast.LENGTH_LONG).show();
            }
            filas.close();
            bd.close();
        }catch(Exception e){
            Toast.makeText(this, "error lectura interna", Toast.LENGTH_LONG).show();
            registrado=false;
            admin.onUpgrade(bd,1,3);
        }finally {
            return registrado;
        }
    }


    public void chequearTablas(){
        // revisa que si existen articulos no respaldados en el servidor, sean respaldados
        // revisa si el codigo estado = 1, se debe respaldar y cambiar estado = 0
        try {

            //arraylist para guardar registros a grabar en el servidor

            AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                    "despensa", null, 1);
            SQLiteDatabase db = adm.getWritableDatabase();
            Cursor filas = db.rawQuery("select serie, nombre, valor, fecha, categoria, origen, run from articulos where subido = 0",null);
            if(filas.moveToFirst()) {
                registros = new ArrayList<Articulos>();
                Toast.makeText(this,"articulos a subir al servidor:"+filas.getCount(),Toast.LENGTH_LONG).show();
                do {
                        art = new Articulos();
                        art.serie_a = filas.getString(0);
                        art.nombre_a = filas.getString(1);
                        art.valor_a = Integer.valueOf(filas.getString(2));
                        art.fecha_a = filas.getString(3);
                        art.categoria_a = Byte.valueOf(filas.getString(4));
                        art.origen_a = Byte.valueOf(filas.getString(5));
                        art.run_a = filas.getString(6);
                        if(art.categoria_a>6){
                            // valida que la categoria sea mayor a las que existen
                            art.categoria_a=7;
                        }
                        registros.add(art);
                    } while (filas.moveToNext());

                Toast.makeText(this,"registros a subir:"+registros.size(),Toast.LENGTH_LONG).show();
                punteroRegistros1 = registros.size();
                punteroRegistros2 = registros.size();

                for(int p=0; p<punteroRegistros1;p++){
                    Toast.makeText(this,"p:"+p+" r->"+registros.get(p).serie_a,Toast.LENGTH_SHORT).show();
                    ejecutarServicio(Globales.protocolo+Globales.miIP+Globales.linkInsertarArticulo);
                }
            }else{
                Toast.makeText(this,"no hay registros que subir:",Toast.LENGTH_LONG).show();
            }
            filas.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(this, "ERROR:"+e, Toast.LENGTH_LONG).show();
        }
    }


    private void ejecutarServicio(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>(){

            @Override
            public void onResponse(String responde){

                AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper(getApplicationContext(),
                            "despensa", null, 1);
                SQLiteDatabase database = admin.getWritableDatabase();
                while(punteroRegistros2>0) {
                    punteroRegistros2--;
                    String s = registros.get(punteroRegistros2).serie_a;
                    database.execSQL("UPDATE articulos SET subido = 1 WHERE serie=" + s);
                    Toast.makeText(getApplicationContext(), "articulo" + s + "sincronizado",
                            Toast.LENGTH_SHORT).show();
                }
                database.close();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){

                        Toast.makeText(getApplicationContext(), "Error al insertar: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.clear();


                if(punteroRegistros1>0) {
                    punteroRegistros1--;
                    parametros.put("serie", registros.get(punteroRegistros1).serie_a);
                    parametros.put("nombre", registros.get(punteroRegistros1).nombre_a);
                    parametros.put("valor", String.valueOf(registros.get(punteroRegistros1).valor_a));
                    parametros.put("fecha", registros.get(punteroRegistros1).fecha_a);
                    parametros.put("categoria", String.valueOf(registros.get(punteroRegistros1).categoria_a));
                    parametros.put("origen", String.valueOf(registros.get(punteroRegistros1).origen_a));
                    parametros.put("run", registros.get(punteroRegistros1).run_a);
                }
                return parametros;
            }
        };

        // configura tiempo de reintento en la publicacion de la informacion al servidor
        // para evitar doble publicacion por el reintento
        // esto hace con no se realicen reintentos

        // stringRequest.setRetryPolicy(new DefaultRetryPolicy( 0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void salir(View view){finish();}

}
