package edu.T10;

import edu.T10.Model.Board.Territory;
import edu.T10.Controller.Game;
import edu.T10.Model.Exceptions.MoveException;
import edu.T10.Model.Exceptions.NumberOfDiceException;
import edu.T10.Model.Exceptions.NumberOfUnitsException;
import edu.T10.Model.Exceptions.PlayerException;
import edu.T10.Model.InvasionResult;
import edu.T10.Model.Player;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import javax.json.stream.JsonParsingException;
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


        try {
            JsonReader jsonReader = Json.createReader(new StringReader(message));
            try{
                JsonObject jsonObj = jsonReader.readObject();
                parseCommand(jsonObj, session);
            } catch (JsonParsingException jpe) {
                jpe.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


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

    private void parseCommand(JsonObject json, Session session) {
        switch (json.getString("Action")) {
            case "Init":
                String jsonString = json.getString("names");

           
                Player[] players = constructPlayers(jsonString);

                this.game = new Game(players);

                this.game.startGame();

                sendInitialSeverData(session);

                sendBack2Server(session, "init");
                break;

            case "Attack":
                InvasionResult invasionResult = null;
                int oldDef = game.getTerritory(json.getInt("targetID")).getStrength();
                try {
                    invasionResult = this.game.conductInvasion(
                            json.getInt("territoryID"),
                            json.getInt("targetID"),
                            json.getInt("unitValue"),
                            json.getInt("attackerDice"),
                            json.getInt("defenderDice"));
                } catch (MoveException | NumberOfUnitsException | NumberOfDiceException e) {
                    System.out.println("Error: " + e.getMessage());
                    sendBackError(session, e.getMessage());
                }

                sendBack2Server(session, "attack");
                String invasionInfo = invasionResult.toString();
                System.out.print(invasionInfo);
                // [DEBUG] //todo delete
                Territory attacker = game.getTerritory(json.getInt("territoryID"));
                Territory defender = game.getTerritory(json.getInt("targetID"));

                System.out.println("\n\n[DEBUG]" +
                        "\nAttacking Army Old Strength: " + json.getInt("unitValue") +
                        "\nInvading Territory New Strength: " + attacker.getStrength() +
                        "\nInvaded Territory Old Strength: " + oldDef +
                        "\nInvaded Territory New Strength: " + defender.getStrength() +
                        "\nOwner of Invaded Territory: " + defender.getOwner() +
                        "\nAttacker ID: " + attacker.getOwner() + "\n\n");
                sendBack(session, buildJson(invasionInfo));

                break;
            case "Reinforce":
                try {
                    this.game.conductReinforcement(
                            json.getInt("territoryID"),
                            json.getInt("unitValue"));
                } catch (NumberOfUnitsException | PlayerException e) {
                    System.out.println("Error: " + e.getMessage());
                    sendBackError(session, e.getMessage());
                }

                sendBack2Server(session, "reinforce");
                break;
            case "Fortify":
                try {
                    this.game.conductFortification(
                            json.getInt("fromTerritoryID"),
                            json.getInt("toTerritoryID"),
                            json.getInt("unitValue"));
                } catch (MoveException | NumberOfUnitsException e) {
                    System.out.println("Error: " + e.getMessage());
                    sendBackError(session, e.getMessage());
                }
                sendBack2Server(session, "fortify");
                break;
            case "PlayCards":
                this.game.playCards(json.getInt("numOfCards"));
                break;
            case "EndTurn":
                boolean endGame = this.game.finishTurn();
                this.game.startTurn();
                String action = endGame ? "endGame" :"continue" ;
                sendBack2Server(session, action);
                break;
            default:
                break;
        }
    }

    private Player[] constructPlayers(String jsonString) {
        String elements[] = jsonString.split(",");
        Player[] players = new Player[elements.length];

        for (int i = 0; i < elements.length; i++){
            String line[] = elements[i].split(" ");
            players[i] = new Player(line[0], colorID(line[1]));
        }

        return players;
    }

    private void sendBack2Server(Session session, String action){
        int playerID = game.getCurrentPlayerID();
        String playerInfo = game.getCurrentPlayer().toString();
        ArrayList playerTerritories = buildTerritoryList(game.getPlayerTerritories(playerID));
        ArrayList allTerritories = buildTerritoryList(game.getAllTerritories());
        JsonObject jobj = Json.createObjectBuilder()
                .add("action", action)
                .add("player", playerInfo)
                .add("territory", concatenateString(playerTerritories))
                .add("board", concatenateString(allTerritories)).build();
        System.out.println("[ServerSide] " + session.getId() + " sends back\n" + jobj.toString());
        sendBack(session, buildJson(jobj.toString()));
    }

    private void sendInitialSeverData(Session session){
        String[] adjList = buildAdjLists(game.getAllTerritories());

        JsonObject jsonObject = Json.createObjectBuilder().add("action", "open")
                .add("adjLists", concatenateAdjList(adjList)).build();

        System.out.println("[ServerSide] " + session.getId() + " sends back\n" + jsonObject.toString());
        sendBack(session, jsonObject);
    }

    private String[] buildAdjLists(Territory territories[]){
        String adjList[] = new String[game.getNumberOfTerritories()];

        for (int i = 0; i < territories.length; i++){
            adjList[i] = territories[i].getId() + territories[i].getAdjTerritoriesString() + ";";
        }

        return adjList;
    }

    private void sendBackError(Session session, String errorMessage){
        JsonObject jsonObject = Json.createObjectBuilder().add("action", "error").add("error", errorMessage).build();

        sendBack(session, jsonObject);
    }

    private ArrayList<String> buildTerritoryList(Territory[] territories){
        ArrayList<String> listOfStrings = new ArrayList<>();

        for (Territory territory : territories) {
            listOfStrings.add(territory.toString() + ";");
        }

        return listOfStrings;
    }

    private void sendBack2Server(Session session){
        int playerID = game.getCurrentPlayerID();
        String playerInfo = game.getCurrentPlayer().toString();
        ArrayList playerTerritories = buildTerritoryList(game.getPlayerTerritories(playerID));
        ArrayList allTerritories = buildTerritoryList(game.getAllTerritories());
        JsonObject jobj = Json.createObjectBuilder()
                .add("player", playerInfo)
                .add("territory", concatenateString(playerTerritories))
                .add("board", concatenateString(allTerritories)).build();
        System.out.println("[ServerSide] " + session.getId() + " sends back\n" + jobj.toString());
        sendBack(session, jobj);
    }



    // Send response message
    private void sendBack(Session session, JsonObject json){
        RemoteEndpoint.Basic remote = session.getBasicRemote();//Get Session remote end
        try{
            System.out.println(json.toString());
            remote.sendText(json.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private JsonObject buildJson(String string){
        JsonReader jsonReader = Json.createReader(new StringReader(string));
        return jsonReader.readObject();
    }

    private String concatenateAdjList(String[] adjList){
        String returnValue = "";

        for (String anAdjList : adjList) {
            returnValue = returnValue + anAdjList;
        }

        return returnValue;
    }

    private String concatenateString(ArrayList array){
        String returnValue = "";

        for (Object anArray : array) {
            returnValue = returnValue + anArray;
        }

        return returnValue;
    }

    private String booleanToString(boolean endGame){
        if (endGame)
            return "1";
        else
            return "0";
    }

    private int colorID(String color){
        switch (color){
            case "GREEN":
                return 0;
            case "YELLOW":
                return 1;
            case "RED":
                return 2;
            case "BLUE":
                return 3;
            case "PINK":
                return 4;
            case "GREY":
                return 5;
            default:
                return -1;
        }
    }
}