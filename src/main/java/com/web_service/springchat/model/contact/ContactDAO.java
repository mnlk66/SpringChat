package com.web_service.springchat.model.contact;

import com.web_service.springchat.model.DAO;

import java.sql.Connection;
import java.util.ArrayList;

public class ContactDAO extends DAO<Contact> {

    public ContactDAO(Connection c) {
        super(c);
    }

    @Override
    public Contact get(int id) {
        return null;
    }

    @Override
    public ArrayList<Contact> getAll(int id) {
        return null;
    }

    @Override
    public boolean create(Contact Obj) {
        return false;
    }

    @Override
    public boolean update(Contact Obj) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
