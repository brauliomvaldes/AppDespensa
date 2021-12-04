<?php

include 'conexion.php';
$run=$_GET['run'];

$consulta = "select * from usuario where run = '$run'";
$resultado = $conexion -> query($consulta);

while($fila = $resultado -> fetch_array()){
	$producto[] = array_map('utf8_encode', $fila);
}

echo json_encode($producto);
$resultado -> close();
 
?>




