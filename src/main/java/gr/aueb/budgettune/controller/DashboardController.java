package gr.aueb.budgettune.controller;

import gr.aueb.budgettune.dto.TransactionSummaryDTO;
import gr.aueb.budgettune.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    private final TransactionService transactionService;

    @Autowired
    public DashboardController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);

        TransactionSummaryDTO summary = transactionService.calculateSummaryByUsername(username);
        model.addAttribute("summary", summary);

        return "dashboard";
    }
}
