package com.francisca.droneService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francisca.droneService.dto.DroneDeliveryDto;
import com.francisca.droneService.dto.DroneDto;
import com.francisca.droneService.enums.DroneModel;
import com.francisca.droneService.enums.DroneState;
import com.francisca.droneService.response.ApiResponse;
import com.francisca.droneService.service.DroneService;
import com.francisca.droneService.service.MedicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {DroneController.class})
@ExtendWith(SpringExtension.class)
class DroneControllerTest {
    @Autowired
    private DroneController droneController;

    @MockBean
    private DroneService droneService;

    @MockBean
    private MedicationService medicationService;

    @Test
    void testToRegisterDrone() throws Exception {
        when(droneService.registerDrone((DroneDto) any())).thenReturn(new ApiResponse<>());

        DroneDto droneDto = new DroneDto();
        droneDto.setBatteryCapacity(BigDecimal.valueOf(42L));
        droneDto.setMedicationDto(new ArrayList<>());
        droneDto.setModel(DroneModel.LIGHTWEIGHT);
        droneDto.setSerialNumber("DJH_MAVIC57_AIR_8");
        droneDto.setState(DroneState.IDLE);
        droneDto.setWeight(120.0d);
        String content = (new ObjectMapper()).writeValueAsString(droneDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/register-drone")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(droneController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":null,\"data\":null}"));
    }

    @Test
    void testToCheckAvailableIdleDrones() throws Exception {
        when(droneService.getAvailableDrones()).thenReturn(new ApiResponse<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/available-drone");
        MockMvcBuilders.standaloneSetup(droneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":null,\"data\":null}"));
    }


    @Test
    void testGetAllDrones() throws Exception {
        when((ApiResponse<?>) droneService.getAllDrones()).thenReturn(new ApiResponse<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/all-drones");
        MockMvcBuilders.standaloneSetup(droneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":null,\"data\":null}"));
    }


    @Test
    void testToDeliverMedication() throws Exception {
        when(droneService.deliverMedication((DroneDeliveryDto) any())).thenReturn(new ApiResponse<>());

        DroneDeliveryDto droneDeliveryDto = new DroneDeliveryDto();
        droneDeliveryDto.setDestination("Destination");
        droneDeliveryDto.setMedicineCode("Medicine Code");
        droneDeliveryDto.setSerialNumber("DJH_MAVIC57_AIR_8");
        droneDeliveryDto.setSource("Source");
        String content = (new ObjectMapper()).writeValueAsString(droneDeliveryDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/droneDelivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(droneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":null,\"data\":null}"));
    }

    @Test
    void testToFindAnIdleDrone() throws Exception {
        when(droneService.getADrone((String) any())).thenReturn(new ApiResponse<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/get_drone/{drone_no}",
                "Drone no");
        MockMvcBuilders.standaloneSetup(droneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":null,\"data\":null}"));
    }


    @Test
    void testGetAllMedication() throws Exception {
        when((ApiResponse<?>) medicationService.listOfMedications()).thenReturn(new ApiResponse<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/all-medication");
        MockMvcBuilders.standaloneSetup(droneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":null,\"data\":null}"));
    }

}

