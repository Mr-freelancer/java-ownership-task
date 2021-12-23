package com.ownershiptask.tasks.repositories;

import com.ownershiptask.tasks.models.File;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Repository
public class FileRepositoryImpl implements FileRepository {

    private static final List<File> files = new ArrayList<>();
    @Override
    public boolean save(File file) {
        return files.add(file);
    }

    @Override
    public List<File> getAll() {
        return files;
    }

    @Override
    public File getById(int id) {
        return files.get(id);
    }

}
