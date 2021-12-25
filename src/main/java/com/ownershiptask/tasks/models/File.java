package com.ownershiptask.tasks.models;
import com.ownershiptask.tasks.services.AccessService;

public class File {
    private User owner;
    private int ID;
    private AccessService accessService;

    public void setOwner(User owner) {
        if(accessService != null)
            // Case for rewrite access, not increment if now owners
            if(this.owner == null)
            accessService.setNumberOwnedFiles(accessService.getNumberOwnedFiles() + 1);
        this.owner = owner;
        owner.addToOwnedFilesList(this);
    }

    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }

    public void setOwner(){
        this.owner = null;
    }

    public User getOwner() {
        return owner;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
