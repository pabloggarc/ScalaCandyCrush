<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PL3 - PAP</title>
        <style>
        body { 
            font-family: 'Calibri'; 
            font-size: 16px; 
            line-height: 1.5; 
            margin: 0; 
            padding: 0;
        }
        h1, h2, h3 {
            text-align: center;
        }
        h3 {
            font-size: 13px; 
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #DC322F;
            color: white;
        }
	</style>
    </head>
    <body>
        <h1><u>LEADERBOARD Scala Candy Crush</u></h1>
        <h2>¡Intenta superar el récord!</h2>
        <h3>
            <?php 
                date_default_timezone_set('Europe/Madrid');
                echo "Datos consultados a " . date('H:i:s, d/m/Y ', time());
            ?>
        </h3>
        <?php
            include_once("puntuaciones.php"); 

            if(isset($_GET["sort"]) && $_GET["sort"] == 0){
                echo '<a href="index.php?sort=1"><button type="button">Ordenar por puntuación</button></a>';
                $sort = 1; 
            }
            else{
                echo '<a href="index.php?sort=0"><button type="button">Ordenar por fecha</button></a>'; 
                $sort = 0; 
            }
            echo '<div style="height: 20px;"></div>'; 

            $pg = new Pg(); 
            $c = $pg -> conectar();  
            $pg -> consultar($c, $sort); 
        ?>
    </body>
</html>
