package com.francisca.droneService.dto;

import com.francisca.droneService.enums.DroneModel;
import com.francisca.droneService.enums.DroneState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DroneDto {

    @Max(value = 100 , message = "serialNumber should not be more than 100 characters")
    private String serialNumber;
    private DroneModel model;
    private DroneState state;
    @Pattern(regexp = "^(100|[1-9]?\\d)$",message = "Battery can not be more than 100%")
    private BigDecimal batteryCapacity;
    @DecimalMax(value = "500", message =" Drone cannot carry more than {value} grams")
    private double weight;
    private List<MedicationDto> medicationDto;

}
