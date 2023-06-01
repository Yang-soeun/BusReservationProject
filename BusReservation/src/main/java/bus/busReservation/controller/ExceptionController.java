package bus.busReservation.controller;

import bus.busReservation.exception.EndTimetableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EndTimetableException.class)
    public String handle(EndTimetableException ex){

        return "reservation/endBusStop";
    }

}
