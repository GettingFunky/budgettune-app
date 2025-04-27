package gr.aueb.budgettune.service;

import gr.aueb.budgettune.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findAll();
    User register(User user);
    void deleteById(Long id);
}
