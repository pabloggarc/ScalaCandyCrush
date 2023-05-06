# Scala Candy Crush

## Programación funcional
Simulación del juego Candy Crush en Scala siguiendo el paradigma funcional, para la PL2 de la asignatura Paradigmas Avanzados de Programación (UAH)
* Parte 1: el juego original
* Parte 2: el juego original, pero en el modo automático, la máquina siempre elegirá la mejor opción
* Parte 3: mismo juego que en la parte 2, pero interacción desde una GUI en vez de terminal

## Cloud Computing
Con motivo de la tercera práctica, se pidió implementar un sistema de puntos, y almacenarlos en la nube, para posteriormente consultarlos. Para esto se hizo uso de Azure, donde se desplegó: 
* Servidor de PostgreSQL
* API en Python, para recoger los datos y almacenarlos en la BBDD
* [Web](https://puntuacionescandy.azurewebsites.net/index.php) con PHP para realizar un leaderboard de las puntuaciones de los jugadores
