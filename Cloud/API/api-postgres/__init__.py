import logging
import azure.functions as func
from . import conexionBBDD

def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')

    name = req.params.get('nombre')
    score = req.params.get('puntos')

    if name and score:
        ret = conexionBBDD.query(name, score)
        return func.HttpResponse(f"He recibido {name} como nombre, que ha obtenido {score} puntos. \npostgres -> {ret}")
    else:
        return func.HttpResponse("Debe enviarse un nombre de jugador y puntuacion. ", status_code=200)