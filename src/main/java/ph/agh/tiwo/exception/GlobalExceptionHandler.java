package ph.agh.tiwo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ph.agh.tiwo.exception.Classes.NoSuchProductException;
import ph.agh.tiwo.exception.Classes.NoSuchUserException;

import java.util.Date;

@EnableWebMvc
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_REQUEST = "Invalid request";

    @ExceptionHandler(value = {NoSuchUserException.class})
    public ResponseEntity<Object> handleNoSuchUserException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoSuchProductException.class})
    public ResponseEntity<Object> handleNoSuchParkingLotException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}