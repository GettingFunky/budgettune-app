package gr.aueb.budgettune.controller;

import gr.aueb.budgettune.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class StatisticsController {

    private final TransactionService transactionService;

    @Autowired
    public StatisticsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model, Principal principal) {
        String username = principal.getName();

        Map<String, BigDecimal> incomePerMonth = transactionService.getMonthlyTotalByType(username, "INCOME");
        Map<String, BigDecimal> expensePerMonth = transactionService.getMonthlyTotalByType(username, "EXPENSE");
        Map<String, BigDecimal> expenseByCategory = transactionService.getExpenseTotalByCategory(username);

        model.addAttribute("incomePerMonth", incomePerMonth);
        model.addAttribute("expensePerMonth", expensePerMonth);
        model.addAttribute("expenseByCategory", expenseByCategory);

        return "statistics";
    }

    }
