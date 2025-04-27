//package gr.aueb.budgettune.controller;
//
//import gr.aueb.budgettune.exception.UserAlreadyExistsException;
//import gr.aueb.budgettune.model.User;
//import gr.aueb.budgettune.service.UserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class UserWebController {
//
//    private final UserService userService;
//
//    public UserWebController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // GET /register – Προβολή της φόρμας εγγραφής
//    @GetMapping("/register")
//    public String showRegisterForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    // POST /register – Υποβολή της φόρμας εγγραφής
//    @PostMapping("/register")
//    public String processRegistration(@ModelAttribute("user") User user, Model model) {
//        try {
//            userService.register(user);
//            return "redirect:/login";
//        } catch (UserAlreadyExistsException e) {
//            model.addAttribute("error", e.getMessage());
//            return "register";
//        }
//    }
//
//    // GET /login – Προβολή login φόρμας (προαιρετικά)
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }
//}
