package com.web_service.springchat.model.groupeDB;

import com.web_service.springchat.model.DAO;

import java.sql.Connection;
import java.util.ArrayList;

public class GroupeDAO  extends DAO<Groupe> {
    public GroupeDAO(Connection c) {
        super(c);
    }

    @Override
    public Groupe get(int id) {
        return null;
    }

    @Override
    public ArrayList<Groupe> getAll(int id) {
        return null;
    }

    @Override
    public boolean create(Groupe Obj) {
        return false;
    }

    @Override
    public boolean update(Groupe Obj) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
