package com.web_service.springchat.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.web_service.springchat.function.TokenJWT;
import com.web_service.springchat.model.utilisateurDB.Utilisateur;
import com.web_service.springchat.model.utilisateurDB.UtilisateurDAO;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private UtilisateurDAO utilisateurDAO;
    private int ownId;

    public boolean StartConnection() {
        boolean isConnect = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DBusername = "userAppent";
            String DBpassword = "userAppent";
//            String DBusername = "root";
//            String DBpassword = "";
            String DBdatabase = "jdbc:mysql://localhost:3306/springchatdb?useTimezone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
            Connection con = DriverManager.getConnection(DBdatabase, DBusername, DBpassword);
            utilisateurDAO = new UtilisateurDAO(con);
            isConnect = true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return isConnect;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String connexion(@RequestParam("mail") String mail, @RequestParam("password") String password) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, Object> map = new HashMap<>();
        if (!StartConnection()) {
            map.put("statut", "-1");
            map.put("message", "echec connecxion db");
        } else {
            Utilisateur u = utilisateurDAO.auth(mail, password);

            if (u != null) {
                gson.toJson(u);
                String token = TokenJWT.create(gson.toJson(u));
                u.setToken(token);
                map.put("statut", "0");
                map.put("data", u);
            } else {
                map.put("statut", "1");
                map.put("message", "échec connexion");
            }
        }
        return gson.toJson(map);
    }

    @PostMapping(value = "/signup", produces = "application/json")
    public String inscription(@RequestBody Utilisateur u) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, String> map = new HashMap<>();
        if (!StartConnection()) {
            map.put("statut", "-1");
            map.put("message", "echec connecxion db");
        } else {
            int rep = utilisateurDAO.create(u);
            if (rep == 0) {
                map.put("statut", "1");
                map.put("message", "utilisateur déjà existant");
            } else if (rep == 1) {
                map.put("statut", "0");
                map.put("message", "compte créé avec succès");
            } else {
                map.put("statut", "-1");
                map.put("message", "something went wrong");
            }
        }
        return gson.toJson(map);
    }

    @PutMapping(value = "/compte/{id}", produces = "application/json")
    public String modificationCompte(@RequestBody Utilisateur u, @PathVariable int id) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, String> map = new HashMap<>();
        if (!StartConnection()) {
            map.put("statut", "-1");
            map.put("message", "echec connecxion db");
        } else {
            u.setId(id);
            int rep = utilisateurDAO.update(u);
            if (rep == 0) {
                map.put("statut", "1");
                map.put("message", "aucune modification n\'a été réalisé");
            } else if (rep == 1) {
                map.put("statut", "0");
                map.put("message", "données modifié avec succès");
            } else {
                map.put("statut", "-1");
                map.put("message", "something went wrong");
            }
        }
        return gson.toJson(map);
    }

    @DeleteMapping(value = "/compte/{id}", produces = "application/json")
    public String supprimerCompte(@PathVariable int id) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, String> map = new HashMap<>();
        if (!StartConnection()) {
            map.put("statut", "-1");
            map.put("message", "echec connecxion db");
        } else {
            int rep = utilisateurDAO.delete(id);
            if (rep > 0) {
                map.put("statut", "0");
                map.put("message", "compte supprimé avec succès");
            } else {
                map.put("statut", "1");
                map.put("message", "Le compte n\'a pas été supprimé");
            }
        }
        return gson.toJson(map);
    }

    @GetMapping(value = "/compte", produces = "application/json")
    public String infoCompte(@RequestHeader String Authorization) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        Map<String, Object> map = new HashMap<>();
        if (!StartConnection()) {
            map.put("statut", "-1");
            map.put("message", "echec connecxion db");
        } else {
            try {
                String token = Authorization.split("[\\s]+")[1];
                Claims decode = TokenJWT.decode(token);
                Object sub = decode.get("sub");
                Utilisateur us = gson.fromJson((String) sub, Utilisateur.class);
                ownId = us.getId();
            } catch (Exception e) {
                map.put("statut", "-1");
                map.put("message", "token as been touched");
                return gson.toJson(map);
            }
            Utilisateur u = utilisateurDAO.get(ownId);
            if (u != null) {
                gson.toJson(u);
                map.put("statut", "0");
                map.put("data", u);
            } else {
                map.put("statut", "1");
                map.put("message", "échec récupération des données");
            }
        }
        return gson.toJson(map);
    }

}
