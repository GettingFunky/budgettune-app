package gr.aueb.budgettune.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Το username είναι υποχρεωτικό")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Το password είναι υποχρεωτικό")
    private String password;

    @Column(nullable = false)
    private String role = "USER";

    // One user may have many transactions (1:N relationship)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    // Default constructor required by JPA
    public User() {}

    // Custom constructor for quick object creation
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters & Setters (JavaBeans standard)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
