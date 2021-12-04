<?php   

require 'config/database.php';
header('Access-Control-Allow-Origin:*');

$db = new Database();
$con = $db->conectar();


$query = $con->query("SELECT * FROM articulos AS a INNER JOIN origen AS o ON a.origen=o.id INNER JOIN categoria AS c ON a.categoria=c.id GROUP BY origen ORDER BY fecha ASC");

$query->execute();

$resultado = $query->fetchAll(PDO::FETCH_ASSOC);
$filas = $query->rowCount();
$totalfilas = $filas;
$filasxpagina = 8;
$paginas = ceil($filas / $filasxpagina);

if(!$_GET || $_GET['pagina']>$filasxpagina || $_GET['pagina']<1){
    header('location:fxorigen.php?pagina=1');
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

    $query = $con->query("SELECT COUNT(a.serie) AS registros, SUM(a.valor) AS ventas, o.nombre AS nom_origen,run FROM articulos AS a INNER JOIN origen AS o ON a.origen=o.id INNER JOIN categoria AS c ON a.categoria=c.id GROUP BY origen ORDER BY fecha ASC LIMIT $inicio, $filasxpagina");
    
    $query->execute();
    $filas = $query->rowCount();
    $resultado = $query->fetchAll(PDO::FETCH_ASSOC);

?>

    <main class="container">
        <div class="row">
            <div class="col">
                <h4>Productos MI DESPENSA</h4>
                
                <a href="index.php" class="btn btn-primary float-right">Listado General</a>
                <a href="fxorigenxcomuna.php" class="btn btn-primary float-right">Comuna del Usuario</a>
                
            </div>
            <h3 class="col">Origen de los Artículos Comprados</h3>
        </div>
        <div class="row">
            <div class="col py-3">
                <table class="table table-border">
                    <thead>
                        <tr align="center">
                            <th>Registros</th>
                            <th>Origen</th> 
                            <th>Ventas</th> 
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                            foreach($resultado AS $row):
                        ?>       
                            <tr align="center">
                                <td><?php echo $row['registros']; ?></td>
                                <td><?php echo $row['nom_origen']; ?></td>
                                <td><?php echo $row['ventas']; ?></td>
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

                        <li class="page-item <?php echo $_GET['pagina']==1 ?'disabled':'enable' ?>"><a class="page-link" href="fxorigen.php?pagina=<?php echo $_GET['pagina']-1?>">Previa</a></li>

                        <?php                
                        for($i=0; $i<$paginas; $i++): ?>
                            <li class="page-item <?php echo $_GET['pagina']==$i+1 ?'active': '' ?> "><a class="page-link" href="fxorigen.php?pagina=<?php echo $i+1 ?>"> <?php echo $i+1 ?>  
                                </a>
                            </li>
                        <?php 
                        endfor ?>    

                        <li class="page-item <?php echo $_GET['pagina']==$paginas ?'disabled':'enable' ?>"><a class="page-link" href="fxorigen.php?pagina=<?php echo $_GET['pagina']+1?>">Siguiente</a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </main>


</body>

</html>