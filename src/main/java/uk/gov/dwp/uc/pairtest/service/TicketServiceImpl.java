package uk.gov.dwp.uc.pairtest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dwp.uc.pairtest.adapter.IPaymentAdapter;
import uk.gov.dwp.uc.pairtest.adapter.ISeatReservationAdapter;
import uk.gov.dwp.uc.pairtest.adapter.model.PaymentDTO;
import uk.gov.dwp.uc.pairtest.adapter.model.ReservationDTO;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.CinemaTicketExceptionHandler;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.PaymentCalculationStrategy;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.SeatsCalculationStrategy;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private ISeatReservationAdapter seatReservationAdapter;
    @Autowired
    private IPaymentAdapter paymentAdapter;
    @Autowired
    private SeatsCalculationStrategy calculateSeatsStrategy;
    @Autowired
    private PaymentCalculationStrategy calculatePaymentStrategy;

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        LOGGER.info("Ticket purchase call started for account id: {}", accountId);
        int totalSeats = calculateSeatsStrategy.calculateSeats(ticketTypeRequests);
        int totalPayment = calculatePaymentStrategy.calculatePayment(ticketTypeRequests);

        seatReservationAdapter.reserveSeat(ReservationDTO.builder().accountId(accountId).totalSeatsToAllocate(totalSeats).build());
        paymentAdapter.paymentRequest(PaymentDTO.builder().accountId(accountId).totalAmountToPay(totalPayment).build());
        LOGGER.info("Ticket purchase call successfully ends for account id: {}", accountId);
    }
}
