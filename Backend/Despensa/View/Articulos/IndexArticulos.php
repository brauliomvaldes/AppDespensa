<!DOCTYPE html>
<html>
<head>
	<title>	</title>
</head>
<body>

	<?php
	$narticulo="";
	$sarticulo="";
	$rarticulo="";
	if(!empty($_GET)){
		$narticulo=$_GET["n"];
		$sarticulo=$_GET["s"];
		$rarticulo=$_GET["r"];
		//echo "***** variable nombre";
		//var_dump($nalumno);
		if($rarticulo==""){
			$sarticulo="";
			$narticulo="";
		}
	}

	?>


	<form method="post" action="../../Controller/Articulos/ArticulosController.php">
	<h1>Mantenedor Articulos Despensa</h1>
	Serie Articulo:<input value="<?php echo $sarticulo; ?>" type="text" name="serie">
	Run Usuario:   <input value="<?php echo $rarticulo; ?>" type="text" name="run">
	<br>
	Nombre     :   <input value="<?php echo $narticulo; ?>" type="text" name="nombre">
	<br>
	<button name="op" value="1">consulta por usuario(run)</button>
	<button name="op" value="2">consulta general</button>
	<button name="op" value="3">actualizar</button>
	<button name="op" value="4">borrar</button>
	<button name="op" value="5">buscar</button>
	
	</form>
	

</body>
</html>