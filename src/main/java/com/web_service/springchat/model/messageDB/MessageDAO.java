package com.web_service.springchat.model.messageDB;

import com.web_service.springchat.model.DAO;

import java.sql.Connection;
import java.util.ArrayList;

public class MessageDAO  extends DAO<Message> {
    public MessageDAO(Connection c) {
        super(c);
    }

    @Override
    public Message get(int id) {
        return null;
    }

    @Override
    public ArrayList<Message> getAll(int id) {
        return null;
    }

    @Override
    public boolean create(Message Obj) {
        return false;
    }

    @Override
    public boolean update(Message Obj) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
