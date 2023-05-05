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

    function consultar(PDO $c){
        $query = "SELECT * FROM partidas;"; 
        $stmt = $c -> query($query); 
        $results = $stmt -> fetchAll(PDO::FETCH_ASSOC);
        echo '<table>';
        echo '<tr><th>Nombre</th><th>Puntos</th></tr>';
        foreach ($results as $row) {
            echo '<tr>';
            echo '<td>' . $row['nombre'] . '</td>';
            echo '<td>' . $row['puntos'] . '</td>';
            echo '</tr>';
        }
        echo '</table>';
    }
}
?>