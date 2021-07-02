package com.roberto.stoomtest.repositories;

import com.roberto.stoomtest.entities.Address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
