package com.web_service.springchat.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.web_service.springchat.function.TokenJWT;
import com.web_service.springchat.model.messageDB.Message;
import com.web_service.springchat.model.messageDB.MessageDAO;
import com.web_service.springchat.model.utilisateurDB.Utilisateur;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {
    private MessageDAO messageDAO;

    public boolean StartConnection(){
        boolean isConnect = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DBusername = "userAppent";
            String DBpassword = "userAppent";
//            String DBusername = "root";
//            String DBpassword = "";
            String DBdatabase = "jdbc:mysql://localhost:3306/springchatdb?useTimezone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
            Connection con = DriverManager.getConnection(DBdatabase, DBusername, DBpassword);
            messageDAO = new MessageDAO(con);
            isConnect = true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return isConnect;
    }

    @GetMapping(value = "/message/{id}", produces = "application/json")
    public String getMessage(@PathVariable int id, @RequestHeader String Authorization){
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, Object> map = new HashMap<>();
        if(!StartConnection()) {
            map.put("statut","-1");
            map.put("message","echec connecxion db");
        } else {
            int ownId;
            try {
                String token = Authorization.split("[\\s]+")[1];
                Claims decode = TokenJWT.decode(token);
                Object sub = decode.get("sub");
                Utilisateur us = gson.fromJson((String) sub, Utilisateur.class);
            } catch (Exception e) {
                map.put("statut","-1");
                map.put("message","token as been touched");
                return gson.toJson(map);
            }


            ArrayList<Message> rep = messageDAO.getAll(id);
            if(rep != null) {
                map.put("statut","0");
                map.put("data",rep);
            } else {
                map.put("statut","-1");
                map.put("message","something went wrong");
            }
        }
        return gson.toJson(map);
    }

    @PostMapping(value = "/message", produces = "application/json")
    public String enregistrerMessage(@RequestBody Message m){
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, Object> map = new HashMap<>();
        if(StartConnection() && m.getId_sender() != m.getId_receiver()) {
            int i = messageDAO.create(m);
            if (i == 0) {
                map.put("statut", "1");
                map.put("message", "échec envoie");
            } else if (i == 1) {
                map.put("statut", "0");
                map.put("message", "message envoyé");
            } else {
                map.put("statut", "-1");
                map.put("message", "something went wrong");
            }
        }
        return gson.toJson(map);
    }

    public boolean supprimerMessage(int id){
        if(StartConnection() ) {
            int i = messageDAO.delete(id);
            return i > 0;
        }
        return false;
    }
}
