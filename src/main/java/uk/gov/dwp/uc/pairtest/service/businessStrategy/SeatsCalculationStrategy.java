package uk.gov.dwp.uc.pairtest.service.businessStrategy;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

/**
 * Strategy to switch between seats allocation ways
 */
public interface SeatsCalculationStrategy {
    int calculateSeats(TicketTypeRequest... ticketTypeRequests);
}
