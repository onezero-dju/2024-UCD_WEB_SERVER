package com.ucd.keynote.domain.organization.exception;

public class DuplicateOrganizationNameException extends RuntimeException{
    public DuplicateOrganizationNameException(String message){
        super(message);
    }
}
