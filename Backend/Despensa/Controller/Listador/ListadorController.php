<?php 

include '../../Model/Usuarios.php';
include '../../Model/Articulos.php';

$op=$_POST["op"];


if($op==1){
	/*
	$run=$_POST["run"];
	if($run==""){
		*/
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

	/*}else{
			print('
		<h3>Campos no requeridos</h3>
	   	<form method="post" action="../../View/Listador/IndexListador.php">
    	<br>
    	<button> volver </button>
		</form>
    	');

	}
	*/

}

if($op==2){
	/*
	$run=$_POST["run"];
	if($run==""){
		*/
		$run=$_POST["run"];
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
    /*
	}else{
			print('
		<h3>Campos no requeridos</h3>		
	   	<form method="post" action="../../View/Listador/IndexListador.php">
    	<br>
    	<button> volver </button>
		</form>
    	');

	}
	*/

}

if($op==3){
	$run=$_POST["run"];
	if($run!=""){
		$articulos= new Articulos();
		$articulos->run=$run;
		$data=$articulos->TraerPorRun();
	 
		print(' <h1>Listador Artículos por Usuario '.$run.' </h1> ');

		print('<table border="1"><tr><th>serie</th><th>nombre</th><th>valor</th><th>fecha</th><th>categoria</th><th>origen</th><th>run</th></tr><tr>');
	
	    $contador = 0;

		foreach($data as $row) {
				print('<tr><td>'.$row["serie"].'</td><td>'.$row["nombre"].'</td><td>'.$row["valor"].'</td><td>'.$row["fecha"].'</td><td>'.$row["categoria"].'</td><td>'.$row["origen"].'</td><td>'.$row["run"].'</td></tr>');			
				$contador++;
		}

		print('</table>');
    
		if($contador == 0){
			print('<h3>No se hallaron artículos para el RUN solicitado</h3>');
		}

    	print('

	   	<form method="post" action="../../View/Listador/IndexListador.php">
    	<br>
    	<button> volver </button>
		</form>
    	');

	}else{
			print('
		<h3>Se requiere RUN del usuario</h3>
	   	<form method="post" action="../../View/Listador/IndexListador.php">
    	<br>
    	<button> volver </button>
		</form>
    	');

	}

}

if($op==4){
	$categoria=$_POST["categoria"];
	if($categoria!=""){
		$articulos= new Articulos();
		$articulos->categoria=$categoria;
		$data=$articulos->TraerPorCategoria();
	 
		print(' <h1>Listador Artículos por Categoria '.$categoria.' </h1> ');

		print('<table border="1"><tr><th>serie</th><th>nombre</th><th>valor</th><th>fecha</th><th>categoria</th><th>origen</th><th>run</th></tr><tr>');
	
	    $contador = 0;

		foreach($data as $row) {
				print('<tr><td>'.$row["serie"].'</td><td>'.$row["nombre"].'</td><td>'.$row["valor"].'</td><td>'.$row["fecha"].'</td><td>'.$row["categoria"].'</td><td>'.$row["origen"].'</td><td>'.$row["run"].'</td></tr>');			
				$contador++;
		}

		print('</table>');
    
		if($contador == 0){
			print('<h3>No se hallaron artículos para la Categoría solicitado</h3>');
		}

    	print('

	   	<form method="post" action="../../View/Listador/IndexListador.php">
    	<br>
    	<button> volver </button>
		</form>
    	');

	}else{
			print('
		<h3>Se requiere Categoría del artículo</h3>
	   	<form method="post" action="../../View/Listador/IndexListador.php">
    	<br>
    	<button> volver </button>
		</form>
    	');

	}

}

if($op==5){
	$origen=$_POST["origen"];
	if($origen!=""){
		$articulos= new Articulos();
		$articulos->origen=$origen;
		$data=$articulos->TraerPorOrigen();
	 
		print(' <h1>Listador Artículos por Origen '.$origen.' </h1> ');

		print('<table border="1"><tr><th>serie</th><th>nombre</th><th>valor</th><th>fecha</th><th>categoria</th><th>origen</th><th>run</th></tr><tr>');
	
	    $contador = 0;

		foreach($data as $row) {
				print('<tr><td>'.$row["serie"].'</td><td>'.$row["nombre"].'</td><td>'.$row["valor"].'</td><td>'.$row["fecha"].'</td><td>'.$row["categoria"].'</td><td>'.$row["origen"].'</td><td>'.$row["run"].'</td></tr>');			
				$contador++;
		}

		print('</table>');
    
		if($contador == 0){
			print('<h3>No se hallaron artículos para el Origen solicitado</h3>');
		}

    	print('

	   	<form method="post" action="../../View/Listador/IndexListador.php">
    	<br>
    	<button> volver </button>
		</form>
    	');

	}else{
			print('
		<h3>Se requiere Origen del artículo</h3>
	   	<form method="post" action="../../View/Listador/IndexListador.php">
    	<br>
    	<button> volver </button>
		</form>
    	');

	}

}



?>