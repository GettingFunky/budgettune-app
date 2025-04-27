package gr.aueb.budgettune.controller;

import gr.aueb.budgettune.dto.UserDTO;
import gr.aueb.budgettune.exception.UserAlreadyExistsException;
import gr.aueb.budgettune.model.User;
import gr.aueb.budgettune.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if (userService.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(userDTO.getUsername());
        }

        User user = new User(userDTO.getUsername(), userDTO.getPassword(), "USER");
        User savedUser = userService.register(user);

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
