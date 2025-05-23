package gr.aueb.budgettune.dto;

import gr.aueb.budgettune.model.TransactionType;
import gr.aueb.budgettune.model.TransactionMeans;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDTO {

    private Long id;

    @NotBlank(message = "Η περιγραφή είναι υποχρεωτική")
    @Size(max = 50, message = "Η περιγραφή δεν πρέπει να ξεπερνά τους 50 χαρακτήρες")
    private String description;

    @NotBlank(message = "Η κατηγορία είναι υποχρεωτική")
    @Size(max = 50, message = "Η κατηγορία δεν πρέπει να ξεπερνά τους 50 χαρακτήρες")
    private String category;

    @NotNull(message = "Το ποσό είναι υποχρεωτικό")
    @DecimalMin(value = "0.01", inclusive = true, message = "Το ποσό πρέπει να είναι μεγαλύτερο από 0")
    @DecimalMax(value = "1000000", inclusive = true, message = "Δεν επιτρέπεται ποσό μεγαλύτερο του εκατομμυρίου")
    private BigDecimal amount;

    @PastOrPresent(message = "Η ημερομηνία δεν μπορεί να είναι μελλοντική")
    @NotNull(message = "Η ημερομηνία είναι υποχρεωτική")
    private LocalDate date;

    @NotNull(message = "Ο τύπος συναλλαγής είναι υποχρεωτικός")
    private TransactionType type;

    private TransactionMeans means;

    @Size(max = 500, message = "Οι σημειώσεις δεν πρέπει να ξεπερνούν τους 500 χαρακτήρες")
    private String notes;

    private Long userId; // Θα χρησιμοποιούμε μόνο το id του User αντί για ολόκληρο τον User

    // Getters και Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public TransactionMeans getMeans() {
        return means;
    }

    public void setMeans(TransactionMeans means) {
        this.means = means;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
