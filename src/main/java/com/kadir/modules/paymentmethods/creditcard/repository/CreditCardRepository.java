package com.kadir.modules.paymentmethods.creditcard.repository;

import com.kadir.modules.paymentmethods.creditcard.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    Optional<CreditCard> findByCardNumber(String cardNumber);

    List<CreditCard> findByUserId(Long userId);
}
