package com.ownershiptask.tasks.models;
import com.ownershiptask.tasks.services.AccessService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class User {
    private final int id;
    private String name;
    private boolean isHighPriority;
    private Set<File> ownedFiles = new HashSet<>();
    private AccessService accessService;

    public void takeOwnershipFiles(File...files){
        accessService.addFilesAccess(this,files);
    }

    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }

    public boolean isHighPriority() {
        return isHighPriority;
    }

    public void addToOwnedFilesList(File file){
        ownedFiles.add(file);
    }

    public void removeFromOwnedFilesList(File file){
        ownedFiles.remove(file);
    }

    public Set<File> getOwnedFiles(){
        return ownedFiles;
    }

    public int getCountOwnedFiles(){
        return ownedFiles.size();
    }

    public void deleteFilesOwnership(File...files){
        accessService.deleteFilesAccess(this,files);
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", " +
                '}';
    }

}
