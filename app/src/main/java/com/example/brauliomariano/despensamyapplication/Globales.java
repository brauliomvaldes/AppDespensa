package com.example.brauliomariano.despensamyapplication;

//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.HashMap;

public class Globales {
    public static final String miIP = "192.168.18.8";
    //public static final String verdadero = "true";
    public static final String protocolo = "http://";
    public static final String linkInsertarArticulo = "/despensa/inserta_articulo.php";
    public static final String linkBuscarUsuario ="/despensa/buscar_usuario.php?run=";
    public static final String linkInsertaUsuario = "/despensa/inserta_usuario.php";
    //public static ArrayList<HashMap> list;


    private static String nombre;
    private static String run;
    private static String email;
    private static String clave;

    public static String getNombre () {return nombre;}
    public static void setNombre(String _nombre){
        Globales.nombre = _nombre;}

    public static String getRun(){return  run;}
    public static void setRun(String _apellido){
        Globales.run = _apellido;}

    public static String getEmail(){return  email;}
    public static void setEmail(String _email){
        Globales.email = _email;}

    public static String getClave(){return  clave;}
    public static void setClave(String _clave){
        Globales.clave = _clave;}

        /*
    public static String FechaLeidaToString(String fecha){
        fecha=fecha.replace("/", "");
        fecha=fecha.replace("\\", "");
        fecha=fecha.replace("Date", "");
        fecha=fecha.replace(")", "");
        fecha=fecha.replace("(", "");
        Date ms = new Date(Long.parseLong(fecha));
        fecha=ms.toString();
        return fecha;
    }


         */

}
