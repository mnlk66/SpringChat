package com.web_service.springchat.model.utilisateurDB;

import com.web_service.springchat.model.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtilisateurDAO  extends DAO<Utilisateur> {
    public UtilisateurDAO(Connection c) {
        super(c);
    }

    @Override
    public Utilisateur get(int id) {
        Utilisateur u = null;
        try {
            String sql = "SELECT * FROM utilisateur WHERE idUser="+id+";";
            ResultSet rslt = connect.createStatement().executeQuery(sql);
            if (rslt.next()) {
                u = new Utilisateur();
                u.setPrenom(rslt.getString("prenom"));
                u.setNom(rslt.getString("nom"));
                u.setMail(rslt.getString("mail"));
                u.setId(rslt.getInt("idUser"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }

    public Utilisateur find(String mail) {
        Utilisateur u = null;
        try {
            String sql = "SELECT * FROM utilisateur WHERE mail='"+mail+"';";
            ResultSet rslt = connect.createStatement().executeQuery(sql);
            if (rslt.next()) {
                u = new Utilisateur();
                u.setPrenom(rslt.getString("prenom"));
                u.setNom(rslt.getString("nom"));
                u.setMail(rslt.getString("mail"));
                u.setId(rslt.getInt("idUser"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }

    public Utilisateur auth(String mail, String password) {
        Utilisateur u = null;
        try {
            String sql = "SELECT * FROM utilisateur WHERE mail='"+mail+"' and password='"+password+"';";
            ResultSet rslt = connect.createStatement().executeQuery(sql);
            if (rslt.next()) {
                u = new Utilisateur();
                u.setPrenom(rslt.getString("prenom"));
                u.setNom(rslt.getString("nom"));
                u.setMail(rslt.getString("mail"));
                u.setId(rslt.getInt("idUser"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }

    @Override
    public ArrayList<Utilisateur> getAll(int id) {
        return null;
    }

    @Override
    public int create(Utilisateur Obj) {
        try {
            String sql = "SELECT * FROM utilisateur WHERE  mail='"+Obj.getMail()+"';";
            ResultSet rslt = connect.createStatement().executeQuery(sql);
            if(rslt.next()) return 0;
            else {
                String sql2 = "INSERT INTO utilisateur(nom, prenom, mail, password) VALUES('"+Obj.getNom()+"','"+Obj.getPrenom()+"','"+Obj.getMail()+"','"+Obj.getPassword()+"');";
                return connect.createStatement().executeUpdate(sql2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Utilisateur Obj) {
        try {
            String sql = "UPDATE utilisateur SET password='"+Obj.getPassword()+"' WHERE idUser="+Obj.getId()+";";
            return connect.createStatement().executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(int id) {
        try {
            String sql = "DELETE FROM utilisateur WHERE idUser="+id+";";
            return connect.createStatement().executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }
}
