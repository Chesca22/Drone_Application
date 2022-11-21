package com.francisca.droneService.exception;

public class NoAvailableDroneFoundException extends RuntimeException{

    public NoAvailableDroneFoundException(String message){
        super(message);
    }
}
