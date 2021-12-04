<?php
include 'Conexion.php';
class Usuarios{
	var $nombreUsuario;
	var $run;
	var $comuna;
	var $ciudad;
	var $edad;
	var $email;
	var $password;

	function Insert(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$conexion->Ejecutar("Insert into usuario(run,nombre) Values('$this->run',$this->nombreAlumno')");

	}
	function Update(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$conexion->Ejecutar("UPDATE usuario set nombre='$this->nombreUsuario' where run= '$this->run'");

	}
	function Delete(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$conexion->Ejecutar("DELETE FROM usuario where run='$this->run'");
	}
	function Traer(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$data= $conexion->Traer("SELECT * FROM usuario");
		return $data;
	}
	function TraerUno(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$data= $conexion->Traer("SELECT * FROM usuario WHERE run='$this->run'");
		return $data;
	}
}
?>