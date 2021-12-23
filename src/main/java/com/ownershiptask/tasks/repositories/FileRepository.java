package com.ownershiptask.tasks.repositories;

import com.ownershiptask.tasks.models.File;

import java.util.List;

public interface FileRepository {
    boolean save(File file);
    List getAll();
    File getById(int id);
}