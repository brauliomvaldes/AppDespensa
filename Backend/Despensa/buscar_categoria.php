<?php

include 'conexion.php';
$id=$_GET['id'];

$consulta = "select * from categoria where id = '$id'";
$resultado = $conexion -> query($consulta);

while($fila = $resultado -> fetch_array()){
	$producto[] = array_map('utf8_encode', $fila);
}

echo json_encode($producto);
$resultado -> close();
 
?>




