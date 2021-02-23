package com.web_service.springchat.model.utilisateurDB;

import com.web_service.springchat.model.DAO;

import java.sql.Connection;
import java.util.ArrayList;

public class UtilisateurDAO  extends DAO<Utilisateur> {
    public UtilisateurDAO(Connection c) {
        super(c);
    }

    @Override
    public Utilisateur get(int id) {
        return null;
    }

    @Override
    public ArrayList<Utilisateur> getAll(int id) {
        return null;
    }

    @Override
    public boolean create(Utilisateur Obj) {
        return false;
    }

    @Override
    public boolean update(Utilisateur Obj) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
