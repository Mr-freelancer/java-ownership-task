package com.ownershiptask.tasks.models;

public class File {
    private int ID;
    private User owner;

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void deleteOwner(){
        this.owner = null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
