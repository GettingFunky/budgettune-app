package gr.aueb.budgettune.mapper;

import gr.aueb.budgettune.dto.TransactionDTO;
import gr.aueb.budgettune.exception.UserNotFoundException;
import gr.aueb.budgettune.model.Transaction;
import gr.aueb.budgettune.model.User;
import gr.aueb.budgettune.repository.UserRepository;

public class TransactionMapper {

    public static Transaction toEntity(TransactionDTO transactionDTO, UserRepository userRepository) {
        Transaction transaction = new Transaction();

        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDate(transactionDTO.getDate());
        transaction.setType(transactionDTO.getType());
        transaction.setNotes(transactionDTO.getNotes());

//        if (transactionDTO.getUserId() != null) {
//            User user = userRepository.findById(transactionDTO.getUserId())
//                    .orElseThrow(() -> new UserNotFoundException(transactionDTO.getUserId()));
//            transaction.setUser(user);
//        }

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException(1L));
        transaction.setUser(user);

        return transaction;
    }

    public static TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();

        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        dto.setNotes(transaction.getNotes());

        if (transaction.getUser() != null) {
            dto.setUserId(transaction.getUser().getId());
        }

        return dto;
    }
}
