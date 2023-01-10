package com.customercvarchive.exception.handler;

import com.customercvarchive.exception.CustomJwtException;
import com.customercvarchive.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
    @NotNull
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          @NotNull HttpHeaders headers,
                                                          @NotNull HttpStatus status,
                                                          @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class) //Hangi Exception ı handler edeceğini ifade eder
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        Map<String,String> map=new HashMap<>();
        map.put("error_message",entityNotFoundException.getMessage());
        map.put("error_details",entityNotFoundException.getDetails());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        // return new ResponseEntity(entityNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomJwtException.class)
    public ResponseEntity<Map<String, String>> handleCustomJwtException(CustomJwtException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error_message", exception.getMessage());
        map.put("error_status", exception.getHttpStatus().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }



    //Exception ana hata sınıfını da buraya ekleriz çünkü tahmin ettiğimiz hataların dışında bir hata çıktığında da o hatayı yakalamak isteriz
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception entityNotFoundException){
        Map<String,String> map=new HashMap<>();
        map.put("error_message",entityNotFoundException.getMessage());
        map.put("\nerror_cause",entityNotFoundException.getCause().toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }
}

