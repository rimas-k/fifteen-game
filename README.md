# fifteen-game

You can test the REST API either by running integration
tests, or issuing these `curl` commands:

To create a game:

```` 
curl -X POST -H "Content-Type: application/json" -d "{\"userId\":\"user1\",\"gameId\":\"game1\",\"boardSize\":4}" http://localhost:8080/game
````
To fetch a created game:
````
curl -v localhost:8080/game/user1/game1
````
To make a move:
````
curl -v -X POST -H  "Content-Type: application/json" -d "{\"userId\":\"user1\",\"gameId\":\"game10\",\"tileLabel\":\"12\"}" http://localhost:8080/game/move
````

