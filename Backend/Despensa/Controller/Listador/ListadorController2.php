<?php 

include '../../Model/Usuarios.php';
include '../../Model/Articulos.php';

$op=$_POST["op"];



if($op==1){
	$usu= new Usuarios();
	$data=$usu->Traer(); 

	print(' <h1>Listador General Usuarios</h1> ');

	print('<table border="1"><tr><th>run</th><th>Nombre</th><th>edad</th><th>direccion</th><th>comuna</th><th>ciudad</th><th>email</th><th>password</th></tr><tr>');
	foreach($data as $row) {
		print('<tr><td>'.$row["run"].'</td><td>'.$row["nombre"].'</td><td>'.$row["edad"].'</td><td>'.$row["direccion"].'</td><td>'.$row["comuna"].'</td><td>'.$row["ciudad"].'</td><td>'.$row["email"].'</td><td>'.$row["password"].'</td>');
	}
	print('</table>');
    
    print('
   	<form method="post" action="../../View/Listador/IndexListador.php">
   	<br>
    <button> volver </button>
	</form>
    	');

}

if($op==2){
	$articulos= new Articulos();
	$data=$articulos->Traer();
	 
	print(' <h1>Listador General Artículos</h1> ');

	print('<table border="1"><tr><th>serie</th><th>nombre</th><th>valor</th><th>fecha</th><th>categoria</th><th>origen</th><th>run</th></tr><tr>');
	foreach($data as $row) {
		print('<tr><td>'.$row["serie"].'</td><td>'.$row["nombre"].'</td><td>'.$row["valor"].'</td><td>'.$row["fecha"].'</td><td>'.$row["categoria"].'</td><td>'.$row["origen"].'</td><td>'.$row["run"].'</td></tr>');
	}
	print('</table>');
    
    print('
   	<form method="post" action="../../View/Listador/IndexListador.php">
    <br>
    <button> volver </button>
	</form>
    	');

}

?>