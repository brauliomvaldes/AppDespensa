<?php   

require 'config/database.php';
header('Access-Control-Allow-Origin:*');

$db = new Database();
$con = $db->conectar();


$query = $con->query("SELECT * FROM articulos AS a INNER JOIN usuario AS u ON a.run=u.run INNER JOIN comuna AS c ON u.comuna=c.id INNER JOIN origen AS o ON a.origen=o.id GROUP BY origen ORDER BY comuna ASC");

$query->execute();

$resultado = $query->fetchAll(PDO::FETCH_ASSOC);
$filas = $query->rowCount();
$totalfilas = $filas;
$filasxpagina = 8;
$paginas = ceil($filas / $filasxpagina);

if(!$_GET || $_GET['pagina']>$filasxpagina || $_GET['pagina']<1){
    header('location:fxorigenxcomuna.php?pagina=1');
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
/*
    $query = $con->query("SELECT COUNT(a.serie) AS registros, SUM(a.valor) AS ventas, o.nombre AS nom_origen,run FROM articulos AS a INNER JOIN origen AS o ON a.origen=o.id INNER JOIN categoria AS c ON a.categoria=c.id INNER JOIN usuario AS u ON a.run=u.run GROUP BY u.comuna GROUP BY origen ORDER BY fecha ASC LIMIT $inicio, $filasxpagina");
  */

  $query = $con->query("SELECT COUNT(a.serie) AS registros, SUM(a.valor) AS ventas, c.nombre AS nom_comuna, o.nombre AS nom_origen FROM articulos AS a INNER JOIN usuario AS u ON a.run=u.run INNER JOIN comuna AS c ON u.comuna=c.id INNER JOIN origen AS o ON a.origen=o.id GROUP BY origen ORDER BY comuna ASC LIMIT $inicio, $filasxpagina");



    $query->execute();
    $filas = $query->rowCount();
    $resultado = $query->fetchAll(PDO::FETCH_ASSOC);

?>

    <main class="container">
        <div class="row">
            <div class="col">
                <h4>Productos MI DESPENSA</h4>
                <a href="index.php" class="btn btn-primary float-right">Listado General</a>
                <a href="fxorigen.php" class="btn btn-primary float-right">Totalizar por Origen</a>
            </div>
            <h3 class="col">Listado por Comuna del Usuario</h3>
        </div>
        <div class="row">
            <div class="col py-3">
                <table class="table table-border">
                    <thead>
                        <tr align="center">
                            <th>Comuna</th>
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
                                <td><?php echo $row['nom_comuna']; ?></td>
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

                        <li class="page-item <?php echo $_GET['pagina']==1 ?'disabled':'enable' ?>"><a class="page-link" href="fxorigenxcomuna.php?pagina=<?php echo $_GET['pagina']-1?>">Previa</a></li>

                        <?php                
                        for($i=0; $i<$paginas; $i++): ?>
                            <li class="page-item <?php echo $_GET['pagina']==$i+1 ?'active': '' ?> "><a class="page-link" href="fxorigenxcomuna.php?pagina=<?php echo $i+1 ?>"> <?php echo $i+1 ?>  
                                </a>
                            </li>
                        <?php 
                        endfor ?>    

                        <li class="page-item <?php echo $_GET['pagina']==$paginas ?'disabled':'enable' ?>"><a class="page-link" href="fxorigenxcomuna.php?pagina=<?php echo $_GET['pagina']+1?>">Siguiente</a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </main>


</body>

</html>