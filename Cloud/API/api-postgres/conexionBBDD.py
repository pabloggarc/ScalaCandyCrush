import psycopg2

def query(nombre, puntos, duracion, fecha): 
    try:
        conn = psycopg2.connect(
            host = "pl3.postgres.database.azure.com",
            database = "candycrush",
            user = "AdminPL3@pl3",
            password = "uah_2023",
            port = "5432"
        )
        cursor = conn.cursor()
        consulta = """ INSERT INTO partidas (nombre, puntos, duracion, fecha) VALUES (%s, %s, %s, %s)"""
        datosConsulta = (nombre, puntos, duracion, fecha)
        cursor.execute(consulta, datosConsulta)
        conn.commit()
    except (Exception, psycopg2.Error) as error:
        return "Error al insertar datos en Postgres"
    finally:
        if (conn):
            cursor.close()
            conn.close()
            return "Datos insertados correctamente!"