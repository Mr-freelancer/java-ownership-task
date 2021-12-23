package com.ownershiptask.tasks.services;

import com.ownershiptask.tasks.models.File;
import com.ownershiptask.tasks.models.User;
import com.ownershiptask.tasks.repositories.FileRepositoryImpl;
import com.ownershiptask.tasks.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.*;

class AccessServiceTest {
    private final FileRepositoryImpl fileRepository = new FileRepositoryImpl();
    private final UserRepositoryImpl userRepository = new UserRepositoryImpl();
    private final AccessService accessService = new AccessService(fileRepository, userRepository);

    @BeforeEach
    void testSetup() throws Exception{
        // Create 10 users
        for (int i = 0; i < 10; i++){
            User user = new User("i");
            user.setName("User " + i);
            user.setAccessService(accessService);
            userRepository.save(user);
        }

        // Create 16 files
        for( int i = 0; i < 16; i++){
            int fileId = i;
            File file;
            file = new File();
            file.setAccessService(accessService);
            file.setID(++fileId);
            fileRepository.save(file);
        }
    }

    @AfterEach
    void dumpAllData(){
        fileRepository.getAll().clear();
        userRepository.getAll().clear();
        accessService.setNumberOwnedFiles(0);
    }

    @Test
    void isFileOwnedFalse() {
        Assertions.assertFalse(accessService.isFileOwned(fileRepository.getById(0)));
    }

    @Test
    void isFileOwnedTrue() {
        File file = fileRepository.getById(0);
        file.setOwner(userRepository.getById(1));
        Assertions.assertTrue(accessService.isFileOwned(file));
    }

    @Test
    void getCountOwnedFiles() {
        fileRepository.getById(1).setOwner(userRepository.getById(0));
        fileRepository.getById(2).setOwner(userRepository.getById(2));
        fileRepository.getById(3).setOwner(userRepository.getById(3));

        Assertions.assertEquals(3, accessService.getNumberOwnedFiles());
    }

    @Test
    void getCountUserFiles() {
        User userImpl = userRepository.getById(0);

        fileRepository.getById(1).setOwner(userImpl);
        fileRepository.getById(2).setOwner(userImpl);
        fileRepository.getById(3).setOwner(userImpl);
        fileRepository.getById(4).setOwner(userImpl);

        Assertions.assertEquals(4,accessService.getCountUserFiles(userImpl));
    }

    @Test
    void getCountAllFiles() {
        Assertions.assertEquals(16, accessService.getNumberAllFiles());
    }

    @Test
    void getCountAllUsers() {
        Assertions.assertEquals(10,accessService.getNumberAllUsers());
    }

