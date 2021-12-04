package com.example.brauliomariano.despensamyapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CarritoCompraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_carrito_compra);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setTitle(R.string.t_carrito);
        listaCarrito();
    }


    public void listaCarrito(){
        try{
            final ListView lista = (ListView)findViewById(R.id.ListView_lista);
            AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                    (this,"despensa",null,1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor filas = bd.rawQuery("select * from carrito",null);
            int index = filas.getCount();
            if(index > 0){

                // luego de leer los articulos y dejarlos en un cursor, se agregan a un array
                String articulos [] = new String[index+1];

                int i = 0, c, p;
                int totalizar = 0;
                while(filas.moveToNext()){
                    c = filas.getInt(0);
                    p = filas.getInt(2);
                    articulos[i] = "> "+c+": "+filas.getString(1)+"---> $ "+p;

                    totalizar = totalizar + ( c * p);
                    i++;
                }
                articulos[i]="Total carrito -----> $ "+totalizar;
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
                Toast.makeText(this, "no hay datos que mostrar", Toast.LENGTH_LONG).show();
            }
            filas.close();
            bd.close();
        }catch(Exception e){
            Toast.makeText(this, "error:"+e, Toast.LENGTH_LONG).show();
        }
    }

    public void enviarMensajeCarrito(View view){

        try{
            final ListView lista = (ListView)findViewById(R.id.ListView_lista);
            AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                    (this,"despensa",null,1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor filas = bd.rawQuery("select * from carrito",null);
            int index = filas.getCount()+1;
            if(index > 0){
                // luego de leer los articulos y dejarlos en un cursor, se agregan a un array
                String articulos="";

                int c, p;
                int totalizar = 0;
                while(filas.moveToNext()){
                    c = filas.getInt(0);
                    p = filas.getInt(2);
                    articulos += "> "+c+": "+filas.getString(1)+"---> $ "+p+"\n";

                    totalizar = totalizar + ( c * p);
                }
                articulos += "Total carrito -----> $ "+totalizar+"\n";

                //lanza whatsapp y envia mensaje al contacto que selecccione
                /*
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                startActivity(launchIntent);
                */

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, articulos);
                sendIntent.setType("text/plain");
                //sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);

                /*
                String url = "whatsapp://send?text= ";
                Intent navegar = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+url));
                startActivity(navegar);
                Toast.makeText(getApplicationContext(),"enviando mensaje",Toast.LENGTH_LONG).show();
                */

            }else {
                Toast.makeText(this, "no hay datos que mostrar", Toast.LENGTH_LONG).show();
            }
            filas.close();
            bd.close();
        }catch(Exception e){
            Toast.makeText(this, "no tiene instalado Whatsapp"+e, Toast.LENGTH_LONG).show();
        }

    }

    public void vaciarCarrito(View view){
        AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                (this,"despensa",null,4);
        SQLiteDatabase bd = admin.getWritableDatabase();
        try{
            bd.execSQL("DELETE FROM carrito");
        }catch (Exception e){
            Toast.makeText(this, "error:"+e, Toast.LENGTH_LONG).show();
        }
    }

    /*
    public void volver(View view){

        finish();
    }
    */
}
