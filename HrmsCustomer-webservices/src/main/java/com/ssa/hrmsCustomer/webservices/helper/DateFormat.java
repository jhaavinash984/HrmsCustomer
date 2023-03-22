package com.ssa.hrmsCustomer.webservices.helper;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormat {

    public LocalDate getLocalDateFromString(String date){
        //String dateInString = "2016-08-16T15:23:01Z";
        LocalDate localDate = null;
        if(date.contains("T")) {
            Instant instant = Instant.parse(date);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Kolkata"));
            localDate = localDateTime.toLocalDate();
        }else{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(date,dtf);
        }
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.parse(date, formatter);*/
        return localDate;
    }
}
