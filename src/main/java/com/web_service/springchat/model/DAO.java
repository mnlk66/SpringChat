package com.web_service.springchat.model;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {
    protected Connection connect;

    public DAO(Connection c) {this.connect = c;}

    public abstract T get(int id);
    public abstract ArrayList<T> getAll(int id);
    public abstract int create (T Obj);
    public abstract int update (T Obj);
    public abstract int delete (int id);
}
