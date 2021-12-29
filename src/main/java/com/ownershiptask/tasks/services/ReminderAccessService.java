package com.ownershiptask.tasks.services;

import com.ownershiptask.tasks.models.File;
import com.ownershiptask.tasks.models.User;
import com.ownershiptask.tasks.repositories.AccessRepositoryImpl;
import com.ownershiptask.tasks.repositories.FileRepositoryImpl;
import com.ownershiptask.tasks.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of AccessService which makes calculations
 * based on the remainder of the division of the number of files
 * and the number of users.
 */

@Service
public class ReminderAccessService implements AccessService{
    /** Constant of balance.
     *  Affects the number of files received for the user.
     * @see #getNumberPossibleAccess(User)
     * */
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

    /**
     * Return true if this file has owner
     *
     * @param file the file to check if the owner exists.
     * @return true if this file has owner
     */
    public boolean isFileOwned(File file){
        return file.getOwner() != null;
    }

    /**
     * Returns the number of files on files Repository.
     *
     * @return the number of files on files Repository.
     */
    public int getNumberAllFiles(){
        return fileRepository.getAll().size();
    }

    /**
     * Returns the number of files on users Repository.
     *
     * @return the number of users on users Repository.
     */
    public int getNumberAllUsers(){
        return userRepository.getAll().size();
    }

    /**
     * Returns the number of owned files on access Repository.
     *
     * @return the number of owned files on access Repository.
     */
    public int getNumberOwnedFiles() {
        return accessRepository.getNumberOwnedFiles();
    }

    /**
     * Set owner to file
     *
     * @param file the file which access is set
     * @param user the user who is accessing
     */
    public void setFileOwner(File file, User user){
         // Case for rewrite access. Increment only if file has no owner
        if(file.getOwner() == null)
            accessRepository.incrementNumberOwnedFiles();
        file.setOwner(user);
        user.addToOwnedFilesList(file);
        accessRepository.addOwner(user);
    }

    /**
     * Returns the number of owned files on the user.
     *
     * @param user еhe user for whom count
     * @return the number of owned files on the user.
     */
    public int getCountUserFiles(User user){
        return user.getOwnedFiles().size();
    }


    /**
     * Sets the user to be the owner of these files.
     *
     * @param user the user who is accessing the files.
     * @param files files accessed by the user.
     */
    @Override
    public void addFilesAccess(User user, File... files){
        for (File file : files) {
            if (getNumberPossibleAccess(user) <= 0)
                return;
            if (isFileOwned(file) & !user.isHighPriority())
                continue;
            setFileOwner(file,user);
            accessRepository.addOwner(user);
        }
    }


    /**
     * Removes file access for the user.
     *
     * @param user the user to delete access to file.
     * @param files files to be removed access from the user.
     */
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


    /**
     * Return the number of possible access to the files.
     * Calculations are based on the remainder of the
     * number of files divided by the number of users.
     * There are two possible calculation options and two sub-options for them:
     * 1. Remainder from division is more than half of the divisor (countIndex >= 0.5):
     *    1.1 First users will get an integer from division + 1 (relevant + 1).
     *        The number of such users is equal to the remainder of the division.
     *    1.2 The remaining users will receive an integer from the division (relevant).
     * 2. Remainder from division is less than half of the divisor (countIndex < 0.5):
     *    2.1 All users will get an integer from the division (relevant).
     *    2.2 When the number of free files(unOwnedFiles) becomes equal
     *        to the remainder of the division(disBalanceFiles),
     *        the first users to request will have access to them.
     *
     * Each user cannot receive more than BALANCE_STEP + disBalanceFiles(remainder of the division).
     *
     * @param user the user whom to count a number of files to access.
     * @return the number of possible access to the files.
     */
    @Override
    public int getNumberPossibleAccess(User user){
        int unOwnedFiles = getNumberAllFiles() - getNumberOwnedFiles();
        int relevant = getNumberAllFiles() / getNumberAllUsers();
        int disBalanceFiles = getNumberAllFiles() % getNumberAllUsers();
        int leftFiles = Math.max((relevant - getCountUserFiles(user)), 0);
        float countIndex = (getNumberAllFiles() % getNumberAllUsers()) / (float) getNumberAllUsers();

        if(getNumberAllUsers() != 0){
            if (countIndex >= 0.5){
                // Case 1.1
                if( getNumberOwnedFiles() + 1 <= disBalanceFiles * (relevant + 1 )  && getCountUserFiles(user) < BALANCE_STEP + 1 ){
                    return relevant - getCountUserFiles(user) + 1;
                }else {
                    // Case 1.2
                    return leftFiles;
                }
            }else{
                // Case 2.2
                if( unOwnedFiles <= disBalanceFiles && unOwnedFiles != 0 && getCountUserFiles(user) <= disBalanceFiles + BALANCE_STEP){
                    return relevant - getCountUserFiles(user) + 1;
                }else{
                    // Case 2.1
                    return leftFiles;
                }
            }
        }
        return BALANCE_STEP;
    }
}
