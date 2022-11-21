package com.francisca.droneService.repository;

import com.francisca.droneService.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Optional<Medication> findByCode(String medicineCode);
    Optional<Medication> findByName(String medicineName);
}
