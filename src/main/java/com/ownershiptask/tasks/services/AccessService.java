package com.ownershiptask.tasks.services;

import com.ownershiptask.tasks.models.File;
import com.ownershiptask.tasks.models.User;

public interface AccessService {
    void addFilesAccess(User user, File... files);
    void deleteFilesAccess(User user, File... files);
    int getNumberPossibleAccess(User user);
}
