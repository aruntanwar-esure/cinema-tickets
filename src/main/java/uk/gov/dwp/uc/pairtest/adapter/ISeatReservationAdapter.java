package uk.gov.dwp.uc.pairtest.adapter;

/**
 * Adapter lets us choose between service providers, third party or in-house in out app
 */
public interface ISeatReservationAdapter {
    /**
     * This method reserves a seat and returns the response object
     * Takes an object and returns an object, behaves in generic manner.
     * @param ticketReservationRequest
     * @return - response object
     */
    Object reserveSeat(Object ticketReservationRequest);
}
