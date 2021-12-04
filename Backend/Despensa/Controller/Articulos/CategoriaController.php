<?php 
include '../../Model/Articulos.php';
$op=$_POST["op"];


if($op==1){
	$run=$_POST["run"];
	if($run > 0){
		$articulos=new Articulos();
		$articulos->run=$run;
		$data=$articulos->TraerPorRun();

		print('<table><tr><th>serie</th><th>nombre</th><th>valor</th><th>fecha</th><th>categoria</th><th>origen</th><th>run</th></tr><tr>');
		foreach($data as $row) {
			print('<tr><td>'.$row["serie"].'</td><td>'.$row["nombre"].'</td><td>'.$row["valor"].'</td><td>'.$row["fecha"].'</td><td>'.$row["categoria"].'</td><td>'.$row["origen"].'</td><td>'.$row["run"].'</td></tr>');
		}
		header("Location: /Despensa/View/Articulos/IndexArticulos.php?n=$campo&r=$run");
    }
}



if($op==2){
	$articulos= new Articulos();
	$data=$articulos->Traer();
	//var_dump($data);
	print('<table><tr><th>serie</th><th>nombre</th><th>valor</th><th>fecha</th><th>categoria</th><th>origen</th><th>run</th></tr><tr>');
	foreach($data as $row) {
		print('<tr><td>'.$row["serie"].'</td><td>'.$row["nombre"].'</td><td>'.$row["valor"].'</td><td>'.$row["fecha"].'</td><td>'.$row["categoria"].'</td><td>'.$row["origen"].'</td><td>'.$row["run"].'</td></tr>');
	}
	print('</table>');
    
    print('
   	<form method="post" action="../../View/Articulos/IndexArticulos.php">
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
	$serie=$_POST["serie"];
	if($serie != ""){
		$articulos=new Articulos();
		$articulos->serie=$serie;
		$nombre=$articulos->TraerUno();
		//var_dump($nombre);
		foreach($nombre as $row) {
			$campo=$row["nombre"];
			echo $campo;
		}
	}
	header("Location: /Despensa/View/Articulos/IndexArticulos.php?n=$campo&s=$serie&r=$");

}


/*
$data=$alu->Traer();
var_dump($data);
print json_encode($data);
foreach($data as $row) {
	print($row["id"]." ".$row["nombre"])."<br>";
}
//$alu::Insert();
*/

?>