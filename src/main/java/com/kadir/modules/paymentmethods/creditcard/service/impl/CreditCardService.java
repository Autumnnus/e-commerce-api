package com.kadir.modules.paymentmethods.creditcard.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.jwt.JWTService;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.merge.MergeUtils;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardCreateDto;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardDto;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardUpdateDto;
import com.kadir.modules.paymentmethods.creditcard.model.CreditCard;
import com.kadir.modules.paymentmethods.creditcard.repository.CreditCardRepository;
import com.kadir.modules.paymentmethods.creditcard.service.ICreditCardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService implements ICreditCardService {

    private final ModelMapper modelMapper;
    private final CreditCardRepository creditCardRepository;
    private final JWTService jwtService;
    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final UserRepository userRepository;

    @Override
    public CreditCardDto addCreditCard(CreditCardCreateDto creditCardCreateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
        if (!creditCardCreateDto.isExpirationDateValid(creditCardCreateDto.getExpirationDate())) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Invalid expiration date"));
        }
        creditCardRepository.findByCardNumber(creditCardCreateDto.getCardNumber())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Credit card already exists")));

        CreditCard creditCard = modelMapper.map(creditCardCreateDto, CreditCard.class);
        String tokenizedCardNumber = jwtService.tokenizeCardNumber(creditCard.getCardNumber());
        creditCard.setCardNumber(tokenizedCardNumber);
        creditCard.setUser(user);
        CreditCard savedCreditCard = creditCardRepository.save(creditCard);
        savedCreditCard.setCardNumber(jwtService.decodeCardNumber(savedCreditCard.getCardNumber()));
        return modelMapper.map(savedCreditCard, CreditCardDto.class);
    }

    @Override
    public CreditCardDto updateCreditCard(Long creditCardId, CreditCardUpdateDto creditCardUpdateDto) {
        CreditCard existingCard = creditCardRepository.findById(creditCardId).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Credit Card not found")));
        MergeUtils.copyNonNullProperties(creditCardUpdateDto, existingCard);
        CreditCard savedCreditCard = creditCardRepository.save(existingCard);
        return modelMapper.map(savedCreditCard, CreditCardDto.class);
    }

    @Override
    public CreditCardDto deleteCreditCard(Long creditCardId) {
        CreditCard existingCard = creditCardRepository.findById(creditCardId).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Credit Card not found")));
        creditCardRepository.delete(existingCard);
        return modelMapper.map(existingCard, CreditCardDto.class);
    }

    @Override
    public CreditCardDto getCreditCard(Long creditCardId) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        CreditCard creditCard = creditCardRepository.findById(creditCardId).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Credit Card not found")));
        if (!creditCard.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "You are not authorized to see this credit card"));
        }
        creditCard.setCardNumber(jwtService.decodeCardNumber(creditCard.getCardNumber()));
        return modelMapper.map(creditCard, CreditCardDto.class);
    }

    @Override
    public List<CreditCardDto> getCreditCards() {
        Long userId = authenticationServiceImpl.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
        List<CreditCard> creditCards = creditCardRepository.findByUserId(userId);
        return creditCards.stream()
                .peek(creditCard -> creditCard.setCardNumber(jwtService.decodeCardNumber(creditCard.getCardNumber())))
                .peek(creditCard -> creditCard.setUser(user))
                .map(creditCard -> modelMapper.map(creditCard, CreditCardDto.class))
                .toList();
    }
}
