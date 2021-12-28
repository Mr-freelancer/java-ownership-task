package com.ownershiptask.tasks.services;

import com.ownershiptask.tasks.models.File;
import com.ownershiptask.tasks.models.User;
import com.ownershiptask.tasks.repositories.AccessRepositoryImpl;
import com.ownershiptask.tasks.repositories.FileRepositoryImpl;
import com.ownershiptask.tasks.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.*;


class ReminderAccessServiceTest {
    private final FileRepositoryImpl fileRepository = new FileRepositoryImpl();
    private final UserRepositoryImpl userRepository = new UserRepositoryImpl();
    private final ReminderAccessService reminderAccessService = new ReminderAccessService(fileRepository, userRepository);
    private final AccessRepositoryImpl accessRepository = new AccessRepositoryImpl();

    @BeforeEach
    void testSetup() {
        // Create 10 users
        reminderAccessService.setAccessRepository(accessRepository);
        for (int i = 0; i < 10; i++){
            User user = new User(i);
            user.setName("User " + i);
            user.setReminderAccessService(reminderAccessService);
            userRepository.save(user);
        }

        // Create 16 files
        for( int i = 0; i < 16; i++){
            int fileId = i;
            File file;
            file = new File();
            file.setID(++fileId);
            fileRepository.save(file);
        }
    }

    @AfterEach
    void dumpAllData(){
        userRepository.getAll().clear();
        fileRepository.getAll().clear();
    }

    @Test
    void isFileOwnedFalse() {
        Assertions.assertFalse(reminderAccessService.isFileOwned(fileRepository.getById(0)));
    }

    @Test
    void isFileOwnedTrue() {
        File file = fileRepository.getById(0);
        file.setOwner(userRepository.getById(1));
        Assertions.assertTrue(reminderAccessService.isFileOwned(file));
    }

    @Test
    void getCountOwnedFiles() {
        User user1 = userRepository.getById(0);
        User user2 = userRepository.getById(1);
        User user3 = userRepository.getById(2);

        reminderAccessService.setFileOwner(fileRepository.getById(1), user1);
        reminderAccessService.setFileOwner(fileRepository.getById(2), user2);
        reminderAccessService.setFileOwner(fileRepository.getById(3), user3);

        Assertions.assertEquals(3, reminderAccessService.getNumberOwnedFiles());
    }

    @Test
    void getCountUserFiles() {
        User user1 = userRepository.getById(0);

        reminderAccessService.setFileOwner(fileRepository.getById(0), user1);
        reminderAccessService.setFileOwner(fileRepository.getById(1), user1);
        reminderAccessService.setFileOwner(fileRepository.getById(2), user1);
        reminderAccessService.setFileOwner(fileRepository.getById(3), user1);

        Assertions.assertEquals(4, reminderAccessService.getCountUserFiles(user1));
    }

    @Test
    void getCountAllFiles() {
        Assertions.assertEquals(16, reminderAccessService.getNumberAllFiles());
    }

    @Test
    void getCountAllUsers() {
        Assertions.assertEquals(10, reminderAccessService.getNumberAllUsers());
    }

