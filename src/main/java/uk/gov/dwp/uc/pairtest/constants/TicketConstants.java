package uk.gov.dwp.uc.pairtest.constants;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.HashMap;
import java.util.Map;

@Component
@Setter
public class TicketConstants {
    private Map<TicketTypeRequest.Type, Integer> ticketTypeToPrice;
    @Value("${cinemaTickets.prices.INFANT:0}")
    int infantPrice;

    @Value("${cinemaTickets.prices.CHILD:10}")
    int childPrice;

    @Value("${cinemaTickets.prices.ADULT:20}")
    int adultPrice;


    public Map<TicketTypeRequest.Type, Integer> ticketTypeToPrice() {
        if (ticketTypeToPrice == null) {
            ticketTypeToPrice = new HashMap<>() {
                {
                    put(TicketTypeRequest.Type.INFANT, infantPrice);
                    put(TicketTypeRequest.Type.CHILD, childPrice);
                    put(TicketTypeRequest.Type.ADULT, adultPrice);
                }
            };
        }
        return ticketTypeToPrice;
    }
}