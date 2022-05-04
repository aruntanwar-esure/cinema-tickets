package uk.gov.dwp.uc.pairtest.controller;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dwp.uc.pairtest.domain.TicketReservationRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketReservationResponse;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.service.TicketService;

import javax.validation.Valid;

@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;

    @Value("${logging.account-id-key:accountId}")
    private String accountIdKey;

    @PostMapping("/reserve")
    public ResponseEntity<TicketReservationResponse> createReservation(@Valid @RequestBody TicketReservationRequest ticketReservationRequest) {
        MDC.clear();
        MDC.put("accountId", String.valueOf(ticketReservationRequest.getAccountId()));
        ticketService.purchaseTickets(ticketReservationRequest.getAccountId(), ticketReservationRequest.getTicketTypeRequests().toArray(TicketTypeRequest[]::new));

        String successMessage = String.format("Ticket(s) have been successfully booked for account id: %d", ticketReservationRequest.getAccountId());
        return new ResponseEntity<>(TicketReservationResponse.builder().status("SUCCESS").message(successMessage).build(), HttpStatus.OK);
    }
}
