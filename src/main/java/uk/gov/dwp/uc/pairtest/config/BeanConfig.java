package uk.gov.dwp.uc.pairtest.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.adapter.IPaymentAdapter;
import uk.gov.dwp.uc.pairtest.adapter.ISeatReservationAdapter;
import uk.gov.dwp.uc.pairtest.adapter.ThirdPartyPaymentAdapter;
import uk.gov.dwp.uc.pairtest.adapter.ThirdPartySeatReservationAdapter;
import uk.gov.dwp.uc.pairtest.service.TicketService;
import uk.gov.dwp.uc.pairtest.service.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.PaymentCalculationStrategy;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.SeatsCalculationStrategy;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.ThirdPartyPaymentCalculator;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.ThirdPartySeatsCalculator;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.validator.SeatReservationValidator;
import uk.gov.dwp.uc.pairtest.service.businessStrategy.validator.ThirdPartySeatReservationValidator;

@Configuration
@ConditionalOnProperty(name = "cinemaTickets.issuingParty.name", havingValue = "third-party", matchIfMissing = true)
public class BeanConfig {
    @Bean
    public TicketService ticketService() {
        return new TicketServiceImpl();
    }

    @Bean
    public SeatReservationService thirdPartySeatReservationService() {

        return (accountId, totalSeatsToAllocate) -> {
            //TODO, just to instantiate the bean here
        };
    }

    @Bean
    public TicketPaymentService thirdPartyTicketPaymentService() {
        return new TicketPaymentServiceImpl();
    }

    @Bean
    public IPaymentAdapter thirdPartyPaymentAdapter() {
        return new ThirdPartyPaymentAdapter();
    }

    @Bean
    public ISeatReservationAdapter thirdPartySeatReservationAdapter() {
        return new ThirdPartySeatReservationAdapter();
    }

    @Bean
    public SeatReservationValidator thirdPartyReservationValidator() {
        return new ThirdPartySeatReservationValidator();
    }

    @Bean
    public SeatsCalculationStrategy thirdPartySeatsCalculator() {

        return new ThirdPartySeatsCalculator();
    }

    @Bean
    public PaymentCalculationStrategy thirdPartyPaymentCalculator() {
        return new ThirdPartyPaymentCalculator();
    }
}
