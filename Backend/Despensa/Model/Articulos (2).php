<?php
//include 'Conexion.php';
class Articulos{
	//var $nombreArticulo;
	var $serie;
	var $run;

/*
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

*/
	function Traer(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$data= $conexion->Traer("SELECT * FROM articulos");
		return $data;
	}

	function TraerPorRun(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$data= $conexion->Traer("SELECT * FROM articulos WHERE run='$this->run'");
		return $data;
	}

	function TraerUno(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$data= $conexion->Traer("SELECT nombre FROM articulos WHERE serie='$this->serie'");
		return $data;
	}
}
?>