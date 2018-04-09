package edu.T10.View;

import edu.T10.Controller.Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Server extends HttpServlet {

	private Controller controller;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGetOrPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGetOrPost(request, response);
    }

    private void doGetOrPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StringBuffer sb = new StringBuffer();

        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        response.getWriter().write("Risk WEB API \n\n");
        return;
    }
}