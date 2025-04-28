package gr.aueb.budgettune.service;

import gr.aueb.budgettune.dto.UserDTO;
import gr.aueb.budgettune.model.User;
import gr.aueb.budgettune.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserServiceImpl userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void register_ShouldEncodePasswordAndSaveUser() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("plaintext");

        when(passwordEncoder.encode("plaintext")).thenReturn("encodedPassword");

        // Act
        userService.register(userDTO);

        // Assert
        verify(userRepository).save(argThat(user ->
                user.getUsername().equals("testuser") &&
                        user.getPassword().equals("encodedPassword") &&
                        user.getRole().equals("USER")
        ));
    }

    @Test
    void existsByUsername_ShouldReturnCorrectValue() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // Act
        boolean exists = userService.existsByUsername("testuser");

        // Assert
        assertThat(exists).isTrue();
        verify(userRepository).existsByUsername("testuser");
    }
}
