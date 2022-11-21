package com.francisca.droneService.repository;

import com.francisca.droneService.enums.DroneState;
import com.francisca.droneService.model.DroneDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<DroneDetails, Long> {
   Optional<DroneDetails> findDroneBySerialNumber(String serialNumber);
    List<DroneDetails> findAllByState(DroneState droneState);
    DroneDetails findByState(DroneState droneState);

    DroneDetails findBySerialNumberAndState(String serialNumber, DroneState states);
 }
