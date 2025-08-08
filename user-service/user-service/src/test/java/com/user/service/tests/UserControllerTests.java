package com.user.service.tests;

import com.user.service.controllers.UserController;
import com.user.service.entitys.User;
import com.user.service.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<User> users = List.of(
                new User(1L, "12345678", "Juan", "juan@mail.com", "1234"),
                new User(2L, "87654321", "Ana", "ana@mail.com", "5678")
        );

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()").value(users.size()))
                        .andExpect(jsonPath("$[0].name").value("Juan"))
                        .andExpect(jsonPath("$[1].name").value("Ana"));
    }

    @Test
    public void testFindUserById_Found() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "12345678", "Juan", "juan@mail.com", "1234");

        when(userService.findById(userId)).thenReturn(user);

        mockMvc.perform(get("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(userId))
                        .andExpect(jsonPath("$.name").value("Juan"))
                        .andExpect(jsonPath("$.dni").value("12345678"));
    }

    @Test
    public void testFindUserById_NotFound() throws Exception {
        Long userId = 2L;

        when(userService.findById(userId)).thenReturn(null);

        mockMvc.perform(get("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }

    @Test
    public void testSaveUser() throws Exception {
        User userToCreate = new User();
        userToCreate.setName("Juan");
        userToCreate.setDni("12345678");
        userToCreate.setEmail("juan@gmail.com");
        userToCreate.setPhone("113452352");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Juan");
        savedUser.setDni("12345678");
        savedUser.setEmail("juan@gmail.com");
        savedUser.setPhone("113452352");

        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        String userJson = """
        {
            "name": "Juan",
            "dni": "12345678",
            "email": "juan@gmail.com",
            "phone": "113452352"
        }
        """;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.dni").value("12345678"))
                .andExpect(jsonPath("$.email").value("juan@gmail.com"))
                .andExpect(jsonPath("$.phone").value("113452352"));

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userService.findById(userId)).thenReturn(existingUser);
        doNothing().when(userService).deleteUserById(userId);

        mockMvc.perform(delete("/user/{id}", userId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).findById(userId);
        verify(userService, times(1)).deleteUserById(userId);
    }

    @Test
    public void testUpdateUser() throws Exception {
        Long userId = 1L;

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Juan");
        existingUser.setDni("12345678");
        existingUser.setEmail("juan@gmail.com");
        existingUser.setPhone("113452352");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("Juan Updated");
        updatedUser.setDni("12345678");
        updatedUser.setEmail("juan.updated@gmail.com");
        updatedUser.setPhone("113000000");

        when(userService.findById(userId)).thenReturn(existingUser);
        when(userService.saveUser(any(User.class))).thenReturn(updatedUser);

        String updateJson = """
    {
        "name": "Juan Updated",
        "dni": "12345678",
        "email": "juan.updated@gmail.com",
        "phone": "113000000"
    }
    """;

        mockMvc.perform(put("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Juan Updated"))
                .andExpect(jsonPath("$.dni").value("12345678"))
                .andExpect(jsonPath("$.email").value("juan.updated@gmail.com"))
                .andExpect(jsonPath("$.phone").value("113000000"));

        verify(userService, times(1)).findById(userId);
        verify(userService, times(1)).saveUser(any(User.class));
    }

}
