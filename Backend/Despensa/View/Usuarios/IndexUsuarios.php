<!DOCTYPE html>
<html>
<head>
	<title>	</title>
</head>
<body>

	<?php
	$n_usuario="";
	$r_usuario="";
	if(!empty($_GET)){
		$n_usuario=$_GET["n"];
		$r_usuario=$_GET["r"];
		//echo "***** variable nombre";
		//var_dump($nalumno);
		if($r_usuario==""){
			$n_usuario="";
		}
	}

	?>


	<form method="post" action="../../Controller/Usuarios/UsuariosController.php">
	<h1>Mantenedor Usuarios Despensa</h1>
	Run Usuario:<input value="<?php echo $r_usuario; ?>" type="text" name="run"><br>
	Nombre     :<input value="<?php echo $n_usuario; ?>" type="text" name="nombre">
	<br>
	<button name="op" value="1">insertar</button>
	<button name="op" value="2">consultar</button>
	<button name="op" value="3">actualizar</button>
	<button name="op" value="4">borrar</button>
	<button name="op" value="5">buscar</button>
	
	</form>
	

</body>
</html>