//package edu.T10;
//
//import edu.T10.Model.Game;
//
//import java.io.*;
//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
//import javax.json.stream.JsonParsingException;
//import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//
//@WebServlet(name = "Server", urlPatterns = {"/"}, loadOnStartup = 1)
//public class Server extends HttpServlet {
//
//    private Game game;
//
//    @Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
////        doGetOrPost(request, response);
//    }
//
//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
////        doGetOrPost(request, response);
//    }
//
//    private void doGetOrPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        response.setContentType("application/json");
//
//        try {
//            StringBuilder sb = new StringBuilder();
//            String s;
//            while ((s = request.getReader().readLine()) != null) {
//                sb.append(s);
//            }
//            JsonReader jsonReader = Json.createReader(new StringReader(sb.toString()));
//            try{
//                JsonObject jsonObj = jsonReader.readObject();
//                parseCommand(jsonObj);
//            } catch (JsonParsingException jpe) {
//                response.getWriter().write("Not a valid json query \n\n");
//
//                response.getWriter().write("Json Query Received \n");
//                response.getWriter().write(sb.toString() + "\n\n");
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            response.getOutputStream().flush();
//        }
//
//        response.setContentType("text/x-json;charset=UTF-8");
//        response.setHeader("Cache-Control", "no-cache");
//
//        response.getWriter().write("Server WEB API \n\n");
//        return;
//    }
//
//    /*
//        Json String: request format
//     */
//    private void parseCommand(JsonObject json){
//        switch (json.getString("Action")) {
//            case "Init":
//                JsonArray jarr = json.getJsonArray("names");
//                String[] names = new String[jarr.size()];
//                for (int i = 0; i < jarr.size(); i++) {
//                    names[i] = jarr.getJsonObject(i).toString();
//                }
//                this.game = new Game(names);
//                this.game.startGame();
//                break;
//            case "Attack":
//                this.game.conductInvasion(
//                        json.getInt("territoryID"),
//                        json.getInt("targetID"),
//                        json.getInt("unitValue"),
//                        json.getInt("attackerDice"),
//                        json.getInt("defenderDice"));
//                break;
//            case "Reinforce":
//                this.game.conductReinforcement(
//                        json.getInt("territoryID"),
//                        json.getInt("unitValue"));
//                break;
//            case "PlayCards":
//                this.game.playCards(json.getInt("numOfCards"));
//                break;
//            case "EndTurn":
//                boolean endGame = this.game.finishTurn();
//                break;
//            default:
//                break;
//        }
//    }
//
//}