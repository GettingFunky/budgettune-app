//package gr.aueb.budgettune.controller;
//
//import gr.aueb.budgettune.model.Transaction;
//import gr.aueb.budgettune.model.User;
//import gr.aueb.budgettune.service.TransactionService;
//import gr.aueb.budgettune.service.UserService;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//public class TransactionWebController {
//
//    private final TransactionService transactionService;
//    private final UserService userService;
//
//    public TransactionWebController(TransactionService transactionService, UserService userService) {
//        this.transactionService = transactionService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/dashboard")
//    public String showDashboard(Model model, Authentication authentication) {
//        String username = authentication.getName();
//        User user = userService.findByUsername(username).orElseThrow();
//        List<Transaction> transactions = transactionService.findAllByUser(user);
//
//        model.addAttribute("username", username);
//        model.addAttribute("transactions", transactions);
//
//        return "dashboard";
//    }
//
//    @GetMapping("/transactions/add")
//    public String showAddTransactionForm(Model model) {
//        model.addAttribute("transaction", new Transaction());
//
//        List<String> types = List.of("INCOME", "EXPENSE");
//        List<String> categories = List.of("Salary", "Groceries", "Transport", "Entertainment", "Utilities");
//
//        model.addAttribute("types", types);
//        model.addAttribute("categories", categories);
//
//        return "add-transaction";
//    }
//
//
//    // POST – Αποθήκευση συναλλαγής
//    @PostMapping("/transactions/add")
//    public String processAddTransaction(@ModelAttribute("transaction") Transaction transaction,
//                                        Authentication authentication) {
//        String username = authentication.getName();
//        User user = userService.findByUsername(username).orElseThrow();
//        transaction.setUser(user);
//        transactionService.save(transaction);
//        return "redirect:/dashboard";
//    }
//
//}
