package com.chat.prs;

import java.util.Date;

//* SINGLE CHAT CONVERSATION MESSAGE
public class Conversation {
    public static final int STATUS_SENDING = 0;
    public static final int STATUS_SENT = 0;
    public static final int STATUS_FAILED = 0;
    private String message;
    private int status = STATUS_SENT;
    private Date date;
    private String sender;

    public Conversation(String message, Date date, String sender) {
        this.message = message;
        this.date = date;
        this.sender = sender;
    }

    public Conversation() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSent() {
        return MainActivity.user.getUsername().equals(sender);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
