<?php
class Conexion{
	var $servername = "localhost";
	var $username = "root";
	var $password = "";
	var $conn;

	function Ejecutar($sql){
		print $sql;
		$this->conn->exec($sql);
	}
	function Traer($sql){
		return $this->conn->query($sql)->fetchAll();
	}
	function Conectar(){
		try {
    		$this->conn = new PDO("mysql:host=$this->servername;dbname=despensa", $this->username, $this->password);
    // set the PDO error mode to exception
    		$this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    		echo ""; //Connected successfully"; 
    		}
		catch(PDOException $e)
    		{
    		echo "Connection failed: " . $e->getMessage();
    		}
	}
	function ObtenerConn(){
		return $this->conn;
	}
}



?>