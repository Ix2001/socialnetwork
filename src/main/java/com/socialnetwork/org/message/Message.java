package com.socialnetwork.org.message;

import com.socialnetwork.org.User.UserData;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "message_sequence"
    )
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "reciever_id")
    private UserData recieverId;
    @Column(name = "sender_id")
    private UserData senderId;



    public Message() {
    }

    public Message(Long id, String text, Date creationDate, UserData recieverId, UserData senderId) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.recieverId = recieverId;
        this.senderId = senderId;
    }

    public Message(String text, Date creationDate, UserData recieverId, UserData senderId) {
        this.text = text;
        this.creationDate = creationDate;
        this.recieverId = recieverId;
        this.senderId = senderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public UserData getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(UserData recieverId) {
        this.recieverId = recieverId;
    }

    public UserData getSenderId() {
        return senderId;
    }

    public void setSenderId(UserData senderId) {
        this.senderId = senderId;
    }
}
