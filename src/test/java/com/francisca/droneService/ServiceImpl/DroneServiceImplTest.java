package com.francisca.droneService.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.francisca.droneService.dto.DroneDeliveryDto;
import com.francisca.droneService.dto.DroneDto;
import com.francisca.droneService.enums.DroneModel;
import com.francisca.droneService.enums.DroneState;
import com.francisca.droneService.exception.DroneAlreadyRegisteredException;
import com.francisca.droneService.exception.MedicineDetailsAlreadyExistException;
import com.francisca.droneService.exception.NoAvailableDroneFoundException;
import com.francisca.droneService.model.DroneDetails;
import com.francisca.droneService.repository.DeliveryRepository;
import com.francisca.droneService.repository.DroneRepository;
import com.francisca.droneService.repository.MedicationRepository;
import com.francisca.droneService.response.ApiResponse;
import com.francisca.droneService.serviceImpl.DroneServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DroneServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DroneServiceImplTest {
    @MockBean
    private DeliveryRepository deliveryRepository;

    @MockBean
    private DroneRepository droneRepository;

    @Autowired
    private DroneServiceImpl droneServiceImpl;

    @MockBean
    private MedicationRepository medicationRepository;

    @Test
    void testToRegisterDrone() {
        DroneDetails droneDetails = new DroneDetails();
        droneDetails.setBatteryCapacity(BigDecimal.valueOf(42L));
        droneDetails.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        droneDetails.setId(123L);
        droneDetails.setMedication(new ArrayList<>());
        droneDetails.setModel(DroneModel.LIGHTWEIGHT);
        droneDetails.setSerialNumber("DJH_MAVIC57_AIR_8");
        droneDetails.setState(DroneState.IDLE);
        droneDetails.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        droneDetails.setWeight(120.0d);
        when(droneRepository.save((DroneDetails) any())).thenReturn(droneDetails);
        when(droneRepository.findDroneBySerialNumber((String) any())).thenReturn(Optional.empty());
        DroneDto droneDto = new DroneDto();
        ApiResponse<DroneDto> actualRegisterDroneResult = droneServiceImpl.registerDrone(droneDto);
        assertSame(droneDto, actualRegisterDroneResult.getData());
        assertEquals("Drone registered successfully", actualRegisterDroneResult.getMessage());
        verify(droneRepository).save((DroneDetails) any());
        verify(droneRepository).findDroneBySerialNumber((String) any());
    }

    @Test
    void testToRegisterDrone2() {
        when(droneRepository.save((DroneDetails) any()))
                .thenThrow(new MedicineDetailsAlreadyExistException("An error occurred"));
        when(droneRepository.findDroneBySerialNumber((String) any()))
                .thenThrow(new MedicineDetailsAlreadyExistException("An error occurred"));
        assertThrows(MedicineDetailsAlreadyExistException.class, () -> droneServiceImpl.registerDrone(new DroneDto()));
        verify(droneRepository).findDroneBySerialNumber((String) any());
    }

    @Test
    void testGetAvailableDrones2() {
        when(droneRepository.findAllByState((DroneState) any()))
                .thenThrow(new DroneAlreadyRegisteredException("An error occurred"));
        assertThrows(DroneAlreadyRegisteredException.class, () -> droneServiceImpl.getAvailableDrones());
        verify(droneRepository).findAllByState((DroneState) any());
    }

    @Test
    void testGetAllDrones() {
        when(droneRepository.findAll()).thenReturn(new ArrayList<>());
        ApiResponse<?> actualAllDrones = droneServiceImpl.getAllDrones();
        assertTrue(((Collection<?>) actualAllDrones.getData()).isEmpty());
        assertEquals("success", actualAllDrones.getMessage());
        verify(droneRepository).findAll();
    }

    @Test
    void testGetAllDrones2() {
        when(droneRepository.findAll()).thenThrow(new DroneAlreadyRegisteredException("An error occurred"));
        assertThrows(DroneAlreadyRegisteredException.class, () -> droneServiceImpl.getAllDrones());
        verify(droneRepository).findAll();
    }

    @Test
    void testGetADroneState() {
        DroneDetails droneDetails = new DroneDetails();
        droneDetails.setBatteryCapacity(BigDecimal.valueOf(42L));
        droneDetails.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        droneDetails.setId(123L);
        droneDetails.setMedication(new ArrayList<>());
        droneDetails.setModel(DroneModel.LIGHTWEIGHT);
        droneDetails.setSerialNumber("DJH_MAVIC57_AIR_8");
        droneDetails.setState(DroneState.IDLE);
        droneDetails.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        droneDetails.setWeight(120.0d);
        when(droneRepository.findByState((DroneState) any())).thenReturn(droneDetails);
        DroneDetails actualADroneState = droneServiceImpl.getADroneState();
        assertSame(droneDetails, actualADroneState);
        assertEquals("42", actualADroneState.getBatteryCapacity().toString());
        verify(droneRepository).findByState((DroneState) any());
    }

    @Test
    void testGetADroneState2() {
        when(droneRepository.findByState((DroneState) any()))
                .thenThrow(new DroneAlreadyRegisteredException("An error occurred"));
        assertThrows(DroneAlreadyRegisteredException.class, () -> droneServiceImpl.getADroneState());
        verify(droneRepository).findByState((DroneState) any());
    }


    @Test
    void testGetADrone2() {
        when(droneRepository.findDroneBySerialNumber((String) any())).thenReturn(Optional.empty());
        assertThrows(NoAvailableDroneFoundException.class, () -> droneServiceImpl.getADrone("Serial No"));
        verify(droneRepository).findDroneBySerialNumber((String) any());
    }

    @Test
    void testDeliverMedication() {
        DroneDetails droneDetails = new DroneDetails();
        droneDetails.setBatteryCapacity(BigDecimal.valueOf(42L));
        droneDetails.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        droneDetails.setId(123L);
        droneDetails.setMedication(new ArrayList<>());
        droneDetails.setModel(DroneModel.LIGHTWEIGHT);
        droneDetails.setSerialNumber("DJH_MAVIC57_AIR_8");
        droneDetails.setState(DroneState.IDLE);
        droneDetails.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        droneDetails.setWeight(103.0d);
        Optional<DroneDetails> ofResult = Optional.of(droneDetails);
        when(droneRepository.findDroneBySerialNumber((String) any())).thenReturn(ofResult);
        assertThrows(NoAvailableDroneFoundException.class,
                () -> droneServiceImpl.deliverMedication(new DroneDeliveryDto("DJH_MAVIC57_AIR_8", "Medicine Code", "Source", "Destination")));
        verify(droneRepository).findDroneBySerialNumber((String) any());
    }


}

