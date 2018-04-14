package edu.T10;

import edu.T10.Model.Game;

import javax.json.*;
import java.io.*;
import javax.json.stream.JsonParsingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/risk")
public class Server {
    private Game game;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        // Get session and WebSocket connection
        System.out.println("[ServerSide] " + session.getId() + " open successfully");
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("[ServerSide] Received message : " + message);
        // Handle new messages
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("[ServerSide] " + session.getId() + " socket closed");
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("[ServerSide] " + session.getId() + " gets error");
        // Do error handling here
    }

    private void parseCommand(JsonObject json) {
        switch (json.getString("Action")) {

            case "Init":
                JsonArray jsonArray = json.getJsonArray("names");
                String[] names = new String[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    names[i] = jsonArray.getJsonObject(i).toString();
                }
                this.game = new Game(names);
                this.game.startGame();


                // return tostring of player, and toString of ALL territories, and tostring of player's territories
                // use session to send back

                break;

            case "Attack":
                this.game.conductInvasion(
                        json.getInt("territoryID"),
                        json.getInt("targetID"),
                        json.getInt("unitValue"),
                        json.getInt("attackerDice"),
                        json.getInt("defenderDice"));
                break;
            case "Reinforce":
                this.game.conductReinforcement(
                        json.getInt("territoryID"),
                        json.getInt("unitValue"));
                break;
            case "PlayCards":
                this.game.playCards(json.getInt("numOfCards"));
                break;
            case "EndTurn":
                boolean endGame = this.game.finishTurn();
                break;
            default:
                break;
        }
    }
}