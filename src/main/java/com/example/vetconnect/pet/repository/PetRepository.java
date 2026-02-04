package com.example.vetconnect.pet.repository;

import com.example.vetconnect.pet.entity.Pet;
import com.example.vetconnect.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet,Long> {
    List<Pet> findAll();
   /* Optional<Pet> findById(Long id);*/
}
