package gr.aueb.budgettune.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        String username = authentication.getName(); // Παίρνει το username του logged-in χρήστη
        model.addAttribute("username", username);
        return "dashboard"; // templates/dashboard.html
    }
}
