package com.ownershiptask.tasks.services;

import com.ownershiptask.tasks.models.File;
import com.ownershiptask.tasks.repositories.FileRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileRepositoryImpl fileRepository;

    public FileService(FileRepositoryImpl fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void save(File file){
        this.fileRepository.save(file);
    }

    public List<File> getAll(){
        return fileRepository.getAll();
    }
}
