package uk.gov.dwp.uc.pairtest.service.businessStrategy;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

/**
 * Strategy to switch between payment ways
 */
public interface PaymentCalculationStrategy {
    int calculatePayment(TicketTypeRequest... ticketTypeRequests);
}
