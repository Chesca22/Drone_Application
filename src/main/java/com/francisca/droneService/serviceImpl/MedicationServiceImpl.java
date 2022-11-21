package com.francisca.droneService.serviceImpl;

import com.francisca.droneService.dto.MedicationDto;
import com.francisca.droneService.exception.MedicineDetailsAlreadyExistException;
import com.francisca.droneService.model.Medication;
import com.francisca.droneService.repository.MedicationRepository;
import com.francisca.droneService.response.ApiResponse;
import com.francisca.droneService.service.MedicationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor

public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;

    @Override
    public ApiResponse<MedicationDto> medicationDetails(MedicationDto medicationDto) {
        Optional<Medication> medicine = medicationRepository.findByCode(medicationDto.getCode());
        if (medicine.isEmpty()) {
            Medication medication = Medication.builder()
                    .name(medicationDto.getName())
                    .weight(medicationDto.getWeight())
                    .code(medicationDto.getCode())
                    .image(medicationDto.getImage())
                    .build();

            medicationRepository.save(medication);
            return new ApiResponse<>("Medication details entered successfully", medicationDto);

        } else
            throw new MedicineDetailsAlreadyExistException("Medication details already entered");

    }

    @Override
    public ApiResponse<?> listOfMedications() {
        List<Medication> medication = medicationRepository.findAll();
        return new ApiResponse<>("success", medication);
    }
}
