package com.kadir.modules.paymentmethods.creditcard.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDate;

@Getter
@Setter
public class CreditCardCreateDto {

    @NotNull(message = "Card holder name is required")
    private String cardHolderName;

    @NotNull(message = "Card number is required")
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    @CreditCardNumber(message = "Invalid card number")
    private String cardNumber;

    @NotNull(message = "Expiration date is required")
    @Pattern(regexp = "\\d{2}/\\d{2}", message = "Expiration date must be in the format MM/YY")
    private String expirationDate;

    @NotNull(message = "Address id is required")
    private Long addressId;

    public boolean isExpirationDateValid(String expirationDate) {
        try {
            String[] parts = expirationDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt("20" + parts[1]);

            LocalDate parsedDate = LocalDate.of(year, month, 1); // Yıl ve ayı belirten tarih
            boolean isValid = !parsedDate.isBefore(LocalDate.now());

            boolean validMonth = month >= 1 && month <= 12;

            return isValid && validMonth;
        } catch (Exception e) {
            return false;
        }
    }


}
