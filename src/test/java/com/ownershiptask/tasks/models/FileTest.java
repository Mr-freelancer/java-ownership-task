package com.ownershiptask.tasks.models;

import com.ownershiptask.tasks.services.AccessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    private User user;
    private User user2;
    private File file;
    private File unsetFile;

    @BeforeEach
    void fileTestSetup(){
        user = new User("1");
        user2 = new User("2");

        file = new File();
        file.setID(777);
        file.setOwner(user);

        unsetFile = new File();
    }

    @Test
    void setOwner() {
        Assertions.assertEquals(null, unsetFile.getOwner());

        unsetFile.setOwner(user2);
        Assertions.assertEquals(user2, unsetFile.getOwner());
    }

    @Test
    void setID() {
        Assertions.assertEquals(777, file.getID());
        file.setID(666);
        Assertions.assertEquals(666, file.getID());
    }

    @Test
    void getID() {
        Assertions.assertEquals(777, file.getID());
    }

    @Test
    void getOwner() {
        Assertions.assertEquals(user, file.getOwner());
    }
}