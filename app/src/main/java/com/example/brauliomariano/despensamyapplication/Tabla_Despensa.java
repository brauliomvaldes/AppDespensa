package com.example.brauliomariano.despensamyapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

public class Tabla_Despensa extends AppCompatActivity {

    private TableLayout tableLayout;
    private String[]header={" cantidad "," artículo "," precio "," origen " , " serie "};
    private ArrayList<String[]> rows = new ArrayList<>();
    private Spinner sOrigenArticulo;
    private byte origenArticulo = 0;
    private int iCurrentSelection;
    private EditText cantidadStock;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(R.string.t_listadespensa);
        setContentView(R.layout.activity_tabla_despensa);
        leerOrigen();
        listarDespensa(0);

        sOrigenArticulo = (Spinner)findViewById(R.id.spinnerOrigenArticulo);
        iCurrentSelection = sOrigenArticulo.getSelectedItemPosition();

        sOrigenArticulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (iCurrentSelection != i){
                    listarDespensa(i);
                }
                iCurrentSelection = i;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }


    private void listarDespensa(int index){
        tableLayout=(TableLayout)findViewById(R.id.table);

        int count = tableLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = tableLayout.getChildAt(i);
            if (child instanceof TableRow)
                ((ViewGroup) child).removeAllViews();
        }


        TableDynamic tableDynamic = new TableDynamic(tableLayout, getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(getArticulos(index));
    }

    private ArrayList<String[]> getArticulos(int indexOrigen){
        //rows.add(new String[]{"1","articulo1","100","lider"});
        // origen es el supermecado donde se tomo el artículo
        try{
            // lee string-array del string.xml para traducir codigo a nombre
            String[] origenes = getResources().getStringArray(R.array.origenes);

            final ListView lista = (ListView)findViewById(R.id.ListView_lista);
            AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper
                    (this,"despensa",null,1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor filas;
            if (indexOrigen == 0) {
                // buscar todos los origenes
                filas = bd.rawQuery("select serie, nombre, valor, stock, origen " +
                        "from articulos", null);
            }else{
                // buscar segun seleccion del origen
                byte oA = (byte)indexOrigen;

                filas = bd.rawQuery("select serie, nombre, valor, stock, origen " +
                        "from articulos where origen='"+oA+"'", null);
            }
            int numFilas = filas.getCount();
            rows.clear();
            if(numFilas > 0){

                // luego de leer los articulos y dejarlos en un cursor, se agregan a un array

                int c, p;
                int totalizar = 0;
                while(filas.moveToNext()){
                    p = filas.getInt(2);
                    c = filas.getInt(3);

                    totalizar = totalizar + ( c * p);

                    rows.add(new String[]{String.valueOf(filas.getInt(3)),
                            filas.getString(1),
                            String.valueOf(filas.getInt(2)),
                            origenes[filas.getInt(4)-1], filas.getString(0)});
                }

                rows.add(new String[]{"-","Totaliza",String.valueOf(totalizar),"-","-"});

                // una vez cargado el array con los articulos, se envia a la TableLayout

            }else {
                Toast.makeText(this, "no hay datos que mostrar"+filas.getCount(), Toast.LENGTH_LONG).show();
            }
            filas.close();
            bd.close();
        }catch(Exception e){
            Toast.makeText(this, "error:"+e, Toast.LENGTH_LONG).show();
        }
        return rows;
    }

    //poblar origen de articulos

    public void leerOrigen(){
        try {
            AdmSQLiteOpenHelper adm = new AdmSQLiteOpenHelper(getApplicationContext(),
                    "despensa", null, 1);
            SQLiteDatabase db = adm.getWritableDatabase();

            Cursor filas = db.rawQuery("select * from origen", null);
            ArrayList<String> lista = new ArrayList<String>();
            if (filas.getCount() > 0) {
                lista.add("filtrar procedencia");
                while (filas.moveToNext()) {
                    lista.add(filas.getString(1));
                    //Toast.makeText(this,"categoria:"+filas.getString(1),Toast.LENGTH_LONG).show();
                }

                sOrigenArticulo = (Spinner) findViewById(R.id.spinnerOrigenArticulo);
                ArrayAdapter<CharSequence> adaptador =
                        new ArrayAdapter(this, R.layout.spinner_item, lista);
                sOrigenArticulo.setAdapter(adaptador);
            } else {
                Toast.makeText(this, "no hay origen", Toast.LENGTH_LONG).show();
            }
            filas.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(this,"error al buscar origen:"+e,Toast.LENGTH_LONG).show();
        }
    }
}
