var wsUri = "ws://localhost:8025/websockets/game";
var websocket = new WebSocket(wsUri);

websocket.onopen = function(event) {
	  console.log( 'Connected to: ' + event.currentTarget.url);

var result = websocket.send("this is a test");

console.log("completed the send request, result was " + result);
};