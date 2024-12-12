package com.kadir.modules.paymentmethods.wallet.repository;

import com.kadir.modules.paymentmethods.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
