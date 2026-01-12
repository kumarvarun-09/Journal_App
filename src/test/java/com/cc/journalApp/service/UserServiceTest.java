package com.cc.journalApp.service;

import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.models.User;
import com.cc.journalApp.request.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@RequiredArgsConstructor
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {"VarunAdmin", "Varun", "Rohan"})
    public void testUserByUserName(String name) {
        User u = null;
        try {
            u = userService.getUserByUserName(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(u);
    }

    @ParameterizedTest
    @ValueSource(strings = {"addhdf", "ioui", "mnbv"})
    public void testUserByUserName2(String name) {
        User u = null;
        try {
            u = userService.getUserByUserName(name);
        } catch (ResourceNotFoundException e) {
            Assertions.assertNull(u);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    void createNewUserTest(UserRequest user) {
        Assertions.assertNotNull(userService.createUser(user));
    }
}
