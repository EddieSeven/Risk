package edu.T10.Controller;

import edu.T10.Model.Game;

import java.io.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Risk", urlPatterns = {"/"}, loadOnStartup = 1)
public class Server extends HttpServlet {

    private Game game;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGetOrPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGetOrPost(request, response);
    }

    private void doGetOrPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");

        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }
            parseCommand(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getOutputStream().flush();
        }

        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        response.getWriter().write("Risk WEB API \n\n");
        return;
    }

    /*
        Json String: request format
     */
    private void parseCommand(String command){
        JsonObject json = Json.createReader(new StringReader(command)).readObject();
        switch (json.getString("Action")) {
            case "Init":
                this.game = new Game(json.getInt("numOfPlayer"));
                this.game.startGame();
                break;
            case "Attack":
                this.game.battleController(
                        json.getInt("territoryID"),
                        json.getInt("targetID"),
                        json.getInt("unitValue"));
                break;
            case "Reinforce":
                this.game.updateTerritory(
                        json.getInt("territoryID"),
                        json.getInt("unitValue"));
                break;
            case "PlayCards":
                this.game.playCards(json.getInt("numOfCards"));
                break;
            case "EndTurn":
                this.game.finishTurn();
                break;
            default:
                break;
        }
    }

}