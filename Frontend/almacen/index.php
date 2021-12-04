<?php   

require 'config/database.php';
header('Access-Control-Allow-Origin:*');

$db = new Database();
$con = $db->conectar();


$query = $con->query("SELECT * FROM articulos AS a INNER JOIN origen AS o ON a.origen=o.id INNER JOIN categoria AS c ON a.categoria=c.id ORDER BY fecha ASC");

$query->execute();

/*
$origen = 5;
$comando = $con->prepare("SELECT serie,nombre,valor,fecha,categoria,origen,run FROM articulos WHERE origen=:su_origen ORDER BY fecha ASC");
$comando->execute(['su_origen'=>$origen]);
*/

$resultado = $query->fetchAll(PDO::FETCH_ASSOC);
$filas = $query->rowCount();
$totalfilas = $filas;
$filasxpagina = 8;
$paginas = ceil($filas / $filasxpagina);

if(!$_GET || $_GET['pagina']>$filasxpagina || $_GET['pagina']<1){
    header('location:index.php?pagina=1');
}

?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="public/css/bootstrap.min.css">
    <script src="public/js/bootstrap.bundle.min.js"></script>
</head>
<body class="py-3">
<?php

    $inicio = $_GET['pagina'] - 1;
    $inicio = $inicio * $filasxpagina;

    $query = $con->query("SELECT serie,a.nombre AS nom_articulo,valor,fecha,c.nombre AS nom_categoria,o.nombre AS nom_origen,run FROM articulos AS a INNER JOIN origen AS o ON a.origen=o.id INNER JOIN categoria AS c ON a.categoria=c.id ORDER BY fecha ASC LIMIT $inicio , $filasxpagina");
    
    $query->execute();
    $filas = $query->rowCount();
    $resultado = $query->fetchAll(PDO::FETCH_ASSOC);

?>

    <main class="container">
        <div class="row">
            <div class="col">
                <h4>Productos MI DESPENSA</h4>
                <a href="fxorigen.php" class="btn btn-primary float-right">Totalizar por Origen</a>
                <a href="fxorigenxcomuna.php" class="btn btn-primary float-right">Comuna del Usuario</a>
            </div>
            <h3 class="col">Listado General de Artículos Registrados</h3>
        </div>
        <div class="row">
            <div class="col py-3">
                <table class="table table-border">
                    <thead>
                        <tr>
                            <th>Serie Producto</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Fecha</th>
                            <th>Categoria</th>
                            <th>Origen</th>
                            <th >RUN Cliente</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                            foreach($resultado AS $row):
                        ?>       
                            <tr>
                                <td><?php echo $row['serie']; ?></td>
                                <td><?php echo $row['nom_articulo']; ?></td>
                                <td><?php echo $row['valor']; ?></td>
                                <td><?php echo $row['fecha']; ?></td>
                                <td><?php echo $row['nom_categoria']; ?></td>
                                <td><?php echo $row['nom_origen']; ?></td>
                                <td align="right"><?php echo $row['run']; ?></td>
                            </tr>
                            
                        <?php 
                            endforeach
                        ?>
                            
                    </tbody>
                    <tr>
                        <td>TOTAL REGISTROS ENCONTRADOS : <?php echo $totalfilas; ?>  
                        </td>
                    </tr>
                </table>
                <nav aria-label="Page navigation example">
                    <ul class="pagination">

                        <li class="page-item <?php echo $_GET['pagina']==1 ?'disabled':'enable' ?>"><a class="page-link" href="index.php?pagina=<?php echo $_GET['pagina']-1?>">Previa</a></li>

                        <?php                
                        for($i=0; $i<$paginas; $i++): ?>
                            <li class="page-item <?php echo $_GET['pagina']==$i+1 ?'active': '' ?> "><a class="page-link" href="index.php?pagina=<?php echo $i+1 ?>"> <?php echo $i+1 ?>  
                                </a>
                            </li>
                        <?php 
                        endfor ?>    

                        <li class="page-item <?php echo $_GET['pagina']==$paginas ?'disabled':'enable' ?>"><a class="page-link" href="index.php?pagina=<?php echo $_GET['pagina']+1?>">Siguiente</a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </main>


</body>

</html>