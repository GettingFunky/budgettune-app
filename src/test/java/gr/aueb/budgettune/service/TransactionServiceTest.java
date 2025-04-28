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
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionServiceImpl transactionService;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionRepository = mock(TransactionRepository.class);
        userRepository = mock(UserRepository.class);
        transactionService = new TransactionServiceImpl(transactionRepository, userRepository);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("USER");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        Authentication auth = new UsernamePasswordAuthenticationToken("testuser", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void createTransaction_ShouldSaveAndReturnTransaction() {
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

        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals("Test Transaction", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.0), result.getAmount());
    }

    @Test
    void findAllTransactions_ShouldReturnUserTransactions() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(50.0));
        transaction.setDescription("Groceries");
        transaction.setDate(LocalDate.now());
        transaction.setType(TransactionType.EXPENSE);
        transaction.setUser(user);

        when(transactionRepository.findAllByUser(user)).thenReturn(Collections.singletonList(transaction));

        var result = transactionService.findAllTransactions();

        assertEquals(1, result.size());
        assertEquals("Groceries", result.get(0).getDescription());
    }

    @Test
    void filterTransactions_ShouldReturnCorrectFilteredResults() {
        Transaction t1 = new Transaction();
        t1.setId(1L);
        t1.setDescription("Supermarket");
        t1.setAmount(new BigDecimal("50.00"));
        t1.setDate(LocalDate.of(2025, 4, 27));
        t1.setType(TransactionType.EXPENSE);
        t1.setMeans(TransactionMeans.CARD);
        t1.setUser(user);

        Transaction t2 = new Transaction();
        t2.setId(2L);
        t2.setDescription("Salary");
        t2.setAmount(new BigDecimal("1000.00"));
        t2.setDate(LocalDate.of(2025, 4, 25));
        t2.setType(TransactionType.INCOME);
        t2.setMeans(TransactionMeans.CASH);
        t2.setUser(user);

        when(transactionRepository.findAllByUser(user)).thenReturn(List.of(t1, t2));

        List<TransactionDTO> results = transactionService.filterTransactions(
                "Sal", null, null, null, null,
                new String[]{"INCOME"},
                new String[]{"CASH"}
        );

        assertEquals(1, results.size());
        assertEquals("Salary", results.get(0).getDescription());
    }

    @Test
    void updateTransaction_ShouldUpdateAndReturnTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setDescription("Old Description");
        transaction.setAmount(new BigDecimal("10.00"));
        transaction.setDate(LocalDate.of(2024, 1, 1));
        transaction.setType(TransactionType.INCOME);
        transaction.setMeans(TransactionMeans.CASH);
        transaction.setUser(user);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        TransactionDTO updatedDTO = new TransactionDTO();
        updatedDTO.setId(1L);
        updatedDTO.setDescription("New Description");
        updatedDTO.setAmount(new BigDecimal("20.00"));
        updatedDTO.setDate(LocalDate.of(2025, 1, 1));
        updatedDTO.setType(TransactionType.EXPENSE);
        updatedDTO.setMeans(TransactionMeans.CARD);

        TransactionDTO result = transactionService.updateTransaction(1L, updatedDTO);

        assertThat(result.getDescription()).isEqualTo("New Description");
        assertThat(result.getAmount()).isEqualTo(new BigDecimal("20.00"));
        assertThat(result.getType()).isEqualTo(TransactionType.EXPENSE);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void deleteTransaction_ShouldDeleteTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setUser(user);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        transactionService.deleteTransaction(1L);

        verify(transactionRepository).delete(transaction);
    }
}
