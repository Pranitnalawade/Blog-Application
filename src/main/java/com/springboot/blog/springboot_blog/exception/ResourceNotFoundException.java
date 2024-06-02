package com.springboot.blog.springboot_blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private String resoureceName;
    private String fieldName;
    private long fielsValue;

    public ResourceNotFoundException(String resoureceName, String fieldName, long fielsValue) {
        super(String.format("%s not found with %s : '%s'",resoureceName,fieldName,fielsValue)); //Post not found with is :1
        this.resoureceName = resoureceName;
        this.fieldName = fieldName;
        this.fielsValue = fielsValue;
    }

    public String getResoureceName() {
        return resoureceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFielsValue() {
        return fielsValue;
    }
}
