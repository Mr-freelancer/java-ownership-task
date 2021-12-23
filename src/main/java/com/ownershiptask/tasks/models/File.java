package com.ownershiptask.tasks.models;

import com.ownershiptask.tasks.services.AccessService;

public class File {
    private User owner;
    private int ID;
    private AccessService accessService;

    public void setOwner(User owner) {
        this.owner = owner;
        accessService.setNumberOwnedFiles(accessService.getNumberOwnedFiles() + 1);
        owner.addToOwnedFilesList(this);
    }

    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }

    public void setOwner(){
        this.owner = null;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void message(){
        System.out.println("I'm a file. My ID is " + this.getID() + " " + this);
    }

    public int getID() {
        return ID;
    }

    public User getOwner() {
        return owner;
    }
}
