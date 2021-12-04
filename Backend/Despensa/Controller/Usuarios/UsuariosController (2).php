<?php 
include '../../Model/Usuarios.php';
$op=$_POST["op"];

/*
if($op==1){
	print($op);
	$r_usuario= $_POST["run"];
	$n_usuario=$_POST["nombre"];
	if($r_usuario != "" && $n_usuario != ""){
		$usu= new Usuarios();
		$usu->nombre=$n_usuario;
		$usu->run=$r_usuario;
		//var_dump($nombre);
		//var_dump($alu);
		$usu->Insert();
	}
	header("Location: /Despensa/View/Usuarios/IndexUsuarios.php");
}
*/



if($op==2){
	$usu= new Usuarios();
	$data=$usu->Traer();
	//var_dump($data);
	print('<table><tr><th>run</th><th>Nombre</th><th>edad</th><th>direccion</th><th>comuna</th><th>ciudad</th><th>email</th><th>password</th></tr><tr>');
	foreach($data as $row) {
		print('<tr><td>'.$row["run"].'</td><td>'.$row["nombre"].'</td><td>'.$row["edad"].'</td><td>'.$row["direccion"].'</td><td>'.$row["comuna"].'</td><td>'.$row["ciudad"].'</td><td>'.$row["email"].'</td><td>'.$row["password"].'</td>');
	}
	print('</table>');
    
    print('
   	<form method="post" action="../../View/Usuarios/IndexUsuarios.php">
    <button>volver</button>
	</form>
    	');

}


/*
if($op==3){
	print($op);
	$run= $_POST["run"];
	$nombre=$_POST["nombre"];
	if($run > 0 && $nombre!=""){
		$usu= new Usuarios();
		$usu->nombreUsuario=$nombre;
		$usu->run=$run;
		$usu->Update();
	}
	header("Location: /Despensa/View/Usuarios/IndexUsuarios.php");	
}

if($op==4){
	print($op);
	$run= $_POST["run"];
	if($run > 0){
		$usu= new Usuarios();
		$usu->run=$run;
		$usu->Delete();
	}
	header("Location: /Despensa/View/Usuarios/IndexUsuarios.php");
}

*/

if($op==5){
	$run=$_POST["r"];

	if($run != ""){
		$usu=new Usuarios();
		$usu->run=$run;
		$registro=$usu->TraerUno();
		 
		foreach($registro as $row) {
			$campo=$row["nombre"];
			echo $campo;
			echo $run;
		}
	}
	header("Location: /Despensa/View/Usuarios/IndexUsuarios.php?n=$campo&r=$run");

}



/*$data=$alu->Traer();
var_dump($data);
print json_encode($data);
foreach($data as $row) {
	print($row["id"]." ".$row["nombre"])."<br>";
}
//$alu::Insert();
*/
?>