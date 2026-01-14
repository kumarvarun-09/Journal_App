package com.cc.journalApp.service;

import com.cc.journalApp.models.User;
import com.cc.journalApp.repository.UserRepository;
import com.cc.journalApp.request.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.when;

@Disabled("tested")
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Mock
    private UserRepository userRepository;

    @Test
    void getUserByUserName() {
        User u = new User(new UserRequest("abcd", "qwer"));
        u.setRoles(List.of("USER"));
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(u);
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("abcd");
        Assertions.assertNotNull(userDetails);
    }
}
