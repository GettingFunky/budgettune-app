package gr.aueb.budgettune.service;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.dto.TransactionSummaryDTO;
import gr.aueb.budgettune.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO findTransactionById(Long id);

    TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO);

    void deleteTransaction(Long id);

    List<TransactionDTO> findAllTransactions();

    List<TransactionDTO> findAllByUser(User user);

    List<TransactionDTO> filterTransactions(
            String description,
            Double amountMin,
            Double amountMax,
            LocalDate dateFrom,
            LocalDate dateTo,
            String[] types,
            String[] means,
            String category
    );

    TransactionSummaryDTO calculateSummary(List<TransactionDTO> transactions);

    TransactionSummaryDTO calculateSummaryByUsername(String username);

    Map<String, BigDecimal> getMonthlyTotalByType(String username, String type);


}
