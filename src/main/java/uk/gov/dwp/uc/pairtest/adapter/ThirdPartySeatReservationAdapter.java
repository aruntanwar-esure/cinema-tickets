package uk.gov.dwp.uc.pairtest.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.adapter.model.ReservationDTO;
import uk.gov.dwp.uc.pairtest.adapter.model.ReservationResponse;

public class ThirdPartySeatReservationAdapter implements ISeatReservationAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartySeatReservationAdapter.class);

    /**
     * You may have a new reservation service (for example: in-house service) for new adapter
     */
    @Autowired
    SeatReservationService seatReservationService;

    @Override
    public Object reserveSeat(Object ticketReservationRequest) {
        // This adapter takes an object and returns an object
        ReservationDTO reservationDTO = (ReservationDTO) ticketReservationRequest;

        LOGGER.info("Ticket reservation to third party started for account id: {}", reservationDTO.getAccountId());
        seatReservationService.reserveSeat(reservationDTO.getAccountId(), reservationDTO.getTotalSeatsToAllocate());
        LOGGER.info("Ticket reservation to third party successfully ends for account id: {}", reservationDTO.getAccountId());
        return ReservationResponse.builder().status("SUCCESS").message("Seats reserved successfully").build();
    }
}
