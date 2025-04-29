package gr.aueb.budgettune.api;

import gr.aueb.budgettune.dto.UserDTO;
import gr.aueb.budgettune.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Εγγραφή Χρήστη", description = "Δημιουργεί έναν νέο χρήστη με username και κωδικό.")
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody UserDTO userDTO) {
        if (userService.existsByUsername(userDTO.getUsername())) {
            return "Το username υπάρχει ήδη.";
        }
        userService.register(userDTO);
        return "Εγγραφή επιτυχής!";
    }
}
