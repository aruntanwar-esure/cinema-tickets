package uk.gov.dwp.uc.pairtest.service.businessStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dwp.uc.pairtest.constants.TicketConstants;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.Arrays;
import java.util.Map;

public class ThirdPartyPaymentCalculator implements PaymentCalculationStrategy {
    @Autowired
    private TicketConstants ticketConstants;

    @Override
    public int calculatePayment(TicketTypeRequest... ticketTypeRequests) {
        Map<TicketTypeRequest.Type, Integer> ticketTypeToPrice = ticketConstants.ticketTypeToPrice();
        int totalPrice = Arrays.stream(ticketTypeRequests).mapToInt(req -> {
            int price = ticketTypeToPrice.get(req.getTicketType());
            int noOfTickets = req.getNoOfTickets();
            return price * noOfTickets;
        }).sum();
        return totalPrice;
    }
}
