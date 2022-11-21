package com.francisca.droneService.exception;

public class MedicineDetailsAlreadyExistException extends RuntimeException {
    public MedicineDetailsAlreadyExistException(String message){
        super (message);
    }
}
