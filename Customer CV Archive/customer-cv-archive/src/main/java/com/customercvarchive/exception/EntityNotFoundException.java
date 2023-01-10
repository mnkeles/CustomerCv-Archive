package com.customercvarchive.exception;

import lombok.Data;

@Data
public class EntityNotFoundException extends  RuntimeException{
    private String details;
    public EntityNotFoundException(String entityName,Integer id) {
        super("Related "+entityName+" not found ! with id : ["+id+"]");
        details="Some Speciat Details";
    }

    public EntityNotFoundException(String entityName,String username) {
        super("Related "+entityName+" not found ! with username : ["+username+"]");
        details="Some Speciat Details";
    }
}
