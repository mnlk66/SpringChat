package com.web_service.springchat.model.contact;

import com.web_service.springchat.model.DAO;
import com.web_service.springchat.model.utilisateurDB.Utilisateur;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ContactDAO extends DAO<Contact> {

    public ContactDAO(Connection c) {
        super(c);
    }

    @Override
    public Contact get(int id) {
        Contact c = new Contact();
        try {
            String sql = "SELECT c.id, c.id_contact, u.nom, u.prenom, u.mail FROM contact c JOIN utilisateur u ON c.id_user=u.idUser WHERE id="+id+";";
            ResultSet rslt = connect.createStatement().executeQuery(sql);
            if (rslt.next()){
                Utilisateur u = new Utilisateur();

                u.setNom(rslt.getString("nom"));
                u.setPrenom(rslt.getString("prenom"));
                u.setMail(rslt.getString("mail"));
                c.setId(rslt.getInt("id"));
                c.setId_contact(rslt.getInt("id_contact"));
                c.setUtilisateur(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return c;
    }

    @Override
    public ArrayList<Contact> getAll(int id) {
        ArrayList<Contact> list = null;
        try {
            String sql = "SELECT c.id, c.id_contact, u.nom, u.prenom, u.mail FROM contact c JOIN utilisateur u ON c.id_contact=u.idUser WHERE id_user="+id+";";
            ResultSet rslt = connect.createStatement().executeQuery(sql);
            list =  new ArrayList<>();
            while (rslt.next()){
                Contact c = new Contact();
                Utilisateur u = new Utilisateur();

                u.setNom(rslt.getString("nom"));
                u.setPrenom(rslt.getString("prenom"));
                u.setMail(rslt.getString("mail"));
                c.setId(rslt.getInt("id"));
                c.setId_contact(rslt.getInt("id_contact"));
                c.setUtilisateur(u);
                list.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public int create(Contact Obj) {
        try {
            String sql_verif = "SELECT * FROM contact WHERE (id_user="+Obj.getId_user()+" AND id_contact="+Obj.getId_contact()+") OR (id_contact="+Obj.getId_user()+" AND id_user="+Obj.getId_contact()+");";
            ResultSet rslt_verif = connect.createStatement().executeQuery(sql_verif);
            if(rslt_verif.next()) return 1;
            String sql = "INSERT INTO contact(id_user, id_contact) VALUES("+Obj.getId_user()+","+Obj.getId_contact()+")";
            String sql_reverse = "INSERT INTO contact(id_user, id_contact) VALUES("+Obj.getId_contact()+","+Obj.getId_user()+")";
            int rslt = connect.createStatement().executeUpdate(sql);
            int rslt_reverse = connect.createStatement().executeUpdate(sql_reverse);
            return rslt > 0 && rslt_reverse > 0 ? 0 : 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Contact Obj) {
        return 0;
    }

    @Override
    public int delete(int id) {
        try {
            String sql = "DELETE FROM contact WHERE id="+id+";";
            return connect.createStatement().executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    public int delete(int id1, int id2) {
        try {
            String sql = "DELETE FROM contact WHERE id_user="+id1+" AND id_contact="+id2+";";
            String sql_reverse = "DELETE FROM contact WHERE id_user="+id2+" AND id_contact="+id1+";";
            int rslt = connect.createStatement().executeUpdate(sql);
            int rslt_reverse = connect.createStatement().executeUpdate(sql_reverse);
            return rslt > 0 && rslt_reverse > 0 ? 0 : 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }
}
