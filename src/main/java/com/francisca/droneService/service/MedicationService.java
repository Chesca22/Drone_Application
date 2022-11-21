package com.francisca.droneService.service;

import com.francisca.droneService.dto.MedicationDto;
import com.francisca.droneService.response.ApiResponse;


public interface MedicationService {

    ApiResponse<?> medicationDetails(MedicationDto medicationDto);


    ApiResponse<?> listOfMedications();
}
