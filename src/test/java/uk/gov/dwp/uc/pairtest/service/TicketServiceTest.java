package uk.gov.dwp.uc.pairtest.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.dwp.uc.pairtest.adapter.IPaymentAdapter;
import uk.gov.dwp.uc.pairtest.adapter.ISeatReservationAdapter;
import uk.gov.dwp.uc.pairtest.config.BeanConfig;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.PaymentCalculationStrategy;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.SeatsCalculationStrategy;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BeanConfig.class, TestConfig.class})
@TestPropertySource(properties = {
        "cinemaTickets.validation.noOfTickets.error=You cannot purchase more than %d tickets",
        "cinemaTickets.validation.adultPresence.error=You cannot book Child/Infant ticket without Adult ticket"
})
public class TicketServiceTest {
    // since adapters only call third party seat reservation and payment,
    // we don't check the logic there, hence mocks
    @MockBean
    private ISeatReservationAdapter seatReservationAdapter;
    @MockBean
    private IPaymentAdapter paymentAdapter;

    @Autowired
    private SeatsCalculationStrategy calculateSeatsStrategy;
    @Autowired
    private PaymentCalculationStrategy calculatePaymentStrategy;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Autowired
    TicketService ticketServiceImpl;

    @Test
    public void testNumberOfTickets() {
        exceptionRule.expect(InvalidPurchaseException.class);
        exceptionRule.expectMessage("You cannot purchase more than 20 tickets");
        List<TicketTypeRequest> ticketTypeRequests = new ArrayList<>();
        ticketTypeRequests.add(new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 31));
        ticketServiceImpl.purchaseTickets(1L, ticketTypeRequests.toArray(TicketTypeRequest[]::new));
    }

    @Test
    public void testChildBookingWithoutParent() {
        exceptionRule.expect(InvalidPurchaseException.class);
        exceptionRule.expectMessage("You cannot book Child/Infant ticket without Adult ticket");
        List<TicketTypeRequest> ticketTypeRequests = new ArrayList<>();
        ticketTypeRequests.add(new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 11));
        ticketServiceImpl.purchaseTickets(1L, ticketTypeRequests.toArray(TicketTypeRequest[]::new));
    }

    @Test
    public void testInfantBookingWithoutParent() {
        exceptionRule.expect(InvalidPurchaseException.class);
        exceptionRule.expectMessage("You cannot book Child/Infant ticket without Adult ticket");
        List<TicketTypeRequest> ticketTypeRequests = new ArrayList<>();
        ticketTypeRequests.add(new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 11));
        ticketServiceImpl.purchaseTickets(1L, ticketTypeRequests.toArray(TicketTypeRequest[]::new));
    }
}
