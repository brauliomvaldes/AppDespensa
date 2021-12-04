<?php

include 'conexion.php';
$serie=$_GET['serie'];

$consulta = "select * from articulo where serie = '$serie'";
$resultado = $conexion -> query($consulta);

while($fila = $resultado -> fetch_array()){
	$producto[] = array_map('utf8_encode', $fila);
}

echo json_encode($producto);
$resultado -> close();
 
?>




