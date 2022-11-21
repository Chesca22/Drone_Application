package com.francisca.droneService.service;

import com.francisca.droneService.dto.DroneDeliveryDto;
import com.francisca.droneService.dto.DroneDto;
import com.francisca.droneService.model.DroneDetails;
import com.francisca.droneService.response.ApiResponse;
import java.util.List;


public interface DroneService {

    ApiResponse<DroneDto> registerDrone(DroneDto droneDto);

    ApiResponse<?> getAvailableDrones();

   // DroneDetails getADroneState();

    ApiResponse<?> getAllDrones();

    DroneDetails getADroneState();

    ApiResponse<DroneDetails> getADrone(String serialNo);

    ApiResponse<DroneDetails> loadDrone(String droneSerialNo, String medCode);


    ApiResponse<String> deliverMedication(DroneDeliveryDto droneDeliveryDto);

    ApiResponse<?> getLoadedMedication(String serialNo);

    void periodicCheckForBatteryHealth(List<DroneDetails> drones);

    ApiResponse<?> checkBatteryLevel(String serialNumber);
}
