package com.web_service.springchat.model.messageDB;

import com.web_service.springchat.model.DAO;
import com.web_service.springchat.model.utilisateurDB.Utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageDAO  extends DAO<Message> {
    public MessageDAO(Connection c) {
        super(c);
    }

    @Override
    public Message get(int id) {
        Message m = new Message();
        try {
            String sql = "SELECT m.*, u.nom, u.prenom FROM messages m JOIN utilisateur u ON u.idUser=m.id_sender WHERE id="+id+";";
            ResultSet rslt = connect.createStatement().executeQuery(sql);
            if (rslt.next()){
                Utilisateur u = new Utilisateur();
                m.setId(rslt.getInt("id"));
                m.setMessage(rslt.getString("message"));
                m.setEtat(rslt.getInt("etat"));
                m.setDate_save(rslt.getDate("date_save"));
                m.setDate_mode(rslt.getDate("date_mode"));
                m.setDate_lecture(rslt.getDate("date_lecture"));

                u.setId(rslt.getInt("id_sender"));
                u.setNom(rslt.getString("nom"));
                u.setPrenom(rslt.getString("prenom"));
                m.setUtilisateur(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return m;
    }

    @Override
    public ArrayList<Message> getAll(int id) {
        ArrayList<Message> list = new ArrayList<>();
        try {
            String sql = "SELECT m.*, u.nom, u.prenom FROM messages m JOIN utilisateur u ON u.idUser=m.id_sender WHERE id_discussion="+id+" ORDER BY date_save;";
            ResultSet rslt = connect.createStatement().executeQuery(sql);
            while (rslt.next()){
                Message m = new Message();
                Utilisateur u = new Utilisateur();
                m.setId(rslt.getInt("id"));
                m.setId_discussion(rslt.getInt("id_discussion"));
                m.setMessage(rslt.getString("message"));
                m.setEtat(rslt.getInt("etat"));
                m.setDate_save(rslt.getDate("date_save"));
                m.setDate_mode(rslt.getDate("date_mode"));
                m.setDate_lecture(rslt.getDate("date_lecture"));

                u.setId(rslt.getInt("id_sender"));
                u.setNom(rslt.getString("nom"));
                u.setPrenom(rslt.getString("prenom"));
                m.setUtilisateur(u);
                list.add(m);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public int create(Message Obj) {
        try {
            String sql = "INSERT INTO messages(id_sender, id_receiver, message, id_discussion) VALUES("+Obj.getId_sender()+","+Obj.getId_receiver()+","+Obj.getMessage()+","+Obj.getId_discussion()+");";
            int rslt = connect.createStatement().executeUpdate(sql);
            return rslt > 0 ? 0 : 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Message Obj) {
        try {
            String sql = "UPDATE messages SET message="+Obj.getMessage()+", date_mode=current_timestamp() WHERE id="+Obj.getId()+";";
            return connect.createStatement().executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(int id) {
        try {
            String sql = "DELETE FROM messages WHERE id="+id+";";
            return connect.createStatement().executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }
}
