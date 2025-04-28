package gr.aueb.budgettune.service;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.model.Transaction;
import gr.aueb.budgettune.model.TransactionMeans;
import gr.aueb.budgettune.model.TransactionType;
import gr.aueb.budgettune.model.User;
import gr.aueb.budgettune.repository.TransactionRepository;
import gr.aueb.budgettune.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionServiceImpl transactionService;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        userRepository = mock(UserRepository.class);
        transactionService = new TransactionServiceImpl(transactionRepository, userRepository);

        // 🛡️ Φτιάχνουμε ψεύτικο authenticated χρήστη
        Authentication auth = new UsernamePasswordAuthenticationToken("testuser", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void createTransaction_ShouldSaveAndReturnTransaction() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("USER");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setDate(LocalDate.now());
        transaction.setDescription("Test Transaction");
        transaction.setType(TransactionType.INCOME);
        transaction.setUser(user);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(BigDecimal.valueOf(100.0));
        transactionDTO.setDate(LocalDate.now());
        transactionDTO.setDescription("Test Transaction");
        transactionDTO.setType(TransactionType.INCOME);

        // Act
        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Test Transaction", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.0), result.getAmount());
    }

    @Test
    void findAllTransactions_ShouldReturnUserTransactions() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(50.0));
        transaction.setDescription("Groceries");
        transaction.setDate(LocalDate.now());
        transaction.setType(TransactionType.EXPENSE);
        transaction.setUser(user);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(transactionRepository.findAllByUser(user)).thenReturn(Collections.singletonList(transaction));

        // Act
        var result = transactionService.findAllTransactions();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Groceries", result.get(0).getDescription());
    }

    @Test
    void filterTransactions_ShouldReturnCorrectFilteredResults() {
        // Arrange
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        Transaction t1 = new Transaction();
        t1.setId(1L);
        t1.setDescription("Supermarket");
        t1.setAmount(new BigDecimal("50.00"));
        t1.setDate(LocalDate.of(2025, 4, 27));
        t1.setType(TransactionType.EXPENSE);
        t1.setMeans(TransactionMeans.CARD);
        t1.setUser(testUser);

        Transaction t2 = new Transaction();
        t2.setId(2L);
        t2.setDescription("Salary");
        t2.setAmount(new BigDecimal("1000.00"));
        t2.setDate(LocalDate.of(2025, 4, 25));
        t2.setType(TransactionType.INCOME);
        t2.setMeans(TransactionMeans.CASH);
        t2.setUser(testUser);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(transactionRepository.findAllByUser(testUser)).thenReturn(List.of(t1, t2));

        // Act
        List<TransactionDTO> results = transactionService.filterTransactions(
                "Sal",       // Περιγραφή που περιέχει "Sal"
                null,        // amountMin
                null,        // amountMax
                null,        // dateFrom
                null,        // dateTo
                new String[]{"INCOME"},  // Μόνο INCOME
                new String[]{"CASH"}     // Μόνο CASH
        );

        // Assert
        assertEquals(1, results.size());
        assertEquals("Salary", results.get(0).getDescription());
    }

}
