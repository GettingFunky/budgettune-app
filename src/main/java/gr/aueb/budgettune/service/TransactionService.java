package gr.aueb.budgettune.service;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.model.User;

import java.util.List;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO findTransactionById(Long id);

    TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO);

    void deleteTransaction(Long id);

    List<TransactionDTO> findAllTransactions();

    List<TransactionDTO> findAllByUser(User user);
}
