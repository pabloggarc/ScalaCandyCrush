import logging
import azure.functions as func
from . import conexionBBDD

def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')

    name = req.params.get('nombre')
    score = req.params.get('puntos')
    time = req.params.get('duracion')
    date = req.params.get('fecha')

    if name and score and time and date:
        ret = conexionBBDD.query(name, score, time, date)
        return func.HttpResponse(
            f"{name} ha jugado durante {time} segundos, obteniendo una puntuación de {score} a fecha de {date}\n{ret}")
    else:
        return func.HttpResponse("Debe enviarse nombre de jugador, puntuación, tiempo, y fecha", 
                                 status_code = 200)