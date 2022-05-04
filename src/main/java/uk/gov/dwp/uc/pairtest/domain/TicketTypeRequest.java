package uk.gov.dwp.uc.pairtest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Immutable Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketTypeRequest {

    @JsonProperty("noOfTickets")
    @Min(value = 1, message = "The ticket value should be min 1")
    private int noOfTickets;
    @JsonProperty("type")
    @NotNull(message = "Type of ticket cannot be null")
    private Type ticketType;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.ticketType = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return ticketType;
    }

    public enum Type {
        ADULT, CHILD, INFANT
    }

}
