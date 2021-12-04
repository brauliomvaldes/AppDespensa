package com.example.brauliomariano.despensamyapplication;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivityEliminar extends AppCompatActivity{
    private String articulos[];
    private int eliminado[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main_eliminar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setTitle(R.string.t_eliminar);
        listarArticulos();
    }

    public void listarArticulos(){

        try{

            // lee string-array del string.xml
            String[] origenes = getResources().getStringArray(R.array.origenes);

            final ListView lista = (ListView)findViewById(R.id.ListView_lista);
            AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                    (this,"despensa",null,1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor filas = bd.rawQuery("select * from listadecompra",null);
            int index = filas.getCount();
            if(index > 0){
                // luego de leer los articulos y dejarlos en un cursor, se agregan a un array
                articulos = new String[index];
                eliminado = new int[index];
                int i = 0, c, p;
                while(filas.moveToNext()){

                    articulos[i] = filas.getString(1);
                    eliminado[i] = 0;
                    i++;
                }

                // una vez cargado el array con los articulos, se envia a la ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_checked, articulos);
                lista.setAdapter(adapter);
                // accion sobre click en la lista

                lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id){
                        int item = position;
                        String itemval = (String)lista.getItemAtPosition(position);

                        if(eliminado[item]==0){
                            eliminado[item]=1;
                            Toast.makeText(getApplication(), "se elimina",Toast.LENGTH_SHORT).show();
                        }else{
                            eliminado[item]=0;
                            Toast.makeText(getApplication(), "se mantiene",Toast.LENGTH_SHORT).show();
                        }
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

    protected void onStop() {
        super.onStop();
        if (eliminado!=null){
            AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                    (this,"despensa",null,1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            for(int ii=0;ii<eliminado.length;ii++){
                if(eliminado[ii]>0){
                    try{
                        bd.execSQL("delete from listadecompra where nombre='"+articulos[ii]+"'");
                    }catch (Exception e){
                        Toast.makeText(this, "error:"+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
