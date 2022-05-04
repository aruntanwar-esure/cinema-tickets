package uk.gov.dwp.uc.pairtest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"accountId", "ticketTypeRequests"})
@Data
public class TicketReservationRequest {
    @JsonProperty("accountId")
    @NotNull(message = "Account id cannot be null")
    @Min(value = 1L, message = "The account id must be greater than 0")
    private Long accountId;

    @JsonProperty("ticketTypeRequests")
    @NotEmpty(message = "Ticket requests cannot be null or blank")
    @Valid
    private List<TicketTypeRequest> ticketTypeRequests;
}
