package com.example.brauliomariano.despensamyapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivityListaCompras extends AppCompatActivity {

    private String[]header={"cantidad", "nombre artículo", "precio"};
    private String shortText="Lista de Compras";
    private String longText="artículos con stock mínimo, se compra según este criterio.";
    private TemplatePDF templatePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main_lista_compras);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setTitle("Check Stock Despensa");

        templatePDF=new TemplatePDF(getApplicationContext());
        templatePDF.openDocument();
        templatePDF.addMetaData("Artciulos", "compra", "hogar");
        templatePDF.addTitles("Lista de Compras", "Supermercado", "Inacap.2018");
        templatePDF.addParagraph(shortText);
        templatePDF.addParagraph(longText);
        templatePDF.createTable(header, getClients());
        templatePDF.closeDocument();
    }

    private ArrayList<String[]> getClients(){

        // lee string-array del string.xml
        String[] origenes = getResources().getStringArray(R.array.origenes);

        ArrayList<String[]>rows = new ArrayList<>();
        EditText minimo = (EditText)findViewById(R.id.et_minimo);
        //cierra teclado virtual
        int totalizar = 0;
        try{
            //define mínimo stock para compra
            int m;
            int i = 0, c, p;

            if(minimo.length()==0){
                m = 0;
            }else{
                m = Integer.parseInt(minimo.getText().toString());
            }
            m++;
            final ListView lista = (ListView)findViewById(R.id.ListView_lista);
            AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                    (this,"despensa",null,1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor filas = bd.rawQuery("select serie, nombre, valor, stock, origen from articulos",null);
            int index = filas.getCount();
            if(index > 0){

                // luego de leer los articulos y dejarlos en un cursor, se agregan a un array
                String articulos [] = new String[index];
                String c1,c2,c3;

                while(filas.moveToNext()){
                    c1 = String.valueOf(filas.getInt(3));
                    c2 = String.valueOf(filas.getString(1));
                    c3 = String.valueOf(filas.getInt(2));

                    rows.add(new String[]{c1,c2,c3});

                    c = filas.getInt(3);
                    p = filas.getInt(2);

                    //recupera el origen del string-array desde string.xml

                    articulos[i] = "> "+c+": "+filas.getString(1)+"---> $ "+p+
                            "("+origenes[filas.getInt(4)]+")";

                    totalizar = totalizar + ( c * p);
                }


            }else {
                Toast.makeText(this, "no hay datos que mostrar", Toast.LENGTH_LONG).show();
            }
            filas.close();
            bd.close();

        }catch(Exception e){
            Toast.makeText(this, "error:"+e, Toast.LENGTH_LONG).show();
        }

        rows.add(new String[]{"","TOTAL ARTICULOS A COMPRAR       $",String.valueOf(totalizar)});
        return rows;
    }


    public void pdfView(View view){
        try{
            View v = this.getCurrentFocus();
            v.clearFocus();
            if(v != null){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
            templatePDF.viewPdf();
        }catch (Exception e){
            Toast.makeText(this, "error:"+e, Toast.LENGTH_LONG).show();
        }
    }

    public void actualizaLista(View view){
        generaCarrito(true);
    }

    public void enviarWhatsapp(View view){
        generaCarrito(false);
    }



    public void generaCarrito(boolean opcion){

        EditText minimo = (EditText)findViewById(R.id.et_minimo);
        // lee string-array del string.xml
        String[] origenes = getResources().getStringArray(R.array.origenes);

        //cierra teclado virtual
        try{
            View v = this.getCurrentFocus();
            v.clearFocus();
            if(v != null){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        }catch (Exception e){}

        try{
            //define mínimo stock para compra
            int m;
            if(minimo.length()==0){
                m = 0;
            }else{
                m = Integer.parseInt(minimo.getText().toString());
            }
            //
            m++;
            final ListView lista = (ListView)findViewById(R.id.ListView_lista);
            AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                    (this,"despensa",null,1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            //Cursor filas = bd.rawQuery("select serie, nombre, valor, stock, " +
            //        "origen from articulos",null);
            Cursor filas = bd.rawQuery("select serie, nombre, valor, stock, " +
                    "origen from articulos where stock <" + m, null);

            int index = filas.getCount()+1;
            if(index > 1){
                // luego de leer los articulos y dejarlos en un cursor, se agregan a un array
                String articulosArray[]=new String[index];
                String articulosString="";
                String linea = "";

                int i = 0, c, p;
                int totalizar = 0;
                while(filas.moveToNext()){
                    c = filas.getInt(3);
                    if(c<m){
                        p = filas.getInt(2);
                        linea = "> " + c + ": " + filas.getString(1) + "---> $ " + p+"("+origenes[filas.getInt(4)]+")";

                        if(opcion){
                            articulosArray[i] = linea;
                        }else{
                            articulosString += linea+"\n";
                        }
                        totalizar = totalizar + ( c * p);
                        i++;
                    }
                }

                linea="Total a comprar -----> $ " + totalizar;
                if(opcion) {
                    articulosArray[i] = linea;
                }else{
                    articulosString += linea+"\n";
                }

                if(opcion){
                    // una vez cargado el array con los articulos, se envia a la ListView

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_list_item_1, articulosArray);
                    lista.setAdapter(adapter);
                    // accion sobre click en la lista

                    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                         int item = position;
                                                         String itemval = (String) lista.getItemAtPosition(position);

                                                     }
                                                 }

                    );
                }else{
                    // una vez cargado el string de articulos, se envia por whatsapp
                    try{
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, articulosString);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);

                    }catch (Exception e){
                        Toast.makeText(this, "no tiene instalado Whatsapp", Toast.LENGTH_LONG).show();

                    }
                }


            }else {
                Toast.makeText(this, "no hay datos que mostrar", Toast.LENGTH_LONG).show();
            }
            filas.close();
            bd.close();
        }catch(Exception e){
            Toast.makeText(this, "error:"+e, Toast.LENGTH_LONG).show();
        }
    }

/*
    public void cerrar(View view){
        finish();
    }
*/

}