    @Test
    public void getCountPossibleInsertsBigRemainder() {
        // 16 files and 10 users. Remainder of division is 6. 6 out of 10 is over 50%
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(0)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(1)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(2)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(3)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(4)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(5)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(6)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(7)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(8)));
        Assertions.assertEquals( 2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(9)));

        // User1
        Assertions.assertEquals(2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(0)));
        userRepository.getById(0).takeOwnershipFiles(fileRepository.getById(0));
        userRepository.getById(0).takeOwnershipFiles(fileRepository.getById(1));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(0)));

        // User2
        Assertions.assertEquals(2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(1)));
        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(2));
        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(3));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(1)));

        // User3
        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(4));
        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(5));

        // User4
        userRepository.getById(3).takeOwnershipFiles(fileRepository.getById(6));
        userRepository.getById(3).takeOwnershipFiles(fileRepository.getById(7));

        // User5
        Assertions.assertEquals(2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(4)));
        userRepository.getById(4).takeOwnershipFiles(fileRepository.getById(8));
        userRepository.getById(4).takeOwnershipFiles(fileRepository.getById(9));

        // User6
        Assertions.assertEquals(2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(5)));
        userRepository.getById(5).takeOwnershipFiles(fileRepository.getById(10));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(5)));
        userRepository.getById(5).takeOwnershipFiles(fileRepository.getById(11));

        // Check users for ownership
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(0)));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(1)));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(2)));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(3)));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(4)));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(5)));

        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(6)));
        userRepository.getById(6).takeOwnershipFiles(fileRepository.getById(12));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(6)));

        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(7)));
        userRepository.getById(7).takeOwnershipFiles(fileRepository.getById(13));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(7)));

        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(8)));
        userRepository.getById(8).takeOwnershipFiles(fileRepository.getById(14));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(8)));

        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(9)));
        userRepository.getById(9).takeOwnershipFiles(fileRepository.getById(15));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(9)));

    }

    @Test
    public void getCountPossibleInsertsSmallRemainder() {
        userRepository.deleteById(userRepository.getAll().size()-1);
        userRepository.deleteById(userRepository.getAll().size()-1);
        userRepository.deleteById(userRepository.getAll().size()-1);

        // Now 16 files and 7 users. Remainder 2
        for (int i = 0; i < userRepository.getAll().size(); i++) {
            Assertions.assertEquals(2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(i)));
        }

        // User1
        userRepository.getById(0).takeOwnershipFiles(fileRepository.getById(0));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(0)));
        userRepository.getById(0).takeOwnershipFiles(fileRepository.getById(1));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(0)));

        // User2
        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(2));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(1)));
        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(3));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(1)));

        // User3
        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(4));
        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(5));

        // User4
        userRepository.getById(3).takeOwnershipFiles(fileRepository.getById(6));
        userRepository.getById(3).takeOwnershipFiles(fileRepository.getById(7));

        // User5
        userRepository.getById(4).takeOwnershipFiles(fileRepository.getById(8));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(4)));
        userRepository.getById(4).takeOwnershipFiles(fileRepository.getById(9));

        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(4)));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(3)));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(2)));

        // User6
        Assertions.assertEquals(2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(5)));
        userRepository.getById(5).takeOwnershipFiles(fileRepository.getById(10));
        userRepository.getById(5).takeOwnershipFiles(fileRepository.getById(11));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(5)));
        Assertions.assertEquals(2, reminderAccessService.getNumberPossibleAccess(userRepository.getById(6)));
        userRepository.getById(6).takeOwnershipFiles(fileRepository.getById(12));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(6)));
        userRepository.getById(6).takeOwnershipFiles(fileRepository.getById(13));

        // 14 files owned, all left files can own by everyone
        Assertions.assertEquals(14, reminderAccessService.getNumberOwnedFiles());

        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(0)));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(1)));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(2)));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(3)));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(4)));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(5)));
        Assertions.assertEquals(1, reminderAccessService.getNumberPossibleAccess(userRepository.getById(6)));

        userRepository.getById(1).takeOwnershipFiles(fileRepository.getById(14));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(1)));

        userRepository.getById(2).takeOwnershipFiles(fileRepository.getById(15));
        Assertions.assertEquals(0, reminderAccessService.getNumberPossibleAccess(userRepository.getById(2)));

    }

    @Test
    void deleteFilesAccess() {
        User user1 = userRepository.getById(0);
        User user2 = userRepository.getById(1);

        // Own 2 files
        user1.takeOwnershipFiles(fileRepository.getById(0));
        user1.takeOwnershipFiles(fileRepository.getById(1));
        Assertions.assertEquals(2, reminderAccessService.getNumberOwnedFiles());

        // Try to delete ownership for not owner
        user2.deleteFilesOwnership(fileRepository.getById(0));
        Assertions.assertEquals(2, reminderAccessService.getNumberOwnedFiles());

        // Delete ownership
        user1.deleteFilesOwnership(fileRepository.getById(0));
        Assertions.assertEquals(1, reminderAccessService.getNumberOwnedFiles());
        user1.deleteFilesOwnership(fileRepository.getById(1));
        Assertions.assertEquals(0, reminderAccessService.getNumberOwnedFiles());
    }

    @Test
    void getPossibleInsertsWithoutRemainder(){
        // Make 8 users for share with 16 users
        userRepository.deleteById(9);
        userRepository.deleteById(8);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                // All users try access to all files
                userRepository.getById(i).takeOwnershipFiles(fileRepository.getById(j));
            }
            // All users have the same number of files
            Assertions.assertEquals(2, userRepository.getById(i).getCountOwnedFiles());
        }
    }

    @Test
    void commonTaskTest(){
        User user1 = userRepository.getById(0);
        user1.setName("User 1");
        User user2 = userRepository.getById(1);
        user2.setName("User 2");
        User user3 = userRepository.getById(2);
        user3.setName("User 3");
        User user4 = userRepository.getById(3);
        user4.setName("User 4");

        // User 1 try to take ownership to files 2,3
        user1.takeOwnershipFiles(fileRepository.getById(1), fileRepository.getById(2));
        Assertions.assertEquals(user1, fileRepository.getById(1).getOwner());
        Assertions.assertEquals(user1, fileRepository.getById(2).getOwner());
        Assertions.assertEquals(2, reminderAccessService.getNumberOwnedFiles());

        // User 2 try to take ownership to files 2,4,5
        user2.takeOwnershipFiles(fileRepository.getById(1), fileRepository.getById(3), fileRepository.getById(4));
        Assertions.assertEquals(user1, fileRepository.getById(1).getOwner());
        Assertions.assertEquals(user2, fileRepository.getById(3).getOwner());
        Assertions.assertEquals(user2, fileRepository.getById(4).getOwner());
        Assertions.assertEquals(4, reminderAccessService.getNumberOwnedFiles());

        // Set user 3 active for rewrite access to file 2
        user3.setActive(true);
        user3.takeOwnershipFiles(fileRepository.getById(1));
        Assertions.assertEquals(4, reminderAccessService.getNumberOwnedFiles());
        Assertions.assertEquals(user3, fileRepository.getById(1).getOwner());

        // Try to delete ownership for user 2 to files 2,4,5
        user2.deleteFilesOwnership(fileRepository.getById(1), fileRepository.getById(3), fileRepository.getById(4));

        Assertions.assertEquals(user3, fileRepository.getById(1).getOwner());
        Assertions.assertEquals(null, fileRepository.getById(3).getOwner());
        Assertions.assertEquals(null, fileRepository.getById(4).getOwner());

        // User 4 with low priority (isActive false) try to take ownership on files 2,3
        user4.takeOwnershipFiles(fileRepository.getById(1),fileRepository.getById(2));
        Assertions.assertEquals(user3, fileRepository.getById(1).getOwner());
        Assertions.assertEquals(user1, fileRepository.getById(2).getOwner());
        Assertions.assertEquals(2, reminderAccessService.getNumberOwnedFiles());
        fileRepository.getAll().forEach(file-> System.out.println(file.getOwner()));
    }
}