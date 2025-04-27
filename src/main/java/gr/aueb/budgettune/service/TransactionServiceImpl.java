package gr.aueb.budgettune.service;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.exception.TransactionNotFoundException;
import gr.aueb.budgettune.mapper.TransactionMapper;
import gr.aueb.budgettune.model.Transaction;
import gr.aueb.budgettune.model.User;
import gr.aueb.budgettune.repository.TransactionRepository;
import gr.aueb.budgettune.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.toEntity(transactionDTO, userRepository);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(savedTransaction);
    }

    @Override
    public TransactionDTO findTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return TransactionMapper.toDTO(transaction);
    }

    @Override
    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        Transaction updatedTransaction = TransactionMapper.toEntity(transactionDTO, userRepository);

        updatedTransaction.setId(id); // **ΠΡΟΣΟΧΗ: Βάζουμε το σωστό id!**

        Transaction savedTransaction = transactionRepository.save(updatedTransaction);
        return TransactionMapper.toDTO(savedTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        transactionRepository.delete(transaction);
    }

    @Override
    public List<TransactionDTO> findAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findAllByUser(User user) {
        List<Transaction> transactions = transactionRepository.findAllByUser(user);
        return transactions.stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }
}
