package com.web_service.springchat.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.web_service.springchat.function.TokenJWT;
import com.web_service.springchat.model.contact.Contact;
import com.web_service.springchat.model.contact.ContactDAO;
import com.web_service.springchat.model.utilisateurDB.Utilisateur;
import com.web_service.springchat.model.utilisateurDB.UtilisateurDAO;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ContactController {
    private ContactDAO contactDAO;
    private UtilisateurDAO utilisateurDAO;
    private int ownId;

    public boolean StartConnection(){
        boolean isConnect = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DBusername = "userAppent";
            String DBpassword = "userAppent";
            String DBdatabase = "jdbc:mysql://localhost:3306/springchatdb?useTimezone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
            Connection con = DriverManager.getConnection(DBdatabase, DBusername, DBpassword);
            contactDAO = new ContactDAO(con);
            utilisateurDAO = new UtilisateurDAO(con);
            isConnect = true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return isConnect;
    }

    @PostMapping(value = "/contact", produces = "application/json")
    public String nouveauContact(@RequestBody Utilisateur utilisateur, @RequestHeader String Authorization){
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String mail = utilisateur.getMail();
        Map<String, Object> map = new HashMap<>();
        if(!StartConnection()) {
            map.put("statut","-1");
            map.put("message","echec connecxion db");
        } else {
            try {
                String token = Authorization.split("[\\s]+")[1];
                Claims decode = TokenJWT.decode(token);
                Object sub = decode.get("sub");
                Utilisateur us = gson.fromJson((String) sub, Utilisateur.class);
                ownId = us.getId();
            } catch (Exception e) {
                map.put("statut","-1");
                map.put("message","token as been touched");
                return gson.toJson(map);
            }

            Utilisateur u = utilisateurDAO.find(mail);
            if (u != null ){
                Contact c = new Contact();
                c.setId_contact(u.getId());
                c.setId_user(ownId);
                int rep = contactDAO.create(c);
                if(rep == 0) {
                    map.put("statut","0");
                    map.put("message","Contact ajouté avec succès");
                } else if(rep == 1){
                    map.put("statut","1");
                    map.put("message","Connexion impossible");
                } else {
                    map.put("statut","-1");
                    map.put("message","something went wrong");
                }
            } else {
                map.put("statut","1");
                map.put("message","Contact non trouvé");
            }
        }
        return gson.toJson(map);
    }

    @DeleteMapping(value = "/contact/{id}", produces = "application/json")
    public String supprimeContact( @PathVariable int id, @RequestHeader String Authorization){
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, Object> map = new HashMap<>();
        if(!StartConnection()) {
            map.put("statut","-1");
            map.put("message","echec connecxion db");
        } else {
            try {
                String token = Authorization.split("[\\s]+")[1];
                Claims decode = TokenJWT.decode(token);
                Object sub = decode.get("sub");
                Utilisateur us = gson.fromJson((String) sub, Utilisateur.class);
                ownId = us.getId();
            } catch (Exception e) {
                map.put("statut","-1");
                map.put("message","token as been touched");
                return gson.toJson(map);
            }


            int rep = contactDAO.delete(ownId, id);
            if(rep == 0) {
                map.put("statut","0");
                map.put("message","Contact supprimé avec succès");
            } else if(rep == 1){
                map.put("statut","1");
                map.put("message","ne figure pas dans vos contact");
            } else {
                map.put("statut","-1");
                map.put("message","something went wrong");
            }
        }
        return gson.toJson(map);
    }
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/contact", produces = "application/json")
    public String getContact( @RequestHeader String Authorization){
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, Object> map = new HashMap<>();
        if(!StartConnection()) {
            map.put("statut","-1");
            map.put("message","echec connecxion db");
        } else {
            try {
                String token = Authorization.split("[\\s]+")[1];
                Claims decode = TokenJWT.decode(token);
                Object sub = decode.get("sub");
                Utilisateur us = gson.fromJson((String) sub, Utilisateur.class);
                ownId = us.getId();
            } catch (Exception e) {
                map.put("statut","-1");
                map.put("message","token as been touched");
                return gson.toJson(map);
            }


            ArrayList<Contact> rep = contactDAO.getAll(ownId);
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
}
