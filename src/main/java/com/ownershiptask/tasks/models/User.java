package com.ownershiptask.tasks.models;
import com.ownershiptask.tasks.services.AccessService;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class User {
    private final String id;
    private String name;
    private boolean isActive;
    private List<File> ownedFilesList = new ArrayList<>();
    private AccessService accessService;

    public void takeOwnershipFiles(File...files){
        accessService.addFilesAccess(this,files);
    }

    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }

    public boolean isActive() {
        return isActive;
    }

    public void addToOwnedFilesList(File file){
        ownedFilesList.add(file);
    }

    public void removeFromOwnedFilesList(File file){
        ownedFilesList.remove(file);
    }

    public List<File> getOwnedFilesList(){
        return ownedFilesList;
    }

    public int getCountOwnedFiles(){
        return ownedFilesList.size();
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
