package com.ownershiptask.tasks.repositories;

import com.ownershiptask.tasks.models.User;

import java.util.HashSet;
import java.util.Set;


public class AccessRepository {
    Set<User> owners = new HashSet<>();
    private int numberOwnedFiles = 0;

    public Set<User> getOwners() {
        return owners;
    }

    public int getNumberOwnedFiles() {
        return numberOwnedFiles;
    }

    public void setOwners(Set<User> owners) {
        this.owners = owners;
    }

    public void setNumberOwnedFiles(int numberOwnedFiles) {
        this.numberOwnedFiles = numberOwnedFiles;
    }

    public void incrementNumberOwnedFiles(){
        this.numberOwnedFiles++;
    }
}
