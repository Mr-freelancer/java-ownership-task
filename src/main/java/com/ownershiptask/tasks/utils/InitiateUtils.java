package com.ownershiptask.tasks.utils;

import com.ownershiptask.tasks.models.File;

import com.ownershiptask.tasks.models.User;
import com.ownershiptask.tasks.repositories.FileRepositoryImpl;
import com.ownershiptask.tasks.repositories.UserRepositoryImpl;
import com.ownershiptask.tasks.services.AccessService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class InitiateUtils implements CommandLineRunner {
    private FileRepositoryImpl fileRepository;
    private UserRepositoryImpl userRepository;
    private AccessService accessService;

    public InitiateUtils( FileRepositoryImpl fileRepository, AccessService accessService, UserRepositoryImpl userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.accessService = accessService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Create some Files
        for( int i = 0; i < 16; i++){
            int fileId = i;
            File file;
            file = new File();
            file.setID(++fileId);
            file.setAccessService(accessService);
            fileRepository.save(file);
        }

        // Create some Users
        User user1 = new User("1");
        user1.setName("one");
        userRepository.save(user1);
        user1.setAccessService(accessService);

        User user2 = new User("2");
        user2.setName("two");
        userRepository.save(user2);
        user2.setAccessService(accessService);

        User user3 = new User("3");
        user3.setName("three");
        userRepository.save(user3);
        user3.setAccessService(accessService);

        User user4 = new User("4");
        user4.setName("four");
        userRepository.save(user4);
        user4.setAccessService(accessService);

        User user5 = new User("5");
        user5.setName("five");
        userRepository.save(user5);
        user5.setAccessService(accessService);

        User user6 = new User("6");
        user6.setName("six");
        userRepository.save(user6);
        user6.setAccessService(accessService);

        User user7 = new User("7");
        user7.setName("seven");
        userRepository.save(user7);
        user7.setAccessService(accessService);

        System.out.println("Can assign user1: " + accessService.getCountPossibleInserts(user1));
        System.out.println("Can assign user2: " + accessService.getCountPossibleInserts(user2));
        System.out.println("Can assign user3: " + accessService.getCountPossibleInserts(user3));
        System.out.println("Can assign user4: " + accessService.getCountPossibleInserts(user4));
        System.out.println("Can assign user5: " + accessService.getCountPossibleInserts(user5));
        System.out.println("Can assign user6: " + accessService.getCountPossibleInserts(user6));
        System.out.println("Can assign user7: " + accessService.getCountPossibleInserts(user7));


        System.out.println("Can assign user1: " + accessService.getCountPossibleInserts(user1));
        user1.takeOwnershipFiles(fileRepository.getById(0), fileRepository.getById(1),fileRepository.getById(2),fileRepository.getById(3),fileRepository.getById(4),fileRepository.getById(5),fileRepository.getById(6),fileRepository.getById(7),fileRepository.getById(8),fileRepository.getById(9),fileRepository.getById(10),fileRepository.getById(11),fileRepository.getById(12),fileRepository.getById(13),fileRepository.getById(14),fileRepository.getById(15));
        user2.takeOwnershipFiles(fileRepository.getById(0), fileRepository.getById(1),fileRepository.getById(2),fileRepository.getById(3),fileRepository.getById(4),fileRepository.getById(5),fileRepository.getById(6),fileRepository.getById(7),fileRepository.getById(8),fileRepository.getById(9),fileRepository.getById(10),fileRepository.getById(11),fileRepository.getById(12),fileRepository.getById(13),fileRepository.getById(14),fileRepository.getById(15));
        user3.takeOwnershipFiles(fileRepository.getById(0), fileRepository.getById(1),fileRepository.getById(2),fileRepository.getById(3),fileRepository.getById(4),fileRepository.getById(5),fileRepository.getById(6),fileRepository.getById(7),fileRepository.getById(8),fileRepository.getById(9),fileRepository.getById(10),fileRepository.getById(11),fileRepository.getById(12),fileRepository.getById(13),fileRepository.getById(14),fileRepository.getById(15));
        user4.takeOwnershipFiles(fileRepository.getById(0), fileRepository.getById(1),fileRepository.getById(2),fileRepository.getById(3),fileRepository.getById(4),fileRepository.getById(5),fileRepository.getById(6),fileRepository.getById(7),fileRepository.getById(8),fileRepository.getById(9),fileRepository.getById(10),fileRepository.getById(11),fileRepository.getById(12),fileRepository.getById(13),fileRepository.getById(14),fileRepository.getById(15));
        user5.takeOwnershipFiles(fileRepository.getById(0), fileRepository.getById(1),fileRepository.getById(2),fileRepository.getById(3),fileRepository.getById(4),fileRepository.getById(5),fileRepository.getById(6),fileRepository.getById(7),fileRepository.getById(8),fileRepository.getById(9),fileRepository.getById(10),fileRepository.getById(11),fileRepository.getById(12),fileRepository.getById(13),fileRepository.getById(14),fileRepository.getById(15));
        user6.takeOwnershipFiles(fileRepository.getById(0), fileRepository.getById(1),fileRepository.getById(2),fileRepository.getById(3),fileRepository.getById(4),fileRepository.getById(5),fileRepository.getById(6),fileRepository.getById(7),fileRepository.getById(8),fileRepository.getById(9),fileRepository.getById(10),fileRepository.getById(11),fileRepository.getById(12),fileRepository.getById(13),fileRepository.getById(14),fileRepository.getById(15));
        user7.takeOwnershipFiles(fileRepository.getById(0), fileRepository.getById(1),fileRepository.getById(2),fileRepository.getById(3),fileRepository.getById(4),fileRepository.getById(5),fileRepository.getById(6),fileRepository.getById(7),fileRepository.getById(8),fileRepository.getById(9),fileRepository.getById(10),fileRepository.getById(11),fileRepository.getById(12),fileRepository.getById(13),fileRepository.getById(14),fileRepository.getById(15));

        System.out.println("Number of files: " + accessService.getNumberAllFiles());
        System.out.println("Owned files: " + accessService.getNumberOwnedFiles());
        System.out.println("Number if users: " + accessService.getNumberAllUsers());

        System.out.println("Can assign user1: " + accessService.getCountPossibleInserts(user1));
        System.out.println("Can assign user2: " + accessService.getCountPossibleInserts(user2));
        System.out.println("Can assign user3: " + accessService.getCountPossibleInserts(user3));
        System.out.println("Can assign user4: " + accessService.getCountPossibleInserts(user4));
        System.out.println("Can assign user5: " + accessService.getCountPossibleInserts(user5));
        System.out.println("Can assign user6: " + accessService.getCountPossibleInserts(user6));
        System.out.println("Can assign user7: " + accessService.getCountPossibleInserts(user7));

        user3.takeOwnershipFiles(fileRepository.getById(15));

        fileRepository.getAll().forEach(file -> System.out.println("File " + file.getID() + " :" + file.getOwner()));
    }
}
