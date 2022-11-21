package com.francisca.droneService.exception;

public class DroneLimitException extends RuntimeException{

    public DroneLimitException(String message){
        super(message);
    }

}
