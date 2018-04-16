package edu.T10;

import edu.T10.Model.Board.Territory;
import edu.T10.Model.Game;
import edu.T10.Model.InvasionResult;

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
                JsonArray jsonArray = json.getJsonArray("names");
                String[] names = new String[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    names[i] = jsonArray.getString(i);
                }
                this.game = new Game(names);
                this.game.startGame();

                sendBack2Server(session, "init");
                break;

            case "Attack":
                InvasionResult invasionResult = this.game.conductInvasion(
                        json.getInt("territoryID"),
                        json.getInt("targetID"),
                        json.getInt("unitValue"),
                        json.getInt("attackerDice"),
                        json.getInt("defenderDice"));

                // todo send message to front end
                sendBack2Server(session, "attack");
                String invasionInfo = invasionResult.toString();
                System.out.print(invasionInfo);
                sendBack(session, buildJson(invasionInfo));

                break;
            case "Reinforce":
                this.game.conductReinforcement(
                        json.getInt("territoryID"),
                        json.getInt("unitValue"));
                sendBack2Server(session, "reinforce");
                break;
            case "Fortify":
                this.game.conductFortification(
                        json.getInt("fromTerritoryID"),
                        json.getInt("toTerritoryID"),
                        json.getInt("unitValue"));
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

    private void sendBack2Server(Session session, String action){
        // todo send message to front end
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

    private ArrayList<String> buildTerritoryList(Territory[] territories){
        ArrayList<String> listOfStrings = new ArrayList<>();

        for (Territory territory : territories) {
            listOfStrings.add(territory.toString() + ";");
        }

        return listOfStrings;
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
}