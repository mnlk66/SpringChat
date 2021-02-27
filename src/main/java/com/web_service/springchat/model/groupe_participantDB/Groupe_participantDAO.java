package com.web_service.springchat.model.groupe_participantDB;

import com.web_service.springchat.model.DAO;

import java.sql.Connection;
import java.util.ArrayList;

public class Groupe_participantDAO extends DAO<Groupe_participant> {

    public Groupe_participantDAO(Connection c) {
        super(c);
    }

    @Override
    public Groupe_participant get(int id) {
        return null;
    }

    @Override
    public ArrayList<Groupe_participant> getAll(int id) {
        return null;
    }

    @Override
    public int create(Groupe_participant Obj) {
        return 0;
    }

    @Override
    public int update(Groupe_participant Obj) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
