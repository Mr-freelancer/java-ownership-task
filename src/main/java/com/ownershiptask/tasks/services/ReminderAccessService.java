package com.ownershiptask.tasks.services;

import com.ownershiptask.tasks.models.File;
import com.ownershiptask.tasks.models.User;
import com.ownershiptask.tasks.repositories.AccessRepositoryImpl;
import com.ownershiptask.tasks.repositories.FileRepositoryImpl;
import com.ownershiptask.tasks.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReminderAccessService implements AccessService{
    private static final int BALANCE_STEP = 1;
    private final FileRepositoryImpl fileRepository;
    private final UserRepositoryImpl userRepository;
    private AccessRepositoryImpl accessRepository;

    public ReminderAccessService(FileRepositoryImpl fileRepository, UserRepositoryImpl userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAccessRepository(AccessRepositoryImpl accessRepositoryImpl) {
        this.accessRepository = accessRepositoryImpl;
    }

    public boolean isFileOwned(File file){
        return file.getOwner() != null;
    }

    public int getNumberAllFiles(){
        return fileRepository.getAll().size();
    }
    public int getNumberAllUsers(){
        return userRepository.getAll().size();
    }

    public int getNumberOwnedFiles() {
        return accessRepository.getNumberOwnedFiles();
    }

    public void setFileOwner(File file, User user){
         // Case for rewrite access. Not increment if no owners
        if(file.getOwner() == null)
            accessRepository.incrementNumberOwnedFiles();
        file.setOwner(user);
        user.addToOwnedFilesList(file);
        accessRepository.addOwner(user);
    }

    public int getCountUserFiles(User user){
        return user.getOwnedFiles().size();
    }

    @Override
    public void addFilesAccess(User user, File... files){
        for (File file : files) {
            if (getNumberPossibleAccess(user) <= 0)
                return;
            if (isFileOwned(file) & !user.isHighPriority()) {
                continue;
            }
            setFileOwner(file,user);
            accessRepository.addOwner(user);
        }
    }

    @Override
    public void deleteFilesAccess(User user, File...files){
        Arrays.stream(files).forEach(file -> {
            if(file.getOwner().equals(user)){
                file.deleteOwner();
                user.removeFromOwnedFilesList(file);
                accessRepository.decrementNumberOwnedFiles();
            }
        });
    }

    @Override
    public int getNumberPossibleAccess(User user){
        int unOwnedFiles = getNumberAllFiles() - getNumberOwnedFiles();
        int relevant = getNumberAllFiles() / getNumberAllUsers();
        int disBalanceFiles = getNumberAllFiles() % getNumberAllUsers();
        int leftFiles = Math.max((relevant - getCountUserFiles(user)), 0);
        float countIndex = (getNumberAllFiles() % getNumberAllUsers()) / (float) getNumberAllUsers();

        if(getNumberAllUsers() != 0){
            if (countIndex >= 0.5){
                if( getNumberOwnedFiles() + 1 <= disBalanceFiles * (relevant + 1 )  && getCountUserFiles(user) < BALANCE_STEP + 1 ){
                    return relevant - getCountUserFiles(user) + 1;
                }else {
                    return leftFiles;
                }
            }else{
                if( unOwnedFiles <= disBalanceFiles && unOwnedFiles != 0 && getCountUserFiles(user) <= disBalanceFiles + BALANCE_STEP){
                    return relevant - getCountUserFiles(user) + 1;
                }else{
                    return leftFiles;
                }
            }
        }
        return BALANCE_STEP;
    }
}
