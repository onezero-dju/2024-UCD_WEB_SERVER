package com.ucd.keynote.domain.organization.exception;

public class InvalidOrganizationDataException extends RuntimeException{
    public InvalidOrganizationDataException(String message){
        super(message);
    }
}
