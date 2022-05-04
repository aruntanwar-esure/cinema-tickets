package uk.gov.dwp.uc.pairtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
public class TicketReservationResponse {
    private String status;
    private String message;
    private Map<String, String> errors;
}
