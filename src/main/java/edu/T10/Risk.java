package edu.T10;

import edu.T10.Model.Game;
import edu.T10.Model.InvasionResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Risk", urlPatterns = {"register"}, loadOnStartup = 1)
public class Risk extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Hello, Risk!!!!");
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name == null)
            name = "World";
        request.setAttribute("user", name);
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }

    public static void main(String[] args) {

        System.out.print("Hello world!");
        Game game = new Game(4);
        game.board.updateTerritoryStrength(3, 5);
        game.board.updateTerritoryStrength(4, 4);
        InvasionResult ir = game.conductInvasion(3, 4,4, 3, 2);
        int az = 65;


   }

}
