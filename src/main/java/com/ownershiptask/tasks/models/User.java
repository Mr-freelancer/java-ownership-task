package com.ownershiptask.tasks.models;
import com.ownershiptask.tasks.services.ReminderAccessService;
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
    private ReminderAccessService reminderAccessService;

    public void takeOwnershipFiles(File...files){
        reminderAccessService.addFilesAccess(this,files);
    }

    public void setReminderAccessService(ReminderAccessService reminderAccessService) {
        this.reminderAccessService = reminderAccessService;
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
        reminderAccessService.deleteFilesAccess(this,files);
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", " +
                '}';
    }

}
