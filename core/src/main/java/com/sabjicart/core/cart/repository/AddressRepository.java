package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>
{
    Optional<Address> findBySubstationId (
        @Param("substationId")
        Long substationId);
}
