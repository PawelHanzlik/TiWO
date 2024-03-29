package ph.agh.tiwo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ph.agh.tiwo.exception.Classes.*;

import java.util.Date;

@EnableWebMvc
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_REQUEST = "Invalid request";

    @ExceptionHandler(value = {NoSuchUserException.class})
    public ResponseEntity<Object> handleNoSuchUserException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoSuchProductException.class})
    public ResponseEntity<Object> handleNoSuchProductException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoSuchProductListException.class})
    public ResponseEntity<Object> handleNoSuchProductListException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(value = {ProductListAlreadyExistsException.class})
    public ResponseEntity<Object> handleProductListAlreadyExistsException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {NegativeMoneyException.class})
    public ResponseEntity<Object> handleNegativeMoneyException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {NoSuchWarehouseException.class})
    public ResponseEntity<Object> handleNoSuchWarehouseException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {WarehouseAlreadyExistsException.class})
    public ResponseEntity<Object> handleWarehouseAlreadyExistsException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {NegativeQuantityException.class})
    public ResponseEntity<Object> handleNegativeQuantityException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_REQUEST);
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}