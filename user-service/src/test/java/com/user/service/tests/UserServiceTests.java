package com.user.service.tests;

import com.user.service.entities.User;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Test
    public void testFindAllUsers() {
        List<User> usersMock = List.of(new User(1L, "12345678", "Juan", "juan@mail.com", "1234"));
        when(userRepository.findAll()).thenReturn(usersMock);

        List<User> result = userServiceImp.findAllUsers();

        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testFindUserById() {
        Long userId = 1L;
        User userMock = new User();
        userMock.setId(userId);
        userMock.setName("Juan");
        userMock.setDni("12345678");
        userMock.setEmail("juan@gmail.com");
        userMock.setPhone("113452352");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));

        User result = userServiceImp.findById(userId);

        assertEquals(userMock, result);

        verify(userRepository, times(1)).findById(userId);

    }

    @Test
    public void testSaveUser() {
        User userToCreate = new User();
        userToCreate.setName("Juan");
        userToCreate.setDni("12345678");
        userToCreate.setEmail("juan@gmail.com");
        userToCreate.setPhone("113452352");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(userToCreate.getName());
        savedUser.setDni(userToCreate.getDni());
        savedUser.setEmail(userToCreate.getEmail());
        savedUser.setPhone(userToCreate.getPhone());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userServiceImp.saveUser(userToCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getName());

        verify(userRepository, times(1)).save(userToCreate);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userServiceImp.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;

        User userToUpdate = new User();
        userToUpdate.setId(userId);
        userToUpdate.setName("Juan Updated");
        userToUpdate.setDni("87654321");
        userToUpdate.setEmail("juan_updated@gmail.com");
        userToUpdate.setPhone("99887766");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName(userToUpdate.getName());
        updatedUser.setDni(userToUpdate.getDni());
        updatedUser.setEmail(userToUpdate.getEmail());
        updatedUser.setPhone(userToUpdate.getPhone());

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userServiceImp.saveUser(userToUpdate);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Juan Updated", result.getName());
        assertEquals("87654321", result.getDni());
        assertEquals("juan_updated@gmail.com", result.getEmail());
        assertEquals("99887766", result.getPhone());

        verify(userRepository, times(1)).save(userToUpdate);
    }

}
