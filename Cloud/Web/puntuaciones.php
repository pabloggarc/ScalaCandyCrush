<?php
class Pg {
    function conectar(){
        $host = "pl3.postgres.database.azure.com"; 
        $bd = "candycrush"; 
        $usuario = "AdminPL3@pl3"; 
        $pass = "uah_2023";

        try{
            $c = new PDO("pgsql:host = $host; dbname = $bd", $usuario, $pass); 
            return $c; 
        }
        catch(PDOException $ex){
            echo "Error al conectarse con Postgres"; 
        }
    }

    function consultar(PDO $c, int $sort){
        if($sort == 0){
            $query = "SELECT * FROM partidas ORDER BY puntos DESC;";
        }
        else if($sort == 1){
            $query = "SELECT * FROM partidas ORDER BY id DESC;";
        }
        else{
            $query = "SELECT * FROM partidas ORDER BY duracion DESC;";
        }
        $stmt = $c -> query($query); 
        $results = $stmt -> fetchAll(PDO::FETCH_ASSOC);
        echo '<table>';
        echo '<tr><th>Nombre</th><th>Puntos</th><th>Tiempo</th><th>Fecha</th></tr>';
        foreach ($results as $row) {
            echo '<tr>';
            echo '<td>' . $row['nombre'] . '</td>';
            echo '<td>' . $row['puntos'] . '</td>';
            echo '<td>' . $row['duracion'] . '</td>';
            echo '<td>' . $row['fecha'] . '</td>';
            echo '</tr>';
        }
        echo '</table>';
    }
}
?>