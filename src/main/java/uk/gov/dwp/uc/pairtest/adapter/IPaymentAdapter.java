package uk.gov.dwp.uc.pairtest.adapter;

/**
 * Adapter lets us choose between payment providers, third party or in-house in our app
 */
public interface IPaymentAdapter {
    /**
     * This method completes payment and returns confirmation object
     * Takes an object and returns an object, behaves in generic manner.
     * @param requestDetails
     * @return - confirmation object
     */
    Object paymentRequest(Object requestDetails);
}
