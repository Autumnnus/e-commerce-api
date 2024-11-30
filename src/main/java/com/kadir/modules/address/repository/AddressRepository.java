package com.kadir.modules.address.repository;

import com.kadir.modules.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT c FROM Address c WHERE c.user.id = ?1")
    List<Address> findByUserId(Long userId);
}
