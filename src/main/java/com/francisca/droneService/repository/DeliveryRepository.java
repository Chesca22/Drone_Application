package com.francisca.droneService.repository;

import com.francisca.droneService.model.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryDetails, Long> {
}
