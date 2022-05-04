package uk.gov.dwp.uc.pairtest.service.businessStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.validator.SeatReservationValidator;

import java.util.Arrays;

public class ThirdPartySeatsCalculator implements SeatsCalculationStrategy {
    @Autowired
    SeatReservationValidator seatReservationValidator;

    @Override
    public int calculateSeats(TicketTypeRequest... ticketTypeRequests) {
        seatReservationValidator.validate(ticketTypeRequests);
        int totalSeats = Arrays.stream(ticketTypeRequests).filter(req -> req.getTicketType() != TicketTypeRequest.Type.INFANT).mapToInt(req -> req.getNoOfTickets()).sum();
        return totalSeats;
    }
}
