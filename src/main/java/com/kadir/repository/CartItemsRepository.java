package com.kadir.repository;

import com.kadir.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    @Query("SELECT c FROM CartItems c WHERE c.user.id = ?1")
    List<CartItems> findByUserId(Long userId);
}
