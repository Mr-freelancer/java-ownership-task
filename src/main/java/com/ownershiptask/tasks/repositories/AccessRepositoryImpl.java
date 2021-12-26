package com.ownershiptask.tasks.repositories;

import com.ownershiptask.tasks.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class AccessRepositoryImpl {
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

    public void decrementNumberOwnedFiles(){
        this.numberOwnedFiles--;
    }

    public boolean addOwner(User owner) {
        return owners.add(owner);
    }
}
