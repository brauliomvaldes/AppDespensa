<?php
//include 'Conexion.php';
class Articulos{
	//var $nombreArticulo;
	var $serie;
	var $run;

	function Traer(){
		$conexion= new Conexion();
		$conexion->Conectar();
		$data= $conexion->Traer("SELECT * FROM articulos");
		return $data;
	}

}
?>