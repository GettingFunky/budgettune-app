package gr.aueb.budgettune.controller;

import gr.aueb.budgettune.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
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
    public String showStatistics(Model model) {
        // Mock δεδομένα για αρχή
        Map<String, BigDecimal> incomePerMonth = new LinkedHashMap<>();
        incomePerMonth.put("Ιαν", BigDecimal.valueOf(1200));
        incomePerMonth.put("Φεβ", BigDecimal.valueOf(950));
        incomePerMonth.put("Μαρ", BigDecimal.valueOf(1300));
        incomePerMonth.put("Απρ", BigDecimal.valueOf(1100));

        Map<String, BigDecimal> expensePerMonth = new LinkedHashMap<>();
        expensePerMonth.put("Ιαν", BigDecimal.valueOf(800));
        expensePerMonth.put("Φεβ", BigDecimal.valueOf(700));
        expensePerMonth.put("Μαρ", BigDecimal.valueOf(900));
        expensePerMonth.put("Απρ", BigDecimal.valueOf(750));

        model.addAttribute("incomePerMonth", incomePerMonth);
        model.addAttribute("expensePerMonth", expensePerMonth);

        return "statistics";
    }
}
