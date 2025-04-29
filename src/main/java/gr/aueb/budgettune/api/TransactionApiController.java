package gr.aueb.budgettune.api;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionApiController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionApiController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Λίστα Συναλλαγών", description = "Επιστρέφει όλες τις συναλλαγές του τρέχοντος χρήστη.")
    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @Operation(summary = "Αναζήτηση Συναλλαγής", description = "Επιστρέφει μία συναλλαγή βάσει του ID της.")
    @GetMapping("/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id) {
        return transactionService.findTransactionById(id);
    }

    @Operation(summary = "Δημιουργία Συναλλαγής", description = "Δημιουργεί μία νέα συναλλαγή για τον τρέχοντα χρήστη.")
    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @Operation(summary = "Ενημέρωση Συναλλαγής", description = "Ενημερώνει τα στοιχεία μιας υπάρχουσας συναλλαγής.")
    @PutMapping("/{id}")
    public TransactionDTO updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @Operation(summary = "Διαγραφή Συναλλαγής", description = "Διαγράφει μία συναλλαγή βάσει του ID της.")
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
