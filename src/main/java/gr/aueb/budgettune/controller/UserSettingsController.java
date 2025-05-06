package gr.aueb.budgettune.controller;

import gr.aueb.budgettune.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import gr.aueb.budgettune.model.User;
import gr.aueb.budgettune.repository.UserRepository;

@Controller
public class UserSettingsController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showUserSettings() {
        return "profile";
    }

    @PostMapping("/profile")
    public String updatePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Authentication authentication,
                                 Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Οι νέοι κωδικοί δεν ταιριάζουν.");
            return "profile";
        }

        boolean success = userService.changePassword(authentication.getName(), currentPassword, newPassword);

        if (!success) {
            model.addAttribute("error", "Ο τρέχων κωδικός είναι λάθος ή δεν βρέθηκε ο χρήστης.");
            return "profile";
        }

        model.addAttribute("success", "Ο κωδικός ενημερώθηκε με επιτυχία.");
        return "profile";
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(Authentication authentication) {
        String username = authentication.getName();
        userService.deleteUser(username);

        // Αποσύνδεση μετά τη διαγραφή
        SecurityContextHolder.clearContext();
        return "redirect:/login?deleted";
    }

}

