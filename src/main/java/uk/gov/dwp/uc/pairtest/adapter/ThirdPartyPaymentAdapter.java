package uk.gov.dwp.uc.pairtest.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import thirdparty.paymentgateway.TicketPaymentService;
import uk.gov.dwp.uc.pairtest.adapter.model.PaymentDTO;
import uk.gov.dwp.uc.pairtest.adapter.model.ReservationResponse;

public class ThirdPartyPaymentAdapter implements IPaymentAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyPaymentAdapter.class);

    /**
     * You may have a new ticket service (for example: in-house service) for new adapter
     */
    @Autowired
    TicketPaymentService ticketService;

    @Override
    public Object paymentRequest(Object requestDetails) {
        PaymentDTO paymentDTO = (PaymentDTO) requestDetails;

        LOGGER.info("Ticket payment to third party started for account id: {}", paymentDTO.getAccountId());
        ticketService.makePayment(paymentDTO.getAccountId(), paymentDTO.getTotalAmountToPay());
        LOGGER.info("Ticket payment to third party ends successfully for account id: {}", paymentDTO.getAccountId());

        return ReservationResponse.builder().status("SUCCESS").message("Payment done successfully").build();
    }
}
