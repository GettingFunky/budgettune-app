package gr.aueb.budgettune.controller;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Εμφάνιση φόρμας δημιουργίας νέας συναλλαγής
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setDate(LocalDate.now()); // Σημερινή ημερομηνία
        model.addAttribute("transactionDTO", transactionDTO);
        return "transaction-form";
    }

    // Υποβολή νέας συναλλαγής
    @PostMapping("/create")
    public String createTransaction(@Valid @ModelAttribute("transactionDTO") TransactionDTO transactionDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "transaction-form";
        }

        transactionService.createTransaction(transactionDTO);
        return "redirect:/transactions/list";
    }

    @GetMapping("/list")
    public String listTransactions(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double amountMin,
            @RequestParam(required = false) Double amountMax,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(required = false) String[] types,
            @RequestParam(required = false) String[] means,
            Model model
    ) {
        // ➡️ Πρώτα φέρνουμε τα φιλτραρισμένα Transactions
        List<TransactionDTO> filteredTransactions = transactionService.filterTransactions(
                description, amountMin, amountMax, dateFrom, dateTo, types, means
        );

        // ➡️ Βάζουμε τα Transactions στο model
        model.addAttribute("transactions", filteredTransactions);

        // ➡️ Υπολογίζουμε Στατιστικά
        model.addAttribute("totalTransactions", filteredTransactions.size());

        model.addAttribute("totalIncome", filteredTransactions.stream()
                .filter(t -> t.getType() == gr.aueb.budgettune.model.TransactionType.INCOME)
                .map(TransactionDTO::getAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

        model.addAttribute("totalExpense", filteredTransactions.stream()
                .filter(t -> t.getType() == gr.aueb.budgettune.model.TransactionType.EXPENSE)
                .map(TransactionDTO::getAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

        return "transactions-list";
    }




    // Εμφάνιση μιας συναλλαγής
    @GetMapping("/{id}")
    public String viewTransaction(@PathVariable Long id, Model model) {
        TransactionDTO transactionDTO = transactionService.findTransactionById(id);
        model.addAttribute("transaction", transactionDTO);
        return "transaction-details"; // Placeholder σελίδα
    }

    // Εμφάνιση φόρμας επεξεργασίας συναλλαγής
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        TransactionDTO transactionDTO = transactionService.findTransactionById(id);
        model.addAttribute("transactionDTO", transactionDTO);
        return "transaction-form";
    }

    @PostMapping("/{id}/edit")
    public String updateTransaction(@PathVariable Long id,
                                    @Valid @ModelAttribute("transactionDTO") TransactionDTO transactionDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "transaction-form";
        }

        transactionService.updateTransaction(id, transactionDTO);
        return "redirect:/transactions/list";
    }

    // Διαγραφή συναλλαγής
    @PostMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return "redirect:/transactions/list";
    }
}