    @Test
    public void getCountPossibleInsertsSmallRemainderTest() {

        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(0)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(1)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(2)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(3)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(4)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(5)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(6)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(7)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(8)));
        Assertions.assertEquals( 2,accessService.getCountPossibleInserts(userRepository.getById(9)));

        // User1
        Assertions.assertEquals(2,accessService.getCountPossibleInserts(userRepository.getById(0)));
        userRepository.getById(0).takeOwnershipFiles(fileRepository.getById(0));
        userRepository.getById(0).takeOwnershipFiles(fileRepository.getById(1));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(0)));

        // User2
        Assertions.assertEquals(2,accessService.getCountPossibleInserts(userRepository.getById(1)));
        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(2));
        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(3));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(1)));

        // User3
        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(4));
        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(5));

        // User4
        userRepository.getById(3).takeOwnershipFiles(fileRepository.getById(6));
        userRepository.getById(3).takeOwnershipFiles(fileRepository.getById(7));

        // User5
        Assertions.assertEquals(2,accessService.getCountPossibleInserts(userRepository.getById(4)));
        userRepository.getById(4).takeOwnershipFiles(fileRepository.getById(8));
        userRepository.getById(4).takeOwnershipFiles(fileRepository.getById(9));

        // User6
        Assertions.assertEquals(2,accessService.getCountPossibleInserts(userRepository.getById(5)));
        userRepository.getById(5).takeOwnershipFiles(fileRepository.getById(10));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(5)));
        userRepository.getById(5).takeOwnershipFiles(fileRepository.getById(11));

        // Check users for ownership
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(0)));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(1)));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(2)));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(3)));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(4)));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(5)));

        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(6)));
        userRepository.getById(6).takeOwnershipFiles(fileRepository.getById(12));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(6)));

        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(7)));
        userRepository.getById(7).takeOwnershipFiles(fileRepository.getById(13));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(7)));

        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(8)));
        userRepository.getById(8).takeOwnershipFiles(fileRepository.getById(14));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(8)));

        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(9)));
        userRepository.getById(9).takeOwnershipFiles(fileRepository.getById(15));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(9)));

    }

    @Test
    public void getCountPossibleInsertsBigRemainder() {
        userRepository.deleteById(userRepository.getAll().size()-1);
        userRepository.deleteById(userRepository.getAll().size()-1);
        userRepository.deleteById(userRepository.getAll().size()-1);

        // Now 16 files and 7 users

        for (int i = 0; i < userRepository.getAll().size(); i++) {
            Assertions.assertEquals(2, accessService.getCountPossibleInserts(userRepository.getById(i)));
        }

        // User1
        userRepository.getById(0).takeOwnershipFiles(fileRepository.getById(0));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(0)));
        userRepository.getById(0).takeOwnershipFiles(fileRepository.getById(1));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(0)));

        // User2
        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(2));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(1)));
        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(3));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(1)));

        // User3
        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(4));
        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(5));

        // User4
        userRepository.getById(3).takeOwnershipFiles(fileRepository.getById(6));
        userRepository.getById(3).takeOwnershipFiles(fileRepository.getById(7));

        // User5
        userRepository.getById(4).takeOwnershipFiles(fileRepository.getById(8));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(4)));
        userRepository.getById(4).takeOwnershipFiles(fileRepository.getById(9));

        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(4)));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(3)));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(2)));

        // User6
        Assertions.assertEquals(2,accessService.getCountPossibleInserts(userRepository.getById(5)));
        userRepository.getById(5).takeOwnershipFiles(fileRepository.getById(10));
        userRepository.getById(5).takeOwnershipFiles(fileRepository.getById(11));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(5)));
        Assertions.assertEquals(2,accessService.getCountPossibleInserts(userRepository.getById(6)));
        userRepository.getById(6).takeOwnershipFiles(fileRepository.getById(12));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(6)));
        userRepository.getById(6).takeOwnershipFiles(fileRepository.getById(13));

        // 14 files owned, all left files can own by everyone
        Assertions.assertEquals(14, accessService.getNumberOwnedFiles());

        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(0)));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(1)));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(2)));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(3)));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(4)));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(5)));
        Assertions.assertEquals(1,accessService.getCountPossibleInserts(userRepository.getById(6)));

        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(14));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(1)));

        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(15));
        Assertions.assertEquals(0,accessService.getCountPossibleInserts(userRepository.getById(2)));

        System.out.println("Result of getCountPossibleInsertsBigRemainder Test:");
        fileRepository.getAll().forEach(file -> System.out.println(file.getOwner()));
    }

    @Test
    void deleteFilesAccess() {
        User user1 = userRepository.getById(0);
        User user2 = userRepository.getById(1);

        // Own 2 files
        user1.takeOwnershipFiles(fileRepository.getById(0));
        user1.takeOwnershipFiles(fileRepository.getById(1));
        Assertions.assertEquals(2, accessService.getNumberOwnedFiles());

        // Try to delete ownership for not owner
        user2.deleteFilesOwnership(fileRepository.getById(0));
        Assertions.assertEquals(2, accessService.getNumberOwnedFiles());

        // Delete ownership
        user1.deleteFilesOwnership(fileRepository.getById(0));
        Assertions.assertEquals(1, accessService.getNumberOwnedFiles());
        user1.deleteFilesOwnership(fileRepository.getById(1));
        Assertions.assertEquals(0, accessService.getNumberOwnedFiles());
    }
}