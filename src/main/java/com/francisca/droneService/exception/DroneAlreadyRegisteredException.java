package com.francisca.droneService.exception;

import lombok.Getter;

@Getter
public class DroneAlreadyRegisteredException extends RuntimeException {
    public DroneAlreadyRegisteredException(String message){
        super(message);
    }
}
