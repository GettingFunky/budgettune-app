package gr.aueb.budgettune.service;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.dto.TransactionSummaryDTO;
import gr.aueb.budgettune.exception.TransactionNotFoundException;
import gr.aueb.budgettune.mapper.TransactionMapper;
import gr.aueb.budgettune.model.Transaction;
import gr.aueb.budgettune.model.TransactionType;
import gr.aueb.budgettune.model.User;
import gr.aueb.budgettune.repository.TransactionRepository;
import gr.aueb.budgettune.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.toEntity(transactionDTO, userRepository);

        // ➡️ Βρίσκουμε ΠΑΝΤΑ τον τρέχοντα χρήστη
        User currentUser = getCurrentUser();
        transaction.setUser(currentUser);

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
        updatedTransaction.setId(id); // Βάζουμε το σωστό ID

        // ➡️ Πολύ Σημαντικό: Βάζουμε ξανά τον τρέχοντα χρήστη!
        User currentUser = getCurrentUser();
        updatedTransaction.setUser(currentUser);

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
        User currentUser = getCurrentUser();

        if (currentUser.getRole().equalsIgnoreCase("ADMIN")) {
            // ➡️ Admin βλέπει ΟΛΕΣ τις συναλλαγές
            List<Transaction> allTransactions = transactionRepository.findAll();
            return allTransactions.stream()
                    .map(TransactionMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            // ➡️ Απλός χρήστης βλέπει ΜΟΝΟ τις δικές του
            List<Transaction> userTransactions = transactionRepository.findAllByUser(currentUser);
            return userTransactions.stream()
                    .map(TransactionMapper::toDTO)
                    .collect(Collectors.toList());
        }
    }


    @Override
    public List<TransactionDTO> findAllByUser(User user) {
        List<Transaction> transactions = transactionRepository.findAllByUser(user);
        return transactions.stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> filterTransactions(
            String description,
            Double amountMin,
            Double amountMax,
            LocalDate dateFrom,
            LocalDate dateTo,
            String[] types,
            String[] means,
            String category) {

        User user = getCurrentUser();
        List<Transaction> allTransactions = transactionRepository.findAllByUser(user);

        return allTransactions.stream()
                .filter(t -> description == null || t.getDescription().toLowerCase().contains(description.toLowerCase()))
                .filter(t -> amountMin == null || t.getAmount().doubleValue() >= amountMin)
                .filter(t -> amountMax == null || t.getAmount().doubleValue() <= amountMax)
                .filter(t -> dateFrom == null || !t.getDate().isBefore(dateFrom))
                .filter(t -> dateTo == null || !t.getDate().isAfter(dateTo))
                .filter(t -> types == null || types.length == 0 || containsType(types, t.getType().name()))
                .filter(t -> means == null || means.length == 0 || containsMeans(means, t.getMeans()))
                .filter(t -> category == null || t.getCategory().toLowerCase().contains(category.toLowerCase()))
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    private boolean containsType(String[] types, String type) {
        for (String t : types) {
            if (t.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsMeans(String[] means, gr.aueb.budgettune.model.TransactionMeans transactionMeans) {
        if (transactionMeans == null) return false;
        for (String m : means) {
            if (m.equalsIgnoreCase(transactionMeans.name())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TransactionSummaryDTO calculateSummary(List<TransactionDTO> transactions) {
        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(TransactionDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(TransactionDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = totalIncome.subtract(totalExpense);
        int totalTransactions = transactions.size();

        return new TransactionSummaryDTO(totalIncome, totalExpense, balance, totalTransactions);
    }

}
