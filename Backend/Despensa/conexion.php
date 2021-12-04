<?php
$hostname='localhost';
$database='despensa';
$username='root';
$password='';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
	echo "lo sentimos, el sitio web está experimentando problemas";
}
?>
