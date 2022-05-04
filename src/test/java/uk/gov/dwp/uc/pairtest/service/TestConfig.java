package uk.gov.dwp.uc.pairtest.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import uk.gov.dwp.uc.pairtest.constants.TicketConstants;

@TestConfiguration
public class TestConfig {
    @Bean
    public TicketConstants ticketConstants() {
        TicketConstants ticketConstants = new TicketConstants();
        ticketConstants.setAdultPrice(20);
        ticketConstants.setChildPrice(20);
        ticketConstants.setInfantPrice(0);
        return ticketConstants;
    }
}
