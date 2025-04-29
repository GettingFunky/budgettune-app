package gr.aueb.budgettune.api;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.service.TransactionService;
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

    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id) {
        return transactionService.findTransactionById(id);
    }

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @PutMapping("/{id}")
    public TransactionDTO updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
