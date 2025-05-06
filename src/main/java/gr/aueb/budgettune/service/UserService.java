package gr.aueb.budgettune.service;

import gr.aueb.budgettune.dto.UserDTO;

public interface UserService {
    void register(UserDTO userDTO);

    boolean existsByUsername(String username);

    boolean changePassword(String username, String currentPassword, String newPassword);

    void deleteUser(String username);

}

