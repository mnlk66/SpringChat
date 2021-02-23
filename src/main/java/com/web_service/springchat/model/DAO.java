package com.web_service.springchat.model;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {
    protected Connection connect;

    public DAO(Connection c) {this.connect = c;}

    public abstract T get(int id);
    public abstract ArrayList<T> getAll(int id);
    public abstract boolean create (T Obj);
    public abstract boolean update (T Obj);
    public abstract boolean delete (int id);
}
