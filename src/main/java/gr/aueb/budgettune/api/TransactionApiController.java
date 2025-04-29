package gr.aueb.budgettune.api;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Συναλλαγές", description = "API για Διαχείριση Συναλλαγών")
public class TransactionApiController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionApiController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Φέρνει όλες τις συναλλαγές του χρήστη")
    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @Operation(summary = "Δημιουργεί νέα συναλλαγή για τον χρήστη")
    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }
}
