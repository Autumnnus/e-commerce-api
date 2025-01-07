package com.kadir.modules.paymentmethods.wallet.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.paymentmethods.wallet.dto.WalletDto;
import com.kadir.modules.paymentmethods.wallet.dto.WalletIUDto;
import com.kadir.modules.paymentmethods.wallet.model.Wallet;
import com.kadir.modules.paymentmethods.wallet.repository.WalletRepository;
import com.kadir.modules.paymentmethods.wallet.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {

    private final ModelMapper modelMapper;
    private final WalletRepository walletRepository;
    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final UserRepository userRepository;

    @Override
    public WalletDto addFunds(WalletIUDto walletIUDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Wallet not found")));
        wallet.setBalance(wallet.getBalance().add(walletIUDto.getBalance()));
        Wallet savedWallet = walletRepository.save(wallet);
        return modelMapper.map(savedWallet, WalletDto.class);
    }

    @Override
    public WalletDto withdrawFunds(WalletIUDto walletIUDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Wallet not found")));
        if (wallet.getBalance().compareTo(walletIUDto.getBalance()) < 0) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Insufficient balance"));
        }
        wallet.setBalance(wallet.getBalance().subtract(walletIUDto.getBalance()));
        Wallet savedWallet = walletRepository.save(wallet);
        return modelMapper.map(savedWallet, WalletDto.class);
    }

    @Override
    public WalletDto getWallet() {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Wallet not found")));
        if (!wallet.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "You are not authorized to see this credit card"));
        }
        return modelMapper.map(wallet, WalletDto.class);
    }
}
