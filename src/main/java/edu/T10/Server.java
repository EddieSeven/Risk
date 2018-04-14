package edu.T10;

import edu.T10.Model.Game;

import java.io.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/risk")
public class Server {

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
}