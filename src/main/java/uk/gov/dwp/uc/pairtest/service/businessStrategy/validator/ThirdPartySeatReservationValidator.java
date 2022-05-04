package uk.gov.dwp.uc.pairtest.service.businessStrategy.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;

public class ThirdPartySeatReservationValidator implements SeatReservationValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartySeatReservationValidator.class);

    @Value("${cinemaTickets.validation.noOfTickets.allowed:20}")
    private int noOfTicketsAllowed;
    @Value("${cinemaTickets.validation.noOfTickets.error}")
    private String noOfTicketsError;

    @Value("${cinemaTickets.validation.adultPresence.error}")
    private String adultPresenceError;

    private void validateNumberOfTickets(TicketTypeRequest[] ticketTypeRequests) {
        int totalTickets = Arrays.stream(ticketTypeRequests).mapToInt(req -> req.getNoOfTickets()).sum();
        if (totalTickets > noOfTicketsAllowed) {
            LOGGER.error("Error encountered for number of tickets for account id: {}. Description: {}", MDC.get("accountId"),
                    String.format(noOfTicketsError, noOfTicketsAllowed));
            throw new InvalidPurchaseException(String.format(noOfTicketsError, noOfTicketsAllowed));
        }
    }

    private void validateAdultPresence(TicketTypeRequest[] ticketTypeRequests) {
        long totalAdults = Arrays.stream(ticketTypeRequests).filter(req -> req.getTicketType() == TicketTypeRequest.Type.ADULT).count();
        if (totalAdults == 0) {
            LOGGER.error("Error encountered for missing adult for account id: {}. Description: {}", MDC.get("accountId"),
                    adultPresenceError);
            throw new InvalidPurchaseException(adultPresenceError);
        }
    }

    @Override
    public void validate(Object request) {
        TicketTypeRequest ticketTypeRequests[] = (TicketTypeRequest[]) request;
        LOGGER.info("Validating business rules for tickets before allocation for account id: {}", MDC.get("accountId"));

        validateNumberOfTickets(ticketTypeRequests);
        validateAdultPresence(ticketTypeRequests);
        LOGGER.info("Validation of business rules successful for tickets allocation for account id: {}", MDC.get("accountId"));
    }
}
