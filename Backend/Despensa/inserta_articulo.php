<?php

include 'conexion.php';
$serie=$_POST['serie'];
$nombre=$_POST['nombre'];
$valor=$_POST['valor'];
$fecha=$_POST['fecha'];
$categoria=$_POST['categoria'];
$origen=$_POST['origen'];
$run=$_POST['run'];

$consulta="INSERT INTO articulos (serie,nombre,valor,fecha,categoria,origen,run) VALUES ('".$serie."','".$nombre."','".$valor."','".$fecha."','".$categoria."','".$origen."','".$run."')";

mysqli_query($conexion,$consulta) or die (mysqli_errno($conexion));
mysqli_close($conexion);
 
?>
