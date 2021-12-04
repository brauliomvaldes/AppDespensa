<?php

class Database{

    private $hostname = "localhost";
    private $database = "despensa";
    private $username = "root";
    private $password = "";
    //private $charset = "utf-8";   charset=".$this->charset;
            


    function conectar(){

        try{
            $conexion = "mysql:host=".$this->hostname.";dbname=".$this->database; 
            
            $options = [
                PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                PDO::ATTR_EMULATE_PREPARES => false
            ];

            // conexion a la base de datos
            $pdo = new PDO($conexion, $this->username, $this->password, $options);

            return $pdo;

        }catch(PDOException $e){
            echo 'Error conexion '.$e->getMessage();
            exit;
        }
    }

}