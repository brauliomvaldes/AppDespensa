package com.example.brauliomariano.despensamyapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TableDynamic {
    private TableLayout tableLayout;
    private Context context;
    private String[]header;
    private ArrayList<String[]>data;
    private TableRow tableRow;
    private TextView txtCell;
    private int indexC;
    private int indexR;
    private boolean colorCelda = false;

    public TableDynamic(TableLayout tableLayout, Context context){
        this.tableLayout=tableLayout;
        this.context=context;
    }
    public void addHeader(String[]header){
        this.header=header;
        createHeader();
    }
    public void addData(ArrayList<String[]> data){
        if(!data.isEmpty()){
            Toast.makeText(context,"datos"+data,Toast.LENGTH_SHORT).show();
            this.data=data;
            createDataTable();
        }
    }

    private void newRow(){
        tableRow=new TableRow(context);
    }
    private void newCell(){
        txtCell=new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(22);

        txtCell.setTextColor(Color.BLUE);
        txtCell.setBackgroundColor(Color.LTGRAY);
    }

    private void createHeader() {
        indexC = 0;
        newRow();
        while (indexC < header.length) {
            newCell();
            txtCell.setText(header[indexC++]);
            tableRow.addView(txtCell, newTableRowParams());
            txtCell.setTextColor(Color.BLUE);
            txtCell.setBackgroundColor(Color.GRAY);
        }
        tableLayout.addView(tableRow);
    }

    private void createDataTable() {
        Toast.makeText(context, "largo:"+header.length,Toast.LENGTH_LONG).show();
        try{

        String info;
        for(indexR=1;indexR<header.length;indexR++) {
            newRow();
            for (indexC = 0; indexC < 5; indexC++) {
                newCell();
                String[]row = data.get(indexR-1);
                info = (indexC < row.length) ? row[indexC] : "";
                txtCell.setText(info);
                tableRow.addView(txtCell, newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
        }catch (Exception e){
            Toast.makeText(context, "error:"+e, Toast.LENGTH_LONG).show();
        }
    }

    //lineas entre celdas

    private TableRow.LayoutParams newTableRowParams(){

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }
}
