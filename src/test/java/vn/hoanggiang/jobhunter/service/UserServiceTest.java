package vn.hoanggiang.jobhunter.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.hoanggiang.jobhunter.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    private @Mock UserRepository userRepository;
    private @Mock CompanyService companyService;
    private @Mock RoleService roleService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, companyService, roleService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handleCreateUser() {
    }

    @Test
    void handleDeleteUser() {
    }

    @Test
    void fetchUserById() {
    }

    @Test
    void fetchAllUser() {
    }

    @Test
    void handleUpdateUser() {
    }

    @Test
    void handleGetUserByUsername() {
    }

    @Test
    void isEmailExist() {
        when(userRepository.existsByEmail("admin@gmail.com")).thenReturn(true);

        boolean result = userService.isEmailExist("admin@gmail.com");
//        Assertions.assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void convertToResCreateUserDTO() {
    }

    @Test
    void convertToResUpdateUserDTO() {
    }

    @Test
    void convertToResUserDTO() {
    }

    @Test
    void updateUserToken() {
    }

    @Test
    void getUserByRefreshTokenAndEmail() {
    }
}