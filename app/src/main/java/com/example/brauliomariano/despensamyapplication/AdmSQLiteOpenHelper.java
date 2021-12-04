package com.example.brauliomariano.despensamyapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdmSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdmSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE articulos (serie TEXT NOT NULL, nombre TEXT NOT NULL, valor INTEGER NOT NULL, " +
                "fecha TEXT NOT NULL, categoria BYTE NOT NULL, origen BYTE NOT NULL, subido BYTE NOT NULL, " +
                "stock INTEGER NOT NULL, run TEXT NOT NULL)");
        db.execSQL("CREATE TABLE categoria (id BYTE PRIMARY KEY, nombre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE origen (id BYTE PRIMARY KEY, nombre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE carrito (cantidad INTEGER, nombre TEXT, valor INTEGER)");
        db.execSQL("CREATE TABLE listadecompra (cantidad INTEGER, nombre TEXT)");

        db.execSQL("CREATE TABLE usuario (run TEXT NOT NULL, nombre TEXT NOT NULL, " +
                "comuna BYTE NOT NULL, " +
                "ciudad BYTE NOT NULL, edad BYTE NOT NULL," +
                "email TEXT NOT NULL, password TEXT NOT NULL)");

        db.execSQL("CREATE TABLE comuna (id INTEGER PRIMARY KEY, nombre TEXT NOT NULL)");
        db.execSQL("CREATE TABLE ciudad (id INTEGER PRIMARY KEY, nombre TEXT NOT NULL)");


        //crear articulo de prueba

        db.execSQL("INSERT INTO articulos VALUES ('7809558102662', 'endulzante Daily', 3000, '2021-08-15', 5, 5, 0, 1, '10412135-7')");

        //poblar categorias

        db.execSQL("INSERT INTO categoria VALUES (1,'Bebestibles')");
        db.execSQL("INSERT INTO categoria VALUES (2,'Conservas')");
        db.execSQL("INSERT INTO categoria VALUES (3,'Embutidos')");
        db.execSQL("INSERT INTO categoria VALUES (4,'Lacteos')");
        db.execSQL("INSERT INTO categoria VALUES (5,'No peresibles')");
        db.execSQL("INSERT INTO categoria VALUES (6,'Peresibles')");

        //poblar origen

        db.execSQL("INSERT INTO origen VALUES (1,'Lider')");
        db.execSQL("INSERT INTO origen VALUES (2,'Jumbo')");
        db.execSQL("INSERT INTO origen VALUES (3,'Santa Isabel')");
        db.execSQL("INSERT INTO origen VALUES (4,'Unimarc')");
        db.execSQL("INSERT INTO origen VALUES (5,'Tottus')");
        db.execSQL("INSERT INTO origen VALUES (6,'Monserrat')");
        db.execSQL("INSERT INTO origen VALUES (7,'Mayorista')");
        db.execSQL("INSERT INTO origen VALUES (8,'aCuenta')");
        db.execSQL("INSERT INTO origen VALUES (9,'Ekono')");

        //poblar comunas

        db.execSQL("INSERT INTO comuna VALUES (1,'Cerrillos')");
        db.execSQL("INSERT INTO comuna VALUES (2,'La Reina')");
        db.execSQL("INSERT INTO comuna VALUES (3,'Pudahuel')");
        db.execSQL("INSERT INTO comuna VALUES (4,'Cerro Navia')");
        db.execSQL("INSERT INTO comuna VALUES (5,'Las Condes')");
        db.execSQL("INSERT INTO comuna VALUES (6,'Quilicura')");
        db.execSQL("INSERT INTO comuna VALUES (7,'Conchalí')");
        db.execSQL("INSERT INTO comuna VALUES (8,'Lo Barnechea')");
        db.execSQL("INSERT INTO comuna VALUES (9,'Quinta Normal')");
        db.execSQL("INSERT INTO comuna VALUES (10,'El Bosque')");
        db.execSQL("INSERT INTO comuna VALUES (11,'Lo Espejo')");
        db.execSQL("INSERT INTO comuna VALUES (12,'Recoleta')");
        db.execSQL("INSERT INTO comuna VALUES (13,'Estación Central')");
        db.execSQL("INSERT INTO comuna VALUES (14,'Lo Prado')");
        db.execSQL("INSERT INTO comuna VALUES (15,'Renca')");
        db.execSQL("INSERT INTO comuna VALUES (16,'Huechuraba')");
        db.execSQL("INSERT INTO comuna VALUES (17,'Macul')");
        db.execSQL("INSERT INTO comuna VALUES (18,'San Joaquín')");
        db.execSQL("INSERT INTO comuna VALUES (19,'Independencia')");
        db.execSQL("INSERT INTO comuna VALUES (20,'Maipú')");
        db.execSQL("INSERT INTO comuna VALUES (21,'San Miguel')");
        db.execSQL("INSERT INTO comuna VALUES (22,'La Cisterna')");
        db.execSQL("INSERT INTO comuna VALUES (23,'Ñunoa')");
        db.execSQL("INSERT INTO comuna VALUES (24,'San Ramón')");
        db.execSQL("INSERT INTO comuna VALUES (25,'La Florida')");
        db.execSQL("INSERT INTO comuna VALUES (26,'Pedro Aguirre Cerda')");
        db.execSQL("INSERT INTO comuna VALUES (27,'Santiago')");
        db.execSQL("INSERT INTO comuna VALUES (28,'La Granja')");
        db.execSQL("INSERT INTO comuna VALUES (29,'Peñalolén')");
        db.execSQL("INSERT INTO comuna VALUES (30,'Vitacura')");
        db.execSQL("INSERT INTO comuna VALUES (31,'La Pintana')");
        db.execSQL("INSERT INTO comuna VALUES (32,'Providencia')");
        db.execSQL("INSERT INTO comuna VALUES (33,'Padre Hurtado')");
        db.execSQL("INSERT INTO comuna VALUES (34,'San Bernardo')");
        db.execSQL("INSERT INTO comuna VALUES (35,'Puente Alto')");
        db.execSQL("INSERT INTO comuna VALUES (36,'Pirque')");
        db.execSQL("INSERT INTO comuna VALUES (37,'San José de Maipo')");

        //poblar ciudad

        db.execSQL("INSERT INTO ciudad VALUES (1,'Santiago')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS articulos");
        db.execSQL("DROP TABLE IF EXISTS carrito");
        db.execSQL("DROP TABLE IF EXISTS listadecompra");
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS origen");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS comuna");
        db.execSQL("DROP TABLE IF EXISTS ciudad");
        onCreate(db);
    }

}
