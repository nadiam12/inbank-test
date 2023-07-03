package com.inbank.loancalculator.service;

import com.inbank.loancalculator.model.CreditModifier;
import com.inbank.loancalculator.model.User;
import com.inbank.loancalculator.error.UserNotFoundException;
import com.inbank.loancalculator.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private final UserService userService = new UserServiceProvider();

    @Test
    public void shouldThrowExceptionForNotExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.getUser(1),
                UserNotFoundException.USER_NOT_FOUND_MESSAGE);
    }


    @Test
    public void shouldReturnExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(1, CreditModifier.SEGMENT_3)));
        User user = userService.getUser(1);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getUserId());
    }
}
