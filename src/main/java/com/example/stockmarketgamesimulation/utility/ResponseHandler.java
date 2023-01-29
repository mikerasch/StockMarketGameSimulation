package com.example.stockmarketgamesimulation.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String status, String body,HttpStatus httpStatus){
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("Status", status);
        responseMap.put("Body", body);
        responseMap.put("Timestamp", new Date());
        return new ResponseEntity<>(responseMap,httpStatus);
    }
}
