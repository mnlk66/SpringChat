package com.web_service.springchat.model.contact;

import com.web_service.springchat.model.messageDB.Message;
import com.web_service.springchat.model.utilisateurDB.Utilisateur;

public class Contact {
    private int id, id_user, id_contact, id_discussion;
    private Utilisateur utilisateur;
    private Message lastMessage;

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_contact() {
        return id_contact;
    }

    public void setId_contact(int id_contact) {
        this.id_contact = id_contact;
    }

    public int getId_discussion() {
        return id_discussion;
    }

    public void setId_discussion(int id_discussion) {
        this.id_discussion = id_discussion;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
