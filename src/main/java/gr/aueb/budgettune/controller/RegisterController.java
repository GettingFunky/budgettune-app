package gr.aueb.budgettune.controller;

import gr.aueb.budgettune.dto.UserDTO;
import gr.aueb.budgettune.model.User;
import gr.aueb.budgettune.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register-form";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
                               BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register-form";
        }

        if (userService.existsByUsername(userDTO.getUsername())) {
            model.addAttribute("registrationError", "Το όνομα χρήστη χρησιμοποιείται ήδη");
            return "register-form";
        }

        userService.register(userDTO);

        return "redirect:/login?registerSuccess";
    }
}
