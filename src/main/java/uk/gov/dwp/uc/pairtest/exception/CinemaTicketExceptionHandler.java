package uk.gov.dwp.uc.pairtest.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.gov.dwp.uc.pairtest.domain.TicketReservationResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CinemaTicketExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CinemaTicketExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TicketReservationResponse> inValidArgumentsExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });
        TicketReservationResponse ticketReservationResponse = TicketReservationResponse.builder().
                message("Method arguments not valid").status("FAILED").errors(errors).build();

        LOGGER.error("Error in validating input for account id: {}. Exception: {}", MDC.get("accountId"),exception.getMessage());
        return new ResponseEntity<>(ticketReservationResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<TicketReservationResponse> jsonParsingExceptionHandler(JsonParseException exception) {
        TicketReservationResponse ticketReservationResponse = TicketReservationResponse.builder().
                message("Json is not formatted properly").status("FAILED").build();
        LOGGER.error("Error in json format: {}", exception.getMessage());
        return new ResponseEntity<>(ticketReservationResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPurchaseException.class)
    public ResponseEntity<TicketReservationResponse> invalidPurchaseExceptionHandler(InvalidPurchaseException exception) {
        TicketReservationResponse ticketReservationResponse = TicketReservationResponse.builder().
                message(exception.getMessage()).status("FAILED").build();

        LOGGER.error("Invalid ticket purchase for account id: {}. Exception: {}", MDC.get("accountId"), exception.getMessage());
        return new ResponseEntity<>(ticketReservationResponse, HttpStatus.BAD_REQUEST);
    }
}
