package com.web_service.springchat.model.messageDB;

import java.sql.Date;

public class Message {
    private int id, id_sender, id_receiver, etat;
    private String message;
    private Date date_save, date_mode, date_lecture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_sender() {
        return id_sender;
    }

    public void setId_sender(int id_sender) {
        this.id_sender = id_sender;
    }

    public int getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(int id_receiver) {
        this.id_receiver = id_receiver;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate_save() {
        return date_save;
    }

    public void setDate_save(Date date_save) {
        this.date_save = date_save;
    }

    public Date getDate_mode() {
        return date_mode;
    }

    public void setDate_mode(Date date_mode) {
        this.date_mode = date_mode;
    }

    public Date getDate_lecture() {
        return date_lecture;
    }

    public void setDate_lecture(Date date_lecture) {
        this.date_lecture = date_lecture;
    }
}
