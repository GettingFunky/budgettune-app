package gr.aueb.budgettune.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "Το όνομα χρήστη είναι υποχρεωτικό")
    @Size(min = 3, max = 20, message = "Το όνομα χρήστη πρέπει να είναι από 3 έως 20 χαρακτήρες")
    private String username;

    @NotBlank(message = "Ο κωδικός είναι υποχρεωτικός")
    @Size(min = 6, max = 100, message = "Ο κωδικός πρέπει να είναι τουλάχιστον 6 χαρακτήρες")
    private String password;

    // Getters και Setters
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
}
