package gr.aueb.budgettune.repository;

import gr.aueb.budgettune.model.Transaction;
import gr.aueb.budgettune.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);
    List<Transaction> findByUserAndType(User user, String type);
    List<Transaction> findByUserAndDate(User user, LocalDate date);
    List<Transaction> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
