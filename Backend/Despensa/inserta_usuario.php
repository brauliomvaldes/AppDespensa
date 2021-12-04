<?php

include 'conexion.php';
$run=$_POST['run'];
$nombre=$_POST['nombre'];
$comuna=$_POST['comuna'];
$ciudad=$_POST['ciudad'];
$edad=$_POST['edad'];
$email=$_POST['email'];
$password=$_POST['password'];

$consulta="INSERT INTO usuario (run,nombre,comuna,ciudad,edad,email,password)VALUES ('".$run."','".$nombre."','".$comuna."','".$ciudad."','".$edad."','".$email."','".$password."')";

mysqli_query($conexion,$consulta) or die (mysqli_errno($conexion));
mysqli_close($conexion);
 
?>




